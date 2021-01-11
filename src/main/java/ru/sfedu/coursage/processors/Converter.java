package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.ConverterArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of SoundData converting
 */
public class Converter extends SoundDataProcessor {
    ConverterArgs args;
    public Converter (SoundData src, SoundData filter, SoundData dst, ConverterArgs args) {
        super(src, filter, dst);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }
}
