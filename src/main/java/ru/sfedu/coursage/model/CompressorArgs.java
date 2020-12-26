package ru.sfedu.coursage.model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Compressor-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class CompressorArgs extends ArgumentPack {
    public CompressorArgs() {
        setProcessorId(ProcessorId.COMPRESSOR);
    }
}
