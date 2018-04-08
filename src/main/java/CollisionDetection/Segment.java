package CollisionDetection;

import GameTools.Vector2;

/**
 * �߶���
 *
 * @author ZYM
 */
public class Segment extends  Ray{


    /**
     * �߶ν�����
     */
    public Vector2 end;

    public Segment(Vector2 start, Vector2 end) {
        super(start,end.sub(start));
        this.end = end;
    }

    public float getLength(){
        return end.sub(start).getLength();
    }

}
