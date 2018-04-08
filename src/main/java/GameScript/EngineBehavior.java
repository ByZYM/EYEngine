package GameScript;

import GameComponent.Component;
import GameComponent.Transform;
import GameException.GameException;
import GameObject.GameObject;
import GameTools.Vector2;

import java.lang.reflect.Constructor;

/**
 * 脚本父类
 */
abstract public class EngineBehavior extends Component {

    /**
     * 物体位置组件
     */
    public Transform transform_world;
    private Transform transform_space;

    /**
     * 是否已经初始化
     */
    public boolean IsStarted = false;

    /**
     * 绑定游戏对象、初始化组件
     *
     * @param go 游戏对象
     */
    public EngineBehavior(GameObject go) {
        super(go);
        this.transform_world = go.getModel().transform_world;
        this.transform_space = go.getModel().transform_space;
    }

    /**
     * 在某个位置 初始化游戏物体
     *
     * @param prefab    游戏预设体 继承与 {@link GameObject}
     * @param parent    父物体 继承与 {@link GameObject}
     * @param transform 物体初始化位置 {@link Vector2}
     * @return {@link GameObject}返回游戏物体实例
     */
    public GameObject Instantiate(Class<? extends GameObject> prefab, GameObject parent, Vector2 transform) {

        Constructor<? extends GameObject> constructor;
        GameObject returnObject = null;
        try {
            constructor = (Constructor<? extends GameObject>) prefab.getConstructors()[0];

            returnObject = constructor.newInstance(parent, transform.getX(), transform.getY());

//			GameObject.AddGameObject(returnObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    /**
     * 在某个位置 初始化游戏物体
     *
     * @param prefab 游戏预设体 继承与 {@link GameObject}
     * @param parent 父物体 继承与 {@link GameObject}
     * @param worldX 物体初始化位置x {@link Float}
     * @param worldY 物体初始化位置y {@link Float}
     * @return {@link GameObject}返回游戏物体实例
     */
    public GameObject Instantiate(Class<? extends GameObject> prefab, GameObject parent, float worldX, float worldY) {

        Constructor<? extends GameObject> constructor;
        GameObject returnObject = null;
        try {

            constructor = (Constructor<? extends GameObject>) prefab.getConstructors()[0];

            returnObject = constructor.newInstance(parent, worldX, worldY);

//			GameObject.AddGameObject(returnObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    /**
     * 初始化执行 只执行一次
     *
     * @throws GameException 游戏错误
     */
    public void Start() throws GameException {

    }

    /**
     * 固定时间更新方法
     *
     * @throws GameException 游戏错误
     */
    public void FixedUpdate() throws GameException {
    }

    /**
     * 每帧更新方法
     *
     * @throws GameException 游戏错误
     */
    public void Update() throws GameException {
    }

    /**
     * update之后执行方法
     */
    public void LateUpdate() {
    }

    /**
     * 当一个刚体接触另一个刚体时调用一次 只有其中一个物体被调用
     *
     * @param other 碰撞物体
     */
    public void OnRigidBodyCollisionEnter(GameObject other) {

    }

    /**
     * 当一个刚体接触另一个刚体时调用 两个物体都会被调用一次 （两个Collider都不勾选IsTrigger时调用）
     *
     * @param other 碰撞物体
     */
    public void OnCollisionEnter(GameObject other) {

    }

    /**
     * 当一个刚体接触另一个刚体时调用 两个物体都会被调用一次 （Collider勾选IsTrigger时调用）
     *
     * @param other 碰撞物体
     */
    public void OnTriggerEnter(GameObject other) {

    }

    /**
     * 当一个刚体接触另一个刚体时 每帧调用
     */
    public void OnCollisionStay() {

    }

    /**
     * 当一个刚体停止接触另一个刚体时 调用一次
     */
    public void OnCollisionExit() {

    }


}
