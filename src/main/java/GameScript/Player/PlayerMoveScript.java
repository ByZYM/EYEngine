package GameScript.Player;

import GameComponent.Animator;
import GameException.GameException;
import GameTools.GameInput.Input;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Time;
import GameTools.Vector2;

import java.awt.event.KeyEvent;

/**
 * �����л��ű�
 */
public class PlayerMoveScript extends EngineBehavior {
    Animator animator;

    /**
     * ����Ϸ���󡢳�ʼ�����
     *
     * @param go ��Ϸ����
     */
    public PlayerMoveScript(GameObject go) {
        super(go);
    }

    @Override
    public void Start() throws GameException {
        animator = (Animator) GetComponent(Animator.class);
    }

    @Override
    public void Update() throws GameException {

        animator.SetBool("idle", true);
        animator.SetBool("left", false);
        animator.SetBool("right", false);

        if (Input.GetKeyDown(KeyEvent.VK_W)) {
            transform_world.translate(new Vector2(0, 10).multi(Time.DeltaTime));
        }
        if (Input.GetKeyDown(KeyEvent.VK_S)) {
            transform_world.translate(new Vector2(0, -10).multi(Time.DeltaTime));
        }

        if (Input.GetKeyDown(KeyEvent.VK_A)) {
            transform_world.translate(new Vector2(-10, 0).multi(Time.DeltaTime));

			/* ���ö������� */
            animator.SetBool("left", true);
            animator.SetBool("right", false);
            animator.SetBool("idle", false);

        }

        if (Input.GetKeyDown(KeyEvent.VK_D)) {
            transform_world.translate(new Vector2(10, 0).multi(Time.DeltaTime));
            /* ���ö������� */
            animator.SetBool("left", false);
            animator.SetBool("right", true);
            animator.SetBool("idle", false);
        }

//        Debug.DrawString("left:" + animator.GetBool("left"), 50, 170);
//        Debug.DrawString("right:" + animator.GetBool("right"), 50, 190);
//        Debug.DrawString("idle:" + animator.GetBool("idle"), 50, 210);
    }
}
