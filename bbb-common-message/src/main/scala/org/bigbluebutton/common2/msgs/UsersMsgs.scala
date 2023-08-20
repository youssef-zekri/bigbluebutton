package org.bigbluebutton.common2.msgs

object RegisterUserReqMsg { val NAME = "RegisterUserReqMsg" }
case class RegisterUserReqMsg(
    header: BbbCoreHeaderWithMeetingId,
    body:   RegisterUserReqMsgBody
) extends BbbCoreMsg
case class RegisterUserReqMsgBody(meetingId: String, intUserId: String, name: String, role: String,
                                  extUserId: String, authToken: String, sessionToken: String, avatarURL: String,
                                  guest: Boolean, authed: Boolean, guestStatus: String, excludeFromDashboard: Boolean,
                                  customParameters: Map[String, String])

object UserRegisteredRespMsg { val NAME = "UserRegisteredRespMsg" }
case class UserRegisteredRespMsg(
    header: BbbCoreHeaderWithMeetingId,
    body:   UserRegisteredRespMsgBody
) extends BbbCoreMsg
case class UserRegisteredRespMsgBody(meetingId: String, userId: String, name: String,
                                     role: String, excludeFromDashboard: Boolean, registeredOn: Long)

object ValidateAuthTokenReqMsg {
  val NAME = "ValidateAuthTokenReqMsg"

  def apply(meetingId: String, userId: String, authToken: String): ValidateAuthTokenReqMsg = {
    val header = BbbClientMsgHeader(ValidateAuthTokenReqMsg.NAME, meetingId, userId)

    val body = ValidateAuthTokenReqMsgBody(userId, authToken)
    ValidateAuthTokenReqMsg(header, body)
  }
}

case class ValidateAuthTokenReqMsg(
    header: BbbClientMsgHeader,
    body:   ValidateAuthTokenReqMsgBody
) extends StandardMsg
case class ValidateAuthTokenReqMsgBody(userId: String, authToken: String)

/**
 * Out Messages
 */

object ValidateAuthTokenRespMsg {
  val NAME = "ValidateAuthTokenRespMsg"
}
case class ValidateAuthTokenRespMsg(
    header: BbbClientMsgHeader,
    body:   ValidateAuthTokenRespMsgBody
) extends BbbCoreMsg
case class ValidateAuthTokenRespMsgBody(userId: String, authToken: String, valid: Boolean, waitForApproval: Boolean,
                                        registeredOn: Long, authTokenValidatedOn: Long, reasonCode: String, reason: String)

object UserLeftMeetingEvtMsg {
  val NAME = "UserLeftMeetingEvtMsg"
  def apply(meetingId: String, userId: String, body: UserLeftMeetingEvtMsgBody): UserLeftMeetingEvtMsg = {
    val header = BbbClientMsgHeader(UserLeftMeetingEvtMsg.NAME, meetingId, userId)
    UserLeftMeetingEvtMsg(header, body)
  }
}

case class UserLeftMeetingEvtMsg(
    header: BbbClientMsgHeader,
    body:   UserLeftMeetingEvtMsgBody
) extends BbbCoreMsg
case class UserLeftMeetingEvtMsgBody(intId: String, eject: Boolean, ejectedBy: String, reason: String, reasonCode: String)

object UserLeftFlagUpdatedEvtMsg {
  val NAME = "UserLeftFlagUpdatedEvtMsg"
  def apply(meetingId: String, userId: String, body: UserLeftFlagUpdatedEvtMsgBody): UserLeftFlagUpdatedEvtMsg = {
    val header = BbbClientMsgHeader(UserLeftFlagUpdatedEvtMsg.NAME, meetingId, userId)
    UserLeftFlagUpdatedEvtMsg(header, body)
  }
}

case class UserLeftFlagUpdatedEvtMsg(
    header: BbbClientMsgHeader,
    body:   UserLeftFlagUpdatedEvtMsgBody
) extends BbbCoreMsg
case class UserLeftFlagUpdatedEvtMsgBody(intId: String, userLeftFlag: Boolean)

