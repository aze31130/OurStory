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
import ourstory.utils.EnchantItem;

public class Summon extends Spell {

	public Summon(Entity caster, List<Entity> targets, int level) {
		super(caster, targets, level);
	}

	private int cpt, level, max, enchantlevel;

	@Override
	public void setup() {
		this.cpt = 0;
		this.enchantlevel = 3 * level;
	}

	@Override
	public void tick() {

		Zombie minion = (Zombie) caster.getWorld().spawnEntity(caster.getLocation(), EntityType.ZOMBIE);
		this.max = 15 + 5 * level;
		cpt++;
		EntityEquipment equipment = minion.getEquipment();
		ItemStack[] armor = {
				EnchantItem.createEnchantedItem(Material.GOLDEN_BOOTS, Map.of(Enchantment.THORNS, enchantlevel)),
				EnchantItem.createEnchantedItem(Material.GOLDEN_LEGGINGS, Map.of(Enchantment.THORNS, enchantlevel)),
				EnchantItem.createEnchantedItem(Material.GOLDEN_CHESTPLATE, Map.of(Enchantment.THORNS, enchantlevel)),
				EnchantItem.createEnchantedItem(Material.GOLDEN_HELMET, Map.of(Enchantment.THORNS, enchantlevel)),
		};

		ItemStack weapon = EnchantItem.createEnchantedItem(Material.GOLDEN_SWORD, Map.of(Enchantment.SHARPNESS, enchantlevel));
		equipment.setArmorContents(armor);
		equipment.setItemInMainHand(weapon);
		minion.setBaby();
	}

	@Override
	public void stop() {}

	@Override
	public boolean shouldStop() {
		return cpt > max;
	}
}
