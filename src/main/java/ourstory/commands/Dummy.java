package ourstory.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import ourstory.utils.Permissions;

public class Dummy implements BasicCommand {

	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.dummy"))
			return;

		if (!(sender.getSender() instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player player = (Player) sender.getSender();
		World w = player.getWorld();

		ArmorStand dummy = (ArmorStand) w.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

		dummy.customName(Component.text("DPS Dummy"));
		dummy.setCustomNameVisible(true);

		dummy.setMetadata("isDummy", new FixedMetadataValue(p, true));
	}
}
