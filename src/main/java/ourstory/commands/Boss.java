package ourstory.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import ourstory.Main;
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

		String bossName = args[0];

		ourstory.bosses.Boss boss = null;
		// arena.getSpawnLocation().set(0, 100, 0)
		switch (bossName) {
			case "Talven":
				boss = new Talven(sender.getLocation());
				boss.onSpawn();
				break;

			default:
				sender.getSender().sendMessage("This boss does not exist !");
				return;
		}

		// Register boss instance
		List<Player> players = new ArrayList<>();
		players.add((Player) sender.getSender());
		Instance instance = new Instance(boss, players, 10, 5, "world");

		Main.runningInstance = instance;
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
