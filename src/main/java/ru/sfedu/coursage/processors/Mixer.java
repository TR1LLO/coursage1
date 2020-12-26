package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.MixerArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of sound mixing process
 */
public class Mixer extends SoundDataProcessor {
    MixerArgs args;
    public Mixer (SoundData src, SoundData filter, SoundData dst, MixerArgs args) {
        super(src, filter, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
