package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;


/**
 *
 * @author NaNI7823
 */

public class onItemDrop implements Listener {

	private final Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");
	private final NamespacedKey ItemIsLocked = new NamespacedKey(plugin, "ItemIsLocked");

	@EventHandler
	public void dropItem(PlayerDropItemEvent event) {

		Player player = event.getPlayer();
		Item item = event.getItemDrop();

		boolean isLocked = item.getItemStack().getItemMeta().getPersistentDataContainer().getOrDefault(ItemIsLocked, PersistentDataType.BOOLEAN, false);

		if (isLocked) {
			event.setCancelled(true);
			player.sendActionBar(Component.text("You can't drop : " + event.getItemDrop().getName() + ", you've locked it already !").color(NamedTextColor.YELLOW));
		}
	}
}
