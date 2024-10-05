package ourstory.utils;

import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Permissions {
	public Permissions() {
		throw new IllegalStateException("You cannot instanciate this class. It should only contain static methods.");
	}

	public static boolean checkPermissions(CommandSender sender, String requestedPermission) {
		boolean allowedPerm = sender.hasPermission(requestedPermission);

		if (!allowedPerm)
			sender.sendMessage(Component.text("You do not have the permission to perform this command !").color(NamedTextColor.RED));

		return allowedPerm;
	}
}
