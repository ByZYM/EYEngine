package GameComponent;

import CollisionDetection.AABB;
import CollisionDetection.AABBTree;
import CollisionDetection.Shape;
import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Vector2;

/**
 * ��ײ�����
 *
 * @author ZYM
 */
public class Collider extends EngineBehavior {

    /**
     * �ֲ���������״�ĸ���������
     */
    private Shape LocalShape;

    /**
     * ȫ��������״
     */
    public Shape WorldShape;

    /**
     * �Ƿ񲻿ɱ������ƶ�
     */
    public boolean IsStatic = false;

    /**
     * ��ײ�Ƿ񽻸��ű�����
     */
    public boolean IsTrigger = false;

    /**
     * ��̬AABB��
     */
    public final static AABBTree bp = new AABBTree();

    public AABB RealAABB;

    public Collider(GameObject go) {
        super(go);
        LocalShape = new Shape(new Vector2(0, 0), new Vector2(3, 0), new Vector2(3, -3), new Vector2(0, -3));
        WorldShape = LocalShape.translate(transform_world.getPosition());
    }

    @Override
    public void Start() throws GameException {
        WorldShape = LocalShape.translate(transform_world.getPosition());
    }

    @Override
    public void Update() throws GameException {
        WorldShape = LocalShape.translate(transform_world.getPosition());
        RealAABB.update(transform_world.getPosition());
//        Debug.DrawString(getGameobject().Name,transform_world.getPosition());
//		Debug.DrawShape(WorldShape, Color.GREEN);
    }

    /**
     * ����ĳ���� �ƶ����� �ƶ�����Ϊv��ģ
     *
     * @param v �ٶ�����
     */
    public void translate(Vector2 v) {
        transform_world.translate(v);
        WorldShape = LocalShape.translate(transform_world.getPosition());
    }

    public Shape getLocalShape() {
        return LocalShape;
    }

    public Shape getWorldShape() {
        return LocalShape.translate(transform_world.getPosition());
    }

    public void setLocalShape(Shape localShape) {
        this.LocalShape = localShape;
        RealAABB = new AABB(this);
        bp.Add(RealAABB);
    }

}
