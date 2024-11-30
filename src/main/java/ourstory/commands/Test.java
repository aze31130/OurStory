package ourstory.commands;

import java.util.Arrays;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.skills.LaserMatrix;
import ourstory.utils.Permissions;

public class Test implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.test"))
			return;

		LaserMatrix an = new LaserMatrix();

		an.cast(sender.getExecutor(), Arrays.asList(sender.getExecutor()));
	}
}
