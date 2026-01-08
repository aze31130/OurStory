package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.utils.EnchantItem;
import org.bukkit.Material;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Player;

public class onBreakVeine implements Listener {

	private static final List<Material> ALLOWED_BLOCKS = List.of(
			Material.COAL_ORE,
			Material.COPPER_ORE,
			Material.IRON_ORE,
			Material.GOLD_ORE,
			Material.DIAMOND_ORE,
			Material.EMERALD_ORE,
			Material.ANCIENT_DEBRIS,
			Material.DEEPSLATE_COAL_ORE,
			Material.DEEPSLATE_COPPER_ORE,
			Material.DEEPSLATE_IRON_ORE,
			Material.DEEPSLATE_GOLD_ORE,
			Material.DEEPSLATE_DIAMOND_ORE,
			Material.DEEPSLATE_EMERALD_ORE);


	private static void getBlockList(Block block, Set<Block> visited, int maxBlocks, Material block_type) {
		if (visited.size() >= maxBlocks)
			return;

		if (block.getType() != block_type)
			return;

		if (!visited.add(block))
			return;

		// voisins (6 directions)
		for (Block relative : new Block[] {
				block.getRelative(1, 0, 0),
				block.getRelative(-1, 0, 0),
				block.getRelative(0, 1, 0),
				block.getRelative(0, -1, 0),
				block.getRelative(0, 0, 1),
				block.getRelative(0, 0, -1)
		}) {
			getBlockList(relative, visited, maxBlocks, block_type);
		}
	}

	@EventHandler
	public void BreakVeine(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		int totalVeineMinerLevel = EnchantItem.getEnchantAmount(item, "veine_miner");

		if (!ALLOWED_BLOCKS.contains(event.getBlock().getType()))
			return;

		if (!item.getType().name().endsWith("_PICKAXE"))
			return;

		Set<Block> block_list = new HashSet<>();
		getBlockList(event.getBlock(), block_list, totalVeineMinerLevel * 2 + 1, event.getBlock().getType());

		for (Block b : block_list)
			b.breakNaturally(item);
	}
}
