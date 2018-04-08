package CollisionDetection;

import CollisionDetection.AABBTree.Node;
import GameComponent.Collider;
import GameTools.Vector2;

public class AABB {
    /**
     * ��Χ�����µ�
     */
    public Vector2 WorldLowerBound;
    /**
     * ��Χ�����ϵ�
     */
    public Vector2 WorldUpperBound;

    /**
     * ��Χ�����µ�
     */
    public Vector2 LocalLowerBound;
    /**
     * ��Χ�����ϵ�
     */
    public Vector2 LocalUpperBound;

    /**
     * AABB��Χ������AABBTree�Ľڵ�
     */
    public Node userData;

    /**
     * ���ص���ײ��
     */
    public Collider collider;

    /**
     * AABB��Χ�г�ʼ���췽��
     */
    public AABB() {

        this.LocalLowerBound = new Vector2();
        this.LocalUpperBound = new Vector2();
        update(new Vector2());
    }

    /**
     * ��Collider����е�WorldShape�л�ȡAABB
     */
    public AABB(Collider c) {
        collider = c;
        Shape shape = c.getLocalShape();
        this.LocalLowerBound = shape.vertices[0].clone();
        this.LocalUpperBound = shape.vertices[0].clone();
        for (Vector2 v : shape.vertices) {
            if (v.x < LocalLowerBound.x) LocalLowerBound.x = v.x;
            if (v.y < LocalLowerBound.y) LocalLowerBound.y = v.y;
            if (v.x > LocalUpperBound.x) LocalUpperBound.x = v.x;
            if (v.y > LocalUpperBound.y) LocalUpperBound.y = v.y;
        }
        update(c.transform_world.getPosition());
    }

    /**
     * �ӵ������л�ȡAABB
     */
    public AABB(Vector2[] vectors) {
        this.WorldLowerBound = vectors[0].clone();
        this.WorldUpperBound = vectors[0].clone();
        for (Vector2 v : vectors) {
            if (v.x < WorldLowerBound.x) WorldLowerBound.x = v.x;
            if (v.y < WorldLowerBound.y) WorldLowerBound.y = v.y;
            if (v.x > WorldUpperBound.x) WorldUpperBound.x = v.x;
            if (v.y > WorldUpperBound.y) WorldUpperBound.y = v.y;
        }
    }


    public void update(Vector2 v) {
        this.WorldLowerBound = this.LocalLowerBound.add(v);
        this.WorldUpperBound = this.LocalUpperBound.add(v);
    }

    public AABB(final AABB copy) {
        this(copy.WorldLowerBound, copy.WorldUpperBound);
    }

    /**
     * AABB��Χ�г�ʼ���췽��
     *
     * @param lowerVertex ���µ�
     * @param upperVertex ���ϵ�
     */
    public AABB(final Vector2 lowerVertex, final Vector2 upperVertex) {
        this.WorldLowerBound = lowerVertex.clone();
        this.WorldUpperBound = upperVertex.clone();
    }

    /**
     * �����Χ�е�ֵ
     *
     * @return ��Χ��ֵ
     */
    public float Volume() {
        return (WorldUpperBound.x - WorldLowerBound.x) * (WorldUpperBound.y - WorldLowerBound.y);
    }

    /**
     * ���ö���Ϊ��������
     *
     * @param aabb Ҫ���ƵĶ���
     */
    public final void set(final AABB aabb) {
        Vector2 v = aabb.WorldLowerBound;
        WorldLowerBound.x = v.x;
        WorldLowerBound.y = v.y;
        Vector2 v1 = aabb.WorldUpperBound;
        WorldUpperBound.x = v1.x;
        WorldUpperBound.y = v1.y;
    }

    /**
     *���ö���Ϊ��������
     * @param lowerBound ���µ�
     * @param upperBound ���ϵ�
     */

    public final void set(final Vector2 lowerBound, final Vector2 upperBound) {
        this.WorldLowerBound.x = lowerBound.x;
        this.WorldLowerBound.y = lowerBound.y;
        this.WorldUpperBound.x = upperBound.x;
        this.WorldUpperBound.y = upperBound.y;
    }

    public final Vector2[] getWorldVertices() {
        Vector2[] argRay=new Vector2[4];
        argRay[0]=WorldUpperBound.clone();
        argRay[0].x -= WorldUpperBound.x - WorldLowerBound.x;
        argRay[1]=WorldUpperBound.clone();
        argRay[2]=WorldLowerBound.clone();
        argRay[2].x += WorldUpperBound.x - WorldLowerBound.x;
        argRay[3]=WorldLowerBound.clone();
        return argRay;
    }

