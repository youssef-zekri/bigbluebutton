import { ApolloClient, ApolloProvider, InMemoryCache } from '@apollo/client';
 import { WebSocketLink } from "@apollo/client/link/ws";
 import React, {useEffect, useState} from "react";
import './App.css';
import { SubscriptionClient } from 'subscriptions-transport-ws';
import UserList from './UserList';
import MeetingInfo from './MeetingInfo';
import TotalOfUsers from './TotalOfUsers';
import TotalOfModerators from './TotalOfModerators';
import TotalOfViewers from './TotalOfViewers';
import TotalOfUsersTalking from './TotalOfUsersTalking';
import TotalOfUniqueNames from './TotalOfUniqueNames';
import ChatMessages from "./ChatMessages";
import ChatsInfo from "./ChatsInfo";
import ChatPublicMessages from "./ChatPublicMessages";
import Annotations from "./Annotations";
import AnnotationsHistory from "./AnnotationsHistory";
import CursorsStream from "./CursorsStream";
import CursorsAll from "./CursorsAll";
import TalkingStream from "./TalkingStream";
import MyInfo from "./MyInfo";
import UserLocalSettings from "./UserLocalSettings";
import UserConnectionStatus from "./UserConnectionStatus";
import UserConnectionStatusReport from "./UserConnectionStatusReport";


function App() {
  const [sessionToken, setSessionToken] = useState(null);
  const [userId, setUserId] = useState(null);
  const [userName, setUserName] = useState(null);
  const [graphqlClient, setGraphqlClient] = useState(null);
  const [enterApiResponse, setEnterApiResponse] = useState('');

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  async function callApiEnter(sessionToken) {
    // get url from input text box
    fetch('/bigbluebutton/api/enter/?sessionToken=' + sessionToken, { credentials: 'include' })
        .then((response) => response.json())
        .then((json) => {
          console.log(json.response);
          setEnterApiResponse(json.response.returncode);
          if(json?.response?.internalUserID) {
            setUserId(json.response.internalUserID);
            setUserName(json.response.fullname);
          }
        });
  }

    useEffect(() => {
        const fetchApiData = async () => {
            if (urlParams.has('sessionToken')) {
                const sessionTokenFromUrl = urlParams.get('sessionToken');

                if (sessionTokenFromUrl !== null) {
                    await callApiEnter(sessionTokenFromUrl);
                    setSessionToken(sessionTokenFromUrl);
                }
            }
        };
        fetchApiData();
    },[]);

  async function connectGraphqlServer(sessionToken) {
    // setSessionToken(sessionToken);
    const wsLink = new WebSocketLink(
      new SubscriptionClient(`wss://${window.location.hostname}/v1/graphql`, {
        reconnect: true,
        timeout: 30000,
        connectionParams: {
          headers: {
            'X-Session-Token': sessionToken,
            'json-patch-supported': 'true'
          }
        }
      })
    );

    setGraphqlClient(new ApolloClient({link: wsLink, cache: new InMemoryCache()}));
  }

    useEffect(() => {
        if(enterApiResponse === 'SUCCESS' && !graphqlClient) {
            console.log(`Creating graphql socket with token ${sessionToken}`);
            const fetchData = async () => {
                await connectGraphqlServer(sessionToken);
            }
            fetchData();
        } else if(enterApiResponse === 'FAILED') {
            console.log('Error on enter API call: ' + enterApiResponse.message);
            console.log(enterApiResponse);
        }
    },[sessionToken, enterApiResponse]);


  return (
    <div className="App">
        {graphqlClient ? (
          <div
            style={{
              height: '100vh',
            }}
          >
          <ApolloProvider client={graphqlClient}>
            Who am I? {userName} ({userId})
            <MeetingInfo />
            <br />
            <MyInfo />
            <br />
            <UserConnectionStatus />
            <br />
            <UserConnectionStatusReport />
            <br />
            <UserLocalSettings userId={userId} />
            <br />
            <UserList userId={userId} />
            <br />
            <ChatsInfo />
            <br />
            <ChatMessages userId={userId} />
            <br />
            <ChatPublicMessages userId={userId} />
            <br />
            <CursorsAll />
            <br />
            <TalkingStream />
            <br />
            <CursorsStream />
            <br />
            <Annotations />
            <br />
            <AnnotationsHistory />
            <br />
            <TotalOfUsers />
            <TotalOfModerators />
            <TotalOfViewers />
            <TotalOfUsersTalking />
            <TotalOfUniqueNames />
        </ApolloProvider>
        </div>
         ) : sessionToken == null ? 'Param sessionToken missing' : 'Loading...'}
    </div>
  );
}

export default App;
