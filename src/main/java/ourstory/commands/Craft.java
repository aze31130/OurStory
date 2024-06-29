package ourstory.commands;

import org.bukkit.command.CommandSender;

public class Craft extends Command {
	public Craft() {
		super(
				"c",
				"Opens crafting table.",
				new String[] {});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("craft command !");
	}
}
