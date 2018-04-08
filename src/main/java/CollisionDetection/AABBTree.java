package CollisionDetection;

import GameComponent.Collider;
import GameException.GameException;
import GameTools.Debug;
import GameTools.Vector2;

import java.util.ArrayList;
import java.util.List;

public class AABBTree implements Broadphase {

    private Node m_root;
    private List<Pair<Collider, Collider>> m_pairs;
    private float m_margin;
    private ArrayList<Node> m_invalidNodes;

    public AABBTree() {
        m_root = null;
        m_margin = 0.2f;
        m_pairs = new ArrayList<>();
        m_invalidNodes = new ArrayList<>();
    }

    public void DrawTree() {
        DrawTree(m_root);
    }

    private void DrawTree(Node node) {
        if (node == null) {
            return;
        }
        if (node.IsLeaf()) {
            try {
                Debug.DrawAABB(node.FatAABB);
                Debug.DrawAABB(node.RealAABB);
            } catch (GameException e) {
                e.printStackTrace();
            }
            return;
        }
        if (node.children[0] != null) {
            try {
                Debug.DrawAABB(node.FatAABB);
                Debug.DrawAABB(node.RealAABB);
            } catch (GameException e) {
                e.printStackTrace();
            }
            DrawTree(node.children[0]);
            DrawTree(node.children[1]);
        }
    }

    @Override
    public void Update() {
        if (m_root != null) {
            if (m_root.IsLeaf()) {
                m_root.UpdateAABB(m_margin);
            } else {

                // �ҵ�����û�б���AABB�а�Χ����Ч�ڵ�
                // grab all invalid nodes
                m_invalidNodes.clear();
                UpdateNodeHelper(m_root, m_invalidNodes);

                // ���²�����Ч�ڵ�
                // re-insert all invalid nodes
                for (final Node node : m_invalidNodes) {
                    // grab parent link
                    // (pointer to the pointer that points to parent)
                    Node parent = node.parent;
                    Node sibling = node.GetSibling();
                    Node grandParent = parent.parent;

                    // ���ֵܽڵ��滻���ڵ�
                    if (grandParent != null) {
                        sibling.parent = grandParent;
                        // �������ɵĽڵ� ��ֵ�� ԭ����ү�ڵ���ӽڵ�
                        if (grandParent.children[0] == parent) {
                            grandParent.children[0] = sibling;
                        } else {
                            grandParent.children[1] = sibling;
                        }
                    } else {
                        sibling.parent = null;
                        m_root = sibling;
                    }

                    // ���²���
                    node.UpdateAABB(m_margin);

                    InsertNode(node, m_root);
                }
                m_invalidNodes.clear();
            }
        }

    }

    private void UpdateNodeHelper(Node node, ArrayList<Node> invalidNodes) {

        // �����Ҷ�ӽڵ�
        if (node.IsLeaf()) {
            // �ж�FatAABB�Ƿ��ٰ���RealAABB
            if (!node.FatAABB.contains(node.RealAABB)) {
                // ������뵽��Ч�ڵ����ڸ���
                invalidNodes.add(node);
            }
        }
        // ����Ƿ�֦���
        else {
            UpdateNodeHelper(node.children[0], invalidNodes);
            UpdateNodeHelper(node.children[1], invalidNodes);
        }
    }

    @Override
    public void Add(AABB aabb) {
        if (m_root != null) {
            // not first node, insert node to tree
            Node node = new Node();
            node.SetLeaf(aabb);
            node.UpdateAABB(m_margin);
            InsertNode(node, m_root);
        } else {
            // first node, make root
            m_root = new Node();
            m_root.SetLeaf(aabb);
            m_root.UpdateAABB(m_margin);
        }
    }

