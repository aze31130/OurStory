package ourstory.utils;

import java.util.Locale;
import org.bukkit.entity.Player;
import ourstory.Main;

public class DeathMessage {
	public static String getRandomDeathMessage(Locale locale, Player player, int rng) {
		String country = locale.getCountry().toLowerCase();

		String deathMessage = Main.deathMessagesEn.get(rng);

		if (country.contains("fr"))
			deathMessage = Main.deathMessagesFr.get(rng);

		deathMessage = deathMessage.replace("[player]", player.getName());

		return deathMessage;
	}
}
