package ourstory.spells;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;

public class LaserExplosion extends Spell {

	public LaserExplosion(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		this.caster = caster;
		this.level = level;
	}

	private Entity caster;
	private int level;
	private int laserAmount, cpt;
	private double laserRange;
	private double speed;
	private double damage;
	private Location startLocation;
	private Location currentLocation;
	private double traveledDistance;



	@Override
	public void setup() {
		this.laserAmount = 250;
		this.laserRange = 50;
		this.speed = 0.3;
		this.damage = 65.0 + 20 * level;
		this.cpt = 0;
		this.startLocation = caster.getLocation().clone().add(0, 18, 0);
		this.currentLocation = startLocation.clone();
		Bukkit.getServer().broadcast(Component.text("setup ended"));

	}

	@Override
	public void tick() {
		// Spawn lasers in random directions
		Vector direction = getRandomDirection();
		spawnLaser(startLocation, direction, laserRange, speed, damage, caster, targets);
		cpt++;
	}

	@Override
	public void stop() {

		Bukkit.getServer().broadcast(Component.text("stop ended"));
	}

	@Override
	public boolean shouldStop() {
		return cpt > laserAmount;
	}

	private void spawnLaser(Location startLocation, Vector direction, double range, double speed, double damage, Entity caster, List<Entity> targets) {

		this.traveledDistance = 0;
		if (traveledDistance >= range) {
			Bukkit.getServer().broadcast(Component.text("nah-uh"));
			return;
		}
		currentLocation.getWorld().spawnParticle(Particle.FIREWORK, currentLocation, 0, 0, 0, 0, 0.1);

		// Check for collisions
		for (Entity entity : currentLocation.getWorld().getNearbyEntities(currentLocation, 0.5, 0.5, 0.5))
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).damage(damage);

		// Move the laser forward
		currentLocation.add(direction.clone().multiply(speed));
		traveledDistance += speed;
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

