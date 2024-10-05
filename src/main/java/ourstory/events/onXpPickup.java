package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.utils.EnchantItem;

public class onXpPickup implements Listener {
	@EventHandler
	public void xpPickup(PlayerExpChangeEvent experience) {
		Player player = experience.getPlayer();
		int multiplier = 1;

		// LUCK potion effect works as exp multiplicator
		if (player.hasPotionEffect(PotionEffectType.LUCK)) {
			PotionEffect pe = player.getPotionEffect(PotionEffectType.LUCK);
			multiplier += pe.getAmplifier() + 1;
		}

		// Check for XP Hunter enchant
		ItemStack[] armorContents = player.getInventory().getArmorContents();
		int totalXPHunterLevels = 0;

		for (ItemStack armor : armorContents)
			totalXPHunterLevels += EnchantItem.getEnchantAmount(armor, "xp_hunter");

		// Sets maximum level of enchant
		if (totalXPHunterLevels > 4)
			totalXPHunterLevels = 4;

		int baseExp = experience.getAmount();
		int totalExp = (int) (baseExp * multiplier + (baseExp * 0.25 * totalXPHunterLevels));

		experience.setAmount(totalExp);
	}
}
