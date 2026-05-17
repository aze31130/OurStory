package ourstory.utils;

import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantItem {
	public static ItemStack createEnchantedItem(Material item, Map<Enchantment, Integer> enchantments) {
		ItemStack result = new ItemStack(item);
		ItemMeta im = result.getItemMeta();

		im.setUnbreakable(true);
		im.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

		for (Entry<Enchantment, Integer> e : enchantments.entrySet())
			im.addEnchant(e.getKey(), e.getValue(), true);

		result.setItemMeta(im);
		return result;
	}

	public static int getEnchantAmount(ItemStack item, String enchantName) {
		int totalLevels = 0;

		if (item == null)
			return totalLevels;

		Map<Enchantment, Integer> enchants = item.getEnchantments();

		for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
			Enchantment enchantment = entry.getKey();
			int level = entry.getValue();

			if (enchantment.getKey().getKey().equals(enchantName))
				totalLevels += level;
		}

		return totalLevels;
	}

	public static int getEnchantLevelQuantity(ItemStack item) {
		int totalLevels = 0;

		if (item == null)
			return totalLevels;

		// Adds all the enchantments levels
		for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet())
			totalLevels += entry.getValue();

		// Adds all the stored enchantments (for books)
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof EnchantmentStorageMeta) {
			EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta) meta;

			for (Map.Entry<Enchantment, Integer> entry : enchantMeta.getStoredEnchants().entrySet())
				totalLevels += entry.getValue();
		}

		return totalLevels;
	}
}
