package at.kaisertorian.chrysotile;

import at.kaisertorian.chrysotile.programs.DiscordLogging;
import at.kaisertorian.chrysotile.programs.LoggingChannel;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Chrysotile {

    private final DataBaseManager dataBaseManager;
    private final DiscordLogging discordLogging;
    private final DiscordClient client;
    private final GatewayDiscordClient gateway;

    DataBaseManager getDataBaseManager(){
        return  dataBaseManager;
    }

    public DiscordClient getClient() {
        return client;
    }

    public GatewayDiscordClient getGateway() {
        return gateway;
    }

    @SuppressWarnings("resource")
    public Chrysotile() throws IOException {
        String apiToken = new String(getClass().getResourceAsStream("/API_key.txt").readAllBytes());
        this.client = DiscordClient.create(apiToken);
        this.gateway = client.login().block();

        gateway.on(ReadyEvent.class).subscribe(readyEvent -> {
            System.out.println("Connected as " + readyEvent.getSelf().getUsername());
        });


        this.dataBaseManager = new DataBaseManager(this);
        this.discordLogging = new DiscordLogging(this);

        gateway.onDisconnect().block();
    }


    public static void main(String[] args) throws IOException {
        new Chrysotile();
    }
}
