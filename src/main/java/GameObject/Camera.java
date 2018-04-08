package GameObject;

import java.util.ArrayList;

import GameConfig.gameConfig;
import GameException.GameException;
import GameModel.Model;
import GameScript.CameraScript;
import GameTools.Screen;
import GameTools.Vector2;

/**
 * 摄影机类
 */
public class Camera extends GameObject implements gameConfig {

    /**
     * 摄像机集合
     */
    public static ArrayList<Camera> cameras = new ArrayList<>();

    /**
     * 摄像机视角宽度比例 (与屏幕宽度相比)
     */
    public float ViewW;

    /**
     * 摄像机视角高度比例 (与屏幕高度相比)
     */
    public float ViewH;

    public final static Vector2 CameraWindowPos = new Vector2((Screen.Width) / 2, (Screen.Height) / 2);

    /**
     * 摄像机构造方法
     *
     * @param Parent 父物体
     * @param worldX 世界坐标X
     * @param worldY 世界坐标Y
     */
    public Camera(GameObject Parent, float worldX, float worldY) {
        super(Parent);
        this.setModel(new Model(this, 0, 0, 0, worldX, worldY));
        ViewW = 1;
        ViewH = 1;
        this.AddComponent(new CameraScript(this));
        cameras.add(this);
    }

    public static Camera GetCurrentCamera() throws GameException {
        if (cameras.size() == 0) {
            throw new GameException("摄像机总数为0");
        }

        return cameras.get(0);
    }

    /**
     * 世界坐标转摄像机屏幕坐标
     *
     * @param v 世界坐标
     * @return 屏幕坐标
     */
    public Vector2 WorldToScreenPoint(Vector2 v) {

        float x = getModel().transform_world.getPosition().getX() * Screen.PX;
        float y = getModel().transform_world.getPosition().getY() * Screen.PX;

        return new Vector2(CameraWindowPos.getX() + v.getX() * Screen.PX - x,
                CameraWindowPos.getY() + v.getY() * Screen.PX - y);
    }

    /**
     * 摄像机屏幕坐标转世界坐标
     *
     * @param v 屏幕坐标
     * @return 世界坐标
     */
    public Vector2 ScreenToWorldPoint(Vector2 v) {

        // world pos
        float x = getModel().transform_world.getPosition().getX();
        float y = getModel().transform_world.getPosition().getY();

        return new Vector2(x - (CameraWindowPos.getX() - v.getX()) / Screen.PX,
                y - (CameraWindowPos.getY() - v.getY()) / Screen.PX);
    }
}
