package GameScript.Objects;

import GameConfig.gameConfig;
import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;

public class EmptyScript extends EngineBehavior implements gameConfig {

    public boolean move = true;

    public EmptyScript(GameObject go) {
        super(go);
    }

    @Override
    public void Update() throws GameException {
        /* transform_world.setPosition(Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition)); */
        move = true;
    }

    @Override
    public void FixedUpdate() throws GameException {
        /*
        // Êó±ê×ó¼ü°´ÏÂ
        if (Input.GetMouseDown(MouseButton.PRIMARY.ordinal())) {
        Instantiate(Scene.class,
        Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition));
        }
        */
    }

    @Override
    public void OnTriggerEnter(GameObject other) {
        if (other.Name != getGameobject().Parent.Name && other.GetComponent(EmptyScript.class) == null) {
            move = false;
        }
    }
}
