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

		if (cause == TeleportCause.ENDER_PEARL)
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
	}
}
