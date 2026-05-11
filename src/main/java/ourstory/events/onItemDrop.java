package ourstory.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;

public class onItemDrop implements Listener {

	private NamespacedKey itemIsLockedKey() {
		return new NamespacedKey(Main.plugin, "locked");
	}

	@EventHandler
	public void dropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Item item = event.getItemDrop();

		ItemMeta meta = item.getItemStack().getItemMeta();
		if (meta == null)
			return;

		boolean isLocked = meta.getPersistentDataContainer()
				.getOrDefault(itemIsLockedKey(), PersistentDataType.BOOLEAN, false);

		if (!isLocked)
			return;

		event.setCancelled(true);
		player.sendActionBar(Component.text("You can't drop : " + item.getName() + ", you've locked it !").color(NamedTextColor.YELLOW));
	}
}
