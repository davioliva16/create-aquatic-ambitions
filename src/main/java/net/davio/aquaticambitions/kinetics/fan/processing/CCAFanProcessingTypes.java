package net.davio.aquaticambitions.kinetics.fan.processing;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingTypeRegistry;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.simibubi.create.foundation.utility.Color;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.davio.aquaticambitions.CreateAquaticAmbitions;
import net.davio.aquaticambitions.entry.CCARecipeTypes;
import net.davio.aquaticambitions.entry.CCATags;
import net.davio.aquaticambitions.kinetics.fan.processing.ChannelingRecipe.ChannelingWrapper;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;


import java.util.List;
import java.util.Optional;


public class CCAFanProcessingTypes {

    public static final ChannelingType CHANNELING = register("channeling", new ChannelingType());

    static {
        Object2ReferenceOpenHashMap<String, FanProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("CHANNELING", CHANNELING);
        map.trim();
    }

    private static <T extends FanProcessingType> T register(String id, T type) {
        FanProcessingTypeRegistry.register(CreateAquaticAmbitions.asResource(id), type);
        return type;
    }

    public static class ChannelingType implements FanProcessingType {
        private static final ChannelingWrapper CHANNELING_WRAPPER = new ChannelingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            //Check if any blockEntity in 3x3 box is an active conduit.
            //This (and priority = 1200) allows us to prevent washing process to override channeling in the 3x3 conduit box
            for(int i = -1; i <= 1; ++i) {
                for(int j = -1; j <= 1; ++j) {
                    for(int k = -1; k <= 1; ++k) {
                        BlockPos adjacentPos = pos.offset(i, j, k);
                        //Check for Active Conduit
                        BlockEntity blockEntity = level.getBlockEntity(adjacentPos);
                        if (blockEntity instanceof ConduitBlockEntity){
                           if(((ConduitBlockEntity) blockEntity).isActive()){
                               return true;
                           }
                        }
                    }
                }
            }
            //Check for matching tags.
            //TODO Add config or Datapack to add tag conduit block - conduit will channel even if inactive
            FluidState fluidState = level.getFluidState(pos);
            BlockState blockState = level.getBlockState(pos);
            return (blockState.is(CCATags.FAN_CHANNELING_PROCESSING_TAG) && fluidState.is(CCATags.FAN_CHANNELING_PROCESSING_FLUID_TAG));
        }

        @Override
        public int getPriority() {
            return 1200;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            CHANNELING_WRAPPER.setItem(0, stack);
            Optional<ChannelingRecipe> recipe = CCARecipeTypes.CHANNELING.find(CHANNELING_WRAPPER,level);
            return recipe.isPresent();
        }

        @Override
        public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
            CHANNELING_WRAPPER.setItem(0,stack);
            Optional<ChannelingRecipe> recipe = CCARecipeTypes.CHANNELING.find(CHANNELING_WRAPPER,level);
            return recipe.map(channelingRecipe -> RecipeApplier.applyRecipeOn(level, stack, channelingRecipe)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0x0055FF).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            if (level.random.nextInt(8) < 2) {
                level.addParticle(ParticleTypes.NAUTILUS, pos.x + (level.random.nextFloat() - .5f) * .5f,
                        pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f));
            } else if (level.random.nextInt(8) < 4) {
                level.addParticle(ParticleTypes.ENCHANT, pos.x + (level.random.nextFloat() - .5f) * .5f,
                        pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f), (level.random.nextFloat() - .5f));
            }
        }
        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0x1F96B1, 0x0055FF, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 256f)
                particleAccess.spawnExtraParticle(ParticleTypes.ENCHANT, .125f);
            if (random.nextFloat() < 1 / 1024f)
                particleAccess.spawnExtraParticle(ParticleTypes.NAUTILUS, .075f);
            if (random.nextFloat() < 1 / 64f)
                particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE, .125f);
            if (random.nextFloat() < 1 / 64f)
                particleAccess.spawnExtraParticle(ParticleTypes.BUBBLE_POP, .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide){
                return;
            }

            if (entity instanceof Player){
                ((Player) entity).addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 260, 0, true, true));
            }
        }
    }
    public static void register() {}
}
