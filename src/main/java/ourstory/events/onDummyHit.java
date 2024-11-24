package ourstory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class onDummyHit implements Listener {
	@EventHandler
	public void dummyHit(EntityDamageByEntityEvent event) {
		// Cancel if not player
		if (!(event.getDamager() instanceof Player))
			return;

		// Cancel if not dummy
		if (event.getEntity().getMetadata("isDummy").size() == 0)
			return;

		event.setCancelled(true);

		Player p = (Player) event.getDamager();
		p.sendMessage(Component.text("Dmg ").append(Component.text(String.format("%.2f", event.getDamage())).color(NamedTextColor.RED)));
	}
}
