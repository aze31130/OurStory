package ourstory.bosses;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import net.kyori.adventure.text.Component;
import ourstory.skills.*;
import ourstory.storage.Storage;
import ourstory.utils.EnchantItem;
import ourstory.utils.TimeUtils;

public class Talven extends Boss implements Runnable {

	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	private Map<Attribute, Double> attributes = Map.of(
			Attribute.MAX_HEALTH, 8000.0,
			Attribute.MOVEMENT_SPEED, 0.4,
			Attribute.ATTACK_DAMAGE, 35.0,
			Attribute.KNOCKBACK_RESISTANCE, 1.0);

	private List<LootEntry> loots = List.of(
			new LootEntry(new ItemStack(Material.GOLDEN_APPLE), 5, 60.0),
			new LootEntry(new ItemStack(Material.DIAMOND), 15, 15.0),
			new LootEntry(new ItemStack(Material.NETHER_STAR), 2, 30.0),
			new LootEntry(new ItemStack(Material.TOTEM_OF_UNDYING), 3, 30.0),
			new LootEntry(new ItemStack(Material.GOLDEN_CARROT), 15, 65.0),
			new LootEntry(new ItemStack(Material.NETHERITE_INGOT), 2, 15.0),
			new LootEntry(new ItemStack(Material.SPAWNER), 1, 50.0),
			new LootEntry(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 5, 60.0),
			new LootEntry(new ItemStack(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE), 1, 10.0));

	public Thread skills = new Thread(this);

	public Talven(Location l, World w) {
		super("Abyssal Sentinel");

		this.entity = (Monster) w.spawnEntity(l, EntityType.ZOMBIE);

		EntityEquipment equipment = entity.getEquipment();
		ItemStack[] armor = {
				EnchantItem.createEnchantedItem(Material.GOLDEN_BOOTS, Map.of(Enchantment.THORNS, 3)),
				EnchantItem.createEnchantedItem(Material.DIAMOND_LEGGINGS, Map.of()),
				EnchantItem.createEnchantedItem(Material.CHAINMAIL_CHESTPLATE, Map.of(Enchantment.THORNS, 1)),
				EnchantItem.createEnchantedItem(Material.CHAINMAIL_HELMET, Map.of())
		};

		equipment.setArmorContents(armor);
		equipment.setItemInMainHand(EnchantItem.createEnchantedItem(Material.NETHERITE_SWORD, Map.of(Enchantment.SHARPNESS, 7)));
		entity.customName(Component.text(this.name));
		entity.setCustomNameVisible(true);

		entity.setMetadata("isBoss", new FixedMetadataValue(p, true));

		entity.setGlowing(true);
		entity.setAggressive(true);

		// Apply attributes modifiers
		for (Map.Entry<Attribute, Double> entry : attributes.entrySet()) {
			AttributeInstance a = entity.getAttribute(entry.getKey());
			a.setBaseValue(entry.getValue());
		}

		// Set entity to max life
		entity.setHealth(attributes.get(Attribute.MAX_HEALTH));

		// Define HealthBar
		this.healthBar = Bukkit.createBossBar(this.name, BarColor.PURPLE, BarStyle.SOLID);
		this.healthBar.setVisible(true);

		double progress = entity.getHealth() / entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
		this.healthBar.setProgress(progress);
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
				ArrowWall a = new ArrowWall();
				a.cast(entity, null);
			}

			if (rng < 25) {
				Summon su = new Summon();
				su.cast(entity, null);
			}

			WitherRage wr = new WitherRage();
			wr.cast(entity, null);

			try {
				Thread.sleep(15000);
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

		// Add player to bossbar list
		this.healthBar.addPlayer(p);

		// Update bossbar
		double progress = entity.getHealth() / entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH).getValue();
		this.healthBar.setProgress(progress);

		// Check phase
		Double maxHealth = attributes.get(Attribute.MAX_HEALTH);
		Double currentHealth = boss.getHealth();

		Double healthPercent = (currentHealth * 100) / maxHealth;

		if (healthPercent < 55 && phase == 1) {
			ArrowWall a = new ArrowWall();
			a.cast(entity, null);
			phase = 2;
		}

		if (healthPercent < 20 && phase == 2) {
			// Summon special skill
			phase = 3;
		}

		if (healthPercent < 20 && phase == 2) {
			// Summon special skill
			Annihilation an = new Annihilation();
			an.cast(boss, null);

			phase = 3;
		}
	}


	public void onDeath(EntityDeathEvent event) {
		// Stop skill loop
		this.skills.interrupt();

		Player killer = event.getEntity().getKiller();
		if (killer == null) {

		}

		Storage s = Storage.getInstance();
		s.bossInstance.isFinished = true;

		// Death animation
		Location bossDeath = event.getEntity().getLocation();
		for (int i = -5; i < 5; i++) {
			event.getEntity().getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, bossDeath.getX() + i, bossDeath.getY(), bossDeath.getZ() + i, 50);
		}

		Bukkit.broadcast(Component.text(this.name + " is dead !"));

		for (Map.Entry<Player, Double> entry : s.bossInstance.damage.entrySet())
			Bukkit.broadcast(Component.text(entry.getKey().getName() + " dealt " + String.format("%.2f", entry.getValue()) + " damage"));

		// Remove health bar
		this.healthBar.removeAll();

		generateDrops(event, loots);
	}
}
