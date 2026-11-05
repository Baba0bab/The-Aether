package com.aetherteam.nitrogen.fabric.mixin;

import com.aetherteam.nitrogen.fabric.level.PieceBeardifierModifier;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Beardifier.class)
public abstract class BeardifierMixin {
    @Definition(id = "structurePiece", local = @Local(type = StructurePiece.class))
    @Definition(id = "PoolElementStructurePiece", type = PoolElementStructurePiece.class)
    @Expression("structurePiece instanceof PoolElementStructurePiece")
    @WrapOperation(method = "forStructuresInChunk", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean nitrogen_fabric$checkCustomBeardifier(Object object, Operation<Boolean> original) {
        return !(object instanceof PieceBeardifierModifier) && original.call(object);
    }

    // In 26.x the piece loop is inlined in forStructuresInChunk; the third List#add is the one for pieces that aren't pool element pieces.
    @WrapOperation(method = "forStructuresInChunk", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private static boolean nitrogen_fabric$useCustomBeardifier(List instance, Object object, Operation<Boolean> original, @Local() StructurePiece structurePiece) {
        if (structurePiece instanceof PieceBeardifierModifier modifier) {
            if (modifier.getTerrainAdjustment() == TerrainAdjustment.NONE) return false;

            object = new Beardifier.Rigid(modifier.getBeardifierBox(), modifier.getTerrainAdjustment(), modifier.getGroundLevelDelta());
        }

        return original.call(instance, object);
    }
}
