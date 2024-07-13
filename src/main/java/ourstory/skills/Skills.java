package ourstory.skills;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Skills {
	public static void shoot(Monster boss, List<Player> damager) {
		// TODO choose random player
		Player target = damager.get(0);

		target.sendMessage("Feel my strengh !");
		for (int i = 0; i < 20; i++) {
			target.getWorld().spawnParticle(Particle.PORTAL, boss.getX(), boss.getY(), boss.getZ(), 50);
		}

		for (int i = 0; i < 10; i++) {
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



	public static void stopTime(Monster boss, List<Player> damager) {
		for (Player p : damager) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 200));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 100, 255));
		}

	}



}
