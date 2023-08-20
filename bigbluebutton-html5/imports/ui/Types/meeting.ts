export interface LockSettings {
  disableCam: boolean;
  disableMic: boolean;
  disableNotes: boolean;
  disablePrivateChat: boolean;
  disablePublicChat: boolean;
  hasActiveLockSetting: boolean;
  hideUserList: boolean;
  hideViewersCursor: boolean;
  meetingId: boolean;
  webcamsOnlyForModerator: boolean;
}

export interface WelcomeSettings {
  welcomeMsg: string;
  modOnlyMessage: string;
  welcomeMsgTemplate: string;
  meetingId: string;
}

export interface MeetingRecording {
  isRecording: boolean;
  startedAt: Date;
  previousRecordedTimeInSeconds: number;
  startedBy: string;
  stoppedAt: number;
  stoppedBy: string;
}
export interface MeetingRecordingPolicies {
  allowStartStopRecording: boolean;
  autoStartRecording: boolean;
  record: boolean;
  keepEvents: boolean;
  startedAt: number;
  startedBy: string;
  stoppedAt: number;
  stoppedBy: string;
}


export interface UsersPolicies {
  allowModsToEjectCameras: boolean;
  allowModsToUnmuteUsers: boolean;
  authenticatedGuest: boolean;
  guestPolicy: string;
  maxUserConcurrentAccesses: number;
  maxUsers: number;
  meetingId: string;
  meetingLayout: string;
  userCameraCap: number;
  webcamsOnlyForModerator: boolean;
}

export interface VoiceSettings {
  dialNumber: string;
  meetingId: string;
  muteOnStart: boolean;
  telVoice: string;
  voiceConf: string;
}

export interface BreakoutPolicies {
  breakoutRooms: Array<unknown>;
  captureNotes: string;
  captureNotesFilename: string;
  captureSlides: string;
  captureSlidesFilename: string;
  freeJoin: boolean;
  meetingId: string;
  parentId: string;
  privateChatEnabled: boolean;
  record: boolean;
  sequence: number;
}

export interface Meeting {
  createdTime: number;
  disabledFeatures: Array<string>;
  duration: number;
  extId: string;
  html5InstanceId: string | null;
  isBreakout: boolean;
  learningDashboardAccessToken: string;
  maxPinnedCameras: number;
  meetingCameraCap: number;
  meetingId: string;
  name: string;
  notifyRecordingIsOn: boolean;
  presentationUploadExternalDescription: string;
  presentationUploadExternalUrl: string;
  usersPolicies: UsersPolicies;
  lockSettings: LockSettings;
  voiceSettings: VoiceSettings;
  breakoutPolicies: BreakoutPolicies;
}
