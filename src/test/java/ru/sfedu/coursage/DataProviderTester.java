package ru.sfedu.coursage;

import org.junit.Assert;
import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.model.dataProviders.AbstractDataProvider;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;
import ru.sfedu.coursage.model.dataProviders.DataProvider;


public abstract class DataProviderTester {
    AbstractDataProvider provider;


    public void operate() {

    }
    public void createSoundData() throws Exception {
        Assert.assertEquals(DataProvider.Error.FAILED, provider.createSoundData(""));

        DataProvider.ProviderResult result = provider.createSoundData(Constants.TEST_WAV_FILE);
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        Assert.assertEquals(false, result.getObject()==null);
        Assert.assertEquals(false, ((SoundData)result.getObject()).getData()==null);
    }
    public void createEmptySoundData() {
        Assert.assertEquals(DataProvider.Error.FAILED, provider.createEmptySoundData(0, 0, 0, 0, ""));
        Assert.assertEquals(DataProvider.Error.FAILED, provider.createEmptySoundData(8, 100, 1, 20050, Constants.DATASOURCE_DIRECTORY+"/CESD_test.wav"));
    }

    public void createCompressorArgs() {

    }
    public void createConverterArgs() {

    }
    public void createEqualizerArgs() {

    }
    public void createMixerArgs() {

    }
    public void createMultiplierArgs() {

    }
    public void createNormalizerArgs() {

    }
    public void createShifterArgs() {

    }

    public void soundDataCRUD() throws Exception {
        DataProvider.ProviderResult result;
        result=provider.createSoundData(Constants.TEST_WAV_FILE);
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        SoundData src=(SoundData)result.getObject();

        result=provider.createSoundDataEmptyClone(src);
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        SoundData dst=(SoundData)result.getObject();

        float[] amps={2, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        result=provider.createEqualizerArgs(amps);
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        EqualizerArgs eq=(EqualizerArgs)result.getObject();

        result=provider.operate(src.getId(), -1, dst.getId(), eq.getId(), eq.getProcessorId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        SoundData res=(SoundData)result.getObject();

        result=provider.removeSoundData(src.getId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        result=provider.removeSoundData(dst.getId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        result=provider.removeArgumentPack(eq.getId(), eq.getProcessorId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());

        result=provider.removeSoundData(src.getId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        result=provider.removeSoundData(res.getId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        result=provider.removeArgumentPack(eq.getId(), eq.getProcessorId());
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());

        result=provider.operate(-1, -1, -1, -1, eq.getProcessorId());
        Assert.assertEquals(DataProvider.Error.BEAN_NOT_FOUND, result.getError());

        result=provider.createSoundDataEmptyClone(new SoundData());
        Assert.assertEquals(DataProvider.Error.EMPTY_SOURCE, result.getError());

    }
}
