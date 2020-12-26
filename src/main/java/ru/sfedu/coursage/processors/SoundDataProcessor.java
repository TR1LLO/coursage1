package ru.sfedu.coursage.processors;

import ru.sfedu.coursage.model.SoundData;

/**
 * abstract class of specific sound processing methods
 */
abstract public class SoundDataProcessor {
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

    /**
     * get value after "property="
     * @param properties string with properties
     * @param property string to find
     * @return substring next to "property=" or null if property not found
     */
    protected static String getProperty(String properties, String property) {
    int i=properties.indexOf(property);
    if(i==-1)
      return null;
    String temp = properties.substring(i+property.length()+1);
    i=temp.indexOf(' ');
    i=i==-1?temp.length():i;
    return temp.substring(0, i);
  }
}
