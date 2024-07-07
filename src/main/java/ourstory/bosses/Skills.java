package ourstory.bosses;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.utils.EnchantItem;

public class Skills {
	private static void broadcastToPlayers(List<Player> players, String message) {
		for (Player p : players)
			p.sendMessage(message);
	}

	public static void heal(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Maximize magic, divine Heal !");

		boss.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 20, 100));
	}

	public static void reinforce(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Feel my strengh");

	}

	public static void summonMinions(Monster boss, List<Player> damager) {
		broadcastToPlayers(damager, "Go Minions, show no mercy !");

		for (int i = 0; i < 10; i++) {
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
	}

	public static void explode(Monster boss, List<Player> damager) {

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
