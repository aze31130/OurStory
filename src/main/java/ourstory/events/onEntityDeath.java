package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.storage.Storage;
import ourstory.utils.EnchantItem;

public class onEntityDeath implements Listener {
	@EventHandler
	public void entityDeath(EntityDeathEvent entity) {
		// Cancel if not player
		if (!(entity.getEntity().getKiller() instanceof Player))
			return;

		Player killer = (Player) entity.getEntity().getKiller();

		// Call on death boss method
		// if (entity.getEntity().getScoreboardTags().contains("isBoss")) {
		// Storage s = Storage.getInstance();
		// s.bossInstance.monster.onDeath(killer);
		// }


		// Compute Leech enchant
		ItemStack weapon = killer.getInventory().getItemInMainHand();
		int totalLeechLevel = EnchantItem.getEnchantAmount(weapon, "leech");

		killer.heal(totalLeechLevel / 2);



		/*
		 * if (event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantments()
		 * .containsKey(Enchantment.LOOT_BONUS_MOBS)) { LootingLevel =
		 * event.getEntity().getKiller().getInventory().getItemInMainHand()
		 * .getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS); }
		 */
	}
}
