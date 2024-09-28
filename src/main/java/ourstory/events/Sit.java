package ourstory.events;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ourstory.Main;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class Sit implements Listener {
	Main plugin;

	public Sit(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSit(PlayerInteractEvent event) {

		if (event.getHand() == null || !event.getHand().equals(EquipmentSlot.HAND))
			return;
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;

		var block = event.getClickedBlock();
		var block_data = block.getBlockData();

		if (!isValidChairBlock(block_data))
			return;

		var player = event.getPlayer();

		if (player.isSneaking())
			return;
		if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR))
			return;
		if (player.isInsideVehicle())
			return;

		event.setCancelled(true);

		var pig_location = pigLocationForBlock(block, player);
		var pig = player.getWorld().spawn(pig_location, Pig.class, (pig_ -> this.configurePig(pig_)));
		pig.addPassenger(player);
	}

	private boolean isValidChairBlock(BlockData block) {
		if (block instanceof Stairs) {
			var bisected = (Bisected) block;
			if (bisected.getHalf().equals(Bisected.Half.BOTTOM))
				return true;
		}
		if (block instanceof Slab) {
			var slab = (Slab) block;
			if (slab.getType().equals(Slab.Type.BOTTOM))
				return true;
		}
		return false;
	}

	private Location pigLocationForBlock(Block block, Player player) {
		BlockData blockData = block.getBlockData();
		Location loc = block.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() + -0.4);
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
					// unreachable
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

	private void configurePig(Pig pig) {
		// set movement speed to 0 to entity to not move when steering item(carrot on a
		// stick) held
		pig.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
		pig.setSaddle(true);
		pig.setInvulnerable(true);
		pig.setSilent(true);
		pig.setMetadata(CHAIR_ENTITY_TAG, new FixedMetadataValue(this.plugin, true));
		pig.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));
		pig.setAI(false);
	}

	public static String CHAIR_ENTITY_TAG = "chair_entity_tag";
}
