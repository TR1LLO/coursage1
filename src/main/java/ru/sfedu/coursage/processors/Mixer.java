package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.MixerArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of sound mixing process
 */
public class Mixer extends SoundDataProcessor {
    MixerArgs args;
    public Mixer (SoundData src, SoundData filter, MixerArgs args) {
        super(src, filter);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }

    /**
     * parse string properties into ArgumentPack
     * @param properties string with all required properties
     * @return ArgumentPack with error code
     */
    public static MixerArgs parse(String properties) {
      return null;
    }
}
