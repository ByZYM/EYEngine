package GameObject;

import CollisionDetection.Shape;
import GameComponent.Collider;
import GameModel.Model;
import GameScript.Objects.WallScript;
import GameTools.Vector2;

/**
 * 固定实体属性类，继承GameLogic.Object类
 */
public class Wall extends GameObject {

    /**
     * 墙
     *
     * @param Parent 父物体
     * @param worldX 世界坐标X
     * @param worldY 世界坐标Y
     */
    public Wall(GameObject Parent, float worldX, float worldY) {
        super(Parent);
        this.setModel(new Model(this, 100, 20, 0, worldX, worldY));

        AddComponent(new Collider(this));
        ((Collider) GetComponent(Collider.class)).IsStatic = true;
        ((Collider) GetComponent(Collider.class)).setLocalShape(
                new Shape(new Vector2(0, 0), new Vector2(100, 0), new Vector2(100, -20), new Vector2(0, -20)));

        AddComponent(new WallScript(this));
    }

}
