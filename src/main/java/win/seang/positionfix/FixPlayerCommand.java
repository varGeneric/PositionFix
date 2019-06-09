package win.seang.positionfix;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.util.RespawnLocation;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class FixPlayerCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext context) throws CommandException {
        UUID uuid;
        if (context.<UUID>getOne("player").isPresent())
            uuid = context.<UUID>getOne("player").get();
        else
            throw new CommandException(Text.EMPTY, true);
        Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
        Optional<User> userTarget = userStorage.get().get(uuid);
        src.sendMessage(Text.of(uuid.toString()));
        userTarget.ifPresent(player -> src.sendMessage(Text.of("User Target Name: " + player.getName())));

        if (userTarget.isPresent() && !userTarget.get().isOnline()) {
            // TODO: Make good when SpongeAPI is fixed
            /*
            Optional<Map<UUID, RespawnLocation>> optRespawnLocations = userTarget.get().get(Keys.RESPAWN_LOCATIONS);
            if (optRespawnLocations.isPresent()) {
                Optional<Location<World>> optTargetLocation;
                for (Map.Entry<UUID, RespawnLocation> spawn : optRespawnLocations.get().entrySet()) {
                    optTargetLocation = spawn.getValue().asLocation();
                    src.sendMessage(Text.of("Found spawn: " + optTargetLocation.get().getPosition().toString()));
                    optTargetLocation.ifPresent(worldLocation -> userTarget.get().setLocation(worldLocation.getPosition(), worldLocation.getExtent().getUniqueId()));
                }
            }
            */
            // ouch very hacky
            userTarget.get().setLocation(new Vector3d(0, 0, 0), Sponge.getServer().getWorld("world").get().getUniqueId());
        }
        else
            throw new CommandException(Text.EMPTY, true);

        return CommandResult.success();
    }
}
