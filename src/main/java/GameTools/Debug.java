package GameTools;

import CollisionDetection.AABB;
import CollisionDetection.Shape;
import EngineFactory.GraphicsFactory;
import GameException.GameException;

import java.awt.*;

/**
 * 游戏Debug系统
 *
 * @author ZYM
 */
public class Debug {

    /**
     * 是否Debug模式
     */
    public static boolean DEBUG = true;


    /* 填充实心shape */
    public static void FillShape(Shape s) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().FillShape(s);
        }
    }

    /* 画空心shape */
    public static void DrawShape(Shape s) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawShape(s);
        }
    }

    public static void DrawShape(Shape s, Color c) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawShape(s, c);
        }
    }

    public static void DrawAABB(AABB aabb) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawAABB(aabb);
        }
    }

    public static void DrawLine(Vector2 start, Vector2 end) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawLine(start, end);
        }
    }

    public static void DrawLine(Vector2 start, Vector2 end, Color c) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawLine(start, end, c);
        }
    }


    /**
     * 在屏幕坐标上 绘制圆
     *
     * @param r 圆半径
     * @param x 屏幕坐标x
     * @param y 屏幕坐标y
     */
    public static void DrawCircle(int r, int x, int y) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawCircle(r,x,y);
        }
    }

    /**
     * 在世界坐标上 绘制文本
     *
     * @param s 文本内容
     * @param v 世界坐标
     * @throws GameException 游戏错误
     */
    public static void DrawString(Object s, Vector2 v) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawString(s, v);
        }
    }

    /**
     * 在屏幕坐标上 绘制文本
     *
     * @param string 文本内容
     * @param x      屏幕坐标x
     * @param y      屏幕坐标y
     */
    public static void DrawString(Object string, int x, int y) {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawString(string, x, y);
        }
    }

}
