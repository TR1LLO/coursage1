package ru.sfedu.coursage.model.dataProviders;

import com.sun.istack.internal.NotNull;
import com.sun.org.apache.xpath.internal.Arg;
import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.SoundData;

import java.util.Collection;

/**
 * interface of basic operations with dataSource
 */
public interface DataProvider {
    enum Error {
        SUCCESS,
        FAILED,
        BEAN_NOT_FOUND,
        EMPTY_SOURCE,
        MISSING_PROPERTY,
        NO_RESULT_DESTINATION
    }
    /**
     * returnable container for provider operation status and related objects
     * @param <T> Object type
     */
    class ProviderResult<T extends Object> {
        Error error;
        T object;
        ProviderResult(Error error, T object){
            this.error = error;
            this.object=object;
        }
        ProviderResult(Error error) {
            this(error, null);
        }
        ProviderResult(T object){
            this(object==null?Error.FAILED:Error.SUCCESS, object);
        }

        public T getObject() {
            return object;
        }
        public Error getError() {
            return error;
        }

        @Override
        public String toString() {
            return "ProviderResult [" +
                    "error=" + error +
                    ", object=" + object + ']';
        }
    }

    /**
     * read all beans from dataSource
     * @param container collection object to store reading beans
     * @param processorId class identifier to write
     * @return SUCCESS if read >0 beans, EMPTY_SOURCE if file doesn't contain required beans, FAILED if related data source doesn't exist
     * @throws Exception
     */
    ProviderResult<Collection<ArgumentPack>> readAllArgumentPacks(@NotNull Collection<ArgumentPack> container,
                                                                  @NotNull ArgumentPack.ProcessorId processorId) throws Exception;
    /**
     * write bean collection into dataSource
     * @param container collection object to write from
     * @param processorId class identifier to write
     * @return SUCCESS if wrote >0 beans, EMPTY_SOURCE if collection is empty, else FAILED
     * @throws Exception
     */
    ProviderResult<Collection<ArgumentPack>> writeAllArgumentPacks(@NotNull Collection<ArgumentPack> container,
                                                                   @NotNull ArgumentPack.ProcessorId processorId) throws Exception;

    /**
     * read all beans from dataSource
     * @param container collection object to store reading beans
     * @return SUCCESS if read >0 beans, EMPTY_SOURCE if file doesn't contain required beans, FAILED if related data source doesn't exist
     * @throws Exception
     */
    ProviderResult<Collection<SoundData>> readAllSoundData(@NotNull Collection<SoundData> container) throws Exception;
    /**
     * write bean collection into dataSource
     * @param container collection object to write from
     * @return SUCCESS if wrote >0 beans, EMPTY_SOURCE if collection is empty, else FAILED
     * @throws Exception
     */
    ProviderResult<Collection<SoundData>> writeAllSoundData(@NotNull Collection<SoundData> container) throws Exception;


    ProviderResult<SoundData> extReadSoundData(@NotNull SoundData obj) throws Exception;
    ProviderResult<SoundData> extWriteSoundData(@NotNull SoundData obj) throws Exception;
    ProviderResult<SoundData> extRemoveSoundData(@NotNull SoundData obj) throws Exception;


    ProviderResult<ArgumentPack> extReadArgumentPack(@NotNull ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception;
    ProviderResult<ArgumentPack> extWriteArgumentPack(@NotNull ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception;
    ProviderResult<ArgumentPack> extRemoveArgumentPack(@NotNull ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception;
}
