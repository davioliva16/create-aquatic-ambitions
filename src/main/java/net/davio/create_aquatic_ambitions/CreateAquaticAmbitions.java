package net.davio.create_aquatic_ambitions;

import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.davio.create_aquatic_ambitions.entry.CCABlocks;
import net.davio.create_aquatic_ambitions.entry.CCACreativeModeTab;
import net.davio.create_aquatic_ambitions.entry.CCAItems;
import net.davio.create_aquatic_ambitions.entry.CCAPartials;
import net.davio.create_aquatic_ambitions.entry.CCARecipeTypes;
import net.davio.create_aquatic_ambitions.entry.CCATags;
import net.davio.create_aquatic_ambitions.kinetics.fan.processing.CCAFanProcessingTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAquaticAmbitions implements ModInitializer {
	public static final String MODID = "create_aquatic_ambitions";
	public static final String NAME = "Create: Aquatic Ambitions";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

	@Override
	public void onInitialize() {

		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);

		CCATags.setRegister();
		CCABlocks.register();
		CCAItems.register();
		CCARecipeTypes.register();
		CCAFanProcessingTypes.register();
		CCACreativeModeTab.registerItemGroups();
		//CCAPartials.init();
		REGISTRATE.register();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(MODID,path);
	}
	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}


}
