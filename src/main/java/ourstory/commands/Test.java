package ourstory.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.skills.Annihilation;
import ourstory.utils.Permissions;

public class Test implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.test"))
			return;

		Annihilation an = new Annihilation();
		an.cast(sender.getExecutor(), null);
	}
}