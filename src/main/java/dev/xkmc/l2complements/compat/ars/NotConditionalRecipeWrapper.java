package dev.xkmc.l2complements.compat.ars;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public record NotConditionalRecipeWrapper(FinishedRecipe base, String[] modid) implements FinishedRecipe {
	public NotConditionalRecipeWrapper(FinishedRecipe base, String[] modid) {
		this.base = base;
		this.modid = modid;
	}

	public static Consumer<FinishedRecipe> mod(Consumer<FinishedRecipe> pvd, String... modid) {
		return (r) -> {
			pvd.accept(new NotConditionalRecipeWrapper(r, modid));
		};
	}

	public void serializeRecipeData(JsonObject pJson) {
		this.base.serializeRecipeData(pJson);
	}

	public ResourceLocation getId() {
		return this.base.getId();
	}

	public RecipeSerializer<?> getType() {
		return this.base.getType();
	}

	public @Nullable JsonObject serializeAdvancement() {
		JsonObject ans = this.base.serializeAdvancement();
		if (ans == null) {
			return null;
		} else {
			this.addCondition(ans);
			return ans;
		}
	}

	public @Nullable ResourceLocation getAdvancementId() {
		return this.base.getAdvancementId();
	}

	public JsonObject serializeRecipe() {
		JsonObject ans = this.base.serializeRecipe();
		this.addCondition(ans);
		return ans;
	}

	private void addCondition(JsonObject ans) {
		JsonArray conditions = new JsonArray();
		for (String str : this.modid) {
			JsonObject condition = new JsonObject();
			condition.addProperty("type", "forge:mod_loaded");
			condition.addProperty("modid", str);
			JsonObject not = new JsonObject();
			not.addProperty("type", "forge:not");
			not.add("value", condition);
			conditions.add(not);
		}
		ans.add("conditions", conditions);
	}

	public FinishedRecipe base() {
		return this.base;
	}

	public String[] modid() {
		return this.modid;
	}
}
