package CollisionDetection;


import GameException.GameException;
import GameTools.Vector2;

/**
 * 分离轴 判断 处理 碰撞
 *
 * @author ZYM
 */
public class SAT {

    /**
     * 判定两个凸多边形是否碰撞
     *
     * @param a   多边形1顶点数组，边为点顺序相连
     * @param b   多边形2顶点数组，边为点顺序相连
     * @param MTD 碰撞后需要分离的量
     * @return 是否碰撞
     * @throws GameException 游戏错误
     */
    public static boolean Intersect(Shape a, Shape b, Vector2 MTD) throws GameException {

		/* 碰撞后重合最小分离量 */
        float min = Float.MAX_VALUE;

		/* 碰撞后重合分离向量 */
        Vector2 push_v = null;

//		Debug.DrawString("a",a.vertices[0].add(new Vector2(-0.5,0.5)));
//		Debug.DrawString("b",b.vertices[0].add(new Vector2(-0.5,0.5)));

		/* 依次遍历图形边，计算投影轴，并计算所有点在投影轴上的投影 */
        for (int i = 0; i < a.vertices.length; i++) {
            Vector2 axis = a.edges[i].direction;

			/* 计算第i条边的法线（投影轴） */
            axis = axis.perp();

//			Debug.DrawLine(a.vertices[i], a.vertices[i].add(axis),Color.RED);
//			Debug.DrawString(i+"",a.vertices[i]);


			/* 计算图形a各顶点在投影轴上的投影 */
            float[] a_ = project(a, axis);
			/* 计算图形b各顶点在投影轴上的投影 */
            float[] b_ = project(b, axis);

			
			/* 计算投影的重合长度 */
            float t = overlap(a_, b_);

//			Debug.DrawString(a_[0]+" "+a_[1]+" "+b_[0]+" "+b_[1], 50, 190+i*20);

//			Debug.DrawString((int)(t*100)/100.0+"",a.vertices[i].add(new Vector2(0,0.5)));

            // 如果不重合，则未发生碰撞
            if (t <= 0) {
                return false;
            } else {

				/* 如果重合，找出最小重合长度和方向 */
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

            // 如果不重合，则未发生碰撞
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
     * 判断线段重合长度，不重合返回-1
     *
     * @param a_ 线段a 包含最小点与最大点
     * @param b_ 线段b 包含最小点与最大点
     * @return 返回一个值，表示线段重合部分的长度，如果不重合则返回-1
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
     * 判断范围中是否包含一个点
     *
     * @param n     点的值
     * @param range 包含最小点与最大点的范围
     * @return 返回是否包含点
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
     * 求形状在一条线上的投影的最小值和最大值
     *
     * @param shape 形状
     * @param axis  轴
     * @return 投影的最小值和最大值
     */
    public static float[] project(Shape shape, Vector2 axis) {
        float[] returnValue = null;

        axis = axis.normalized();

        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

		/* 将多边形各顶点依次投影到轴上，并找到最小、最大值 */
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
