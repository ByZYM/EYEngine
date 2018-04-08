package GameTools.Animation;

import java.util.HashMap;
import java.util.HashSet;

import GameException.GameException;
import GameTools.Compare;

/**
 * ����������������״̬�л���
 *
 * @author ZYM
 */

public class AnimationController {

    /**
     * ȫ������Ƭ��
     */
    public HashMap<String, Animation> anims = new HashMap<>();

    /**
     * ȫ����������
     */
    public HashSet<TransitionCondition> conditions = new HashSet<>();

    /**
     * ����״̬��
     */
    private HashMap<String, Boolean> BoolState = new HashMap<>();

    /**
     * Double״̬��
     */
    private HashMap<String, Double> DoubleState = new HashMap<>();

    /**
     * Integer״̬��
     */
    private HashMap<String, Integer> IntState = new HashMap<>();

    /**
     * ��ǰ����Ƭ��
     */
    public Animation CurrentAnimtion;

    /**
     * ���ö���Ƭ��
     *
     * @param anim ����Ƭ������
     */
    public void SetAnimations(Animation... anim) {
        for (Animation a : anim) {
            anims.put(a.getAnimationName(), a);
        }
        CurrentAnimtion = anim[0];
    }

    /**
     * ���ݶ������֣���ȡ����Ƭ��
     *
     * @param AnimationName ��������
     * @return ����{@link Animation}
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
     * �ж϶������ɣ��л�����״̬
     */
    public void TransitionCompare() {
        // �Ƚ����й�������
        for (TransitionCondition c : conditions) {
            /* ������������ */
            if (c.Animation1 == GetCurrentAnimation() && c.IsTrue()) {
                // ����������������ɶ���
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

    /* �������� */
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
                throw new GameException("����������ǰ�ö�������ɶ���������");
            }

        }

        /* �жϹ��������Ƿ���� */
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
