package GameTools.Math;

import GameException.GameException;

public class MathE {

    private MathE() {
    }

    /**
     * 开方
     *
     * @param num 要开方的数
     * @return 开放结果
     */
    public static float sqrt(float num) {
        return (float) Math.sqrt(num);
    }

    /**
     * 数的乘方
     *
     * @param num  数
     * @param num2 幂
     * @return 数的幂次方
     */
    public static float pow(float num, float num2) {
        return (float) Math.pow(num, num2);
    }

    /**
     * 限制
     *
     * @param value 要限制的值
     * @param min   最小值
     * @param max   最大值
     * @return 限制value的值在min和max之间， 如果value小于min，返回min。 如果value大于max，返回max，否则返回value
     * @throws GameException Clamp错误
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