    /**
     * ����ڵ�
     *
     * @param node   Ҫ����Ľڵ�
     * @param parent ����λ�õĽڵ�
     */
    private void InsertNode(final Node node, final Node parent) {
        /* ������ڵ�ΪҶ�ӽڵ� */
        if (parent.IsLeaf()) {

            /* �����µĸ��ڵ㣬��ԭ���ĸ��ڵ��Ϊ�¸��ڵ���ӽڵ� */
            Node newParent = new Node();
            Node grandParent = parent.parent;

            // ��ԭ����ү�ڵ㸳ֵ�������ɵĸ��ڵ�ĸ��ڵ�
            newParent.parent = grandParent;

            // ��node��parent�ĸ��ڵ�ָ���¸��ڵ�
            newParent.SetBranch(node, parent);

            if (grandParent != null) {
                // �������ɵĽڵ� ��ֵ�� ԭ����ү�ڵ���ӽڵ�
                if (grandParent.children[0] == parent) {
                    grandParent.children[0] = newParent;
                } else {
                    grandParent.children[1] = newParent;
                }
            } else {
                m_root = newParent;
            }
            // ���¸��ڵ�İ�Χ��
            newParent.UpdateAABB(m_margin);

//            /* ƽ���� */
//            BalanceTree(newParent.parent);

        } else {
            /* ������ڵ�Ϊ��֦�ڵ� */
            // parent is branch, compute volume differences
            // between pre-insert and post-insert
            final AABB aabb0 = parent.children[0].FatAABB;
            final AABB aabb1 = parent.children[1].FatAABB;

            // ����Cost
            final float volumeDiff0 = aabb0.combine(node.FatAABB).Volume() - aabb0.Volume();
            final float volumeDiff1 = aabb1.combine(node.FatAABB).Volume() - aabb1.Volume();

            // ��CostС�Ľڵ����
            if (volumeDiff0 < volumeDiff1) {
                InsertNode(node, parent.children[0]);
            } else {
                InsertNode(node, parent.children[1]);
            }
            // ���¸��ڵ�İ�Χ��
            parent.UpdateAABB(m_margin);
//            /* ƽ���� */
//            BalanceTree(parent);

            parent.height = (parent.children[0].height > parent.children[1].height ? parent.children[0].height : parent.children[1].height) + 1;
        }
    }

//    //ƽ���� ���ڲ���ڵ�ʱ ƽ��
//    private void BalanceTree(final Node parent) {
//
//        if (parent == null) {
//            return;
//        }
//        if (parent.IsLeaf() || parent.height < 2) {
//            return;
//        }
//
//        Node leftChild = parent.children[0];
//
//        Node rightChild = parent.children[1];
//
//        /* ƽ��ֵ */
//        int balance = leftChild.height - rightChild.height;
//
//        /* ƽ���� */
//        if (balance >= -1 && balance <= 1) {
//            return;
//        }
//
//        /* ������������������� */
//        if (balance > 1) {
//            Node left_grand_left_child = leftChild.children[0];
//            Node left_grand_right_child = leftChild.children[1];
//
//            /* ���������߶ȴ����������� */
//            if (left_grand_left_child.height > left_grand_right_child.height) {
//                /* ���ṹΪ��
//                 *          parent
//                 *           / \
//                 *          cL  cR
//                 *         / \
//                 *        gL gR
//                 *       / \
//                 *      L   R
//                 *      .....
//                 * */
//
//                /* �Ż�Ϊ��
//                 *              cL
//                 *            /    \
//                 *           gL    New
//                 *         /  \    /  \
//                 *        L    R gR    cR
//                 *        .....
//                 * */
//
//                /* ���������� */
//                Node grandParent = parent.parent;
//
//                Node newNode = new Node();
//                newNode.SetBranch(left_grand_right_child, rightChild);
//                leftChild.SetBranch(left_grand_left_child, newNode);
//                newNode.UpdateAABB(m_margin);
//                leftChild.parent = grandParent;
//
//                /* ��LeftChild��Ϊ���ڵ� */
//                if (grandParent != null) {
//                    if (grandParent.children[0] == parent) {
//                        grandParent.children[0] = leftChild;
//                    } else {
//                        grandParent.children[1] = leftChild;
//                    }
//                    leftChild.parent.height -= 1;
//                } else {
//                    m_root = leftChild;
//                }
//                // ���¸��ڵ�İ�Χ��
//                leftChild.UpdateAABB(m_margin);
//
//                /* ƽ���ӽڵ���� */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//                return;
//            }
//
//            /* ���������߶ȴ��������������໥���� */
//            if (left_grand_left_child.height < left_grand_right_child.height) {
//                /* ���ṹΪ��
//                 *          parent
//                 *           / \
//                 *          cL  cR
//                 *         / \
//                 *       gL   gR
//                 *            / \
//                 *           L   R
//                 *           .....
//                 * */
//
//                /* �Ż�Ϊ��
//                 *              cL
//                 *            /    \
//                 *          new     gR
//                 *         /  \    /  \
//                 *        gL  cR  L    R
//                 *        .....
//                 * */
//
//                Node grandParent = parent.parent;
//
//                Node newNode = new Node();
//                newNode.SetBranch(left_grand_left_child, rightChild);
//                leftChild.SetBranch(newNode, left_grand_right_child);
//                newNode.UpdateAABB(m_margin);
//                leftChild.parent = grandParent;
//
//                /* ��LeftChild��Ϊ���ڵ� */
//                if (grandParent != null) {
//                    if (grandParent.children[0] == parent) {
//                        grandParent.children[0] = leftChild;
//                    } else {
//                        grandParent.children[1] = leftChild;
//                    }
//                    leftChild.parent.height -= 1;
//                } else {
//                    m_root = leftChild;
//                }
//                // ���¸��ڵ�İ�Χ��
//                leftChild.UpdateAABB(m_margin);
//
//                /* ƽ���ӽڵ���� */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//                return;
//            }
//
//        }
//
//        /* �������߶ȴ��������� */
//        if (balance < -1) {
//            leftChild = parent.children[1];
//            rightChild = parent.children[0];
//
//            Node right_grand_left_child = rightChild.children[0];
//
//            Node right_grand_right_child = rightChild.children[1];
//
//            /* ���������߶ȴ����������� */
//            if (right_grand_left_child.height > right_grand_right_child.height) {
//                /* ���ṹΪ��
//                 *            o
//                 *           / \
//                 *         cL   cR
//                 *             / \
//                 *           gL   gR
//                 *          / \
//                 *         L   R
//                 *         .....
//                 * */
//
//                /* �Ż�Ϊ��
//                 *              cR
//                 *            /    \
//                 *          gL     new
//                 *         /  \    /  \
//                 *        L    R  cL  gR
//                 *        .....
//                 * */
//
//                Node grandParent = parent.parent;
//                Node newNode = new Node();
//
//                newNode.SetBranch(rightChild, right_grand_right_child);
//
//                rightChild.SetBranch(right_grand_left_child, newNode);
//                newNode.UpdateAABB(m_margin);
//                rightChild.parent = grandParent;
//
//                /* ��LeftChild��Ϊ���ڵ� */
//                if (grandParent != null) {
//                    if (grandParent.children[0] == parent) {
//                        grandParent.children[0] = rightChild;
//                    } else {
//                        grandParent.children[1] = rightChild;
//                    }
//                    rightChild.parent.height -= 1;
//                } else {
//                    m_root = rightChild;
//                }
//                // ���¸��ڵ�İ�Χ��
//                rightChild.UpdateAABB(m_margin);
//
//                /* ƽ���ӽڵ���� */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//                return;
//            }
//
//            /* ���������߶ȴ��������������໥���� */
//            if (right_grand_left_child.height < right_grand_right_child.height) {
//                /* ���ṹΪ��
//                 *parent      o
//                 *           / \
//                 *children cL   cR
//                 *              / \
//                 *granC       gL   gR
//                 *                / \
//                 *               L   R
//                 *               .....
//                 * */
//
//                /* �Ż�Ϊ��
//                 *              cR
//                 *            /    \
//                 *          new     gR
//                 *         /  \    /  \
//                 *        cL   gL L    R
//                 *        .....
//                 * */
//
//                Node grandParent = parent.parent;
//                Node newNode = new Node();
//
//                newNode.SetBranch(rightChild, right_grand_left_child);
//
//                rightChild.SetBranch(newNode,right_grand_right_child );
//                newNode.UpdateAABB(m_margin);
//                rightChild.parent = grandParent;
//
//                /* ��LeftChild��Ϊ���ڵ� */
//                if (grandParent != null) {
//                    if (grandParent.children[0] == parent) {
//                        grandParent.children[0] = rightChild;
//                    } else {
//                        grandParent.children[1] = rightChild;
//                    }
//                    rightChild.parent.height -= 1;
//                } else {
//                    m_root = rightChild;
//                }
//                // ���¸��ڵ�İ�Χ��
//                rightChild.UpdateAABB(m_margin);
//
//                /* ƽ���ӽڵ���� */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//
//                return;
//            }
//        }
//    }

