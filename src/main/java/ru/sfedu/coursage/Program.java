package ru.sfedu.coursage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;
import ru.sfedu.coursage.model.SoundData;

public class Program {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws Exception {

        CSVDataProvider dataProvider = new CSVDataProvider();

        SoundData data = new SoundData();
        data.setId(2);
        data.setBitness(SoundData.Bitness.SHORT_16);
        data.setChannels(-1);
        data.setSampleRate(44100);
        data.setSourceFile("no");

    }
}
