package ourstory.events;

import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.utils.EnchantItem;

public class onPhoenixDeath implements Listener {
	@EventHandler
	public void phoenixDeath(PlayerDeathEvent event) {
		Player player = event.getPlayer();

		Random random = new Random();

		// Test for Phoenix enchant
		ItemStack[] armorContents = player.getInventory().getArmorContents();

		int totalPhoenixLevel = 0;

		for (ItemStack armor : armorContents)
			totalPhoenixLevel += EnchantItem.getEnchantAmount(armor, "phoenix");

		if (random.nextInt(0, 101) < (totalPhoenixLevel * 2.5)) {
			player.sendMessage(Component.text("You got blessed by the Phoenix enchant ! Your inventory has been safeguarded !").color(NamedTextColor.GREEN));
			event.setKeepInventory(true);
			event.setDroppedExp(0);
			event.getDrops().clear();
		}
	}
}
