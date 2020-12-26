package ru.sfedu.coursage.model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Normalizer-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class NormalizerArgs extends ArgumentPack {
    public NormalizerArgs () {
        setProcessorId(ProcessorId.NORMALIZER);
    }
}
