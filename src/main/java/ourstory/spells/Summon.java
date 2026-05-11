package ourstory.spells;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ourstory.utils.EnchantItem;

public class Summon extends Spell {

	public Summon(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() {
		// not yet implemented; see commented WIP below
	}

	@Override
	public void tick() {
		// not yet implemented
	}

	@Override
	public void stop() {
		// not yet implemented
	}
	// @Override
	// public void cast(Entity caster, List<Entity> targets, int level) {
	// for (int i = 0; i < 15; i++) {
	// new BukkitRunnable() {
	// @Override
	// public void run() {
	// Zombie minion = (Zombie) caster.getWorld().spawnEntity(caster.getLocation(), EntityType.ZOMBIE);

	@Override
	public boolean shouldStop() {
		return true;
	}

	// EntityEquipment equipment = minion.getEquipment();
	// ItemStack[] armor = {
	// EnchantItem.createEnchantedItem(Material.GOLDEN_BOOTS, Map.of(Enchantment.THORNS, 3)),
	// EnchantItem.createEnchantedItem(Material.GOLDEN_LEGGINGS, Map.of()),
	// EnchantItem.createEnchantedItem(Material.GOLDEN_CHESTPLATE, Map.of(Enchantment.THORNS, 1)),
	// EnchantItem.createEnchantedItem(Material.GOLDEN_HELMET, Map.of(Enchantment.THORNS, 3))
	// };

	// equipment.setArmorContents(armor);

	// minion.setBaby();
	// }
	// }.runTaskLater(plugin, i * 2);
	// }
	// }
}
