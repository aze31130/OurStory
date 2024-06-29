package ourstory.commands;

import org.bukkit.command.CommandSender;

public abstract class Command {
	public String name;
	public String description;
	public String[] arguments;

	public Command(String name, String description, String[] arguments) {
		this.name = name;
		this.description = description;
		this.arguments = arguments;
	}

	public abstract void execute(CommandSender sender, String[] args);
}
