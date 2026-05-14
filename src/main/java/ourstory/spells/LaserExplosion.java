package ourstory.spells;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class LaserExplosion extends Spell {

	public LaserExplosion(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		this.caster = caster;
		this.level = level;
	}

	private Entity caster;
	private int level;
	private int laserAmount;
	private double laserRange;
	private double speed;
	private double damage;
	private Location startLocation;
	private double traveledDistance;
	List<Laser> lstLasers = new ArrayList<Laser>();

	private record Laser(Location location, Vector direction) {
	}


	@Override
	public void setup() {
		this.laserAmount = 250 + 50 * level;
		this.laserRange = 30;
		this.speed = 0.3;
		this.damage = 65.0 + 20 * level;
		this.startLocation = caster.getLocation().clone().add(0, 18, 0);
		this.traveledDistance = 0;
		// lst of lasers
		for (int i = 0; i < laserAmount; i++) {
			lstLasers.add(new Laser(startLocation.clone(), getRandomDirection()));
		}
	}

	@Override
	public void tick() {
		// Spawn lasers in random directions
		for (Laser l : lstLasers) {
			spawnLaser(l, laserRange, speed, damage, caster, targets);
		}
		traveledDistance += speed;
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return traveledDistance >= laserRange;
	}

	private void spawnLaser(Laser l, double range, double speed, double damage, Entity caster, List<Entity> targets) {


		l.location.getWorld().spawnParticle(Particle.FIREWORK, l.location, 0, 0, 0, 0, 0.1);
		// Check for collisions
		for (Entity entity : l.location.getWorld().getNearbyEntities(l.location, 0.5, 0.5, 0.5))
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).damage(damage);

		// Move the laser forward
		l.location.add(l.direction.clone().multiply(speed));
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

