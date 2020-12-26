package ru.sfedu.coursage.model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Multiplier-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class MultiplierArgs extends ArgumentPack {
    public MultiplierArgs() {
        setProcessorId(ProcessorId.MULTIPLIER);
    }
}
