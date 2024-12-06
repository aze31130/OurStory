package ourstory.events;

import java.util.Arrays;
import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class onDisplayItem implements Listener {

	private List<String> triggerWords = Arrays.asList("*", "@", "[item]");

	@EventHandler
	public void displayItemInChat(AsyncChatEvent event) {
		Component message = event.originalMessage();
		String content = PlainTextComponentSerializer.plainText().serialize(message);

		for (String trigger : triggerWords) {
			if (content.equals(trigger)) {
				// TODO display player's item in mainhand if exist
			}
		}
	}
}
