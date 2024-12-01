package ourstory.skills;

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

public class Summon implements Skills {
	@Override
	public void cast(Entity caster, List<Entity> targets) {
		for (int i = 0; i < 15; i++) {
			new BukkitRunnable() {
				@Override
				public void run() {
					Zombie minion = (Zombie) caster.getWorld().spawnEntity(caster.getLocation(), EntityType.ZOMBIE);

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
			}.runTaskLater(plugin, i * 2);
		}
	}
}
