package ourstory.events;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Directional;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class onPlayerSit implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void playerSit(PlayerInteractEvent event) {
		EquipmentSlot hand = event.getHand();

		if (hand == null || !hand.equals(EquipmentSlot.HAND))
			return;
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		BlockData blockData = block.getBlockData();
		Location pigLocation = pigLocationForBlock(block, player);

		if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR))
			return;
		if (!isValidChairBlock(blockData, pigLocation))
			return;
		if (player.isSneaking())
			return;
		if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR))
			return;
		if (player.isInsideVehicle())
			return;

		event.setCancelled(true);

		Pig pig = player.getWorld().spawn(pigLocation, Pig.class, this::configurePig);
		pig.addPassenger(player);
	}

	private void configurePig(Pig pig) {
		// movement speed to 0 to block carrot on a stick item effect
		pig.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
		pig.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
		pig.setSaddle(true);
		pig.setInvulnerable(false); // Allow player to kill the pig if chair moved
		pig.setSilent(true);
		pig.addScoreboardTag("chair");
		pig.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 0, false, false));
		pig.setAI(false);
	}

	private boolean isValidChairBlock(BlockData block, Location pigLocation) {
		// Check if a chair pig is already placed
		Collection<Entity> nearbyEntities = pigLocation.getWorld().getNearbyEntities(pigLocation, 1, 1, 1);

		for (Entity e : nearbyEntities) {
			if (e instanceof Pig && e.getScoreboardTags().contains("chair")) {
				Bukkit.getConsoleSender().sendMessage("cancel");
				return false;
			}
		}

		if (block instanceof Stairs) {
			Bisected bisected = (Bisected) block;
			if (bisected.getHalf().equals(Bisected.Half.BOTTOM))
				return true;
		}
		if (block instanceof Slab) {
			Slab slab = (Slab) block;
			if (slab.getType().equals(Slab.Type.BOTTOM))
				return true;
		}

		return false;
	}

	private Location pigLocationForBlock(Block block, Player player) {
		BlockData blockData = block.getBlockData();
		Location loc = block.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() - 0.3);
		loc.setZ(loc.getZ() + 0.5);

		if (blockData instanceof Directional) {
			BlockFace facing = ((Directional) blockData).getFacing();
			switch (facing) {
				case SOUTH:
					loc.setYaw(180);
					break;
				case WEST:
					loc.setYaw(270);
					break;
				case EAST:
					loc.setYaw(90);
					break;
				case NORTH:
					loc.setYaw(0);
					break;
				default:
			}
		} else {
			loc.setYaw(player.getLocation().getYaw() + 180);
		}

		if (blockData instanceof Stairs) {
			Stairs.Shape shape = ((Stairs) blockData).getShape();
			if (shape == Stairs.Shape.INNER_RIGHT || shape == Stairs.Shape.OUTER_RIGHT) {
				loc.setYaw(loc.getYaw() + 45);
			} else if (shape == Stairs.Shape.INNER_LEFT || shape == Stairs.Shape.OUTER_LEFT) {
				loc.setYaw(loc.getYaw() - 45);
			}
		}
		return loc;
	}
}
