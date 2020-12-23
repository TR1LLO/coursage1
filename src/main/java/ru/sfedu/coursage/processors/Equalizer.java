package ru.sfedu.coursage.processors;
import ru.sfedu.coursage.model.EqualizerArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of sound equalization process
 */
public class Equalizer extends SoundDataProcessor {
    EqualizerArgs args;
    public Equalizer (SoundData src, EqualizerArgs args) {
        super(src, null);
        this.args=args;
    }

    @Override
    public SoundData operate() {

        return null;
    }

    private static final String AMPLITUDE_PROPERTY="amps";
    /**
     * parse string properties into ArgumentPack
     * @param properties string with all required properties
     * @return ArgumentPack with error code
     */
    public static EqualizerArgs parse(String properties) {
        String temp;
        EqualizerArgs res=new EqualizerArgs();

        float[] amps=new float[res.getCount()];
        for(int i=0; i< res.getCount(); i++)
        {
            temp=getProperty(properties, AMPLITUDE_PROPERTY+"["+i+"]");
            if(temp!=null)
                amps[i]=Float.valueOf(temp);
        }
        res.setAmplitudes(amps);
        return res;
    }
}
