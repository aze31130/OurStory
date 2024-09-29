package ourstory.events;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.storage.Storage;

public class onEntityHit implements Listener {
	@EventHandler
	public void entityHit(EntityDamageByEntityEvent entity) {
		// Call onHit method for boss monsters
		if (entity.getEntity().getScoreboardTags().contains("isBoss")) {
			Storage s = Storage.getInstance();
			s.bossInstance.monster.onHit(entity);
		}

		if ((entity.getDamager() instanceof Snowball))
			entity.setDamage(1);

		if ((entity.getDamager() instanceof Egg))
			entity.setDamage(3);
	}

	@EventHandler
	public void onEntityDamagedByPlayer(EntityDamageByEntityEvent event) {
		// Cancel if not player
		if (!(event.getDamager() instanceof Player))
			return;

		Player p = (Player) event.getDamager();

		// Compute Final Damage enchant
		ItemStack weapon = p.getInventory().getItemInMainHand();
		int totalFinalDamageLevel = 0;

		if (weapon != null) {
			Map<Enchantment, Integer> enchants = weapon.getEnchantments();

			for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
				Enchantment enchantment = entry.getKey();
				int level = entry.getValue();

				if (enchantment.getKey().getKey().equals("final_damage"))
					totalFinalDamageLevel += level;
			}
		}

		double newDamage = event.getDamage() * (1 + 0.05 * totalFinalDamageLevel);

		event.setDamage(newDamage);


		// LivingEntity entity = (LivingEntity) e.getEntity();
		// org.bukkit.entity.Player p = (org.bukkit.entity.Player) e.getDamager();
		// Storage s = Storage.getInstance();

		// // Delete old bar and cancel runnable if exists
		// if (s.healthBarHashMap.containsKey(p.getUniqueId())) {
		// BossBar oldBar = s.healthBarHashMap.get(p.getUniqueId());
		// BukkitRunnable runnable = s.healthBarRunnables.get(p.getUniqueId());

		// oldBar.removeAll();
		// runnable.cancel();

		// s.healthBarHashMap.remove(p.getUniqueId());
		// s.healthBarRunnables.remove(p.getUniqueId());
		// }

		// // Create the bar
		// BossBar healthBar = Bukkit.createBossBar(
		// HealthBar.getTitle(entity), HealthBar.getColor(e.getEntity()), BarStyle.SOLID, new BarFlag[0]);
		// healthBar.addPlayer(p);
		// s.healthBarHashMap.put(p.getUniqueId(), healthBar);

		// // Set bar to current health %
		// double remainingLife = entity.getHealth() - e.getDamage();
		// if (remainingLife > 0)
		// healthBar.setProgress(remainingLife /
		// entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());


		// BukkitRunnable bukkitRunnable = new BukkitRunnable() {
		// public void run() {
		// if (s.healthBarHashMap.containsKey(p.getUniqueId())) {
		// BossBar oldBar = s.healthBarHashMap.get(p.getUniqueId());
		// BukkitRunnable runnable = s.healthBarRunnables.get(p.getUniqueId());

		// oldBar.removeAll();
		// runnable.cancel();

		// s.healthBarHashMap.remove(p.getUniqueId());
		// s.healthBarRunnables.remove(p.getUniqueId());
		// }
		// }
		// };
		// bukkitRunnable.runTaskLater(Bukkit.getPluginManager().getPlugin("Ourstory"), (long) (10 * 20));
	}
}
