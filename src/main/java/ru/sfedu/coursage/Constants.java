package ru.sfedu.coursage;

import ru.sfedu.coursage.model.*;
import ru.sfedu.coursage.utils.ConfigurationUtil;

import java.io.IOException;

public class Constants {
    public static String CONFIG_PROPERTY = "properties.path";
    public static String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    public static boolean loadingStatus = loadConstants();
    public static boolean loadConstants() {
        try {
            TEST_WAV_FILE = ConfigurationUtil.getConfigurationEntry(TEST_WAV_KEY);
            TEST_WAV_DESTINATION = ConfigurationUtil.getConfigurationEntry(TEST_WAV_DESTINATION_KEY);

            CSV_DIRECTORY = ConfigurationUtil.getConfigurationEntry(CSV_DIRECTORY_KEY);
            XML_DIRECTORY = ConfigurationUtil.getConfigurationEntry(XML_DIRECTORY_KEY);
            CSV_FORMAT = ConfigurationUtil.getConfigurationEntry(CSV_FORMAT_KEY);
            XML_FORMAT = ConfigurationUtil.getConfigurationEntry(XML_FORMAT_KEY);

            JDBC_USER = ConfigurationUtil.getConfigurationEntry(JDBC_USER_KEY);
            JDBC_PASSWORD = ConfigurationUtil.getConfigurationEntry(JDBC_PASSWORD_KEY);
            JDBC_URL = ConfigurationUtil.getConfigurationEntry(JDBC_URL_KEY);
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public static final String TEST_WAV_KEY = "testWavFile";
    public static final String TEST_WAV_DESTINATION_KEY = "testWavDestination";
    public static final String CSV_DIRECTORY_KEY = "csvDirectory";
    public static final String XML_DIRECTORY_KEY = "xmlDirectory";
    public static final String CSV_FORMAT_KEY = "csvFormat";
    public static final String XML_FORMAT_KEY = "xmlFormat";

    public static final String JDBC_USER_KEY = "jdbcUser";
    public static final String JDBC_PASSWORD_KEY = "jdbcPassword";
    public static final String JDBC_URL_KEY = "jdbcUrl";

    public static String CSV_DIRECTORY;
    public static String XML_DIRECTORY;
    public static String CSV_FORMAT;
    public static String XML_FORMAT;

    //-----------------------------TESTS------------------------------------
    public static String TEST_WAV_FILE;
    public static String TEST_WAV_DESTINATION;
    public static final String TEST_INVALID_FILE = "";
    public static final int TEST_SOUNDDATA_BITS = SoundData.Bitness.CHAR_8.getBits();
    public static final int TEST_SOUNDDATA_SIZE = 4096;
    public static final int TEST_SOUNDDATA_CHANNELS = 1;
    public static final int TEST_SOUNDDATA_SAMPLERATE = 20050;


    public static final float TEST_COMPRESSOR_POWER = 0.9f;
    public static final boolean TEST_COMPRESSOR_DISTORTION = false;

    public static final int TEST_CONVERTER_BITS = 8;
    public static final int TEST_CONVERTER_CHANNELS = 1;
    public static final int TEST_CONVERTER_SAMPLERATE = 1;

    public static final float[] TEST_EQUALIZER_AMPS = {0, 0, 0, 0, 0, 2, 0, 0, 0, 0};

    public static final boolean TEST_MIXER_CHANNELMIXING = true;
    public static final boolean TEST_MIXER_COVER = true;

    public static final boolean TEST_MULTIPLIER_AVERAGE = true;
    public static final int TEST_MULTIPLIER_COUNT = 4;

    public static final float TEST_NORMALIZER_AMP = 4.0f;
    public static final boolean TEST_NORMALIZER_HARD = false;
    public static final int TEST_NORMALIZER_PERIOD = 1;

    public static final float TEST_SHIFTER_FREQUENCY = 100.0f;
    public static final float TEST_SHIFTER_STEP = 1.0f;
    public static final int TEST_SHIFTER_RADIUS = 20;


    //------------------------------JDBC-------------------------------------
    public static String JDBC_USER;
    public static String JDBC_PASSWORD;
    public static String JDBC_URL;


    public static final String JDBC_COLUMNS_SOUND_DATA =
            "id bigint primary key, " +
                    "bitness varchar(8), " +
                    "channels integer, " +
                    "sampleRate integer, " +
                    "sourceFile varchar(256)";
    public static final String JDBC_COLUMNS_ARGUMENT_PACK =
            "id bigint primary key, " +
                    "processorId varchar(16), " +
                    "completed integer, " +
                    "errorCode varchar(64), " +
                    "warnCode varchar(64)";

    public static final String JDBC_COLUMNS_COMPRESSOR =
            "power real, " +
                    "distortion integer";
    public static final String JDBC_COLUMNS_CONVERTER =
            "bitness varchar(8), " +
                    "channels integer, " +
                    "sampleRate integer";
    public static final String JDBC_COLUMNS_EQUALIZER =
            "a0 real, " + "a1 real, " +
                    "a2 real, " + "a3 real, " +
                    "a4 real, " + "a5 real, " +
                    "a6 real, " + "a7 real, " +
                    "a8 real, " + "a9 real";
    public static final String JDBC_COLUMNS_MIXER =
            "channelMixing integer, " +
                    "cover integer";
    public static final String JDBC_COLUMNS_MULTIPLIER =
            "average integer, " +
                    "count integer";
    public static final String JDBC_COLUMNS_NORMALIZER =
            "amp real, " +
                    "hard integer, " +
                    "period integer";
    public static final String JDBC_COLUMNS_SHIFTER =
            "frequency real, " +
                    "step real, " +
                    "radius integer";

    public static final String JDBC_SOUND_DATA_COL = "id, bitness, channels, sampleRate, sourceFile";
    public static final String JDBC_ARGUMENT_PACK_COL = "id, processorId, completed, errorCode, warnCode";
    public static final String JDBC_COMPRESSOR_COL    = JDBC_ARGUMENT_PACK_COL+ ", power, distortion";
    public static final String JDBC_CONVERTER_COL     = JDBC_ARGUMENT_PACK_COL+ ", bitness, channels, sampleRate";
    public static final String JDBC_EQUALIZER_COL     = JDBC_ARGUMENT_PACK_COL+ ", a0, a1, a2, a3, a4, a5, a6, a7, a8, a9";
    public static final String JDBC_MIXER_COL         = JDBC_ARGUMENT_PACK_COL+ ", channelMixing, cover";
    public static final String JDBC_MULTIPLIER_COL    = JDBC_ARGUMENT_PACK_COL+ ", average, count";
    public static final String JDBC_NORMALIZER_COL    = JDBC_ARGUMENT_PACK_COL+ ", amp, hard, period";
    public static final String JDBC_SHIFTER_COL       = JDBC_ARGUMENT_PACK_COL+ ", frequency, step, radius";

    public static final String JDBC_INIT_SOUND_DATA   = "create table if not exists "+
            SoundData.class.getSimpleName()+        "(" + JDBC_COLUMNS_SOUND_DATA + ");";
    public static final String JDBC_INIT_COMPRESSOR   = "create table if not exists "+
            CompressorArgs.class.getSimpleName()+   "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_COMPRESSOR + ");";
    public static final String JDBC_INIT_CONVERTER    = "create table if not exists "+
            ConverterArgs.class.getSimpleName()+    "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_CONVERTER  + ");";
    public static final String JDBC_INIT_EQUALIZER    = "create table if not exists "+
            EqualizerArgs.class.getSimpleName()+    "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_EQUALIZER  + ");";
    public static final String JDBC_INIT_MIXER        = "create table if not exists "+
            MixerArgs.class.getSimpleName()+        "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_MIXER      + ");";
    public static final String JDBC_INIT_MULTIPLIER   = "create table if not exists "+
            MultiplierArgs.class.getSimpleName()+   "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_MULTIPLIER + ");";
    public static final String JDBC_INIT_NORMALIZER   = "create table if not exists "+
            NormalizerArgs.class.getSimpleName()+   "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_NORMALIZER + ");";
    public static final String JDBC_INIT_SHIFTER      = "create table if not exists "+
            ShifterArgs.class.getSimpleName()+      "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_SHIFTER    + ");";


    public static final String JDBC_FORMAT_SELECT_ALL = "select * from %s";
    public static final String JDBC_FORMAT_SELECT_ONE = "select * from %s where id=%d";
    public static final String JDBC_FORMAT_INSERT = "insert into %s(%s) values(%s)";
    public static final String JDBC_FORMAT_REMOVE = "delete from %s where id=%d";

}
