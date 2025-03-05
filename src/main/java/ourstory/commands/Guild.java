package ourstory.commands;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.Main;
import ourstory.utils.Permissions;

public class Guild implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.guild"))
			return;
		// TODO
	}

	/*
	 * /party invite <username> /party join
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0)
			return List.of("create", "invite", "join");

		if (args[0].equalsIgnoreCase("join"))
			return Main.guilds.stream().map(g -> g.name).collect(Collectors.toList());

		return Collections.emptyList();
	}
}
