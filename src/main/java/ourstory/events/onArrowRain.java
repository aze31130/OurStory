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
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import ourstory.utils.EnchantItem;

/**
 *
 * @author aurel
 */
public class onArrowRain implements Listener {

	private final Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");
	private final NamespacedKey bowEnchantSaver = new NamespacedKey(plugin, "bowEnchantSaver");
	private final NamespacedKey bowForceSaver = new NamespacedKey(plugin, "bowForceSaver");
	private final HashMap<UUID, Integer> currentActiveArrows = new HashMap<UUID, Integer>();
	private final int maxGeneratingArrowsPerPlayer = 10;
	private final int arrowDespawnRate = 1200;
	private final int arrowLifeTime = 40;

	@EventHandler
	public void onLaunch(EntityShootBowEvent e) {
		ItemStack bow = e.getBow();
		if (bow.getType() != Material.BOW)
			return;
		if (EnchantItem.getEnchantAmount(bow, "arrow_rain") == 0)
			return;

		e.getProjectile().getPersistentDataContainer().set(bowEnchantSaver, PersistentDataType.INTEGER, EnchantItem.getEnchantAmount(e.getBow(), "arrow_rain"));
		e.getProjectile().getPersistentDataContainer().set(bowForceSaver, PersistentDataType.FLOAT, e.getForce());
	}

	@EventHandler
	public void arrowRain(ProjectileHitEvent event) {
		if (!event.getEntity().getPersistentDataContainer().has(bowEnchantSaver, PersistentDataType.INTEGER))
			return;

		Arrow arrowOrigin = (Arrow) event.getEntity();
		LivingEntity player = (LivingEntity) arrowOrigin.getShooter();
		UUID playerId = player.getUniqueId();

		int enchantLevel = arrowOrigin.getPersistentDataContainer().get(bowEnchantSaver, PersistentDataType.INTEGER);
		float force = arrowOrigin.getPersistentDataContainer().get(bowForceSaver, PersistentDataType.FLOAT);

		Entity hitty = event.getHitEntity();
		if (hitty == null)
			return;

		if (!currentActiveArrows.containsKey(playerId))
			currentActiveArrows.put(playerId, 0);
		if (currentActiveArrows.get(playerId) >= maxGeneratingArrowsPerPlayer)
			return;
		currentActiveArrows.replace(playerId, currentActiveArrows.get(playerId) + 1);
		new BukkitRunnable() {
			int counter = 0;

			@Override
			public void run() {
				if (counter >= enchantLevel) {
					currentActiveArrows.replace(playerId, currentActiveArrows.get(playerId) - 1);
					this.cancel();
					return;
				}
				Location entityLoc = event.getHitEntity().getLocation().add(0, 8, 0);
				while (!entityLoc.getBlock().isPassable())
					entityLoc.add(0, 1, 0);
				Arrow arrow = player.getWorld().spawnArrow(entityLoc, new Vector(0, -1, 0), force, 0);
				arrow.setBasePotionType((arrowOrigin).getBasePotionType());
				arrow.setLifetimeTicks(arrowDespawnRate - arrowLifeTime);
				arrow.setShooter(player);
				arrow.setDamage((arrowOrigin).getDamage());
				arrow.setWeapon((arrowOrigin).getWeapon());
				arrow.setCritical((arrowOrigin).isCritical());
				arrow.setHitSound(Sound.BLOCK_END_PORTAL_FRAME_FILL);
				arrow.setGlowing(true);
				arrow.setPickupStatus(PickupStatus.DISALLOWED);
				arrow.setPierceLevel(127);
				counter++;
			}
		}.runTaskTimer(plugin, 20, 20);
	}
}
