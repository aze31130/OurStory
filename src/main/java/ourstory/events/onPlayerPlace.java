package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class onPlayerPlace implements Listener {
	/*
	 * Cancel the block placement if the item has enchantments
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		ItemStack itemInHand = event.getItemInHand();

		if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasEnchants()) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(Component.text("You cannot place blocks that have enchantments !").color(NamedTextColor.RED));
		}
	}
}
