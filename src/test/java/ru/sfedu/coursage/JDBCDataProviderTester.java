package ru.sfedu.coursage;

import org.junit.jupiter.api.Test;
import ru.sfedu.coursage.model.SoundData;
import ru.sfedu.coursage.model.dataProviders.JDBCDataProvider;

import java.util.ArrayList;

public class JDBCDataProviderTester extends DataProviderTester {
    JDBCDataProviderTester() throws Exception {
        this.provider=new JDBCDataProvider();
    }

    @Test
    public void soundDataCRUD() throws Exception {
        ArrayList<SoundData> list = new ArrayList<>();
        provider.readAllSoundData(list);
        for(SoundData data: list)
            System.out.println(data);
    }
}
