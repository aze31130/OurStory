package ourstory.commands;

import java.util.Collections;
import java.util.List;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.utils.Permissions;

public class Guild implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.party"))
			return;
	}

	/*
	 * /party invite <username> /party join
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		if (args.length == 0)
			return List.of("invite", "join");
		return Collections.emptyList();
	}
}
