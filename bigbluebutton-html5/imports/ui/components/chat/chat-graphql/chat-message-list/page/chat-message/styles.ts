import styled from 'styled-components';

import {
  borderSize,
} from '/imports/ui/stylesheets/styled-components/general';
import {
  lineHeightComputed,
  fontSizeBase,
} from '/imports/ui/stylesheets/styled-components/typography';

export const ChatWrapper = styled.div`
  pointer-events: auto;
  [dir='rtl'] & {
    direction: rtl;
  }
  display: flex;
  flex-flow: column;
  position: relative;
  ${({ sameSender }) =>
    sameSender &&
    `
    flex: 1;
    margin: ${borderSize} 0 0 ${borderSize};
    margin-top: calc(${lineHeightComputed} / 3);
  `}
  ${({ sameSender }) =>
    !sameSender &&
    `
    padding-top:${lineHeightComputed};
  `}
  [dir="rtl"] & {
    margin: ${borderSize} ${borderSize} 0 0;
  }
  font-size: ${fontSizeBase};
`;

export const ChatContent = styled.div`
  display: flex;
  flex-flow: column;
  width: 100%;
`;
