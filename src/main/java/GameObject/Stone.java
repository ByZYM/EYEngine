package GameObject;

import CollisionDetection.Shape;
import GameComponent.Collider;
import GameComponent.RigidBody;
import GameException.GameException;
import GameModel.Map;
import GameModel.Model;
import GameScript.Objects.DropItemScript;
import GameTools.Vector2;

import java.io.IOException;

/**
 * 固定实体属性类，继承GameLogic.Object类
 */
public class Stone extends GameObject {

    /**
     * 石头
     *
     * @param Parent 父物体
     * @param worldX 世界坐标X
     * @param worldY 世界坐标Y
     * @throws IOException   输入输出错误
     * @throws GameException 游戏错误
     */
    public Stone(GameObject Parent, float worldX, float worldY) throws GameException, IOException {

        super(Parent);

        this.setModel(new Model(this, new Map("img/1.png"), 1, 1, 0, worldX, worldY));

        // 添加插件组件
        AddComponent(new DropItemScript(this));

        AddComponent(new Collider(this));

        ((Collider) GetComponent(Collider.class))
                .setLocalShape(new Shape(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, -1), new Vector2(0, -1)));

        AddComponent(new RigidBody(this));
        ((RigidBody) GetComponent(RigidBody.class)).setM(1);

    }

}
