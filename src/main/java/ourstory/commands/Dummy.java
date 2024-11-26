package ourstory.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import ourstory.utils.Permissions;

public class Dummy implements BasicCommand {

	private Plugin p = Bukkit.getPluginManager().getPlugin("OurStory");

	private final Map<String, String> DummyName = Map.of(
			"Normal", "Classic ",
			"Undead", "Undead ",
			"Spider", "Crawling ",
			"Marin", "Aquatic ");

	private final Map<String, String> DummyTag = Map.of(
			"Undead", "sensitive_smite",
			"Spider", "sensitive_boa",
			"Marin", "sensitive_impaling");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.dummy"))
			return;

		if (!(sender.getSender() instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player player = (Player) sender.getSender();
		World w = player.getWorld();

		ArmorStand dummy = (ArmorStand) w.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

		String base_name = "";
		if (args.length > 0) {
			for (String argus : args) {
				if (DummyName.containsKey(argus))
					base_name += DummyName.get(argus);
				if (DummyTag.containsKey(argus))
					dummy.setMetadata(DummyTag.get(argus), new FixedMetadataValue(p, true));
			}
		}
		if (base_name.isEmpty())
			base_name = "Normal ";

		// Don't let duplicate name be there and sort them like : Undead Crawling Aquatic Dummy. Moreover,
		// remove Classic if another one is there
		String[] words = base_name.split("\\s+");
		base_name = Arrays.stream(words)
				.filter(word -> !(word.equals("Classic") && words.length > 1))
				.distinct()
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.joining(" "));
		dummy.customName(Component.text(base_name + " Dummy"));
		dummy.setCustomNameVisible(true);

		dummy.setMetadata("isDummy", new FixedMetadataValue(p, true));

	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		List<String> suggestions = new ArrayList<>();
		suggestions.add("Normal");
		suggestions.add("Undead");
		suggestions.add("Spider");
		suggestions.add("Marin");
		return suggestions;
	}
}
