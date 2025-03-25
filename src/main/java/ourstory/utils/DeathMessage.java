package ourstory.utils;

import java.util.Locale;
import java.util.Random;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import ourstory.Main;

public class DeathMessage {
	public static String getRandomDeathMessage(Locale locale, Player player) {
		JSONArray deathMessages = Main.messages.getJSONArray("death");
		Random random = new Random();
		int rng = random.nextInt(deathMessages.length());

		String deathMessage = deathMessages.getString(rng).replace("[player]", player.getName());

		return deathMessage;
	}
}
