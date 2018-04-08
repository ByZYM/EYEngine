package GameModel;

import EngineFactory.GraphicsFactory;
import GameComponent.Transform;
import GameException.GameException;
import GameObject.Camera;
import GameObject.GameObject;
import GameTools.Screen;
import GameTools.Vector2;

import java.awt.*;

/**
 * 游戏对象
 *
 * @author ZYM 包含模型，模型贴图，位置组件等
 */
public class Model {

    /**
     * 物体世界位置组件
     */
    public final Transform transform_world;

    /**
     * 物体局部位置组件（如果物体为子物体，则局部坐标为相对父物体的坐标）
     */
    public final Transform transform_space;

    /**
     * 贴图对象
     */
    protected Map map = null;

    /**
     * 物体宽度 单位 m
     */
    private float width;

    /**
     * 物体高度 单位 m
     */
    private float height;

    /**
     * 渲染顺序，数值越高越先渲染
     */
    private int depth = 0;

    /**
     * 挂载游戏物体
     */
    private GameObject gameobject;

    /**
     * 有贴图模型
     *
     * @param gameobject 挂载游戏物体
     * @param m          模型贴图
     * @param width      模型宽度
     * @param height     模型高度
     * @param depth      摄像机渲染顺序
     * @param PosX       如果是子物体，则为局部坐标，否则为世界坐标
     * @param PosY       如果是子物体，则为局部坐标，否则为世界坐标
     */
    public Model(GameObject gameobject, Map m, float width, float height, int depth, float PosX, float PosY) {
        this.gameobject = gameobject;
        map = m;
        this.width = width;
        this.height = height;
        this.setDepth(depth);
        transform_world = new Transform(gameobject);
        transform_space = new Transform(gameobject);

        if (gameobject.Parent == null) {
            transform_world.setPosition(new Vector2(PosX, PosY));
        } else {
            transform_space.setPosition(new Vector2(PosX, PosY));
            transform_world.setPosition(transform_space.getPosition().add(gameobject.Parent.getModel().transform_world.getPosition()));
        }
    }


    /**
     * 无贴图模型
     *
     * @param gameobject 挂载游戏物体
     * @param width      模型宽度
     * @param height     模型高度
     * @param depth      摄像机渲染顺序
     */
    public Model(GameObject gameobject, float width, float height, int depth, float worldX, float worldY) {
        this(gameobject, null, width, height, depth, worldX, worldY);
    }

    /**
     * 在屏幕上绘制模型
     *
     * @param g 绘制对象
     * @throws GameException 游戏错误
     */
    public void draw(Graphics2D g) throws GameException {
        //如果具有父物体，则该物体世界坐标=父物体世界坐标+物体局部坐标
        if (gameobject.Parent != null) {
            transform_world.setPosition(transform_space.getPosition().add(gameobject.Parent.getModel().transform_world.getPosition()));
        }

        Vector2 WindowPoint = Camera.GetCurrentCamera().WorldToScreenPoint(transform_world.getPosition());

        if (width == 0 && height == 0) {
            //不绘制
        } else if (map != null && map.getIm() != null) {
            GraphicsFactory.GetDrawE().DrawImage(map.getIm(),
                    (int) WindowPoint.getX(),
                    (int) (Camera.CameraWindowPos.getY() * 2 - WindowPoint.getY()),
                    (int) (getWidth() * Screen.PX),
                    (int) (getHeight() * Screen.PX), null);

        } else {
            GraphicsFactory.GetDrawE().DrawRect(
                    (int) WindowPoint.getX(),
                    (int) (Camera.CameraWindowPos.getY() * 2 - WindowPoint.getY()),
                    (int) (getWidth() * Screen.PX),
                    (int) (getHeight() * Screen.PX));
        }
    }

    public Transform getTransformWorld() {
        return transform_world;
    }

    /**
     * 设置物体宽度
     *
     * @param width 物体宽度
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * 获取物体宽度
     *
     * @return 物体宽度 米为单位
     */
    public float getWidth() {
        return width;
    }

    /**
     * 设置物体高度
     *
     * @param height 物体高度
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * 获取物体高度
     *
     * @return 物体高度 米为单位
     */
    public float getHeight() {
        return height;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}
