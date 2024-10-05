package ourstory.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class onFireworkUse implements Listener {
	/*
	 * Nerf elytra speed.
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.isGliding() && event.getMaterial() == Material.FIREWORK_ROCKET && player.getVelocity().length() > 0.70)
			event.setCancelled(true);
	}
}
