package dev.xkmc.l2complements.compat;

import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2complements.init.registrate.LCBlocks;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2complements.init.registrate.LCRecipes;
import dev.xkmc.l2library.util.Proxy;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

@JeiPlugin
public class LCJeiPlugin implements IModPlugin {

	public static LCJeiPlugin INSTANCE;

	public final ResourceLocation UID = new ResourceLocation(L2Complements.MODID, "main");

	public final BurntRecipeCategory BURNT = new BurntRecipeCategory();
	public final DiffuseRecipeCategory DIFFUSE = new DiffuseRecipeCategory();

	public IGuiHelper GUI_HELPER;

	public LCJeiPlugin() {
		INSTANCE = this;
	}

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(Items.ENCHANTED_BOOK, LCEmiPlugin::partSubType);
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registration) {
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(BURNT.init(helper));
		registration.addRecipeCategories(DIFFUSE.init(helper));
		GUI_HELPER = helper;
	}

	@Override
	public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		var level = Proxy.getClientWorld();
		assert level != null;
		registration.addRecipes(BURNT.getRecipeType(), level.getRecipeManager().getAllRecipesFor(LCRecipes.RT_BURNT.get()));
		registration.addRecipes(DIFFUSE.getRecipeType(), level.getRecipeManager().getAllRecipesFor(LCRecipes.RT_DIFFUSION.get()));
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(Items.LAVA_BUCKET.getDefaultInstance(), BURNT.getRecipeType());
		registration.addRecipeCatalyst(Items.FLINT_AND_STEEL.getDefaultInstance(), BURNT.getRecipeType());
		registration.addRecipeCatalyst(Items.FIRE_CHARGE.getDefaultInstance(), BURNT.getRecipeType());
		registration.addRecipeCatalyst(LCBlocks.ETERNAL_ANVIL.asStack(), RecipeTypes.ANVIL);
		registration.addRecipeCatalyst(LCItems.DIFFUSION_WAND.asStack(), DIFFUSE.getRecipeType());
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
	}

	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
