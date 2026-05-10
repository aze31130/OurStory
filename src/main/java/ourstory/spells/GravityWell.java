package ourstory.spells;

import org.bukkit.util.Vector;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;


public class GravityWell extends Spell {
	private Entity caster;
	private double maxRadius;
	private double pullStrength;
	private double explosionDamage;
	private double shrinkSpeedBase;
	private double acceleration;
	private Location startLocation;

	private double currentRadius;
	private double shrinkSpeed;

	public GravityWell(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		this.caster = caster;
	}

	@Override
	public void setup() {
		this.maxRadius = 1 + (level * 1.2);
		this.pullStrength = 0.08 + (level * 0.05);
		this.explosionDamage = (3.5f * level) / 2;

		this.shrinkSpeedBase = 0.02 + (level * 0.01);
		this.acceleration = 0.002 + (level * 0.001);

		this.startLocation = caster.getLocation();

		this.currentRadius = maxRadius;
		this.shrinkSpeed = shrinkSpeedBase;
	}

	@Override
	public void tick() {
		// Attraction
		for (Entity entity : startLocation.getWorld().getNearbyEntities(
				startLocation, maxRadius, maxRadius, maxRadius)) {

			if (!(entity instanceof LivingEntity target))
				continue;
			if (target.equals(caster))
				continue;
			if (target.getLocation().distanceSquared(startLocation) > currentRadius * currentRadius)
				continue;

			Vector pull = startLocation.toVector()
					.subtract(target.getLocation().toVector())
					.normalize()
					.multiply(pullStrength);
			target.setVelocity(target.getVelocity().add(pull));
		}

		// Cercle au sol
		drawGroundCircle(
				startLocation,
				currentRadius,
				Particle.END_ROD,
				140);

		// Fermeture + accélération
		currentRadius -= shrinkSpeed;
		shrinkSpeed += acceleration;
	}

	@Override
	public void stop() {
		explode(startLocation, explosionDamage, maxRadius, caster);
	}

	@Override
	public boolean shouldStop() {
		return currentRadius <= 0.3;
	}

	private void drawGroundCircle(Location center, double radius, Particle particle, int points) {
		World world = center.getWorld();
		double y = center.getY() + 0.05; // légèrement au-dessus du sol

		for (int i = 0; i < points; i++) {
			double angle = 2 * Math.PI * i / points;
			double x = Math.cos(angle) * radius;
			double z = Math.sin(angle) * radius;

			Location particleLoc = new Location(world, center.getX() + x, y, center.getZ() + z);

			if (particle.getDataType().equals(Float.class))
				world.spawnParticle(particle, particleLoc, 1, 0d, 0d, 0d, 0d, 0f);
			else
				world.spawnParticle(particle, particleLoc, 1, 0d, 0d, 0d, 0d);
		}
	}

	private void explode(Location center, double damage, double radius, Entity caster) {
		World world = center.getWorld();
		world.spawnParticle(Particle.EXPLOSION, center, 3);
		world.playSound(center, Sound.ENTITY_GENERIC_EXPLODE, 1f, 0.7f);

		for (Entity entity : world.getNearbyEntities(center, radius, radius, radius)) {
			if (!(entity instanceof LivingEntity living))
				continue;
			if (living.equals(caster))
				continue;

			living.damage(damage);

			Vector knockback = living.getLocation().toVector()
					.subtract(center.toVector())
					.normalize()
					.multiply(1.5);

			living.setVelocity(knockback);
		}
	}
}
