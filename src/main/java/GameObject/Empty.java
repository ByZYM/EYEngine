package GameObject;

import GameModel.Model;

public class Empty extends GameObject {

    public Empty(GameObject Parent, float worldX, float worldY) {
        super(Parent);
        this.setModel(new Model(this, 0, 0, 0, worldX, worldY));

    }
}
