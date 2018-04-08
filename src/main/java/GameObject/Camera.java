package GameObject;

import java.util.ArrayList;

import GameConfig.gameConfig;
import GameException.GameException;
import GameModel.Model;
import GameScript.CameraScript;
import GameTools.Screen;
import GameTools.Vector2;

/**
 * ��Ӱ����
 */
public class Camera extends GameObject implements gameConfig {

    /**
     * ���������
     */
    public static ArrayList<Camera> cameras = new ArrayList<>();

    /**
     * ������ӽǿ�ȱ��� (����Ļ������)
     */
    public float ViewW;

    /**
     * ������ӽǸ߶ȱ��� (����Ļ�߶����)
     */
    public float ViewH;

    public final static Vector2 CameraWindowPos = new Vector2((Screen.Width) / 2, (Screen.Height) / 2);

    /**
     * ��������췽��
     *
     * @param Parent ������
     * @param worldX ��������X
     * @param worldY ��������Y
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
            throw new GameException("���������Ϊ0");
        }

        return cameras.get(0);
    }

    /**
     * ��������ת�������Ļ����
     *
     * @param v ��������
     * @return ��Ļ����
     */
    public Vector2 WorldToScreenPoint(Vector2 v) {

        float x = getModel().transform_world.getPosition().getX() * Screen.PX;
        float y = getModel().transform_world.getPosition().getY() * Screen.PX;

        return new Vector2(CameraWindowPos.getX() + v.getX() * Screen.PX - x,
                CameraWindowPos.getY() + v.getY() * Screen.PX - y);
    }

    /**
     * �������Ļ����ת��������
     *
     * @param v ��Ļ����
     * @return ��������
     */
    public Vector2 ScreenToWorldPoint(Vector2 v) {

        // world pos
        float x = getModel().transform_world.getPosition().getX();
        float y = getModel().transform_world.getPosition().getY();

        return new Vector2(x - (CameraWindowPos.getX() - v.getX()) / Screen.PX,
                y - (CameraWindowPos.getY() - v.getY()) / Screen.PX);
    }
}
