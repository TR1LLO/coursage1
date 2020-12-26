package ru.sfedu.coursage.model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Converter-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class ConverterArgs extends ArgumentPack {
    public ConverterArgs () {
        setProcessorId(ProcessorId.CONVERTER);
    }
}
