package at.kaisertorian.chrysotile.programs;

public class LoggingChannel {

    private int guildId;
    private int channelId;
    private DiscordLogging.LoggingTypes loggingType;

    public int getGuildId() {
        return guildId;
    }

    public void setGuildId(int guildId) {
        this.guildId = guildId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public DiscordLogging.LoggingTypes getLoggingType() {
        return loggingType;
    }

    public void setLoggingType(DiscordLogging.LoggingTypes loggingType) {
        this.loggingType = loggingType;
    }

    public LoggingChannel(int guildId, int channelId, DiscordLogging.LoggingTypes loggingType){
        this.guildId = guildId;
        this.channelId = channelId;
        this.loggingType = loggingType;
    }
}
