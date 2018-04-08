package GameScript.Player;

import GameComponent.Collider;
import GameException.GameException;
import GameObject.Camera;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameScript.Objects.DropItemScript;
import GameTools.Debug;
import GameTools.GameInput.Input;
import GameTools.Screen;
import GameTools.Time;
import GameTools.Vector2;

import java.util.ArrayList;

public class MouseScript extends EngineBehavior {

    float lastFired=0;

    /**
     * Player脚本组件
     *
     * @param go 挂载游戏物体
     */
    public MouseScript(GameObject go) {
        super(go);
    }

    @Override
    public void Start() throws GameException {
    }

    /* 每秒开枪速率 */
    public float fireRate=3.0f;

    @Override
    public void Update() throws GameException {
        /* 查找鼠标指向的物体 */
        ArrayList<Collider> out = new ArrayList<>();

        Vector2 MouseWorldPosition = Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition);

        Collider.bp.Pick(MouseWorldPosition, out);

        if (out.size() == 1) {

            GameObject go = out.get(0).getGameobject();



            DropItemScript dis = (DropItemScript) go.GetComponent(DropItemScript.class);

            /* 如果鼠标指向掉落物 */
            if (dis != null) {
                Debug.DrawString("[E] "+go.GetName(), Screen.Width / 2, Screen.Height / 2);
            }else{
                Debug.DrawString(go.GetName(), Screen.Width / 2, Screen.Height / 2);
            }
        }

        /* 鼠标左键 射击 */
        if(Input.GetMouseDown(1)){

            //首次攻击
            if (lastFired==0) {
                lastFired = -1 / fireRate;
            }

            if (Time.Time - lastFired > 1 / fireRate) {
                lastFired = Time.Time;
                Debug.DrawLine(transform_world.getPosition(),MouseWorldPosition);
            }
        }

//        if(Input.GetMouseDown(MouseEvent.BUTTON1)){
//            Instantiate(Stone.class, null,MouseWorldPosition.x, MouseWorldPosition.y);
//        }

        /* 绘制鼠标 */
        Debug.DrawCircle(1, (int) Input.MousePosition.x, Screen.Height - (int) Input.MousePosition.y);

    }

}
