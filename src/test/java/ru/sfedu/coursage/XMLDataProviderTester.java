package ru.sfedu.coursage;

import org.junit.jupiter.api.Test;
import ru.sfedu.coursage.model.dataProviders.XMLDataProvider;

public class XMLDataProviderTester extends DataProviderTester {
    XMLDataProviderTester() {
        this.provider=new XMLDataProvider();
    }

    @Test
    public void soundDataCRUD() throws Exception {
        super.soundDataCRUD();
    }
}
