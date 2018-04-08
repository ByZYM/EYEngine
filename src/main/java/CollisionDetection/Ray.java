package CollisionDetection;

import GameTools.Vector2;

/**
 * ������
 *
 * @author ZYM
 */
public class Ray {

    /**
     * ������ʼ��
     */
    public Vector2 start;

    /**
     * ���߷���
     */
    public Vector2 direction;

    public Ray(Vector2 start, Vector2 direction) {
        this.start = start;
        this.direction = direction.normalized();
    }



    /**
     * �ж����߶��Ƿ��ཻ
     *
     * @param other   �ж��߶�
     * @param hitInfo �ص���ײ��Ϣ ������ײ�㡢����
     * @return �ж��Ƿ��ཻ
     */
    public boolean Intersect(final Segment other, final RayHitInfo hitInfo) {
        // ���T1��
        float T1 = (direction.x * (other.start.y - start.y) + direction.y * (start.x - other.start.x)) / (other.direction.x * direction.y - other.direction.y * direction.x);

        // ����T1�����T
        float T = (other.start.x + other.direction.x * T1 - start.x) / direction.x;

        /* �ཻ */
        if(T>0 && T1>=0 && T1<=other.getLength()){
            Vector2 pos=new Vector2(start.x+T*direction.x,start.y+T*direction.y);

            hitInfo.setPosition(pos);
            hitInfo.setPerp(other.direction.perp());

            return true;
        }

        return false;
    }
}
