package ourstory.utils;

import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Permissions {
	private Permissions() {
		throw new IllegalStateException("Utility class, cannot be instantiated.");
	}

	public static boolean checkPermissions(CommandSender sender, String requestedPermission) {
		if (sender.hasPermission(requestedPermission))
			return true;

		sender.sendMessage(Component.text("You do not have the permission to perform this command !").color(NamedTextColor.RED));
		return false;
	}
}
