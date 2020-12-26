package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.CompressorArgs;
import ru.sfedu.coursage.model.DataArray;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of oscillation compression process
 */
public class Compressor extends SoundDataProcessor {
    CompressorArgs args;
    DataArray in, out;
    float power;
    boolean distortion;
    public Compressor(SoundData src, SoundData dst, CompressorArgs args) {
        super(src, null, dst);
        this.args=args;
        in=src.getData();
        out= dst.getData();
        power=args.getPower();
        distortion= args.isDistortion();
    }

    private float operate(int ch, int pos) {
        float v=in.read(ch, pos);
        return distortion?(v>power?power:v<-power?-power:v):(float)Math.pow(v, power);
    }
    @Override
    public SoundData operate() {
        logger.debug("Equalizer operating...");
        for(int ch=0; ch<in.getChannels(); ch++)
        {
            for(int i=0; i<out.getSize(); i++)
                out.write(ch, i, operate(ch, i));
        }
        logger.info("Equalizer operating finished");
        return dst;
    }
}
