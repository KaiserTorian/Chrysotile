package at.kaisertorian.chrysotile;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Chrysotile {

    private static Class<?>[] PROGRAMS = new Class[]{};

    private final DiscordClient client;
    private final GatewayDiscordClient gateway;

    @SuppressWarnings("resource")
    public Chrysotile() throws IOException {
        String apiToken = new String(getClass().getResourceAsStream("/API_key.txt").readAllBytes());

        this.client = DiscordClient.create(apiToken);
        this.gateway = client.login().block();


        gateway.onDisconnect().block();
    }

    public static void main(String[] args) throws IOException {

       new Chrysotile();


    }
}
