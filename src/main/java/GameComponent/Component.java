package GameComponent;

import GameObject.GameObject;

/**
 * ��������ĸ���
 *
 * @author ZYM
 */
public class Component {

    //������ص���Ϸ����
    private GameObject gameobject;

    /**
     * ������ص���Ϸ����
     *
     * @param go ��Ϸ����
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
     * ������
     *
     * @param c Component
     */
    public void AddComponent(Component c) {
        gameobject.AddComponent(c);
    }

    /**
     * �����������ɾ�����
     *
     * @param c �����
     */
    public void DeleteComponent(Class<?> c) {
        gameobject.DeleteComponent(c);
    }

    /**
     * �����������Ѱ�ҹ����������ϵ����ʵ��
     *
     * @param t �����
     * @return ���ʵ��
     */
    public Object GetComponent(Class<?> t) {
        return gameobject.GetComponent(t);
    }

}
