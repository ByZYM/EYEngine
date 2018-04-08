package GameGraphics;

import CollisionDetection.AABB;
import CollisionDetection.Shape;
import GameException.GameException;
import GameObject.Camera;
import GameTools.Vector2;

import java.awt.*;
import java.awt.image.ImageObserver;

public class IDrawImpl implements IDrawE {


    /**
     * 由第一次绘图时初始化 {@link GameGraphics.GameFrame#gBuffer}
     */
    public Graphics2D gBuffer = null;

//    private final CopyOnWriteArrayList<IDraw> DRAW_LIST = new CopyOnWriteArrayList<>();


    interface IDraw {

        void draw() throws GameException;

    }

    @Override
    public void SetGBuffer(Graphics2D gBuffer) {
        this.gBuffer = gBuffer;
    }

    @Override
    public Graphics2D GetGBuffer() {
        return gBuffer;
    }

    public final void ClearList() {
//        DRAW_LIST.clear();
    }

    public final void DrawList() throws GameException {
////        CopyOnWriteArrayList<IDraw> ii = (CopyOnWriteArrayList<IDraw>)DRAW_LIST.clone();
//
////        if (ii.size() == 0) {
//        Iterator<IDraw> it = DRAW_LIST.iterator();
//        while (it.hasNext()) {
//            IDraw d = it.next();
//            if (d != null) {
//                d.draw();
//            }
//        }
////            tempList.clear();
//        return;
////        }
//
////        tempList = (CopyOnWriteArrayList)ii.clone();
////        Iterator<IDraw> it = ii.iterator();
////        while (it.hasNext()) {
////            IDraw d = it.next();
////            if (d != null) {
////                d.draw();
////            }
////        }
////        return;

    }

    public void DrawImage(Image img, int x, int y,
                          int width, int height,
                          ImageObserver observer) {
//        DRAW_LIST.add(() ->
        gBuffer.drawImage(img, x, y, width, height, observer);
//        );
    }

    public void DrawRect(int x, int y, int width, int height) {
//        DRAW_LIST.add(() -> {
        gBuffer.drawRect(x, y, width, height);
//        });
    }


    /* 填充实心shape */
    public void FillShape(CollisionDetection.Shape s) throws GameException {
//        DRAW_LIST.add(() -> {

        Polygon polygon = new Polygon();


        for (int i = 0; i < s.vertices.length; i++) {

            Vector2 WindowVertexPoint = Camera.GetCurrentCamera().WorldToScreenPoint(s.vertices[i]);
            polygon.addPoint((int) WindowVertexPoint.x, (int) (Camera.CameraWindowPos.getY() * 2 - WindowVertexPoint.y));
        }

        gBuffer.fillPolygon(polygon);
        gBuffer.drawPolygon(polygon);

//        }
//        );
    }

    /* 画空心shape */
    public void DrawShape(CollisionDetection.Shape s) throws GameException {

//        DRAW_LIST.add(() -> {
        for (int i = 0; i < s.edges.length; i++) {

            Vector2 WindowStartPoint = Camera.GetCurrentCamera().WorldToScreenPoint(s.edges[i].start);
            Vector2 WindowEndPoint = Camera.GetCurrentCamera().WorldToScreenPoint(s.edges[i].end);

            gBuffer.drawLine((int) WindowStartPoint.getX(),
                             (int) (Camera.CameraWindowPos.getY() * 2 - WindowStartPoint.getY()), (int) WindowEndPoint.getX(),
                             (int) (Camera.CameraWindowPos.getY() * 2 - WindowEndPoint.getY()));
        }

//        Polygon polygon=new Polygon();
//
//
//        for (int i = 0; i < s.vertices.length; i++) {
//
//            Vector2 WindowVerticePoint = Camera.GetCurrentCamera().WorldToScreenPoint(s.vertices[i]);
//            polygon.addPoint((int)WindowVerticePoint.x,(int) (Camera.CameraWindowPos.getY() * 2 - WindowVerticePoint.y));
//        }
//
//        gBuffer.drawPolygon(polygon);
//        });
    }

