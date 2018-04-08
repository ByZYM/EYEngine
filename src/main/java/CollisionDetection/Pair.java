package CollisionDetection;

/**
 * Pair对
 * @author ZYM
 * @param <E> 首个对象
 * @param <F> 第二个对象
 */
public class Pair<E extends Object, F extends Object> {
    private E first;
    private F second;

    public Pair(E first, F second) {
        this.first = first;
        this.second = second;
    }

    public E getFirst() {
        return first;
    }

    public void setFirst(E first) {
        this.first = first;
    }

    public F getSecond() {
        return second;
    }

    public void setSecond(F second) {
        this.second = second;
    }

    public void push(E first, F second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "<" + first + " " + second + ">";
    }
}
