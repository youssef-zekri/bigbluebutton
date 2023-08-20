package org.bigbluebutton.core.apps.breakout

import org.bigbluebutton.common2.msgs._
import org.bigbluebutton.core.api.BreakoutRoomCreatedInternalMsg
import org.bigbluebutton.core.apps.BreakoutModel
import org.bigbluebutton.core.db.BreakoutRoomDAO
import org.bigbluebutton.core.domain.{ BreakoutRoom2x, MeetingState2x }
import org.bigbluebutton.core.running.{ LiveMeeting, MeetingActor, OutMsgRouter }

trait BreakoutRoomCreatedMsgHdlr {
  this: MeetingActor =>

  val liveMeeting: LiveMeeting
  val outGW: OutMsgRouter

  def handleBreakoutRoomCreatedInternalMsg(msg: BreakoutRoomCreatedInternalMsg, state: MeetingState2x): MeetingState2x = {

    val updatedModel = for {
      breakoutModel <- state.breakout
      room <- breakoutModel.find(msg.breakoutId)
      startedRoom = breakoutModel.started(room, System.currentTimeMillis())
    } yield {
      val updatedRoom = sendBreakoutRoomStarted(startedRoom)
      var updatedModel = breakoutModel.update(updatedRoom)
      //      BreakoutRoomDAO.updateRoomStarted(room.id)

      // We postpone sending invitation until all breakout rooms have been created
      if (updatedModel.hasAllStarted()) {
        updatedModel = updatedModel.copy(startedOn = Some(System.currentTimeMillis()))
        BreakoutRoomDAO.updateRoomsStarted(room.parentId)
        updatedModel = sendBreakoutRoomsList(updatedModel)
      }
      updatedModel
    }

    updatedModel match {
      case Some(model) => state.update(Some(model))
      case None        => state
    }
  }

  def buildBreakoutRoomsListEvtMsg(meetingId: String, rooms: Vector[BreakoutRoomInfo], roomsReady: Boolean, sendInviteToModerators: Boolean): BbbCommonEnvCoreMsg = {
    val routing = Routing.addMsgToClientRouting(MessageTypes.BROADCAST_TO_MEETING, meetingId, "not-used")
    val envelope = BbbCoreEnvelope(BreakoutRoomsListEvtMsg.NAME, routing)
    val header = BbbClientMsgHeader(BreakoutRoomsListEvtMsg.NAME, meetingId, "not-used")

    val body = BreakoutRoomsListEvtMsgBody(meetingId, rooms, roomsReady, sendInviteToModerators)
    val event = BreakoutRoomsListEvtMsg(header, body)
    BbbCommonEnvCoreMsg(envelope, event)
  }

  def sendBreakoutRoomsList(breakoutModel: BreakoutModel): BreakoutModel = {
    val breakoutRooms = breakoutModel.rooms.values.toVector map { r =>
      val html5JoinUrls = for {
        user <- r.assignedUsers
        (redirectToHtml5JoinURL, redirectJoinURL) <- BreakoutHdlrHelpers.getRedirectUrls(liveMeeting, user, r.externalId, r.sequence.toString())
      } yield (user -> redirectToHtml5JoinURL)

      new BreakoutRoomInfo(r.name, r.externalId, r.id, r.sequence, r.shortName, r.isDefaultName, r.freeJoin, html5JoinUrls.toMap, r.captureNotes, r.captureSlides)
    }

    log.info("Sending breakout rooms list to {} with containing {} room(s)", liveMeeting.props.meetingProp.intId, breakoutRooms.length)

    val msgEvent = buildBreakoutRoomsListEvtMsg(liveMeeting.props.meetingProp.intId, breakoutRooms, true, breakoutModel.sendInviteToModerators)
    outGW.send(msgEvent)

    breakoutModel
  }

  def sendBreakoutRoomStarted(room: BreakoutRoom2x): BreakoutRoom2x = {
    log.info("Sending breakout room started {} for parent meeting {} ", room.id, room.parentId)

    def build(meetingId: String, breakout: BreakoutRoomInfo): BbbCommonEnvCoreMsg = {
      val routing = Routing.addMsgToClientRouting(
        MessageTypes.BROADCAST_TO_MEETING,
        liveMeeting.props.meetingProp.intId, "not-used"
      )
      val envelope = BbbCoreEnvelope(BreakoutRoomStartedEvtMsg.NAME, routing)
      val header = BbbClientMsgHeader(BreakoutRoomStartedEvtMsg.NAME, liveMeeting.props.meetingProp.intId, "not-used")

      val body = BreakoutRoomStartedEvtMsgBody(meetingId, breakout)
      val event = BreakoutRoomStartedEvtMsg(header, body)
      BbbCommonEnvCoreMsg(envelope, event)
    }

    val breakoutInfo = BreakoutRoomInfo(room.name, room.externalId, room.id, room.sequence, room.shortName, room.isDefaultName, room.freeJoin, Map(), room.captureNotes, room.captureSlides)
    val event = build(liveMeeting.props.meetingProp.intId, breakoutInfo)
    outGW.send(event)

    room
  }

}
