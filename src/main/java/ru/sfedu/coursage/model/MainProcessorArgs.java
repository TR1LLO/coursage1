package ru.sfedu.coursage.model;

/**
 * MainProcessor-parameterizing package
 */
public class MainProcessorArgs extends ArgumentPack {
    private SoundData src, filter, dst;
    private ArgumentPack processorArgs;
  
    public MainProcessorArgs () {
    }
  
    public void setSrc (SoundData newVar) {
    src = newVar;
  }
    public SoundData getSrc () {
    return src;
  }

    public void setDst(SoundData dst) {
        this.dst = dst;
    }
    public SoundData getDst() {
        return dst;
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

    @Override
    public String toString() {
      return "MPArgs ["+
              "src="+src.getId()+", "+
              "filter="+(filter==null?"null":filter.getId())+", "+
              "dst="+dst.getId()+", "+
              "error="+getErrorCode()+", "+
              "warn="+getWarnCode()+", " +
              processorArgs.toString()+"]<-"+super.toString();
  }
}
