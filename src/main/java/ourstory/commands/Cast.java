package ourstory.commands;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.NonNull;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.Main;
import ourstory.spells.Annihilation;
import ourstory.spells.GravityWell;
import ourstory.spells.Spell;
import ourstory.utils.Permissions;
import org.bukkit.entity.Entity;
import java.util.ArrayList;

public class Cast implements BasicCommand {

	private static final java.util.Map<String, BiFunction<Player, List<Entity>, Spell>> SPELLS = java.util.Map.of(
			"Annihilation", (p, t) -> new Annihilation(p, t, 1),
			"GravityWell", (p, t) -> new GravityWell(p, t, 1));

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.cast"))
			return;

		if (!(sender.getSender() instanceof Player player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		if (args.length == 0) {
			player.sendMessage("You need to provide a spell name. Available: " + String.join(", ", SPELLS.keySet()));
			return;
		}

		BiFunction<Player, List<Entity>, Spell> factory = SPELLS.get(args[0]);
		if (factory == null) {
			player.sendMessage("Unknown spell: " + args[0]);
			return;
		}

		Spell spell = factory.apply(player, new ArrayList<>(player.getNearbyEntities(50, 50, 50)));
		spell.setup();

		new BukkitRunnable() {
			@Override
			public void run() {
				if (spell.shouldStop()) {
					spell.stop();
					cancel();
					return;
				}
				spell.tick();
			}
		}.runTaskTimer(Main.plugin, 0, 1);
	}

	@Override
	public @NonNull Collection<String> suggest(@NonNull CommandSourceStack commandSourceStack, String[] args) {
		return SPELLS.keySet();
	}
}
