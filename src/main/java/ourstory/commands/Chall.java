package ourstory.commands;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.advancement.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.css.RGBColor;
import io.papermc.paper.advancement.AdvancementDisplay.Frame;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.util.RGBLike;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import ourstory.utils.Permissions;

public class Chall implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.chall"))
			return;

		if (!(sender.getSender() instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player p = (Player) sender.getSender();

		if (args.length == 0) {

			long succeed = 0;
			long total_adv = 0;
			Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
			while (iterator.hasNext()) {
				Advancement actual_adv = iterator.next();
				String texti = PlainTextComponentSerializer.plainText().serialize(actual_adv.displayName());
				if (check_adv_types(texti)) {
					total_adv++;
					if (p.getAdvancementProgress(actual_adv).isDone()) {
						succeed++;
					}
				}
			}

			String noArgsChall = "§rYou achieved §a§l" + Long.toString(succeed) + "§r out of §5§l" + Long.toString(total_adv) + "§r Advancements.";
			sender.getSender().sendMessage(Component.text(noArgsChall));
			return;
		}

		String advname = "[" + args[0].replace("_", " ") + "]";

		Advancement found_adv = null;
		Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
		while (iterator.hasNext()) {
			Advancement actual_adv = iterator.next();
			String texti = PlainTextComponentSerializer.plainText().serialize(actual_adv.displayName());
			if (check_adv_types(texti)) {
				if (texti.equals(advname)) {
					found_adv = actual_adv;
				}
			}
		}

		if (found_adv == null) {
			sender.getSender().sendMessage(Component.text("This Advancement doesn't exists !").color(NamedTextColor.RED));
			return;
		}
		Bukkit.getConsoleSender().sendMessage(found_adv.getDisplay().description());
		Component lores = found_adv.displayName().color(found_adv.getDisplay().description().color()).appendNewline()
				.append(p.displayName().color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)).appendNewline()
				.append(found_adv.getDisplay().description()
						// Problème sur l'achievement Riddle Me This qui est multiligne, et qui ne colore QUE la première
						// ligne
						.color(NamedTextColor.GRAY))
				.appendNewline();
		if (p.getAdvancementProgress(found_adv).getAdvancement().getCriteria().size() == 1) {
			if (p.getAdvancementProgress(found_adv).isDone()) {
				lores = lores.append(Component.text("☑ Achieved").color(NamedTextColor.DARK_GREEN));
			} else {
				lores = lores.append(Component.text("☒ Not Completed").color(NamedTextColor.DARK_RED));
			}
		} else {
			String content = "☒ ";
			float progress_perone = (float) (p.getAdvancementProgress(found_adv).getAwardedCriteria().size())
					/ (float) (p.getAdvancementProgress(found_adv).getAwardedCriteria().size() + p.getAdvancementProgress(found_adv).getRemainingCriteria().size());
			// 170 => 255 , 0 => 170, 0 (Dark Red => Gold)
			TextColor gradient_color = TextColor.color(Math.round(170 + 170 * (progress_perone)), Math.round(340 * progress_perone), 0);
			if (progress_perone > 0.5) {
				// 255 => 0 , 0, 0 (Gold => Dark_Green)
				gradient_color = TextColor.color(Math.round(510 * (1 - progress_perone)), 170, 0);
			}

			if (progress_perone > 0.) {
				content = "☐ ";
				if (progress_perone >= 1) {
					content = "☑ ";
				}
			}
			lores = lores.append(Component.text(content).color(gradient_color))
					.append(Component.text(p.getAdvancementProgress(found_adv).getAwardedCriteria().size())
							.color(gradient_color))
					.append(Component.text(" / ")
							.color(gradient_color))
					.append(Component.text(p.getAdvancementProgress(found_adv).getAwardedCriteria().size() + p.getAdvancementProgress(found_adv).getRemainingCriteria().size())
							.color(gradient_color));
		}


		Bukkit.broadcast(Component.text(advname).color(found_adv.getDisplay().description().color())
				.hoverEvent(HoverEvent.showText(lores))

	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		List<String> suggestions = new ArrayList<>();
		Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
		while (iterator.hasNext()) {
			String texti = PlainTextComponentSerializer.plainText().serialize(iterator.next().displayName());
			texti = texti.replace("[", "").replace("]", "").replace(" ", "_");
			if (args.length > 0 && check_adv_types(texti) && texti.toLowerCase().startsWith(args[0].toLowerCase())) {
				suggestions.add(texti);
			}
			if (args.length == 0 && check_adv_types(texti)) {
				suggestions.add(texti);
			}
		}
		return suggestions;
	}

	private static boolean check_adv_types(String texti) {
		return !texti.startsWith("minecraft:recipes") && !texti.startsWith("minecraft:husbandry") && !texti.startsWith("blazeandcave:technical");
	}
}

