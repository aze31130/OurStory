package ourstory.spells;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ThunderCharge extends Spell {

	private int range;
	private double strikeDamage;
	private Location currentLocation;
	private int cpt;
	private int step;

	public ThunderCharge(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	@Override
	public void setup() {
		this.strikeDamage = 10.0;
		this.range = 20;
		this.step = 1;
		this.cpt = 0;
		this.currentLocation = caster.getLocation();
	}

	@Override
	public void tick() {

		spawnChargingParticles(currentLocation, cpt);
		drawMagicCircle(currentLocation);
		cpt += 2;

		if (cpt >= 100) {
			startDispersePhase(caster, currentLocation, targets);
		}
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return step > range;
	}


	private void startDispersePhase(Entity caster, Location origin, List<Entity> targets) {
		World world = origin.getWorld();
		Vector forward = origin.getDirection().setY(0).normalize();
		if (forward.lengthSquared() == 0)
			forward = new Vector(0, 0, 1);

		Vector right = new Vector(-forward.getZ(), 0, forward.getX()).normalize();
		Vector left = right.clone().multiply(-1);
		Vector back = forward.clone().multiply(-1);
		Vector[] directions = new Vector[] {forward, back, right, left};

		for (Vector direction : directions) {
			Location strikeLoc = origin.clone().add(direction.clone().multiply(step));
			strikeLoc.setY(world.getHighestBlockYAt(strikeLoc));

			world.strikeLightningEffect(strikeLoc);
			igniteGround(strikeLoc);
			damageTargetsOnStrike(caster, targets, strikeLoc);
		}

		step++;
	}

	private void damageTargetsOnStrike(Entity caster, List<Entity> targets, Location strikeLoc) {
		double hitRadiusSquared = 2.25;

		for (Entity targetEntity : targets) {
			if (!(targetEntity instanceof LivingEntity living))
				continue;
			if (!living.isValid())
				continue;
			if (living.equals(caster))
				continue;
			if (!living.getWorld().equals(strikeLoc.getWorld()))
				continue;
			if (living.getLocation().distanceSquared(strikeLoc) > hitRadiusSquared)
				continue;

			living.damage(strikeDamage, caster);
		}
	}

	private void igniteGround(Location strikeLoc) {
		Block highestBlock = strikeLoc.getWorld().getHighestBlockAt(strikeLoc);
		Block fireBlock = highestBlock.getRelative(0, 1, 0);

		if (fireBlock.getType().isAir())
			fireBlock.setType(Material.FIRE, true);
	}

	private void spawnChargingParticles(Location center, int elapsedTicks) {
		World world = center.getWorld();
		double progress = Math.min(1.0, elapsedTicks / 100.0);
		int particleCount = 8 + (int) (progress * 40);
		double radius = 0.8 + (progress * 1.4);
		double yBase = center.getY() + 0.4 + (progress * 0.4);

		for (int i = 0; i < particleCount; i++) {
			double angle = (2 * Math.PI * i) / particleCount + (elapsedTicks * 0.08);
			double x = center.getX() + Math.cos(angle) * radius;
			double z = center.getZ() + Math.sin(angle) * radius;
			double y = yBase + ((i % 5) * 0.12);
			world.spawnParticle(Particle.END_ROD, x, y, z, 1, 0, 0, 0, 0);
		}
	}

	private void drawMagicCircle(Location center) {
		World world = center.getWorld();
		double y = world.getHighestBlockYAt(center) + 0.1;
		double radius = 2.2;
		int points = 72;

		for (int i = 0; i < points; i++) {
			double angle = 2 * Math.PI * i / points;
			double x = center.getX() + Math.cos(angle) * radius;
			double z = center.getZ() + Math.sin(angle) * radius;

			world.spawnParticle(Particle.ENCHANT, x, y, z, 1, 0, 0, 0, 0);
			if (i % 12 == 0)
				world.spawnParticle(Particle.ELECTRIC_SPARK, x, y + 0.05, z, 1, 0, 0, 0, 0);
		}
	}
}
