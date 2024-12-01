package ourstory.skills;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustTransition;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;

public class LaserMatrix implements Skills {

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		int laserAmount = 10;
		int laserRange = 35;
		int orientation = Math.random() < 0.5 ? -1 : 1;
		int durationSeconds = 35;
		double rotationSpeed = 0.6;

		Location center = caster.getLocation();

		new BukkitRunnable() {
			double currentAngle = 0;
			int ticks = 0;

			@Override
			public void run() {
				if (ticks >= durationSeconds * 20) {
					cancel();
					return;
				}

				// Rotate lasers
				for (int i = 0; i < laserAmount; i++) {
					double laserAngle = currentAngle + (360.0 / laserAmount) * i;
					double radians = Math.toRadians(laserAngle);

					double x = Math.cos(radians) * laserRange;
					double z = Math.sin(radians) * laserRange;

					Location laserEnd = center.clone().add(x, 0, z);

					// Show the laser with particles
					drawLaser(center, laserEnd, caster, targets);
				}

				// Update rotation
				currentAngle += rotationSpeed * orientation;
				ticks++;
			}
		}.runTaskTimer(plugin, 0, 1); // Schedule task, runs every tick (1 tick = 1/20 second)
	}

	// Helper method to draw a laser
	private void drawLaser(Location start, Location end, Entity caster, List<Entity> targets) {
		int steps = 150;

		for (int i = 0; i <= steps; i++) {
			double t = (double) i / steps;
			double x = start.getX() + (end.getX() - start.getX()) * t;
			double y = start.getY() + (end.getY() - start.getY()) * t;
			double z = start.getZ() + (end.getZ() - start.getZ()) * t;

			DustTransition dustOptions = new DustTransition(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 0, 0), 1.0F);
			Location particleLocation = new Location(start.getWorld(), x, y, z);

			start.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, x, y, z, 1, dustOptions);

			// Damage targeted entities
			for (Entity target : targets) {
				if (!(target instanceof LivingEntity))
					continue;

				if (particleLocation.distance(target.getLocation()) <= 1.0) {
					LivingEntity livingTarget = (LivingEntity) target;
					livingTarget.damage(40.0, caster);
				}
			}
		}
	}
}
