import Auth from '/imports/ui/services/auth';
import Storage from '/imports/ui/services/storage/session';
import { makeCall } from '/imports/ui/services/api';
import { indexOf, without } from '/imports/utils/array-utils';
import { throttle } from '/imports/utils/throttle';;

// @ts-ignore - temporary, while meteor exists in the project
const CHAT_CONFIG = Meteor.settings.public.chat;

const PUBLIC_CHAT_ID = CHAT_CONFIG.public_id;
const PUBLIC_GROUP_CHAT_ID = CHAT_CONFIG.public_group_id;
const CHAT_EMPHASIZE_TEXT = CHAT_CONFIG.moderatorChatEmphasized;
const START_TYPING_THROTTLE_INTERVAL = 1000;

// session for closed chat list
const CLOSED_CHAT_LIST_KEY = 'closedChatList';

export const sendGroupMessage = (message: string, idChatOpen: string) => {
  const { userID: senderUserId } = Auth;
  const chatID = idChatOpen === PUBLIC_CHAT_ID ? PUBLIC_GROUP_CHAT_ID : idChatOpen;

  const receiverId = { id: chatID };

  const payload = {
    correlationId: `${senderUserId}-${Date.now()}`,
    sender: {
      id: senderUserId,
      name: '',
      role: '',
    },
    chatEmphasizedText: CHAT_EMPHASIZE_TEXT,
    message,
  };

  const currentClosedChats = Storage.getItem(CLOSED_CHAT_LIST_KEY);

  // Remove the chat that user send messages from the session.
  if (indexOf(currentClosedChats, receiverId.id) > -1) {
    Storage.setItem(CLOSED_CHAT_LIST_KEY, without(currentClosedChats, receiverId.id));
  }

  return makeCall('sendGroupChatMsg', chatID, payload);
};

export const handleSendMessage = (message: string, idChatOpen: string) => {
  return sendGroupMessage(message, idChatOpen);
};

export const startUserTyping = throttle(
  (chatId: string) => {
    makeCall('startUserTyping', chatId);
  },
  START_TYPING_THROTTLE_INTERVAL,
  { leading: true, trailing: false }
);
export const stopUserTyping = () => makeCall('stopUserTyping');

export default {
  sendGroupMessage,
  handleSendMessage,
  startUserTyping,
  stopUserTyping,
};
