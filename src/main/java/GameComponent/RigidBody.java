package GameComponent;

import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Time;
import GameTools.Vector2;

/**
 * ������������������Լ�������ײ
 *
 * @author zym
 */
public class RigidBody extends EngineBehavior {

    /* �������ٶ� */
    private float Gravity = 9.8f;

    /* �������� */
    private float M = 1;

    /* �����˶����� */ // ���˶���������� ţ��
    private float Drag = 5;

    /* ���嵱ǰ�ٶ� */
    private Vector2 Velocity = new Vector2(0, 0);

    /* �Ƿ�ʹ������ */
    private boolean UseGravity = true;

    /* ���Ա���
         * 0Ϊ������ 1Ϊ��������ʧ
          * */
    private float Bouncyness = 1;

    /* �Ƿ��ڵ����� */
    private boolean OnGround = false;

    /* �Ƿ����ײ */
    private boolean CanInteract = true;

    private Collider collider = null;

    public RigidBody(GameObject go) {
        super(go);
    }

    @Override
    public void Start() throws GameException {
        if (GetComponent(Collider.class) == null) {
            throw new GameException("RigidBody����б���Ҫ��Collider���֧��");
        }
        setCollider((Collider) GetComponent(Collider.class));
    }

    /**
     * ��ȡ�¸�ʱ����������ײ��
     *
     * @return ������ײ��
     */
    public Collider GetNextCollider() {
        return null;
    }

    @Override
    public void OnRigidBodyCollisionEnter(GameObject other) {

        // ��ǰ��ײ��������
        RigidBody RigidBody1 = ((RigidBody) GetComponent(RigidBody.class));
        // ������ײ��������
        RigidBody RigidBody2 = ((RigidBody) other.GetComponent(RigidBody.class));

        if (RigidBody2 != null) {

            // ��ǰ��ײ���ٶ�
            Vector2 V1 = RigidBody1.getVelocity();

            float M1 = RigidBody1.getM();

            Vector2 V2 = RigidBody2.getVelocity();

            float M2 = RigidBody2.getM();

            // ��ײ���ٶ�
            Vector2 V1_ = new Vector2();
            Vector2 V2_ = new Vector2();


            V1_.setX(((M1 - M2) * V1.getX() + 2 * M2 * V2.getX()) / (M1 + M2));
            V1_.setY(((M1 - M2) * V1.getY() + 2 * M2 * V2.getY()) / (M1 + M2));

            V2_.setX(((M2 - M1) * V2.getX() + 2 * M1 * V1.getX()) / (M1 + M2));
            V2_.setY(((M2 - M1) * V2.getY() + 2 * M1 * V1.getY()) / (M1 + M2));


            RigidBody2.setVelocity(V2_.multi(RigidBody2.Bouncyness));

            RigidBody1.setVelocity(V1_.multi(RigidBody1.Bouncyness));

//			Debug.DrawString(V1 + " " + V2 + " " + RigidBody1.getVelocity() + " " + RigidBody2.getVelocity(), 50, 230);

        } else if (RigidBody2 == null) {

            Vector2 V1 = RigidBody1.getVelocity();

            float M1 = RigidBody1.getM();
            float M2 = 10000000;

            Vector2 V1_ = new Vector2(0, 0);

            V1_.setX(((M1 - M2) * V1.getX()) / (M1 + M2));
            V1_.setY(((M1 - M2) * V1.getY()) / (M1 + M2));

            RigidBody1.setVelocity(V1_.multi(RigidBody1.Bouncyness));
        }
    }

    @Override
    public void Update() throws GameException {
        // /* �ж��Ƿ��ڵ����� */
        // if(OnGround==true){
        // OnGround=true;
        // }else{
        // OnGround=false;
        // }
    }

    /**
     * ����Ӱ��
     */
    @Override
    public void FixedUpdate() {
        /* ���ʹ������ */
        if (UseGravity && !OnGround) {
            AddForce(new Vector2(0, -Gravity * M));
        }

        // �������
        AddForce(new Vector2(Drag * (Velocity.getX() > 0 ? -1 : (Velocity.getX() == 0 ? 0 : 1)),
                             Drag * (Velocity.getY() > 0 ? -1 : (Velocity.getY() == 0 ? 0 : 1))).multi(getM()));

//		if (Math.abs(Velocity.getLength()) > Gravity * Time.FixedTime) {
        /* �����˶� */
        transform_world.translate(Velocity.multi(Time.FixedTime));
//		}

        // Debug.DrawString(Velocity, 50, 230);
    }

    @Override
    public void LateUpdate() {

    }

    /**
     * ����ǰ����һ���� ����ʱ��ΪDeltaTime
     *
     * @param F �����������
     */
    public void AddForce(Vector2 F) {
        /* ������ٶ� */
        Vector2 A = F.div(M);

        Velocity.addLocal(A.multi(Time.FixedTime));
    }

    /**
     * �����Ƿ������ײ
     *
     * @param interact �Ƿ���ײ
     */
    public void setCanInteract(boolean interact) {
        this.CanInteract = interact;
    }

    /**
     * �����Ƿ������ײ
     *
     * @return canInteract
     */
    public boolean getCanInteract() {
        return this.CanInteract;
    }

    /**
     * ��ȡ����
     *
     * @return Gravity ����
     */
    public float getGravity() {
        return Gravity;
    }

    /**
     * ��������
     *
     * @param g ����
     */
    public void setGravity(float g) {
        this.Gravity = g;
    }

    /**
     * @return �Ƿ�ʹ������
     */
    public boolean isUseGravity() {
        return UseGravity;
    }

    /**
     * @param useGravity �����Ƿ�ʹ������
     */
    public void setUseGravity(boolean useGravity) {
        UseGravity = useGravity;
    }

    /**
     * @return ��ȡ��������
     */
    public float getM() {
        return M;
    }

    /**
     * @param m ������������ kg
     */
    public void setM(float m) {
        M = m;
    }

    /**
     * @return ��ȡ�����˶��ٶ� m/s
     */
    public Vector2 getVelocity() {
        return Velocity;
    }

    /**
     * @param velocity ���������˶��ٶ� m/s
     */
    public void setVelocity(Vector2 velocity) {
        Velocity = velocity;
    }

    /**
     * @return ��ȡ�����˶�����
     */
    public float getDrag() {
        return Drag;
    }

    /**
     * @param drag ���������˶�����������Խ���������Խ�졣
     */
    public void setDrag(float drag) {
        Drag = drag;
    }

    /**
     * @return ��ײ��
     */
    public Collider getCollider() {
        return collider;
    }

    /**
     * ������ײ�����
     *
     * @param collider ��ײ�����
     */
    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public float getBouncyness() {
        return Bouncyness;
    }

    public void setBouncyness(float bouncyness) {
        Bouncyness = bouncyness;
    }
}
