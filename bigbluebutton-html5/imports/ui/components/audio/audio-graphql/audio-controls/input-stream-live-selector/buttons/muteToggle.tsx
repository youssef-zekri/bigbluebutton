import React from "react";
import { defineMessages, useIntl } from "react-intl";
import Styled from "../styles";
import { useShortcut } from "/imports/ui/core/hooks/useShortcut";
import Settings from '/imports/ui/services/settings';

// @ts-ignore - temporary while settings are still in .js
const animations = Settings.application.animations;

const intlMessages = defineMessages({
  muteAudio: {
    id: 'app.actionsBar.muteLabel',
    description: 'Mute audio button label',
  },
  unmuteAudio: {
    id: 'app.actionsBar.unmuteLabel',
    description: 'Unmute audio button label',
  },
});

interface MuteToggleProps {
  talking: boolean;
  muted: boolean;
  disabled: boolean;
  isAudioLocked: boolean;
  toggleMuteMicrophone: (muted: boolean) => void;
}

export const Mutetoggle: React.FC<MuteToggleProps> = ({
  talking,
  muted,
  disabled,
  isAudioLocked,
  toggleMuteMicrophone,
}) => {
  const intl = useIntl();
  const toggleMuteShourtcut = useShortcut('toggleMute');

  const label = muted ? intl.formatMessage(intlMessages.unmuteAudio)
    : intl.formatMessage(intlMessages.muteAudio);
  const onClickCallback = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    toggleMuteMicrophone(muted);
  }
  return (<Styled.MuteToggleButton
    onClick={onClickCallback}
    disabled={disabled || isAudioLocked}
    hideLabel
    label={label}
    aria-label={label}
    color={!muted ? 'primary' : 'default'}
    ghost={muted}
    icon={muted ? 'mute' : 'unmute'}
    size="lg"
    circle
    accessKey={toggleMuteShourtcut}
    $talking={talking || undefined}
    animations={animations}
    data-test={muted ? 'unmuteMicButton' : 'muteMicButton'}
  />)
};

export default Mutetoggle;