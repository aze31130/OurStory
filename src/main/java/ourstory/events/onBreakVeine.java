package ourstory.events;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import ourstory.utils.EnchantItem;

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

	@EventHandler
	public void breakVeine(BlockBreakEvent event) {
		if (!ALLOWED_BLOCKS.contains(event.getBlock().getType()))
			return;

		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (!item.getType().name().endsWith("_PICKAXE"))
			return;

		int veinMinerLevel = EnchantItem.getEnchantAmount(item, "veine_miner");
		if (veinMinerLevel <= 0)
			return;

		int maxExtra = veinMinerLevel * 2;
		Set<Block> visited = new HashSet<>();
		visited.add(event.getBlock());

		Deque<Block> queue = new ArrayDeque<>();
		queue.add(event.getBlock());
		Material target = event.getBlock().getType();

		while (!queue.isEmpty() && visited.size() - 1 < maxExtra) {
			Block current = queue.removeFirst();
			for (int[] delta : new int[][]{{1,0,0},{-1,0,0},{0,1,0},{0,-1,0},{0,0,1},{0,0,-1}}) {
				if (visited.size() - 1 >= maxExtra)
					break;
				Block neighbor = current.getRelative(delta[0], delta[1], delta[2]);
				if (neighbor.getType() != target)
					continue;
				if (!visited.add(neighbor))
					continue;
				queue.add(neighbor);
			}
		}

		visited.remove(event.getBlock());
		for (Block b : visited)
			b.breakNaturally(item);
	}
}
