package ourstory.recipes;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import ourstory.Main;

public class Furnace {
	private record CustomRecipeFurnace(String recipeName, Material source, ItemStack result, float exp, int cookTime) {
	}

	public static void createCustomRecipes() {
		List<CustomRecipeFurnace> recipes = Arrays.asList(
				new CustomRecipeFurnace("rotten_leather", Material.ROTTEN_FLESH, new ItemStack(Material.LEATHER, 1), 1, 200));

		for (CustomRecipeFurnace r : recipes) {
			FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(Main.namespace, r.recipeName()), r.result(), r.source(), r.exp(), r.cookTime());

			Bukkit.addRecipe(recipe);
		}
	}
}
