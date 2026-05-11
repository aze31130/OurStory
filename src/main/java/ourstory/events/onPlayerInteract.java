package ourstory.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
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
		if (e.getAction() != Action.PHYSICAL)
			return;

		Block clicked = e.getClickedBlock();
		if (clicked == null)
			return;

		if (clicked.getType() == Material.FARMLAND)
			e.setCancelled(true);
	}
}
