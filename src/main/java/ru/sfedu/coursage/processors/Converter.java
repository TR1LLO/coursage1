package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.ConverterArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of SoundData converting
 */
public class Converter extends SoundDataProcessor {
    ConverterArgs args;
    public Converter (SoundData src, SoundData dst, ConverterArgs args) {
        super(src, null, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
