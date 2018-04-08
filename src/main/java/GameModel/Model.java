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
 * ��Ϸ����
 *
 * @author ZYM ����ģ�ͣ�ģ����ͼ��λ�������
 */
public class Model {

    /**
     * ��������λ�����
     */
    public final Transform transform_world;

    /**
     * ����ֲ�λ��������������Ϊ�����壬��ֲ�����Ϊ��Ը���������꣩
     */
    public final Transform transform_space;

    /**
     * ��ͼ����
     */
    protected Map map = null;

    /**
     * ������ ��λ m
     */
    private float width;

    /**
     * ����߶� ��λ m
     */
    private float height;

    /**
     * ��Ⱦ˳����ֵԽ��Խ����Ⱦ
     */
    private int depth = 0;

    /**
     * ������Ϸ����
     */
    private GameObject gameobject;

    /**
     * ����ͼģ��
     *
     * @param gameobject ������Ϸ����
     * @param m          ģ����ͼ
     * @param width      ģ�Ϳ��
     * @param height     ģ�͸߶�
     * @param depth      �������Ⱦ˳��
     * @param PosX       ����������壬��Ϊ�ֲ����꣬����Ϊ��������
     * @param PosY       ����������壬��Ϊ�ֲ����꣬����Ϊ��������
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
     * ����ͼģ��
     *
     * @param gameobject ������Ϸ����
     * @param width      ģ�Ϳ��
     * @param height     ģ�͸߶�
     * @param depth      �������Ⱦ˳��
     */
    public Model(GameObject gameobject, float width, float height, int depth, float worldX, float worldY) {
        this(gameobject, null, width, height, depth, worldX, worldY);
    }

    /**
     * ����Ļ�ϻ���ģ��
     *
     * @param g ���ƶ���
     * @throws GameException ��Ϸ����
     */
    public void draw(Graphics2D g) throws GameException {
        //������и����壬���������������=��������������+����ֲ�����
        if (gameobject.Parent != null) {
            transform_world.setPosition(transform_space.getPosition().add(gameobject.Parent.getModel().transform_world.getPosition()));
        }

        Vector2 WindowPoint = Camera.GetCurrentCamera().WorldToScreenPoint(transform_world.getPosition());

        if (width == 0 && height == 0) {
            //������
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
     * ����������
     *
     * @param width ������
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * ��ȡ������
     *
     * @return ������ ��Ϊ��λ
     */
    public float getWidth() {
        return width;
    }

    /**
     * ��������߶�
     *
     * @param height ����߶�
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * ��ȡ����߶�
     *
     * @return ����߶� ��Ϊ��λ
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
