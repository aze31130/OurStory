package ourstory.events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
	private final HashMap<UUID, Integer> current_active_arrows = new HashMap();
	private final int maxGeneratingArrowsPerPlayer = 10;


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
	public void arrowRain(ProjectileHitEvent event) throws IllegalAccessException {

		if (!event.getEntity().getPersistentDataContainer().has(BowEnchantSaver, PersistentDataType.INTEGER))
			return;

		Arrow arroworigin = (Arrow) event.getEntity();
		LivingEntity player = (LivingEntity) arroworigin.getShooter();
		UUID player_id = player.getUniqueId();

		int enchant_level = arroworigin.getPersistentDataContainer().get(BowEnchantSaver, PersistentDataType.INTEGER);
		float force = arroworigin.getPersistentDataContainer().get(BowForceSaver, PersistentDataType.FLOAT);

		Entity hitty = event.getHitEntity();
		if (hitty == null)
			return;

		if (!current_active_arrows.containsKey(player_id))
			current_active_arrows.put(player_id, 0);
		if (current_active_arrows.get(player_id) >= maxGeneratingArrowsPerPlayer)
			return;
		current_active_arrows.replace(player_id, current_active_arrows.get(player_id) + 1);
		new BukkitRunnable() {
			int counter = 0;

			@Override
			public void run() {
				if (counter >= enchant_level) {
					current_active_arrows.replace(player_id, current_active_arrows.get(player_id) - 1);
					this.cancel();
					return;
				}
				Location entityloc = event.getHitEntity().getLocation().add(0, 8, 0);
				while (!entityloc.getBlock().isPassable())
					entityloc.add(0, 1, 0);
				Arrow arrow = player.getWorld().spawnArrow(entityloc, new Vector(0, -1, 0), force, 0);
				arrow.setBasePotionType((arroworigin).getBasePotionType());
				arrow.setLifetimeTicks(1160); // Default maxTime : 1200. i.e arrows will last 2 seconds ON THE GROUND
				arrow.setShooter(player);
				arrow.setDamage((arroworigin).getDamage());
				arrow.setWeapon((arroworigin).getWeapon());
				arrow.setCritical((arroworigin).isCritical());
				arrow.setHitSound(Sound.BLOCK_END_PORTAL_FRAME_FILL);
				arrow.setGlowing(true);
				arrow.setPickupStatus(PickupStatus.DISALLOWED);
				arrow.setPierceLevel(127);
				counter++;
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("Ourstory"), 20L, 20L);
	}
}
