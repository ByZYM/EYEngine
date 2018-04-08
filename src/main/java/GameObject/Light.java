package GameObject;

import GameModel.Model;

public class Light extends GameObject {

    public Light(GameObject Parent, float worldX, float worldY) {
        super(Parent);
        this.setModel(new Model(this, 0, 0, 0, worldX, worldY));

//        AddComponent(new GameComponent.Light(this));
    }
}
