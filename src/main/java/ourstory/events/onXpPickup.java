package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class onXpPickup implements Listener {
	@EventHandler
	public void xpPickup(PlayerExpChangeEvent experience) {
		org.bukkit.entity.Player player = experience.getPlayer();
		int multiplier = 1;

		// LUCK potion effect works as exp multiplicator
		if (player.hasPotionEffect(PotionEffectType.LUCK)) {
			PotionEffect pe = player.getPotionEffect(PotionEffectType.LUCK);
			multiplier += pe.getAmplifier() + 1;
		}

		experience.setAmount(experience.getAmount() * multiplier);
	}
}
