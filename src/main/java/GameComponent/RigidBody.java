package GameComponent;

import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Time;
import GameTools.Vector2;

/**
 * 刚体组件，物体受力以及物体碰撞
 *
 * @author zym
 */
public class RigidBody extends EngineBehavior {

    /* 重力加速度 */
    private float Gravity = 9.8f;

    /* 物体质量 */
    private float M = 1;

    /* 物体运动阻力 */ // 沿运动方向的阻力 牛顿
    private float Drag = 5;

    /* 物体当前速度 */
    private Vector2 Velocity = new Vector2(0, 0);

    /* 是否使用重力 */
    private boolean UseGravity = true;

    /* 弹性比例
         * 0为不反弹 1为无能量损失
          * */
    private float Bouncyness = 1;

    /* 是否在地面上 */
    private boolean OnGround = false;

    /* 是否可碰撞 */
    private boolean CanInteract = true;

    private Collider collider = null;

    public RigidBody(GameObject go) {
        super(go);
    }

    @Override
    public void Start() throws GameException {
        if (GetComponent(Collider.class) == null) {
            throw new GameException("RigidBody组件中必须要有Collider组件支持");
        }
        setCollider((Collider) GetComponent(Collider.class));
    }

    /**
     * 获取下个时间点物体的碰撞器
     *
     * @return 返回碰撞器
     */
    public Collider GetNextCollider() {
        return null;
    }

    @Override
    public void OnRigidBodyCollisionEnter(GameObject other) {

        // 当前碰撞物刚体组件
        RigidBody RigidBody1 = ((RigidBody) GetComponent(RigidBody.class));
        // 其他碰撞物刚体组件
        RigidBody RigidBody2 = ((RigidBody) other.GetComponent(RigidBody.class));

        if (RigidBody2 != null) {

            // 当前碰撞物速度
            Vector2 V1 = RigidBody1.getVelocity();

            float M1 = RigidBody1.getM();

            Vector2 V2 = RigidBody2.getVelocity();

            float M2 = RigidBody2.getM();

            // 碰撞后速度
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
        // /* 判断是否在地面上 */
        // if(OnGround==true){
        // OnGround=true;
        // }else{
        // OnGround=false;
        // }
    }

    /**
     * 重力影响
     */
    @Override
    public void FixedUpdate() {
        /* 如果使用重力 */
        if (UseGravity && !OnGround) {
            AddForce(new Vector2(0, -Gravity * M));
        }

        // 添加阻力
        AddForce(new Vector2(Drag * (Velocity.getX() > 0 ? -1 : (Velocity.getX() == 0 ? 0 : 1)),
                             Drag * (Velocity.getY() > 0 ? -1 : (Velocity.getY() == 0 ? 0 : 1))).multi(getM()));

//		if (Math.abs(Velocity.getLength()) > Gravity * Time.FixedTime) {
        /* 物体运动 */
        transform_world.translate(Velocity.multi(Time.FixedTime));
//		}

        // Debug.DrawString(Velocity, 50, 230);
    }

    @Override
    public void LateUpdate() {

    }

    /**
     * 给当前物体一个力 持续时间为DeltaTime
     *
     * @param F 力的向量表达
     */
    public void AddForce(Vector2 F) {
        /* 计算加速度 */
        Vector2 A = F.div(M);

        Velocity.addLocal(A.multi(Time.FixedTime));
    }

    /**
     * 设置是否可以碰撞
     *
     * @param interact 是否碰撞
     */
    public void setCanInteract(boolean interact) {
        this.CanInteract = interact;
    }

    /**
     * 返回是否可以碰撞
     *
     * @return canInteract
     */
    public boolean getCanInteract() {
        return this.CanInteract;
    }

    /**
     * 获取重力
     *
     * @return Gravity 重力
     */
    public float getGravity() {
        return Gravity;
    }

    /**
     * 设置重力
     *
     * @param g 重力
     */
    public void setGravity(float g) {
        this.Gravity = g;
    }

    /**
     * @return 是否使用重力
     */
    public boolean isUseGravity() {
        return UseGravity;
    }

    /**
     * @param useGravity 设置是否使用重力
     */
    public void setUseGravity(boolean useGravity) {
        UseGravity = useGravity;
    }

    /**
     * @return 获取物体质量
     */
    public float getM() {
        return M;
    }

    /**
     * @param m 设置物体质量 kg
     */
    public void setM(float m) {
        M = m;
    }

    /**
     * @return 获取物体运动速度 m/s
     */
    public Vector2 getVelocity() {
        return Velocity;
    }

    /**
     * @param velocity 设置物体运动速度 m/s
     */
    public void setVelocity(Vector2 velocity) {
        Velocity = velocity;
    }

    /**
     * @return 获取物体运动阻力
     */
    public float getDrag() {
        return Drag;
    }

    /**
     * @param drag 设置物体运动阻力，阻力越高物体减慢越快。
     */
    public void setDrag(float drag) {
        Drag = drag;
    }

    /**
     * @return 碰撞器
     */
    public Collider getCollider() {
        return collider;
    }

    /**
     * 设置碰撞器组件
     *
     * @param collider 碰撞器组件
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
