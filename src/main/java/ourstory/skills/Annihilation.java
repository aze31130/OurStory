package ourstory.skills;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Annihilation implements Skills {
	/*
	 * Special skill, last skill only used in chaos mode. Triggers at 20% HP remaining. The boss invokes
	 * a flame circle arround him and deals a lot of damages.
	 */
	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		Location loc = caster.getLocation();

		int pointCount = 6;
		double radius = 20;
		int totalSteps = 40;

		for (int i = 0; i < pointCount; i++) {
			double angle = 2 * Math.PI * i / pointCount;

			new BukkitRunnable() {
				int t = 0;

				@Override
				public void run() {
					if (t > totalSteps) {
						this.cancel();
						return;
					}

					double progress = (double) t / totalSteps;
					double easedProgress = 1 - Math.pow(1 - progress, 2);

					double currentRadius = radius * (1 - easedProgress);
					double currentAngle = angle + Math.PI * easedProgress;

					double x = Math.cos(currentAngle) * currentRadius;
					double z = Math.sin(currentAngle) * currentRadius;
					Location particleLoc = loc.clone().add(x, 0, z);

					caster.getWorld().spawnParticle(Particle.LARGE_SMOKE, particleLoc, 0, 0, 0, 0, 0);
					t++;
				}
			}.runTaskTimer(p, i * 2L, 2L);
		}

		new BukkitRunnable() {
			double radiusIncrease = 0.5;
			double startRadius = 0.5;
			double endRadius = 20;

			@Override
			public void run() {
				if (startRadius >= endRadius) {
					this.cancel();
					return;
				}

				playCircleEffect(caster, loc, startRadius);
				startRadius += radiusIncrease;

				List<Entity> nearbyEntities = caster.getNearbyEntities(startRadius, 2, startRadius);
				for (Entity entity : nearbyEntities) {
					// Push the entity
					Vector direction = entity.getLocation().toVector().subtract(loc.toVector()).normalize();
					entity.setVelocity(direction.multiply(0.7));

					// Damage if player
					if (entity instanceof Player) {
						Player player = (Player) entity;
						player.damage(7.0);
					}
				}
			}
		}.runTaskTimer(plugin, 100, 1);
	}

	private static void playCircleEffect(Entity caster, Location loc, double radius) {
		for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 150) {
			final double x = radius * Math.cos(angle);
			final double z = radius * Math.sin(angle);

			loc.add(x, 0, z);
			caster.getWorld().spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0);
			loc.subtract(x, 0, z);
		}
	}
}
