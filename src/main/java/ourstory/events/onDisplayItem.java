package ourstory.events;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.ShowItem;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class onDisplayItem implements Listener {

	private List<String> triggerWords = Arrays.asList("*", "@", "[item]");

	@EventHandler
	public void displayItemInChat(AsyncChatEvent event) {
		Component message = event.originalMessage();
		String content = PlainTextComponentSerializer.plainText().serialize(message);

		if (!triggerWords.contains(content))
			return;

		Player player = event.getPlayer();
		ItemStack mainHandItem = player.getInventory().getItemInMainHand();
		HoverEvent<ShowItem> he = mainHandItem.asHoverEvent();

		if (mainHandItem == null || mainHandItem.getType().isAir())
			return;

		Component itemName = mainHandItem.getItemMeta().hasDisplayName()
				? mainHandItem.getItemMeta().displayName()
				: Component.text(mainHandItem.getType().name());

		TextComponent result = Component.text(player.getName() + " shared ").append(itemName).hoverEvent(he);

		for (Player p : Bukkit.getOnlinePlayers())
			p.sendMessage(result);

		// Cancel the message containing the trigger word
		event.setCancelled(true);
	}
}
