package ru.sfedu.coursage.model.dataProviders;

import com.sun.istack.internal.NotNull;
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
            this(Error.SUCCESS, object);
        }

        public T getObject() {
            return object;
        }
        public Error getError() {
            return error;
        }
    }

    /**
     * read all beans from dataSource
     * @param container collection object to store reading beans
     * @param bean class to read
     * @param <T>
     * @return SUCCESS if read >0 beans, EMPTY_SOURCE if file doesn't contain required beans, FAILED if related data source doesn't exist
     * @throws Exception
     */
    <T>ProviderResult<Collection<T>> readAll(@NotNull Collection<T> container,
                                             @NotNull Class bean) throws Exception;
    /**
     * write bean collection into dataSource
     * @param container collection object to write from
     * @param bean class to write
     * @param <T>
     * @return SUCCESS if wrote >0 beans, EMPTY_SOURCE if collection is empty, else FAILED
     * @throws Exception
     */
    <T>ProviderResult<Collection<T>> writeAll(@NotNull Collection<T> container,
                                              @NotNull Class bean) throws Exception;
    /**
     * fill bean object with related data from dataSource
     * @param obj bean object used to compare by equals method with file containing to extract its fields
     * @param <T> extends CopyingInterface for field copying
     * @return SUCCESS if object successfully extracted, EMPTY_SOURCE if file doesn't contain required beans, BEAN_NOT_FOUND if file doesn't contain equal to obj bean, FAILED if related data source doesn't exist
     * @throws Exception
     */
    <T>ProviderResult<T> read(@NotNull T obj,
                              @NotNull Class bean) throws Exception;
    /**
     * write bean into dataSource or overwrite if equal already exist
     * @param obj bean to write/overwrite
     * @param <T>
     * @return SUCCESS if bean was found in file and overwrited, BEAN_NOT_FOUND if bean wasn't found but was successfully writed, else FAILED
     * @throws Exception
     */
    <T>ProviderResult<T> write(@NotNull T obj,
                               @NotNull Class bean) throws Exception;
    /**
     * remove bean from dataSource
     * @param obj object to remove, compares with file content by equals method
     * @param <T>
     * @return SUCCESS if bean was successfully removed, BEAN_NOT_FOUND if file doesn't contain equal bean, EMPTY_SOURCE if file doesn't contain required beans, FAILED if related data source doesn't exist
     * @throws Exception
     */
    <T>ProviderResult<T> remove(@NotNull T obj,
                                @NotNull Class bean) throws Exception;
}
