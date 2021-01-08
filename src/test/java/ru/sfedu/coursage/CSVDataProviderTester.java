package ru.sfedu.coursage;

import org.junit.jupiter.api.Test;
import ru.sfedu.coursage.model.dataProviders.CSVDataProvider;

public class CSVDataProviderTester extends DataProviderTester {
    CSVDataProviderTester() {
        this.provider=new CSVDataProvider();
    }

    @Test
    public void createCompressorArgs() throws Exception {
        super.createCompressorArgs();
    }
    @Test
    public void createConverterArgs() throws Exception {
        super.createConverterArgs();
    }
    @Test
    public void createEqualizerArgs() throws Exception {
        super.createEqualizerArgs();
    }
    @Test
    public void createMixerArgs() throws Exception {
        super.createMixerArgs();
    }
    @Test
    public void createMultiplierArgs() throws Exception {
        super.createMultiplierArgs();
    }
    @Test
    public void createNormalizerArgs() throws Exception {
        super.createNormalizerArgs();
    }
    @Test
    public void createShifterArgs() throws Exception {
        super.createShifterArgs();
    }


    @Test
    public void createSoundDataFail() throws Exception {
        super.createSoundDataFail();
    }
    @Test
    public void createSoundData() throws Exception {
        super.createSoundData();
    }
    @Test
    public void createEmptySoundData() {
        super.createEmptySoundData();
    }
    @Test
    public void operateFail() throws Exception {
        super.operateFail();
    }
    @Test
    public void operate() throws Exception {
        super.operate();
    }


    @Test
    public void readSoundDataFail() throws Exception {
        super.readSoundDataFail();
    }
    @Test
    public void readSoundData() throws Exception {
        super.readSoundData();
    }
    @Test
    public void writeSoundData() throws Exception {
        super.writeSoundData();
    }
    @Test
    public void removeSoundDataFail() throws Exception {
        super.removeSoundDataFail();
    }
    @Test
    public void removeSoundData() throws Exception {
        super.removeSoundData();
    }


    @Test
    public void readArgumentPackFail() throws Exception {
        super.readArgumentPackFail();
    }
    @Test
    public void readArgumentPack() throws Exception {
        super.readArgumentPack();
    }
    @Test
    public void writeArgumentPack() throws Exception {
        super.writeArgumentPack();
    }
    @Test
    public void removeArgumentPackFail() throws Exception {
        super.removeArgumentPackFail();
    }
    @Test
    public void removeArgumentPack() throws Exception {
        super.removeArgumentPack();
    }
}
