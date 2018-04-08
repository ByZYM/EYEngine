package GameScript.Objects;

import GameObject.GameObject;
import GameScript.EngineBehavior;

public class WallScript extends EngineBehavior {

    /**
     * Wall脚本组件
     *
     * @param go 挂载游戏物体
     */
    public WallScript(GameObject go) {
        super(go);
    }

    @Override
    public void Update() {
    }

}
