package ourstory.skills;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
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
		// PlayerUtils.broadcastToPlayers(damager, "Take this !");

	}

	public static void dragonBreath() {

	}

	public static void arrowWall() {

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