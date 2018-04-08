package GameObject;

import CollisionDetection.Shape;
import GameComponent.Collider;
import GameModel.Model;
import GameTools.Vector2;

public class Scene extends GameObject {

    public Scene(GameObject Parent, float worldX, float worldY) {
        super(Parent);
        this.setModel(new Model(this, 0, 0, 0, worldX, worldY));

        Empty s1 = new Empty(this, -200, 200);
        Empty s2 = new Empty(this, 200, 200);
        Empty s3 = new Empty(this, 200, -200);
        Empty s4 = new Empty(this, -200, -200);

        s1.AddComponent(new Collider(s1));
        ((Collider) s1.GetComponent(Collider.class)).setLocalShape(new Shape(new Vector2(0, 0)));

        s2.AddComponent(new Collider(s2));
        ((Collider) s2.GetComponent(Collider.class)).setLocalShape(new Shape(new Vector2(0, 0)));

        s3.AddComponent(new Collider(s3));
        ((Collider) s3.GetComponent(Collider.class)).setLocalShape(new Shape(new Vector2(0, 0)));

        s4.AddComponent(new Collider(s4));
        ((Collider) s4.GetComponent(Collider.class)).setLocalShape(new Shape(new Vector2(0, 0)));
    }

}
