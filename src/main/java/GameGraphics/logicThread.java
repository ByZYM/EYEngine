package GameGraphics;

import CollisionDetection.Pair;
import CollisionDetection.SAT;
import EngineFactory.GraphicsFactory;
import GameComponent.Collider;
import GameComponent.Component;
import GameComponent.RigidBody;
import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Time;
import GameTools.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * ִ�м�����߳�
 */
public class logicThread implements Runnable, KeyListener {

    /**
     * ��ͣ��Ϸ
     */
    boolean pause = false;

    /**
     * ������
     */
    private Time fr = new Time();

    // ÿ20�������һ��FixedUpdate
    private float mS = 0L;

    final GameFrame j;

    public logicThread(GameFrame j) {
        this.j = j;
    }

    renderThread renderThread;

    /* ��Runnableʵ�ֵ�run���������ô��ڵ��ػ淽�� */
    public void run() {
//        synchronized (IDrawE.DRAW_LIST) {
            while (true) {
                if (!pause) {
                    try {
//                        if(!renderThread.finished){
//                            IDrawE.DRAW_LIST.wait();
//                        }
                        renderThread.finished = false;
//                        Calculate();
                        renderThread.finished = true;
//                        IDrawE.DRAW_LIST.notifyAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pause = !(!pause);
            }
//        }
    }

    public void Calculate() throws GameException {

        GraphicsFactory.GetDrawE().ClearList();
        // ������
        fr.deltaTime();


        /*
             * --------------------------���ƶ���ִ��Start��Update�ű�-------------------
			 * -------
			 */
//            Collection<GameObject> clct = GameObject.GetAllObjectsCollection();
        ArrayList<GameObject> allObjects = new ArrayList<>(GameObject.GetAllObjectsCollection());

        for (GameObject o : allObjects) {

            for (Component e : o.getComponents()) {
                if (e instanceof EngineBehavior) {
                    if (!((EngineBehavior) e).IsStarted) {
                        ((EngineBehavior) e).Start();
                        ((EngineBehavior) e).IsStarted = true;
                    }
                    ((EngineBehavior) e).Update();
                }
            }
        }
            /*
             * --------------------------���ƶ���ִ��Start��Update�ű�-------------------
			 * -------
			 */
            /* --------------------------��Ҫ����-------------------------- */

			/* --------------------------��ײ���-------------------------- */

        // ����AABB��
        Collider.bp.Update();


        // ��ȡ������ײ
        List<Pair<Collider, Collider>> ColliderPairList = Collider.bp.ComputePairs();

        for (Pair<Collider, Collider> pair : ColliderPairList) {
            // ��������
            Vector2 MTD = new Vector2();


            Collider c1 = pair.getFirst();
            Collider c2 = pair.getSecond();
            GameObject ob1 = c1.getGameobject();
            GameObject ob2 = c2.getGameobject();

            // ����������ײ
            if (SAT.Intersect(c1.WorldShape, c2.WorldShape, MTD)) {

                // �ǽű�������ײ
                if (!c1.IsTrigger && !c2.IsTrigger) {

                    // ���������ײ
                    if (ob1.GetComponent(RigidBody.class) != null) {
                        // ������ײ
                        for (Component e : ob1.getComponents()) {
                            if (e instanceof EngineBehavior) {
                                ((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
                            }
                        }
                    } else if (ob2.GetComponent(RigidBody.class) != null) {
                        // ������ײ
                        for (Component e : ob2.getComponents()) {
                            if (e instanceof EngineBehavior) {
                                ((EngineBehavior) e).OnRigidBodyCollisionEnter(ob1);
                            }
                        }
                    }

                    Vector2 D = c1.transform_world.getPosition().sub(c2.transform_world.getPosition());

//						MTD.multiLocal(0.001f);

                    if (D.dot(MTD) < 0)
                        MTD.multiLocal(-1);
                    if (c1.IsStatic && !c2.IsStatic) {
                        c2.translate(MTD.multi(-1));
                    } else if (!c1.IsStatic && c2.IsStatic) {
                        c1.translate(MTD.multi(1));
                    } else if (!c1.IsStatic) {
                        c1.translate(MTD.multi(0.5f));
                        c2.translate(MTD.multi(-0.5f));
                    }
                    // ������ײ
                    for (Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob2);
                        }
                    }

                    // ������ײ
                    for (Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob1);
                        }
                    }
                }

					/* �ű�������ײ */
                else {
                    // ������ײ
                    for (Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob2);
                        }
                    }

                    // ������ײ
                    for (Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob1);
                        }
                    }
                }

            }

        }

