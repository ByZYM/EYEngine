package CollisionDetection;

import GameComponent.Collider;

public class Physics {

    public static final AABBTree aabbTree= Collider.bp;

    public static final boolean RayCast(Ray ray, RayHitInfo hitInfoCallBack, float maxDistance) {
        return aabbTree.RayCast(ray, hitInfoCallBack, maxDistance);
    }
}
