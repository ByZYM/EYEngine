package GameScript.Objects;


import GameConfig.gameConfig;
import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;

/**
 * Ð¡Çò½Å±¾
 */
public class SmallBallScript extends EngineBehavior implements gameConfig {

    public SmallBallScript(GameObject go) {
        super(go);
    }

    @Override
    public void Update() throws GameException {

//		if (Input.GetKeyDown(KeyEvent.VK_G)) {
//			((RigidBody)GetComponent(RigidBody.class)).setUseGravity(!((RigidBody)GetComponent(RigidBody.class)).isUseGravity());
//		}

//		Debug.DrawString(((RigidBody)GetComponent(RigidBody.class)).isUseGravity(), 50, 210);
    }

    @Override
    public void FixedUpdate() {
        // transform.translate(new Vector2(0, -0.2));
    }
}
