package GameComponent;

import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Compare;
import GameTools.Animation.Animation;
import GameTools.Animation.AnimationController;

/**
 * 动画组件
 *
 * @author ZYM
 */
public class Animator extends EngineBehavior {

    /**
     * 动画控制器
     */
    private AnimationController AnimController = new AnimationController();


    public Animator(GameObject go) {
        super(go);
    }

    public void AddTransition(String State, Compare compare, String Value, String FromAnimation, String ToAnimation) throws GameException {
        AnimController.AddTransition(State, compare, Value, FromAnimation, ToAnimation);
    }

    public void SetAnimation(Animation... anim) {
        AnimController.SetAnimations(anim);
    }

    public void SetBool(String key, Boolean value) {
        AnimController.SetBool(key, value);
    }

    public void SetDouble(String key, Double value) {
        AnimController.SetDouble(key, value);
    }

    public void SetInt(String key, Integer value) {
        AnimController.SetInt(key, value);
    }

    public Boolean GetBool(String key) {
        return AnimController.GetBool(key);
    }

    public Double GetDouble(String key) {
        return AnimController.GetDouble(key);
    }

    public Integer GetInt(String key) {
        return AnimController.GetInt(key);
    }


    @Override
    public void Update() throws GameException {
        //运行状态机
        AnimController.TransitionCompare();
        //获取动画状态机当前动画
        getGameobject().getModel().setMap(AnimController.GetCurrentAnimation().run());
    }

}
