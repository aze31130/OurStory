package ourstory.skills;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Warden;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import net.kyori.adventure.text.Component;

public class SummonWarden implements Skills {
	@Override
	public void cast(Entity caster, List<Entity> targets) {
		Location summonPlace = caster.getLocation().add(caster.getLocation().getDirection().normalize().multiply(1.5)).add(0, 0.57, 0);
		World world = summonPlace.getWorld();

		world.spawnParticle(Particle.REVERSE_PORTAL, summonPlace, 80, 0.7, 1, 0.7, 0.02);
		world.playSound(summonPlace, Sound.ENTITY_WARDEN_NEARBY_CLOSE, 100, 0.8f);

		new BukkitRunnable() {
			int ticks = 0;

			@Override
			public void run() {
				ticks++;
				double circleRadius = 4;

				// Draw rotating blue circle
				for (double t = 0; t < 2 * Math.PI; t += Math.PI / 24) {
					double x = Math.cos(t + ticks * 0.1) * circleRadius;
					double z = Math.sin(t + ticks * 0.1) * circleRadius;
					Location loc = summonPlace.clone().add(x, 0.01, z);
					world.spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 1, 0, 0, 0, 0);
				}

				if (ticks % 4 == 0)
					drawStar(world, summonPlace, circleRadius, Particle.SOUL_FIRE_FLAME);

				if (ticks % 10 == 0) {
					world.playSound(summonPlace, Sound.ENTITY_WARDEN_HEARTBEAT, 100, 0.5f);
				}

				if (ticks > 100) {
					this.cancel();
					world.playSound(summonPlace, Sound.ENTITY_WARDEN_ROAR, 100, 0.5f);

					Warden warden = (Warden) world.spawn(summonPlace, Warden.class, w -> {
						w.customName(Component.text("Undead Warden"));
						w.setCustomNameVisible(true);
					});

					for (Entity target : targets) {

						warden.setAnger(target, 140);
					}
				}
			}
		}.runTaskTimer(plugin, 0, 2);
	}

	private void drawStar(World world, Location center, double radius, Particle particle) {
		int points = 15;
		double angle = 2 * Math.PI / points;
		Location[] vertices = new Location[points];

		// Get vertices
		for (int i = 0; i < points; i++) {
			double x = radius * Math.cos(i * angle);
			double z = radius * Math.sin(i * angle);
			vertices[i] = center.clone().add(x, 0.01, z);
		}

		// Connect in star pattern
		for (int i = 0; i < points; i++) {
			Location start = vertices[i];
			Location end = vertices[(i + 2) % points];
			drawLine(world, start, end, particle);
		}
	}

	private void drawLine(World world, Location from, Location to, Particle particle) {
		Vector direction = to.toVector().subtract(from.toVector());
		int points = 10;
		for (int i = 0; i <= points; i++) {
			Vector point = from.toVector().add(direction.clone().multiply(i / (double) points));
			world.spawnParticle(particle, point.toLocation(world), 1, 0, 0, 0, 0);
		}
	}
}