    @Override
    public List<Pair<Collider, Collider>> ComputePairs() {
        m_pairs.clear();

        // ���û�и��ڵ� ���� ֻ�и��ڵ�һ���ڵ� ֱ�ӷ��ؿ�
        if (m_root == null || m_root.IsLeaf()) {
            return m_pairs;
        }

        // ��������ʱ��
        ClearChildrenCrossFlagHelper(m_root);

        // �ݹ������ײ��
        ComputePairsHelper(m_root.children[0], m_root.children[1]);

        return m_pairs;
    }

    // ������нڵ㱻���ʱ��

    private void ClearChildrenCrossFlagHelper(Node node) {
        node.childrenCrossed = false;
        if (!node.IsLeaf()) {
            ClearChildrenCrossFlagHelper(node.children[0]);
            ClearChildrenCrossFlagHelper(node.children[1]);
        }
    }

    // ���ʽڵ�
    private void CrossChildren(Node node) {
        if (!node.childrenCrossed) {
            ComputePairsHelper(node.children[0], node.children[1]);
            node.childrenCrossed = true;
        }
    }

    private void ComputePairsHelper(Node n0, Node n1) {
        if (n0.IsLeaf()) {
            // 2 leaves, check proxies instead of fat AABBs
            if (n1.IsLeaf()) {
                if (n0.RealAABB.collides(n1.RealAABB))
                    m_pairs.add(new Pair<>(n0.RealAABB.collider, n1.RealAABB.collider));
            }
            // 1 branch / 1 leaf, 2 cross checks
            else {
                CrossChildren(n1);
                ComputePairsHelper(n0, n1.children[0]);
                ComputePairsHelper(n0, n1.children[1]);
            }
        } else {
            // 1 branch / 1 leaf, 2 cross checks
            if (n1.IsLeaf()) {
                CrossChildren(n0);
                ComputePairsHelper(n0.children[0], n1);
                ComputePairsHelper(n0.children[1], n1);
            }
            // 2 branches, 4 cross checks
            else {
                CrossChildren(n0);
                CrossChildren(n1);
                ComputePairsHelper(n0.children[0], n1.children[0]);
                ComputePairsHelper(n0.children[0], n1.children[1]);
                ComputePairsHelper(n0.children[1], n1.children[0]);
                ComputePairsHelper(n0.children[1], n1.children[1]);
            }
        } // end of if (n0.IsLeaf())
    }

