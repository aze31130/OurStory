package ourstory.events;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.ShowItem;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

		String itemName = mainHandItem.getItemMeta().hasDisplayName()
				? PlainTextComponentSerializer.plainText().serialize(mainHandItem.getItemMeta().displayName())
				: mainHandItem.getType().name();
		TextComponent result = Component.text(itemName, NamedTextColor.AQUA, TextDecoration.BOLD, TextDecoration.ITALIC).hoverEvent(he);

		for (Audience audience : Bukkit.getOnlinePlayers())
			audience.sendMessage(event.renderer().render(player, player.displayName(), result, audience));

		event.message(result);
		// event.setCancelled(true);
	}
}
