package ourstory.commands;

import java.util.List;
import org.bukkit.entity.Player;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import de.bluecolored.bluemap.api.BlueMapAPI;

public class Map implements BasicCommand {

	private static final List<String> STATES = List.of("hide", "show");

	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!BlueMapAPI.getInstance().isPresent())
			return;

		if (!(sender.getSender() instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		if (args.length == 0) {
			sender.getSender().sendMessage(Component.text("Usage: /map <hide|show>").color(NamedTextColor.RED));
			return;
		}

		String state = args[0].toLowerCase();

		if (!STATES.contains(state)) {
			sender.getSender().sendMessage(Component.text("Invalid option. Use: hide or show").color(NamedTextColor.RED));
			return;
		}

		Player player = (Player) sender.getSender();
		Boolean isVisible = state.equals("show");
		BlueMapAPI api = BlueMapAPI.getInstance().get();

		api.getWebApp().setPlayerVisibility(player.getUniqueId(), isVisible);
		player.sendMessage(Component.text(isVisible ? "You are now visible on the map." : "You are now hidden from the map.", isVisible ? NamedTextColor.GREEN : NamedTextColor.YELLOW));
	}

	/*
	 * /map <hide / show>
	 */
	@Override
	public List<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		return List.of("hide", "show");
	}
}
