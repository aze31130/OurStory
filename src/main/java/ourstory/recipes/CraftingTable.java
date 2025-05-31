package ourstory.recipes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import ourstory.Main;

public class CraftingTable {

	private record CustomRecipeCraftingTable(
			String recipeName,
			String itemName,
			Supplier<ItemStack> result,
			String shape1,
			String shape2,
			String shape3,
			Map<Character, Material> materials,
			Boolean unbreakable) {
	}

	private static final List<CustomRecipeCraftingTable> recipes = Arrays.asList(
			new CustomRecipeCraftingTable("wither_elytra", "Wither's Ultimate Elytra", () -> new ItemStack(Material.ELYTRA, 1),
					"NNN", "NEN", "NNN", Map.of('E', Material.ELYTRA, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_bow", "Wither's Ultimate Bow", () -> new ItemStack(Material.BOW, 1),
					" NS", "N S", " NS", Map.of('S', Material.STRING, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_pickaxe", "Wither's Ultimate Pickaxe", () -> new ItemStack(Material.DIAMOND_PICKAXE, 1),
					"NNN", " S ", " S ", Map.of('S', Material.STICK, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_sword", "Wither's Ultimate Sword", () -> new ItemStack(Material.DIAMOND_SWORD, 1),
					" N ", " N ", " S ", Map.of('S', Material.STICK, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_shovel", "Wither's Ultimate Shovel", () -> new ItemStack(Material.DIAMOND_SHOVEL, 1),
					" N ", " S ", " S ", Map.of('S', Material.STICK, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_axe", "Wither's Ultimate Axe", () -> new ItemStack(Material.DIAMOND_AXE, 1),
					"NN ", "NS ", " S ", Map.of('S', Material.STICK, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_hoe", "Wither's Ultimate Hoe", () -> new ItemStack(Material.DIAMOND_HOE, 1),
					"NN ", " S ", " S ", Map.of('S', Material.STICK, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_helmet", "Wither's Ultimate Helmet", () -> new ItemStack(Material.DIAMOND_HELMET, 1),
					"NNN", "N N", "   ", Map.of('N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_chestplate", "Wither's Ultimate Chestplate", () -> new ItemStack(Material.DIAMOND_CHESTPLATE, 1),
					"N N", "NNN", "NNN", Map.of('N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_leggings", "Wither's Ultimate Leggings", () -> new ItemStack(Material.DIAMOND_LEGGINGS, 1),
					"NNN", "N N", "N N", Map.of('N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_boots", "Wither's Ultimate Boots", () -> new ItemStack(Material.DIAMOND_BOOTS, 1),
					"N N", "N N", "   ", Map.of('N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_trident", "Wither's Ultimate Trident", () -> new ItemStack(Material.TRIDENT, 1),
					" N ", "NTN", " N ", Map.of('T', Material.TRIDENT, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_mace", "Wither's Ultimate Mace", () -> new ItemStack(Material.MACE, 1),
					" N ", "NMN", " N ", Map.of('M', Material.MACE, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_crossbow", "Wither's Ultimate Crossbow", () -> new ItemStack(Material.CROSSBOW, 1),
					" N ", "NCN", " N ", Map.of('C', Material.CROSSBOW, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("wither_fishing_rod", "Wither's Ultimate Fishing Rod", () -> new ItemStack(Material.FISHING_ROD, 1),
					"  S", " SF", "S N", Map.of('S', Material.STICK, 'F', Material.STRING, 'N', Material.NETHER_STAR), true),

			new CustomRecipeCraftingTable("super_firework_5", "Super Firework Rocket", () -> CraftingTable.createFirework(5),
					"   ", "GPG", "GGG", Map.of('P', Material.PAPER, 'G', Material.GUNPOWDER), false),

			new CustomRecipeCraftingTable("super_firework_7", "Hyper Firework Rocket", () -> CraftingTable.createFirework(7),
					"G G", "GPG", "GGG", Map.of('P', Material.PAPER, 'G', Material.GUNPOWDER), false),

			new CustomRecipeCraftingTable("super_firework_9", "Mega Firework Rocket", () -> CraftingTable.createFirework(9),
					"GGG", "GPG", "GGG", Map.of('P', Material.PAPER, 'G', Material.GUNPOWDER), false));

	private static ItemStack createFirework(int power) {
		ItemStack result = new ItemStack(Material.FIREWORK_ROCKET);
		FireworkMeta meta = (FireworkMeta) result.getItemMeta();

		meta.setPower(power);
		result.setItemMeta(meta);
		result.setAmount(3);

		return result;
	}

	public static void createCustomRecipes() {
		for (CustomRecipeCraftingTable r : recipes) {
			ItemStack recipeResult = new ItemStack(r.result().get());
			ItemMeta recipeResultMeta = recipeResult.getItemMeta();

			recipeResultMeta.setUnbreakable(r.unbreakable());

			Component itemName = Component.text(r.itemName());

			if (r.unbreakable())
				itemName = itemName.color(NamedTextColor.RED).decorate(TextDecoration.BOLD);

			recipeResultMeta.itemName(itemName);
			recipeResult.setItemMeta(recipeResultMeta);

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.namespace, r.recipeName()), recipeResult);
			recipe.shape(r.shape1(), r.shape2(), r.shape3());

			for (Map.Entry<Character, Material> entry : r.materials().entrySet())
				recipe.setIngredient(entry.getKey(), entry.getValue());

			Bukkit.addRecipe(recipe);
		}
	}
}
