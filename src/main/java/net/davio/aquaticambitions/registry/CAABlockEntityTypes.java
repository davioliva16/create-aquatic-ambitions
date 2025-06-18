package net.davio.aquaticambitions.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitBlockEntity;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitRenderer;
import net.davio.aquaticambitions.content.processing.conduit.MechanicalConduitVisual;
import net.davio.aquaticambitions.ponder.util.fakeConduitEntity.FakeActiveConduitBlockEntity;
import net.davio.aquaticambitions.ponder.util.fakeConduitEntity.FakeConduitRenderer;

import static net.davio.aquaticambitions.CreateAquaticAmbitions.REGISTRATE;

public class CAABlockEntityTypes {

    public static final BlockEntityEntry<MechanicalConduitBlockEntity> MECHANICAL_CONDUIT = REGISTRATE
            .blockEntity("mechanical_conduit",MechanicalConduitBlockEntity::new)
            .visual(() -> MechanicalConduitVisual::new, false)
            .validBlocks(CAABlocks.MECHANICAL_CONDUIT)
            .renderer(() -> MechanicalConduitRenderer::new)
            .register();

    public static final BlockEntityEntry<FakeActiveConduitBlockEntity> FAKE_CONDUIT = REGISTRATE
            .blockEntity("fake_conduit", FakeActiveConduitBlockEntity::new)
            .validBlocks(CAABlocks.FAKE_CONDUIT)
            .renderer(() -> FakeConduitRenderer::new)
            .register();

    public static void register() {
    }
}
