package GameComponent;

import CollisionDetection.AABB;
import CollisionDetection.AABBTree;
import CollisionDetection.Shape;
import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Vector2;

/**
 * 碰撞器组件
 *
 * @author ZYM
 */
public class Collider extends EngineBehavior {

    /**
     * 局部坐标下形状的各顶点坐标
     */
    private Shape LocalShape;

    /**
     * 全局坐标形状
     */
    public Shape WorldShape;

    /**
     * 是否不可被动的移动
     */
    public boolean IsStatic = false;

    /**
     * 碰撞是否交给脚本处理
     */
    public boolean IsTrigger = false;

    /**
     * 动态AABB树
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
     * 朝着某方向 移动物体 移动距离为v的模
     *
     * @param v 速度向量
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
