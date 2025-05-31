package ourstory.recipes;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import ourstory.Main;

public class StoneCutter {

	private record CustomRecipeStoneCutter(
			String recipeName,
			Material source,
			ItemStack result) {
	}

	private static final List<CustomRecipeStoneCutter> recipes = Arrays.asList(
			new CustomRecipeStoneCutter("stone_cobbled", Material.STONE, new ItemStack(Material.COBBLESTONE, 1)),
			new CustomRecipeStoneCutter("cobbled_gravel", Material.COBBLESTONE, new ItemStack(Material.GRAVEL, 1)),
			new CustomRecipeStoneCutter("gravel_sand", Material.GRAVEL, new ItemStack(Material.SAND, 1)),
			new CustomRecipeStoneCutter("deep_deepcobbled", Material.DEEPSLATE, new ItemStack(Material.COBBLED_DEEPSLATE, 1)),
			new CustomRecipeStoneCutter("deepcobbled_gravel", Material.COBBLED_DEEPSLATE, new ItemStack(Material.GRAVEL, 1)));

	public static void createCustomRecipes() {
		for (CustomRecipeStoneCutter t : recipes) {
			StonecuttingRecipe recipe = new StonecuttingRecipe(new NamespacedKey(Main.namespace, t.recipeName()), t.result(), t.source());

			Bukkit.addRecipe(recipe);
		}
	}
}
