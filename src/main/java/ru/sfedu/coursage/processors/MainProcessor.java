package ru.sfedu.coursage.processors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.model.*;

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
}
