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
		if (!(entity.getEntity().getKiller() instanceof Player killer))
			return;

		ItemStack weapon = killer.getInventory().getItemInMainHand();
		int totalLeechLevel = EnchantItem.getEnchantAmount(weapon, "leech");
		if (totalLeechLevel <= 0)
			return;

		killer.heal(totalLeechLevel / 2.0);
	}
}
