package CollisionDetection;


import GameException.GameException;
import GameTools.Vector2;

/**
 * ������ �ж� ���� ��ײ
 *
 * @author ZYM
 */
public class SAT {

    /**
     * �ж�����͹������Ƿ���ײ
     *
     * @param a   �����1�������飬��Ϊ��˳������
     * @param b   �����2�������飬��Ϊ��˳������
     * @param MTD ��ײ����Ҫ�������
     * @return �Ƿ���ײ
     * @throws GameException ��Ϸ����
     */
    public static boolean Intersect(Shape a, Shape b, Vector2 MTD) throws GameException {

		/* ��ײ���غ���С������ */
        float min = Float.MAX_VALUE;

		/* ��ײ���غϷ������� */
        Vector2 push_v = null;

//		Debug.DrawString("a",a.vertices[0].add(new Vector2(-0.5,0.5)));
//		Debug.DrawString("b",b.vertices[0].add(new Vector2(-0.5,0.5)));

		/* ���α���ͼ�αߣ�����ͶӰ�ᣬ���������е���ͶӰ���ϵ�ͶӰ */
        for (int i = 0; i < a.vertices.length; i++) {
            Vector2 axis = a.edges[i].direction;

			/* �����i���ߵķ��ߣ�ͶӰ�ᣩ */
            axis = axis.perp();

//			Debug.DrawLine(a.vertices[i], a.vertices[i].add(axis),Color.RED);
//			Debug.DrawString(i+"",a.vertices[i]);


			/* ����ͼ��a��������ͶӰ���ϵ�ͶӰ */
            float[] a_ = project(a, axis);
			/* ����ͼ��b��������ͶӰ���ϵ�ͶӰ */
            float[] b_ = project(b, axis);

			
			/* ����ͶӰ���غϳ��� */
            float t = overlap(a_, b_);

//			Debug.DrawString(a_[0]+" "+a_[1]+" "+b_[0]+" "+b_[1], 50, 190+i*20);

//			Debug.DrawString((int)(t*100)/100.0+"",a.vertices[i].add(new Vector2(0,0.5)));

            // ������غϣ���δ������ײ
            if (t <= 0) {
                return false;
            } else {

				/* ����غϣ��ҳ���С�غϳ��Ⱥͷ��� */
                if (t < min) {
                    min = t;
                    push_v = axis.normalized();
                }
            }
        }

        for (int i = 0; i < b.vertices.length; i++) {
            Vector2 axis = b.edges[i].direction;
            axis = axis.perp();

//			Debug.DrawLine(b.vertices[i], b.vertices[i].add(axis),Color.RED);
//			Debug.DrawString(i+"",a.vertices[i]);
            float[] a_ = project(a, axis), b_ = project(b, axis);

            float t = overlap(a_, b_);
//			Debug.DrawString((int)(t*100)/100.0+"",b.vertices[i].add(new Vector2(0,0.5)));

            // ������غϣ���δ������ײ
            if (t <= 0) {
                return false;
            } else {
                if (t < min) {
                    min = t;
                    push_v = axis.normalized();
                }
            }
        }

        if (push_v != null) {
            MTD.setX(push_v.getX() * min);
            MTD.setY(push_v.getY() * min);

//			Debug.DrawString(MTD+" "+push_v, a.vertices[0].add(new Vector2(0, 1)));
        }

        return true;
    }

    /**
     * �ж��߶��غϳ��ȣ����غϷ���-1
     *
     * @param a_ �߶�a ������С��������
     * @param b_ �߶�b ������С��������
     * @return ����һ��ֵ����ʾ�߶��غϲ��ֵĳ��ȣ�������غ��򷵻�-1
     */
    private static float overlap(float[] a_, float[] b_) {
        if (contains(a_[0], b_) && contains(a_[1], b_))
            return b_[1] - a_[0] < a_[1] - b_[0] ? b_[1] - a_[0] : a_[1] - b_[0];

        if (contains(a_[0], b_) && !contains(a_[1], b_))
            return b_[1] - a_[0];
        if (contains(a_[1], b_) && !contains(a_[0], b_))
            return a_[1] - b_[0];

        if (contains(b_[0], a_) && contains(b_[1], a_))
            return a_[1] - b_[0] < b_[1] - a_[0] ? a_[1] - b_[0] : b_[1] - a_[0];

        if (contains(b_[0], a_) && !contains(b_[1], a_))
            return a_[1] - b_[0];
        if (contains(b_[1], a_) && !contains(b_[0], a_))
            return b_[1] - a_[0];

        return -1;

    }

    /**
     * �жϷ�Χ���Ƿ����һ����
     *
     * @param n     ���ֵ
     * @param range ������С��������ķ�Χ
     * @return �����Ƿ������
     */
    private static boolean contains(float n, float[] range) {
        float a = range[0], b = range[1];

        if (b < a) {
            a = b;
            b = range[0];
        }
        return (n >= a && n <= b);
    }

    /**
     * ����״��һ�����ϵ�ͶӰ����Сֵ�����ֵ
     *
     * @param shape ��״
     * @param axis  ��
     * @return ͶӰ����Сֵ�����ֵ
     */
    public static float[] project(Shape shape, Vector2 axis) {
        float[] returnValue = null;

        axis = axis.normalized();

        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

		/* ������θ���������ͶӰ�����ϣ����ҵ���С�����ֵ */
        for (int i = 0; i < shape.vertices.length; i++) {
            float proj = shape.vertices[i].dot(axis);
            if (proj < min)
                min = proj;
            if (proj > max)
                max = proj;
        }

        returnValue = new float[]{min, max};

        return returnValue;
    }

}
