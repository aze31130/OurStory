package ourstory.commands;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;
import ourstory.utils.Permissions;

public class CancelDrop implements BasicCommand {

	private NamespacedKey itemIsLockedKey() {
		return new NamespacedKey(Main.plugin, "locked");
	}

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.itemlock"))
			return;

		if (!(sender.getSender() instanceof Player player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		ItemStack item = player.getInventory().getItemInMainHand();
		if (item.isEmpty()) {
			player.sendMessage(Component.text("You must hold an item to lock or unlock it.").color(NamedTextColor.RED));
			return;
		}

		ItemMeta meta = item.getItemMeta();
		if (meta == null) {
			player.sendMessage(Component.text("This item cannot be locked.").color(NamedTextColor.RED));
			return;
		}

		NamespacedKey key = itemIsLockedKey();
		boolean isLocked = meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false);
		meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, !isLocked);
		item.setItemMeta(meta);

		player.sendMessage(Component.text(
				isLocked ? "Item drop-lock removed." : "Item is now drop-locked.")
				.color(isLocked ? NamedTextColor.YELLOW : NamedTextColor.GREEN));
	}
}
