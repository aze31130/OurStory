package ourstory.utils;

import java.util.List;
import org.bukkit.entity.Player;

public class PlayerUtils {
	public static void broadcastToPlayers(List<Player> players, String message) {
		for (Player p : players)
			p.sendMessage(message);
	}
}
