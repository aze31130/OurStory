package ourstory.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class onPlayerInteract implements Listener {
	/*
	 * Removes player that breaks farmland if jumped on top
	 */
	@EventHandler
	public void cancelPlayerJumpOnFarmland(PlayerInteractEvent e) {
		if (e == null)
			return;

		// Disable jumping on farmlands
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType().equals(Material.FARMLAND))
			e.setCancelled(true);
	}
}
