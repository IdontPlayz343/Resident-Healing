package pride_seebot.resident_healing.client.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import pride_seebot.resident_healing.component.ModDataComponentTypes;

import java.util.List;


public record HerbTintSource(int layerIndex) implements ItemTintSource {
    public static final MapCodec<HerbTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("layer_index").forGetter(HerbTintSource::layerIndex)
            ).apply(instance, HerbTintSource::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        List<String> contents = stack.get(ModDataComponentTypes.HERB_CONTENTS.get());

        if (contents == null || layerIndex >= contents.size()) {
            return 0x00FFFFFF;
        }

        String herbType = contents.get(layerIndex);
        return switch (herbType) {
            case "green" -> 0xFF55FF55;
            case "red" -> 0xFFFF5555;
            case "blue" -> 0xFF5555FF;
            default -> 0x00FFFFFF;
        };
    }

    @Override
    public MapCodec<HerbTintSource> type(){return MAP_CODEC;}
}