package ourstory.commands;

import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.bosses.Talven;
import ourstory.storage.BossInstance;
import ourstory.storage.Storage;
import ourstory.utils.Permissions;

public class Boss implements BasicCommand {
	private final List<String> bossNames = List.of("Talven");

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

		// Teleport player to the arena
		World arena = Bukkit.getWorld("world");
		Player p = (Player) sender.getSender();
		// p.teleport(arena.getSpawnLocation());

		switch (bossName) {
			case "AbyssalSentinel":
				boss = new Talven(arena.getSpawnLocation().set(0, 100, 0), arena);
				boss.onSpawn();
				break;

			default:
				sender.getSender().sendMessage("This boss does not exist !");
				return;
		}

		// Register boss instance
		Storage s = Storage.getInstance();
		s.bossInstance = new BossInstance(boss, List.of(p), 15);
	}

	/*
	 * /boss <bossname> <difficulty>
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0)
			return bossNames;

		return Collections.emptyList();
	}
}
