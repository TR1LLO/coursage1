package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.NormalizerArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of sound normalization process
 */
public class Normalizer extends SoundDataProcessor {
    NormalizerArgs args;
    public Normalizer (SoundData src, SoundData dst, NormalizerArgs args) {
        super(src, null, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
