package ourstory.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;
import ourstory.goal.ChargeGoal;
import ourstory.goal.SleepGoal;

public class MobGoalCommand implements BasicCommand {
	private static final Integer RADIUS = 20;

	@Override
	public void execute(CommandSourceStack cmdSource, String[] args) {
		if (!(cmdSource.getExecutor() instanceof Player)) {
			return;
		}
		LivingEntity sender = (LivingEntity) cmdSource.getExecutor();
		Server server = sender.getServer();
		Location loc = sender.getLocation();
		var bb = new BoundingBox(
				loc.x() - RADIUS, loc.y() - RADIUS, loc.z() - RADIUS,
				loc.x() + RADIUS, loc.y() + RADIUS, loc.z() + RADIUS);
		for (var entity : sender.getWorld().getNearbyEntities(bb, e -> e instanceof Cow)) {
			Cow cow = (Cow) entity;
			server.getMobGoals().addGoal(cow, 0, new SleepGoal(sender, cow));
			server.getMobGoals().addGoal(cow, 1, new ChargeGoal(sender, cow));
		}
	}
}
