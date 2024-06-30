package ourstory.commands;

import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.utils.Permissions;

public class Craft implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.craft"))
			return;

		final Player player = (Player) sender.getSender();
		player.openWorkbench(player.getLocation(), true);
	}
}
