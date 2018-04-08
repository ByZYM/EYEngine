package GameScript;

import GameComponent.Component;
import GameComponent.Transform;
import GameException.GameException;
import GameObject.GameObject;
import GameTools.Vector2;

import java.lang.reflect.Constructor;

/**
 * �ű�����
 */
abstract public class EngineBehavior extends Component {

    /**
     * ����λ�����
     */
    public Transform transform_world;
    private Transform transform_space;

    /**
     * �Ƿ��Ѿ���ʼ��
     */
    public boolean IsStarted = false;

    /**
     * ����Ϸ���󡢳�ʼ�����
     *
     * @param go ��Ϸ����
     */
    public EngineBehavior(GameObject go) {
        super(go);
        this.transform_world = go.getModel().transform_world;
        this.transform_space = go.getModel().transform_space;
    }

    /**
     * ��ĳ��λ�� ��ʼ����Ϸ����
     *
     * @param prefab    ��ϷԤ���� �̳��� {@link GameObject}
     * @param parent    ������ �̳��� {@link GameObject}
     * @param transform �����ʼ��λ�� {@link Vector2}
     * @return {@link GameObject}������Ϸ����ʵ��
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
     * ��ĳ��λ�� ��ʼ����Ϸ����
     *
     * @param prefab ��ϷԤ���� �̳��� {@link GameObject}
     * @param parent ������ �̳��� {@link GameObject}
     * @param worldX �����ʼ��λ��x {@link Float}
     * @param worldY �����ʼ��λ��y {@link Float}
     * @return {@link GameObject}������Ϸ����ʵ��
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
     * ��ʼ��ִ�� ִֻ��һ��
     *
     * @throws GameException ��Ϸ����
     */
    public void Start() throws GameException {

    }

    /**
     * �̶�ʱ����·���
     *
     * @throws GameException ��Ϸ����
     */
    public void FixedUpdate() throws GameException {
    }

    /**
     * ÿ֡���·���
     *
     * @throws GameException ��Ϸ����
     */
    public void Update() throws GameException {
    }

    /**
     * update֮��ִ�з���
     */
    public void LateUpdate() {
    }

    /**
     * ��һ������Ӵ���һ������ʱ����һ�� ֻ������һ�����屻����
     *
     * @param other ��ײ����
     */
    public void OnRigidBodyCollisionEnter(GameObject other) {

    }

    /**
     * ��һ������Ӵ���һ������ʱ���� �������嶼�ᱻ����һ�� ������Collider������ѡIsTriggerʱ���ã�
     *
     * @param other ��ײ����
     */
    public void OnCollisionEnter(GameObject other) {

    }

    /**
     * ��һ������Ӵ���һ������ʱ���� �������嶼�ᱻ����һ�� ��Collider��ѡIsTriggerʱ���ã�
     *
     * @param other ��ײ����
     */
    public void OnTriggerEnter(GameObject other) {

    }

    /**
     * ��һ������Ӵ���һ������ʱ ÿ֡����
     */
    public void OnCollisionStay() {

    }

    /**
     * ��һ������ֹͣ�Ӵ���һ������ʱ ����һ��
     */
    public void OnCollisionExit() {

    }


}
