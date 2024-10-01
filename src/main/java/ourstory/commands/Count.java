package ourstory.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.utils.Permissions;

public class Count implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.count"))
			return;

		if (!(sender.getSender() instanceof org.bukkit.entity.Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		org.bukkit.entity.Player p = (org.bukkit.entity.Player) sender.getSender();
		Material material = Material.getMaterial(args[0]);
		int count = countItems(p, material);
		if (count == 0) {
			sender.getSender().sendMessage("You don't have any " + args[0] + " in your Inventory.");
			return;
		}
		sender.getSender().sendMessage("You have " + count + " " + args[0] + " in your Inventory.");
	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		List<String> suggestions = new ArrayList<>();
		for (Material material : Material.values()) {
			if (material.toString().startsWith(args[0].toUpperCase())) {
				suggestions.add(material.toString());
			}
		}
		return suggestions;
	}

	private static int countItems(org.bukkit.entity.Player player, Material material) {

		int count = 0;

		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) {
				continue;
			}
			if (item.getType().equals(material)) {
				count += item.getAmount();
			}
		}

		return count;
	}
}
