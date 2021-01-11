package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.ShifterArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of spectral shift sound effect
 */
public class Shifter extends SoundDataProcessor {
    private ShifterArgs args;
    public Shifter(SoundData src, SoundData filter, SoundData dst, ShifterArgs args) {
        super(src, filter, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