    @Override
    public void Pick(Vector2 pos, ArrayList<Collider> result) {
        CollideDetect(pos, result);
    }

    @Override
    public void Query(AABB aabb, ArrayList<Collider> output) {
        CollideDetect(aabb, output);
    }

    private void CollideDetect(Object aabbOrPos, ArrayList<Collider> output) {
        ArrayList<Node> q = new ArrayList<>();
        if (m_root != null) {
            q.add(m_root);
        }

        while (!q.isEmpty()) {
            Node node = q.remove(q.size() - 1);

            if (node.IsLeaf()) {
                if (aabbOrPos instanceof AABB) {
                    if (node.RealAABB.collides((AABB) aabbOrPos))
                        output.add(node.RealAABB.collider);
                } else if (aabbOrPos instanceof Vector2) {
                    if (node.RealAABB.collides((Vector2) aabbOrPos))
                        output.add(node.RealAABB.collider);
                }
            } else {
                q.add(node.children[0]);
                q.add(node.children[1]);
            }
        }
    }

    /* �ж������Ƿ���ָ��Shape��ײ */
    private boolean RayShape(final Ray ray, final RayHitInfo hitPointCallBack, final Shape shape, float maxDistance) {
        boolean returnValue = false;

        float firstHit = maxDistance;

        for (Segment seg : shape.edges) {

            /* ��ײ��ص� */
            RayHitInfo hitPos = new RayHitInfo();

            if (ray.Intersect(seg, hitPos)) {

                /* ��ײ�������߷������� */
                float len = hitPos.getPosition().sub(ray.start).getLength();

                /* �ҵ������ */
                if (len < maxDistance && len < firstHit) {
                    hitPointCallBack.setPerp(hitPos.getPerp().clone());
                    hitPointCallBack.setPosition(hitPos.getPosition().clone());
                    hitPointCallBack.setRay(ray);
                    firstHit = len;
                    returnValue = true;
                }
            }
        }
        return returnValue;
    }

