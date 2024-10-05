package ourstory.commands;

import java.util.Iterator;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ourstory.utils.Permissions;

public class Split implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.split"))
			return;

		if (!(sender.getSender() instanceof Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		Player p = (Player) sender.getSender();
		ItemStack item = p.getInventory().getItemInMainHand();

		if (!item.getType().equals(Material.ENCHANTED_BOOK)) {
			sender.getSender().sendMessage(Component.text("You need to hold an enchanted book !").color(NamedTextColor.RED));
			return;
		}

		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
		int enchantAmount = meta.getStoredEnchants().size();

		int totalLevel = 0;
		for (Integer value : meta.getStoredEnchants().values())
			totalLevel += value;

		int levelPrice = enchantAmount * 3 + totalLevel;

		if (enchantAmount <= 1) {
			sender.getSender().sendMessage(Component.text("You need at least two enchant to split a book !").color(NamedTextColor.RED));
			return;
		}

		if (!p.getInventory().containsAtLeast(new ItemStack(Material.BOOK), enchantAmount)) {
			sender.getSender().sendMessage(Component.text("You need to have at least " + enchantAmount + " vanilla book to split this book !").color(NamedTextColor.RED));
			return;
		}

		if (p.getLevel() < levelPrice) {
			sender.getSender().sendMessage(Component.text("You need at least " + levelPrice + " levels to split this book !").color(NamedTextColor.RED));
			return;
		}

		// Check if the player has enough space in inventory for the split books
		int emptySlots = 0;
		for (ItemStack content : p.getInventory().getStorageContents())
			if (content == null || content.getType() == Material.AIR)
				emptySlots++;

		if (emptySlots < enchantAmount) {
			sender.getSender().sendMessage(Component.text("You need at least " + enchantAmount + " empty inventory slots to split this book!").color(NamedTextColor.RED));
			return;
		}

		p.setLevel(p.getLevel() - levelPrice);
		splitEnchants(p, item);
		sender.getSender().sendMessage(Component.text("Successfully splitted for " + levelPrice + " levels.").color(NamedTextColor.GREEN));
	}

	private static void splitEnchants(Player player, ItemStack book) {
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
		Map<Enchantment, Integer> enchants = meta.getStoredEnchants();
		Iterator<Enchantment> var5 = enchants.keySet().iterator();

		while (var5.hasNext()) {
			Enchantment enchant = (Enchantment) var5.next();
			ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
			EnchantmentStorageMeta im = (EnchantmentStorageMeta) is.getItemMeta();
			im.addStoredEnchant(enchant, (Integer) enchants.get(enchant), true);
			is.setItemMeta(im);

			player.getInventory().addItem(new ItemStack[] {is});
			player.getInventory().removeItem(new ItemStack[] {new ItemStack(Material.BOOK)});

			player.sendMessage("Obtained " + enchant.getKey() + " book");
		}

		player.getInventory().remove(book);
		player.updateInventory();
	}
}
