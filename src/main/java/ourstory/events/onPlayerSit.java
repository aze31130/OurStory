package ourstory.events;

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
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Directional;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.Main;

public class onPlayerSit implements Listener {

	private static final String CHAIR_ENTITY_TAG = "chair";

	@EventHandler
	public void playerSit(PlayerInteractEvent event) {
		EquipmentSlot hand = event.getHand();
		if (hand == null || hand != EquipmentSlot.HAND)
			return;
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (block == null)
			return;
		if (player.getInventory().getItemInMainHand().getType() != Material.AIR)
			return;
		if (player.isSneaking() || player.isInsideVehicle())
			return;

		BlockData blockData = block.getBlockData();
		Location pigLocation = pigLocationForBlock(block, player);
		if (!isValidChairBlock(blockData))
			return;

		event.setCancelled(true);

		Pig pig = player.getWorld().spawn(pigLocation, Pig.class, this::configurePig);
		pig.addPassenger(player);
	}

	private void configurePig(Pig pig) {
		pig.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
		pig.setSaddle(true);
		pig.setInvulnerable(true);
		pig.setGravity(false);
		pig.setSilent(true);
		pig.setMetadata(CHAIR_ENTITY_TAG, new FixedMetadataValue(Main.plugin, true));
		pig.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 0, false, false));
		pig.setAI(false);
	}

	private boolean isValidChairBlock(BlockData block) {
		if (block instanceof Stairs stairs)
			return stairs.getHalf() == Bisected.Half.BOTTOM;
		if (block instanceof Slab slab)
			return slab.getType() == Slab.Type.BOTTOM;
		return false;
	}

	private Location pigLocationForBlock(Block block, Player player) {
		BlockData blockData = block.getBlockData();
		Location loc = block.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() - 0.45);
		loc.setZ(loc.getZ() + 0.5);

		if (blockData instanceof Directional directional) {
			BlockFace facing = directional.getFacing();
			switch (facing) {
				case SOUTH -> loc.setYaw(180);
				case WEST -> loc.setYaw(270);
				case EAST -> loc.setYaw(90);
				case NORTH -> loc.setYaw(0);
				default -> { /* leave default yaw */ }
			}
		} else {
			loc.setYaw(player.getLocation().getYaw() + 180);
		}

		if (blockData instanceof Stairs stairs) {
			Stairs.Shape shape = stairs.getShape();
			if (shape == Stairs.Shape.INNER_RIGHT || shape == Stairs.Shape.OUTER_RIGHT)
				loc.setYaw(loc.getYaw() + 45);
			else if (shape == Stairs.Shape.INNER_LEFT || shape == Stairs.Shape.OUTER_LEFT)
				loc.setYaw(loc.getYaw() - 45);
		}
		return loc;
	}

	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		if (e.getDismounted().hasMetadata(CHAIR_ENTITY_TAG))
			Bukkit.getScheduler().runTaskLater(Main.plugin, () -> e.getDismounted().remove(), 1L);
	}
}
