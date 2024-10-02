package ourstory.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class onFireworkUse implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.isGliding() && event.getMaterial() == Material.FIREWORK_ROCKET) {

			if (player.getVelocity().length() > 0.85) {
				player.setVelocity(player.getVelocity().normalize());
				event.setCancelled(true);
			}
		}
	}
}
