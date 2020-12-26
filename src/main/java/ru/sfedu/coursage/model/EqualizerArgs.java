package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import java.io.Serializable;

/**
 * Equalizer-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class EqualizerArgs extends ArgumentPack implements Serializable {
    @CsvBindByPosition(position = 5)
    private float amp0;
    @CsvBindByPosition(position = 6)
    private float amp1;
    @CsvBindByPosition(position = 7)
    private float amp2;
    @CsvBindByPosition(position = 8)
    private float amp3;
    @CsvBindByPosition(position = 9)
    private float amp4;
    @CsvBindByPosition(position = 10)
    private float amp5;
    @CsvBindByPosition(position = 11)
    private float amp6;
    @CsvBindByPosition(position = 12)
    private float amp7;
    @CsvBindByPosition(position = 13)
    private float amp8;
    @CsvBindByPosition(position = 14)
    private float amp9;

    @CsvIgnore
    @Transient
    private int count = 10;
    @CsvIgnore
    @Transient
    private float[] amps = new float[count];
    public EqualizerArgs () {
        setProcessorId(ProcessorId.EQUALIZER);
    }

    public float getAmp0() {
        return amp0=amps[0];
    }
    public float getAmp1() {
        return amp1=amps[1];
    }
    public float getAmp2() {
        return amp2=amps[2];
    }
    public float getAmp3() {
        return amp3=amps[3];
    }
    public float getAmp4() {
        return amp4=amps[4];
    }
    public float getAmp5() {
        return amp5=amps[5];
    }
    public float getAmp6() {
        return amp6=amps[6];
    }
    public float getAmp7() {
        return amp7=amps[7];
    }
    public float getAmp8() {
        return amp8=amps[8];
    }
    public float getAmp9() {
        return amp9=amps[9];
    }

    public void setAmp0(float amp0) {
        this.amp0 = amp0;
        amps[0]=amp0;
    }
    public void setAmp1(float amp1) {
        this.amp1 = amp1;
        amps[1]=amp1;
    }
    public void setAmp2(float amp2) {
        this.amp2 = amp2;
        amps[2]=amp2;
    }
    public void setAmp3(float amp3) {
        this.amp3 = amp3;
        amps[3]=amp3;
    }
    public void setAmp4(float amp4) {
        this.amp4 = amp4;
        amps[4]=amp4;
    }
    public void setAmp5(float amp5) {
        this.amp5 = amp5;
        amps[5]=amp5;
    }
    public void setAmp6(float amp6) {
        this.amp6 = amp6;
        amps[6]=amp6;
    }
    public void setAmp7(float amp7) {
        this.amp7 = amp7;
        amps[7]=amp7;
    }
    public void setAmp8(float amp8) {
        this.amp8 = amp8;
        amps[8]=amp8;
    }
    public void setAmp9(float amp9) {
        this.amp9 = amp9;
        amps[9]=amp9;
    }

    public float[] getAmps() {
        amps[0]=amp0;
        amps[1]=amp1;
        amps[2]=amp2;
        amps[3]=amp3;
        amps[4]=amp4;
        amps[5]=amp5;
        amps[6]=amp6;
        amps[7]=amp7;
        amps[8]=amp8;
        amps[9]=amp9;
        return amps;
    }
    public void setAmps(float[] amps) {
        this.amps=amps;
        setAmp0(amps[0]);
        setAmp1(amps[1]);
        setAmp2(amps[2]);
        setAmp3(amps[3]);
        setAmp4(amps[4]);
        setAmp5(amps[5]);
        setAmp6(amps[6]);
        setAmp7(amps[7]);
        setAmp8(amps[8]);
        setAmp9(amps[9]);
    }
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "EqualizerArgs ["+
                amp0+", "+amp1+", "+
                amp2+", "+amp3+", "+
                amp4+", "+amp5+", "+
                amp6+", "+amp7+", "+
                amp8+", "+amp9+"]<-"+super.toString();
    }
}
