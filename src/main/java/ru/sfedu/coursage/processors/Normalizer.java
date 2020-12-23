package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.NormalizerArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of sound normalization process
 */
public class Normalizer extends SoundDataProcessor {
    NormalizerArgs args;
    public Normalizer (SoundData data, NormalizerArgs args) {
        super(data, null);
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
    public static NormalizerArgs parse(String properties) {
        return null;
    }
}
