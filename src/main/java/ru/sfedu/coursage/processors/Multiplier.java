package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.MultiplierArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of multiplication process
 */
public class Multiplier extends SoundDataProcessor {
    MultiplierArgs args;
    public Multiplier(SoundData src, SoundData filter, MultiplierArgs args) {
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
    public static MultiplierArgs parse(String properties) {
        return null;
    }
}
