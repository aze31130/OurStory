package ourstory.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
			"Normal", "Classic",
			"Undead", "Undead",
			"Spider", "Crawling",
			"Marin", "Aquatic");

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

		Set<String> baseName = new HashSet<String>();

		for (String argus : args) {
			if (DummyName.containsKey(argus))
				baseName.add(DummyName.get(argus));
			if (DummyTag.containsKey(argus))
				dummy.setMetadata(DummyTag.get(argus), new FixedMetadataValue(p, true));
		}

		if (baseName.isEmpty())
			baseName.add("Classic");

		// Don't let duplicate name be there and sort them like : Undead Crawling Aquatic Dummy. Moreover,
		// remove Classic if another one is there
		String finalName = new ArrayList<>(baseName).stream()
				.filter(word -> !(word.equals("Classic") && baseName.size() > 1))
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.joining(" "));

		dummy.customName(Component.text(finalName + " Dummy"));
		dummy.setCustomNameVisible(true);

		dummy.setMetadata("isDummy", new FixedMetadataValue(p, true));
	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		return DummyName.keySet();
	}
}
