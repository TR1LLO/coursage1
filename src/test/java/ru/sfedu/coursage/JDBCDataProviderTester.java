package ru.sfedu.coursage;

import org.junit.jupiter.api.Test;
import ru.sfedu.coursage.model.dataProviders.JDBCDataProvider;

public class JDBCDataProviderTester extends DataProviderTester {
    JDBCDataProviderTester() {
        this.provider=new JDBCDataProvider();
    }

    public void soundDataCRUD() throws Exception {
        super.soundDataCRUD();
    }
}
