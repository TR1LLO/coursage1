package ru.sfedu.coursage.model.dataProviders;

import ru.sfedu.coursage.Constants;
import ru.sfedu.coursage.model.ArgumentPack;
import ru.sfedu.coursage.model.SoundData;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * class-implementer of JDBC-based API
 */
public class JDBCDataProvider extends AbstractDataProvider {
    private Connection connection;
    public boolean connect() throws SQLException {
        connection = DriverManager.getConnection(Constants.JDBC_ROOT,
                Constants.JDBC_ADMIN,
                Constants.JDBC_PASSWORD);
        return connection!=null;
    }
    public boolean disconnect() throws SQLException {
        connection.close();
        return connection.isClosed();
    }
    public ResultSet executeSQLRequest(String request){
        try{
            PreparedStatement statement = connection.prepareStatement(request);
            statement.executeUpdate();
            statement.close();
            return statement.executeQuery();
        } catch (SQLException e){
            return null;
        }
    }


    //-----------------------------------------INTERFACE-------------------------------------------
    public <T extends ArgumentPack> ProviderResult<Collection<T>> readAllArgumentPacks(Collection<T> container, Class<T> tClass) throws Exception {
        String query="select * from "+tClass.getSimpleName();
        ResultSet result =executeSQLRequest(query);

        return null;
    }
    public <T extends ArgumentPack> ProviderResult<Collection<T>> writeAllArgumentPacks(Collection<T> container, Class<T> tClass) throws Exception {
        String beg="insert into "+tClass.getSimpleName()+" values(", end=")";
        Iterator<T> iterator=container.iterator();
        while(iterator.hasNext())
            executeSQLRequest(beg+end);
        return null;
    }

    public ProviderResult<Collection<SoundData>> readAllSoundData(Collection<SoundData> container) throws Exception {
        return null;
    }
    public ProviderResult<Collection<SoundData>> writeAllSoundData(Collection<SoundData> container) throws Exception {
        return null;
    }

    public ProviderResult<SoundData> extReadSoundData(SoundData obj) throws Exception {
        return null;
    }
    public ProviderResult<SoundData> extWriteSoundData(SoundData obj) throws Exception {
        return null;
    }
    public ProviderResult<SoundData> extRemoveSoundData(SoundData obj) throws Exception {
        return null;
    }

    public ProviderResult<ArgumentPack> extReadArgumentPack(ArgumentPack obj, Class tClass) throws Exception {
        return null;
    }
    public ProviderResult<ArgumentPack> extWriteArgumentPack(ArgumentPack obj, Class tClass) throws Exception {
        return null;
    }
    public ProviderResult<ArgumentPack> extRemoveArgumentPack(ArgumentPack obj, Class tClass) throws Exception {
        return null;
    }
}
