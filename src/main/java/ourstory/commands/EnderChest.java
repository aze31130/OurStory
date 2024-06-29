package ourstory.commands;

import org.bukkit.command.CommandSender;

public class EnderChest extends Command {
	public EnderChest() {
		super(
				"ec",
				"Opens your own enderchest.",
				new String[] {});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage("ec command !");
	}
}
