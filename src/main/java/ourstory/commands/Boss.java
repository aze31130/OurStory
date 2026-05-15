package ourstory.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import ourstory.Main;
import ourstory.bosses.HolyCow;
import ourstory.bosses.Instance;
import ourstory.bosses.Talven;
import ourstory.utils.Permissions;

public class Boss implements BasicCommand {
	private final List<String> availableBoss = List.of("Talven");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.boss"))
			return;

		if (args.length < 1) {
			sender.getSender().sendMessage("You need to provide a boss name !");
			return;
		}

		Player playerSender = (Player) sender.getExecutor();
		Mob test = (Mob) playerSender.getWorld().spawn(sender.getLocation(), IronGolem.class);
		ourstory.bosses.Boss boss = new Talven("Talven", test, List.of(playerSender), 0);
		boss.registerGoals(Bukkit.getServer().getMobGoals());

		// String bossName = args[0];

		// ourstory.bosses.Boss boss = null;
		// // arena.getSpawnLocation().set(0, 100, 0)
		// switch (bossName) {
		// case "Talven":
		// // boss = new Talven(sender.getLocation());
		// // boss.onSpawn();
		// break;

		// default:
		// sender.getSender().sendMessage("This boss does not exist !");
		// return;
		// }

		// // Register boss instance
		// List<Player> players = new ArrayList<>();
		// players.add((Player) sender.getSender());
		// Instance instance = new Instance(boss, players, 10, 5, "world");

		// Main.runningInstance = instance;
	}

	/*
	 * /boss <bossname>
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0)
			return availableBoss;

		String input = args[0].toLowerCase();

		return availableBoss.stream()
				.filter(boss -> boss.toLowerCase().startsWith(input))
				.collect(Collectors.toList());
	}
}