object UserJoinedMeetingEvtMsg {
  val NAME = "UserJoinedMeetingEvtMsg"
  def apply(meetingId: String, userId: String, body: UserJoinedMeetingEvtMsgBody): UserJoinedMeetingEvtMsg = {
    val header = BbbClientMsgHeader(UserJoinedMeetingEvtMsg.NAME, meetingId, userId)
    UserJoinedMeetingEvtMsg(header, body)
  }
}

case class UserJoinedMeetingEvtMsg(
    header: BbbClientMsgHeader,
    body:   UserJoinedMeetingEvtMsgBody
) extends BbbCoreMsg
case class UserJoinedMeetingEvtMsgBody(
    intId:         String,
    extId:         String,
    name:          String,
    role:          String,
    guest:         Boolean,
    authed:        Boolean,
    guestStatus:   String,
    emoji:         String,
    reactionEmoji: String,
    raiseHand:     Boolean,
    away:          Boolean,
    pin:           Boolean,
    presenter:     Boolean,
    locked:        Boolean,
    avatar:        String,
    color:         String,
    clientType:    String
)

/**
 * Sent by client to get all users in a meeting.
 */
object GetUsersReqMsg { val NAME = "GetUsersReqMsg" }
case class GetUsersReqMsg(header: BbbClientMsgHeader, body: GetUsersReqMsgBody) extends StandardMsg
case class GetUsersReqMsgBody(requesterId: String)

/**
 * Sent by user to get status of recording mark.
 */
object GetRecordingStatusReqMsg { val NAME = "GetRecordingStatusReqMsg" }
case class GetRecordingStatusReqMsg(header: BbbClientMsgHeader, body: GetRecordingStatusReqMsgBody) extends StandardMsg
case class GetRecordingStatusReqMsgBody(requestedBy: String)

/**
 * Sent to user as response to get recording mark.
 */
object GetRecordingStatusRespMsg { val NAME = "GetRecordingStatusRespMsg" }
case class GetRecordingStatusRespMsg(header: BbbClientMsgHeader, body: GetRecordingStatusRespMsgBody) extends BbbCoreMsg
case class GetRecordingStatusRespMsgBody(
    recorded:                Boolean,
    recording:               Boolean,
    recordFullDurationMedia: Boolean,
    requestedBy:             String
)

/**
 * Sent by user to start recording mark.
 */
object SetRecordingStatusCmdMsg { val NAME = "SetRecordingStatusCmdMsg" }
case class SetRecordingStatusCmdMsg(header: BbbClientMsgHeader, body: SetRecordingStatusCmdMsgBody) extends StandardMsg
case class SetRecordingStatusCmdMsgBody(recording: Boolean, setBy: String)

/**
 * Sent by user to start recording mark and ignore previsous marks
 */
object RecordAndClearPreviousMarkersCmdMsg { val NAME = "RecordAndClearPreviousMarkersCmdMsg" }
case class RecordAndClearPreviousMarkersCmdMsg(header: BbbClientMsgHeader, body: RecordAndClearPreviousMarkersCmdMsgBody) extends StandardMsg
case class RecordAndClearPreviousMarkersCmdMsgBody(recording: Boolean, setBy: String)

/**
 * Sent to all users about start recording mark.
 */
object RecordingStatusChangedEvtMsg { val NAME = "RecordingStatusChangedEvtMsg" }
case class RecordingStatusChangedEvtMsg(header: BbbClientMsgHeader, body: RecordingStatusChangedEvtMsgBody) extends BbbCoreMsg
case class RecordingStatusChangedEvtMsgBody(recording: Boolean, setBy: String)

object UpdateRecordingTimerEvtMsg { val NAME = "UpdateRecordingTimerEvtMsg" }
case class UpdateRecordingTimerEvtMsg(header: BbbClientMsgHeader, body: UpdateRecordingTimerEvtMsgBody) extends BbbCoreMsg
case class UpdateRecordingTimerEvtMsgBody(time: Long)

/**
 * Sent by user to update webcamsOnlyForModerator meeting property.
 */
