package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Converter-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class ConverterArgs extends ArgumentPack {
    @CsvBindByPosition(position = 5)
    private SoundData.Bitness bitness;
    @CsvBindByPosition(position = 6)
    private int channels;
    @CsvBindByPosition(position = 7)
    private int sampleRate;
    public ConverterArgs () {
        setProcessorId(ProcessorId.CONVERTER);
    }

    public void setBitness(SoundData.Bitness bitness) {
        this.bitness = bitness;
    }
    public SoundData.Bitness getBitness() {
        return bitness;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }
    public int getChannels() {
        return channels;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    public int getSampleRate() {
        return sampleRate;
    }
}
