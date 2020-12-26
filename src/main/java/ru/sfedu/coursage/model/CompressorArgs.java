package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Compressor-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class CompressorArgs extends ArgumentPack {
    @CsvBindByPosition(position = 5)
    private float power;
    @CsvBindByPosition(position = 6)
    private boolean distortion;

    public CompressorArgs() {
        setProcessorId(ProcessorId.COMPRESSOR);
    }

    public void setPower(float power) {
        this.power = power;
    }
    public float getPower() {
        return power;
    }

    public void setDistortion(boolean distortion) {
        this.distortion = distortion;
    }
    public boolean isDistortion() {
        return distortion;
    }
}
