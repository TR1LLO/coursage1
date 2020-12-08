package ru.sfedu.coursage.model.dataProviders;

import com.sun.istack.internal.NotNull;
import ru.sfedu.coursage.model.CopyingInterface;

import java.util.Collection;

public interface DataProvider {
    enum DatabaseError {
        SUCCESS,
        FAILED,
        BEAN_NOT_FOUND,
        EMPTY_SOURCE
    }

    /**
     * @param container collection object to store reading beans
     * @param bean class to read
     * @param <T>
     * @return SUCCESS if read >0 beans, EMPTY_SOURCE if file doesn't contain required beans, FAILED if related data source doesn't exist
     * @throws Exception
     */
    <T>DatabaseError readAll(@NotNull Collection<T> container,
                             @NotNull Class bean) throws Exception;

    /**
     * @param container collection object to write from
     * @param bean class to write
     * @param <T>
     * @return SUCCESS if wrote >0 beans, EMPTY_SOURCE if collection is empty, else FAILED
     * @throws Exception
     */
    <T>DatabaseError writeAll(@NotNull Collection<T> container,
                              @NotNull Class bean) throws Exception;

    /**
     * @param obj bean object used to compare by equals method with file containing to extract its fields
     * @param <T> extends CopyingInterface for field copying
     * @return SUCCESS if object successfully extracted, EMPTY_SOURCE if file doesn't contain required beans, BEAN_NOT_FOUND if file doesn't contain equal to obj bean, FAILED if related data source doesn't exist
     * @throws Exception
     */
    <T extends CopyingInterface>DatabaseError read(@NotNull T obj) throws Exception;

    /**
     * @param obj bean to write/overwrite
     * @param <T>
     * @return SUCCESS if bean was found in file and overwrited, BEAN_NOT_FOUND if bean wasn't found but was successfully writed, else FAILED
     * @throws Exception
     */
    <T>DatabaseError write(@NotNull T obj) throws Exception;

    /**
     * @param obj object to remove, compares with file content by equals method
     * @param <T>
     * @return SUCCESS if bean was successfully removed, BEAN_NOT_FOUND if file doesn't contain equal bean, EMPTY_SOURCE if file doesn't contain required beans, FAILED if related data source doesn't exist
     * @throws Exception
     */
    <T>DatabaseError remove(@NotNull T obj) throws Exception;
}
