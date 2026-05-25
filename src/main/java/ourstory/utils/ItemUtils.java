package ourstory.utils;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

	public static boolean isTool(ItemStack item) {
		if (item == null)
			return false;

		Material material = item.getType();

		return Tag.ITEMS_PICKAXES.isTagged(material)
				|| Tag.ITEMS_SHOVELS.isTagged(material)
				|| Tag.ITEMS_AXES.isTagged(material)
				|| Tag.ITEMS_HOES.isTagged(material)
				|| Tag.ITEMS_SWORDS.isTagged(material)
				|| Tag.ITEMS_SPEARS.isTagged(material)
				|| Tag.ITEMS_ENCHANTABLE_BOW.isTagged(material)
				|| Tag.ITEMS_ENCHANTABLE_CROSSBOW.isTagged(material)
				|| Tag.ITEMS_ENCHANTABLE_MACE.isTagged(material);
	}

	public static boolean isArmor(ItemStack item) {
		if (item == null)
			return false;

		Material material = item.getType();

		return Tag.ITEMS_HEAD_ARMOR.isTagged(material)
				|| Tag.ITEMS_CHEST_ARMOR.isTagged(material)
				|| Tag.ITEMS_LEG_ARMOR.isTagged(material)
				|| Tag.ITEMS_FOOT_ARMOR.isTagged(material);
	}
}
