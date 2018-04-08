package CollisionDetection;

import GameComponent.Collider;
import GameTools.Vector2;

import java.util.ArrayList;
import java.util.List;

public interface Broadphase {

    // adds a new AABB to the broadphase
//	public void Add(AABB aabb);
    public void Add(AABB aabb);

    // updates broadphase to react to changes to AABB
    public void Update();

    // returns a list of possibly colliding colliders
    public List<Pair<Collider, Collider>> ComputePairs();

    // returns a collider that collides with a point
    // returns null if no such collider exists
    public void Pick(final Vector2 point, ArrayList<Collider> result);

    // returns a list of colliders whose AABBs collide
    // with a query AABB
    public void Query(final AABB aabb, ArrayList<Collider> output);

    public boolean RayCast(Ray ray, RayHitInfo hit, float maxDistance);
}