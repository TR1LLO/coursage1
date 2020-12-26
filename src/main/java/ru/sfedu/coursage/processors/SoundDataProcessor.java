package ru.sfedu.coursage.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.coursage.model.SoundData;

/**
 * abstract class of specific sound processing methods
 */
abstract public class SoundDataProcessor {
    public static Logger logger = LogManager.getLogger();

    protected SoundData src, filter, dst;
    public SoundDataProcessor (SoundData src, SoundData filter, SoundData dst) {
        this.src=src;
        this.filter=filter;
        this.dst=dst;
    }

    /**
     * run processing
     * @return new SoundData object with processed content
     */
    abstract public SoundData operate();
}
