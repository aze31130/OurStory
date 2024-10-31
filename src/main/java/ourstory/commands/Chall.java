package ourstory.commands;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.advancement.*;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import ourstory.utils.Permissions;

public class Chall implements BasicCommand {

	private static final String completedAdvancement = "☑ Achieved";
	private static final String uncompletedAdvancement = "☒ Not Completed";

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.chall"))
			return;

		if (!(sender.getSender() instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player p = (Player) sender.getSender();
		List<Advancement> advancements = getAdvancements();

		if (args.length == 0) {
			int totalAdvancements = advancements.size();

			// Count finished advancements
			int succeed = (int) advancements.stream().filter(ad -> p.getAdvancementProgress(ad).isDone()).count();

			sender.getSender().sendMessage(
					Component.text("You achieved ").append(Component.text(succeed).color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD)
							.append(Component.text(" out of ").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
							.append(Component.text(totalAdvancements).color(NamedTextColor.DARK_PURPLE).decorate(TextDecoration.BOLD))
							.append(Component.text(" advancements.").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))));
			return;
		}

		String advancementName = args[0];

		// Get the advancement
		Optional<Advancement> adv = advancements.stream().filter(ad -> getAdvancementName(ad).equalsIgnoreCase(advancementName)).findFirst();

		if (adv.isEmpty()) {
			sender.getSender().sendMessage(Component.text("This Advancement doesn't exists !").color(NamedTextColor.RED));
			return;
		}

		Advancement advancement = adv.get();

		// Format advancement display
		Component lores = advancement.displayName().color(advancement.getDisplay().description().color())
				.appendNewline()
				.append(p.displayName().color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
				.appendNewline()
				.append(advancement.getDisplay().description().color(NamedTextColor.GRAY))
				.appendNewline();

		if (p.getAdvancementProgress(advancement).isDone()) {
			lores = lores.append(Component.text(Chall.completedAdvancement).color(NamedTextColor.DARK_GREEN));
		} else {
			lores = lores.append(Component.text(Chall.uncompletedAdvancement).color(NamedTextColor.DARK_RED));
		}

		if (advancement.getCriteria().size() > 1) {
			int criterias = advancement.getCriteria().size();
			int completedCriterias = p.getAdvancementProgress(advancement).getAwardedCriteria().size();

			float progress = (float) completedCriterias / criterias;

			// Compute gradiant from Dark Red to Gold colors ([170, 0, 0] to [255, 170, 0])
			TextColor gradientColor = TextColor.color(Math.round(170 + 170 * (progress)), Math.round(340 * progress), 0);

			// If progress is >50% then compute gradiant from Gold to DarkGreen ([255, 170, 0] to [0, 170, 0])
			if (progress > 0.5)
				gradientColor = TextColor.color(Math.round(510 * (1 - progress)), 170, 0);

			lores = lores.append(Component.text(" (").color(gradientColor)
					.append(Component.text(completedCriterias))
					.append(Component.text(" / "))
					.append(Component.text(criterias))
					.append(Component.text(")")));
		}

		Component intro = Component.text(p.getName() + " shared ");
		Component advComponent = Component.text(getAdvancementName(advancement)).color(advancement.getDisplay().description().color()).hoverEvent(HoverEvent.showText(lores));

		Bukkit.broadcast(intro.append(advComponent));
	}

	private List<Advancement> getAdvancements() {
		Iterator<Advancement> advancementsIt = Bukkit.getServer().advancementIterator();
		List<Advancement> advancements = new ArrayList<>();

		while (advancementsIt.hasNext()) {
			Advancement ns = advancementsIt.next();

			// Remove vanilla recipes and technical advancements
			if (ns.getKey().asString().startsWith("minecraft:recipes") || ns.getKey().asString().contains("blazeandcave:technical"))
				continue;
			advancements.add(ns);
		}

		return advancements;
	}

	private String getAdvancementName(Advancement a) {
		String advancementName = PlainTextComponentSerializer.plainText().serialize(a.displayName());
		return advancementName.replace("[", "").replace("]", "").replace(" ", "_");
	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		List<String> suggestions = new ArrayList<>();
		List<Advancement> allAdvancements = getAdvancements();

		for (Advancement a : allAdvancements) {
			String advancementName = getAdvancementName(a);

			if (args.length == 0 || (args.length > 0 && advancementName.toLowerCase().startsWith(args[0].toLowerCase())))
				suggestions.add(advancementName);
		}

		return suggestions;
	}
}
