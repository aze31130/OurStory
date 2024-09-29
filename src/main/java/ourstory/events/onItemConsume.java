package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class onItemConsume implements Listener {
	@EventHandler
	public void itemConsume(final PlayerItemConsumeEvent event) {
		org.bukkit.entity.Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();

		switch (item.getType()) {
			case RABBIT_STEW:
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2400, 1));
				break;

			case PUMPKIN_PIE:
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3600, 1));
				break;

			case BEETROOT_SOUP:
				player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 6000, 1));
				break;

			default:
				break;
		}
	}
}
