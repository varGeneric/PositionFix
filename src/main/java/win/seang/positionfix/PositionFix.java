package win.seang.positionfix;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "positionfix",
        name = "Position Fix",
        url = "https://github.com/varGeneric",
        authors = {
                "CommitDelete"
        },
        description = "IDK"
)
public class PositionFix {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Hello World!");
    }

    @Listener
    public void onPreInitialization(GamePreInitializationEvent event)
    {
        CommandSpec fixPlayerCommand = CommandSpec.builder()
                .description(Text.EMPTY)
                .extendedDescription(Text.EMPTY)
                .arguments(
                        GenericArguments.allOf(GenericArguments.uuid(Text.of("player")))
                )
                .permission("fixposition.command.fix")
                .executor(new FixPlayerCommand())
                .build();

        Sponge.getCommandManager().register(this, fixPlayerCommand, "fixplayer");
    }
}
