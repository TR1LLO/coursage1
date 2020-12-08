package ru.sfedu.coursage.model;

public class MainProcessorArgs extends ArgumentPack {
  public static enum CompletionInstruction {
      
  }
    
  private SoundData src;
  private SoundData filter;
  private CompletionInstruction completionInstruction;
  private ArgumentPack processorArgs;
  
  public MainProcessorArgs () { };
  
  public void setSrc (SoundData newVar) {
    src = newVar;
  }
  public SoundData getSrc () {
    return src;
  }

  public void setFilter (SoundData newVar) {
    filter = newVar;
  }
  public SoundData getFilter () {
    return filter;
  }

  public void setCompletionInstruction (CompletionInstruction newVar) {
    completionInstruction = newVar;
  }
  public CompletionInstruction getCompletionInstruction () {
    return completionInstruction;
  }

  public void setProcessorArgs (ArgumentPack newVar) {
    processorArgs = newVar;
  }

  public ArgumentPack getProcessorArgs () {
    return processorArgs;
  }

  public static MainProcessorArgs parse(String properties)
  {
      return null;
  }


}
