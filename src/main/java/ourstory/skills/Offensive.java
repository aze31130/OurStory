package ourstory.skills;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ourstory.storage.Storage;

public class Offensive {
	public static void shoot() {
		Storage s = Storage.getInstance();
		Monster boss = s.bossInstance.monster.entity;

		// TODO choose random player
		Player target = s.bossInstance.players.get(0);

		target.sendMessage("Feel my strengh !");
		target.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, boss.getX(), boss.getY(), boss.getZ(), 5);

		for (int i = 0; i < 15; i++) {
			new BukkitRunnable() {
				@Override
				public void run() {
					Location bossLocation = boss.getLocation();
					Location playerLocation = target.getLocation();

					Vector direction = playerLocation.toVector().subtract(bossLocation.toVector()).normalize();

					// Spawn the WitherSkull at the boss's location
					WitherSkull skull =
							(WitherSkull) boss.getWorld().spawnEntity(boss.getLocation().add(0, 1, 0), EntityType.WITHER_SKULL);

					// Set the direction of the WitherSkull to point towards the player
					skull.setDirection(direction);
					skull.setAcceleration(direction.multiply(0.1));
				}
			}.runTaskLater(Bukkit.getPluginManager().getPlugin("OurStory"), 20 + i * 4);
		}
	}

	public static void meteors() {

	}

	public static void dragonBreath() {

	}

	public static void arrowWall() {
		Storage s = Storage.getInstance();
		Monster boss = s.bossInstance.monster.entity;
		Location bossLocation = boss.getLocation().add(0, 3, 0); // Spawn arrows 3 blocks above boss

		for (int i = 0; i < 800; i++) {
			new BukkitRunnable() {
				@Override
				public void run() {
					// Generate a random direction for each arrow in a spherical pattern
					double theta = Math.random() * 2 * Math.PI; // Angle around the Y-axis
					double phi = Math.random() * Math.PI; // Angle from the Y-axis down



					// Convert spherical coordinates to Cartesian (X, Y, Z)
					double x = Math.sin(phi) * Math.cos(theta);
					double y = Math.cos(phi);
					double z = Math.sin(phi) * Math.sin(theta);
					Vector direction = new Vector(x, y, z).normalize().multiply(4); // Speed of 3 blocks/sec

					// Spawn the arrow at the boss's location with the calculated direction
					Arrow arrow = boss.getWorld().spawnArrow(bossLocation, direction, 2, 0);
					arrow.setShooter(boss); // Set the boss as the shooter (optional)
				}
			}.runTaskLater(Bukkit.getPluginManager().getPlugin("OurStory"), i / 10); // Slight delay for each arrow
		}
	}

	public static void wave() {

	}

	/*
	 * 
	 */
	public static void doom() {

	}

	/*
	 * Special skill, last skill only used in chaos mode. Triggers at 5% HP remaining. The boss becomes
	 * enraged and greatly increases damage output.
	 */
	public static void annihilation() {

	}
}
