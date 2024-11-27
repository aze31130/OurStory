package ourstory.events;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;
import ourstory.utils.DeathMessage;

public class onPlayerDeath implements Listener {

	@EventHandler
	public void playerDeath(PlayerDeathEvent event) {
		Player player = event.getPlayer();

		Random random = new Random();
		int rng = random.nextInt(Main.deathMessagesEn.size());

		for (Player p : Bukkit.getOnlinePlayers())
			p.sendMessage(Component.text(DeathMessage.getRandomDeathMessage(p.locale(), player, rng)).color(NamedTextColor.DARK_RED));

		// Whisper to player his death location
		player.sendMessage(Component.text(
				"Death location (" + player.getWorld().getName() + ")" +
						" X: " + player.getLocation().getBlockX() +
						" Y: " + player.getLocation().getBlockY() +
						" Z: " + player.getLocation().getBlockZ())
				.color(NamedTextColor.LIGHT_PURPLE));

		// Death sound
		for (Player OnlinePlayer : Bukkit.getOnlinePlayers())
			OnlinePlayer.playSound(OnlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1000, 1);
	}
}
