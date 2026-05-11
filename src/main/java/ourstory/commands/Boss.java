package ourstory.commands;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.bosses.Talven;
import ourstory.utils.Permissions;

public class Boss implements BasicCommand {
	private static final List<String> AVAILABLE_BOSSES = List.of("Talven");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.boss"))
			return;

		if (args.length < 1) {
			sender.getSender().sendMessage("You need to provide a boss name !");
			return;
		}

		String bossName = args[0];
		if (!AVAILABLE_BOSSES.contains(bossName)) {
			sender.getSender().sendMessage("Unknown boss: " + bossName);
			return;
		}

		if (!(sender.getExecutor() instanceof Player playerSender)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Mob mob = (Mob) playerSender.getWorld().spawn(sender.getLocation(), IronGolem.class);
		ourstory.bosses.Boss boss = new Talven(bossName, mob, List.of(playerSender), 0);
		boss.registerGoals(Bukkit.getServer().getMobGoals());
	}

	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0)
			return AVAILABLE_BOSSES;

		String input = args[0].toLowerCase();
		return AVAILABLE_BOSSES.stream()
				.filter(boss -> boss.toLowerCase().startsWith(input))
				.collect(Collectors.toList());
	}
}
