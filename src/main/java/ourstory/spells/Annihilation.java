package ourstory.spells;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;

public class Annihilation extends Spell {
	/*
	 * Special skill, last skill only used in chaos mode. Triggers at 20% HP remaining.
	 * The caster invokes a flame circle around itself and deals heavy damage.
	 */
	private Location loc;
	private int pointCount;
	private double radius;
	private int totalSteps;
	private double radiusIncrease;
	private double startRadius;
	private double endRadius;
	private int t;

	public Annihilation(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	@Override
	public void setup() {
		Bukkit.getServer().broadcast(Component.text("Prepare for Annihilation"));

		this.loc = caster.getLocation();
		this.pointCount = 6;
		this.radius = 20;
		this.totalSteps = 40;
		this.radiusIncrease = 0.5;
		this.startRadius = 0.5;
		this.endRadius = 20;
		this.t = 0;
	}

	@Override
	public void tick() {
		double progress = (double) t / totalSteps;
		double easedProgress = 1 - Math.pow(1 - progress, 2);

		for (int i = 0; i < pointCount; i++) {
			double angle = 2 * Math.PI * i / pointCount;
			double currentRadius = radius * (1 - easedProgress);
			double currentAngle = angle + Math.PI * easedProgress;

			double x = Math.cos(currentAngle) * currentRadius;
			double z = Math.sin(currentAngle) * currentRadius;
			Location particleLoc = loc.clone().add(x, 0, z);

			caster.getWorld().spawnParticle(Particle.LARGE_SMOKE, particleLoc, 0, 0, 0, 0, 0);
		}
		t++;

		playCircleEffect(caster, loc, startRadius);
		startRadius += radiusIncrease;
		for (Entity entity : caster.getNearbyEntities(startRadius, 2, startRadius)) {
			if (!(entity instanceof LivingEntity livingTarget))
				continue;
			if (entity.equals(caster))
				continue;

			Vector direction = entity.getLocation().toVector().subtract(loc.toVector()).normalize();
			entity.setVelocity(direction.multiply(0.7));
			livingTarget.damage(7.0);
		}
	}

	@Override
	public boolean shouldStop() {
		return (startRadius >= endRadius) || (t > totalSteps);
	}

	@Override
	public void stop() {
		// no-op
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