object UpdateWebcamsOnlyForModeratorCmdMsg { val NAME = "UpdateWebcamsOnlyForModeratorCmdMsg" }
case class UpdateWebcamsOnlyForModeratorCmdMsg(header: BbbClientMsgHeader, body: UpdateWebcamsOnlyForModeratorCmdMsgBody) extends StandardMsg
case class UpdateWebcamsOnlyForModeratorCmdMsgBody(webcamsOnlyForModerator: Boolean, setBy: String)

/**
 * Sent by user to get the value of webcamsOnlyForModerator mark.
 */
object GetWebcamsOnlyForModeratorReqMsg { val NAME = "GetWebcamsOnlyForModeratorReqMsg" }
case class GetWebcamsOnlyForModeratorReqMsg(header: BbbClientMsgHeader, body: GetWebcamsOnlyForModeratorReqMsgBody) extends StandardMsg
case class GetWebcamsOnlyForModeratorReqMsgBody(requestedBy: String)

/**
 * Sent to user as response to get webcamsOnlyForModerator mark.
 */
object GetWebcamsOnlyForModeratorRespMsg { val NAME = "GetWebcamsOnlyForModeratorRespMsg" }
case class GetWebcamsOnlyForModeratorRespMsg(header: BbbClientMsgHeader, body: GetWebcamsOnlyForModeratorRespMsgBody) extends BbbCoreMsg
case class GetWebcamsOnlyForModeratorRespMsgBody(webcamsOnlyForModerator: Boolean, requestedBy: String)

/**
 * Sent to all users about webcam only for moderator value.
 */
object WebcamsOnlyForModeratorChangedEvtMsg { val NAME = "WebcamsOnlyForModeratorChangedEvtMsg" }
case class WebcamsOnlyForModeratorChangedEvtMsg(header: BbbClientMsgHeader, body: WebcamsOnlyForModeratorChangedEvtMsgBody) extends BbbCoreMsg
case class WebcamsOnlyForModeratorChangedEvtMsgBody(webcamsOnlyForModerator: Boolean, setBy: String)

/**
 * Sent by user to get status of screenshare (meant for late joiners).
 */
object GetScreenshareStatusReqMsg { val NAME = "GetScreenshareStatusReqMsg" }
case class GetScreenshareStatusReqMsg(header: BbbClientMsgHeader, body: GetScreenshareStatusReqMsgBody) extends StandardMsg
case class GetScreenshareStatusReqMsgBody(requestedBy: String)

/**
 * Sent from client about a user changing emoji.
 */
object ChangeUserEmojiCmdMsg { val NAME = "ChangeUserEmojiCmdMsg" }
case class ChangeUserEmojiCmdMsg(header: BbbClientMsgHeader, body: ChangeUserEmojiCmdMsgBody) extends StandardMsg
case class ChangeUserEmojiCmdMsgBody(userId: String, emoji: String)

/**
 * Sent to all clients about a user changing emoji.
 */
object UserEmojiChangedEvtMsg { val NAME = "UserEmojiChangedEvtMsg" }
case class UserEmojiChangedEvtMsg(header: BbbClientMsgHeader, body: UserEmojiChangedEvtMsgBody) extends BbbCoreMsg
case class UserEmojiChangedEvtMsgBody(userId: String, emoji: String)

/**
 * Sent from client about a user changing RaiseHand.
 */
object ChangeUserRaiseHandReqMsg { val NAME = "ChangeUserRaiseHandReqMsg" }
case class ChangeUserRaiseHandReqMsg(header: BbbClientMsgHeader, body: ChangeUserRaiseHandReqMsgBody) extends StandardMsg
case class ChangeUserRaiseHandReqMsgBody(userId: String, raiseHand: Boolean)

/**
 * Sent to all clients about a user changing RaiseHand.
 */
object UserRaiseHandChangedEvtMsg { val NAME = "UserRaiseHandChangedEvtMsg" }
case class UserRaiseHandChangedEvtMsg(header: BbbClientMsgHeader, body: UserRaiseHandChangedEvtMsgBody) extends BbbCoreMsg
case class UserRaiseHandChangedEvtMsgBody(userId: String, raiseHand: Boolean)

