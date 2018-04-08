package GameTools;

import GameTools.Math.MathE;

/**
 * 二维向量
 *
 * @author ZYM
 */
public class Vector2 {

    public float x;

    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * 计算线段end-start的角度
     * @param start 起始点
     * @param end 终止点
     * @return 弧度角
     */
    public static float RelativeAngle(Vector2 start,Vector2 end){
        Vector2 t=end.sub(start);
        return MathE.atan2(t.y, t.x);
    }

    /**
     * 将向量沿着原点旋转一定弧度
     * @param angle
     * @return
     */
    public Vector2 rotate(float angle){
        float tx=x*MathE.cos(angle)-y*MathE.sin(angle);
        float ty=x*MathE.sin(angle)+y*MathE.cos(angle);
        return new Vector2(tx,ty);
    }


    /**
     * 将向量沿着原点旋转一定弧度
     * @param angle
     * @return
     */
    public void rotateLocal(float angle){
        this.x=x*MathE.cos(angle)-y*MathE.sin(angle);
        this.y=x*MathE.sin(angle)+y*MathE.cos(angle);
    }


    /**
     * 向量相加
     *
     * @param v 加上的向量
     * @return Vector2 新向量
     */
    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    /**
     * 向量相减
     *
     * @param v 减去向量
     * @return Vector2 新向量
     */
    public Vector2 sub(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    /**
     * 向量的点乘
     *
     * @param v 另一个向量
     * @return 向量点乘
     */
    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    /**
     * 向量的数乘
     *
     * @param d 数值
     * @return Vector2 新向量
     */
    public Vector2 multi(float d) {
        return new Vector2(x * d, y * d);
    }

    /**
     * 向量除以实数
     *
     * @param d 数值
     * @return Vector2
     */
    public Vector2 div(float d) {
        return new Vector2(x / d, y / d);
    }

    /**
     * 向量相加
     *
     * @param v 加上的向量
     * @return Vector2当前操作向量
     */
    public Vector2 addLocal(Vector2 v) {
        x = x + v.x;
        y = y + v.y;
        return this;
    }

    /**
     * 向量相减
     *
     * @param v 减去向量
     * @return Vector2当前操作向量
     */
    public Vector2 subLocal(Vector2 v) {
        x = x - v.x;
        y = y - v.y;
        return this;
    }

    /**
     * 向量的数乘
     *
     * @param d 数值
     * @return Vector2当前操作向量
     */
    public Vector2 multiLocal(float d) {
        x *= d;
        y *= d;
        return this;
    }

    /**
     * 向量除以实数
     *
     * @param d 数值
     * @return Vector2当前操作向量
     */
    public Vector2 divLocal(float d) {
        x /= d;
        y /= d;
        return this;
    }

    /**
     * 归一化向量，返回长度为1的向量
     *
     * @return 长度为1的向量
     */
    public Vector2 normalized() {
        float mag =  MathE.sqrt(x * x + y * y);
        return new Vector2(x / mag, y / mag);
    }

    /**
     * 求向量的一个法向量 (初始坐标为原点)
     *
     * @return 返回一个法向量 (初始坐标为原点)
     */
    public Vector2 perp() {
        return new Vector2(-y, x);
    }

    /**
     * 获取向量的模长
     *
     * @return 向量模长
     */
    public float getLength() {
        return MathE.sqrt(MathE.pow(x, 2) + MathE.pow(y, 2));
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public final Vector2 clone() {
        return new Vector2(x, y);
    }

    public void set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Float.floatToIntBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Float.floatToIntBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector2))
            return false;
        Vector2 other = (Vector2) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }
}
