package ourstory.bosses;

import org.bukkit.inventory.ItemStack;

public record LootEntry(ItemStack item, int maxQuantity, Double proba) {
}
