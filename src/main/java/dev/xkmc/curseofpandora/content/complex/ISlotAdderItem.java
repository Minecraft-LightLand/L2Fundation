package dev.xkmc.curseofpandora.content.complex;

import dev.xkmc.l2library.capability.conditionals.TokenKey;

import java.util.function.Supplier;

public abstract class ISlotAdderItem<T extends BaseTickingToken> extends ITokenProviderItem<T> {

	public final SlotAdder adder;

	public ISlotAdderItem(Properties properties, TokenKey<T> key, Supplier<T> sup, SlotAdder adder) {
		super(properties, key, sup);
		this.adder = adder;
	}

	public final SlotAdder getSlotAdder() {
		return adder;
	}

}
