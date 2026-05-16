package ourstory.events;

import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ourstory.utils.EnchantItem;

public class onReplant implements Listener {
	private Plugin plugin = Bukkit.getPluginManager().getPlugin("OurStory");

	private static final Map<Material, Material> ALLOWED_PLANT = Map.of(
			Material.WHEAT, Material.WHEAT_SEEDS,
			Material.CARROTS, Material.CARROT,
			Material.POTATOES, Material.POTATO,
			Material.BEETROOTS, Material.BEETROOT_SEEDS,
			Material.NETHER_WART, Material.NETHER_WART);

	@EventHandler
	public void onReplantEnchant(BlockBreakEvent event) {
		Block b = event.getBlock();
		Player p = event.getPlayer();
		ItemStack itemInHand = p.getInventory().getItemInMainHand();
		BlockData blockData = b.getBlockData();

		if (!ALLOWED_PLANT.containsKey(b.getType()))
			return;

		if (!(blockData instanceof Ageable ageable))
			return;

		if (EnchantItem.getEnchantAmount(itemInHand, "replant") == 0)
			return;

		if (ageable.getAge() < ageable.getMaximumAge())
			return;

		// Replant the seed
		p.getServer().getScheduler().runTaskLater(plugin, () -> {
			Ageable newSeed = (Ageable) blockData;
			newSeed.setAge(0);
			b.setBlockData(newSeed);
		}, 4L);
	}
}
