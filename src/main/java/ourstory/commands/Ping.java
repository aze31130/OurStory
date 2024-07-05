package ourstory.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.utils.Permissions;

public class Ping implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.ping"))
			return;

		if (!(sender.getSender() instanceof org.bukkit.entity.Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		org.bukkit.entity.Player p = (org.bukkit.entity.Player) sender.getSender();
		p.sendMessage("Your latency: " + p.getPing() + "ms.");
	}
}
