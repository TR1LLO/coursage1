package ru.sfedu.coursage.model.dataProviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.Constants;
import ru.sfedu.coursage.model.CopyingInterface;

import java.io.*;
import java.util.*;

public class CSVDataProvider implements DataProvider {
    public static Logger logger = LogManager.getLogger(CSVDataProvider.class);

    /**
     * @param bean
     * @return full path to related to bean csv file
     */
    public static String getPath(Class bean) {
        return Constants.DATADIRECTORY+bean.toString()+".csv";
    }

    public <T>DatabaseError readAll(Collection<T> container, Class bean) throws Exception {
        String path=getPath(bean);
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            List<T> list = new CsvToBeanBuilder<T>(reader)
                    .withType(bean)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
            reader.close();
            if(list.size()==0) {
                logger.warn("readAll emptyFile "+path);
                return DatabaseError.EMPTY_SOURCE;
            }
            else {
                container.addAll(list);
                logger.info("readAll succeed "+path);
                return DatabaseError.SUCCESS;
            }
        }
        catch(FileNotFoundException e) {
            logger.debug("readAll fileNotFoundException "+path);
            return DatabaseError.FAILED;
        }
    }
    public <T>DatabaseError writeAll(Collection<T> container, Class bean) throws Exception {
        String path=getPath(bean);
        if(container.size() == 0) {
            logger.warn("writeAll emptyContainer");
            return DatabaseError.EMPTY_SOURCE;
        }
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path));

            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .build();
            beanToCsv.write(container.iterator());
            writer.close();
            logger.info("readAll succeed "+path);
            return DatabaseError.SUCCESS;
        }
        catch (IOException e) {
            logger.debug("readAll failed "+path+", " + e);
            return DatabaseError.FAILED;
        }
    }

    public <T extends CopyingInterface>DatabaseError read(T obj) throws Exception {
        List<T> list = new ArrayList<>();
        if(readAll(list, obj.getClass())==DatabaseError.EMPTY_SOURCE)
            return DatabaseError.EMPTY_SOURCE;

        int i=list.indexOf(obj);
        if(i==-1)
            return DatabaseError.BEAN_NOT_FOUND;
        obj.copyFields(list.get(i));
        return DatabaseError.SUCCESS;
    }
    public <T>DatabaseError write(T obj) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        readAll(list, obj.getClass());

        list.remove(obj);
        list.add(obj);
        writeAll(list, obj.getClass());
        return DatabaseError.SUCCESS;
    }
    public <T>DatabaseError remove(T obj) throws Exception {
        ArrayList<T> list = new ArrayList<>();
        readAll(list, obj.getClass());

        if(!list.remove(obj))
            return DatabaseError.BEAN_NOT_FOUND;
        writeAll(list, obj.getClass());
        return DatabaseError.SUCCESS;
    }
}
