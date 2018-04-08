package GameTools.Math;

import GameException.GameException;

public class MathE {

    private MathE() {
    }

    /**
     * ����
     *
     * @param num Ҫ��������
     * @return ���Ž��
     */
    public static float sqrt(float num) {
        return (float) Math.sqrt(num);
    }

    /**
     * ���ĳ˷�
     *
     * @param num  ��
     * @param num2 ��
     * @return �����ݴη�
     */
    public static float pow(float num, float num2) {
        return (float) Math.pow(num, num2);
    }

    /**
     * ����
     *
     * @param value Ҫ���Ƶ�ֵ
     * @param min   ��Сֵ
     * @param max   ���ֵ
     * @return ����value��ֵ��min��max֮�䣬 ���valueС��min������min�� ���value����max������max�����򷵻�value
     * @throws GameException Clamp����
     */
    public static float clamp(float value, float min, float max) throws GameException {
        if (min > max) {
            float temp = min;
            min = max;
            max = temp;
        }
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }


    public static float cos(float x) {
        return (float) Math.cos(x);
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }
}
