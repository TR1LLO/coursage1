package ru.sfedu.coursage.model.dataProviders;
import com.sun.istack.internal.NotNull;
import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.processors.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * intermediate class-implementer of dataSource-independent API methods
 */
public abstract class AbstractDataProvider implements DataProvider {
    //----------------------API-------------------------------------
    /**
     * run processing
     * @param args filled MainProcessor argument package
     * @return processed SoundData if succeed, null if couldn't identify processor type, args doesn't contain significant data or processor can't operate incoming data
     */
    public SoundData operate(MainProcessorArgs args) {
        MainProcessor processor = new MainProcessor(args);
        return processor.operate();
    }
    /**
     * parse String properties into ready-to-work MainProcessorArgs package
     * @param properties string with properties to parse (format property=value)
     * @return MainProcessorArgs with errorCode PARSING_FAILED if missed property or found incompatible type, NO_SUCH_PROCESSOR if couldn't identify processor type
     * @throws Exception
     */
    public MainProcessorArgs createMainProcessorArgs(String properties) throws Exception {
        return MainProcessor.parse(properties, this);
    }

    /**
     * run processing
     * @param srcId processing SoundData id
     * @param filterId additional SoundData id
     * @param argumentPackId processor package id
     * @param processor processor id
     * @return SUCCESS + processed SoundData if proceed with no errors, BEAN_NOT_FOUND if significant id doesn't exist in dataSource, else FAILED
     * @throws Exception
     */
    public ProviderResult<SoundData> operate(long srcId, long filterId, long argumentPackId, ArgumentPack.ProcessorId processor) throws Exception{
        ProviderResult result;
        SoundData src = new SoundData();
        src.setId(srcId);
        result=read(src, SoundData.class);
        if(result.error==Error.BEAN_NOT_FOUND)
            return new ProviderResult(Error.BEAN_NOT_FOUND, src);
        src=(SoundData)result.getObject();

        SoundData filter = new SoundData();
        filter.setId(filterId);
        result=read(filter, SoundData.class);
        if(filterId!=-1 && result.error!=Error.BEAN_NOT_FOUND)
            return new ProviderResult(Error.BEAN_NOT_FOUND, filter);
        filter=(SoundData)result.getObject();

        ArgumentPack pack = readArgumentPack(argumentPackId, processor).getObject();
        if(pack==null)
            return new ProviderResult(Error.BEAN_NOT_FOUND);

        MainProcessorArgs args = new MainProcessorArgs();
        args.setSrc(src);
        args.setFilter(filter);
        args.setProcessorId(pack.getProcessorId());
        args.setProcessorArgs(pack);

        args.setWarnCode(ArgumentPack.WarnCode.INDEFINITE);
        args.setErrorCode(ArgumentPack.ErrorCode.INDEFINITE);
        args.setResultFile(src.getSourceFile().substring(0, src.getSourceFile().lastIndexOf('.'))+"operated.wav");
        args.setMagnitude(1.0f);
        SoundData res = operate(args);
        return write(res, SoundData.class);
    }
    /**
     * create SoundData from .wav file and write into dataSource
     * @param sourceFile .wav source file
     * @return FAILED if file contains format errors or couldn't be properly read, else SUCCESS + SoundData with external DataArray
     * @throws Exception
     */
    public ProviderResult<SoundData> createSoundData(String sourceFile) throws Exception {
        SoundData data = DataArray.readWavSoundData(sourceFile);
        if(data==null)
            return new ProviderResult(Error.FAILED);
        data.setId(nextSoundDataId());
        return write(data, SoundData.class);
    }


    //----------------------SOUND_DATA------------------------------
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
        readAll(set, SoundData.class);

        Iterator<SoundData> iterator = set.iterator();
        for(long i=0; i<set.size()+1; i++)
            if(!iterator.hasNext())
                return i;
            else if(i<iterator.next().getId())
                return i;
        return -1;
    }
    /**
     * read SoundData by id
     * @param id identifier of SoundData
     * @return SUCCESS + SoundData if succeed, BEAN_NOT_FOUND if dataSource doesn't contain SoundData with such id, else FAILED
     * @throws Exception
     */
    public ProviderResult<SoundData> readSoundData(long id) throws Exception {
        SoundData data = new SoundData();
        data.setId(id);
        return read(data, SoundData.class);
    }
    /**
     * write transient SoundData into dataSource
     * @param data bean to write
     * @return SUCCESS if bean overwrited, BEAN_NOT_FOUND if writed new record, FAILED if error occurred
     * @throws Exception
     */
    public ProviderResult<SoundData> writeSoundData(@NotNull SoundData data) throws Exception {
        if(write(data, SoundData.class).error==Error.FAILED)
            return new ProviderResult(Error.FAILED);
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
        SoundData data=new SoundData();
        data.setId(id);
        return remove(data, SoundData.class);
    }


    //----------------------ARGUMENT_PACKS--------------------------
    /**
     * get free ArgumentPack id
     * @param tClass bean of ArgumentPack
     * @param <T>
     * @return the least free id in dataSource, -1 if no free values available
     * @throws Exception
     */
    public <T extends ArgumentPack>long nextArgumentPackId(Class<T> tClass) throws Exception {
        TreeSet<T> set = new TreeSet<>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return (int)(o1.getId()-o2.getId());
            }
        });
        readAll(set, tClass);

        Iterator<T> iterator = set.iterator();
        for(long i=0; i<set.size()+1; i++)
            if(i<iterator.next().getId())
                return i;
        return -1;
    }
    /**
     * read ArgumentPack by ids
     * @param id package id
     * @param processor processor id
     * @return SUCCESS + ArgumentPack if succeed, BEAN_NOT_FOUND if dataSource doesn't contain requested package, else FAILED
     * @throws Exception
     */
    public ProviderResult<ArgumentPack> readArgumentPack(long id, ArgumentPack.ProcessorId processor) throws Exception {
        ArgumentPack pack = processor.newPackage();
        pack.setId(id);
        return read(pack, processor.getPackageClass());
    }
    /**
     * write ArgumentPack into dataSource
     * @param pack bean to write
     * @return SUCCEED if overwrited successfully, BEAN_NOT_FOUND if created new record in dataSource, else FAILED
     * @throws Exception
     */
    public ProviderResult<ArgumentPack> writeArgumentPack(@NotNull ArgumentPack pack) throws Exception {
        return write(pack, pack.getProcessorId().getPackageClass());
    }
    /**
     * remove ArgumentPack by ids
     * @param id package id
     * @param processor processor id
     * @return SUCCEED if removed successfully, BEAN_NOT_FOUND if dataSource doesn't contain equal bean, EMPTY_SOURCE if dataSource already empty, else FAILED
     * @throws Exception
     */
    public ProviderResult<ArgumentPack> removeArgumentPack(long id, ArgumentPack.ProcessorId processor) throws Exception {
        ArgumentPack pack = processor.newPackage();
        pack.setId(id);
        return remove(pack, processor.getPackageClass());
    }
}
