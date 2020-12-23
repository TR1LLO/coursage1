package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;

import java.io.Serializable;

/**
 * Equalizer-parameterizing bean-package
 */
public class EqualizerArgs extends ArgumentPack implements Serializable {
  @CsvIgnore
  private int count=10;
  @CsvBindByPosition(position = 7)
  private float[] amplitudes = new float[count];
  public EqualizerArgs () {
      setProcessorId(ProcessorId.EQUALIZER);
  }

  public float[] getAmplitudes() {
      return amplitudes;
  }
  public void setAmplitudes(float[] amplitudes) {
    this.amplitudes = amplitudes;
  }

  public int getCount() {
    return count;
  }

  @Override
  public String toString() {
    String arr=""+amplitudes[0];
    for(int i=1; i<count; i++)
        arr+=", "+amplitudes[i];
    return "EqualizerArgs ["+count+": "+arr+ "]<-"+super.toString();
  }
}