//			// �����ظ����������ļ���
//			for (int i = 0; i < allObjects.size(); i++) {
//
//				GameObject ob1 = allObjects.get(i);
//
//				for (int j = i + 1; j < allObjects.size(); j++) {
//
//					GameObject ob2 = allObjects.get(j);
//
//					Collider c1 = (Collider) ob1.GetComponent(Collider.class);
//					Collider c2 = (Collider) ob2.GetComponent(Collider.class);
//
//					// ��������
//					Vector2 MTD = new Vector2();
//
//					if (c2 != c1 && c1 != null && c2 != null) {
//
//						// ����������ײ
//						if (SAT.Intersect(c1.WorldShape, c2.WorldShape, MTD)) {
//
//							// �ǽű�������ײ
//							if (!c1.IsTrigger && !c2.IsTrigger) {
//
//								// ���������ײ
//								if (ob1.GetComponent(RigidBody.class) != null) {
//									// ������ײ
//									for (Component e : ob1.getComponents()) {
//										if (e instanceof EngineBehavior) {
//											((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
//										}
//									}
//								} else if (ob2.GetComponent(RigidBody.class) != null) {
//									// ������ײ
//									for (Component e : ob2.getComponents()) {
//										if (e instanceof EngineBehavior) {
//											((EngineBehavior) e).OnRigidBodyCollisionEnter(ob1);
//										}
//									}
//								}
//
//								Vector2 D = c1.transform_world.getPosition().sub(c2.transform_world.getPosition());
//								if (D.dot(MTD) < 0)
//									MTD.multiLocal(-1);
//								if (c1.IsStatic && !c2.IsStatic) {
//									c2.translate(MTD.multi(-1));
//								} else if (!c1.IsStatic && c2.IsStatic) {
//									c1.translate(MTD.multi(1));
//								} else if (!c1.IsStatic && !c2.IsStatic) {
//									c1.translate(MTD.multi(0.5f));
//									c2.translate(MTD.multi(-0.5f));
//								}
//								// ������ײ
//								for (Component e : ob1.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnCollisionEnter(ob2);
//									}
//								}
//
//								// ������ײ
//								for (Component e : ob2.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnCollisionEnter(ob1);
//									}
//								}
//							}
//
//							/* �ű�������ײ */
//							else {
//								// ������ײ
//								for (Component e : ob1.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnTriggerEnter(ob2);
//									}
//								}
//
//								// ������ײ
//								for (Component e : ob2.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnTriggerEnter(ob1);
//									}
//								}
//							}
//
//						}
//
//					}
//				}
//			}


			/* --------------------------��ײ���-------------------------- */

			/*
             * --------------------------ִ��FixedUpdate�ű�------------------------
			 * --
			 */
        mS += Time.DeltaTime;
        // ÿ2����ִ��һ��
        if (mS > Time.FixedTime) {
            mS -= Time.FixedTime;

            for (GameObject o : allObjects) {
                // GameObject o = allObjects.get(i);
                for (Component e : o.getComponents()) {
                    // ����ǽű�
                    if (e instanceof EngineBehavior) {
                        ((EngineBehavior) e).FixedUpdate();
                    }
                }
            }
        }
            /*
             * --------------------------ִ��FixedUpdate�ű�------------------------
			 * --
			 */

			/*
             * --------------------------ִ��LateUpdate�ű�-------------------------
			 * -
			 */
        for (GameObject o : allObjects) {
            // GameObject o = allObjects.get(i);
            // ִ�нű�
            for (Component e : o.getComponents()) {

                // ����ǽű�
                if (e instanceof EngineBehavior) {
                    ((EngineBehavior) e).LateUpdate();
                }
            }
        }
			/*
			 * --------------------------ִ��LateUpdate�ű�-------------------------
			 * -
			 */
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown()) {
            switch (e.getKeyCode()) {
            /* ��ͣ��Ϸ */
                case KeyEvent.VK_P:
                    pause = !pause;
                    break;
            }
        } else {
            switch (e.getKeyCode()) {
			/* ��֡���� */
                case KeyEvent.VK_P:
                    if (pause == true) {
                        try {
                            Calculate();
                        } catch (GameException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void setRenderThread(renderThread renderThread) {
        this.renderThread = renderThread;
    }
}