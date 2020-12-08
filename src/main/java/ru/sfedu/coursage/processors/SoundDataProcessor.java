package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.SoundData;
import ru.sfedu.coursage.model.ArgumentPack;

abstract public class SoundDataProcessor {
  private long processorId;
  
  public SoundDataProcessor () { };
  
  
  public void setProcessorId (long newVar) {
    processorId = newVar;
  }
  public long getProcessorId () {
    return processorId;
  }

  
  public SoundData operate(SoundData src, SoundData filter, ArgumentPack args)
  {
      return null;
  }


  public static ArgumentPack parse(String properties)
  {
      return null;
  }


  protected SoundData operateWave(SoundData src, SoundData filter, ArgumentPack args)
  {
      return null;
  }


  protected SoundData operateSpectre(SoundData src, SoundData filter, ArgumentPack args)
  {
      return null;
  }


}
