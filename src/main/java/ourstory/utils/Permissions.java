package ourstory.utils;

import org.bukkit.command.CommandSender;

public class Permissions {
	public Permissions() {
		throw new IllegalStateException("You cannot instanciate this class. It should only contain static methods.");
	}

	public static boolean checkPermissions(CommandSender sender, String requestedPermission) {
		boolean allowedPerm = sender.hasPermission(requestedPermission);

		if (!allowedPerm)
			sender.sendMessage("You do not have the permission to perform this command !");

		return allowedPerm;
	}
}
