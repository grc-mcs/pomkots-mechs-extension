package grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer;

import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;

public class MSRenderUtils {
    public static Vec3 getSeatPosition(BakedGeoModel model, PmgBaseEntity entity) {
        return getSeatPosition(model, entity, 18F);
    }

    public static Vec3 getSeatPosition(BakedGeoModel model, PmgBaseEntity entity, float modelHeight) {
        if (model != null) {
            GeoBone vcSeat = model.getBone("seat").get();
            GeoBone vcRoot = model.getBone("root").get();

            Vector3d seatPos = vcSeat.getLocalPosition();
            Vector3d rootPos = vcRoot.getLocalPosition();

            return new Vec3(
                    seatPos.x * entity.DEFAULT_SCALE - rootPos.x * entity.DEFAULT_SCALE,
                    seatPos.y * entity.DEFAULT_SCALE - rootPos.y * entity.DEFAULT_SCALE - modelHeight,
                    seatPos.z * entity.DEFAULT_SCALE - rootPos.z * entity.DEFAULT_SCALE);
        } else {
            return Vec3.ZERO;
        }
    }

    public static Vec3 getMainCameraPosition(BakedGeoModel model, PmgBaseEntity entity, float partialTick) {
        return getMainCameraPosition(model, entity, partialTick, entity.DEFAULT_SCALE);
    }

    public static Vec3 getMainCameraPosition(BakedGeoModel model, PmgBaseEntity entity, float partialTick, float scale) {
        if (model != null) {
            GeoBone vcSeat = model.getBone("maincam").get();
            Vector3d seatPos = vcSeat.getLocalPosition();
//
//
//            return new Vec3(
//                    entity.position().x + seatPos.x,
//                    entity.position().y + seatPos.y * entity.DEFAULT_SCALE,
//                    entity.position().z + seatPos.z
//            );

            return new Vec3(
                    Mth.lerp(partialTick, entity.xo, entity.position().x) + seatPos.x,
                    Mth.lerp(partialTick, entity.yo, entity.position().y) + seatPos.y * scale,
                    Mth.lerp(partialTick, entity.zo, entity.position().z) + seatPos.z
            );

//            Vector3d v = vcSeat.getWorldPosition();
//            return new Vec3(v.x, v.y, v.z);
        } else {
            return Vec3.ZERO;
        }
    }
}
