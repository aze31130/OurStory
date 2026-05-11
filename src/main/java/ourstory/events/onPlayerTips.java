package ourstory.events;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;

public class onPlayerTips {
	private onPlayerTips() {
		throw new IllegalStateException("Utility class");
	}

	public static void playerTips() {
		if (Bukkit.getOnlinePlayers().isEmpty())
			return;

		JSONArray tipsMessages;
		try {
			tipsMessages = Main.messages.getJSONArray("tips");
		} catch (JSONException e) {
			return;
		}

		if (tipsMessages.length() == 0)
			return;

		int randomIndex = ThreadLocalRandom.current().nextInt(tipsMessages.length());
		Bukkit.broadcast(Component.text("[Tip] " + tipsMessages.getString(randomIndex)).color(NamedTextColor.GREEN));
	}
}
