package ourstory.utils;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONException;
import ourstory.Main;

public final class DeathMessage {
	private DeathMessage() {
		throw new IllegalStateException("Utility class");
	}

	public static String getRandomDeathMessage(Locale locale, Player player) {
		JSONArray deathMessages;
		try {
			deathMessages = Main.messages.getJSONArray("death");
		} catch (JSONException e) {
			return player.getName() + " died.";
		}

		if (deathMessages.length() == 0)
			return player.getName() + " died.";

		int rng = ThreadLocalRandom.current().nextInt(deathMessages.length());
		return deathMessages.getString(rng).replace("[player]", player.getName());
	}
}
