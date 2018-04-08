package CollisionDetection;

import GameTools.Vector2;

/**
 * 凸多边形形状类
 *
 * @author ZYM
 */
public class Shape {
    /**
     * 多边形顶点集合
     */
    public Vector2[] vertices;

    public Segment[] edges;

    /**
     * 初始化图形顶点、边 必须是顺时针方向初始化点
     *
     * @param vertices 顶点
     */
    public Shape(Vector2... vertices) {
        this.vertices = vertices;

		/* 初始化边 */
        edges = new Segment[vertices.length];

        for (int i = 0; i < vertices.length - 1; i++) {
            edges[i] = new Segment(vertices[i], vertices[i + 1]);
        }
        edges[vertices.length - 1] = new Segment(vertices[vertices.length - 1], vertices[0]);
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Shape Vertices[ ");
        for (Vector2 v : vertices) {
            s.append(v + " ");
        }

        s.append("]");

        return s.toString();
    }

    /**
     * 移动形状
     *
     * @param v 移动向量
     * @return 移动之后的Shape
     */
    public Shape translate(Vector2 v) {
        Vector2[] newVertices = new Vector2[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = new Vector2(vertices[i].getX() + v.getX(), vertices[i].getY() + v.getY());
        }
        return new Shape(newVertices);
    }

}
