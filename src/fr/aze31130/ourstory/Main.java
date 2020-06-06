package fr.aze31130.ourstory;

import org.bukkit.entity.Bat;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OURSTORY]: Plugin v 1.5.1 Enabled !");
		this.getConfig().addDefault("messages.nopermission", (Object)"§cYou do not have the permission to perform this command");
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		getServer().getPluginManager().registerEvents(this, this);
		CustomCraft();
	}
	
	public void CustomCraft(){
		//firework + potions
        ItemStack nether_elytra = new ItemStack(Material.ELYTRA);
        ItemMeta nether_elytra_meta = nether_elytra.getItemMeta();
        nether_elytra_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lElytra");
        nether_elytra_meta.setUnbreakable(true);
        nether_elytra_meta.addEnchant(Enchantment.MENDING, 1, true);
        nether_elytra.setItemMeta(nether_elytra_meta);
        NamespacedKey nether_elytra_key = new NamespacedKey(this, "nether_elytra");
        ShapedRecipe nether_elytra_recipe = new ShapedRecipe(nether_elytra_key, nether_elytra);
        nether_elytra_recipe.shape("NNN","NEN","NNN");
        nether_elytra_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_elytra_recipe.setIngredient('E', Material.ELYTRA);
        getServer().addRecipe(nether_elytra_recipe);
		
		ItemStack nether_bow = new ItemStack(Material.BOW);
        ItemMeta nether_bow_meta = nether_bow.getItemMeta();
        nether_bow_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lBow");
        nether_bow_meta.setUnbreakable(true);
        nether_bow.setItemMeta(nether_bow_meta);
        NamespacedKey nether_bow_key = new NamespacedKey(this, "nether_bow");
        ShapedRecipe nether_bow_recipe = new ShapedRecipe(nether_bow_key, nether_bow);
        nether_bow_recipe.shape(" NS","N S"," NS");
        nether_bow_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_bow_recipe.setIngredient('S', Material.STRING);
        getServer().addRecipe(nether_bow_recipe);
        
		ItemStack nether_sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta nether_sword_meta = nether_sword.getItemMeta();
        nether_sword_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lSword");
        nether_sword_meta.setUnbreakable(true);
        nether_sword.setItemMeta(nether_sword_meta);
        NamespacedKey nether_sword_key = new NamespacedKey(this, "nether_sword");
        ShapedRecipe nether_sword_recipe = new ShapedRecipe(nether_sword_key, nether_sword);
        nether_sword_recipe.shape(" N "," N "," S ");
        nether_sword_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_sword_recipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(nether_sword_recipe);
        
		ItemStack nether_pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta nether_pickaxe_meta = nether_pickaxe.getItemMeta();
        nether_pickaxe_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lPickaxe");
        nether_pickaxe_meta.setUnbreakable(true);
        nether_pickaxe.setItemMeta(nether_pickaxe_meta);
        NamespacedKey nether_pickaxe_key = new NamespacedKey(this, "nether_pickaxe");
        ShapedRecipe nether_pickaxe_recipe = new ShapedRecipe(nether_pickaxe_key, nether_pickaxe);
        nether_pickaxe_recipe.shape("NNN"," S "," S ");
        nether_pickaxe_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_pickaxe_recipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(nether_pickaxe_recipe);
        
        ItemStack nether_spade = new ItemStack(Material.DIAMOND_SPADE);
        ItemMeta nether_spade_meta = nether_spade.getItemMeta();
        nether_spade_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lShovel");
        nether_spade_meta.setUnbreakable(true);
        nether_spade.setItemMeta(nether_spade_meta);
        NamespacedKey nether_spade_key = new NamespacedKey(this, "nether_spade");
        ShapedRecipe nether_spade_recipe = new ShapedRecipe(nether_spade_key, nether_spade);
        nether_spade_recipe.shape(" N "," S "," S ");
        nether_spade_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_spade_recipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(nether_spade_recipe);
        
        ItemStack nether_axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta nether_axe_meta = nether_axe.getItemMeta();
        nether_axe_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lAxe");
        nether_axe_meta.setUnbreakable(true);
        nether_axe.setItemMeta(nether_axe_meta);
        NamespacedKey nether_axe_key = new NamespacedKey(this, "nether_axe");
        ShapedRecipe nether_axe_recipe = new ShapedRecipe(nether_axe_key, nether_axe);
        nether_axe_recipe.shape("NN ","NS "," S ");
        nether_axe_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_axe_recipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(nether_axe_recipe);
        
        ItemStack nether_hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta nether_hoe_meta = nether_hoe.getItemMeta();
        nether_hoe_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lHoe");
        nether_hoe_meta.setUnbreakable(true);
        nether_hoe.setItemMeta(nether_hoe_meta);
        NamespacedKey nether_hoe_key = new NamespacedKey(this, "nether_hoe");
        ShapedRecipe nether_hoe_recipe = new ShapedRecipe(nether_hoe_key, nether_hoe);
        nether_hoe_recipe.shape("NN "," S "," S ");
        nether_hoe_recipe.setIngredient('N', Material.NETHER_STAR);
        nether_hoe_recipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(nether_hoe_recipe);
        
        ItemStack nether_helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta nether_helmet_meta = nether_helmet.getItemMeta();
        nether_helmet_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lHelmet");
        nether_helmet_meta.setUnbreakable(true);
        nether_helmet.setItemMeta(nether_helmet_meta);
        NamespacedKey nether_helmet_key = new NamespacedKey(this, "nether_helmet");
        ShapedRecipe nether_helmet_recipe = new ShapedRecipe(nether_helmet_key, nether_helmet);
        nether_helmet_recipe.shape("   ","NNN","N N");
        nether_helmet_recipe.setIngredient('N', Material.NETHER_STAR);
        getServer().addRecipe(nether_helmet_recipe);

        ItemStack nether_chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta nether_chestplate_meta = nether_chestplate.getItemMeta();
        nether_chestplate_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lChestplate");
        nether_chestplate_meta.setUnbreakable(true);
        nether_chestplate.setItemMeta(nether_chestplate_meta);
        NamespacedKey nether_chestplate_key = new NamespacedKey(this, "nether_chestplate");
        ShapedRecipe nether_chestplate_recipe = new ShapedRecipe(nether_chestplate_key, nether_chestplate);
        nether_chestplate_recipe.shape("N N","NNN","NNN");
        nether_chestplate_recipe.setIngredient('N', Material.NETHER_STAR);
        getServer().addRecipe(nether_chestplate_recipe);
        
        ItemStack nether_leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta nether_leggings_meta = nether_leggings.getItemMeta();
        nether_leggings_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lLeggings");
        nether_leggings_meta.setUnbreakable(true);
        nether_leggings.setItemMeta(nether_leggings_meta);
        NamespacedKey nether_leggings_key = new NamespacedKey(this, "nether_leggings");
        ShapedRecipe nether_leggings_recipe = new ShapedRecipe(nether_leggings_key, nether_leggings);
        nether_leggings_recipe.shape("NNN","N N","N N");
        nether_leggings_recipe.setIngredient('N', Material.NETHER_STAR);
        getServer().addRecipe(nether_leggings_recipe);
        
        ItemStack nether_boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta nether_boots_meta = nether_boots.getItemMeta();
        nether_boots_meta.setDisplayName("§c§lWither's §c§lUltimate §c§lBoots");
        nether_boots_meta.setUnbreakable(true);
        nether_boots.setItemMeta(nether_boots_meta);
        NamespacedKey nether_boots_key = new NamespacedKey(this, "nether_boots");
        ShapedRecipe nether_boots_recipe = new ShapedRecipe(nether_boots_key, nether_boots);
        nether_boots_recipe.shape("   ","N N","N N");
        nether_boots_recipe.setIngredient('N', Material.NETHER_STAR);
        getServer().addRecipe(nether_boots_recipe);
        
        final ItemStack LuckPotion = new ItemStack(Material.POTION);
        final PotionMeta LuckPotion_meta = (PotionMeta)LuckPotion.getItemMeta();
        LuckPotion_meta.addCustomEffect(new PotionEffect(PotionEffectType.LUCK, 6000, 0, false, true), true);
        LuckPotion_meta.setDisplayName("Potion of §a§lDOUBLE §a§lEXPERIENCE");
        LuckPotion.setItemMeta(LuckPotion_meta);
        final ShapelessRecipe LuckPotion_recipe = new ShapelessRecipe(LuckPotion);
        
        LuckPotion_recipe.addIngredient(Material.POTION);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        LuckPotion_recipe.addIngredient(Material.EMERALD_BLOCK);
        getServer().addRecipe(LuckPotion_recipe);
        
        
        ItemStack Result = new ItemStack(Material.IRON_BLOCK, 1);
        FurnaceRecipe recipe = new FurnaceRecipe(Result, Material.IRON_BARDING);
        getServer().addRecipe(recipe);
        
        ItemStack Result2 = new ItemStack(Material.GOLD_BLOCK, 1);
        FurnaceRecipe recipe2 = new FurnaceRecipe(Result2, Material.GOLD_BARDING);
        getServer().addRecipe(recipe2);
        
        ItemStack Result3 = new ItemStack(Material.DIAMOND_BLOCK, 1);
        FurnaceRecipe recipe3 = new FurnaceRecipe(Result3, Material.DIAMOND_BARDING);
        getServer().addRecipe(recipe3);
        
        
        final ItemStack is = new ItemStack(Material.POTION);
        
        final PotionMeta pm = (PotionMeta)is.getItemMeta();
        pm.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 3600, 1, true, true), true);
        pm.setDisplayName("§rSplash Potion of Mining Fatigue");
        is.setItemMeta((ItemMeta)pm);
        final ShapelessRecipe r = new ShapelessRecipe(is);
        r.addIngredient(Material.QUARTZ);
        getServer().addRecipe(r);
	}
	
	@EventHandler (priority = EventPriority.LOW)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerIP = player.getAddress().getHostName();
        
        if ((player.getName().equalsIgnoreCase("aze31130")) && (!playerIP.equals("guilhem-1.home"))){
        	//player.kickPlayer(ChatColor.RED + "Kicked by SERVER CONSOLE");
        } else {
        	player.sendMessage(ChatColor.DARK_AQUA + "Ourstory version 1.5.1: §a§lThe §a§lAniversary §a§lGame §a§lUpdate");
        	player.sendMessage(ChatColor.DARK_AQUA + "Type /changelog to see list of all changes");
        	player.sendMessage(ChatColor.GREEN + "Welcome back to OurStory " + player.getName());
        }
    }
	
	
	@EventHandler (priority = EventPriority.LOW)
    public void onItemConsume(final PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		//ItemStack item2 = player.getInventory().getItemInOffHand();
		
		if (item.getType() == Material.RABBIT_STEW){
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2400, 1));
        }
		
		if (item.getType() == Material.PUMPKIN_PIE){
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3600, 1));
        }
		
		if (item.getType() == Material.BEETROOT_SOUP){
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000, 1));
        }
    }
	
	@EventHandler
	public void mobDeath(EntityDeathEvent event){
		if(event.getEntity().getKiller() != null){
			//Check if the mob has been killed by a player
			if(event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player)){
				
				Random random = new Random();
				int RNG = 0;
				int LootingLevel = 0;
				
				if (event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS)){
					LootingLevel = event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
				}
				
				switch(LootingLevel){
					case 1:
						RNG = random.nextInt(975000) + 1;
						break;
					case 2:
						RNG = random.nextInt(900000) + 1;
						break;
					case 3:
						RNG = random.nextInt(825000) + 1;
						break;
					case 4:
						RNG = random.nextInt(750000) + 1;
						break;
					case 5:
						RNG = random.nextInt(600000) + 1;
						break;
					case 6:
						RNG = random.nextInt(500000) + 1;
						break;
					case 7:
						RNG = random.nextInt(350000) + 1;
						break;
					case 8:
						RNG = random.nextInt(200000) + 1;
						break;
					case 9:
						RNG = random.nextInt(100000) + 1;
						break;
					case 10:
						RNG = random.nextInt(10000) + 1;
						break;
					default:
						RNG = random.nextInt(1000000) + 1;
						break;
				}
				
			    if(RNG == 1){
			    	LivingEntity entity = event.getEntity();
			    	ItemStack monster_spawner = new ItemStack(Material.MOB_SPAWNER, 1);
			    	ItemMeta monster_spawner_meta = monster_spawner.getItemMeta();
			    	monster_spawner_meta.setUnbreakable(true);
			    	monster_spawner_meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
			    	List<String> monster_spawner_lore = Arrays.asList("This mythical spawner holds one of", "the rarest power in the world.", "");
			    	String mobType = "";
			    	Boolean isElibible = true;
			    	switch(event.getEntity().toString()){
			    		case "CraftBat":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lBat §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: BAT");
			    			mobType = "Bat";
			    			break;
			    		case "CraftBlaze":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lBlaze §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: BLAZE");
			    			mobType = "Blaze";
			    			break;
			    		case "CraftCaveSpider":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lCave_Spider §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: CAVE_SPIDER");
			    			mobType = "Cave_Spider";
			    			break;
			    		case "CraftChicken":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lChicken §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: CHICKEN");
			    			mobType = "Chicken";
			    			break;
			    		case "CraftCow":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lCow §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: COW");
			    			mobType = "Cow";
			    			break;
			    		case "CraftCreeper":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lCreeper §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: CREEPER");
			    			mobType = "Creeper";
			    			break;
			    		case "CraftDonkey":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lDonkey §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: DONKEY");
			    			mobType = "Donkey";
			    			break;
			    		case "CraftEnderman":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lEnderman §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: ENDERMAN");
			    			mobType = "Enderman";
			    			break;
			    		case "CraftEndermite":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lEndermite §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: ENDERMITE");
			    			mobType = "Endermite";
			    			break;
			    		case "CraftEvoker":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lEvoker §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: EVOKER");
			    			mobType = "Evoker";
			    			break;
			    		case "CraftGhast":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lGhast §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: GHAST");
			    			mobType = "Ghast";
			    			break;
			    		case "CraftGuardian":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lGuardian §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: GUARDIAN");
			    			mobType = "Guardian";
			    			break;
			    		case "CraftHorse{variant=HORSE, owner=null}":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lHorse §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: HORSE");
			    			mobType = "Horse";
			    			break;
			    		case "CraftHusk":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lHusk §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: HUSK");
			    			mobType = "Husk";
			    			break;
			    		case "CraftIronGolem":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lIron_Golem §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: IRONGOLEM");
			    			mobType = "Iron_Golem";
			    			break;
			    		case "CraftLlama":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lLlama §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: LLAMA");
			    			mobType = "Llama";
			    			break;
			    		case "CraftMagmaCube":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lMagma_Cube §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: MAGMA_CUBE");
			    			mobType = "Magma_Cube";
			    			break;
			    		case "CraftMushroomCow":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lMooshroom_Cow §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: MUSHROOM_COW");
			    			mobType = "Mooshroom_Cow";
			    			break;
			    		case "CraftMule":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lMule §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: MULE");
			    			mobType = "Mule";
			    			break;
			    		case "CraftOcelot":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lOcelot §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: OCELOT");
			    			mobType = "Ocelot";
			    			break;
			    		case "CraftParrot":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lParrot §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: PARROT");
			    			mobType = "Parrot";
			    			break;
			    		case "CraftPig":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lPig §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: PIG");
			    			mobType = "Pig";
			    			break;
			    		case "CraftPolarBear":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lPolar_Bear §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: POLAR_BEAR");
			    			mobType = "Polar_Bear";
			    			break;
			    		//case "CraftRabbit":
			    			//monster_spawner_meta.setDisplayName("§4§lMythical §c§lRabbit §c§lSpawner");
			    			//monster_spawner_lore.set(2, "Spawner type: RABBIT");
			    			//mobType = "Rabbit";
			    			//break;
			    		case "CraftSheep":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSheep §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SHEEP");
			    			mobType = "Sheep";
			    			break;
			    		case "CraftShulker":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lShulker §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SHULKER");
			    			mobType = "Shulker";
			    			break;
			    		case "CraftSilverfish":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSilverfish §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SILVERFISH");
			    			mobType = "Silverfish";
			    			break;
			    		case "CraftSkeleton":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSkeleton §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SKELETON");
			    			mobType = "Skeleton";
			    			break;
			    		case "CraftSkeletonHorse":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSkeleton_Horse §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SKELETON_HORSE");
			    			mobType = "Skeleton_Horse";
			    			break;
			    		case "CraftSlime":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSlime §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SLIME");
			    			mobType = "Slime";
			    			break;
			    		case "CraftSnowman":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSnowman §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SNOWMAN");
			    			mobType = "Snowman";
			    			break;
			    		case "CraftSpider":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSpider §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SPIDER");
			    			mobType = "Spider";
			    			break;
			    		case "CraftSquid":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lSquid §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: SQUID");
			    			mobType = "Squid";
			    			break;
			    		case "CraftStray":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lStray §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: STRAY");
			    			mobType = "Stray";
			    			break;
			    		case "CraftVex":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lVex §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: VEX");
			    			mobType = "Vex";
			    			break;
			    		case "CraftVillager":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lVillager §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: VILLAGER");
			    			mobType = "Villager";
			    			break;
			    		case "CraftVindicator":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lVindicator §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: VINDICATOR");
			    			mobType = "Vindicator";
			    			break;
			    		case "CraftWitch":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lWitch §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: WITCH");
			    			mobType = "Witch";
			    			break;
			    		case "CraftWitherSkeleton":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lWither_Skeleton §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: WITHER_SKELETON");
			    			mobType = "Wither_Skeleton";
			    			break;
			    		case "CraftWolf":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lWolf §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: WOLF");
			    			mobType = "Wolf";
			    			break;
			    		case "CraftZombie":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lZombie §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: ZOMBIE");
			    			mobType = "Zombie";
			    			break;
			    		case "CraftPigZombie":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lZombie_Pigman §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: PIG_ZOMBIE");
			    			mobType = "Zombie_Pigman";
			    			break;
			    		case "CraftVillagerZombie":
			    			monster_spawner_meta.setDisplayName("§4§lMythical §c§lZombie_Villager §c§lSpawner");
			    			monster_spawner_lore.set(2, "Spawner type: ZOMBIE_VILLAGER");
			    			mobType = "Zombie_Villager";
			    			break;
			    		default:
			    			isElibible = false;
			    			break;
			    	}
			    	
			    	if(isElibible){
			    		monster_spawner_meta.setLore(monster_spawner_lore);
			            monster_spawner.setItemMeta(monster_spawner_meta);
			            entity.getLocation().getWorld().dropItem(entity.getLocation(), monster_spawner);
				    	
				    	for (Player OnlinePlayer : Bukkit.getOnlinePlayers())
			            {
			            	OnlinePlayer.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "OurStory " + ChatColor.AQUA + "Player " + event.getEntity().getKiller().getName() + " just dropped a §4§lMythical " + mobType + " §c§lSpawner");
			            	OnlinePlayer.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Congratulation " + event.getEntity().getKiller().getName() + " on such an amazing achievement !");
			            	OnlinePlayer.playSound(OnlinePlayer.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1000, 1);
			            }
			    	}
			    }
			}
		}
	}
	
	@EventHandler
    public void craftItem(PrepareItemCraftEvent event){
		if(event.getRecipe() != null){
	        Material itemType = event.getRecipe().getResult().getType();
	        if(itemType == Material.END_CRYSTAL){
	        	event.getInventory().setResult(new ItemStack(Material.AIR));
	            for(HumanEntity player:event.getViewers()){
	                if(player instanceof Player){
	                    ((Player)player).sendMessage(ChatColor.RED + "This item has been temporarly disabled.");
	                }
	            }
	        }
		} else {
			return;
		}
    }
	
	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.MOB_SPAWNER)) {
			ItemStack item = event.getPlayer().getItemInHand();
			ItemMeta item_meta = item.getItemMeta();
			
			if (item_meta.hasLore() && item_meta.hasEnchant(Enchantment.VANISHING_CURSE) && item_meta.isUnbreakable() && (item_meta.getDisplayName().contains("§c§lSpawner")) && (item_meta.getDisplayName().contains("§4§lMythical"))) {

				CreatureSpawner cs = (CreatureSpawner)event.getBlock().getState();
				List<String> test = item_meta.getLore();
		    	Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "OUTPUT: " + test.get(2));
		    	switch(test.get(2)){
		    		case "Spawner type: BAT":
		    			cs.setSpawnedType(EntityType.BAT);
		    			break;
		    		case "Spawner type: BLAZE":
		    			cs.setSpawnedType(EntityType.BLAZE);
		    			break;
		    		case "Spawner type: CAVE_SPIDER":
		    			cs.setSpawnedType(EntityType.CAVE_SPIDER);
		    			break;
		    		case "Spawner type: CHICKEN":
		    			cs.setSpawnedType(EntityType.CHICKEN);
		    			break;
		    		case "Spawner type: COW":
		    			cs.setSpawnedType(EntityType.COW);
		    			break;
		    		case "Spawner type: CREEPER":
		    			cs.setSpawnedType(EntityType.CREEPER);
		    			break;
		    		case "Spawner type: DONKEY":
		    			cs.setSpawnedType(EntityType.DONKEY);
		    			break;
		    		case "Spawner type: ENDERMAN":
		    			cs.setSpawnedType(EntityType.ENDERMAN);
		    			break;
		    		case "Spawner type: ENDERMITE":
		    			cs.setSpawnedType(EntityType.ENDERMITE);
		    			break;
		    		case "Spawner type: EVOKER":
		    			cs.setSpawnedType(EntityType.EVOKER);
		    			break;
		    		case "Spawner type: GHAST":
		    			cs.setSpawnedType(EntityType.GHAST);
		    			break;
		    		case "Spawner type: GUARDIAN":
		    			cs.setSpawnedType(EntityType.GUARDIAN);
		    			break;
		    		case "Spawner type: HORSE":
		    			cs.setSpawnedType(EntityType.HORSE);
		    			break;
		    		case "Spawner type: HUSK":
		    			cs.setSpawnedType(EntityType.HUSK);
		    			break;
		    		case "Spawner type: IRON_GOLEM":
		    			cs.setSpawnedType(EntityType.IRON_GOLEM);
		    			break;
		    		case "Spawner type: LLAMA":
		    			cs.setSpawnedType(EntityType.LLAMA);
		    			break;
		    		case "Spawner type: MAGMA_CUBE":
		    			cs.setSpawnedType(EntityType.MAGMA_CUBE);
		    			break;
		    		case "Spawner type: MUSHROOM_COW":
		    			cs.setSpawnedType(EntityType.MUSHROOM_COW);
		    			break;
		    		case "Spawner type: MULE":
		    			cs.setSpawnedType(EntityType.MULE);
		    			break;
		    		case "Spawner type: OCELOT":
		    			cs.setSpawnedType(EntityType.OCELOT);
		    			break;
		    		case "Spawner type: PARROT":
		    			cs.setSpawnedType(EntityType.PARROT);
		    			break;
		    		case "Spawner type: PIG":
		    			cs.setSpawnedType(EntityType.PIG);
		    			break;
		    		case "Spawner type: POLAR_BEAR":
		    			cs.setSpawnedType(EntityType.POLAR_BEAR);
		    			break;
		    		case "Spawner type: RABBIT":
		    			cs.setSpawnedType(EntityType.RABBIT);
		    			break;
		    		case "Spawner type: SHEEP":
		    			cs.setSpawnedType(EntityType.SHEEP);
		    			break;
		    		case "Spawner type: SHULKER":
		    			cs.setSpawnedType(EntityType.SHULKER);
		    			break;
		    		case "Spawner type: SILVERFISH":
		    			cs.setSpawnedType(EntityType.SILVERFISH);
		    			break;
		    		case "Spawner type: SKELETON":
		    			cs.setSpawnedType(EntityType.SKELETON);
		    			break;
		    		case "Spawner type: SKELETON_HORSE":
		    			cs.setSpawnedType(EntityType.SKELETON_HORSE);
		    			break;
		    		case "Spawner type: SLIME":
		    			cs.setSpawnedType(EntityType.SLIME);
		    			break;
		    		case "Spawner type: SNOWMAN":
		    			cs.setSpawnedType(EntityType.SNOWMAN);
		    			break;
		    		case "Spawner type: SPIDER":
		    			cs.setSpawnedType(EntityType.SPIDER);
		    			break;
		    		case "Spawner type: SQUID":
		    			cs.setSpawnedType(EntityType.SQUID);
		    			break;
		    		case "Spawner type: STRAY":
		    			cs.setSpawnedType(EntityType.STRAY);
		    			break;
		    		case "Spawner type: VEX":
		    			cs.setSpawnedType(EntityType.VEX);
		    			break;
		    		case "Spawner type: VILLAGER":
		    			cs.setSpawnedType(EntityType.VILLAGER);
		    			break;
		    		case "Spawner type: VINDICATOR":
		    			cs.setSpawnedType(EntityType.VINDICATOR);
		    			break;
		    		case "Spawner type: WITCH":
		    			cs.setSpawnedType(EntityType.WITCH);
		    			break;
		    		case "Spawner type: WITHER_SKELETON":
		    			cs.setSpawnedType(EntityType.WITHER_SKELETON);
		    			break;
		    		case "Spawner type: WOLF":
		    			cs.setSpawnedType(EntityType.WOLF);
		    			break;
		    		case "Spawner type: ZOMBIE":
		    			cs.setSpawnedType(EntityType.ZOMBIE);
		    			break;
		    		case "Spawner type: PIG_ZOMBIE":
		    			cs.setSpawnedType(EntityType.PIG_ZOMBIE);
		    			break;
		    		case "Spawner type: ZOMBIE_VILLAGER":
		    			cs.setSpawnedType(EntityType.ZOMBIE_VILLAGER);
		    			break;
		    		default:
		    			cs.setSpawnedType(EntityType.PIG);
		    			break;
		    	}
				cs.update();	
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler (priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event)
    {
		if(event.getEntity() != null){
			if (event.getEntityType() == EntityType.PLAYER){
				Player player = event.getEntity();
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OURSTORY]: " + player.getName() + " died !");
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OURSTORY]: World: " + player.getWorld().getName());
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OURSTORY]: X: " + player.getLocation().getBlockX());
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OURSTORY]: Y: " + player.getLocation().getBlockY());
		        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[OURSTORY]: Z: " + player.getLocation().getBlockZ());
		        
		        player.sendMessage(ChatColor.GREEN + "Your death location:");
		        player.sendMessage(ChatColor.GREEN + "World: " + player.getWorld().getName());
		        player.sendMessage(ChatColor.GREEN + "X: " + player.getLocation().getBlockX());
		        player.sendMessage(ChatColor.GREEN + "Y: " + player.getLocation().getBlockY());
		        player.sendMessage(ChatColor.GREEN + "Z: " + player.getLocation().getBlockZ());
		        
		        
		        ItemStack item = player.getPlayer().getItemInHand();
				ItemMeta item_meta = item.getItemMeta();
				//TODO ADD NULL VERIFICATION
		        //Check if the players is using the Anniversary item
		        if(item_meta.hasLore() && item_meta.hasEnchant(Enchantment.SILK_TOUCH) && item_meta.isUnbreakable() && item_meta.getDisplayName().contains("§7Phoenix X")){
		        	player.sendMessage(ChatColor.GREEN + "CLOCK DETECTED !!!!");
		        	
		        	event.setKeepInventory(true);
		        	event.setKeepLevel(true);
		        	//event.getDrops().clear();
					//TO CHECK LATER FOR DUPLICATION
		        } else {
		        	player.sendMessage(ChatColor.GREEN + "ERROR NOT DETECTED");
		        }
		        
		        for (Player OnlinePlayer : Bukkit.getOnlinePlayers())
		        {
		        	OnlinePlayer.playSound(OnlinePlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1000, 1);
		        }
			} else {
				return;
			}
		} else {
			return;
		}
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
    public void onEntityHit(final EntityDamageByEntityEvent entity) {
        if ((entity.getDamager() instanceof Snowball) && (entity.getEntity() instanceof Player)) {
        	entity.setDamage(1);
        	entity.getEntity().getWorld().playEffect(entity.getEntity().getLocation(), Effect.STEP_SOUND, 80, 1);
        }
        if ((entity.getDamager() instanceof Egg) && (entity.getEntity() instanceof Player)) {
        	entity.setDamage(3);
        	entity.getEntity().getWorld().playEffect(entity.getEntity().getLocation(), Effect.STEP_SOUND, 80, 1);
        }
    }
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
	    if(event.getCause() == TeleportCause.ENDER_PEARL){
	    	event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
	    }
	    
	    if(event.getCause() == TeleportCause.CHORUS_FRUIT){
	    	event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 700, 1));
	    	event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1));
	    }
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityPortal(EntityPortalEvent event) {
		if(!(event.getEntity() instanceof Ghast) && !(event.getEntity() instanceof Cow) && !(event.getEntity() instanceof Player)){
            event.setCancelled(true);
            event.getEntity().setFireTicks(20);
        }
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onXpPickup(PlayerExpChangeEvent experience) {
		
		Player player = experience.getPlayer();
		
		//TODO ADD THE ANIVERSARY ITEM HERE
		
        if(player.hasPotionEffect(PotionEffectType.LUCK))
        {
            Collection<PotionEffect> player_effects = player.getActivePotionEffects();
            for(PotionEffect effect : player_effects)
            {
                if(effect.getType().equals(PotionEffectType.LUCK))
                {
                	int multiplier = 1;
                	
                	switch(effect.getAmplifier()){
                		case 0:
                			multiplier = 2;
                			break;
                		case 1:
                			multiplier = 3;
                			break;
                		case 2:
                			multiplier = 4;
                			break;
                		case 3:
                			multiplier = 5;
                			break;
                		default:
                			multiplier = 1;
                	}
                	
                	experience.setAmount(experience.getAmount() * multiplier);
                }
            }
        }
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("changelog")) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.BLUE + "-Added world_heaven dimmention");
				sender.sendMessage(ChatColor.GREEN + "-Optimized plugin");
				sender.sendMessage(ChatColor.GREEN + "-Fixed death message");
				sender.sendMessage(ChatColor.GREEN + "-Added /statistics beta command");
				sender.sendMessage(ChatColor.GREEN + "-Added beta test potion in brewing stand");
				sender.sendMessage(ChatColor.YELLOW + "-Considering the carpet duplicator as a temporary feature");
				sender.sendMessage(ChatColor.RED + "-Merged some advancements (you will have to achieve them again)");
				sender.sendMessage(ChatColor.RED + "-Removed Block logger plugin");
			}
			else
			{
				sender.sendMessage("Only players can run this command!");
			}
		}
		
		if ((cmd.getName().equalsIgnoreCase("c")) || (cmd.getName().equalsIgnoreCase("craft"))) {
			if (sender instanceof Player) {
				if (sender.hasPermission("ourstory.craft")) {
					final Player player = (Player)sender;
					player.openWorkbench(player.getLocation(), true);
				}
				else
				{
					sender.sendMessage(this.getConfig().getString("messages.nopermission").replace("&", "§"));
				}
			}
			else
			{
				sender.sendMessage("Only players can run this command!");
			}
		}
		
		if ((cmd.getName().equalsIgnoreCase("ec")) || (cmd.getName().equalsIgnoreCase("enderchest"))) {
			if (sender instanceof Player) {
				if (sender.hasPermission("ourstory.enderchest")) {
					final Player player = (Player)sender;
					player.openInventory(player.getEnderChest());
				}
				else
				{
					sender.sendMessage(this.getConfig().getString("messages.nopermission").replace("&", "§"));
				}
			}
			else
			{
				sender.sendMessage("Only players can run this command!");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("statistics")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("ourstory.stats")) {
					final Player player = (Player)sender;
					player.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "OURSTORY " + ChatColor.RED + "This command is still under developement but it will be soon released");
				}
				else
				{
					sender.sendMessage(this.getConfig().getString("messages.nopermission").replace("&", "§"));
				}
			}
			else
			{
				sender.sendMessage("Only players can run this command!");
			}
		}

		//if ((cmd.getName().equalsIgnoreCase("a")) || (cmd.getName().equalsIgnoreCase("anvil"))) {
			//if (sender instanceof Player) {
				//if (sender.hasPermission("ourstory.anvil")) {
					//final Player player = (Player)sender;
					//if ((args.length > 0) && (args[0].equalsIgnoreCase("confirm"))) {
						//Inventory anvil = Bukkit.createInventory(player, InventoryType.ANVIL);
						//player.openInventory(anvil);
					//} else {
						//player.sendMessage(ChatColor.DARK_RED + "[WARNING] " + ChatColor.RED + "This command is still unstable for now and needs to be reviewed !");
						//player.sendMessage(ChatColor.DARK_RED + "[WARNING] " + ChatColor.RED + "Note that if you loose any items by using this command, it will NOT be given back.");
						//player.sendMessage(ChatColor.DARK_RED + "[WARNING] " + ChatColor.RED + "You still have this snapshot access by tapping the following command:");
						//player.sendMessage(ChatColor.DARK_RED + "[WARNING] " + ChatColor.RED + "/a confirm " + ChatColor.DARK_RED + "or " + ChatColor.RED + "/anvil confirm ");
					//}	
				//}
				//else
				//{
					//sender.sendMessage(this.getConfig().getString("messages.nopermission").replace("&", "§"));
				//}
			//}
			//else
			//{
				//sender.sendMessage("Only players can run this command!");
			//}
		//}
		return true;
    }
}
