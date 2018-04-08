package CollisionDetection;

import GameTools.Vector2;

/**
 * 射线类
 *
 * @author ZYM
 */
public class Ray {

    /**
     * 射线起始点
     */
    public Vector2 start;

    /**
     * 射线方向
     */
    public Vector2 direction;

    public Ray(Vector2 start, Vector2 direction) {
        this.start = start;
        this.direction = direction.normalized();
    }



    /**
     * 判断与线段是否相交
     *
     * @param other   判断线段
     * @param hitInfo 回调碰撞信息 包含碰撞点、法线
     * @return 判断是否相交
     */
    public boolean Intersect(final Segment other, final RayHitInfo hitInfo) {
        // 解出T1！
        float T1 = (direction.x * (other.start.y - start.y) + direction.y * (start.x - other.start.x)) / (other.direction.x * direction.y - other.direction.y * direction.x);

        // 代入T1，解出T
        float T = (other.start.x + other.direction.x * T1 - start.x) / direction.x;

        /* 相交 */
        if(T>0 && T1>=0 && T1<=other.getLength()){
            Vector2 pos=new Vector2(start.x+T*direction.x,start.y+T*direction.y);

            hitInfo.setPosition(pos);
            hitInfo.setPerp(other.direction.perp());

            return true;
        }

        return false;
    }
}
