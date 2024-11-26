package ourstory.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import ourstory.utils.EnchantItem;

/**
 *
 * @author aurel
 */
public class onArrowRain implements Listener {

	private final NamespacedKey BowEnchantSaver = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "BowEnchantSaver");
	private final NamespacedKey BowForceSaver = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "BowForceSaver");

	@EventHandler
	public void onLaunch(EntityShootBowEvent e) {
		if (e.getBow().getType() != Material.BOW)
			return;
		if (EnchantItem.getEnchantAmount(e.getBow(), "arrow_rain") == 0)
			return;

		e.getProjectile().getPersistentDataContainer().set(BowEnchantSaver, PersistentDataType.INTEGER, EnchantItem.getEnchantAmount(e.getBow(), "arrow_rain"));
		e.getProjectile().getPersistentDataContainer().set(BowForceSaver, PersistentDataType.FLOAT, e.getForce());
	}

	@EventHandler
	public void arrowRain(ProjectileHitEvent event) {

		if (!event.getEntity().getPersistentDataContainer().has(BowEnchantSaver, PersistentDataType.INTEGER))
			return;

		int enchant_level = event.getEntity().getPersistentDataContainer().get(BowEnchantSaver, PersistentDataType.INTEGER);
		float force = event.getEntity().getPersistentDataContainer().get(BowForceSaver, PersistentDataType.FLOAT);

		Player player = (Player) event.getEntity().getShooter();
		Entity hitty = event.getHitEntity();
		if (hitty == null)
			return;

		new BukkitRunnable() {
			int counter = 0;

			@Override
			public void run() {
				if (counter >= enchant_level) {
					this.cancel();
					return;
				}
				Location entityloc = event.getHitEntity().getLocation().add(0, 8, 0);
				while (!entityloc.getBlock().isPassable())
					entityloc.add(0, 1, 0);
				Arrow arrow = player.getWorld().spawnArrow(entityloc, new Vector(0, -1, 0), force, 0);
				arrow.setBasePotionType(((Arrow) event.getEntity()).getBasePotionType());
				arrow.setLifetimeTicks(200);
				arrow.setShooter(player);
				arrow.setDamage(((Arrow) event.getEntity()).getDamage());
				arrow.setWeapon(((Arrow) event.getEntity()).getWeapon());
				arrow.setCritical(((Arrow) event.getEntity()).isCritical());
				arrow.setHitSound(Sound.BLOCK_END_PORTAL_FRAME_FILL);
				arrow.setGlowing(true);
				counter++;
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("Ourstory"), 20L, 20L);
	}
}
