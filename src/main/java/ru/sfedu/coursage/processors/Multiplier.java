package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.MultiplierArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of multiplication process
 */
public class Multiplier extends SoundDataProcessor {
    MultiplierArgs args;
    public Multiplier(SoundData src, SoundData filter, SoundData dst, MultiplierArgs args) {
        super(src, filter, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
