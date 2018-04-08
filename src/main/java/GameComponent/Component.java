package GameComponent;

import GameObject.GameObject;

/**
 * 所有组件的父类
 *
 * @author ZYM
 */
public class Component {

    //组件挂载的游戏物体
    private GameObject gameobject;

    /**
     * 组件挂载的游戏物体
     *
     * @param go 游戏物体
     */
    public Component(GameObject go) {
        this.gameobject = go;
    }

    public GameObject getGameobject() {
        return gameobject;
    }

    public void setGameobject(GameObject gameobject) {
        this.gameobject = gameobject;
    }


    /**
     * 添加组件
     *
     * @param c Component
     */
    public void AddComponent(Component c) {
        gameobject.AddComponent(c);
    }

    /**
     * 根据组件类型删除组件
     *
     * @param c 组件类
     */
    public void DeleteComponent(Class<?> c) {
        gameobject.DeleteComponent(c);
    }

    /**
     * 根据组件类型寻找挂载在物体上的组件实例
     *
     * @param t 组件类
     * @return 组件实体
     */
    public Object GetComponent(Class<?> t) {
        return gameobject.GetComponent(t);
    }

}