/**
 * Sent from client about a user changing Away.
 */
object ChangeUserAwayReqMsg { val NAME = "ChangeUserAwayReqMsg" }
case class ChangeUserAwayReqMsg(header: BbbClientMsgHeader, body: ChangeUserAwayReqMsgBody) extends StandardMsg
case class ChangeUserAwayReqMsgBody(userId: String, away: Boolean)

/**
 * Sent to all clients about a user changing Away.
 */
object UserAwayChangedEvtMsg { val NAME = "UserAwayChangedEvtMsg" }
case class UserAwayChangedEvtMsg(header: BbbClientMsgHeader, body: UserAwayChangedEvtMsgBody) extends BbbCoreMsg
case class UserAwayChangedEvtMsgBody(userId: String, away: Boolean)

/**
 * Sent from client about a user changing ReactionEmoji.
 */
object ChangeUserReactionEmojiReqMsg { val NAME = "ChangeUserReactionEmojiReqMsg" }
case class ChangeUserReactionEmojiReqMsg(header: BbbClientMsgHeader, body: ChangeUserReactionEmojiReqMsgBody) extends StandardMsg
case class ChangeUserReactionEmojiReqMsgBody(userId: String, reactionEmoji: String)

/**
 * Sent to all clients about a user changing ReactionEmoji.
 */
object UserReactionEmojiChangedEvtMsg { val NAME = "UserReactionEmojiChangedEvtMsg" }
case class UserReactionEmojiChangedEvtMsg(header: BbbClientMsgHeader, body: UserReactionEmojiChangedEvtMsgBody) extends BbbCoreMsg
case class UserReactionEmojiChangedEvtMsgBody(userId: String, reactionEmoji: String)

/**
 * Sent from meteor about a user reaction's expiration.
 */
object UserReactionTimeExpiredCmdMsg { val NAME = "UserReactionTimeExpiredCmdMsg" }
case class UserReactionTimeExpiredCmdMsg(header: BbbClientMsgHeader, body: UserReactionTimeExpiredCmdMsgBody) extends StandardMsg
case class UserReactionTimeExpiredCmdMsgBody(userId: String)

/**
 * Sent from client about a mod clearing all users' emoji.
 */
object ClearAllUsersEmojiCmdMsg { val NAME = "ClearAllUsersEmojiCmdMsg" }
case class ClearAllUsersEmojiCmdMsg(header: BbbClientMsgHeader, body: ClearAllUsersEmojiCmdMsgBody) extends StandardMsg
case class ClearAllUsersEmojiCmdMsgBody(userId: String)

/**
 * Sent to all clients about clearing all users' emoji.
 */
object ClearedAllUsersEmojiEvtMsg { val NAME = "ClearedAllUsersEmojiEvtMsg" }
case class ClearedAllUsersEmojiEvtMsg(header: BbbClientMsgHeader, body: ClearedAllUsersEmojiEvtMsgBody) extends StandardMsg
case class ClearedAllUsersEmojiEvtMsgBody()

/**
 * Sent from client about a mod clearing all users' Reaction.
 */
object ClearAllUsersReactionCmdMsg { val NAME = "ClearAllUsersReactionCmdMsg" }
case class ClearAllUsersReactionCmdMsg(header: BbbClientMsgHeader, body: ClearAllUsersReactionCmdMsgBody) extends StandardMsg
case class ClearAllUsersReactionCmdMsgBody(userId: String)

/**
 * Sent to all clients about clearing all users' Reaction.
 */
object ClearedAllUsersReactionEvtMsg { val NAME = "ClearedAllUsersReactionEvtMsg" }
case class ClearedAllUsersReactionEvtMsg(header: BbbClientMsgHeader, body: ClearedAllUsersReactionEvtMsgBody) extends StandardMsg
case class ClearedAllUsersReactionEvtMsgBody()

/**
 * Sent from client about a user mobile flag.
 */
object ChangeUserMobileFlagReqMsg { val NAME = "ChangeUserMobileFlagReqMsg" }
case class ChangeUserMobileFlagReqMsg(header: BbbClientMsgHeader, body: ChangeUserMobileFlagReqMsgBody) extends StandardMsg
case class ChangeUserMobileFlagReqMsgBody(userId: String, mobile: Boolean)

