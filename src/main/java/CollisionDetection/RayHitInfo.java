package CollisionDetection;

import GameComponent.Collider;
import GameTools.Vector2;

/**
 * ���߷�����ײ֮�󷵻ص���ײ��Ϣ
 */
public class RayHitInfo {

    /**
     * ��ײ��
     */
    private Collider collider;

    /**
     * ����
     */
    private Ray ray;

    /**
     * ��ײ���ϵ���ײ��
     */
    private Vector2 position;
    /**
     * ��ײ����
     */
    private Vector2 perp;

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPerp() {
        return perp;
    }

    public void setPerp(Vector2 perp) {
        this.perp = perp;
    }

    public RayHitInfo(Collider collider, Ray ray, Vector2 position, Vector2 perp) {
        this.collider = collider;
        this.ray = ray;
        this.position = position;
        this.perp = perp;
    }

    public RayHitInfo() {
    }

    @Override
    public final RayHitInfo clone() {
        return new RayHitInfo(collider, ray, position, perp);
    }

    public void set(RayHitInfo other){
        this.collider = other.collider;
        this.ray = other.ray;
        this.position = other.position;
        this.perp = other.perp;
    }
}
