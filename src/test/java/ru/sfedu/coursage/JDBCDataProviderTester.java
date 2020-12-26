package ru.sfedu.coursage;

import org.junit.jupiter.api.Test;
import ru.sfedu.coursage.model.dataProviders.JDBCDataProvider;

public class JDBCDataProviderTester extends DataProviderTester {
    JDBCDataProviderTester() throws Exception {
        this.provider=new JDBCDataProvider();
    }

    @Test
    public void soundDataCRUD() throws Exception {
        super.soundDataCRUD();
    }
}
