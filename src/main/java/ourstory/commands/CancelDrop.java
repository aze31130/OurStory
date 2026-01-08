package ourstory.commands;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class CancelDrop implements BasicCommand {

	private final Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");
	private final NamespacedKey ItemIsLocked = new NamespacedKey(plugin, "locked");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		Player player = (Player) sender.getSender();
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();

		boolean isLocked = item.getPersistentDataContainer().getOrDefault(ItemIsLocked, PersistentDataType.BOOLEAN, false);

		meta.getPersistentDataContainer().set(ItemIsLocked, PersistentDataType.BOOLEAN, !isLocked);
		item.setItemMeta(meta);
	}
}
