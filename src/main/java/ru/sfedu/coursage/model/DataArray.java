package ru.sfedu.coursage.model;

public class DataArray {
  public static enum SoundDataType {
      WAVE,
      SPECTUM
  }
  public static enum DataAccessType {
      RIGID,
      CYCLED,
      STRETCHED
  }
  
  private long size;
  private long blockSize;
  private SoundDataType type;
  private DataAccessType accessType;
  private String uri;
  
  
  public DataArray () { };

  
  public void setSize (long newVar) {
    size = newVar;
  }
  public long getSize () {
    return size;
  }

  public void setBlockSize (long newVar) {
    blockSize = newVar;
  }
  public long getBlockSize () {
    return blockSize;
  }

  public void setType (SoundDataType newVar) {
    type = newVar;
  }
  public SoundDataType getType () {
    return type;
  }

  public void setAccessType (DataAccessType newVar) {
    accessType = newVar;
  }
  public DataAccessType getAccessType () {
    return accessType;
  }
  
  public void setUri(String uri) {
      this.uri=uri;
  }
  public String getUri() {
      return uri;
  }
  
  
  public static void readDataArray(DataArray array, String src) throws Exception {
      
  }
  public static void writeDataArray(DataArray array, String src) throws Exception {
      
  }
}
