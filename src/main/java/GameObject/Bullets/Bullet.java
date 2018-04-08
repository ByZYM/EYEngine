package GameObject.Bullets;

import GameObject.GameObject;
import GameScript.Objects.Bullets.General;

public class Bullet extends GameObject {
    /**
     * ��ʼ����Ϸ����
     *
     * @param Parent ������
     */
    public Bullet(GameObject Parent) {
        super(Parent);

        AddComponent(new General(this));
    }
}
