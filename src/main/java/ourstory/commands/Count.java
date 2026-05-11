package ourstory.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.utils.Permissions;

public class Count implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.count"))
			return;

		if (!(sender.getSender() instanceof Player p)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Material material = null;

		if (args.length > 0) {
			material = Material.matchMaterial(args[0]);
		} else {
			ItemStack item = p.getInventory().getItemInMainHand();
			if (!item.isEmpty())
				material = item.getType();
		}

		if (material == null) {
			sender.getSender().sendMessage(Component.text("You need to provide a valid item name !").color(NamedTextColor.RED));
			return;
		}

		int count = countItems(p, material);
		if (count == 0) {
			sender.getSender().sendMessage("You don't have any " + material.toString() + " in your Inventory.");
			return;
		}
		sender.getSender().sendMessage("You have " + count + " " + material.toString() + " in your Inventory.");
	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		String prefix = args.length == 0 ? "" : args[0].toUpperCase(Locale.ROOT);
		List<String> suggestions = new ArrayList<>();
		for (Material material : Material.values()) {
			String name = material.toString();
			if (prefix.isEmpty() || name.startsWith(prefix))
				suggestions.add(name);
		}
		return suggestions;
	}

	private static int countItems(Player player, Material material) {
		int count = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null)
				continue;
			if (item.getType().equals(material))
				count += item.getAmount();
		}
		return count;
	}
}
