package ru.sfedu.coursage.model.dataProviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.Constants;

import java.io.*;
import java.util.*;

/**
 * class-implementer of CSV-based API
 */
public class CSVDataProvider extends AbstractDataProvider {
    public static Logger logger = LogManager.getLogger(CSVDataProvider.class);
    public static String getPath(Class bean) {
        return Constants.DATADIRECTORY+bean.toString()+".csv";
    }

    public <T>ProviderResult<Collection<T>> readAll(Collection<T> container, Class bean) throws Exception {
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
                logger.warn("readAll emptyFile "+path);
                return new ProviderResult(DataProvider.Error.EMPTY_SOURCE);
            }
            else {
                container.addAll(list);
                logger.info("readAll succeed "+path);
                return new ProviderResult(container);
            }
        }
        catch(FileNotFoundException e) {
            logger.debug("readAll fileNotFoundException "+path);
            return new ProviderResult(DataProvider.Error.FAILED);
        }
    }
    public <T>ProviderResult<Collection<T>> writeAll(Collection<T> container, Class bean) throws Exception {
        String path=getPath(bean);
        if(container.size() == 0) {
            logger.warn("writeAll emptyContainer");
            return new ProviderResult(DataProvider.Error.EMPTY_SOURCE);
        }
        CSVWriter writer = new CSVWriter(new FileWriter(path));

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withEscapechar('\0')
                .build();
        beanToCsv.write(container.iterator());
        writer.close();
        logger.info("readAll succeed "+path);
        return new ProviderResult(container);
    }

    public <T>ProviderResult<T> read(T obj, Class bean) throws Exception {
        List<T> list = new ArrayList<>();
        if(readAll(list, bean).error == DataProvider.Error.EMPTY_SOURCE)
            return new ProviderResult(DataProvider.Error.EMPTY_SOURCE);

        int i=list.indexOf(obj);
        if(i==-1)
            return new ProviderResult(DataProvider.Error.BEAN_NOT_FOUND);
        return new ProviderResult(list.get(i));
    }
    public <T>ProviderResult<T> write(T obj, Class bean) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        readAll(list, bean);

        list.remove(obj);
        list.add(obj);
        writeAll(list, obj.getClass());
        return new ProviderResult(obj);
    }
    public <T>ProviderResult<T> remove(T obj, Class bean) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        readAll(list, bean);

        if(!list.remove(obj))
            return new ProviderResult(DataProvider.Error.BEAN_NOT_FOUND);
        writeAll(list, obj.getClass());
        return new ProviderResult(obj);
    }
}
