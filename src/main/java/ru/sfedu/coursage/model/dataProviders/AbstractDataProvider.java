package ru.sfedu.coursage.model.dataProviders;
import com.sun.istack.internal.NotNull;
import com.sun.org.apache.xpath.internal.Arg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.processors.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * intermediate class-implementer of dataSource-independent API methods
 */
public abstract class AbstractDataProvider implements DataProvider {
    public static Logger logger = LogManager.getLogger();

    //---------------------EXTERNAL_API---------------------------------
    /**
     * run processing
     * @param args filled MainProcessor argument package
     * @return processed SoundData if succeed, null if couldn't identify processor type, args doesn't contain significant data or processor can't operate incoming data
     */
    public SoundData operate(@NotNull MainProcessorArgs args) {
        logger.debug("MainProcessor operating started...");
        MainProcessor processor = new MainProcessor(args);
        return processor.operate();
    }
    /**
     * create empty persistent SoundData clone
     * @param orig original object
     * @return SUCCESS + SoundData if cloned and writed successfully, else FAILED
     * @throws Exception
     */
    public ProviderResult<SoundData> createSoundDataEmptyClone(@NotNull SoundData orig) throws Exception {
        logger.debug("SoundData empty cloning...");
        if(orig.getData()==null)
            return new ProviderResult(Error.EMPTY_SOURCE);
        ProviderResult<SoundData> result= createEmptySoundData(
                orig.getData().getBits(),
                orig.getData().getSize(),
                orig.getData().getChannels(),
                orig.getSampleRate(),
                orig.getSourceFile().substring(0, orig.getSourceFile().lastIndexOf('.'))+"mod.wav");
        if(result.getError()==Error.FAILED) {
            logger.error("empty SoundData creating failed");
            return result;
        }
        result.getObject().setId(nextSoundDataId());
        return writeSoundData(result.getObject());
    }
    /**
     * creates transient SoundData with given attributes
     * @param id
     * @param bits
     * @param channels
     * @param sampleRate
     * @param src
     * @return
     * @throws Exception
     */
    public ProviderResult<SoundData> createSoundData(long id, int bits, int channels, int sampleRate, @NotNull String src) throws Exception {
        logger.debug("SoundData creating...");
        SoundData data = new SoundData();
        data.setId(id);
        data.setBitness(SoundData.Bitness.valueOf(bits));
        data.setChannels(channels);
        data.setSourceFile(src);
        data.setSampleRate(sampleRate);
        data.setData(DataArray.readWavDataArray(data));
        if(data.getData()==null) {
            logger.error("DataArray init failed");
            return new ProviderResult(Error.FAILED);
        }
        logger.info("SoundData created");
        return new ProviderResult(data);
    }

