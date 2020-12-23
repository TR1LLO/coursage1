package ru.sfedu.coursage.model;

import java.io.Serializable;
import java.util.Objects;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;

/**
 * bean-handler of sound data
 */
public class SoundData implements Serializable {
  public enum Bitness {
      CHAR_8,
      SHORT_16,
      INT_24,
      FLOAT
  }

  @CsvBindByPosition(position = 0)
  private long id;
  @CsvBindByPosition(position = 1)
  private Bitness bitness;
  @CsvBindByPosition(position = 2)
  private int channels;
  @CsvBindByPosition(position = 3)
  private int sampleRate;
  @CsvBindByPosition(position = 4)
  private String sourceFile;
  @CsvIgnore
  private DataArray data;
  
  
  public SoundData () { };
  public SoundData(long id, Bitness bitness, char channels, int sampleRate, DataArray data) {
      this.id=id;
      this.bitness=bitness;
      this.channels=channels;
      this.sampleRate=sampleRate;
      this.data=data;
  }

  public void setId (long newVar) {
    id = newVar;
  }
  public long getId () {
    return id;
  }
  
  public void setBitness (Bitness newVar) {
    bitness = newVar;
  }
  public Bitness getBitness () {
    return bitness;
  }

  public void setChannels (int newVar) {
    channels = newVar;
  }
  public int getChannels () {
    return channels;
  }

  public void setSampleRate (int newVar) {
    sampleRate = newVar;
  }
  public int getSampleRate () {
    return sampleRate;
  }

  public void setSourceFile(String sourceFile) {
      this.sourceFile=sourceFile;
  }
  public String getSourceFile() {
      return sourceFile;
  }

  public void setData (DataArray newVar) {
    data = newVar;
  }
  public DataArray getData () {
    return data;
  }

  @Override
  public String toString() {
      return "SoundData ["
              + "id=" + id 
              + ", bt=" + bitness
              + ", ch=" + channels
              + ", sr=" + sampleRate
              + ", sf=" + sourceFile
              + "]";
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SoundData data = (SoundData) o;
    return id == data.id;
  }
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
