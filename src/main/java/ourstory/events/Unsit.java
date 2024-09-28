package ourstory.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import ourstory.Main;

public class Unsit implements Listener {

	Main plugin;

	public Unsit(Main plugin) {
		//
	}

	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		if (e.getDismounted().hasMetadata(Sit.CHAIR_ENTITY_TAG)) {
			Bukkit.getScheduler().runTaskLater(this.plugin, () -> e.getDismounted().remove(), 1L);
		}
	}

}
