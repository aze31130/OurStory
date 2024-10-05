package ourstory.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class onMineAmethyst implements Listener {
	/*
	 * Allow players to mine Budding Amethyst if mined with silk touch
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();

		if (block.getType() == Material.BUDDING_AMETHYST && itemInHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.BUDDING_AMETHYST));
			// block.setType(Material.AIR);
		}
	}
}
