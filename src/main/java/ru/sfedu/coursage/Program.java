package ru.sfedu.coursage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.dataProviders.*;
import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.SoundData;

import java.util.Locale;

public class Program {
    public static Logger logger = LogManager.getLogger();
    public static AbstractDataProvider provider;
    private enum CLICommand {
        CREATE_COMPRESSOR,
        CREATE_CONVERTER,
        CREATE_EQUALIZER,
        CREATE_MIXER,
        CREATE_MULTIPLIER,
        CREATE_NORMALIZER,
        CREATE_SHIFTER,
        OPERATE,
        CREATE_SOUNDDATA,
        CREATE_EMPTY_SOUNDDATA,
        READ_ARGUMENTPACK,
        REMOVE_ARGUMENTPACK,
        READ_SOUNDDATA,
        REMOVE_SOUNDDATA
    }

    public static void main(String[] args) throws Exception {
        switch(args[0].toLowerCase(Locale.ROOT)) {
            case "csv":     provider=new CSVDataProvider();     break;
            case "xml":     provider=new XMLDataProvider();     break;
            case "jdbc":    provider=new JDBCDataProvider();    break;
            default: logger.error("undefined dataprovider"); return;
        }

        DataProvider.ProviderResult result = null;
        switch (CLICommand.valueOf(args[1].toUpperCase(Locale.ROOT))) {
            case CREATE_COMPRESSOR:       result = provider.createCompressorArgs(Integer.parseInt(args[2]), Boolean.parseBoolean(args[2])); break;
            case CREATE_CONVERTER:        result = provider.createConverterArgs(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4])); break;
            case CREATE_MIXER:            result = provider.createMixerArgs(Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3])); break;
            case CREATE_MULTIPLIER:       result = provider.createMultiplierArgs(Boolean.parseBoolean(args[2]), Integer.parseInt(args[3])); break;
            case CREATE_NORMALIZER:       result = provider.createNormalizerArgs(Float.parseFloat(args[2]), Boolean.parseBoolean(args[3]), Integer.parseInt(args[4])); break;
            case CREATE_SHIFTER:          result = provider.createShifterArgs(Float.parseFloat(args[2]), Float.parseFloat(args[3]), Integer.parseInt(args[4])); break;
            case CREATE_EQUALIZER:
                float a[] = {Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7]), Float.parseFloat(args[8]), Float.parseFloat(args[9]), Float.parseFloat(args[10]), Float.parseFloat(args[11])};
                result = provider.createEqualizerArgs(a); break;

            case OPERATE:                 result = provider.operate(Long.parseLong(args[3]), Long.parseLong(args[4]), Long.parseLong(args[5]), Long.parseLong(args[6]), ArgumentPack.ProcessorId.valueOf(args[7].toUpperCase(Locale.ROOT))); break;
            case CREATE_SOUNDDATA:        result = provider.createSoundData(args[2]); break;
            case CREATE_EMPTY_SOUNDDATA:  result = provider.createEmptySoundData(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6]); break;

            case READ_ARGUMENTPACK:       result = provider.readArgumentPack(Long.parseLong(args[2]), ArgumentPack.ProcessorId.valueOf(args[7].toUpperCase(Locale.ROOT))); break;
            case REMOVE_ARGUMENTPACK:     result = provider.removeArgumentPack(Long.parseLong(args[2]), ArgumentPack.ProcessorId.valueOf(args[7].toUpperCase(Locale.ROOT))); break;
            case READ_SOUNDDATA:          result = provider.readSoundData(Long.parseLong(args[2])); break;
            case REMOVE_SOUNDDATA:        result = provider.removeSoundData(Long.parseLong(args[2])); break;
        }
        logger.info(result==null?"undefined command":result.toString());
    }
}
