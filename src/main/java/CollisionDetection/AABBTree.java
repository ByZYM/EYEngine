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

                // 找到所有没有被大AABB盒包围的无效节点
                // grab all invalid nodes
                m_invalidNodes.clear();
                UpdateNodeHelper(m_root, m_invalidNodes);

                // 重新插入无效节点
                // re-insert all invalid nodes
                for (final Node node : m_invalidNodes) {
                    // grab parent link
                    // (pointer to the pointer that points to parent)
                    Node parent = node.parent;
                    Node sibling = node.GetSibling();
                    Node grandParent = parent.parent;

                    // 用兄弟节点替换父节点
                    if (grandParent != null) {
                        sibling.parent = grandParent;
                        // 将新生成的节点 赋值给 原来的爷节点的子节点
                        if (grandParent.children[0] == parent) {
                            grandParent.children[0] = sibling;
                        } else {
                            grandParent.children[1] = sibling;
                        }
                    } else {
                        sibling.parent = null;
                        m_root = sibling;
                    }

                    // 重新插入
                    node.UpdateAABB(m_margin);

                    InsertNode(node, m_root);
                }
                m_invalidNodes.clear();
            }
        }

    }

    private void UpdateNodeHelper(Node node, ArrayList<Node> invalidNodes) {

        // 如果是叶子节点
        if (node.IsLeaf()) {
            // 判断FatAABB是否不再包含RealAABB
            if (!node.FatAABB.contains(node.RealAABB)) {
                // 将其加入到无效节点用于更新
                invalidNodes.add(node);
            }
        }
        // 如果是分枝结点
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
     * 插入节点
     *
     * @param node   要插入的节点
     * @param parent 插入位置的节点
     */
    private void InsertNode(final Node node, final Node parent) {
        /* 如果父节点为叶子节点 */
        if (parent.IsLeaf()) {

            /* 生成新的父节点，将原来的父节点变为新父节点的子节点 */
            Node newParent = new Node();
            Node grandParent = parent.parent;

            // 将原来的爷节点赋值给新生成的父节点的父节点
            newParent.parent = grandParent;

            // 将node、parent的父节点指向新父节点
            newParent.SetBranch(node, parent);

            if (grandParent != null) {
                // 将新生成的节点 赋值给 原来的爷节点的子节点
                if (grandParent.children[0] == parent) {
                    grandParent.children[0] = newParent;
                } else {
                    grandParent.children[1] = newParent;
                }
            } else {
                m_root = newParent;
            }
            // 更新父节点的包围盒
            newParent.UpdateAABB(m_margin);

//            /* 平衡树 */
//            BalanceTree(newParent.parent);

        } else {
            /* 如果父节点为分枝节点 */
            // parent is branch, compute volume differences
            // between pre-insert and post-insert
            final AABB aabb0 = parent.children[0].FatAABB;
            final AABB aabb1 = parent.children[1].FatAABB;

            // 计算Cost
            final float volumeDiff0 = aabb0.combine(node.FatAABB).Volume() - aabb0.Volume();
            final float volumeDiff1 = aabb1.combine(node.FatAABB).Volume() - aabb1.Volume();

            // 在Cost小的节点插入
            if (volumeDiff0 < volumeDiff1) {
                InsertNode(node, parent.children[0]);
            } else {
                InsertNode(node, parent.children[1]);
            }
            // 更新父节点的包围盒
            parent.UpdateAABB(m_margin);
//            /* 平衡树 */
//            BalanceTree(parent);

            parent.height = (parent.children[0].height > parent.children[1].height ? parent.children[0].height : parent.children[1].height) + 1;
        }
    }

//    //平衡树 用于插入节点时 平衡
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
//        /* 平衡值 */
//        int balance = leftChild.height - rightChild.height;
//
//        /* 平衡树 */
//        if (balance >= -1 && balance <= 1) {
//            return;
//        }
//
//        /* 如果左子树大于右子树 */
//        if (balance > 1) {
//            Node left_grand_left_child = leftChild.children[0];
//            Node left_grand_right_child = leftChild.children[1];
//
//            /* 左孙子树高度大于右孙子树 */
//            if (left_grand_left_child.height > left_grand_right_child.height) {
//                /* 树结构为：
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
//                /* 优化为：
//                 *              cL
//                 *            /    \
//                 *           gL    New
//                 *         /  \    /  \
//                 *        L    R gR    cR
//                 *        .....
//                 * */
//
//                /* 上旋左子树 */
//                Node grandParent = parent.parent;
//
//                Node newNode = new Node();
//                newNode.SetBranch(left_grand_right_child, rightChild);
//                leftChild.SetBranch(left_grand_left_child, newNode);
//                newNode.UpdateAABB(m_margin);
//                leftChild.parent = grandParent;
//
//                /* 将LeftChild变为父节点 */
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
//                // 更新父节点的包围盒
//                leftChild.UpdateAABB(m_margin);
//
//                /* 平衡子节点的树 */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//                return;
//            }
//
//            /* 右孙子树高度大于左孙子树，相互交换 */
//            if (left_grand_left_child.height < left_grand_right_child.height) {
//                /* 树结构为：
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
//                /* 优化为：
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
//                /* 将LeftChild变为父节点 */
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
//                // 更新父节点的包围盒
//                leftChild.UpdateAABB(m_margin);
//
//                /* 平衡子节点的树 */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//                return;
//            }
//
//        }
//
//        /* 右子树高度大于左子树 */
//        if (balance < -1) {
//            leftChild = parent.children[1];
//            rightChild = parent.children[0];
//
//            Node right_grand_left_child = rightChild.children[0];
//
//            Node right_grand_right_child = rightChild.children[1];
//
//            /* 左孙子树高度大于右孙子树 */
//            if (right_grand_left_child.height > right_grand_right_child.height) {
//                /* 树结构为：
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
//                /* 优化为：
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
//                /* 将LeftChild变为父节点 */
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
//                // 更新父节点的包围盒
//                rightChild.UpdateAABB(m_margin);
//
//                /* 平衡子节点的树 */
//                BalanceTree(parent.children[0]);
//                BalanceTree(parent.children[1]);
//                return;
//            }
//
//            /* 右孙子树高度大于左孙子树，相互交换 */
//            if (right_grand_left_child.height < right_grand_right_child.height) {
//                /* 树结构为：
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
//                /* 优化为：
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
//                /* 将LeftChild变为父节点 */
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
//                // 更新父节点的包围盒
//                rightChild.UpdateAABB(m_margin);
//
//                /* 平衡子节点的树 */
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

        // 如果没有根节点 或者 只有根节点一个节点 直接返回空
        if (m_root == null || m_root.IsLeaf()) {
            return m_pairs;
        }

        // 清除被访问标记
        ClearChildrenCrossFlagHelper(m_root);

        // 递归计算碰撞对
        ComputePairsHelper(m_root.children[0], m_root.children[1]);

        return m_pairs;
    }

    // 清除所有节点被访问标记

    private void ClearChildrenCrossFlagHelper(Node node) {
        node.childrenCrossed = false;
        if (!node.IsLeaf()) {
            ClearChildrenCrossFlagHelper(node.children[0]);
            ClearChildrenCrossFlagHelper(node.children[1]);
        }
    }

    // 访问节点
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

    /* 判断射线是否与指定Shape碰撞 */
    private boolean RayShape(final Ray ray, final RayHitInfo hitPointCallBack, final Shape shape, float maxDistance) {
        boolean returnValue = false;

        float firstHit = maxDistance;

        for (Segment seg : shape.edges) {

            /* 碰撞点回调 */
            RayHitInfo hitPos = new RayHitInfo();

            if (ray.Intersect(seg, hitPos)) {

                /* 碰撞点离射线发射点距离 */
                float len = hitPos.getPosition().sub(ray.start).getLength();

                /* 找到最近点 */
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
     * 返回是否与树中的Collider碰撞
     *
     * @param ray             射线
     * @param hitInfoCallBack 碰撞信息
     * @param maxDistance     最大检测距离
     * @return 是否碰撞
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

            /* ---如果与FatAABB有碰撞--- */
//            if (RayShape(ray, test, new Shape(node.FatAABB.getWorldVertices()), maxDistance)) {
            for (Segment seg : new Shape(node.FatAABB.getWorldVertices()).edges) {
                if (ray.Intersect(seg, hitPos)) {
                    if (hitPos.getPosition().sub(ray.start).getLength() < maxDistance) {
                        /* ---如果与FatAABB有碰撞--- */

                        if (node.IsLeaf()) {

                            RayHitInfo HitPoint = new RayHitInfo();

                             /* 检测与实际碰撞器是否碰撞 */
                            if (RayShape(ray, HitPoint, node.RealAABB.collider.WorldShape, maxDistance)) {

                                /* 找最近的碰撞器 */
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

        /* 节点高度 叶子节点默认为0 null节点为-1 */
        int height = -1;

        // FatAABB包含RealAABB RealAABB包含Collider
        // 胖AABB
        AABB FatAABB;
        // 实际AABB
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

            /* 设置父节点高度为子节点+1 */
            height = (n0.height > n1.height ? n0.height : n1.height) + 1;
        }

        // make this node a leaf
        void SetLeaf(AABB data) {
            // create two-way link
            this.RealAABB = data;
            data.userData = this;

            /* 叶子节点高度为0 */
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
         * 获取兄弟节点
         *
         * @return 兄弟节点
         */
        final Node GetSibling() {
            return this == parent.children[0] ? parent.children[1] : parent.children[0];
        }
    }


}
