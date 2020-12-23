package ru.sfedu.coursage.model;

/**
 * MainProcessor-parameterizing package
 */
public class MainProcessorArgs extends ArgumentPack {
    private SoundData src;
    private SoundData filter;
    private ArgumentPack processorArgs;
    private String resultFile;
  
    public MainProcessorArgs () {
    }
  
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

    public void setProcessorArgs (ArgumentPack newVar) {
    processorArgs = newVar;
  }
    public ArgumentPack getProcessorArgs () {
    return processorArgs;
  }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }
    public String getResultFile() {
        return resultFile;
    }

    @Override
    public String toString() {
      return "MPArgs ["+
              "src="+src.getId()+", "+
              "filter="+(filter==null?"null":filter.getId())+", "+
              "result="+resultFile+", "+
              "error="+getErrorCode()+", "+
              "warn="+getWarnCode()+", " +
              processorArgs.toString()+"]<-"+super.toString();
  }
}