    //----------------------PUBLIC_API----------------------------------
    /**
     * run processing
     * @param srcId processing SoundData id
     * @param filterId additional SoundData id
     * @param argumentPackId processor package id
     * @param processor processor id
     * @return SUCCESS + processed SoundData if proceed with no errors, BEAN_NOT_FOUND if significant id doesn't exist in dataSource, else FAILED
     * @throws Exception
     */
    public ProviderResult<SoundData> operate(long srcId, long filterId, long dstId, long argumentPackId, @NotNull ArgumentPack.ProcessorId processor) throws Exception{
        logger.info("operating process started...");
        ProviderResult result;

        logger.debug("src init");
        //-----------------src_init--------------------
        SoundData src;
        result=readSoundData(srcId);
        if(result.error==Error.BEAN_NOT_FOUND) {
            logger.error("src not found");
            return result;
        }
        src=(SoundData)result.getObject();

        logger.debug("dst init");
        //-----------------dst_init--------------------
        SoundData dst;
        if(dstId!=-1) {
            result=readSoundData(dstId);
            if(result.error==Error.BEAN_NOT_FOUND) {
                logger.error("dst not found");
                return result;
            }
        }
        else {
            logger.warn("dst auto creating...");
            result= createSoundDataEmptyClone(src);
            if(result.getError()==Error.FAILED) {
                logger.error("dst auto creating failed");
                return result;
            }
        }
        dst=(SoundData)result.getObject();

        logger.debug("filter init");
        //-----------------filter_init--------------------
        SoundData filter = null;
        if(filterId!=-1)
        {
            result=readSoundData(filterId);
            if(result.error==Error.BEAN_NOT_FOUND) {
                logger.error("filter not found");
                return result;
            }
            filter=(SoundData)result.getObject();
        }
        else logger.warn("filter id -1");

        logger.debug("pack init");
        //-----------------pack_init--------------------
        result=readArgumentPack(argumentPackId, processor);
        if(result.getError()==Error.BEAN_NOT_FOUND) {
            logger.error("pack not found");
            return result;
        }
        ArgumentPack pack =(ArgumentPack)result.getObject();

        logger.info("bean loading complete");
        //-----------------operating_start----------------
        MainProcessorArgs args = new MainProcessorArgs();
        args.setSrc(src);
        args.setDst(dst);
        args.setFilter(filter);
        args.setProcessorId(pack.getProcessorId());
        args.setProcessorArgs(pack);

        args.setWarnCode(ArgumentPack.WarnCode.INDEFINITE);
        args.setErrorCode(ArgumentPack.ErrorCode.INDEFINITE);
        return writeSoundData(operate(args));
    }
    /**
     * create persistent SoundData from .wav file
     * @param sourceFile .wav source file
     * @return FAILED if file contains format errors or couldn't be properly read, else SUCCESS + SoundData with external DataArray
     * @throws Exception
     */
    public ProviderResult<SoundData> createSoundData(@NotNull String sourceFile) throws Exception {
        logger.debug("SoundData creating...");
        SoundData data = DataArray.readWavSoundData(sourceFile);
        if(data==null) {
            logger.error("SoundData creating failed");
            return new ProviderResult(Error.FAILED);
        }
        data.setId(nextSoundDataId());
        return extWriteSoundData(data);
    }
    /**
     * create transient SoundData with empty DataArray
     * @param bits count of bits per sample
     * @param size buffer size in samples
     * @param channels channel count
     * @param destination file used to further storing DataArray
     * @return new transient SoundData object with initialized empty DataArray
     */
    public ProviderResult<SoundData> createEmptySoundData(int bits, int size, int channels, int sampleRate, @NotNull String destination) {
        logger.debug("empty SoundData creating...");
        SoundData data = new SoundData();
        data.setBitness(SoundData.Bitness.valueOf(bits));
        data.setChannels(channels);
        data.setSourceFile(destination);
        data.setSampleRate(sampleRate);
        data.setData(DataArray.createEmpty(size, bits, channels, sampleRate));
        if(data.getData()==null) {
            logger.error("DataArray init failed");
            return new ProviderResult(Error.FAILED);
        }
        logger.info("empty SoundData created");
        return new ProviderResult(data);
    }


    public ProviderResult<ArgumentPack> createCompressorArgs(float power, boolean distortion) throws Exception {
        logger.debug("CompressorArgs creating...");
        CompressorArgs args=new CompressorArgs();
        args.setPower(power);
        args.setDistortion(distortion);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }
    public ProviderResult<ArgumentPack> createConverterArgs(SoundData.Bitness bitness, int channels, int sampleRate) throws Exception {
        logger.debug("ConverterArgs creating...");
        ConverterArgs args=new ConverterArgs();
        args.setBitness(bitness);
        args.setChannels(channels);
        args.setSampleRate(sampleRate);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }
    public ProviderResult<ArgumentPack> createEqualizerArgs(float[] amplitudes) throws Exception {
        logger.debug("EqualizerArgs creating...");
        EqualizerArgs args=new EqualizerArgs();
        args.setAmps(amplitudes);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }
    public ProviderResult<ArgumentPack> createMixerArgs(boolean channelMixing, boolean cover) throws Exception {
        logger.debug("MixerArgs creating...");
        MixerArgs args=new MixerArgs();
        args.setChannelMixing(channelMixing);
        args.setCover(cover);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }
    public ProviderResult<ArgumentPack> createMultiplierArgs(boolean average, int count) throws Exception {
        logger.debug("MixerArgs creating...");
        MultiplierArgs args=new MultiplierArgs();
        args.setAverage(average);
        args.setCount(count);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }
    public ProviderResult<ArgumentPack> createNormalizerArgs(float amp, boolean hard, int period) throws Exception {
        logger.debug("MixerArgs creating...");
        NormalizerArgs args=new NormalizerArgs();
        args.setAmp(amp);
        args.setHard(hard);
        args.setPeriod(period);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }
    public ProviderResult<ArgumentPack> createShifterArgs(float frequency, float step, int radius) throws Exception {
        logger.debug("MixerArgs creating...");
        ShifterArgs args=new ShifterArgs();
        args.setFrequency(frequency);
        args.setStep(step);
        args.setRadius(radius);

        args.setId(nextArgumentPackId(args.getProcessorId()));
        return writeArgumentPack(args);
    }


