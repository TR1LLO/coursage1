package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.ConverterArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of SoundData converting
 */
public class Converter extends SoundDataProcessor {
    ConverterArgs args;
    public Converter (SoundData src, ConverterArgs args) {
        super(src, null);
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
    public static ConverterArgs parse(String properties) {
        return null;
    }
}
