package at.kaisertorian.chrysotile.programs;

import at.kaisertorian.chrysotile.Chrysotile;
import discord4j.core.event.domain.guild.GuildEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.command.ApplicationCommand;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

public class DiscordLogging {


    public enum LoggingTypes{
        JOIN,
        LEAVE,
    }

    private final Chrysotile chrysotile;

    public DiscordLogging(Chrysotile chrysotile) {
        this.chrysotile = chrysotile;
        initCommands();
        connectEvents();
    }


    private void initCommands(){
        ApplicationCommandRequest loggingCommand = ApplicationCommandRequest.builder()
                .name("logging")
                .description("Manager logging on your guild.")

                .addOption(ApplicationCommandOptionData.builder()
                        .name("add")
                        .description("Add a new logging event.")
                        .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("channel")
                                .description("The channel where the logs should be.")
                                .type(ApplicationCommandOption.Type.CHANNEL.getValue())
                                .required(true)
                                .build())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("event")
                                .description("What should be logged")
                                .type(ApplicationCommandOption.Type.STRING.getValue())
                                .required(true)
                                .build())
                        .build())

                .addOption(ApplicationCommandOptionData.builder()
                        .name("remove")
                        .description("Removes logging actions.")
                        .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("channel")
                                .description("Select the channel")
                                .type(ApplicationCommandOption.Type.CHANNEL.getValue())
                                .required(true)
                                .build())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("event")
                                .description("Select the logging event.")
                                .type(ApplicationCommandOption.Type.STRING.getValue())
                                .required(true)
                                .build())
                        .build())

                .addOption(ApplicationCommandOptionData.builder()
                        .name("list")
                        .description("Lists all logging events on the guild.")
                        .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("channel")
                                .description("get all logging events from this channel.")
                                .type(ApplicationCommandOption.Type.CHANNEL.getValue())
                                .required(false)
                                .build())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("event")
                                .description("Get all channel with this event")
                                .type(ApplicationCommandOption.Type.STRING.getValue())
                                .required(false)
                                .build())
                        .build())
                .build();



        chrysotile.getClient().getApplicationService()
                .createGuildApplicationCommand(chrysotile.getClient().getApplicationId().block(),1002206629606981714L,loggingCommand).subscribe();

        chrysotile.getGateway().on(ChatInputInteractionEvent.class, event -> {
            System.out.println(event.getCommandName());
            event.getOptions().toString();
            String name = event.getOption("add  ")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .orElse("Your default value");
            System.out.println(event.getOptions().toString());
            event.reply(event.getOptions().toString());
            return null;
        }).subscribe();
    }




    private void connectEvents() {
        chrysotile.getGateway().on(GuildEvent.class,this::onGuildEvent).subscribe();
    }


    private Mono<?> onGuildEvent(GuildEvent guildEvent){
        return Mono.empty();
    }
}

