package GameComponent;

import GameObject.GameObject;
import GameTools.Vector2;

/**
 * 物体移动类
 *
 * @author ZYM
 */
public class Transform extends Component {

    /**
     * 坐标组件初始化
     *
     * @param go 挂载物体对象
     */
    public Transform(GameObject go) {
        super(go);
    }

    /**
     * 物体世界坐标 xy为笛卡尔坐标系
     */
    private Vector2 position = new Vector2(0, 0);

    /**
     * 获取物体当前世界坐标位置 米为单位
     *
     * @return Vector2
     */
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * 朝着某方向 移动物体 移动距离为v的模
     *
     * @param v 速度向量
     */
    public void translate(Vector2 v) {
        position.addLocal(v);
    }

}