package GameObject;

import GameComponent.Component;
import GameModel.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 一个标准的游戏物体 包含： 模型 组件 脚本（组件）
 *
 * @author ZYM
 */
public class GameObject {

    /**
     * 物体模型
     */
    private Model model = null;

    /**
     * 组件
     */
    private ArrayList<Component> components;

    /**
     * 全部实体
     */
    private final static HashMap<String, GameObject> allObjects = new HashMap<>();

    /**
     * 父物体
     */
    public GameObject Parent;

    /**
     * 子物体
     */
    public HashMap<String, GameObject> Children;

    public String Name;

    /**
     * 初始化游戏物体
     *
     * @param Parent 父物体
     */
    public GameObject(GameObject Parent) {
        this.Parent = Parent;
        components = new ArrayList<>();
        Children = new HashMap<>();
        /* 游戏物体名字 */
        Name = this.getClass().getSimpleName();

        if (Parent != null) {
            Parent.AddChild(this);
        }

        GameObject.AddGameObject(this);
    }

    public static void AddGameObject(GameObject g) {
        String name = GetNewName(g.Name, null, allObjects);
        g.Name = name;
        allObjects.put(name, g);
    }

    public static HashMap<String, GameObject> GetAllObjects() {
        return allObjects;
    }

    public static Collection<GameObject> GetAllObjectsCollection() {
        return allObjects.values();
    }

    public String GetName() {
        return Name;
    }

    public static final GameObject FindGameObjectByName(String Name){
        return allObjects.get(Name);
    }

    /**
     * 设置名字，并修改AllObj与父物体中Children的Key名
     *
     * @param NewName
     * @return
     */
    public void SetName(String NewName) {
        GameObject t = GetAllObjects().remove(this.Name);
        String name = GetNewName(NewName, null, allObjects);
        t.Name = name;
        AddGameObject(t);
        if (t.Parent != null) {
            t.Parent.Children.remove(this.Name);
            t.Parent.Children.put(this.Name, t);
        }
    }

    /**
     * 获取一个新名字，该名字不能和映射中的Key相同，如果相同则取相同类型名字的空值加1。 如 要加入的名字为 aabb11，映射中有 aabb11
     * aabb13则新名字为aabb12。 如 要加入的名字为 aabb1，映射中有 aabb1 aabb3则新名字为aabb2。
     *
     * @param name 名字 前面非数字部分
     * @param num  名字 后面数字部分 ，如果为null，表示名字没有数字部分
     * @param map  拥有名字的映射
     * @return 新名字
     */
    private static String GetNewName(String name, Integer num, HashMap<String, GameObject> map) {

        if (num == null) {
			/* 如果映射中不存在该名字，则该名字合法 */
            if (!map.containsKey(name)) {
                return name;
            }
            // 否则查找新名字
            return GetNewName(name, 1, map);
        } else {
			/* 如果映射中不存在该名字，则该名字合法 */
            if (!map.containsKey(name + num)) {
                return name + num;
            }
            // 否则查找新名字
            return GetNewName(name, num + 1, map);
        }
    }

    /**
     * 添加子物体
     *
     * @param child
     */
    public void AddChild(GameObject child) {
        Children.put(child.Name, child);
    }

    /**
     * 判断物体是否为子物体 如果存在父物体则是
     *
     * @return 是否子物体
     */
    public boolean IsChildren() {
        return Parent != null;
    }

    /**
     * 添加组件
     *
     * @param c Component
     */
    public void AddComponent(Component c) {
        // 如果组件中具有相同类型 则不加入
        for (Component com : components) {
            if (com.getClass().getName().equals(c.getClass().getName())) {
                return;
            }
        }
        components.add(c);
    }

    /**
     * 根据组件类型删除组件
     *
     * @param c 组件类
     */
    public void DeleteComponent(Class<?> c) {

        // 类型相同，删除组件
        for (Component com : components) {
            if (com.getClass().getName().equals(c.getClass().getName())) {
                components.remove(com);
                break;
            }
        }
    }

    /**
     * 根据组件类型寻找挂载在物体上的组件实例
     *
     * @param t 组件类
     * @return 返回组件实例
     */
    public Object GetComponent(Class<?> t) {
        for (Component c : getComponents()) {
            if (c.getClass().getName().equals(t.getName())) {
                return c;
            }
        }
        return null;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

}
