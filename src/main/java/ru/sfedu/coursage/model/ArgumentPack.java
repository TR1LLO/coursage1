package ru.sfedu.coursage.model;
abstract public class ArgumentPack {
    public static enum ErrorCode {
        SUCCESS
    }
    public static enum WarnCode {
        
    }

  //
  // Fields
  //

  private long processorId;
  private boolean completed;
  private ErrorCode errorCode;
  private WarnCode warnCode;
  
  //
  // Constructors
  //
  public ArgumentPack () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of processorId
   * @param newVar the new value of processorId
   */
  public void setProcessorId (long newVar) {
    processorId = newVar;
  }

  /**
   * Get the value of processorId
   * @return the value of processorId
   */
  public long getProcessorId () {
    return processorId;
  }

  /**
   * Set the value of completed
   * @param newVar the new value of completed
   */
  public void setCompleted (boolean newVar) {
    completed = newVar;
  }

  /**
   * Get the value of completed
   * @return the value of completed
   */
  public boolean getCompleted () {
    return completed;
  }

  /**
   * Set the value of errorCode
   * @param newVar the new value of errorCode
   */
  public void setErrorCode (ErrorCode newVar) {
    errorCode = newVar;
  }

  /**
   * Get the value of errorCode
   * @return the value of errorCode
   */
  public ErrorCode getErrorCode () {
    return errorCode;
  }

  /**
   * Set the value of warnCode
   * @param newVar the new value of warnCode
   */
  public void setWarnCode (WarnCode newVar) {
    warnCode = newVar;
  }

  /**
   * Get the value of warnCode
   * @return the value of warnCode
   */
  public WarnCode getWarnCode () {
    return warnCode;
  }

  //
  // Other methods
  //

}
