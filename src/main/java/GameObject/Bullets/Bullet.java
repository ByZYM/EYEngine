package GameObject.Bullets;

import GameObject.GameObject;
import GameScript.Objects.Bullets.General;

public class Bullet extends GameObject {
    /**
     * 初始化游戏物体
     *
     * @param Parent 父物体
     */
    public Bullet(GameObject Parent) {
        super(Parent);

        AddComponent(new General(this));
    }
}
