package ourstory.skills;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.List;

public class LaserExplosion implements Skills {

	final Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		int laserAmount = 250;
		double laserRange = 50;
		double speed = 0.3;
		double damage = 65.0;

		Location startLocation = caster.getLocation().clone().add(0, 18, 0);

		// Spawn lasers in random directions
		for (int i = 0; i < laserAmount; i++) {
			Vector direction = getRandomDirection();
			spawnLaser(startLocation, direction, laserRange, speed, damage, caster, targets);
		}
	}

	private void spawnLaser(Location startLocation, Vector direction, double range, double speed, double damage, Entity caster, List<Entity> targets) {
		Bukkit.getScheduler().runTaskTimer(p, new Runnable() {
			Location currentLocation = startLocation.clone();
			double traveledDistance = 0;

			@Override
			public void run() {
				if (traveledDistance >= range)
					return;

				currentLocation.getWorld().spawnParticle(Particle.FIREWORK, currentLocation, 0, 0, 0, 0, 0.1);

				// Check for collisions
				for (Entity entity : currentLocation.getWorld().getNearbyEntities(currentLocation, 0.5, 0.5, 0.5))
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).damage(damage);

				// Move the laser forward
				currentLocation.add(direction.clone().multiply(speed));
				traveledDistance += speed;
			}
		}, 0, 1);
	}

	private Vector getRandomDirection() {
		// Generate a random unit vector
		double x = Math.random() * 2 - 1;
		double y = Math.random() * 2 - 1;
		double z = Math.random() * 2 - 1;

		// Direct all lasers to the ground
		if (y > 0)
			y *= -1;

		// Increase the direction to the ground
		if (y > -0.5)
			y = -0.5;

		return new Vector(x, y, z).normalize();
	}
}

