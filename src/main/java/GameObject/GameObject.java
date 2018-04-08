package GameObject;

import GameComponent.Component;
import GameModel.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * һ����׼����Ϸ���� ������ ģ�� ��� �ű��������
 *
 * @author ZYM
 */
public class GameObject {

    /**
     * ����ģ��
     */
    private Model model = null;

    /**
     * ���
     */
    private ArrayList<Component> components;

    /**
     * ȫ��ʵ��
     */
    private final static HashMap<String, GameObject> allObjects = new HashMap<>();

    /**
     * ������
     */
    public GameObject Parent;

    /**
     * ������
     */
    public HashMap<String, GameObject> Children;

    public String Name;

    /**
     * ��ʼ����Ϸ����
     *
     * @param Parent ������
     */
    public GameObject(GameObject Parent) {
        this.Parent = Parent;
        components = new ArrayList<>();
        Children = new HashMap<>();
        /* ��Ϸ�������� */
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
     * �������֣����޸�AllObj�븸������Children��Key��
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
     * ��ȡһ�������֣������ֲ��ܺ�ӳ���е�Key��ͬ�������ͬ��ȡ��ͬ�������ֵĿ�ֵ��1�� �� Ҫ���������Ϊ aabb11��ӳ������ aabb11
     * aabb13��������Ϊaabb12�� �� Ҫ���������Ϊ aabb1��ӳ������ aabb1 aabb3��������Ϊaabb2��
     *
     * @param name ���� ǰ������ֲ���
     * @param num  ���� �������ֲ��� �����Ϊnull����ʾ����û�����ֲ���
     * @param map  ӵ�����ֵ�ӳ��
     * @return ������
     */
    private static String GetNewName(String name, Integer num, HashMap<String, GameObject> map) {

        if (num == null) {
			/* ���ӳ���в����ڸ����֣�������ֺϷ� */
            if (!map.containsKey(name)) {
                return name;
            }
            // �������������
            return GetNewName(name, 1, map);
        } else {
			/* ���ӳ���в����ڸ����֣�������ֺϷ� */
            if (!map.containsKey(name + num)) {
                return name + num;
            }
            // �������������
            return GetNewName(name, num + 1, map);
        }
    }

    /**
     * ���������
     *
     * @param child
     */
    public void AddChild(GameObject child) {
        Children.put(child.Name, child);
    }

    /**
     * �ж������Ƿ�Ϊ������ ������ڸ���������
     *
     * @return �Ƿ�������
     */
    public boolean IsChildren() {
        return Parent != null;
    }

    /**
     * ������
     *
     * @param c Component
     */
    public void AddComponent(Component c) {
        // �������о�����ͬ���� �򲻼���
        for (Component com : components) {
            if (com.getClass().getName().equals(c.getClass().getName())) {
                return;
            }
        }
        components.add(c);
    }

    /**
     * �����������ɾ�����
     *
     * @param c �����
     */
    public void DeleteComponent(Class<?> c) {

        // ������ͬ��ɾ�����
        for (Component com : components) {
            if (com.getClass().getName().equals(c.getClass().getName())) {
                components.remove(com);
                break;
            }
        }
    }

    /**
     * �����������Ѱ�ҹ����������ϵ����ʵ��
     *
     * @param t �����
     * @return �������ʵ��
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
