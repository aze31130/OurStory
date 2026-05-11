package ourstory.events;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import ourstory.Main;
import ourstory.utils.EnchantItem;

/**
 *
 * @author aurel
 */
public class onReachEquip implements Listener {

	private static final int MAX_REACH_LEVEL = 4;

	@EventHandler
	public void onPlayerChangeArmor(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		int totalReachLevels = 0;
		for (ItemStack armor : player.getInventory().getArmorContents())
			totalReachLevels += EnchantItem.getEnchantAmount(armor, "reach");

		if (totalReachLevels > MAX_REACH_LEVEL)
			totalReachLevels = MAX_REACH_LEVEL;

		applyReachModifier(player, totalReachLevels);
	}

	private void applyReachModifier(Player player, int reachLevel) {
		AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
		if (attribute == null)
			return;

		NamespacedKey reachKey = new NamespacedKey(Main.plugin, "reach_bonus");
		attribute.removeModifier(reachKey);

		if (reachLevel <= 0)
			return;

		AttributeModifier buff = new AttributeModifier(reachKey, reachLevel, AttributeModifier.Operation.ADD_NUMBER);
		attribute.addModifier(buff);
	}
}
