package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Shifter-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class ShifterArgs extends ArgumentPack {
    @CsvBindByPosition(position = 5)
    private float frequency = 0;
    @CsvBindByPosition(position = 6)
    private float step = 1;
    @CsvBindByPosition(position = 7)
    private int radius = 24;
    public ShifterArgs() {
        setProcessorId(ProcessorId.SHIFTER);
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
    public float getFrequency() {
        return frequency;
    }

    public void setStep(float step) {
        this.step = step;
    }
    public float getStep() {
        return step;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getRadius() {
        return radius;
    }
}
