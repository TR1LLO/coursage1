package ru.sfedu.coursage.model;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Mixer-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class MixerArgs extends ArgumentPack {
    public MixerArgs () {
        setProcessorId(ProcessorId.MIXER);
    }
}
