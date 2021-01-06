package ru.sfedu.coursage.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * class-container of sound PCM
 */
public class DataArray implements Serializable {
    public static Logger logger = LogManager.getLogger();

    private int size, bits, channels;
    private float[][] buffer;
    public void write(int ch, int pos, float val) {
        buffer[(size+pos)%size][ch%channels]=val;
    }
    public float read(int ch, int pos) {
        return buffer[(size+pos)%size][ch%channels];
    }

    public DataArray() {}
    public int getBits() {
        return bits;
    }
    public int getChannels() {
        return channels;
    }
    public int getSize() {
        return size;
    }

    public float hash = 0.0f;
    /**
     * get hash of buffer content
     * @return hash-sum
     */
    public float bufferHash() {
        float r=0;
        for(int ch=0; ch<channels; ch++)
            for(int i=0; i<size; i++)
                r+=Math.abs(buffer[i][ch]);
        return r;
    }
    /**
     * get hash-sum of bytearray content
     * @param bytearray actual bytearray
     * @return bytearray long hash-sum
     */
    public static long byteHash(byte[] bytearray) {
        long r=0;
        for(int i=0; i<bytearray.length; i++)
            r+=Math.abs(bytearray[i]);
        return r;
    }


    /**
     * decode bytearray into float buffer
     * @param bytearray binary array
     * @param size target float array length
     * @param channels number of channels
     * @param bits bits per float element
     * @return float buffer with position-channel access
     */
    private static float[][] decodeByteArray(byte[] bytearray, int size, int channels, int bits) {
        logger.debug("bytearray decoding...");
        float[][] buffer=new float[size][channels];
        switch (bits) {
            case 16:
                for(int i=0; i< size; i++)
                    for(int k=0; k<channels; k++)
                        buffer[i][k]=(float)bytesToShort(
                                bytearray[i*channels*2+k+1],
                                bytearray[i*channels*2+k+0]);
                break;
            case 24:
                for(int i=0; i<size; i++)
                    for(int k=0; k<channels; k++)
                        buffer[i][k]=(float)bytesToInt((byte)0,
                                bytearray[i*channels*3+k+2],
                                bytearray[i*channels*3+k+1],
                                bytearray[i*channels*3+k+0]);
                break;
            case 32:
                for(int i=0; i<size; i++)
                    for(int k=0; k<channels; k++)
                        buffer[i][k]=Float.intBitsToFloat(bytesToInt(
                                bytearray[i*channels*4+k+3],
                                bytearray[i*channels*4+k+2],
                                bytearray[i*channels*4+k+1],
                                bytearray[i*channels*4+k+0]));
                break;
            default:
                for(int i=0; i< size; i++)
                    for(int k=0; k<channels; k++)
                        buffer[i][k]=(float)bytearray[i*channels+k];
                break;
        }
        logger.debug("bytearray decoding finished");
        return buffer;
    }
    /**
     * encode float buffer into bytearray
     * @param buffer float array
     * @param size length of float array
     * @param channels number of array channels
     * @param bits bits per float element
     * @return binary interpretation of float buffer
     */
    private static byte[] toByteArray(float[][] buffer, int size, int channels, int bits){
        logger.debug("bytearray coding...");
        byte[] bytearray=new byte[size*channels*bits/8];
        switch (bits) {
            case 16:
                for(int i=0; i<bytearray.length; i+=2) {
                    int k=i/(bits/8);
                    int temp=(int)buffer[k/channels][k%channels];
                    bytearray[i+0]=(byte)(temp&0xFF);
                    bytearray[i+1]=(byte)((temp>>>8)&0xFF);
                }
                break;
            case 24:
                for(int i=0; i<bytearray.length; i+=3) {
                    int k=i/(bits/8);
                    int temp=(int)buffer[k/channels][k%channels];
                    bytearray[i+0]=(byte)(temp&0xFF);
                    bytearray[i+1]=(byte)((temp>>>8)&0xFF);
                    bytearray[i+2]=(byte)((temp>>>16)&0xFF);
                }
                break;
            case 32:
                for(int i=0; i<bytearray.length; i+=4) {
                    int k=i/(bits/8);
                    int temp=Float.floatToIntBits(buffer[k/channels][k%channels]);
                    bytearray[i+0]=(byte)(temp&0xFF);
                    bytearray[i+1]=(byte)((temp>>>8)&0xFF);
                    bytearray[i+2]=(byte)((temp>>>16)&0xFF);
                    bytearray[i+3]=(byte)((temp>>>24)&0xFF);
                }
                break;
            default:
                for(int i=0; i<bytearray.length; i++)
                    bytearray[i]=(byte)buffer[i/channels][i%channels];
                break;
        }
        logger.debug("bytearray coding finished");
        return bytearray;
    }


