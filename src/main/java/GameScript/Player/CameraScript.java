package GameScript.Player;

import GameComponent.Animator;
import GameException.GameException;
import GameTools.GameInput.Input;
import GameObject.Camera;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Math.MathE;
import GameTools.Vector2;

public class CameraScript extends EngineBehavior {

    Animator animator;

    /**
     * Player�ű����
     *
     * @param go ������Ϸ����
     */
    public CameraScript(GameObject go) {
        super(go);
    }

    @Override
    public void Start() throws GameException {

    }

    @Override
    public void Update() throws GameException {

        /* �������λ�� */
        Vector2 MouseWorldPosition=Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition);

        Vector2 CameraPos=Camera.GetCurrentCamera().getModel().transform_world.getPosition();
        /* ����ԭ��Ϊ�����λ�õ����������� */
        Vector2 MousePos=MouseWorldPosition.sub(CameraPos);


        /* ���뾶 m */
        float r=1.5f;
        Vector2 NewCameraPos=new Vector2();

        if (MathE.pow(MousePos.x, 2) + MathE.pow(MousePos.y, 2) > MathE.pow(r, 2)) {
            //����Ϊ (x1,y1)����Բ�����ߵĽ�������(x,y)
            //r^2*(x1^2/(x1^2+y1^2))=x^2
            //r^2-x^2=y^2

            float tempX = MousePos.x;
            float tempY = MousePos.y;

            if (tempX > 0) {
                tempX = MathE.sqrt(MathE.pow(r, 2) * (MathE.pow(tempX, 2) / (MathE.pow(tempX, 2) + MathE.pow(tempY, 2))));
            }
            else if (tempX < 0) {
                tempX = -MathE.sqrt(MathE.pow(r, 2) * (MathE.pow(tempX, 2) / (MathE.pow(tempX, 2) + MathE.pow(tempY, 2))));
            }

            if (tempY > 0) {
                tempY = (MathE.sqrt(MathE.pow(r, 2) - MathE.pow(tempX, 2)));

            }
            else if (tempY < 0) {
                tempY = -(MathE.sqrt(MathE.pow(r, 2) - MathE.pow(tempX, 2)));
            }

            NewCameraPos.setX(tempX);
            NewCameraPos.setY(tempY);
        }else{
            NewCameraPos.set(MousePos);
        }

        CameraPos.set(NewCameraPos.add(transform_world.getPosition()));

    }

}
