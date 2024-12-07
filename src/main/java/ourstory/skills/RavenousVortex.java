package ourstory.skills;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
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

	final static NamespacedKey GravityKey = new NamespacedKey(plugin, "boss_ravenous_vortex");
	final static double effectRange = 10;
	final static double effectGravityRadius = 2;
	final static double skillDuration = 40;

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		List<Location> targetsLocation = new ArrayList<>();

		for (Entity target : targets)
			targetsLocation.add(target.getLocation().clone());

		new BukkitRunnable() {
			double effectDuration = skillDuration;
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

				for (Location location : targetsLocation) {

					handleEffect(oldAffectedEntities, newlyAffectedEntities, -1, 0.6, Particle.FLAME, location, 1);
					handleEffect(oldFallDownEntities, newlyFallDownEntities, 99, 3, Particle.GLOW, location.clone().add(new Location(location.getWorld(), 0, 23, 0)), 1);
					playCircleEffect(location.clone().add(new Location(location.getWorld(), 0, 23, 0)), effectGravityRadius * 1.5, Particle.GLOW);
					playCircleEffect(location.clone().add(new Location(location.getWorld(), 0, 26, 0)), effectGravityRadius * 2, Particle.GLOW);
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	public static void handleEffect(HashSet<Player> oldSet, HashSet<Player> newSet, int strength, double radiusheight, Particle part, Location loc, double radius_mult) {
		newSet.addAll(loc.getNearbyEntities(RavenousVortex.effectGravityRadius, radiusheight, RavenousVortex.effectGravityRadius)
				.stream()
				.filter(entity -> entity instanceof Player)
				.map(entity -> (Player) entity)
				.collect(Collectors.toCollection(HashSet::new)));
		HashSet<Player> difference = new HashSet<>(oldSet);
		difference.removeAll(newSet);
		difference.forEach(entity -> {
			clearEffect(entity);
		});
		oldSet.clear();
		oldSet.addAll(newSet);
		newSet.forEach(entity -> {
			applyEffect(entity, strength);
		});
		playCircleEffect(loc, effectGravityRadius * radius_mult, part);
	}

	public static void clearEffect(Collection<Player> oldSet) {
		for (Player p : oldSet)
			clearEffect(p);
	}

	public static void clearEffect(Player entity) {
		if (entity.getAttribute(Attribute.GENERIC_GRAVITY) != null)
			entity.getAttribute(Attribute.GENERIC_GRAVITY).removeModifier(GravityKey);
	}

	public static void applyEffect(Player entity, int strength) {
		clearEffect(entity);

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
