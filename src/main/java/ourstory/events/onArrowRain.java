package ourstory.events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ourstory.Main;
import ourstory.utils.EnchantItem;

/**
 *
 * @author aurel
 */
public class onArrowRain implements Listener {

	private static final int MAX_GENERATING_ARROWS_PER_PLAYER = 10;
	private static final int ARROW_DESPAWN_RATE = 1200;
	private static final int ARROW_LIFE_TIME = 40;

	private final Map<UUID, Integer> currentActiveArrows = new HashMap<>();

	private NamespacedKey bowEnchantSaver() {
		return new NamespacedKey(Main.plugin, "bowEnchantSaver");
	}

	private NamespacedKey bowForceSaver() {
		return new NamespacedKey(Main.plugin, "bowForceSaver");
	}

	@EventHandler
	public void onLaunch(EntityShootBowEvent e) {
		ItemStack bow = e.getBow();
		if (bow == null || bow.getType() != Material.BOW)
			return;
		int enchantAmount = EnchantItem.getEnchantAmount(bow, "arrow_rain");
		if (enchantAmount == 0)
			return;

		e.getProjectile().getPersistentDataContainer().set(bowEnchantSaver(), PersistentDataType.INTEGER, enchantAmount);
		e.getProjectile().getPersistentDataContainer().set(bowForceSaver(), PersistentDataType.FLOAT, e.getForce());
	}

	@EventHandler
	public void arrowRain(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow arrowOrigin))
			return;
		if (!arrowOrigin.getPersistentDataContainer().has(bowEnchantSaver(), PersistentDataType.INTEGER))
			return;

		if (!(arrowOrigin.getShooter() instanceof LivingEntity shooter))
			return;
		UUID shooterId = shooter.getUniqueId();

		Integer enchantLevel = arrowOrigin.getPersistentDataContainer().get(bowEnchantSaver(), PersistentDataType.INTEGER);
		Float force = arrowOrigin.getPersistentDataContainer().get(bowForceSaver(), PersistentDataType.FLOAT);
		if (enchantLevel == null || force == null)
			return;

		Entity hitEntity = event.getHitEntity();
		if (hitEntity == null)
			return;

		Location anchor = hitEntity.getLocation();

		int active = currentActiveArrows.getOrDefault(shooterId, 0);
		if (active >= MAX_GENERATING_ARROWS_PER_PLAYER)
			return;
		currentActiveArrows.put(shooterId, active + 1);

		final int levels = enchantLevel;
		final float launchForce = force;
		new BukkitRunnable() {
			int counter = 0;

			@Override
			public void run() {
				if (counter >= levels || !shooter.isValid()) {
					Integer remaining = currentActiveArrows.get(shooterId);
					if (remaining != null) {
						int newValue = remaining - 1;
						if (newValue <= 0)
							currentActiveArrows.remove(shooterId);
						else
							currentActiveArrows.put(shooterId, newValue);
					}
					this.cancel();
					return;
				}

				Location entityLoc = anchor.clone().add(0, 8, 0);
				int safety = 0;
				while (!entityLoc.getBlock().isPassable() && safety++ < 64)
					entityLoc.add(0, 1, 0);

				Arrow arrow = shooter.getWorld().spawnArrow(entityLoc, new Vector(0, -1, 0), launchForce, 0);
				arrow.setBasePotionType(arrowOrigin.getBasePotionType());
				arrow.setLifetimeTicks(ARROW_DESPAWN_RATE - ARROW_LIFE_TIME);
				arrow.setShooter(shooter);
				arrow.setDamage(arrowOrigin.getDamage());
				arrow.setWeapon(arrowOrigin.getWeapon());
				arrow.setCritical(arrowOrigin.isCritical());
				arrow.setHitSound(Sound.BLOCK_END_PORTAL_FRAME_FILL);
				arrow.setGlowing(true);
				arrow.setPickupStatus(PickupStatus.DISALLOWED);
				arrow.setPierceLevel(127);
				counter++;
			}
		}.runTaskTimer(Main.plugin, 20, 20);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		currentActiveArrows.remove(event.getPlayer().getUniqueId());
	}
}
