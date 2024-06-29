package ourstory.events;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ourstory.commands.Craft;
import ourstory.commands.EnderChest;

public class CommandEvent implements Listener {
	private final List<ourstory.commands.Command> commands = List.of(
			new EnderChest(),
			new Craft());

	@EventHandler
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

		for (ourstory.commands.Command c : commands) {
			if (c.name.equals(cmd.getName()))
				c.execute(sender, args);
		}
		return false;
	}
}
