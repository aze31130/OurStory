package ourstory.spells;

import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ourstory.utils.EnchantItem;

public class ZombieSummon extends Spell {
	private Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");
	private final Random random = new Random();

	private int tick = 0;
	private boolean finished = false;

	public ZombieSummon(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	@Override
	public void setup() {
		caster.getWorld().playSound(caster.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1.5f, 0.6f);
	}

	@Override
	public void tick() {
		tick++;

		Location center = caster.getLocation();

		// Ritual animation (first ~40 ticks)
		if (tick < 40) {
			double radius = 2.5;

			for (int i = 0; i < 6; i++) {
				double angle = (tick * 0.3) + (i * (Math.PI * 2 / 6));
				double x = Math.cos(angle) * radius;
				double z = Math.sin(angle) * radius;

				Location particleLoc = center.clone().add(x, 0.2 + (tick * 0.02), z);

				center.getWorld().spawnParticle(Particle.OMINOUS_SPAWNING, particleLoc, 20, 1, 5, 1, 0.1);
			}

			center.getWorld().spawnParticle(Particle.PORTAL, center.clone().add(0, 1.2, 0), 10, 0.5, 0.5, 0.5, 0.1);
			return;
		}

		// Spawn zombies once
		if (!finished) {
			finished = true;

			for (int i = 0; i < 50; i++) {
				new BukkitRunnable() {
					@Override
					public void run() {
						Location spawnLoc = caster.getLocation().clone().add(randomOffset(8), 0, randomOffset(8));

						Zombie zombie = (Zombie) caster.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE);
						if (random.nextBoolean())
							zombie.setBaby();
						zombie.setRemoveWhenFarAway(true);
						zombie.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.35);
						zombie.setTarget(getClosestTarget(spawnLoc, targets));

						EntityEquipment eq = zombie.getEquipment();
						ItemStack[] armor = {
								EnchantItem.createEnchantedItem(Material.DIAMOND_BOOTS, Map.of(Enchantment.THORNS, 5)),
								EnchantItem.createEnchantedItem(Material.DIAMOND_LEGGINGS, Map.of(Enchantment.PROTECTION, 4)),
								EnchantItem.createEnchantedItem(Material.DIAMOND_CHESTPLATE, Map.of(Enchantment.PROJECTILE_PROTECTION, 4)),
								EnchantItem.createEnchantedItem(Material.DIAMOND_HELMET, Map.of(Enchantment.PROJECTILE_PROTECTION, 4))
						};
						eq.setArmorContents(armor);

						zombie.getWorld().spawnParticle(Particle.SONIC_BOOM, spawnLoc, 8, 0.5, 0.5, 0.5, 0.02);
						zombie.getWorld().playSound(spawnLoc, Sound.ENTITY_ZOMBIE_AMBIENT, 0.6f, 0.8f);
					}
				}.runTaskLater(plugin, i * 2L);
			}
		}
	}

	private LivingEntity getClosestTarget(Location from, List<Entity> targets) {
		LivingEntity best = null;
		double bestDist = Double.MAX_VALUE;

		for (Entity e : targets) {
			if (!(e instanceof LivingEntity le))
				continue;
			if (le.isDead())
				continue;

			double d = le.getLocation().distanceSquared(from);
			if (d < bestDist) {
				bestDist = d;
				best = le;
			}
		}

		return best;
	}

	private double randomOffset(double range) {
		return (random.nextDouble() * 2 - 1) * range;
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return finished;
	}
}