/**
 * Sent to all clients about a user mobile flag.
 */
object UserMobileFlagChangedEvtMsg { val NAME = "UserMobileFlagChangedEvtMsg" }
case class UserMobileFlagChangedEvtMsg(header: BbbClientMsgHeader, body: UserMobileFlagChangedEvtMsgBody) extends BbbCoreMsg
case class UserMobileFlagChangedEvtMsgBody(userId: String, mobile: Boolean)

object AssignPresenterReqMsg { val NAME = "AssignPresenterReqMsg" }
case class AssignPresenterReqMsg(header: BbbClientMsgHeader, body: AssignPresenterReqMsgBody) extends StandardMsg
case class AssignPresenterReqMsgBody(requesterId: String, newPresenterId: String, newPresenterName: String, assignedBy: String)

/**
 * Sent from client to change the video pin of the user in the meeting.
 */
object ChangeUserPinStateReqMsg { val NAME = "ChangeUserPinStateReqMsg" }
case class ChangeUserPinStateReqMsg(header: BbbClientMsgHeader, body: ChangeUserPinStateReqMsgBody) extends StandardMsg
case class ChangeUserPinStateReqMsgBody(userId: String, pin: Boolean, changedBy: String)

object UserPinStateChangedEvtMsg { val NAME = "UserPinStateChangedEvtMsg" }
case class UserPinStateChangedEvtMsg(header: BbbClientMsgHeader, body: UserPinStateChangedEvtMsgBody) extends BbbCoreMsg
case class UserPinStateChangedEvtMsgBody(userId: String, pin: Boolean, changedBy: String)
/**
 * Sent from client to change the role of the user in the meeting.
 */
object ChangeUserRoleCmdMsg { val NAME = "ChangeUserRoleCmdMsg" }
case class ChangeUserRoleCmdMsg(header: BbbClientMsgHeader, body: ChangeUserRoleCmdMsgBody) extends StandardMsg
case class ChangeUserRoleCmdMsgBody(userId: String, role: String, changedBy: String)

object UserRoleChangedEvtMsg { val NAME = "UserRoleChangedEvtMsg" }
case class UserRoleChangedEvtMsg(header: BbbClientMsgHeader, body: UserRoleChangedEvtMsgBody) extends BbbCoreMsg
case class UserRoleChangedEvtMsgBody(userId: String, role: String, changedBy: String)

/**
 * Sent from client to eject the user from the meeting.
 */
object EjectUserFromMeetingCmdMsg { val NAME = "EjectUserFromMeetingCmdMsg" }
case class EjectUserFromMeetingCmdMsg(header: BbbClientMsgHeader, body: EjectUserFromMeetingCmdMsgBody) extends StandardMsg
case class EjectUserFromMeetingCmdMsgBody(userId: String, ejectedBy: String, banUser: Boolean)

/**
 * Sent from client to lock user in meeting.
 */
object LockUserInMeetingCmdMsg { val NAME = "LockUserInMeetingCmdMsg" }
case class LockUserInMeetingCmdMsg(header: BbbClientMsgHeader, body: LockUserInMeetingCmdMsgBody) extends StandardMsg
case class LockUserInMeetingCmdMsgBody(userId: String, lock: Boolean, lockedBy: String)

/**
 * Send to client that user has been locked.
 */
object UserLockedInMeetingEvtMsg { val NAME = "UserLockedInMeetingEvtMsg" }
case class UserLockedInMeetingEvtMsg(header: BbbClientMsgHeader, body: UserLockedInMeetingEvtMsgBody) extends BbbCoreMsg
case class UserLockedInMeetingEvtMsgBody(userId: String, locked: Boolean, lockedBy: String)

/**
 * Sent by client to lock users.
 */
object LockUsersInMeetingCmdMsg { val NAME = "LockUsersInMeetingCmdMsg" }
case class LockUsersInMeetingCmdMsg(header: BbbClientMsgHeader, body: LockUsersInMeetingCmdMsgBody) extends StandardMsg
case class LockUsersInMeetingCmdMsgBody(lock: Boolean, lockedBy: String, except: Vector[String])

