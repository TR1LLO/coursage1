package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.CompressorArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of oscillation compression process
 */
public class Compressor extends SoundDataProcessor {
    CompressorArgs args;
    public Compressor(SoundData src, CompressorArgs args) {
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
    public static CompressorArgs parse(String properties) {
    return null;
  }
}
