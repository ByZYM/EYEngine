package GameComponent;


import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Debug;
import GameTools.Screen;

/**
 * ���Դ��
 *
 * @author ZYM
 */
public class Light extends EngineBehavior {

    /**
     * �����ʼ��
     *
     * @param go �����������
     */
    public Light(GameObject go) {
        super(go);
    }

    @Override
    public void Update() throws GameException {
//
//        float maxDistance = 400;
//
////        final Vector2 LightSource =transform_world.getPosition();
//        final Vector2 LightSource = Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition);
//
//        ArrayList<Vector2> LightPoints = new ArrayList<>();
//
//        for (GameObject gBuffer : GameObject.GetAllObjectsCollection()) {
//            Collider c =
//                    ((Collider) gBuffer.GetComponent(Collider.class));
//            if (c != null) {
//                Shape s = c.getWorldShape();
//                for (Vector2 v : s.vertices) {
//                    Vector2 dir = v.sub(LightSource);
//
//                    Ray r = new Ray(LightSource, dir);
//                    Ray r1 = new Ray(LightSource, dir.rotate(-0.01f));
//                    Ray r2 = new Ray(LightSource, dir.rotate(0.01f));
//                    RayHitInfo hit = new RayHitInfo();
//
//                    if (Physics.RayCast(r, hit, maxDistance)) {
//                        LightPoints.add(hit.getPosition().clone());
//                    } else {
//                        //������󳤶ȵĵ�
//                        LightPoints.add(r.start.add(r.direction.multi(maxDistance)));
//                    }
//
//                    if (Physics.RayCast(r1, hit, maxDistance)) {
//                        LightPoints.add(hit.getPosition().clone());
//                    } else {
//                        //������󳤶ȵĵ�
//                        LightPoints.add(r1.start.add(r1.direction.multi(maxDistance)));
//                    }
//
//                    if (Physics.RayCast(r2, hit, maxDistance)) {
//                        LightPoints.add(hit.getPosition().clone());
//                    } else {
//                        //������󳤶ȵĵ�
//                        LightPoints.add(r2.start.add(r2.direction.multi(maxDistance)));
//                    }
//
//                }
//            }
//        }
//
//        //���սǶ�˳������
//        LightPoints.sort((pointA, pointB) -> {
//            float AngleA = Vector2.RelativeAngle(LightSource, pointA);
//            float AngleB = Vector2.RelativeAngle(LightSource, pointB);
//            if (AngleA > AngleB) return 1;
//            if (AngleA < AngleB) return -1;
//            return 0;
//        });
//
//        for (int i = 0; i < LightPoints.size() - 1; i++) {
//            Shape s = new Shape(LightSource, LightPoints.get(i + 1), LightPoints.get(i));
//            Debug.FillShape(s);
//        }
//
//        if (LightPoints.size() > 0 ) {
//            Shape s = new Shape(LightSource, LightPoints.get(LightPoints.size() - 1), LightPoints.get(0));
//            Debug.FillShape(s);
//        }

        Debug.DrawString("GUI", Screen.Width/2, Screen.Height/2);


    }

}