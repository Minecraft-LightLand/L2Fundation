package dev.xkmc.curseofpandora.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.curseofpandora.init.registrate.CoPFakeEffects;
import dev.xkmc.curseofpandora.init.registrate.CoPItems;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class CurseOfPandora {

	public static final String MODID = L2Complements.MODID;

	public static final L2Registrate REGISTRATE = L2Complements.REGISTRATE;

	public static final RegistryEntry<Attribute> SPELL = reg("spell_tolerance", 1, 10000, "Spell Tolerance");
	public static final RegistryEntry<Attribute> REALITY = reg("reality_index", 0, 10000, "Reality Index");

	public static void register() {
		CoPItems.register();
		CoPFakeEffects.register();
	}

	public static void modifyAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, SPELL.get());
		event.add(EntityType.PLAYER, REALITY.get());
	}

	private static RegistryEntry<Attribute> reg(String id, double def, double max, String name) {
		REGISTRATE.addRawLang("attribute.name." + id, name);
		return REGISTRATE.simple(id, ForgeRegistries.ATTRIBUTES.getRegistryKey(),
				() -> new RangedAttribute("attribute.name." + id, def, 0, max)
						.setSyncable(true));
	}

}
