package dev.xkmc.l2foundation.content.questline.mobs.swamp;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2foundation.network.ConfigType;
import dev.xkmc.l2foundation.network.NetworkManager;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.HashMap;

@SerialClass
public class SlimeProperties extends BaseConfig {

	@SerialClass
	public static class SlimeConfig {

		@SerialClass.SerialField
		public MobEffect effect = MobEffects.POISON;
		@SerialClass.SerialField
		public int weight = 0;
		@SerialClass.SerialField
		public Item drop = Items.SPIDER_EYE;
		@SerialClass.SerialField
		public double chance = 0.2;
		@SerialClass.SerialField
		public int duration = 100;
		@SerialClass.SerialField
		public int amplifier = 0;

		public String id = "";
		public MobEffectInstance ins = new MobEffectInstance(effect, duration, amplifier);

		@DataGenOnly
		public SlimeConfig setEffect(MobEffect eff) {
			this.effect = eff;
			return this;
		}

		@DataGenOnly
		public SlimeConfig setWeight(int weight) {
			this.weight = weight;
			return this;
		}

		@DataGenOnly
		public SlimeConfig setDrop(Item drop) {
			this.drop = drop;
			return this;
		}

		@DataGenOnly
		public SlimeConfig setChance(double chance) {
			this.chance = chance;
			return this;
		}

		@DataGenOnly
		public SlimeConfig setDuration(int duration) {
			this.duration = duration;
			return this;
		}

		@DataGenOnly
		public SlimeConfig setAmplifier(int amplifier) {
			this.amplifier = amplifier;
			return this;
		}


	}

	public static final SlimeConfig DEF = new SlimeConfig();

	@Nullable
	public static SlimeProperties getInstance() {
		return NetworkManager.getConfig(ConfigType.POTION_SLIME_DROP);
	}

	@ConfigCollect(CollectType.MAP_OVERWRITE)
	@SerialClass.SerialField
	public HashMap<String, SlimeConfig> map = new HashMap<>();

	@SerialClass.OnInject
	public void onInject() {
		map.forEach((v, k) -> {
			k.id = v;
			k.ins = new MobEffectInstance(k.effect, k.duration, k.amplifier);
		});
	}

	@DataGenOnly
	public SlimeProperties add(String id, SlimeConfig config) {
		map.put(id, config);
		return this;
	}

	public static String getRandomConfig(RandomSource random) {
		SlimeProperties ins = getInstance();
		if (ins == null) return "";
		return WeightedRandomList.create(ins.map.values().stream().map(e -> WeightedEntry.wrap(e.id, e.weight)).toList())
				.getRandom(random).map(WeightedEntry.Wrapper::getData).orElse("");
	}
}
