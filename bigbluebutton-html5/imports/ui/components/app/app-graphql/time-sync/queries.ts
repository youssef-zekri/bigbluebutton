import { gql } from '@apollo/client';

export interface GetServerTimeResponse {
  current_time: Array<{ currentTimestamp: Date }>;
}

export const GET_SERVER_TIME = gql`
  query getServerTime {
    current_time {
      currentTimestamp
    }
  }
`;

export default {
  GET_SERVER_TIME,
};
