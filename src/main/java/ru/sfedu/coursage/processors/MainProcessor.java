package ru.sfedu.coursage.processors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.model.ArgumentPack.ErrorCode;
import ru.sfedu.coursage.model.dataProviders.AbstractDataProvider;
import sun.applet.Main;

/**
 * generalizing class-dispatcher of SoundProcessors and their methods
 */
public class MainProcessor {
    public static Logger logger = LogManager.getLogger();
    MainProcessorArgs args;
    public MainProcessor (MainProcessorArgs args) {
        this.args=args;
    }

    /**
     * run processing
     * @return SoundData with processed content
     */
    public SoundData operate() {
        logger.debug("processor dispatching...");
        ArgumentPack pack = args.getProcessorArgs();
        SoundDataProcessor processor;
        switch (pack.getProcessorId()) {
            case CONVERTER:     processor=new Converter(    args.getSrc(), args.getDst(), (ConverterArgs)pack);  break;
            case SHIFTER:       processor=new Shifter(      args.getSrc(), args.getDst(), (ShifterArgs)pack);    break;
            case NORMALIZER:    processor=new Normalizer(   args.getSrc(), args.getDst(), (NormalizerArgs)pack); break;
            case EQUALIZER:     processor=new Equalizer(    args.getSrc(), args.getDst(), (EqualizerArgs)pack);  break;
            case COMPRESSOR:    processor=new Compressor(   args.getSrc(), args.getDst(), (CompressorArgs)pack); break;
            case MIXER:         processor=new Mixer(        args.getSrc(), args.getFilter(), args.getDst(), (MixerArgs)pack);      break;
            case MULTIPLIER:    processor=new Multiplier(   args.getSrc(), args.getFilter(), args.getDst(), (MultiplierArgs)pack); break;
            default:
                logger.error("target processorId undefined");
                return null;
        }
        SoundData res = processor.operate();
        res.getData().hash=res.getData().bufferHash();
        return res;
    }

    //---------------------------PARSING-------------------------------
    /**
     * parse string properties into ArgumentPack of requested processor type
     * @param processorId target processor identifier
     * @param properties string properties (format "property=value ")
     * @return ArgumentPack with errorCode
     */
    public static ArgumentPack createArgumentPack(ArgumentPack.ProcessorId processorId, String properties) {
        logger.debug("parser dispatching...");
        switch (processorId) {
            case EQUALIZER:     return Equalizer.parse(properties);
            case MIXER:         return Mixer.parse(properties);
            case MULTIPLIER:    return Multiplier.parse(properties);
            case NORMALIZER:    return Normalizer.parse(properties);
            case COMPRESSOR:    return Compressor.parse(properties);
            case SHIFTER:       return Shifter.parse(properties);
            case CONVERTER:     return Converter.parse(properties);
            default:
                logger.error("target processorId undefined");
                return null;
        }
    }

    private final static String SRC_PROPERTY = "src";
    private final static String DST_PROPERTY = "dst";
    private final static String FILTER_PROPERTY = "filter";
    private final static String PACK_ID_PROPERTY = "id";
    private final static String PROCESSOR_PROPERTY = "processor";

    /**
     * parse string into MainProcessorArgs with related transient beans
     * @param properties string to parse
     * @param provider provider for bean extracting
     * @return MainProcessorArgs with errorCode PARSING_FAILED if couldn't get significant beans NO_SUCH_PROCESSOR if couldn't identify processor
     * @throws Exception
     */
    public static MainProcessorArgs parse(String properties, AbstractDataProvider provider) throws Exception {
        logger.debug("MainProcessorArgs parsing...");
        String temp;
        MainProcessorArgs args = new MainProcessorArgs();
        args.setErrorCode(ErrorCode.SUCCESS);

        logger.debug("src init");
        //---------------------src_init----------------------
        temp=SoundDataProcessor.getProperty(properties, SRC_PROPERTY);
        if(temp!=null) {
            SoundData data = provider.readSoundData(Long.valueOf(temp)).getObject();
            if(data!=null) {
                DataArray.readWavDataArray(data);
                args.setSrc(data);
            }
            else logger.error("src bean not found");
        }
        else logger.error("src property missing");
        if(args.getSrc()==null) {
            logger.error("src init failed");
            args.setErrorCode(ErrorCode.PARSING_FAILED);
        }

        logger.debug("dst init");
        //---------------------dst_init----------------------
        temp=SoundDataProcessor.getProperty(properties, DST_PROPERTY);
        if(temp!=null) {
            SoundData data = provider.readSoundData(Long.valueOf(temp)).getObject();
            if(data!=null) {
                DataArray.readWavDataArray(data);
                args.setDst(data);
            }
            else logger.error("dst bean not found");
        }
        else logger.error("dst property missing");
        if(args.getDst()==null) {
            logger.error("dst init failed");
            args.setErrorCode(ErrorCode.PARSING_FAILED);
        }

        logger.debug("filter init");
        //---------------------filter_init----------------------
        temp=SoundDataProcessor.getProperty(properties, FILTER_PROPERTY);
        if(temp!=null) {
            SoundData data = provider.readSoundData(Long.valueOf(temp)).getObject();
            if(data!=null)
            {
                DataArray.readWavDataArray(data);
                args.setSrc(data);
            }
            else logger.error("filter bean not found");
        }
        else logger.warn("filter property missing");

        logger.debug("identifiers init");
        //------------------identifiers_init----------------------
        temp=SoundDataProcessor.getProperty(properties, PROCESSOR_PROPERTY);
        if(temp!=null) {
            ArgumentPack.ProcessorId processor = ArgumentPack.ProcessorId.valueOf(temp);
            args.setProcessorId(processor);
            ArgumentPack pack = createArgumentPack(args.getProcessorId(), properties);

            temp=SoundDataProcessor.getProperty(properties, PACK_ID_PROPERTY);
            if(temp!=null) {
                logger.warn("pack id found");
                pack.setId(Long.valueOf(temp));
                provider.read(pack, processor.getPackageClass());
            }
            args.setProcessorArgs(pack);
        }
        else {
            logger.error("processor property missing");
            args.setErrorCode(ErrorCode.NO_SUCH_PROCESSOR);
        }

        logger.debug("MainProcessorArgs parsed");
        return args;
    }
}
