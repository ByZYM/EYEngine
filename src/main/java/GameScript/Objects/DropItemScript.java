package GameScript.Objects;

import GameObject.GameObject;
import GameScript.EngineBehavior;

/* ������ű� */
public class DropItemScript extends EngineBehavior {

    private String ItemName;

    /**
     * ����Ϸ���󡢳�ʼ�����
     *
     * @param go ��Ϸ����
     */
    public DropItemScript(GameObject go) {
        super(go);
    }

}
