package ourstory.bosses;

import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.kyori.adventure.text.Component;
import ourstory.utils.EnchantItem;

public class Shadowblade extends Boss {

	private Map<Difficulty, Map<Attribute, Double>> attributes = Map.of(
			Difficulty.EASY, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 200.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 5.0),
			Difficulty.NORMAL, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 1500.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 15.0),
			Difficulty.HARD, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 15000.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 30.0,
					Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1.0),
			Difficulty.CHAOS, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 200000.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 70.0,
					Attribute.GENERIC_KNOCKBACK_RESISTANCE, 10.0));

	public Zombie entity;
	// public BossBar healthBar = Bukkit.createBossBar("Test Boss", BarColor.PURPLE, BarStyle.SOLID);

	public Shadowblade(Difficulty difficulty, Location l, World w) {
		this.entity = (Zombie) w.spawnEntity(l, EntityType.ZOMBIE);

		EntityEquipment equipment = entity.getEquipment();
		ItemStack[] armor = {
				EnchantItem.createEnchantedItem(Material.GOLDEN_BOOTS, Map.of(Enchantment.THORNS, 3)),
				EnchantItem.createEnchantedItem(Material.DIAMOND_LEGGINGS, Map.of()),
				EnchantItem.createEnchantedItem(Material.CHAINMAIL_CHESTPLATE, Map.of()),
				EnchantItem.createEnchantedItem(Material.CHAINMAIL_HELMET, Map.of())
		};

		equipment.setArmorContents(armor);
		equipment.setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
		entity.customName(Component.text("Commander Shadowblade"));
		entity.setCustomNameVisible(true);
		entity.addScoreboardTag("isBoss");
		entity.clearLootTable();
		entity.setGlowing(true);
		entity.setAggressive(true);

		// Define boss health
		this.difficulty = difficulty;
		this.name = "Test Boss";

		for (Map.Entry<Attribute, Double> entry : attributes.get(difficulty).entrySet()) {
			System.out.println(entry.getKey());
			AttributeInstance a = entity.getAttribute(entry.getKey());
			a.setBaseValue(entry.getValue());
		}

		// Set entity to max life
		entity.setHealth(attributes.get(difficulty).get(Attribute.GENERIC_MAX_HEALTH));
	}

	public void onSpawn() {

	}

	/*
	 * Boss skills. For now, they only trigger randomly after an hit.
	 */
	public void onHit(EntityDamageByEntityEvent event) {
		int rng = new Random().nextInt(100);

		Player damager = (Player) event.getDamager();
		Monster boss = (Monster) event.getEntity();

		// 3% chance to spawn minion
		if (rng < 3) {
			damager.sendMessage("Feel my strengh");
			// event.getEntity().getWorld().spawnEntity(null, null)
		}

		// Launch fireballs
		if (rng < 5) {
			damager.sendMessage("Take this !");
		}

		// Regen
		if (rng < 50) {
			damager.sendMessage("Heal !");
			boss.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 20, 100));
		}

		// Random TP + Blindness
		if (rng < 50) {
			damager.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
			damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1));
		}
	}

	public void onDeath(Entity damager) {
		// Generate loot table here
		Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),
				"setblock 1 128 39 minecraft:chest{LootTable:\"ourstory:example_boss\"} destroy");

		// Death animation
		double x = damager.getLocation().getX();
		double y = damager.getLocation().getY();
		double z = damager.getLocation().getZ();

		for (int i = -2; i < 2; i++) {
			damager.getWorld().spawnParticle(Particle.CLOUD, x + i, y, z + i, 50);
		}

		damager.sendMessage("Aaaaaa you killed me " + damager.getName());
	}
}
