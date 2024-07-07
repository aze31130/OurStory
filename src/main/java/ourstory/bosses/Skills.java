package ourstory.bosses;

import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ourstory.utils.EnchantItem;

public class Skills {
	private static void broadcastToPlayers(List<Player> players, String message) {
		for (Player p : players)
			p.sendMessage(message);
	}

	public static void heal(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Ultimate Heal !");

		boss.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 20, 120));
	}

	public static void reinforce(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Feel my strengh");

	}

	public static void summonMinions(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Go Minions, show no mercy !");

		for (int i = 0; i < 10; i++) {
			new BukkitRunnable() {
				@Override
				public void run() {
					Zombie minion = (Zombie) boss.getWorld().spawnEntity(boss.getLocation(), EntityType.ZOMBIE);

					EntityEquipment equipment = minion.getEquipment();
					ItemStack[] armor = {
							EnchantItem.createEnchantedItem(Material.GOLDEN_BOOTS, Map.of(Enchantment.THORNS, 3)),
							EnchantItem.createEnchantedItem(Material.GOLDEN_LEGGINGS, Map.of()),
							EnchantItem.createEnchantedItem(Material.GOLDEN_CHESTPLATE, Map.of(Enchantment.THORNS, 1)),
							EnchantItem.createEnchantedItem(Material.GOLDEN_HELMET, Map.of(Enchantment.THORNS, 3))
					};

					equipment.setArmorContents(armor);

					minion.setBaby();
				}
			}.runTaskLater(Bukkit.getPluginManager().getPlugin("OurStory"), i * 2);
		}
	}


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

	public static void teleport(Monster boss, List<Player> damager) {

	}

	public static void stopTime(Monster boss, List<Player> damager) {
		for (Player p : damager) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 200));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 100, 255));
		}

	}

	public static void meteors(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Take this !");

	}

	public static void dragonBreath(Monster boss, List<Player> damager) {

	}

	public static void arrowWall(Monster boss, List<Player> damager) {

	}
}
