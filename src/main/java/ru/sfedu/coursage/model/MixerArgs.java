package ru.sfedu.coursage.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Root;

/**
 * Mixer-parameterizing bean-package
 */
@Root
@Default(DefaultType.FIELD)
public class MixerArgs extends ArgumentPack {
    @CsvBindByPosition(position = 5)
    private boolean channelMixing;
    @CsvBindByPosition(position = 6)
    private boolean cover;

    public MixerArgs () {
        setProcessorId(ProcessorId.MIXER);
    }

    public void setChannelMixing(boolean channelMixing) {
        this.channelMixing = channelMixing;
    }
    public boolean isChannelMixing() {
        return channelMixing;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }
    public boolean isCover() {
        return cover;
    }
}
