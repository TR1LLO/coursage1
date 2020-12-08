package ru.sfedu.coursage;

import ru.sfedu.coursage.model.SoundData;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;
import ru.sfedu.coursage.model.dataProviders.DataProvider;
import org.junit.jupiter.api.*;

import java.util.Random;

public class DataProviderTester {
    public static int iterations = 24;
    public static SoundData randomizeSoundData(SoundData obj) {
        Random r = new Random();
        obj.setSampleRate(22050 + r.nextInt()%22050);
        obj.setChannels(r.nextInt()%10);
        obj.setBitness(SoundData.Bitness.values()[Math.abs(r.nextInt())% SoundData.Bitness.values().length]);
        obj.setId(r.nextInt());
        return obj;
    }


    public static void soundDataTest(DataProvider provider) throws Exception {
        for(int i=0; i<iterations; i++)
        {
            SoundData data1 = randomizeSoundData(new SoundData());
            data1.setId(i);
            Assertions.assertEquals(DataProvider.DatabaseError.SUCCESS, provider.write(data1));

            SoundData data2 = new SoundData();
            data2.setId(data1.getId());
            Assertions.assertEquals(DataProvider.DatabaseError.SUCCESS, provider.read(data2));
            Assertions.assertEquals(data1, data2);

            Assertions.assertEquals(DataProvider.DatabaseError.SUCCESS, provider.remove(data1));
            Assertions.assertEquals(DataProvider.DatabaseError.BEAN_NOT_FOUND, provider.read(data1));
        }
    }

    @Test
    public void main() throws Exception {
        soundDataTest(new CSVDataProvider());
    }
}
