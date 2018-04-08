package GameObject;

import CollisionDetection.Shape;
import GameComponent.Animator;
import GameComponent.Collider;
import GameException.GameException;
import GameModel.Model;
import GameScript.Player.PlayerMoveScript;
import GameScript.Player.CameraScript;
import GameScript.Player.MouseScript;
import GameTools.Animation.Animation;
import GameTools.Compare;
import GameTools.Vector2;

import java.io.IOException;

/**
 * ��Ϸ�����࣬�̳�GameObject ��
 */
public class Player extends GameObject {

    /**
     * ���
     *
     * @param Parent ������
     * @param worldX ��������X
     * @param worldY ��������Y
     * @throws IOException   �����������
     * @throws GameException ��Ϸ����
     */
    public Player(GameObject Parent, float worldX, float worldY) throws GameException, IOException {

        super(Parent);

		/* -[#��Ҫ]-��������ģ��������� */
        this.setModel(new Model(this, 1, 2, 0, worldX, worldY));

		/* ��Ӳ����� */
        AddComponent(new CameraScript(this));
        AddComponent(new MouseScript(this));
        AddComponent(new PlayerMoveScript(this));

		/* �����ײ����� */
        AddComponent(new  Collider(this));
        /* -[#�����ײ��������Ҫ]-������ײ����״ */
        ((Collider) GetComponent(Collider.class))
                .setLocalShape(new Shape(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, -2), new Vector2(0, -2)));


		/* ��Ӷ������ */
        AddComponent(new Animator(this));
        Animator animator = ((Animator) GetComponent(Animator.class));
        /* ��ʼ������ */
        animator.SetAnimation(new Animation("idle", "img/idle.bmp", 100, 200),
                              new Animation("right", "img/right.bmp", 100, 200), new Animation("left", "img/left.bmp", 100, 200));

		/* ���ö������� */
        animator.SetBool("left", false);
        animator.SetBool("right", false);
        animator.SetBool("idle", true);

		/* ���ö���ת������ */
        animator.AddTransition("idle", Compare.BoolEqual, "true", "left", "idle");
        animator.AddTransition("idle", Compare.BoolEqual, "true", "right", "idle");

        animator.AddTransition("left", Compare.BoolEqual, "true", "idle", "left");
        animator.AddTransition("left", Compare.BoolEqual, "true", "right", "left");
        animator.AddTransition("right", Compare.BoolEqual, "true", "idle", "right");
        animator.AddTransition("right", Compare.BoolEqual, "true", "left", "right");

        //���������
        Light light = new Light(this, 0, 0);
        light.SetName("light");

    }

}
