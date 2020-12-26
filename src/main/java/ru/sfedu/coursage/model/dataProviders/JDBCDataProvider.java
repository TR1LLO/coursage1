package ru.sfedu.coursage.model.dataProviders;

import ru.sfedu.coursage.Constants;
import ru.sfedu.coursage.model.*;

import java.sql.*;
import java.util.Collection;

/**
 * class-implementer of JDBC-based API
 */
public class JDBCDataProvider extends AbstractDataProvider {
    private Statement statement;
    private Connection connection;
    private ResultSet set;
    public boolean connect() throws Exception {
        connection = DriverManager.getConnection(Constants.JDBC_URL,
                Constants.JDBC_ADMIN,
                Constants.JDBC_PASSWORD);
        return !connection.isClosed();
    }
    public ResultSet sqlQuery(String sql) throws SQLException {
        statement = connection.createStatement();
        set = statement.executeQuery(sql);
        set.next();
        return set;
    }
    public ResultSet dmlQuery(String dml) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate(dml);
        set=statement.getResultSet();
        statement.close();
        return set;
    }


    public JDBCDataProvider() throws Exception {
        logger.info("jdbc init...");
        connect();
        dmlQuery(Constants.JDBC_INIT_SOUND_DATA);
        dmlQuery(Constants.JDBC_INIT_COMPRESSOR);
        dmlQuery(Constants.JDBC_INIT_CONVERTER);
        dmlQuery(Constants.JDBC_INIT_EQUALIZER);
        dmlQuery(Constants.JDBC_INIT_MIXER);
        dmlQuery(Constants.JDBC_INIT_MULTIPLIER);
        dmlQuery(Constants.JDBC_INIT_NORMALIZER);
        dmlQuery(Constants.JDBC_INIT_SHIFTER);
        logger.info("jdbc initialized");

    }

    private String commonPackValues(ArgumentPack pack) {
        return pack.getId()+", '"+pack.getProcessorId()+"', "+
                (pack.getCompleted()?1:0)+", '"+pack.getErrorCode()+"', '"+pack.getWarnCode()+"'";
    }
    private String equalizerValues(EqualizerArgs args) {
        float[] amps=args.getAmps();
        String s=commonPackValues(args)+", "+amps[0];
        for(int i=1; i<amps.length; i++)
            s+=", "+amps[i];
        return s;
    }
    private String compressorValues(CompressorArgs args) {
        return commonPackValues(args)+", "+args.getPower()+", "+(args.isDistortion()?1:0);
    }
    private String converterValues(ConverterArgs args) {
        return commonPackValues(args)+", "+args.getBitness()+", "+args.getChannels()+", "+args.getSampleRate();
    }
    private String mixerValues(MixerArgs args) {
        return commonPackValues(args)+", "+(args.isChannelMixing()?1:0)+", "+(args.isCover()?1:0);
    }
    private String multiplierValues(MultiplierArgs args) {
        return commonPackValues(args)+", "+(args.isAverage()?1:0)+", "+args.getCount();
    }
    private String normalizerValues(NormalizerArgs args) {
        return commonPackValues(args)+", "+args.getAmp()+", "+(args.isHard()?1:0)+", "+args.getPeriod();
    }
    private String shifterValues(ShifterArgs args) {
        return commonPackValues(args)+", "+args.getFrequency()+", "+args.getStep()+", "+args.getRadius();
    }

    private ArgumentPack    readCommonArgs  (ResultSet set, ArgumentPack pack) throws Exception {
        pack.setId(set.getLong("ID"));
        pack.setProcessorId(ArgumentPack.ProcessorId.valueOf(set.getString("PROCESSORID")));
        pack.setCompleted(set.getInt("COMPLETED")==1);
        String temp=set.getString("ERRORCODE");
        if(!temp.equals("null"))
            pack.setErrorCode(ArgumentPack.ErrorCode.valueOf(temp));
        temp=set.getString("WARNCODE");
        if(!temp.equals("null"))
            pack.setWarnCode(ArgumentPack.WarnCode.valueOf(set.getString(5)));
        return pack;
    }
    private EqualizerArgs   readEqualizer   (ResultSet set) throws Exception {
        EqualizerArgs pack = new EqualizerArgs();
        readCommonArgs(set, pack);

        float[] amps=pack.getAmps();
        for(int i=0; i<amps.length; i++)
            amps[i]=set.getFloat("a"+i);
        return pack;
    }
    private CompressorArgs  readCompressor  (ResultSet set) throws Exception {
        CompressorArgs pack = new CompressorArgs();
        readCommonArgs(set, pack);

        pack.setPower(set.getFloat("POWER"));
        pack.setDistortion(set.getBoolean("DISTORTION"));
        return pack;
    }
    private ConverterArgs   readConverter   (ResultSet set) throws Exception {
        ConverterArgs pack = new ConverterArgs();
        readCommonArgs(set, pack);

        pack.setBitness(SoundData.Bitness.valueOf(set.getString("BITNESS")));
        pack.setChannels(set.getInt("CHANNELS"));
        pack.setSampleRate(set.getInt("SAMPLERATE"));
        return pack;
    }
    private MixerArgs       readMixer       (ResultSet set) throws Exception {
        MixerArgs pack = new MixerArgs();
        readCommonArgs(set, pack);

        pack.setChannelMixing(set.getBoolean("CHANNELMIXING"));
        pack.setCover(set.getBoolean("COVER"));
        return pack;
    }
    private MultiplierArgs  readMultiplier  (ResultSet set) throws Exception {
        MultiplierArgs pack = new MultiplierArgs();
        readCommonArgs(set, pack);

        pack.setAverage(set.getBoolean("AVERAGE"));
        pack.setCount(set.getInt("COUNT"));
        return pack;
    }
    private NormalizerArgs  readNormalizer  (ResultSet set) throws Exception {
        NormalizerArgs pack = new NormalizerArgs();
        readCommonArgs(set, pack);

        pack.setAmp(set.getFloat("AMP"));
        pack.setHard(set.getBoolean("HARD"));
        pack.setPeriod(set.getInt("PERIOD"));
        return pack;
    }
    private ShifterArgs     readShifter     (ResultSet set) throws Exception {
        ShifterArgs pack = new ShifterArgs();
        readCommonArgs(set, pack);

        pack.setFrequency(set.getFloat("FREQUENCY"));
        pack.setStep(set.getFloat("STEP"));
        pack.setRadius(set.getInt("RADIUS"));
        return pack;
    }

    private String packValues(ArgumentPack pack, ArgumentPack.ProcessorId processorId) {
        switch (processorId) {
            case CONVERTER:     return converterValues  ((ConverterArgs)pack);
            case MIXER:         return mixerValues      ((MixerArgs) pack);
            case SHIFTER:       return shifterValues    ((ShifterArgs) pack);
            case MULTIPLIER:    return multiplierValues ((MultiplierArgs) pack);
            case NORMALIZER:    return normalizerValues ((NormalizerArgs) pack);
            case EQUALIZER:     return equalizerValues  ((EqualizerArgs)pack);
            case COMPRESSOR:    return compressorValues ((CompressorArgs) pack);
            default: return null;
        }
    }
    private String soundDataValues(SoundData data) {
        return data.getId()+", '"+data.getBitness()+"', "+data.getChannels()+", "+
                data.getSampleRate()+", '"+data.getSourceFile()+"'";
    }
    private SoundData readSoundData(ResultSet set, boolean dataInit) throws Exception {
        try {
            SoundData data = new SoundData();
            data.setId(set.getLong("ID"));
            data.setBitness(SoundData.Bitness.valueOf(set.getString("BITNESS")));
            data.setChannels(set.getInt("CHANNELS"));
            data.setSourceFile(set.getString("SOURCEFILE"));
            data.setSampleRate(set.getInt("SAMPLERATE"));
            if(!dataInit)
                return data;
            data.setData(DataArray.readWavDataArray(data));
            return data;
        }
        catch(SQLException e){
            logger.error(e);
            return null;
        }
    }
    private ArgumentPack readArgumentPack(ResultSet set, ArgumentPack.ProcessorId processorId) throws Exception {
        switch (processorId) {
            case EQUALIZER:     return readEqualizer(set);
            case COMPRESSOR:    return readCompressor(set);
            case NORMALIZER:    return readNormalizer(set);
            case MULTIPLIER:    return readMultiplier(set);
            case SHIFTER:       return readShifter(set);
            case CONVERTER:     return readConverter(set);
            case MIXER:         return readMixer(set);
        }
        return null;
    }


    //-----------------------------------------INTERFACE-------------------------------------------
    public ProviderResult<Collection<ArgumentPack>> readAllArgumentPacks(Collection<ArgumentPack> container, ArgumentPack.ProcessorId processorId) throws Exception {
        try {
            sqlQuery(String.format(
                    Constants.JDBC_FORMAT_SELECT_ALL,
                    processorId.getPackageClass().getSimpleName()));
            while (set.next())
                container.add(readArgumentPack(set, processorId));
            return new ProviderResult(container);
        }
        catch (SQLException e) {
            logger.error(e);
            return new ProviderResult(Error.FAILED, container);
        }
    }
    public ProviderResult<Collection<ArgumentPack>> writeAllArgumentPacks(Collection<ArgumentPack> container, ArgumentPack.ProcessorId processorId) throws Exception {
        for(ArgumentPack pack: container)
            extWriteArgumentPack(pack, processorId);
        return new ProviderResult(container);
    }

    public ProviderResult<Collection<SoundData>> readAllSoundData(Collection<SoundData> container) throws Exception {
        try {
            sqlQuery(String.format(
                    Constants.JDBC_FORMAT_SELECT_ALL,
                    SoundData.class.getSimpleName()));
            while(set.next())
                container.add(readSoundData(set, false));
            return new ProviderResult(container);
        }
        catch (SQLException e) {
            logger.error(e);
            return new ProviderResult(Error.FAILED, container);
        }
    }
    public ProviderResult<Collection<SoundData>> writeAllSoundData(Collection<SoundData> container) throws Exception {
        for(SoundData data: container)
            extWriteSoundData(data);
        return new ProviderResult(container);
    }

    public ProviderResult<SoundData> extReadSoundData(SoundData obj) throws Exception {
        logger.debug("selecting SoundData...");
        try {
            sqlQuery(String.format(
                    Constants.JDBC_FORMAT_SELECT_ONE,
                    SoundData.class.getSimpleName(),
                    obj.getId()));
            return new ProviderResult(readSoundData(set, true));
        }
        catch (SQLException e) {
            return new ProviderResult(Error.BEAN_NOT_FOUND);
        }
    }
    public ProviderResult<SoundData> extWriteSoundData(SoundData obj) throws Exception {
        extRemoveSoundData(obj);
        logger.debug("inserting SoundData...");
        dmlQuery(String.format(
                Constants.JDBC_FORMAT_INSERT,
                SoundData.class.getSimpleName(),
                Constants.JDBC_SOUND_DATA_COL,
                soundDataValues(obj)));
        return new ProviderResult(obj);
    }
    public ProviderResult<SoundData> extRemoveSoundData(SoundData obj) throws Exception {
        logger.debug("deleting SoundData...");
        dmlQuery(String.format(
                Constants.JDBC_FORMAT_REMOVE,
                SoundData.class.getSimpleName(),
                obj.getId()));
        return new ProviderResult(obj);
    }

    public ProviderResult<ArgumentPack> extReadArgumentPack(ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception {
        logger.debug("selecting ArgumentPack...");
        try {
            sqlQuery(String.format(
                    Constants.JDBC_FORMAT_SELECT_ONE,
                    processorId.getPackageClass().getSimpleName(),
                    obj.getId()));
        }
        catch (SQLException e){
            logger.error(e);
            return new ProviderResult(Error.BEAN_NOT_FOUND);
        }
        ArgumentPack pack=readArgumentPack(set, processorId);
        return new ProviderResult(pack);
    }
    public ProviderResult<ArgumentPack> extWriteArgumentPack(ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception {
        extRemoveArgumentPack(obj, processorId);
        logger.debug("inserting ArgumentPack...");
        String columns;
        switch (processorId) {
            case MIXER:     columns=Constants.JDBC_MIXER_COL;       break;
            case SHIFTER:   columns=Constants.JDBC_SHIFTER_COL;     break;
            case CONVERTER: columns=Constants.JDBC_CONVERTER_COL;   break;
            case EQUALIZER: columns=Constants.JDBC_EQUALIZER_COL;   break;
            case COMPRESSOR:columns=Constants.JDBC_COMPRESSOR_COL;  break;
            case MULTIPLIER:columns=Constants.JDBC_MULTIPLIER_COL;  break;
            case NORMALIZER:columns=Constants.JDBC_NORMALIZER_COL;  break;
            default: return new ProviderResult(Error.FAILED);
        }
        dmlQuery(String.format(
                Constants.JDBC_FORMAT_INSERT,
                processorId.getPackageClass().getSimpleName(),
                columns,
                packValues(obj, processorId)));
        return new ProviderResult(obj);
    }
    public ProviderResult<ArgumentPack> extRemoveArgumentPack(ArgumentPack obj, ArgumentPack.ProcessorId processorId) throws Exception {
        logger.debug("removing ArgumentPack...");
        dmlQuery(String.format(
                Constants.JDBC_FORMAT_REMOVE,
                processorId.getPackageClass().getSimpleName(),
                obj.getId()));
        return new ProviderResult(obj);
    }
}
