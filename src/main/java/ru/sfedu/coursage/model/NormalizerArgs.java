package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Normalizer-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class NormalizerArgs extends ArgumentPack {
    @CsvBindByPosition(position = 5)
    private float amp;
    @CsvBindByPosition(position = 6)
    private boolean hard;
    @CsvBindByPosition(position = 7)
    private int period;

    public NormalizerArgs () {
        setProcessorId(ProcessorId.NORMALIZER);
    }

    public void setAmp(float amp) {
        this.amp = amp;
    }
    public float getAmp() {
        return amp;
    }

    public void setHard(boolean hard) {
        this.hard = hard;
    }
    public boolean isHard() {
        return hard;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
    public int getPeriod() {
        return period;
    }
}
