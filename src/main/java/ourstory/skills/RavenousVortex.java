package ourstory.skills;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author aurel
 */
public class RavenousVortex implements Skills {

	static NamespacedKey GravityKey = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "boss_ravenouos_vortex");
	static double effectRange = 10;
	static double effectGravityRadius = 2;

	@Override
	public void cast(Entity caster, List<Entity> targets) {

		new BukkitRunnable() {
			double effectDuration = 50;
			List<Location> nearbyEntitiesLocation = caster.getNearbyEntities(effectRange, 2, effectRange)
					.stream()
					.filter(entity -> entity instanceof Player)
					.map(entity -> entity.getLocation())
					.collect(Collectors.toList());
			HashSet<Player> oldAffectedEntities = new HashSet<>();
			HashSet<Player> oldFallDownEntities = new HashSet<>();

			@Override
			public void run() {
				if (effectDuration <= 0) {
					clearEffect(oldAffectedEntities);
					clearEffect(oldFallDownEntities);
					this.cancel();
					return;
				}
				effectDuration--;
				HashSet<Player> newlyAffectedEntities = new HashSet<>();
				HashSet<Player> newlyFallDownEntities = new HashSet<>();

				for (Location effectLocation : nearbyEntitiesLocation) {
					handleEffect(oldAffectedEntities, newlyAffectedEntities, -1, 0.6, Particle.FLAME, effectLocation, 1);
					handleEffect(oldFallDownEntities, newlyFallDownEntities, 99, 3, Particle.GLOW, effectLocation.clone().add(new Location(effectLocation.getWorld(), 0, 23, 0)), 1);
					playCircleEffect(effectLocation.clone().add(new Location(effectLocation.getWorld(), 0, 23, 0)), effectGravityRadius * 1.5, Particle.GLOW);
					playCircleEffect(effectLocation.clone().add(new Location(effectLocation.getWorld(), 0, 26, 0)), effectGravityRadius * 2, Particle.GLOW);
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	public static void handleEffect(HashSet<Player> oldSet, HashSet<Player> newSet, int strength, double radiusheight, Particle part, Location loc, double radius_mult) {
		newSet.addAll(loc.getNearbyEntities(effectGravityRadius, radiusheight, effectGravityRadius)
				.stream()
				.filter(entity -> entity instanceof Player)
				.map(entity -> (Player) entity)
				.collect(Collectors.toCollection(HashSet::new)));
		HashSet<Player> difference = new HashSet<>(oldSet);
		difference.removeAll(newSet);
		difference.forEach(entity -> {
			removeEffect(entity);
		});
		oldSet.clear();
		oldSet.addAll(newSet);
		newSet.forEach(entity -> {
			applyEffect(entity, strength);
		});
		playCircleEffect(loc.clone().add(0, -radiusheight, 0), effectGravityRadius * radius_mult, part);
	}

	public static void clearEffect(HashSet<Player> oldSet) {
		oldSet.forEach(entity -> {
			removeEffect(entity);
		});
	}

	public static void removeEffect(Player entity) {
		if (entity.getAttribute(Attribute.GENERIC_GRAVITY) != null)
			entity.getAttribute(Attribute.GENERIC_GRAVITY).removeModifier(GravityKey);
	}

	public static void applyEffect(Player entity, int strength) {
		removeEffect(entity);

		AttributeModifier buff = new AttributeModifier(GravityKey, strength, AttributeModifier.Operation.ADD_NUMBER);
		entity.getAttribute(Attribute.GENERIC_GRAVITY).addModifier(buff);
	}

	private static void playCircleEffect(Location loc, double radius, Particle particle) {
		for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 45) {
			final double x = radius * Math.cos(angle);
			final double z = radius * Math.sin(angle);

			loc.add(x, 0, z);
			loc.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, 0);
			loc.subtract(x, 0, z);
		}
	}
}
