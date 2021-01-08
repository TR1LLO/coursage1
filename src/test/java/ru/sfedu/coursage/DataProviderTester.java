package ru.sfedu.coursage;

import org.junit.Assert;
import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.model.dataProviders.AbstractDataProvider;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;
import ru.sfedu.coursage.model.dataProviders.DataProvider;


public abstract class DataProviderTester {
    AbstractDataProvider provider;

    public void createCompressorArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createCompressorArgs(
                Constants.TEST_COMPRESSOR_POWER,
                Constants.TEST_COMPRESSOR_DISTORTION).getError());
    }
    public void createConverterArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createConverterArgs(
                Constants.TEST_CONVERTER_BITNESS,
                Constants.TEST_CONVERTER_CHANNELS,
                Constants.TEST_CONVERTER_SAMPLERATE).getError());
    }
    public void createEqualizerArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createEqualizerArgs(
                Constants.TEST_EQUALIZER_AMPS).getError());
    }
    public void createMixerArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createMixerArgs(
                Constants.TEST_MIXER_CHANNELMIXING,
                Constants.TEST_MIXER_COVER).getError());
    }
    public void createMultiplierArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createMultiplierArgs(
                Constants.TEST_MULTIPLIER_AVERAGE,
                Constants.TEST_MULTIPLIER_COUNT).getError());
    }
    public void createNormalizerArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createNormalizerArgs(
                Constants.TEST_NORMALIZER_AMP,
                Constants.TEST_NORMALIZER_HARD,
                Constants.TEST_NORMALIZER_PERIOD).getError());
    }
    public void createShifterArgs() throws Exception {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createShifterArgs(
                Constants.TEST_SHIFTER_FREQUENCY,
                Constants.TEST_SHIFTER_STEP,
                Constants.TEST_SHIFTER_RADIUS).getError());
    }


    public void createSoundDataFail() throws Exception {
        Assert.assertEquals(DataProvider.Error.FAILED, provider.createSoundData(Constants.TEST_INVALID_FILE).getError());
    }
    public void createSoundData() throws Exception {
        DataProvider.ProviderResult result = provider.createSoundData(Constants.TEST_WAV_FILE);
        Assert.assertEquals(DataProvider.Error.SUCCESS, result.getError());
        Assert.assertEquals(false, result.getObject()==null);
        Assert.assertEquals(false, ((SoundData)result.getObject()).getData()==null);
    }
    public void createEmptySoundData() {
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.createEmptySoundData(
                Constants.TEST_SOUNDDATA_BITS,
                Constants.TEST_SOUNDDATA_SIZE,
                Constants.TEST_SOUNDDATA_CHANNELS,
                Constants.TEST_SOUNDDATA_SAMPLERATE,
                Constants.TEST_WAV_FILE_DST).getError());
    }
    public void operateFail() throws Exception {
        Assert.assertEquals(DataProvider.Error.FAILED, provider.operate(-1, -1, -1, -1, null).getError());
    }
    public void operate() throws Exception {
        SoundData src = provider.createSoundData(Constants.TEST_WAV_FILE).getObject();
        SoundData flt = provider.createSoundDataEmptyClone(src).getObject();
        SoundData dst = provider.createSoundDataEmptyClone(src).getObject();

        ArgumentPack args = provider.createEqualizerArgs(Constants.TEST_EQUALIZER_AMPS).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.operate(
                src.getId(), flt.getId(), dst.getId(),
                args.getId(), args.getProcessorId()).getError());
    }


    public void readSoundDataFail() throws Exception {
        Assert.assertEquals(DataProvider.Error.BEAN_NOT_FOUND, provider.readSoundData(
                provider.nextSoundDataId()).getError());
    }
    public void readSoundData() throws Exception {
        SoundData data = provider.createSoundData(Constants.TEST_WAV_FILE).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.readSoundData(
                data.getId()).getError());
    }
    public void writeSoundData() throws Exception {
        SoundData data = provider.createSoundData(Constants.TEST_WAV_FILE).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.writeSoundData(
                data).getError());
    }
    public void removeSoundDataFail() throws Exception {
        Assert.assertEquals(DataProvider.Error.BEAN_NOT_FOUND, provider.removeSoundData(
                provider.nextSoundDataId()).getError());
    }
    public void removeSoundData() throws Exception {
        SoundData data = provider.createSoundData(Constants.TEST_WAV_FILE).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.removeSoundData(
                data.getId()).getError());
    }


    public void readArgumentPackFail() throws Exception {
        Assert.assertEquals(DataProvider.Error.BEAN_NOT_FOUND, provider.readArgumentPack(
                provider.nextArgumentPackId(ArgumentPack.ProcessorId.EQUALIZER),
                ArgumentPack.ProcessorId.EQUALIZER).getError());
    }
    public void readArgumentPack() throws Exception {
        ArgumentPack pack = provider.createEqualizerArgs(Constants.TEST_EQUALIZER_AMPS).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.readArgumentPack(
                pack.getId(),
                pack.getProcessorId()).getError());
    }
    public void writeArgumentPack() throws Exception {
        ArgumentPack pack = provider.createEqualizerArgs(Constants.TEST_EQUALIZER_AMPS).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.writeArgumentPack(
                pack).getError());
    }
    public void removeArgumentPackFail() throws Exception {
        Assert.assertEquals(DataProvider.Error.BEAN_NOT_FOUND, provider.removeArgumentPack(
                -1, ArgumentPack.ProcessorId.EQUALIZER).getError());
    }
    public void removeArgumentPack() throws Exception {
        ArgumentPack pack = provider.createEqualizerArgs(Constants.TEST_EQUALIZER_AMPS).getObject();
        Assert.assertEquals(DataProvider.Error.SUCCESS, provider.removeArgumentPack(
                pack.getId(),
                pack.getProcessorId()).getError());
    }
}
