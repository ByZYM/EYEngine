package CollisionDetection;

import GameTools.Vector2;

/**
 * 线段类
 *
 * @author ZYM
 */
public class Segment extends  Ray{


    /**
     * 线段结束点
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
