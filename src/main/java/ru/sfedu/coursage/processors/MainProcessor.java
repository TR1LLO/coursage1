package ru.sfedu.coursage.processors;
import ru.sfedu.coursage.model.ArgumentPack.ErrorCode;
import ru.sfedu.coursage.model.MainProcessorArgs;


public class MainProcessor {
  public MainProcessor () { };
  
  public ErrorCode process(MainProcessorArgs args)
  {
      return ErrorCode.SUCCESS;
  }
}