    public void DrawShape(Shape s, Color c) throws GameException {

//        DRAW_LIST.add(() -> {
        Color cc = gBuffer.getColor();
        gBuffer.setColor(c);

        for (int i = 0; i < s.edges.length; i++) {

            Vector2 WindowStartPoint = Camera.GetCurrentCamera().WorldToScreenPoint(s.edges[i].start);
            Vector2 WindowEndPoint = Camera.GetCurrentCamera().WorldToScreenPoint(s.edges[i].end);

            gBuffer.drawLine((int) WindowStartPoint.getX(),
                             (int) (Camera.CameraWindowPos.getY() * 2 - WindowStartPoint.getY()), (int) WindowEndPoint.getX(),
                             (int) (Camera.CameraWindowPos.getY() * 2 - WindowEndPoint.getY()));
        }

        gBuffer.setColor(cc);
//        });
    }

    public void DrawAABB(AABB aabb) throws GameException {
//        DRAW_LIST.add(() -> {
        Vector2 LeftBottom = Camera.GetCurrentCamera().WorldToScreenPoint(aabb.WorldLowerBound);
        Vector2 RightUp = Camera.GetCurrentCamera().WorldToScreenPoint(aabb.WorldUpperBound);

        Vector2 s = new Vector2(aabb.WorldLowerBound.x, aabb.WorldUpperBound.y);

        DrawString(aabb, s);

        gBuffer.drawRect((int) LeftBottom.x, (int) (Camera.CameraWindowPos.getY() * 2 - RightUp.y),
                         (int) (RightUp.x - LeftBottom.x), (int) (RightUp.y - LeftBottom.y));
//        });

    }

    public void DrawLine(Vector2 start, Vector2 end) throws GameException {
//        DRAW_LIST.add(() -> {
        Vector2 WindowStartPoint = Camera.GetCurrentCamera().WorldToScreenPoint(start);
        Vector2 WindowEndPoint = Camera.GetCurrentCamera().WorldToScreenPoint(end);

        gBuffer.drawLine((int) WindowStartPoint.getX(), (int) (Camera.CameraWindowPos.getY() * 2 - WindowStartPoint.getY()),
                         (int) WindowEndPoint.getX(), (int) (Camera.CameraWindowPos.getY() * 2 - WindowEndPoint.getY()));
//        });
    }

    public void DrawLine(Vector2 start, Vector2 end, Color c) throws GameException {
//        DRAW_LIST.add(() -> {
        Color cc = gBuffer.getColor();
        gBuffer.setColor(c);
        Vector2 WindowStartPoint = Camera.GetCurrentCamera().WorldToScreenPoint(start);
        Vector2 WindowEndPoint = Camera.GetCurrentCamera().WorldToScreenPoint(end);

        gBuffer.drawLine((int) WindowStartPoint.getX(), (int) (Camera.CameraWindowPos.getY() * 2 - WindowStartPoint.getY()),
                         (int) WindowEndPoint.getX(), (int) (Camera.CameraWindowPos.getY() * 2 - WindowEndPoint.getY()));
        gBuffer.setColor(cc);
//        });
    }

    /**
     * 在世界坐标上 绘制文本
     *
     * @param s 文本内容
     * @param v 世界坐标
     * @throws GameException 游戏错误
     */
    public void DrawString(Object s, Vector2 v) throws GameException {
//        DRAW_LIST.add(() -> {
        Vector2 WindowStartPoint = Camera.GetCurrentCamera().WorldToScreenPoint(v);

        gBuffer.drawString(String.valueOf(s),
                           (int) WindowStartPoint.getX(),
                           (int) (Camera.CameraWindowPos.getY() * 2 - WindowStartPoint.getY()));
//        });
    }

    /**
     * 在屏幕坐标上 绘制文本
     *
     * @param s 文本内容
     * @param x 屏幕坐标x
     * @param y 屏幕坐标y
     */
    public void DrawString(Object s, int x, int y) {
//        DRAW_LIST.add(() -> {
        gBuffer.drawString(String.valueOf(s), x, y);
//        });
    }

    /**
     * 在屏幕坐标上 绘制圆
     *
     * @param r 圆半径
     * @param x 屏幕坐标x
     * @param y 屏幕坐标y
     */
    @Override
    public void DrawCircle(int r, int x, int y) {
        gBuffer.drawOval(x,y,(r*2),(r*2));
    }
}