    //--------------------SOUND_DATA_RWR-------------------------------
    /**
     * get free SoundData id
     * @return the least free id in dataSource, -1 if no free values available
     * @throws Exception
     */
    public long nextSoundDataId() throws Exception {
        TreeSet<SoundData> set = new TreeSet<>(new Comparator<SoundData>() {
            @Override
            public int compare(SoundData o1, SoundData o2) {
                return (int)(o1.getId()-o2.getId());
            }
        });
        readAllSoundData(set);

        Iterator<SoundData> iterator = set.iterator();
        for(long i=0; i<set.size(); i++)
            if(i<iterator.next().getId())
                return i;
        return set.size();
    }
    /**
     * read SoundData by id
     * @param id identifier of SoundData
     * @return SUCCESS + SoundData if succeed, BEAN_NOT_FOUND if dataSource doesn't contain SoundData with such id, else FAILED
     * @throws Exception
     */
    public ProviderResult<SoundData> readSoundData(long id) throws Exception {
        logger.info("SoundData reading...");
        SoundData data = new SoundData();
        data.setId(id);
        ProviderResult result = extReadSoundData(data);
        if(result.getError()==Error.BEAN_NOT_FOUND) {
            logger.warn("SoundData not found");
            return result;
        }
        data=(SoundData)result.getObject();
        logger.debug("DataArray init...");
        data.setData(DataArray.readWavDataArray(data));
        if(data.getData()==null) {
            logger.error("DataArray init failed");
            return new ProviderResult(Error.FAILED, data);
        }
        logger.debug("DataArray init complete");
        logger.info("SoundData readed");
        return new ProviderResult(data);
    }
    /**
     * write SoundData into dataSource
     * @param data bean to write
     * @return SUCCESS if bean overwrited, BEAN_NOT_FOUND if writed new record, FAILED if error occurred
     * @throws Exception
     */
    public ProviderResult<SoundData> writeSoundData(@NotNull SoundData data) throws Exception {
        logger.info("SoundData saving...");
        if(extWriteSoundData(data).error==Error.FAILED) {
            logger.error("SoundData writing corrupted");
            return new ProviderResult(Error.FAILED);
        }
        return DataArray.writeWavDataArray(data)?
                new ProviderResult(data):
                new ProviderResult(Error.NO_RESULT_DESTINATION, data);
    }
    /**
     * remove SoundData by id
     * @param id identifier of SoundData
     * @return SUCCESS if bean found and removed, BEAN_NOT_FOUND if dataSource doesn't contain equal bean, else FAILED
     * @throws Exception
     */
    public ProviderResult<SoundData> removeSoundData(long id) throws Exception {
        logger.warn("SoundData removing...");
        SoundData data=new SoundData();
        data.setId(id);
        return extRemoveSoundData(data);
    }


    //-------------------ARGUMENT_PACKS_RWR----------------------------
    /**
     * get free ArgumentPack id
     * @param processorId identifier of ArgumentPack class
     * @return the least free id in dataSource, -1 if no free values available
     * @throws Exception
     */
    public long nextArgumentPackId(ArgumentPack.ProcessorId processorId) throws Exception {
        TreeSet<ArgumentPack> set = new TreeSet<>(new Comparator<ArgumentPack>() {
            @Override
            public int compare(ArgumentPack o1, ArgumentPack o2) {
                return (int)(o1.getId()-o2.getId());
            }
        });
        readAllArgumentPacks(set, processorId);

        Iterator<ArgumentPack> iterator = set.iterator();
        for(long i=0; i<set.size(); i++)
            if(i<iterator.next().getId())
                return i;
        return set.size();
    }
    /**
     * read ArgumentPack by ids
     * @param id package id
     * @param processor processor id
     * @return SUCCESS + ArgumentPack if succeed, BEAN_NOT_FOUND if dataSource doesn't contain requested package, else FAILED
     * @throws Exception
     */
    public ProviderResult<ArgumentPack> readArgumentPack(long id, @NotNull ArgumentPack.ProcessorId processor) throws Exception {
        logger.debug("ArgumentPack reading...");
        ArgumentPack pack = processor.newPackage();
        pack.setId(id);
        return extReadArgumentPack(pack, processor);
    }
    /**
     * write ArgumentPack into dataSource
     * @param pack bean to write
     * @return SUCCEED if overwrited successfully, BEAN_NOT_FOUND if created new record in dataSource, else FAILED
     * @throws Exception
     */
    public ProviderResult<ArgumentPack> writeArgumentPack(@NotNull ArgumentPack pack) throws Exception {
        logger.debug("ArgumentPack writing...");
        return extWriteArgumentPack(pack, pack.getProcessorId());
    }
    /**
     * remove ArgumentPack by ids
     * @param id package id
     * @param processor processor id
     * @return SUCCEED if removed successfully, BEAN_NOT_FOUND if dataSource doesn't contain equal bean, EMPTY_SOURCE if dataSource already empty, else FAILED
     * @throws Exception
     */
    public ProviderResult<ArgumentPack> removeArgumentPack(long id, @NotNull ArgumentPack.ProcessorId processor) throws Exception {
        logger.debug("ArgumentPack removing...");
        ArgumentPack pack = processor.newPackage();
        pack.setId(id);
        return extRemoveArgumentPack(pack, processor);
    }
}
