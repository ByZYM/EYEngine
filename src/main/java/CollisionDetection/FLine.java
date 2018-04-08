package CollisionDetection;

import GameTools.Vector2;

/**
 * ����ֱ�� (�ѿ���ϵ) ����y=kx+b
 *
 * @author ZYM
 */
public class FLine {
    /**
     * ֱ������
     *
     * @author ZYM
     */
    public static enum LineType {
        /**
         * һ��ֱ��
         */
        General,

        /**
         * Yʼ��Ϊ0 (��Y��ƽ��ֱ��)
         */
        ParallelY
    }

    ;

    /**
     * ֱ��Kֵ
     */
    public float K;

    /**
     * ֱ��Bֵ
     */
    public float B;

    /**
     * ���Ϊ��Y��ƽ��ֱ�� ����ʽΪ x=X XΪ��ֵ
     */
    public float X;

    /**
     * ֱ������
     */
    public LineType type;

    /**
     * ��֪K��B ���캯��ֱ��
     *
     * @param type ֱ������
     * @param K    ���Ϊһ��ֱ�ߣ������ֱ��б�ʣ����Ϊ��Y��ƽ��ֱ�ߣ������Xֵ
     * @param B    ֱ����Y�ύ��
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
     * ��֪K����ֱ��һ�� ���캯��ֱ�� һ��ֱ��
     *
     * @param K     ����ֱ��б��
     * @param point ��ֱ��һ��
     */
    public FLine(float K, Vector2 point) {
        this.K = K;
        this.B = point.getY() - point.getX() * K;
    }

    /**
     * ����һ�㺯��ֵ
     *
     * @param x �����Ա���
     * @return ���������
     */
    public float Calc(float x) {
        return K * x + B;
    }

    /**
     * �ж�һ�����Ƿ���ֱ���ϣ���Χ 1e-6
     *
     * @param point �жϵ�{@link Vector2}
     * @return �Ƿ���ֱ����
     */
    public boolean IsPointOnLine(Vector2 point) {
        float error = 1e-6f;
        boolean returnValue = false;
        switch (type) {
            case General:
            /* ������㺯������ֱ���� */
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
//	 * �����ֱ���ϵ�ͶӰ��
//	 * 
//	 * @param point
//	 *            Ҫ��ĵ�
//	 * @return ͶӰ��
//	 */
//	public Vector2 GetProjection(Vector2 point) {
//		return point;
//	}

    /**
     * ��ֱ����ֱ�ߵĽ���
     *
     * @param other ��һ��ֱ��
     * @return �н��㷵�ؽ��㣬û�н��㷵��null
     */
    public Vector2 GetInsection(FLine other) {

        Vector2 returnValue = null;
        float x = 0;
        float y = 0;

        switch (type) {
            case General:
                if (other.type == LineType.General) {
                    // �����ƽ��
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
