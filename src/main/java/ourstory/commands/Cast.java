package ourstory.commands;

import java.util.Collection;
import java.util.Map;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.skills.Annihilation;
import ourstory.skills.LaserExplosion;
import ourstory.skills.LaserMatrix;
import ourstory.skills.Skills;
import ourstory.utils.Permissions;

public class Cast implements BasicCommand {

	private final Map<String, Skills> skills = Map.of(
			"Annihilation", new Annihilation(),
			"LaserMatrix", new LaserMatrix(),
			"LaserExplosion", new LaserExplosion());

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.cast"))
			return;

		Player player = (Player) sender.getSender();

		if (args.length == 0) {
			player.sendMessage("You need to provide a skill name");
			return;
		}

		for (String s : args) {
			if (!skills.containsKey(s))
				continue;

			skills.get(s).cast(player, player.getNearbyEntities(50, 50, 50));
		}
	}

	/*
	 * /boss <bossname> <difficulty>
	 */
	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		return skills.keySet();
	}
}
