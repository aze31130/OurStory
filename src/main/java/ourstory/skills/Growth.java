package ourstory.skills;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author aurel
 */
public class Growth implements Skills {
	static NamespacedKey GrowthKey = new NamespacedKey(Bukkit.getPluginManager().getPlugin("OurStory"), "boss_scale_skill");
	/*
	 * Value need to be POSITIVE. 0 to 1 makes the entity shrink. 1 to ininite makes it grow
	 */
	double specificScale = 3;
	double growthPreparationTime = 60; // 3 sec
	double growthDuration = 400; // 20 sec
	double growthResetTime = 60; // 3 sec

	@Override
	public void cast(Entity caster, List<Entity> targets) {
		new BukkitRunnable() {
			int timer = 0;

			@Override
			public void run() {
				timer++;
				if (timer < growthPreparationTime) {
					double percent = (double) timer / growthPreparationTime;
					applyEffect((LivingEntity) caster, specificScale * percent + (1 - percent));
				} else if (timer < growthPreparationTime + growthDuration) {
					applyEffect((LivingEntity) caster, specificScale);
				} else if (timer < growthPreparationTime + growthDuration + growthResetTime) {
					double percent = ((double) timer - growthPreparationTime - growthDuration) / growthResetTime;
					applyEffect((LivingEntity) caster, specificScale * (1. - percent) + percent);
				} else {
					removeEffect((LivingEntity) caster);
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	public static void removeEffect(LivingEntity entity) {
		if (entity.getAttribute(Attribute.SCALE) != null)
			entity.getAttribute(Attribute.SCALE).removeModifier(GrowthKey);
	}

	public static void applyEffect(LivingEntity entity, double strength) {
		removeEffect(entity);

		AttributeModifier buff = new AttributeModifier(GrowthKey, strength - 1, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
		entity.getAttribute(Attribute.SCALE).addModifier(buff);
	}
}
