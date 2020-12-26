package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.ShifterArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of spectral shift sound effect
 */
public class Shifter extends SoundDataProcessor {
    private ShifterArgs args;
    public Shifter(SoundData src, SoundData dst, ShifterArgs args) {
        super(src, null, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