    /**
     * create empty DataArray
     * @param size channel length
     * @param bits bits per float
     * @param channels number of channels
     * @return empty initialized DataArray
     */
    public static DataArray createEmpty(int size, int bits, int channels, int sampleRate) {
        if(size<=0) return null;
        if(bits!=8 && bits!=16 && bits!=24 && bits!=32) return null;
        DataArray array = new DataArray();
        array.size=size;
        array.bits=bits;
        array.channels=channels;
        array.buffer=new float[size][channels];
        array.sampleRate=sampleRate;

        int len=size*(bits/8)*channels;
        array.chunkSize=36+len;
        array.numChannels=(short)channels;
        array.byteRate=(bits/8)*sampleRate*channels;
        array.blockAlign=(short)(channels*(bits/8));
        array.bitsPerSample=(short)bits;
        array.subChunk2Size=len;
        logger.info("created empty DataArray");
        return array;
    }
    /**
     * create DataArray empty clone
     * @param orig original object
     * @return DataArray with similar attributes but empty buffer
     */
    public static DataArray emptyClone(DataArray orig) {
        return createEmpty(orig.size, orig.bits, orig.channels, orig.sampleRate);
    }


    //------------------------------WAV_CONVERSION--------------------------
    //------------------------------WAV_HEADER-------------------------------
    private static final int riff=0x52494646;       //  0-4     RIFF
    private int chunkSize;                          //  4-8
    private static final int format=0x57415645;     //  8-12    WAVE
    private static final int subChunk1Id=0x666d7420;//  12-16   fmt
    private static final int subChunk1Size=16;      //  16-20   16
    private static final short audioFormat=1;       //  20-22   1
    private short numChannels;                      //  22-24
    public int sampleRate;                          //  24-28
    public int byteRate;                            //  28-32
    private short blockAlign;                       //  32-34
    private short bitsPerSample;                    //  34-36
    private static final int subChunk2Id=0x64617461;//  36-40   data
    private int subChunk2Size;                      //  40-44
    private static int list=1414744396;             //  LIST

    private static byte[] toBytes(int val) {
        byte[] res=new byte[4];
        res[0]=(byte)(val&0xFF);
        res[1]=(byte)((val&0xFF00)>>>8);
        res[2]=(byte)((val&0xFF0000)>>>16);
        res[3]=(byte)((val&0xFF000000)>>>24);
        return res;
    }
    private static byte[] toBytes(short val) {
        byte[] res=new byte[2];
        res[0]=(byte)(val&0xFF);
        res[1]=(byte)((val&0xFF00)>>>8);
        return res;
    }
    private static short bytesToShort(byte b0, byte b1) {
        int a1=b1&0xFF;
        int a2=(b0<<8)&0xFF00;
        return (short)(a1+a2);
    }
    private static int bytesToInt(byte b0, byte b1, byte b2, byte b3) {
        int a1=b3&0xFF;
        int a2=(b2<<8)&0xFF00;
        int a3=(b1<<16)&0xFF0000;
        int a4=(b0<<24)&0xFF000000;
        return a1+a2+a3+a4;
    }

