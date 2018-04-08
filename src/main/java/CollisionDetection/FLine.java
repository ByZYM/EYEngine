package CollisionDetection;

import GameTools.Vector2;

/**
 * 函数直线 (笛卡尔系) 形如y=kx+b
 *
 * @author ZYM
 */
public class FLine {
    /**
     * 直线类型
     *
     * @author ZYM
     */
    public static enum LineType {
        /**
         * 一般直线
         */
        General,

        /**
         * Y始终为0 (与Y轴平行直线)
         */
        ParallelY
    }

    ;

    /**
     * 直线K值
     */
    public float K;

    /**
     * 直线B值
     */
    public float B;

    /**
     * 如果为与Y轴平行直线 函数式为 x=X X为定值
     */
    public float X;

    /**
     * 直线类型
     */
    public LineType type;

    /**
     * 已知K、B 构造函数直线
     *
     * @param type 直线类型
     * @param K    如果为一般直线，则代表直线斜率，如果为与Y轴平行直线，则代表X值
     * @param B    直线与Y轴交点
     */
    public FLine(LineType type, float K, float B) {
        switch (type) {
            case General:
                this.type = type;
                this.K = K;
                this.B = B;
                break;
            case ParallelY:
                this.type = type;
                this.X = K;
                break;
            default:
                break;
        }
    }

    /**
     * 已知K及过直线一点 构造函数直线 一般直线
     *
     * @param K     代表直线斜率
     * @param point 过直线一点
     */
    public FLine(float K, Vector2 point) {
        this.K = K;
        this.B = point.getY() - point.getX() * K;
    }

    /**
     * 计算一般函数值
     *
     * @param x 函数自变量
     * @return 函数因变量
     */
    public float Calc(float x) {
        return K * x + B;
    }

    /**
     * 判断一个点是否在直线上，误差范围 1e-6
     *
     * @param point 判断点{@link Vector2}
     * @return 是否在直线上
     */
    public boolean IsPointOnLine(Vector2 point) {
        float error = 1e-6f;
        boolean returnValue = false;
        switch (type) {
            case General:
            /* 如果满足函数则在直线上 */
                if (point.getX() * K + B - point.getY() <= error) {
                    returnValue = true;
                }
                break;
            case ParallelY:
                if (point.getX() == X) {
                    returnValue = true;
                }
                break;
            default:
                break;
        }
        return returnValue;
    }

//	/**
//	 * 求点在直线上的投影点
//	 * 
//	 * @param point
//	 *            要求的点
//	 * @return 投影点
//	 */
//	public Vector2 GetProjection(Vector2 point) {
//		return point;
//	}

    /**
     * 求直线与直线的交点
     *
     * @param other 另一条直线
     * @return 有交点返回交点，没有交点返回null
     */
    public Vector2 GetInsection(FLine other) {

        Vector2 returnValue = null;
        float x = 0;
        float y = 0;

        switch (type) {
            case General:
                if (other.type == LineType.General) {
                    // 如果不平行
                    if (K != other.K) {
                        x = (other.B - B) / (K - other.K);
                        y = Calc(x);
                        returnValue = new Vector2(x, y);
                    }
                } else if (other.type == LineType.ParallelY) {
                    x = other.X;
                    y = Calc(x);
                    returnValue = new Vector2(x, y);
                }
                break;
            case ParallelY:
                if (other.type == LineType.General) {
                    x = X;
                    y = other.Calc(x);
                    returnValue = new Vector2(x, y);
                }
                break;
            default:
                break;
        }
        return returnValue;
    }
}