/**
 * Sent by client to set lock setting.
 */
object ChangeLockSettingsInMeetingCmdMsg { val NAME = "ChangeLockSettingsInMeetingCmdMsg" }
case class ChangeLockSettingsInMeetingCmdMsg(
    header: BbbClientMsgHeader,
    body:   ChangeLockSettingsInMeetingCmdMsgBody
) extends StandardMsg
case class ChangeLockSettingsInMeetingCmdMsgBody(disableCam: Boolean, disableMic: Boolean, disablePrivChat: Boolean,
                                                 disablePubChat: Boolean, disableNotes: Boolean, hideUserList: Boolean, lockOnJoin: Boolean,
                                                 lockOnJoinConfigurable: Boolean, hideViewersCursor: Boolean, hideViewersAnnotation: Boolean, setBy: String)

object LockSettingsInMeetingChangedEvtMsg { val NAME = "LockSettingsInMeetingChangedEvtMsg" }
case class LockSettingsInMeetingChangedEvtMsg(
    header: BbbClientMsgHeader,
    body:   LockSettingsInMeetingChangedEvtMsgBody
) extends BbbCoreMsg
case class LockSettingsInMeetingChangedEvtMsgBody(disableCam: Boolean, disableMic: Boolean, disablePrivChat: Boolean,
                                                  disablePubChat: Boolean, disableNotes: Boolean, hideUserList: Boolean, lockOnJoin: Boolean,
                                                  lockOnJoinConfigurable: Boolean, hideViewersCursor: Boolean, hideViewersAnnotation: Boolean, setBy: String)

/**
 * Sent by client to query the lock settings.
 */
object GetLockSettingsReqMsg { val NAME = "GetLockSettingsReqMsg" }
case class GetLockSettingsReqMsg(header: BbbClientMsgHeader, body: GetLockSettingsReqMsgBody) extends StandardMsg
case class GetLockSettingsReqMsgBody(requesterId: String)

/**
 * Response to the query for lock settings.
 */
object GetLockSettingsRespMsg { val NAME = "GetLockSettingsRespMsg" }
case class GetLockSettingsRespMsg(header: BbbClientMsgHeader, body: GetLockSettingsRespMsgBody) extends BbbCoreMsg
case class GetLockSettingsRespMsgBody(disableCam: Boolean, disableMic: Boolean, disablePrivChat: Boolean,
                                      disablePubChat: Boolean, disableNotes: Boolean, hideUserList: Boolean, lockOnJoin: Boolean,
                                      lockOnJoinConfigurable: Boolean, hideViewersCursor: Boolean, hideViewersAnnotation: Boolean)

object LockSettingsNotInitializedRespMsg { val NAME = "LockSettingsNotInitializedRespMsg" }
case class LockSettingsNotInitializedRespMsg(header: BbbClientMsgHeader, body: LockSettingsNotInitializedRespMsgBody) extends BbbCoreMsg
case class LockSettingsNotInitializedRespMsgBody(userId: String)

/**
 * Sent from client to logout and end meeting.
 */
object LogoutAndEndMeetingCmdMsg { val NAME = "LogoutAndEndMeetingCmdMsg" }
case class LogoutAndEndMeetingCmdMsg(header: BbbClientMsgHeader, body: LogoutAndEndMeetingCmdMsgBody) extends StandardMsg
case class LogoutAndEndMeetingCmdMsgBody(userId: String)

object UserJoinMeetingReqMsg { val NAME = "UserJoinMeetingReqMsg" }
case class UserJoinMeetingReqMsg(header: BbbClientMsgHeader, body: UserJoinMeetingReqMsgBody) extends StandardMsg
case class UserJoinMeetingReqMsgBody(userId: String, authToken: String, clientType: String)

/**
 * Sent from Flash client to rejoin meeting after reconnection
 */
