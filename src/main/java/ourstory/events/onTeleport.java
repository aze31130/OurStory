package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class onTeleport implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		TeleportCause cause = event.getCause();

		switch (cause) {
			case ENDER_PEARL:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
				break;

			case CHORUS_FRUIT:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 700, 0));
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0));
				break;

			default:
				break;
		}
	}
}