    private static boolean readHeader(DataArray data, FileInputStream stream) throws IOException {
        logger.debug("wavHeader reading...");
        byte[] b = new byte[44];
        stream.read(b);
        data.chunkSize=bytesToInt(b[7], b[6], b[5], b[4]);
        data.numChannels=bytesToShort(b[23], b[22]);
        data.sampleRate=bytesToInt(b[27], b[26], b[25], b[24]);
        data.byteRate=bytesToInt(b[31], b[30], b[29], b[28]);
        data.blockAlign=bytesToShort(b[33], b[32]);
        data.bitsPerSample=bytesToShort(b[35], b[34]);
        data.subChunk2Size=bytesToInt(b[43], b[42], b[41], b[40]);

        if(bytesToInt(b[39], b[38], b[37], b[36])==list) {
            logger.debug("wavHeader: list section found");
            data.chunkSize-=data.subChunk2Size;
            stream.skip(data.subChunk2Size);
            byte[] b4 = new byte[4];
            stream.skip(4);
            stream.read(b4);
            data.subChunk2Size=bytesToInt(b4[3], b4[2], b4[1], b4[0]);
        }
        logger.debug("wavHeader readed");
        return true;
    }
    private static boolean writeHeader(DataArray data, FileOutputStream stream) throws IOException {
        logger.debug("wavHeader writing...");
        byte[] b2, b4;
        b4=toBytes(Integer.reverseBytes(riff));         stream.write(b4);
        b4=toBytes(data.chunkSize);                     stream.write(b4);
        b4=toBytes(Integer.reverseBytes(format));       stream.write(b4);
        b4=toBytes(Integer.reverseBytes(subChunk1Id));  stream.write(b4);
        b4=toBytes(subChunk1Size);                      stream.write(b4);
        b2=toBytes(audioFormat);                        stream.write(b2);
        b2=toBytes(data.numChannels);                   stream.write(b2);
        b4=toBytes(data.sampleRate);                    stream.write(b4);
        b4=toBytes(data.byteRate);                      stream.write(b4);
        b2=toBytes(data.blockAlign);                    stream.write(b2);
        b2=toBytes(data.bitsPerSample);                 stream.write(b2);
        b4=toBytes(Integer.reverseBytes(subChunk2Id));  stream.write(b4);
        b4=toBytes(data.subChunk2Size);                 stream.write(b4);
        logger.debug("wavHeader writed");
        return true;
    }

    //-----------------------------WAV_READING---------------------------------
    public static SoundData readWavSoundData(String src) throws IOException {
        logger.debug("wav file reading...");
        SoundData soundData = new SoundData();
        soundData.setSourceFile(src);

        DataArray data = readWavDataArray(soundData);
        if(data==null) {
            logger.error("wav file reading corrupted");
            return null;
        }

        switch (data.bits) {
            case 16: soundData.setBitness(SoundData.Bitness.SHORT_16); break;
            case 24: soundData.setBitness(SoundData.Bitness.INT_24); break;
            default: soundData.setBitness(SoundData.Bitness.CHAR_8); break;
        }
        soundData.setChannels(data.numChannels);
        soundData.setSampleRate(data.sampleRate);
        logger.info("wav file readed");
        return soundData;
    }
    public static DataArray readWavDataArray(SoundData soundData) throws IOException {
        FileInputStream stream = new FileInputStream(soundData.getSourceFile());
        DataArray data = new DataArray();
        soundData.setData(data);

        if(!readHeader(data, stream))
            return null;
        data.bits=data.bitsPerSample;
        data.channels=data.numChannels;

        byte[] bytearray = new byte[data.subChunk2Size];
        stream.read(bytearray);
        stream.close();

        data.size=bytearray.length/data.channels/(data.bits/8);
        data.buffer=decodeByteArray(bytearray, data.size, data.channels, data.bits);
        data.hash=data.bufferHash();
        return data;
    }
    public static boolean writeWavDataArray(SoundData soundData) throws IOException {
        logger.debug("wav file writing...");
        if(soundData.getSourceFile().equals("")) {
            logger.warn("no destination file");
            return false;
        }
        FileOutputStream stream = new FileOutputStream(soundData.getSourceFile());
        DataArray data = soundData.getData();
        writeHeader(data, stream);
        byte[] temp=toByteArray(data.buffer, data.size, data.channels, data.bits);
        stream.write(temp);
        stream.close();
        logger.debug("wav file writed");
        return true;
    }

    @Override
    public String toString() {
        return "DataArray ["+
                "size="+size+", "+
                "channels="+channels+", "+
                "bits="+bits+", "+
                "hash="+hash+ "]";
    }
}
