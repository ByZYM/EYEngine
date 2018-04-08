package GameComponent;

import GameObject.GameObject;
import GameTools.Vector2;

/**
 * �����ƶ���
 *
 * @author ZYM
 */
public class Transform extends Component {

    /**
     * ���������ʼ��
     *
     * @param go �����������
     */
    public Transform(GameObject go) {
        super(go);
    }

    /**
     * ������������ xyΪ�ѿ�������ϵ
     */
    private Vector2 position = new Vector2(0, 0);

    /**
     * ��ȡ���嵱ǰ��������λ�� ��Ϊ��λ
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
     * ����ĳ���� �ƶ����� �ƶ�����Ϊv��ģ
     *
     * @param v �ٶ�����
     */
    public void translate(Vector2 v) {
        position.addLocal(v);
    }

}