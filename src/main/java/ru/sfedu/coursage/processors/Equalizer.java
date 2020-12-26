package ru.sfedu.coursage.processors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.model.DataArray;
import ru.sfedu.coursage.model.EqualizerArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of sound equalization process
 */
public class Equalizer extends SoundDataProcessor {
    EqualizerArgs args;
    private int count, size, channels;
    private float[][] buffer;

    private float[] amps;
    DataArray in, out;
    public Equalizer (SoundData src, SoundData dst, EqualizerArgs args) {
        super(src, null, dst);
        this.args=args;

        in=src.getData();
        out= dst.getData();
        amps= args.getAmps();

        channels=src.getChannels();
        count= amps.length;
        size=2<<count;
        buffer = new float[count][size];
    }
    private void memset() {
        for(int i=0; i<count; i++)
            for(int k=0; k<size; k++)
                buffer[i][k]=0.0f;
    }

    private float operate(int ch, int pos) {
        int tpos=pos%size, d=2;
        buffer[0][tpos]=(in.read(ch, pos-1)+in.read(ch, pos))/2;
        for(int i=1; i<count; i++, d*=2)
            buffer[i][tpos]=(buffer[i-1][(size+tpos-d)%size]+buffer[i-1][tpos])/2;

        d=2;
        float res=amps[count - 1]*(buffer[0][(size+tpos-1)%size]-in.read(ch, pos));
        for(int i=1; i<count; i++, d*=2)
            res+=amps[count - 1 - i]*(buffer[i][(size+tpos-d)%size]-buffer[i-1][(size+tpos)%size]);
        return res;
    }
    @Override
    public SoundData operate() {
        logger.debug("Equalizer operating...");
        for(int ch=0; ch<channels; ch++)
        {
            memset();
            for(int i=0; i<out.getSize(); i++)
                out.write(ch, i, operate(ch, i));
        }
        logger.info("Equalizer operating finished");
        return dst;
    }

    public static Logger logger = LogManager.getLogger();
    private static final String AMPLITUDE_PROPERTY="amps";
    /**
     * parse string properties into ArgumentPack
     * @param properties string with all required properties
     * @return ArgumentPack with error code
     */
    public static EqualizerArgs parse(String properties) {
        logger.info("EqualizerArgs parsing...");
        String temp;
        EqualizerArgs res=new EqualizerArgs();

        float[] amps=new float[res.getCount()];
        for(int i=0; i< res.getCount(); i++)
        {
            temp=getProperty(properties, AMPLITUDE_PROPERTY+"["+i+"]");
            if(temp!=null)
                amps[i]=Float.valueOf(temp);
            logger.debug("EqualizerArgs parsing: amps parsed");
        }
        res.setAmps(amps);
        logger.info("EqualizerArgs parsed");
        return res;
    }
}
