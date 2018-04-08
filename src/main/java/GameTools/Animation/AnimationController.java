package GameTools.Animation;

import java.util.HashMap;
import java.util.HashSet;

import GameException.GameException;
import GameTools.Compare;

/**
 * 动画控制器（动画状态切换）
 *
 * @author ZYM
 */

public class AnimationController {

    /**
     * 全部动画片段
     */
    public HashMap<String, Animation> anims = new HashMap<>();

    /**
     * 全部过渡条件
     */
    public HashSet<TransitionCondition> conditions = new HashSet<>();

    /**
     * 布尔状态机
     */
    private HashMap<String, Boolean> BoolState = new HashMap<>();

    /**
     * Double状态机
     */
    private HashMap<String, Double> DoubleState = new HashMap<>();

    /**
     * Integer状态机
     */
    private HashMap<String, Integer> IntState = new HashMap<>();

    /**
     * 当前动画片段
     */
    public Animation CurrentAnimtion;

    /**
     * 设置动画片段
     *
     * @param anim 动画片段数组
     */
    public void SetAnimations(Animation... anim) {
        for (Animation a : anim) {
            anims.put(a.getAnimationName(), a);
        }
        CurrentAnimtion = anim[0];
    }

    /**
     * 根据动画名字，获取动画片段
     *
     * @param AnimationName 动画名称
     * @return 动画{@link Animation}
     */
    public Animation GetAnimation(String AnimationName) {
        return anims.get(AnimationName);
    }

    public void SetBool(String key, Boolean value) {
        BoolState.put(key, value);
    }

    public void SetDouble(String key, Double value) {
        DoubleState.put(key, value);
    }

    public void SetInt(String key, Integer value) {
        IntState.put(key, value);
    }

    public Boolean GetBool(String key) {
        return BoolState.get(key);
    }

    public Double GetDouble(String key) {
        return DoubleState.get(key);
    }

    public Integer GetInt(String key) {
        return IntState.get(key);
    }

    public Animation GetCurrentAnimation() {
        return CurrentAnimtion;
    }

    private void SetCurrentAnimation(Animation anim) {
        CurrentAnimtion = anim;
    }

    public void AddTransition(String State, Compare compare, String Value, String Animation1, String Animation2)
            throws GameException {
        conditions.add(new TransitionCondition(State, compare, Value, Animation1, Animation2));
    }

    /**
     * 判断动画过渡，切换动画状态
     */
    public void TransitionCompare() {
        // 比较所有过渡条件
        for (TransitionCondition c : conditions) {
            /* 过渡条件成立 */
            if (c.Animation1 == GetCurrentAnimation() && c.IsTrue()) {
                // 如果满足条件，过渡动画
                SetCurrentAnimation(c.Animation2);
            }
        }

    }

    interface compareable<T extends Comparable<T>> {
//		default int compare(T o1, T o2) {
//			return o1.compareTo(o2);
//		}

        int compareTo(T o1, T o2);
    }

    /* 过渡条件 */
    class TransitionCondition {
        public String StateKey;
        public Compare compare;
        public String Value;
        public Animation Animation1;
        public Animation Animation2;

        public TransitionCondition(String State, Compare compare, String Value, String Animation1, String Animation2)
                throws GameException {
            this.StateKey = State;
            this.compare = compare;
            this.Value = Value;
            this.Animation1 = GetAnimation(Animation1);
            this.Animation2 = GetAnimation(Animation2);

            if (Animation1 == null || Animation2 == null) {
                throw new GameException("过渡条件：前置动画或过渡动画不存在");
            }

        }

        /* 判断过渡条件是否成立 */
        public boolean IsTrue() {
            boolean returnValue = false;
            switch (compare) {
                case BoolEqual:
                    returnValue = GetBool(StateKey) == Boolean.valueOf(Value);
                    break;
                case BoolNotEqual:
                    returnValue = GetBool(StateKey) != Boolean.valueOf(Value);
                    break;
                case DoubleEqual:
                    returnValue = GetDouble(StateKey) == Double.valueOf(Value);
                    break;
                case DoubleGreaterEqualThan:
                    returnValue = GetDouble(StateKey) >= Double.valueOf(Value);
                    break;
                case DoubleGreaterThan:
                    returnValue = GetDouble(StateKey) > Double.valueOf(Value);
                    break;
                case DoubleLessEqualThan:
                    returnValue = GetDouble(StateKey) <= Double.valueOf(Value);
                    break;
                case DoubleLessThan:
                    returnValue = GetDouble(StateKey) < Double.valueOf(Value);
                    break;
                case IntEqualThan:
                    returnValue = GetInt(StateKey) == Integer.valueOf(Value);
                    break;
                case IntGreaterEqualThan:
                    returnValue = GetInt(StateKey) >= Integer.valueOf(Value);
                    break;
                case IntGreaterThan:
                    returnValue = GetInt(StateKey) > Integer.valueOf(Value);
                    break;
                case IntLessEqualThan:
                    returnValue = GetInt(StateKey) <= Integer.valueOf(Value);
                    break;
                case IntLessThan:
                    returnValue = GetInt(StateKey) < Integer.valueOf(Value);
                    break;
                default:
                    break;
            }
            return returnValue;
        }

    }

}
