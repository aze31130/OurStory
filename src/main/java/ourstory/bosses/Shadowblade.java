package ourstory.bosses;

import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import net.kyori.adventure.text.Component;
import ourstory.utils.EnchantItem;

public class Shadowblade extends Boss implements Runnable {

	private Map<Difficulty, Map<Attribute, Double>> attributes = Map.of(
			Difficulty.EASY, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 200.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 5.0),
			Difficulty.NORMAL, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 1500.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 11.0),
			Difficulty.HARD, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 15000.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 21.0,
					Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1.0),
			Difficulty.CHAOS, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 200000.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 45.0,
					Attribute.GENERIC_KNOCKBACK_RESISTANCE, 10.0));

	public Thread skills = new Thread(this);
	public final String name = "Commander Shadowblade";
	public Monster entity;
	public Difficulty difficulty;
	public int phase;

	public List<Player> players;

	public Shadowblade(Difficulty difficulty, List<Player> players, Location l, World w) {
		// Define boss health
		this.difficulty = difficulty;
		this.phase = 1;
		this.players = players;
		this.entity = (Monster) w.spawnEntity(l, EntityType.ZOMBIE);

		EntityEquipment equipment = entity.getEquipment();
		ItemStack[] armor = {
				EnchantItem.createEnchantedItem(Material.GOLDEN_BOOTS, Map.of(Enchantment.THORNS, 3)),
				EnchantItem.createEnchantedItem(Material.DIAMOND_LEGGINGS, Map.of()),
				EnchantItem.createEnchantedItem(Material.CHAINMAIL_CHESTPLATE, Map.of(Enchantment.THORNS, 1)),
				EnchantItem.createEnchantedItem(Material.CHAINMAIL_HELMET, Map.of())
		};

		equipment.setArmorContents(armor);
		equipment.setItemInMainHand(EnchantItem.createEnchantedItem(Material.NETHERITE_SWORD, Map.of()));
		entity.customName(Component.text(this.name));
		entity.setCustomNameVisible(true);
		entity.addScoreboardTag("isBoss");
		entity.addScoreboardTag(difficulty.name());



		// Parse the loot table key
		NamespacedKey key = NamespacedKey.fromString("ourstory:example_boss");
		LootTable lootTable = Bukkit.getLootTable(key);
		entity.setLootTable(lootTable);


		entity.setGlowing(true);
		entity.setAggressive(true);

		// Apply attributes modifiers
		for (Map.Entry<Attribute, Double> entry : attributes.get(difficulty).entrySet()) {
			AttributeInstance a = entity.getAttribute(entry.getKey());
			a.setBaseValue(entry.getValue());
		}

		// Set entity to max life
		entity.setHealth(attributes.get(difficulty).get(Attribute.GENERIC_MAX_HEALTH));
	}

	public void onSpawn() {
		/*
		 * Some dialogue / effects here
		 */

		// Start skill thread
		this.skills.start();
	}

	/*
	 * Boss skills. For now, they only trigger randomly after an hit.
	 */
	@Override
	public void run() {
		Random r = new Random();

		while (true) {


			if (this.difficulty.level > Difficulty.NORMAL.level) {
				Skills.shoot(entity, players);
				Skills.summonMinions(entity, players);
			}

			// int rng = r.nextInt(100);

			// 3% chance to spawn minion
			// if (rng < 10)
			// Skills.summonMinions(boss, List.of(damager));
			//
			// if (rng < 5)
			// Skills.explode(boss, List.of(damager));
			//
			// if (rng < 50 && this.difficulty.equals(Difficulty.HARD))
			// Skills.heal(boss, List.of(damager));
			//
			// if (rng < 50) {
			//
			// }

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	/*
	 * Method used to change phase
	 */
	public void onHit(EntityDamageByEntityEvent event) {
		Monster boss = (Monster) event.getEntity();

		Double maxHealth = attributes.get(difficulty).get(Attribute.GENERIC_MAX_HEALTH);
		Double currentHealth = boss.getHealth();

		Double healthPercent = (currentHealth * 100) / maxHealth;

		if (healthPercent < 20 && difficulty.equals(Difficulty.HARD)) {
			// Summon special skill
		}

		if (healthPercent < 20 && difficulty.equals(Difficulty.CHAOS)) {
			// Summon special skill
		}
	}


	public void onDeath(Entity damager) {
		// Stop skill loop
		this.skills.interrupt();

		// Death animation
		double x = damager.getX();
		double y = damager.getY();
		double z = damager.getZ();

		for (int i = -2; i < 2; i++) {
			damager.getWorld().spawnParticle(Particle.CLOUD, x + i, y, z + i, 50);
		}

		damager.sendMessage("Aaaaaa you killed me " + damager.getName());
	}
}
