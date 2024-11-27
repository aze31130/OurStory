package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.utils.EnchantItem;

public class onEntityDeath implements Listener {
	@EventHandler
	public void entityDeath(EntityDeathEvent entity) {
		// Cancel if not player
		if (!(entity.getEntity().getKiller() instanceof Player))
			return;

		Player killer = (Player) entity.getEntity().getKiller();

		// Compute Leech enchant
		ItemStack weapon = killer.getInventory().getItemInMainHand();
		int totalLeechLevel = EnchantItem.getEnchantAmount(weapon, "leech");

		killer.heal(totalLeechLevel / 2);
	}
}
