package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.utils.EnchantItem;

public class onFinalDamage implements Listener {
	@EventHandler
	public void finalDamage(EntityDamageByEntityEvent event) {
		// Cancel if not player
		if (!(event.getDamager() instanceof Player))
			return;

		Player p = (Player) event.getDamager();

		// Compute Final Damage enchant
		ItemStack weapon = p.getInventory().getItemInMainHand();
		int totalFinalDamageLevel = EnchantItem.getEnchantAmount(weapon, "final_damage");

		double newDamage = event.getDamage() * (1 + 0.05 * totalFinalDamageLevel);

		event.setDamage(newDamage);
	}
}
