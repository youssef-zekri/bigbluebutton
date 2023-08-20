package org.bigbluebutton.core2.testdata

import org.bigbluebutton.core.models._
import org.bigbluebutton.core.running.LiveMeeting
import org.bigbluebutton.core.util.RandomStringGenerator

object TestDataGen {
  def createRegisteredUser(users: RegisteredUsers, name: String, role: String,
                           guest: Boolean, authed: Boolean, waitForApproval: Boolean): RegisteredUser = {
    val id = "w_" + RandomStringGenerator.randomAlphanumericString(16)
    val extId = RandomStringGenerator.randomAlphanumericString(16)
    val authToken = RandomStringGenerator.randomAlphanumericString(16)
    val sessionToken = RandomStringGenerator.randomAlphanumericString(16)
    val avatarURL = "https://www." + RandomStringGenerator.randomAlphanumericString(32) + ".com/" +
      RandomStringGenerator.randomAlphanumericString(10) + ".png"

    val ru = RegisteredUsers.create(userId = id, extId, name, role,
      authToken, sessionToken, avatarURL, guest, authed, GuestStatus.ALLOW, false)

    RegisteredUsers.add(users, ru, meetingId = "test")
    ru
  }

  def createVoiceUserForUser(user: RegisteredUser, callingWith: String, muted: Boolean, talking: Boolean,
                             listenOnly: Boolean): VoiceUserState = {
    val voiceUserId = RandomStringGenerator.randomAlphanumericString(8)
    VoiceUserState(intId = user.id, voiceUserId = voiceUserId, callingWith, callerName = user.name,
      callerNum = user.name, "#ff6242", muted, talking, listenOnly,
      false,
      "9b3f4504-275d-4315-9922-21174262d88c")
  }

  def createFakeVoiceOnlyUser(callingWith: String, muted: Boolean, talking: Boolean,
                              listenOnly: Boolean, name: String): VoiceUserState = {
    val voiceUserId = RandomStringGenerator.randomAlphanumericString(8)
    val intId = "v_" + RandomStringGenerator.randomAlphanumericString(16)
    VoiceUserState(intId, voiceUserId = voiceUserId, callingWith, callerName = name,
      callerNum = name, "#ff6242", muted, talking, listenOnly
      false,
      "9b3f4504-275d-4315-9922-21174262d88c")
  }

  def createFakeWebcamStreamFor(userId: String, subscribers: Set[String]): WebcamStream = {
    val streamId = RandomStringGenerator.randomAlphanumericString(10)
    WebcamStream(streamId, userId, subscribers)
  }

  def createUserFor(liveMeeting: LiveMeeting, regUser: RegisteredUser, presenter: Boolean): UserState = {
    val u = UserState(intId = regUser.id, extId = regUser.externId, name = regUser.name, role = regUser.role,
      guest = regUser.guest, authed = regUser.authed, guestStatus = regUser.guestStatus,
      emoji = "none", reactionEmoji = "none", raiseHand = false, away = false, pin = false, mobile = false,
      locked = false, presenter = false, avatar = regUser.avatarURL, color = "#ff6242",
      clientType = "unknown", pickExempted = false, userLeftFlag = UserLeftFlag(false, 0))
    Users2x.add(liveMeeting.users2x, u)
    u
  }
}
