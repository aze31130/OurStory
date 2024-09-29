package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ourstory.storage.Storage;

public class onEntityDeath implements Listener {
	@EventHandler
	public void entityDeath(EntityDeathEvent entity) {
		// Call on death boss method
		if (entity.getEntity().getScoreboardTags().contains("isBoss")) {
			Storage s = Storage.getInstance();
			s.bossInstance.monster.onDeath(entity.getEntity().getKiller());
		}

		// Compute Leech enchant
		// TODO

		/*
		 * if (event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantments()
		 * .containsKey(Enchantment.LOOT_BONUS_MOBS)) { LootingLevel =
		 * event.getEntity().getKiller().getInventory().getItemInMainHand()
		 * .getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS); }
		 */
	}
}