    /**
     * ��������Χ�кϲ�������ֵ����ǰ��Χ��
     *
     * @param aabb1 ��Χ��1
     * @param aabb2 ��Χ��2
     */
    public final AABB combineLocal(final AABB aabb1, final AABB aabb2) {
        WorldLowerBound.x = aabb1.WorldLowerBound.x < aabb2.WorldLowerBound.x ? aabb1.WorldLowerBound.x : aabb2.WorldLowerBound.x;
        WorldLowerBound.y = aabb1.WorldLowerBound.y < aabb2.WorldLowerBound.y ? aabb1.WorldLowerBound.y : aabb2.WorldLowerBound.y;
        WorldUpperBound.x = aabb1.WorldUpperBound.x > aabb2.WorldUpperBound.x ? aabb1.WorldUpperBound.x : aabb2.WorldUpperBound.x;
        WorldUpperBound.y = aabb1.WorldUpperBound.y > aabb2.WorldUpperBound.y ? aabb1.WorldUpperBound.y : aabb2.WorldUpperBound.y;
        return this;
    }

    /**
     * ����ǰ��Χ������һ����Χ�н��кϲ�
     *
     * @param aabb ��һ����Χ��
     */
    public final AABB combineLocal(final AABB aabb) {
        WorldLowerBound.x = WorldLowerBound.x < aabb.WorldLowerBound.x ? WorldLowerBound.x : aabb.WorldLowerBound.x;
        WorldLowerBound.y = WorldLowerBound.y < aabb.WorldLowerBound.y ? WorldLowerBound.y : aabb.WorldLowerBound.y;
        WorldUpperBound.x = WorldUpperBound.x > aabb.WorldUpperBound.x ? WorldUpperBound.x : aabb.WorldUpperBound.x;
        WorldUpperBound.y = WorldUpperBound.y > aabb.WorldUpperBound.y ? WorldUpperBound.y : aabb.WorldUpperBound.y;
        return this;
    }

    public final AABB combine(final AABB aabb) {
        AABB returnValue = new AABB();
        returnValue.WorldLowerBound.x = WorldLowerBound.x < aabb.WorldLowerBound.x ? WorldLowerBound.x : aabb.WorldLowerBound.x;
        returnValue.WorldLowerBound.y = WorldLowerBound.y < aabb.WorldLowerBound.y ? WorldLowerBound.y : aabb.WorldLowerBound.y;
        returnValue.WorldUpperBound.x = WorldUpperBound.x > aabb.WorldUpperBound.x ? WorldUpperBound.x : aabb.WorldUpperBound.x;
        returnValue.WorldUpperBound.y = WorldUpperBound.y > aabb.WorldUpperBound.y ? WorldUpperBound.y : aabb.WorldUpperBound.y;
        return returnValue;
    }

    /**
     * �жϵ�ǰ��Χ���ڲ��Ƿ����ĳ����Χ��
     *
     * @param aabb �жϰ�Χ��
     * @return �Ƿ񱻰���
     */
    public final boolean contains(final AABB aabb) {
        return WorldLowerBound.x <= aabb.WorldLowerBound.x && WorldLowerBound.y <= aabb.WorldLowerBound.y
                && aabb.WorldUpperBound.x <= WorldUpperBound.x && aabb.WorldUpperBound.y <= WorldUpperBound.y;
    }

    /**
     * �ж�������Χ���Ƿ����ص�����
     *
     * @param a ��Χ��1
     * @param b ��Χ��2
     * @return �Ƿ��ص�
     */
    public static final boolean testOverlap(final AABB a, final AABB b) {
        if (b.WorldLowerBound.x - a.WorldUpperBound.x > 0.0f || b.WorldLowerBound.y - a.WorldUpperBound.y > 0.0f) {
            return false;
        }

        if (a.WorldLowerBound.x - b.WorldUpperBound.x > 0.0f || a.WorldLowerBound.y - b.WorldUpperBound.y > 0.0f) {
            return false;
        }

        return true;
    }

    public boolean collides(final AABB other) {
        return testOverlap(this, other);
    }

    public boolean collides(Vector2 point) {
        if (point.x < WorldLowerBound.x) return false;
        if (point.y < WorldLowerBound.y) return false;
        if (point.x > WorldUpperBound.x) return false;
        if (point.y > WorldUpperBound.y) return false;
        return true;
    }

    @Override
    public final String toString() {
        final String s = "AABB[" + WorldLowerBound + " . " + WorldUpperBound + "]";
        return s;
    }


}
