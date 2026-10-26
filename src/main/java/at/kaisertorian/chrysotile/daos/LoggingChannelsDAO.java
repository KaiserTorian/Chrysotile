package at.kaisertorian.chrysotile.daos;

import at.kaisertorian.chrysotile.DataBaseManager;
import at.kaisertorian.chrysotile.programs.DiscordLogging;
import at.kaisertorian.chrysotile.programs.LoggingChannel;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoggingChannelsDAO {
    private final DataBaseManager dbm;

    public LoggingChannelsDAO(DataBaseManager dbm) {
        this.dbm = dbm;
    }


    public void createTable() {
        dbm.connect(connection -> {
            connection.prepareStatement("""
            CREATE TABLE IF NOT EXISTS logging_channels(
                guildId INTEGER,
                channelId INTEGER,
                loggingType TEXT
            )""").execute();
        });
    }


    public void deleteTable() {
        dbm.connect(connection -> {
            connection.prepareStatement("""
            DROP TABLE logging_channels""").execute();
        });
    }


    public List<LoggingChannel> getAllLoggingChannels(int guildId) {
        List<LoggingChannel> loggingChannels = new ArrayList<LoggingChannel>();
        dbm.connect(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT * FROM logging_channels WHERE GuildId = ?""");

            preparedStatement.setInt(1,guildId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
//                int guildId = resultSet.getInt(1);
                int channelId = resultSet.getInt(2);
                String loggingTypeString = resultSet.getString(3);
                DiscordLogging.LoggingTypes loggingTypes = DiscordLogging.LoggingTypes.valueOf(loggingTypeString);

                LoggingChannel loggingChannel = new LoggingChannel(guildId,channelId,loggingTypes);


            }
        });

        return loggingChannels;
    }

    public void addLoggingChannel(LoggingChannel lc){
        dbm.connect(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    INSERT INTO logging_channels 
                        (guildId, channelId, loggingType) VALUES (? ,?, ?)""");

            preparedStatement.setInt(1,lc.getGuildId());
            preparedStatement.setInt(2, lc.getChannelId());
            preparedStatement.setString(3, lc.getLoggingType().toString());

            preparedStatement.execute();

        });
    }

}
