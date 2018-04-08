package GameScript.Objects;

import GameObject.GameObject;
import GameScript.EngineBehavior;

/* 掉落物脚本 */
public class DropItemScript extends EngineBehavior {

    private String ItemName;

    /**
     * 绑定游戏对象、初始化组件
     *
     * @param go 游戏对象
     */
    public DropItemScript(GameObject go) {
        super(go);
    }

}
