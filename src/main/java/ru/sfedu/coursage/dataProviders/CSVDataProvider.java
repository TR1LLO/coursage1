package ru.sfedu.coursage.dataProviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.coursage.Constants;
import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.SoundData;

import java.io.*;
import java.util.*;

/**
 * class-implementer of CSV-based API
 */
public class CSVDataProvider extends AbstractDataProvider {
    public static String getPath(Class bean) {
        return Constants.CSV_DIRECTORY +bean.getSimpleName()+Constants.CSV_FORMAT;
    }

    private <T>ProviderResult<Collection<T>> readAll(Collection<T> container, Class bean) throws Exception {
        String path=getPath(bean);
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            List<T> list = new CsvToBeanBuilder<T>(reader)
                    .withEscapeChar('\0')
                    .withType(bean)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
            reader.close();
            if(list.size()==0) {
                logger.warn("readAll empty file");
                return new ProviderResult(DataProvider.Error.EMPTY_SOURCE);
            }
            else {
                container.addAll(list);
                logger.info("readAll succeed");
                return new ProviderResult(container);
            }
        }
        catch(FileNotFoundException e) {
            logger.debug(e);
            return new ProviderResult(DataProvider.Error.FAILED);
        }
    }
    private <T>ProviderResult<Collection<T>> writeAll(Collection<T> container, Class bean) throws Exception {
        String path=getPath(bean);
        if(container.size() == 0) {
            logger.warn("writeAll empty container");
            return new ProviderResult(DataProvider.Error.EMPTY_SOURCE);
        }
        CSVWriter writer = new CSVWriter(new FileWriter(path));

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withEscapechar('\0')
                .build();
        beanToCsv.write(container.iterator());
        writer.close();
        logger.info("readAll succeed");
        return new ProviderResult(container);
    }

    private <T>ProviderResult<T> read(T obj, Class bean) throws Exception {
        List<T> list = new ArrayList<>();
        if(readAll(list, bean).error == DataProvider.Error.EMPTY_SOURCE)
            return new ProviderResult(DataProvider.Error.EMPTY_SOURCE);

        int i=list.indexOf(obj);
        if(i==-1) {
            logger.error("bean not found");
            return new ProviderResult(DataProvider.Error.BEAN_NOT_FOUND);
        }
        logger.debug("bean readed");
        return new ProviderResult(list.get(i));
    }
    private <T>ProviderResult<T> write(T obj, Class bean) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        readAll(list, bean);

        list.remove(obj);
        list.add(obj);
        writeAll(list, obj.getClass());
        logger.debug("bean writed");
        return new ProviderResult(obj);
    }
    private <T>ProviderResult<T> remove(T obj, Class bean) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        readAll(list, bean);

        if(!list.remove(obj))
            return new ProviderResult(Error.BEAN_NOT_FOUND, obj);
        writeAll(list, obj.getClass());
        logger.warn("bean removed");
        return new ProviderResult(obj);
    }

    //-----------------------------------------INTERFACE-------------------------------------------
    public ProviderResult<Collection<ArgumentPack>> readAllArgumentPacks(Collection<ArgumentPack> container, ArgumentPack.ProcessorId processorId) throws Exception {
        return readAll(container, processorId.getPackageClass());
    }
    public ProviderResult<Collection<ArgumentPack>> writeAllArgumentPacks(Collection<ArgumentPack> container, ArgumentPack.ProcessorId processorId) throws Exception {
        return writeAll(container, processorId.getPackageClass());
    }

    public ProviderResult<Collection<SoundData>> readAllSoundData(Collection<SoundData> container) throws Exception {
        return readAll(container, SoundData.class);
    }
    public ProviderResult<Collection<SoundData>> writeAllSoundData(Collection<SoundData> container) throws Exception {
        return writeAll(container, SoundData.class);
    }

    public ProviderResult<SoundData> extReadSoundData(SoundData obj) throws Exception {
        return read(obj, SoundData.class);
    }
    public ProviderResult<SoundData> extWriteSoundData(SoundData obj) throws Exception {
        return write(obj, SoundData.class);
    }
    public ProviderResult<SoundData> extRemoveSoundData(SoundData obj) throws Exception {
        return remove(obj, SoundData.class);
    }

    public ProviderResult<ArgumentPack> extReadArgumentPack(ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception {
        return read(obj, processorId.getPackageClass());
    }
    public ProviderResult<ArgumentPack> extWriteArgumentPack(ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception {
        return write(obj, processorId.getPackageClass());
    }
    public ProviderResult<ArgumentPack> extRemoveArgumentPack(ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception {
        return remove(obj, processorId.getPackageClass());
    }
}
