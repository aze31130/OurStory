package ourstory.commands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.Main;
import ourstory.bosses.Boss;
import ourstory.bosses.HolyCow;

public class MobGoalCommand implements BasicCommand {
	@Override
	public void execute(CommandSourceStack cmdSource, String[] args) {
		if (!(cmdSource.getExecutor() instanceof Player)) {
			return;
		}
		Player sender = (Player) cmdSource.getExecutor();
		var engagedPlayers = List.of(sender);
		Boss boss = new HolyCow(engagedPlayers, 0);

		boss.spawn(sender.getWorld(), sender.getLocation());
		Bukkit.getPluginManager().registerEvents(boss, Bukkit.getPluginManager().getPlugin(Main.namespace));
	}
}
