import { gql } from '@apollo/client';
import { Message } from '/imports/ui/Types/message';

export interface ChatMessagePublicSubscriptionResponse {
  chat_message_public: Array<Message>;
}

export interface ChatMessagePrivateSubscriptionResponse {
  chat_message_private: Array<Message>;
}

export const CHAT_MESSAGE_PUBLIC_SUBSCRIPTION = gql`
  subscription chatMessages($limit: Int!, $offset: Int!) {
    chat_message_public(limit: $limit, offset: $offset, order_by: { createdTime: asc }) {
      user {
        name
        userId
        avatar
        isOnline
        isModerator
        color
      }
      messageType
      chatId
      message
      messageId
      createdTime
      createdTimeAsDate
      messageMetadata
    }
  }
`;

export const CHAT_MESSAGE_PRIVATE_SUBSCRIPTION = gql`
  subscription chatMessages($limit: Int!, $offset: Int!, $requestedChatId: String!) {
    chat_message_private(
      limit: $limit
      offset: $offset
      where: { chatId: { _eq: $requestedChatId } }
      order_by: { createdTime: asc }
    ) {
      user {
        name
        userId
        avatar
        isOnline
        isModerator
        color
      }
      chatId
      message
      messageType
      messageId
      createdTime
      createdTimeAsDate
      messageMetadata
    }
  }
`;
