package ru.sfedu.coursage.model;


import com.opencsv.bean.CsvBindByPosition;
import ru.sfedu.coursage.processors.*;

import java.util.Objects;

/**
 * abstract bean-package of parameters related to specific SoundProcessor
 */
abstract public class ArgumentPack {
    /**
     * significant bean states
     */
    public enum ErrorCode {
        SUCCESS,
        INDEFINITE,
        PARSING_FAILED,
        NO_SUCH_PROCESSOR
    }
    /**
     * uncritical processing information
     */
    public enum WarnCode {
        INDEFINITE
    }
    /**
     * SoundProcessor-ArgumentPack identifiers and attendant information
     */
    public enum ProcessorId {
        COMPRESSOR( CompressorArgs.class,   Compressor.class),
        CONVERTER(  ConverterArgs.class,    Converter.class),
        EQUALIZER(  EqualizerArgs.class,    Equalizer.class),
        MIXER(      MixerArgs.class,        Mixer.class),
        MULTIPLIER( MultiplierArgs.class,   Multiplier.class),
        NORMALIZER( NormalizerArgs.class,   Normalizer.class),
        SHIFTER(    ShifterArgs.class,      Shifter.class);

        private Class<? extends ArgumentPack> packageClass;
        private Class<? extends SoundDataProcessor> processorClass;
        ProcessorId(Class<? extends ArgumentPack> packageClass,
                    Class<? extends SoundDataProcessor> processorClass) {
            this.packageClass=packageClass;
            this.processorClass=processorClass;
        }
        public Class<? extends ArgumentPack> getPackageClass() {
            return packageClass;
        }
        public Class<? extends SoundDataProcessor> getProcessorClass() {
            return processorClass;
        }

        public ArgumentPack newPackage() {
            switch (this) {
                case COMPRESSOR:    return new CompressorArgs();
                case CONVERTER:     return new ConverterArgs();
                case EQUALIZER:     return new EqualizerArgs();
                case NORMALIZER:    return new NormalizerArgs();
                case MULTIPLIER:    return new MultiplierArgs();
                case SHIFTER:       return new ShifterArgs();
                case MIXER:         return new MixerArgs();
                default: return null;
            }
        }
    }

    @CsvBindByPosition(position = 0)
    private long id;
    @CsvBindByPosition(position = 1)
    private ProcessorId processorId;
    @CsvBindByPosition(position = 2)
    private boolean completed;
    @CsvBindByPosition(position = 3)
    private ErrorCode errorCode;
    @CsvBindByPosition(position = 4)
    private WarnCode warnCode;
    @CsvBindByPosition(position = 5)
    private float magnitude;
    public ArgumentPack () {
  }

    public void setId(long id) {
        this.id=id;
    }
    public long getId() {
      return id;
    }

    public void setProcessorId (ProcessorId newVar) {
      processorId = newVar;
    }
    public ProcessorId getProcessorId () {
      return processorId;
    }

    public void setCompleted (boolean newVar) {
      completed = newVar;
    }
    public boolean getCompleted () {
      return completed;
    }

    public void setErrorCode (ErrorCode newVar) {
      errorCode = newVar;
    }
    public ErrorCode getErrorCode () {
      return errorCode;
    }

    public void setWarnCode (WarnCode newVar) {
      warnCode = newVar;
    }
    public WarnCode getWarnCode () {
      return warnCode;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }
    public float getMagnitude() {
        return magnitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArgumentPack that = (ArgumentPack) o;
        return id == that.id;
  }
    @Override
    public int hashCode() {
        return Objects.hash(id);
  }
    @Override
    public String toString() {
        return "APack ["+
                "id="+id+", "+
                "proc="+processorId+"]";
    }
}
