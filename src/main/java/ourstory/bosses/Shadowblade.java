package ourstory.bosses;

import java.util.Date;
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
import ourstory.skills.Offensive;
import ourstory.skills.Summon;
import ourstory.storage.Storage;
import ourstory.utils.EnchantItem;
import ourstory.utils.TimeUtils;

public class Shadowblade extends Boss implements Runnable {

	private Map<Difficulty, Map<Attribute, Double>> attributes = Map.of(
			Difficulty.EASY, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 200.0,
					Attribute.GENERIC_MOVEMENT_SPEED, 0.1,
					Attribute.GENERIC_ATTACK_DAMAGE, 5.0),
			Difficulty.NORMAL, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 1500.0,
					Attribute.GENERIC_MOVEMENT_SPEED, 0.15,
					Attribute.GENERIC_ATTACK_DAMAGE, 11.0),
			Difficulty.HARD, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 1500.0,
					Attribute.GENERIC_MOVEMENT_SPEED, 0.2,
					Attribute.GENERIC_ATTACK_DAMAGE, 21.0,
					Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1.0),
			Difficulty.CHAOS, Map.of(
					Attribute.GENERIC_MAX_HEALTH, 2000.0,
					Attribute.GENERIC_ATTACK_DAMAGE, 45.0,
					Attribute.GENERIC_MOVEMENT_SPEED, 0.25,
					Attribute.GENERIC_KNOCKBACK_RESISTANCE, 10.0));

	public Thread skills = new Thread(this);
	public final String name = "Commander Shadowblade";
	public Difficulty difficulty;
	public int phase;

	public Shadowblade(Difficulty difficulty, Location l, World w) {
		// Define boss health
		this.difficulty = difficulty;
		this.phase = 1;
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
		Storage s = Storage.getInstance();

		// Waiting time for boss skills
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		while (true) {

			/*
			 * Check if party ran out of time
			 */
			TimeUtils.displayTimeWarning();
			Date currentDate = new Date();

			if (currentDate.after(s.bossInstance.finished)) {
				// Kill the boss
				this.entity.setHealth(0);

				// TP back player
				// TODO

				// Message
				for (Player p : s.bossInstance.players)
					p.sendMessage("Too late, the ran out of time !");
			}

			int rng = r.nextInt(100);

			if (rng < 10) {
				Summon.summonMinions();
			}

			if (this.difficulty.level > Difficulty.NORMAL.level) {
				Offensive.shoot();
				// Summon.summonMinions(entity, players);
			}


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
	 * Method used for phase transitions
	 */
	public void onHit(EntityDamageByEntityEvent event) {
		Monster boss = (Monster) event.getEntity();
		Player p = (Player) event.getDamager();
		Storage s = Storage.getInstance();

		// Increase party damage
		s.bossInstance.damage.put(p, s.bossInstance.damage.get(p) + event.getDamage());



		// Check phase



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

		Storage s = Storage.getInstance();
		s.bossInstance.isFinished = true;

		// Death animation
		double x = damager.getX();
		double y = damager.getY();
		double z = damager.getZ();

		for (int i = -2; i < 2; i++) {
			damager.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, x + i, y, z + i, 50);
		}

		damager.sendMessage("Aaaaaa you killed me " + damager.getName());

		for (Map.Entry<Player, Double> entry : s.bossInstance.damage.entrySet())
			damager.sendMessage(entry.getKey().getName() + " dealt " + String.format("%.2f", entry.getValue()) + " damage");
	}
}
