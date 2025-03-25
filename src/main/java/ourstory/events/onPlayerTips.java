package ourstory.events;

import java.util.Random;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;

public class onPlayerTips {
	public static void playerTips() {
		// Skip event if no one is online
		if (Bukkit.getOnlinePlayers().size() == 0)
			return;

		JSONArray tipsMessages = Main.messages.getJSONArray("tips");

		Random rng = new Random();
		int randomIndex = rng.nextInt(tipsMessages.length());

		Bukkit.broadcast(Component.text("[Tip] " + tipsMessages.getString(randomIndex)).color(NamedTextColor.GREEN));
	}
}
