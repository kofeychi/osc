package dev.kofeychi.osc;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FishingRodBehaviour {

    public static void execute(Vec3d position, ServerWorld world, ServerPlayerEntity player, Hand hand) {
        ItemStack i = player.getStackInHand(hand);
        if(i.getName().getLiteralString().contains("nuke")) {
            nuke(position,world,player);
            if(player.interactionManager.isSurvivalLike()) {
                i.damage(99999999,player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
            return;
        }
        if(i.getName().getLiteralString().contains("stab")) {
            stab(position,world,player);
            if(player.interactionManager.isSurvivalLike()) {
                i.damage(99999999,player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
            return;
        }
    }

    public static void stab(Vec3d position, ServerWorld world, ServerPlayerEntity player) {
        OscConfig.Stab im = OscConfig.HOLDER.instance().stab;
        int Y = 0;
        for(int i = 0; i < im.DEPTH; i++) {
            var point = new Vec3d(position.x,position.y-Y,position.z);
            world.createExplosion(player, Explosion.createDamageSource(world, player), null, point.x, point.y+1, point.z, 4.0F, false, World.ExplosionSourceType.TNT);
            Y += im.SPACING;
        }
    }

    public static void nuke(Vec3d position, ServerWorld world, ServerPlayerEntity player) {
        OscConfig.Nuke im = OscConfig.HOLDER.instance().nuke;
        int radius = im.RING_STEP;
        int segments = im.TNT_PER_RING;
        ArrayList<Vec3d> points = new ArrayList<>();
        for(int i = 0; i < im.RINGS; i++){

            points.addAll(Maths.circle(position,radius,segments));

            radius += im.RING_STEP;
            segments += im.TNT_PER_RING_ADDED;
        }

        var startorigin = new Vec3d(position.x,position.y+20,position.z);

        points.forEach(point -> {
            point = new Vec3d(point.x,point.y+20,point.z);
            var entity = new TntEntity(world,startorigin.x,startorigin.y,startorigin.z,player);
            entity.setFuse((int) (80+(position.distanceTo(point)/2)));
            entity.setVelocity(entity.getPos().subtract(point).multiply(Math.PI/100));
            world.spawnEntity(entity);
        });
    }

}
