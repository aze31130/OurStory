package ourstory.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.spells.*;
import ourstory.utils.Permissions;

public class Cast implements BasicCommand {

	public Map<String, Spell> skills;
	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	static {
		final Map<String, Spell> skills = Map.of(
				"Annihilation", new Annihilation(null, null, 0),
				"LaserMatrix", new LaserMatrix(null, null, 0),
				"LaserExplosion", new LaserExplosion(null, null, 0),
				"ArrowWall", new ArrowWall(null, null, 0));
	}


	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.cast"))
			return;

		Player player = (Player) sender.getSender();

		if (args.length == 0) {
			player.sendMessage("You need to provide a skill name");
			return;
		}

		Spell test = new Annihilation(player, player.getNearbyEntities(50, 50, 50), 1);
		test.setup();

		new BukkitRunnable() {
			@Override
			public void run() {
				if (test.shouldStop()) {
					test.stop();
					cancel();
				}
				test.tick();
			}
		}.runTaskTimer(p, 0, 1);


		// for (String s : args) {
		// if (!skills.containsKey(s))
		// continue;
		// if (s.equalsIgnoreCase("Annihilation")) {
		// new Annihilation(player, player.getNearbyEntities(50, 50, 50), 1);


		// }

		// // skills.get(s).cast(player, player.getNearbyEntities(50, 50, 50), 1);
		// }
	}

	/*
	 * /boss <bossname> <difficulty>
	 */
	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		// return skills.keySet();
		return List.of("Annihilation");
	}
}
