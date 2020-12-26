package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Multiplier-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class MultiplierArgs extends ArgumentPack {
    @CsvBindByPosition(position = 5)
    private boolean average;
    @CsvBindByPosition(position = 6)
    private int count;
    public MultiplierArgs() {
        setProcessorId(ProcessorId.MULTIPLIER);
    }

    public void setAverage(boolean average) {
        this.average = average;
    }
    public boolean isAverage() {
        return average;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
}