    /**
     * �����Ƿ������е�Collider��ײ
     *
     * @param ray             ����
     * @param hitInfoCallBack ��ײ��Ϣ
     * @param maxDistance     ��������
     * @return �Ƿ���ײ
     */
    @Override
    public boolean RayCast(final Ray ray, final RayHitInfo hitInfoCallBack, float maxDistance) {
        boolean returnValue = false;

        float firstHit = maxDistance;

        ArrayList<Node> q = new ArrayList<>();
        if (m_root != null) {
            q.add(m_root);
        }

        while (!q.isEmpty()) {
            Node node = q.remove(q.size() - 1);

            RayHitInfo test = new RayHitInfo();

            RayHitInfo hitPos = new RayHitInfo();

            /* ---�����FatAABB����ײ--- */
//            if (RayShape(ray, test, new Shape(node.FatAABB.getWorldVertices()), maxDistance)) {
            for (Segment seg : new Shape(node.FatAABB.getWorldVertices()).edges) {
                if (ray.Intersect(seg, hitPos)) {
                    if (hitPos.getPosition().sub(ray.start).getLength() < maxDistance) {
                        /* ---�����FatAABB����ײ--- */

                        if (node.IsLeaf()) {

                            RayHitInfo HitPoint = new RayHitInfo();

                             /* �����ʵ����ײ���Ƿ���ײ */
                            if (RayShape(ray, HitPoint, node.RealAABB.collider.WorldShape, maxDistance)) {

                                /* ���������ײ�� */
                                float len = HitPoint.getPosition().sub(ray.start).getLength();

                                if (len < firstHit) {
                                    hitInfoCallBack.setPerp(HitPoint.getPerp().clone());
                                    hitInfoCallBack.setPosition(HitPoint.getPosition().clone());
                                    hitInfoCallBack.setRay(ray);
                                    hitInfoCallBack.setCollider(node.RealAABB.collider);
                                    firstHit = len;
                                    returnValue = true;
                                }
                            }
                        } else {
                            q.add(node.children[0]);
                            q.add(node.children[1]);
                        }
                        break;
                    }
                }
            }

        }
        return returnValue;
    }

    class Node {
        Node parent;
        Node children[] = new Node[2];
        boolean childrenCrossed;

        /* �ڵ�߶� Ҷ�ӽڵ�Ĭ��Ϊ0 null�ڵ�Ϊ-1 */
        int height = -1;

        // FatAABB����RealAABB RealAABB����Collider
        // ��AABB
        AABB FatAABB;
        // ʵ��AABB
        AABB RealAABB;

        Node() {
            parent = null;
            children[0] = null;
            children[1] = null;
            FatAABB = new AABB();
            RealAABB = new AABB();
            height = -1;
        }

        final boolean IsLeaf() {
            return children[0] == null;
        }

        // make this ndoe a branch
        void SetBranch(Node n0, Node n1) {
            n0.parent = this;
            n1.parent = this;

            children[0] = n0;
            children[1] = n1;

            /* ���ø��ڵ�߶�Ϊ�ӽڵ�+1 */
            height = (n0.height > n1.height ? n0.height : n1.height) + 1;
        }

        // make this node a leaf
        void SetLeaf(AABB data) {
            // create two-way link
            this.RealAABB = data;
            data.userData = this;

            /* Ҷ�ӽڵ�߶�Ϊ0 */
            height = 0;

            children[0] = null;
            children[1] = null;
        }

        void UpdateAABB(float margin) {
            if (IsLeaf()) {
                // make fat AABB
                final Vector2 marginVec = new Vector2(margin, margin);
                FatAABB.set(RealAABB.WorldLowerBound.sub(marginVec), RealAABB.WorldUpperBound.add(marginVec));
            } else
                // make union of child AABBs of child nodes
                FatAABB = children[0].FatAABB.combine(children[1].FatAABB);
        }

        /**
         * ��ȡ�ֵܽڵ�
         *
         * @return �ֵܽڵ�
         */
        final Node GetSibling() {
            return this == parent.children[0] ? parent.children[1] : parent.children[0];
        }
    }


}
