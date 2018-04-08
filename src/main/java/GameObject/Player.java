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
 * 游戏物体类，继承GameObject 类
 */
public class Player extends GameObject {

    /**
     * 玩家
     *
     * @param Parent 父物体
     * @param worldX 世界坐标X
     * @param worldY 世界坐标Y
     * @throws IOException   输入输出错误
     * @throws GameException 游戏错误
     */
    public Player(GameObject Parent, float worldX, float worldY) throws GameException, IOException {

        super(Parent);

		/* -[#必要]-设置物体模型相关属性 */
        this.setModel(new Model(this, 1, 2, 0, worldX, worldY));

		/* 添加插件组件 */
        AddComponent(new CameraScript(this));
        AddComponent(new MouseScript(this));
        AddComponent(new PlayerMoveScript(this));

		/* 添加碰撞器组件 */
        AddComponent(new  Collider(this));
        /* -[#添加碰撞器组件后必要]-设置碰撞器形状 */
        ((Collider) GetComponent(Collider.class))
                .setLocalShape(new Shape(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, -2), new Vector2(0, -2)));


		/* 添加动画组件 */
        AddComponent(new Animator(this));
        Animator animator = ((Animator) GetComponent(Animator.class));
        /* 初始化动画 */
        animator.SetAnimation(new Animation("idle", "img/idle.bmp", 100, 200),
                              new Animation("right", "img/right.bmp", 100, 200), new Animation("left", "img/left.bmp", 100, 200));

		/* 设置动画参数 */
        animator.SetBool("left", false);
        animator.SetBool("right", false);
        animator.SetBool("idle", true);

		/* 设置动画转换条件 */
        animator.AddTransition("idle", Compare.BoolEqual, "true", "left", "idle");
        animator.AddTransition("idle", Compare.BoolEqual, "true", "right", "idle");

        animator.AddTransition("left", Compare.BoolEqual, "true", "idle", "left");
        animator.AddTransition("left", Compare.BoolEqual, "true", "right", "left");
        animator.AddTransition("right", Compare.BoolEqual, "true", "idle", "right");
        animator.AddTransition("right", Compare.BoolEqual, "true", "left", "right");

        //添加子物体
        Light light = new Light(this, 0, 0);
        light.SetName("light");

    }

}
