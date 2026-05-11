package ourstory.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import ourstory.Main;
import ourstory.utils.Permissions;

public class Dummy implements BasicCommand {

	private static final Map<String, String> DUMMY_NAME = Map.of(
			"Normal", "Classic",
			"Undead", "Undead",
			"Spider", "Crawling",
			"Marin", "Aquatic");

	private static final Map<String, String> DUMMY_TAG = Map.of(
			"Undead", "sensitive_smite",
			"Spider", "sensitive_boa",
			"Marin", "sensitive_impaling");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.dummy"))
			return;

		if (!(sender.getSender() instanceof Player player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		World w = player.getWorld();
		ArmorStand dummy = (ArmorStand) w.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

		Set<String> baseName = new HashSet<>();
		for (String arg : args) {
			if (DUMMY_NAME.containsKey(arg))
				baseName.add(DUMMY_NAME.get(arg));
			if (DUMMY_TAG.containsKey(arg))
				dummy.setMetadata(DUMMY_TAG.get(arg), new FixedMetadataValue(Main.plugin, true));
		}

		if (baseName.isEmpty())
			baseName.add("Classic");

		String finalName = new ArrayList<>(baseName).stream()
				.filter(word -> !(word.equals("Classic") && baseName.size() > 1))
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.joining(" "));

		dummy.customName(Component.text(finalName + " Dummy"));
		dummy.setCustomNameVisible(true);
		dummy.setMetadata("isDummy", new FixedMetadataValue(Main.plugin, true));
	}

	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		return DUMMY_NAME.keySet();
	}
}
