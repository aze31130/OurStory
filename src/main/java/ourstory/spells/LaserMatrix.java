package ourstory.spells;

import java.util.List;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustTransition;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class LaserMatrix extends Spell {
	public LaserMatrix(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		this.caster = caster;
		this.level = level;
		this.targets = targets;
	}

	private List<Entity> targets;
	private Entity caster;
	private int laserAmount, laserRange, orientation, durationSeconds, ticks, level;
	private double rotationSpeed, currentAngle;
	private Location center;

	@Override
	public void setup() {
		this.laserAmount = 10;
		this.laserRange = 50;
		this.orientation = Math.random() < 0.5 ? -1 : 1;
		this.durationSeconds = 35;
		this.rotationSpeed = 0.6;
		this.center = caster.getLocation().clone();
		this.currentAngle = 0;
		this.ticks = 0;
	}

	@Override
	public void tick() {
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

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return ticks >= durationSeconds * 20;
	}

	// // Helper method to draw a laser
	private void drawLaser(Location start, Location end, Entity caster, List<Entity> targets) {
		int steps = 150;

		for (int i = 0; i <= steps; i++) {
			double t = (double) i / steps;
			double x = start.getX() + (end.getX() - start.getX()) * t;
			double y = start.getY() + (end.getY() - start.getY()) * t;
			double z = start.getZ() + (end.getZ() - start.getZ()) * t;

			DustTransition dustOptions = new DustTransition(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 0,
					0), 1.0F);
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
