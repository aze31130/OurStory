package ourstory.events;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
	public void onEntityDamagedByPlayer(EntityDamageByEntityEvent e) {
		return;
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
