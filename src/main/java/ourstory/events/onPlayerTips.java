package ourstory.events;

import java.util.Random;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.Main;

public class onPlayerTips {
	public static void playerTips() {
		Random rng = new Random();
		int randomIndex = rng.nextInt(Main.tipMessages.size());

		Bukkit.broadcast(Component.text("[Tip] " + Main.tipMessages.get(randomIndex)).color(NamedTextColor.GREEN));
	}
}