package ourstory.commands;

import java.util.Collections;
import java.util.List;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.Main;
import ourstory.bosses.Instance;
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

		switch (bossName) {
			case "AbyssalSentinel":
				// boss = new Talven(arena.getSpawnLocation().set(0, 100, 0), arena);
				// boss.onSpawn();
				break;

			default:
				sender.getSender().sendMessage("This boss does not exist !");
				return;
		}

		// Register boss instance
		Instance instance = new Instance(boss, null, 10, 5, "world");

		Main.runningInstances.add(instance);
	}

	/*
	 * /boss <bossname>
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0)
			return availableBoss;

		return Collections.emptyList();
	}
}
