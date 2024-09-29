package ourstory.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class onPlayerJoin implements Listener {
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		org.bukkit.entity.Player player = event.getPlayer();

		player.sendMessage(Component.text("Welcome back to OurStory " + player.getName() + " !").color(NamedTextColor.AQUA));
	}
}
