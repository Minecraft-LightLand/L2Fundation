package dev.xkmc.l2complements.content.enchantment.legacy;

import dev.xkmc.l2complements.init.data.LCConfig;
import dev.xkmc.l2complements.init.registrate.LCEnchantments;
import dev.xkmc.l2core.init.reg.ench.CustomDescEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchColor;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2damagetracker.contents.attack.DamageModifier;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;

public class VoidTouchEnchantment extends LegacyEnchantment implements CustomDescEnchantment {

	private static final ResourceLocation VOID_ATTACK = LCEnchantments.VOID_TOUCH.id().location().withSuffix("_attack");
	private static final ResourceLocation VOID_DAMAGE = LCEnchantments.VOID_TOUCH.id().location().withSuffix("_damage");

	private static double getChance(DamageData data, int level) {
		if (data.getStrength() < 0.95f) return 0;
		double chance = LCConfig.SERVER.voidTouchChance.get() * level;
		DamageSource source = data.getSource();
		if (source.is(DamageTypeTags.BYPASSES_ARMOR))
			chance += LCConfig.SERVER.voidTouchChanceBonus.get();
		if (source.is(DamageTypeTags.BYPASSES_EFFECTS) && source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
			chance += LCConfig.SERVER.voidTouchChanceBonus.get();
		return chance;
	}

	private static boolean allow(DamageData cache, ItemStack weapon) {
		int level = LCEnchantments.VOID_TOUCH.getLv(weapon);
		if (level <= 0) return false;
		double chance = getChance(cache, level);
		double rr = new Random(new Random(cache.getTarget().tickCount).nextLong()).nextDouble();
		if (rr > chance) return false;
		return true;
	}

	public static void postAttack(DamageData.Attack data, ItemStack weapon) {
		if (!allow(data, weapon)) return;
		data.setNonCancellable();
	}

	public static void initAttack(DamageData.Offence data, ItemStack weapon) {
		if (!allow(data, weapon)) return;
		if (data.getAttacker() == null) return;
		if (data.getAttacker().getAttribute(Attributes.ATTACK_DAMAGE) == null) return;
		float maxDmg = data.getDamageOriginal();
		if (data.getSource().is(L2DamageTypes.DIRECT)) {
			double damage = data.getAttacker().getAttributeValue(Attributes.ATTACK_DAMAGE);
			var pl = data.getPlayerData();
			var crit = pl == null ? null : pl.getCriticalHitEvent();
			if (crit != null) {
				damage *= crit.getDamageMultiplier();
			}
			maxDmg = (float) Math.max(damage, maxDmg);
		}
		float finDmg = maxDmg;
		data.addHurtModifier(DamageModifier.nonlinearPre(0, e -> Math.max(e, finDmg), VOID_ATTACK));
	}

	public static void initDamage(DamageData.Defence cache, ItemStack weapon) {
		if (!allow(cache, weapon)) return;
		float finalDamage = Math.max(cache.getDamageOriginal(), cache.getDamageIncoming());
		cache.addDealtModifier(DamageModifier.nonlinearPre(5000, e -> Math.max(e, finalDamage), VOID_DAMAGE));
	}

	@Override
	public List<Component> descFull(int lv, String key, boolean alt, boolean book, EnchColor color) {
		return List.of(Component.translatable(key,
				CustomDescEnchantment.perc(lv * LCConfig.SERVER.voidTouchChance.get()),
				CustomDescEnchantment.perc(lv * LCConfig.SERVER.voidTouchChanceBonus.get())
		).withStyle(color.desc()));
	}

}
