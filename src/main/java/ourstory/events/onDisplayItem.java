package ourstory.events;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class onDisplayItem implements Listener {

	private static final List<String> TRIGGER_WORDS = List.of("*", "@", "[item]");

	@EventHandler
	public void displayItemInChat(AsyncChatEvent event) {
		Component message = event.originalMessage();
		String content = PlainTextComponentSerializer.plainText().serialize(message);

		if (!TRIGGER_WORDS.contains(content))
			return;

		Player player = event.getPlayer();
		ItemStack mainHandItem = player.getInventory().getItemInMainHand();
		if (mainHandItem.isEmpty())
			return;

		ItemMeta meta = mainHandItem.getItemMeta();
		Component itemName = (meta != null && meta.hasDisplayName())
				? meta.displayName()
				: Component.text(mainHandItem.getType().name());

		TextComponent result = Component.text(player.getName() + " shared ")
				.append(itemName)
				.hoverEvent(mainHandItem.asHoverEvent());

		for (Player p : Bukkit.getOnlinePlayers())
			p.sendMessage(result);

		event.setCancelled(true);
	}
}