object UserJoinMeetingAfterReconnectReqMsg { val NAME = "UserJoinMeetingAfterReconnectReqMsg" }
case class UserJoinMeetingAfterReconnectReqMsg(header: BbbClientMsgHeader, body: UserJoinMeetingAfterReconnectReqMsgBody) extends StandardMsg
case class UserJoinMeetingAfterReconnectReqMsgBody(userId: String, authToken: String, clientType: String)

/**
 * Sent from client to bbb-akka to notify that a user is leaving
 */
object UserLeaveReqMsg { val NAME = "UserLeaveReqMsg" }
case class UserLeaveReqMsg(header: BbbClientMsgHeader, body: UserLeaveReqMsgBody) extends StandardMsg
case class UserLeaveReqMsgBody(userId: String, sessionId: String, loggedOut: Boolean)

object GetUsersMeetingReqMsg { val NAME = "GetUsersMeetingReqMsg" }
case class GetUsersMeetingReqMsg(header: BbbClientMsgHeader, body: GetUsersMeetingReqMsgBody) extends StandardMsg
case class GetUsersMeetingReqMsgBody(userId: String)

object GetUsersMeetingRespMsg {
  val NAME = "GetUsersMeetingRespMsg"

  def apply(meetingId: String, userId: String, users: Vector[WebUser]): GetUsersMeetingRespMsg = {
    val header = BbbClientMsgHeader(GetUsersMeetingRespMsg.NAME, meetingId, userId)

    val body = GetUsersMeetingRespMsgBody(users)
    GetUsersMeetingRespMsg(header, body)
  }

}
case class GetUsersMeetingRespMsg(header: BbbClientMsgHeader, body: GetUsersMeetingRespMsgBody) extends BbbCoreMsg
case class GetUsersMeetingRespMsgBody(users: Vector[WebUser])
case class WebUser(intId: String, extId: String, name: String, role: String,
                   guest: Boolean, authed: Boolean, guestStatus: String,
                   emoji: String, locked: Boolean,
                   presenter: Boolean, avatar: String, clientType: String)

object SyncGetUsersMeetingRespMsg { val NAME = "SyncGetUsersMeetingRespMsg" }
case class SyncGetUsersMeetingRespMsg(header: BbbClientMsgHeader, body: SyncGetUsersMeetingRespMsgBody) extends BbbCoreMsg
case class SyncGetUsersMeetingRespMsgBody(users: Vector[WebUser])

object GetVoiceUsersMeetingRespMsg {
  val NAME = "GetVoiceUsersMeetingRespMsg"

  def apply(meetingId: String, userId: String, users: Vector[VoiceConfUser]): GetVoiceUsersMeetingRespMsg = {
    val header = BbbClientMsgHeader(GetVoiceUsersMeetingRespMsg.NAME, meetingId, userId)

    val body = GetVoiceUsersMeetingRespMsgBody(users)
    GetVoiceUsersMeetingRespMsg(header, body)
  }
}

case class GetVoiceUsersMeetingRespMsg(header: BbbClientMsgHeader, body: GetVoiceUsersMeetingRespMsgBody) extends BbbCoreMsg
case class GetVoiceUsersMeetingRespMsgBody(users: Vector[VoiceConfUser])
case class VoiceConfUser(intId: String, voiceUserId: String, callingWith: String, callerName: String,
                         callerNum: String, color: String, muted: Boolean, talking: Boolean, listenOnly: Boolean)

/**
 * Sent from client to add user to the presenter group of a meeting.
 */
object AddUserToPresenterGroupCmdMsg { val NAME = "AddUserToPresenterGroupCmdMsg" }
case class AddUserToPresenterGroupCmdMsg(header: BbbClientMsgHeader, body: AddUserToPresenterGroupCmdMsgBody) extends StandardMsg
case class AddUserToPresenterGroupCmdMsgBody(userId: String, requesterId: String)

/**
 * Sent to all clients about a user added to the presenter group of a meeting
 */
object UserAddedToPresenterGroupEvtMsg { val NAME = "UserAddedToPresenterGroupEvtMsg" }
case class UserAddedToPresenterGroupEvtMsg(header: BbbClientMsgHeader, body: UserAddedToPresenterGroupEvtMsgBody) extends StandardMsg
case class UserAddedToPresenterGroupEvtMsgBody(userId: String, requesterId: String)

