package ourstory.commands;

import java.util.Iterator;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import ourstory.utils.Permissions;

public class Split implements BasicCommand {
	@Override
	public void execute(CommandSourceStack sender, String[] args) {
		if (!Permissions.checkPermissions(sender.getSender(), "ourstory.split"))
			return;

		if (!(sender.getSender() instanceof org.bukkit.entity.Player)) {
			sender.getSender().sendMessage("Only a player can run this command !");
			return;
		}

		org.bukkit.entity.Player p = (org.bukkit.entity.Player) sender.getSender();
		ItemStack item = p.getInventory().getItemInMainHand();

		if (!item.getType().equals(Material.ENCHANTED_BOOK)) {
			sender.getSender().sendMessage("You need to hold an enchanted book !");
			return;
		}

		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
		int enchantAmount = meta.getStoredEnchants().size();

		int totalLevel = 0;
		for (Integer value : meta.getStoredEnchants().values())
			totalLevel += value;

		int levelPrice = enchantAmount * 3 + totalLevel;

		if (enchantAmount <= 1) {
			sender.getSender().sendMessage("You need at least two enchant to split a book !");
			return;
		}

		if (!p.getInventory().containsAtLeast(new ItemStack(Material.BOOK), enchantAmount)) {
			sender.getSender().sendMessage("You need to have at least " + enchantAmount + " vanilla book to split this book !");
			return;
		}

		if (p.getLevel() < levelPrice) {
			sender.getSender().sendMessage("You need at least " + levelPrice + " levels to split this book !");
			return;
		}

		p.setLevel(p.getLevel() - levelPrice);
		splitEnchants(p, item);
		sender.getSender().sendMessage("Successfully splitted for " + levelPrice + " levels.");
	}

	private static void splitEnchants(org.bukkit.entity.Player player, ItemStack book) {
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
