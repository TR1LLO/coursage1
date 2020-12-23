package ru.sfedu.coursage.processors;
import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.model.ArgumentPack.ErrorCode;
import ru.sfedu.coursage.model.dataProviders.AbstractDataProvider;

/**
 * generalizing class-dispatcher of SoundProcessors and their methods
 */
public class MainProcessor {
    MainProcessorArgs args;
    public MainProcessor (MainProcessorArgs args) {
        this.args=args;
    }

    /**
     * run processing
     * @return SoundData with processed content
     */
    public SoundData operate() {
        ArgumentPack pack = args.getProcessorArgs();
        SoundDataProcessor processor;
        switch (pack.getProcessorId()) {
            case CONVERTER:     processor=new Converter(args.getSrc(), (ConverterArgs)pack);  break;
            case SHIFTER:       processor=new Shifter(args.getSrc(),   (ShifterArgs)pack);    break;
            case NORMALIZER:    processor=new Normalizer(args.getSrc(),(NormalizerArgs)pack); break;
            case EQUALIZER:     processor=new Equalizer(args.getSrc(), (EqualizerArgs)pack);  break;
            case COMPRESSOR:    processor=new Compressor(args.getSrc(),(CompressorArgs)pack); break;
            case MIXER:         processor=new Mixer(args.getSrc(), args.getFilter(), (MixerArgs)pack);           break;
            case MULTIPLIER:    processor=new Multiplier(args.getSrc(), args.getFilter(), (MultiplierArgs)pack); break;
            default: return null;
        }
        return processor.operate();
    }

    //---------------------------PARSING-------------------------------
    /**
     * parse string properties into ArgumentPack of requested processor type
     * @param processorId target processor identifier
     * @param properties string properties (format "property=value ")
     * @return ArgumentPack with errorCode
     */
    public static ArgumentPack createArgumentPack(ArgumentPack.ProcessorId processorId, String properties) {
        switch (processorId) {
            case EQUALIZER:     return Equalizer.parse(properties);
            case MIXER:         return Mixer.parse(properties);
            case MULTIPLIER:    return Multiplier.parse(properties);
            case NORMALIZER:    return Normalizer.parse(properties);
            case COMPRESSOR:    return Compressor.parse(properties);
            case SHIFTER:       return Shifter.parse(properties);
            case CONVERTER:     return Converter.parse(properties);
            default:            return null;
        }
    }

    private final static String SRC_PROPERTY = "src";
    private final static String FILTER_PROPERTY = "filter";
    private final static String RESULT_PROPERTY = "result";
    private final static String PROCESSOR_PROPERTY = "processor";
    private final static String MAGNITUDE_PROPERTY = "magn";

    /**
     * parse string into MainProcessorArgs with related transient beans
     * @param properties string to parse
     * @param provider provider for bean extracting
     * @return MainProcessorArgs with errorCode PARSING_FAILED if couldn't get significant beans NO_SUCH_PROCESSOR if couldn't identify processor
     * @throws Exception
     */
    public static MainProcessorArgs parse(String properties, AbstractDataProvider provider) throws Exception {
        String temp;
        MainProcessorArgs args = new MainProcessorArgs();
        args.setErrorCode(ErrorCode.SUCCESS);

        temp=SoundDataProcessor.getProperty(properties, SRC_PROPERTY);
        if(temp!=null) {
            SoundData data = provider.readSoundData(Long.valueOf(temp)).getObject();
            if(data!=null) {
                DataArray.readWavDataArray(data);
                args.setSrc(data);
            }
        }
        if(args.getSrc()==null)
            args.setErrorCode(ErrorCode.PARSING_FAILED);

        temp=SoundDataProcessor.getProperty(properties, FILTER_PROPERTY);
        if(temp!=null) {
            SoundData data = provider.readSoundData(Long.valueOf(temp)).getObject();
            if(data!=null)
            {
                DataArray.readWavDataArray(data);
                args.setSrc(data);
            }
        }

        temp=SoundDataProcessor.getProperty(properties, PROCESSOR_PROPERTY);
        if(temp!=null) {
            args.setProcessorId(ArgumentPack.ProcessorId.valueOf(temp));
            args.setProcessorArgs(createArgumentPack(args.getProcessorId(), properties));
        }
        else args.setErrorCode(ErrorCode.NO_SUCH_PROCESSOR);

        temp=SoundDataProcessor.getProperty(properties, MAGNITUDE_PROPERTY);
        if(temp!=null)
            args.setMagnitude(Float.valueOf(temp));

        temp=SoundDataProcessor.getProperty(properties, RESULT_PROPERTY);
        args.setResultFile(temp==null?"null":temp);
        return args;
    }
}
