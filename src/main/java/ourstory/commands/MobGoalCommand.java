package ourstory.commands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.bosses.Boss;
import ourstory.bosses.HolyCow;
import ourstory.utils.Permissions;

public class MobGoalCommand implements BasicCommand {
	@Override
	public void execute(CommandSourceStack cmdSource, String[] args) {
		if (!Permissions.checkPermissions(cmdSource.getSender(), "ourstory.goal"))
			return;

		if (!(cmdSource.getExecutor() instanceof Player sender)) {
			cmdSource.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Mob holyCowMob = (Mob) sender.getWorld().spawn(sender.getLocation(), Cow.class);
		Boss boss = new HolyCow(holyCowMob, List.of(sender), 0);
		boss.registerGoals(Bukkit.getServer().getMobGoals());
	}
}
