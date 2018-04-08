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
 * �̶�ʵ�������࣬�̳�GameLogic.Object��
 */
public class Stone extends GameObject {

    /**
     * ʯͷ
     *
     * @param Parent ������
     * @param worldX ��������X
     * @param worldY ��������Y
     * @throws IOException   �����������
     * @throws GameException ��Ϸ����
     */
    public Stone(GameObject Parent, float worldX, float worldY) throws GameException, IOException {

        super(Parent);

        this.setModel(new Model(this, new Map("img/1.png"), 1, 1, 0, worldX, worldY));

        // ��Ӳ�����
        AddComponent(new DropItemScript(this));

        AddComponent(new Collider(this));

        ((Collider) GetComponent(Collider.class))
                .setLocalShape(new Shape(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, -1), new Vector2(0, -1)));

        AddComponent(new RigidBody(this));
        ((RigidBody) GetComponent(RigidBody.class)).setM(1);

    }

}
