package ourstory.commands;

import java.util.Arrays;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.skills.LaserExplosion;
import ourstory.utils.Permissions;

public class Test implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.test"))
			return;

		LaserExplosion an = new LaserExplosion();

		an.cast(sender.getExecutor(), Arrays.asList(sender.getExecutor()));
	}
}
