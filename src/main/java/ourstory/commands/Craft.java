package ourstory.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class Craft implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		sender.getSender().sendMessage("craft command !");
	}
}
