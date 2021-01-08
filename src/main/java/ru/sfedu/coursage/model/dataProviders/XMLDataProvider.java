package ru.sfedu.coursage.model.dataProviders;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.coursage.Constants;
import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.SoundData;

import javax.xml.stream.XMLStreamException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * class-implementer of XML-based API
 */
public class XMLDataProvider extends AbstractDataProvider {
    public static String getPath(Class bean) {
        return Constants.DATASOURCE_DIRECTORY+"/xml/" +bean.getSimpleName()+".xml";
    }

    public <T> ProviderResult<Collection<T>> readAll(Collection<T> container, Class bean) throws Exception {
        logger.debug("xml parsing...");
        try {
            Serializer serializer = new Persister();
            FileReader reader = new FileReader(getPath(bean));
            XMLWrapper<T>wrapper=serializer.read(XMLWrapper.class, reader);
            reader.close();
            container.addAll(wrapper.getList());
        }
        catch (XMLStreamException e) {
            logger.warn("xml file is empty");
            return new ProviderResult(Error.EMPTY_SOURCE);
        }
        catch (IOException e) {
            logger.error(e);
            return new ProviderResult(Error.FAILED);
        }
        logger.debug("xml parsed");
        return new ProviderResult(container);
    }
    public <T> ProviderResult<Collection<T>> writeAll(Collection<T> container, Class bean) throws Exception {
        logger.debug("xml file writing...");
        if(container.size()==0)
            logger.warn("collection is empty");
        Serializer serializer = new Persister();
        FileWriter writer = new FileWriter(getPath(bean));
        XMLWrapper<T> wrapper = new XMLWrapper<>(container);
        serializer.write(wrapper, writer);
        writer.close();
        logger.debug("xml writing succeed");
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
