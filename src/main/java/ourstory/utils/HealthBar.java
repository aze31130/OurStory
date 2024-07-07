package ourstory.utils;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class HealthBar {
	public static BarColor getColor(Entity entity) {
		if (entity.getScoreboardTags().contains("isBoss"))
			return BarColor.PURPLE;
		if (entity instanceof Monster)
			return BarColor.RED;
		if (entity instanceof Animals)
			return BarColor.GREEN;

		return BarColor.YELLOW;
	}

	public static BarStyle getStyle(Entity entity) {
		return BarStyle.SOLID;
	}

	public static String getTitle(Entity entity) {
		return entity.getName();
	}
}
