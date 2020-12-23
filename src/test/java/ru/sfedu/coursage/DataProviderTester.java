package ru.sfedu.coursage;

import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;
import ru.sfedu.coursage.model.dataProviders.AbstractDataProvider;
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

    public static void soundDataTest(AbstractDataProvider provider) throws Exception {
        for(int i=0; i<iterations; i++)
        {
            SoundData data1 = randomizeSoundData(new SoundData());
            data1.setId(i);
            Assertions.assertEquals(DataProvider.Error.SUCCESS, provider.write(data1, SoundData.class).getError());

            DataProvider.ProviderResult temp = provider.read(data1, SoundData.class);
            Assertions.assertEquals(DataProvider.Error.SUCCESS, temp.getError());
            Assertions.assertEquals(data1, temp.getObject());

            Assertions.assertEquals(DataProvider.Error.SUCCESS, provider.remove(data1, SoundData.class).getError());
            Assertions.assertEquals(DataProvider.Error.BEAN_NOT_FOUND, provider.read(data1, SoundData.class).getError());
        }
    }
    public static void dataArrayTest(AbstractDataProvider provider) throws Exception {
        SoundData s1=DataArray.readWavSoundData("C:\\Users\\Argon\\Desktop\\oof\\wav\\_eng.wav");

        s1.setSourceFile("C:\\Users\\Argon\\Desktop\\oof\\wav\\_eng2.wav");
        s1.getData().sampleRate/=2;
        s1.getData().byteRate/=2;
        DataArray.writeWavDataArray(s1);
    }

    public static void mainArgumentPackTest(AbstractDataProvider provider) throws Exception {
        SoundData src = provider.createSoundData("C:/Users/Argon/Desktop/oof/wav/_eng2.wav").getObject();
        String properties="id=64 "+
                "result="+"C:/Users/Argon/Desktop/oof/wav/_eng3.wav"+" "+
                "processor="+ArgumentPack.ProcessorId.EQUALIZER+" "+
                "src="+src.getId()+" "+
                "filter=-1 "+
                "amps[3]=4";

        MainProcessorArgs args = provider.createMainProcessorArgs(properties);
        System.out.println(args);
    }
    @Test
    public void main() throws Exception {
        mainArgumentPackTest(new CSVDataProvider());
    }
}