/**
 * Sent from client to remove user from the presenter group of a meeting.
 */
object RemoveUserFromPresenterGroupCmdMsg { val NAME = "RemoveUserFromPresenterGroupCmdMsg" }
case class RemoveUserFromPresenterGroupCmdMsg(header: BbbClientMsgHeader, body: RemoveUserFromPresenterGroupCmdMsgBody) extends StandardMsg
case class RemoveUserFromPresenterGroupCmdMsgBody(userId: String, requesterId: String)

/**
 * Sent to all clients about a user removed from the presenter group of a meeting
 */
object UserRemovedFromPresenterGroupEvtMsg { val NAME = "UserRemovedFromPresenterGroupEvtMsg" }
case class UserRemovedFromPresenterGroupEvtMsg(header: BbbClientMsgHeader, body: UserRemovedFromPresenterGroupEvtMsgBody) extends StandardMsg
case class UserRemovedFromPresenterGroupEvtMsgBody(userId: String, requesterId: String)

/**
 * Sent from client to request the presenter group of a meeting.
 */
object GetPresenterGroupReqMsg { val NAME = "GetPresenterGroupReqMsg" }
case class GetPresenterGroupReqMsg(header: BbbClientMsgHeader, body: GetPresenterGroupReqMsgBody) extends StandardMsg
case class GetPresenterGroupReqMsgBody(requesterId: String)

/**
 * Sent to all clients about the members of the presenter group of a meeting
 */
object GetPresenterGroupRespMsg { val NAME = "GetPresenterGroupRespMsg" }
case class GetPresenterGroupRespMsg(header: BbbClientMsgHeader, body: GetPresenterGroupRespMsgBody) extends StandardMsg
case class GetPresenterGroupRespMsgBody(presenterGroup: Vector[String], requesterId: String)

object UserInactivityInspectMsg { val NAME = "UserInactivityInspectMsg" }
case class UserInactivityInspectMsg(header: BbbClientMsgHeader, body: UserInactivityInspectMsgBody) extends StandardMsg
case class UserInactivityInspectMsgBody(meetingId: String, responseDelay: Long)

object UserActivitySignCmdMsg { val NAME = "UserActivitySignCmdMsg" }
case class UserActivitySignCmdMsg(header: BbbClientMsgHeader, body: UserActivitySignCmdMsgBody) extends StandardMsg
case class UserActivitySignCmdMsgBody(userId: String)

/**
 * Sent from client to randomly select a viewer
 */
object SelectRandomViewerReqMsg { val NAME = "SelectRandomViewerReqMsg" }
case class SelectRandomViewerReqMsg(header: BbbClientMsgHeader, body: SelectRandomViewerReqMsgBody) extends StandardMsg
case class SelectRandomViewerReqMsgBody(requestedBy: String)

/**
 * Response to request for a random viewer
 */
object SelectRandomViewerRespMsg { val NAME = "SelectRandomViewerRespMsg" }
case class SelectRandomViewerRespMsg(header: BbbClientMsgHeader, body: SelectRandomViewerRespMsgBody) extends StandardMsg
case class SelectRandomViewerRespMsgBody(requestedBy: String, userIds: Vector[String], choice: String)

object SetUserSpeechLocaleReqMsg { val NAME = "SetUserSpeechLocaleReqMsg" }
case class SetUserSpeechLocaleReqMsg(header: BbbClientMsgHeader, body: SetUserSpeechLocaleReqMsgBody) extends StandardMsg
case class SetUserSpeechLocaleReqMsgBody(locale: String, provider: String)

object UserSpeechLocaleChangedEvtMsg { val NAME = "UserSpeechLocaleChangedEvtMsg" }
case class UserSpeechLocaleChangedEvtMsg(header: BbbClientMsgHeader, body: UserSpeechLocaleChangedEvtMsgBody) extends BbbCoreMsg
case class UserSpeechLocaleChangedEvtMsgBody(locale: String, provider: String)
