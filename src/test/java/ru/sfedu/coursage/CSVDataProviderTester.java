package ru.sfedu.coursage;

import org.junit.jupiter.api.Test;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;

public class CSVDataProviderTester extends DataProviderTester {
    CSVDataProviderTester() {
        this.provider=new CSVDataProvider();
    }

    @Test
    public void soundDataCRUD() throws Exception {
        super.soundDataCRUD();
    }
}
