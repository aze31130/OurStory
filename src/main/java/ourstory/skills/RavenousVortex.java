package ourstory.skills;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author aurel
 */
public class RavenousVortex implements Skills {

	private final static NamespacedKey GravityKey = new NamespacedKey(plugin, "boss_ravenous_vortex");
	private final static double targetRadius = 2;
	private final static double gravityUplift = -1;
	private final static double gravityDownlift = 99;
	private final static long skillDuration = 10;
	private final static long skillActivationDelay = 50;

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		List<Location> targetLocations = new ArrayList<>();

		// Copies locations where the skill will apply
		for (Entity target : targets) {
			Location targetLocation = target.getLocation().clone();
			targetLocations.add(targetLocation);
		}

		// Play flame effect as a warning for players
		new BukkitRunnable() {
			long timer = skillActivationDelay;

			@Override
			public void run() {
				if (timer <= 0) {
					this.cancel();
					return;
				}
				// Spawns circles where the skill will apply
				for (Location location : targetLocations)
					playCircleEffect(location, targetRadius, Particle.FLAME);

				timer--;
			}
		}.runTaskTimer(plugin, 0, 1);

		// Executed later to let the player run outside the circle
		new BukkitRunnable() {
			@Override
			public void run() {
				/*
				 * At this point, we can get all entity that didn't dodge the skill.
				 */
				List<Entity> realTargets = new ArrayList<>();

				for (Location targetLocation : targetLocations)
					realTargets.addAll(targetLocation.getNearbyEntities(targetRadius, 3, targetRadius));

				// Schedule uplift
				new BukkitRunnable() {
					@Override
					public void run() {
						for (Entity e : realTargets)
							applyGravity(e, gravityUplift);

					}
				}.runTaskLater(plugin, 0);

				// Schedule downlift
				new BukkitRunnable() {
					@Override
					public void run() {
						for (Entity e : realTargets)
							applyGravity(e, gravityDownlift);
					}
				}.runTaskLater(plugin, skillDuration);


				// Schedule removal of the gravity effect
				new BukkitRunnable() {
					@Override
					public void run() {
						for (Entity e : realTargets)
							clearGravity(e);
					}
				}.runTaskLater(plugin, skillDuration * 2);
			}
		}.runTaskLater(plugin, skillActivationDelay);
	}

	private static void applyGravity(Entity target, double strength) {
		clearGravity(target);

		AttributeModifier gravityModifier = new AttributeModifier(GravityKey, strength, AttributeModifier.Operation.ADD_NUMBER);
		((Attributable) target).getAttribute(Attribute.GENERIC_GRAVITY).addModifier(gravityModifier);

		playCircleEffect(target.getLocation(), targetRadius * 2, Particle.GLOW);
	}

	private static void clearGravity(Entity target) {
		if (((Attributable) target).getAttribute(Attribute.GENERIC_GRAVITY) != null)
			((Attributable) target).getAttribute(Attribute.GENERIC_GRAVITY).removeModifier(GravityKey);
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
