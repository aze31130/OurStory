package ourstory.events;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import ourstory.utils.EnchantItem;

/**
 *
 * @author aurel
 */
public class onReachEquip implements Listener {
	@EventHandler
	public void onPlayerChangeArmor(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		int totalReachLevels = 0;
		ItemStack[] armorContents = player.getInventory().getArmorContents();

		for (ItemStack armor : armorContents)
			totalReachLevels += EnchantItem.getEnchantAmount(armor, "reach");

		if (totalReachLevels > 4)
			totalReachLevels = 4;
		applyReachModifier(player, totalReachLevels);
	}

	private void applyReachModifier(Player player, int reachLevel) {
		NamespacedKey ReachKey = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "reach_bonus");
		if (player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE) != null)
			player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE).removeModifier(ReachKey);

		AttributeModifier buff = new AttributeModifier(ReachKey, reachLevel, AttributeModifier.Operation.ADD_NUMBER);
		player.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE).addModifier(buff);
	}
}
