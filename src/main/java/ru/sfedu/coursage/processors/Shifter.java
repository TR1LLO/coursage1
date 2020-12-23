package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.ShifterArgs;
import ru.sfedu.coursage.model.SoundData;

/**
 * class-implementer of spectral shift sound effect
 */
public class Shifter extends SoundDataProcessor {
    private ShifterArgs args;
    public Shifter(SoundData src, ShifterArgs args) {
        super(src, null);
        this.args=args;
    }

    @Override
    public SoundData operate() {
        return null;
    }


    private static final String FREQUENCY_PROPERTY = "freq";
    private static final String RADIUS_PROPERTY = "radius";
    private static final String STEP_PROPERTY = "step";

    /**
     * parse string properties into ArgumentPack
     * @param properties string with all required properties
     * @return ArgumentPack with error code
     */
    public static ShifterArgs parse(String properties) {
        String temp;
        ShifterArgs args = new ShifterArgs();

        temp=getProperty(properties, FREQUENCY_PROPERTY);
        if(temp!=null)
            args.setFrequency(Float.valueOf(temp));
        else args.setErrorCode(ArgumentPack.ErrorCode.PARSING_FAILED);

        temp=getProperty(properties, RADIUS_PROPERTY);
        if(temp!=null)
            args.setRadius(Integer.valueOf(temp));

        temp=getProperty(properties, STEP_PROPERTY);
        if(temp!=null)
            args.setStep(Float.valueOf(temp));
        return args;
    }


}
