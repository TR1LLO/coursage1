package ru.sfedu.coursage;

import ru.sfedu.coursage.model.*;

public class Constants {
    public static String DATASOURCE_DIRECTORY = "C:/Users/Argon/IdeaProjects/coursage";
    public static String TEST_WAV_FILE = "C:/Users/Argon/IdeaProjects/coursage/bonk.wav";

    public static String JDBC_ADMIN = "admin";
    public static String JDBC_PASSWORD = "password";
    public static String JDBC_URL = "jdbc:h2:C:\\Users\\Argon\\IdeaProjects\\coursage";


    public static String JDBC_COLUMNS_SOUND_DATA =
            "id bigint primary key, " +
                    "bitness varchar(8), " +
                    "channels integer, " +
                    "sampleRate integer, " +
                    "sourceFile varchar(256)";
    public static String JDBC_COLUMNS_ARGUMENT_PACK =
            "id bigint primary key, " +
                    "processorId varchar(16), " +
                    "completed integer, " +
                    "errorCode varchar(64), " +
                    "warnCode varchar(64)";

    public static String JDBC_COLUMNS_COMPRESSOR =
            "power real, " +
                    "distortion integer";
    public static String JDBC_COLUMNS_CONVERTER =
            "bitness varchar(8), " +
                    "channels integer, " +
                    "sampleRate integer";
    public static String JDBC_COLUMNS_EQUALIZER =
            "a0 real, " +
                    "a1 real, " +
                    "a2 real, " +
                    "a3 real, " +
                    "a4 real, " +
                    "a5 real, " +
                    "a6 real, " +
                    "a7 real, " +
                    "a8 real, " +
                    "a9 real";
    public static String JDBC_COLUMNS_MIXER =
            "channelMixing integer, " +
                    "cover integer";
    public static String JDBC_COLUMNS_MULTIPLIER =
            "average integer, " +
                    "count integer";
    public static String JDBC_COLUMNS_NORMALIZER =
            "amp real, " +
                    "hard integer, " +
                    "period integer";
    public static String JDBC_COLUMNS_SHIFTER =
            "frequency real, " +
                    "step real, " +
                    "radius integer";

    public static String JDBC_SOUND_DATA_COL = "id, bitness, channels, sampleRate, sourceFile";
    public static String JDBC_ARGUMENT_PACK_COL = "id, processorId, completed, errorCode, warnCode";
    public static String JDBC_COMPRESSOR_COL    = JDBC_ARGUMENT_PACK_COL+ ", power, distortion";
    public static String JDBC_CONVERTER_COL     = JDBC_ARGUMENT_PACK_COL+ ", bitness, channels, sampleRate";
    public static String JDBC_EQUALIZER_COL     = JDBC_ARGUMENT_PACK_COL+ ", a0, a1, a2, a3, a4, a5, a6, a7, a8, a9";
    public static String JDBC_MIXER_COL         = JDBC_ARGUMENT_PACK_COL+ ", channelMixing, cover";
    public static String JDBC_MULTIPLIER_COL    = JDBC_ARGUMENT_PACK_COL+ ", average, count";
    public static String JDBC_NORMALIZER_COL    = JDBC_ARGUMENT_PACK_COL+ ", amp, hard, period";
    public static String JDBC_SHIFTER_COL       = JDBC_ARGUMENT_PACK_COL+ ", frequency, step, radius";

    public static String JDBC_INIT_SOUND_DATA   = "create table if not exists "+
            SoundData.class.getSimpleName()+        "(" + JDBC_COLUMNS_SOUND_DATA + ");";
    public static String JDBC_INIT_COMPRESSOR   = "create table if not exists "+
            CompressorArgs.class.getSimpleName()+   "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_COMPRESSOR + ");";
    public static String JDBC_INIT_CONVERTER    = "create table if not exists "+
            ConverterArgs.class.getSimpleName()+    "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_CONVERTER  + ");";
    public static String JDBC_INIT_EQUALIZER    = "create table if not exists "+
            EqualizerArgs.class.getSimpleName()+    "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_EQUALIZER  + ");";
    public static String JDBC_INIT_MIXER        = "create table if not exists "+
            MixerArgs.class.getSimpleName()+        "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_MIXER      + ");";
    public static String JDBC_INIT_MULTIPLIER   = "create table if not exists "+
            MultiplierArgs.class.getSimpleName()+   "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_MULTIPLIER + ");";
    public static String JDBC_INIT_NORMALIZER   = "create table if not exists "+
            NormalizerArgs.class.getSimpleName()+   "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_NORMALIZER + ");";
    public static String JDBC_INIT_SHIFTER      = "create table if not exists "+
            ShifterArgs.class.getSimpleName()+      "(" + JDBC_COLUMNS_ARGUMENT_PACK+ ", " +JDBC_COLUMNS_SHIFTER    + ");";


    public static String JDBC_FORMAT_SELECT_ALL = "select * from %s";
    public static String JDBC_FORMAT_SELECT_ONE = "select * from %s where id=%d";
    public static String JDBC_FORMAT_INSERT = "insert into %s(%s) values(%s)";
    public static String JDBC_FORMAT_REMOVE = "delete from %s where id=%d";

}
