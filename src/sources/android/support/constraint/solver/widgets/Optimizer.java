package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.ConstraintWidget;

public class Optimizer {
    static final int FLAG_CHAIN_DANGLING = 1;
    static final int FLAG_RECOMPUTE_BOUNDS = 2;
    static final int FLAG_USE_OPTIMIZE = 0;
    public static final int OPTIMIZATION_BARRIER = 2;
    public static final int OPTIMIZATION_CHAIN = 4;
    public static final int OPTIMIZATION_DIMENSIONS = 8;
    public static final int OPTIMIZATION_DIRECT = 1;
    public static final int OPTIMIZATION_GROUPS = 32;
    public static final int OPTIMIZATION_NONE = 0;
    public static final int OPTIMIZATION_RATIO = 16;
    public static final int OPTIMIZATION_STANDARD = 7;
    static boolean[] flags = new boolean[3];

    static void analyze(int i, ConstraintWidget constraintWidget) {
        constraintWidget.updateResolutionNodes();
        ResolutionAnchor resolutionNode = constraintWidget.mLeft.getResolutionNode();
        ResolutionAnchor resolutionNode2 = constraintWidget.mTop.getResolutionNode();
        ResolutionAnchor resolutionNode3 = constraintWidget.mRight.getResolutionNode();
        ResolutionAnchor resolutionNode4 = constraintWidget.mBottom.getResolutionNode();
        boolean z = (i & 8) == 8;
        boolean z2 = constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && optimizableMatchConstraint(constraintWidget, 0);
        if (!(resolutionNode.type == 4 || resolutionNode3.type == 4)) {
            if (constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || (z2 && constraintWidget.getVisibility() == 8)) {
                if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget == null) {
                    resolutionNode.setType(1);
                    resolutionNode3.setType(1);
                    if (z) {
                        resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode3.dependsOn(resolutionNode, constraintWidget.getWidth());
                    }
                } else if (constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget == null) {
                    resolutionNode.setType(1);
                    resolutionNode3.setType(1);
                    if (z) {
                        resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode3.dependsOn(resolutionNode, constraintWidget.getWidth());
                    }
                } else if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget != null) {
                    resolutionNode.setType(1);
                    resolutionNode3.setType(1);
                    resolutionNode.dependsOn(resolutionNode3, -constraintWidget.getWidth());
                    if (z) {
                        resolutionNode.dependsOn(resolutionNode3, -1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode.dependsOn(resolutionNode3, -constraintWidget.getWidth());
                    }
                } else if (!(constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                    resolutionNode.setType(2);
                    resolutionNode3.setType(2);
                    if (z) {
                        constraintWidget.getResolutionWidth().addDependent(resolutionNode);
                        constraintWidget.getResolutionWidth().addDependent(resolutionNode3);
                        resolutionNode.setOpposite(resolutionNode3, -1, constraintWidget.getResolutionWidth());
                        resolutionNode3.setOpposite(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode.setOpposite(resolutionNode3, (float) (-constraintWidget.getWidth()));
                        resolutionNode3.setOpposite(resolutionNode, (float) constraintWidget.getWidth());
                    }
                }
            } else if (z2) {
                int width = constraintWidget.getWidth();
                resolutionNode.setType(1);
                resolutionNode3.setType(1);
                if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget == null) {
                    if (z) {
                        resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode3.dependsOn(resolutionNode, width);
                    }
                } else if (constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget != null) {
                    if (constraintWidget.mLeft.mTarget != null || constraintWidget.mRight.mTarget == null) {
                        if (!(constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) {
                            if (z) {
                                constraintWidget.getResolutionWidth().addDependent(resolutionNode);
                                constraintWidget.getResolutionWidth().addDependent(resolutionNode3);
                            }
                            if (constraintWidget.mDimensionRatio == 0.0f) {
                                resolutionNode.setType(3);
                                resolutionNode3.setType(3);
                                resolutionNode.setOpposite(resolutionNode3, 0.0f);
                                resolutionNode3.setOpposite(resolutionNode, 0.0f);
                            } else {
                                resolutionNode.setType(2);
                                resolutionNode3.setType(2);
                                resolutionNode.setOpposite(resolutionNode3, (float) (-width));
                                resolutionNode3.setOpposite(resolutionNode, (float) width);
                                constraintWidget.setWidth(width);
                            }
                        }
                    } else if (z) {
                        resolutionNode.dependsOn(resolutionNode3, -1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode.dependsOn(resolutionNode3, -width);
                    }
                } else if (z) {
                    resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                } else {
                    resolutionNode3.dependsOn(resolutionNode, width);
                }
            }
        }
        boolean z3 = constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && optimizableMatchConstraint(constraintWidget, 1);
        if (resolutionNode2.type != 4 && resolutionNode4.type != 4) {
            if (constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || (z3 && constraintWidget.getVisibility() == 8)) {
                if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget == null) {
                    resolutionNode2.setType(1);
                    resolutionNode4.setType(1);
                    if (z) {
                        resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode4.dependsOn(resolutionNode2, constraintWidget.getHeight());
                    }
                    ConstraintAnchor constraintAnchor = constraintWidget.mBaseline;
                    if (constraintAnchor.mTarget != null) {
                        constraintAnchor.getResolutionNode().setType(1);
                        resolutionNode2.dependsOn(1, constraintWidget.mBaseline.getResolutionNode(), -constraintWidget.mBaselineDistance);
                    }
                } else if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget == null) {
                    resolutionNode2.setType(1);
                    resolutionNode4.setType(1);
                    if (z) {
                        resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode4.dependsOn(resolutionNode2, constraintWidget.getHeight());
                    }
                    if (constraintWidget.mBaselineDistance > 0) {
                        constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
                    }
                } else if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget != null) {
                    resolutionNode2.setType(1);
                    resolutionNode4.setType(1);
                    if (z) {
                        resolutionNode2.dependsOn(resolutionNode4, -1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode2.dependsOn(resolutionNode4, -constraintWidget.getHeight());
                    }
                    if (constraintWidget.mBaselineDistance > 0) {
                        constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
                    }
                } else if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null) {
                    resolutionNode2.setType(2);
                    resolutionNode4.setType(2);
                    if (z) {
                        resolutionNode2.setOpposite(resolutionNode4, -1, constraintWidget.getResolutionHeight());
                        resolutionNode4.setOpposite(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                        constraintWidget.getResolutionHeight().addDependent(resolutionNode2);
                        constraintWidget.getResolutionWidth().addDependent(resolutionNode4);
                    } else {
                        resolutionNode2.setOpposite(resolutionNode4, (float) (-constraintWidget.getHeight()));
                        resolutionNode4.setOpposite(resolutionNode2, (float) constraintWidget.getHeight());
                    }
                    if (constraintWidget.mBaselineDistance > 0) {
                        constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
                    }
                }
            } else if (z3) {
                int height = constraintWidget.getHeight();
                resolutionNode2.setType(1);
                resolutionNode4.setType(1);
                if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget == null) {
                    if (z) {
                        resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode4.dependsOn(resolutionNode2, height);
                    }
                } else if (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget != null) {
                    if (constraintWidget.mTop.mTarget != null || constraintWidget.mBottom.mTarget == null) {
                        if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null) {
                            if (z) {
                                constraintWidget.getResolutionHeight().addDependent(resolutionNode2);
                                constraintWidget.getResolutionWidth().addDependent(resolutionNode4);
                            }
                            if (constraintWidget.mDimensionRatio == 0.0f) {
                                resolutionNode2.setType(3);
                                resolutionNode4.setType(3);
                                resolutionNode2.setOpposite(resolutionNode4, 0.0f);
                                resolutionNode4.setOpposite(resolutionNode2, 0.0f);
                                return;
                            }
                            resolutionNode2.setType(2);
                            resolutionNode4.setType(2);
                            resolutionNode2.setOpposite(resolutionNode4, (float) (-height));
                            resolutionNode4.setOpposite(resolutionNode2, (float) height);
                            constraintWidget.setHeight(height);
                            if (constraintWidget.mBaselineDistance > 0) {
                                constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
                            }
                        }
                    } else if (z) {
                        resolutionNode2.dependsOn(resolutionNode4, -1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode2.dependsOn(resolutionNode4, -height);
                    }
                } else if (z) {
                    resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                } else {
                    resolutionNode4.dependsOn(resolutionNode2, height);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0032, code lost:
        if (r7.mHorizontalChainStyle == 2) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        if (r7.mVerticalChainStyle == 2) goto L_0x0034;
     */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01d5  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0109  */
    static boolean applyChainOptimized(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i, int i2, ChainHead chainHead) {
        boolean z;
        boolean z2;
        ResolutionAnchor resolutionAnchor;
        float f2;
        float f3;
        ConstraintWidget constraintWidget;
        boolean z3;
        ConstraintWidget constraintWidget2 = chainHead.mFirst;
        ConstraintWidget constraintWidget3 = chainHead.mLast;
        ConstraintWidget constraintWidget4 = chainHead.mFirstVisibleWidget;
        ConstraintWidget constraintWidget5 = chainHead.mLastVisibleWidget;
        ConstraintWidget constraintWidget6 = chainHead.mHead;
        float f4 = chainHead.mTotalWeight;
        ConstraintWidget constraintWidget7 = chainHead.mFirstMatchConstraintWidget;
        ConstraintWidget constraintWidget8 = chainHead.mLastMatchConstraintWidget;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ((ConstraintWidget) constraintWidgetContainer).mListDimensionBehaviors[i];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i == 0) {
            z2 = constraintWidget6.mHorizontalChainStyle == 0;
            z = constraintWidget6.mHorizontalChainStyle == 1;
        } else {
            z2 = constraintWidget6.mVerticalChainStyle == 0;
            z = constraintWidget6.mVerticalChainStyle == 1;
        }
        boolean z4 = true;
        ConstraintWidget constraintWidget9 = constraintWidget2;
        int i3 = 0;
        boolean z5 = false;
        int i4 = 0;
        float f5 = 0.0f;
        float f6 = 0.0f;
        while (!z5) {
            if (constraintWidget9.getVisibility() != 8) {
                i4++;
                f5 += (float) (i == 0 ? constraintWidget9.getWidth() : constraintWidget9.getHeight());
                if (constraintWidget9 != constraintWidget4) {
                    f5 += (float) constraintWidget9.mListAnchors[i2].getMargin();
                }
                if (constraintWidget9 != constraintWidget5) {
                    f5 += (float) constraintWidget9.mListAnchors[i2 + 1].getMargin();
                }
                f6 = f6 + ((float) constraintWidget9.mListAnchors[i2].getMargin()) + ((float) constraintWidget9.mListAnchors[i2 + 1].getMargin());
            }
            ConstraintAnchor constraintAnchor = constraintWidget9.mListAnchors[i2];
            if (constraintWidget9.getVisibility() != 8 && constraintWidget9.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                i3++;
                if (i != 0) {
                    z3 = false;
                    if (constraintWidget9.mMatchConstraintDefaultHeight != 0) {
                        return false;
                    }
                    if (constraintWidget9.mMatchConstraintMinHeight == 0) {
                        if (constraintWidget9.mMatchConstraintMaxHeight != 0) {
                        }
                    }
                    return z3;
                } else if (constraintWidget9.mMatchConstraintDefaultWidth != 0) {
                    return false;
                } else {
                    z3 = false;
                    if (!(constraintWidget9.mMatchConstraintMinWidth == 0 && constraintWidget9.mMatchConstraintMaxWidth == 0)) {
                        return false;
                    }
                }
                if (constraintWidget9.mDimensionRatio != 0.0f) {
                    return z3;
                }
            }
            ConstraintAnchor constraintAnchor2 = constraintWidget9.mListAnchors[i2 + 1].mTarget;
            if (constraintAnchor2 != null) {
                ConstraintWidget constraintWidget10 = constraintAnchor2.mOwner;
                ConstraintAnchor[] constraintAnchorArr = constraintWidget10.mListAnchors;
                if (constraintAnchorArr[i2].mTarget != null && constraintAnchorArr[i2].mTarget.mOwner == constraintWidget9) {
                    constraintWidget = constraintWidget10;
                    if (constraintWidget == null) {
                        constraintWidget9 = constraintWidget;
                    } else {
                        z5 = true;
                    }
                }
            }
            constraintWidget = null;
            if (constraintWidget == null) {
            }
        }
        ResolutionAnchor resolutionNode = constraintWidget2.mListAnchors[i2].getResolutionNode();
        int i5 = i2 + 1;
        ResolutionAnchor resolutionNode2 = constraintWidget3.mListAnchors[i5].getResolutionNode();
        ResolutionAnchor resolutionAnchor2 = resolutionNode.target;
        if (resolutionAnchor2 == null || (resolutionAnchor = resolutionNode2.target) == null || ((ResolutionNode) resolutionAnchor2).state != 1 || ((ResolutionNode) resolutionAnchor).state != 1) {
            return false;
        }
        if (i3 > 0 && i3 != i4) {
            return false;
        }
        if (z4 || z2 || z) {
            f2 = constraintWidget4 != null ? (float) constraintWidget4.mListAnchors[i2].getMargin() : 0.0f;
            if (constraintWidget5 != null) {
                f2 += (float) constraintWidget5.mListAnchors[i5].getMargin();
            }
        } else {
            f2 = 0.0f;
        }
        float f7 = resolutionNode.target.resolvedOffset;
        float f8 = resolutionNode2.target.resolvedOffset;
        float f9 = (f7 < f8 ? f8 - f7 : f7 - f8) - f5;
        if (i3 <= 0 || i3 != i4) {
            if (f9 < 0.0f) {
                z4 = true;
                z2 = false;
                z = false;
            }
            if (z4) {
                ConstraintWidget constraintWidget11 = constraintWidget2;
                float biasPercent = f7 + ((f9 - f2) * constraintWidget11.getBiasPercent(i));
                while (constraintWidget11 != null) {
                    Metrics metrics = LinearSystem.sMetrics;
                    if (metrics != null) {
                        metrics.nonresolvedWidgets--;
                        metrics.resolvedWidgets++;
                        metrics.chainConnectionResolved++;
                    }
                    ConstraintWidget constraintWidget12 = constraintWidget11.mNextChainWidget[i];
                    if (constraintWidget12 != null || constraintWidget11 == constraintWidget3) {
                        int width = i == 0 ? constraintWidget11.getWidth() : constraintWidget11.getHeight();
                        float margin = biasPercent + ((float) constraintWidget11.mListAnchors[i2].getMargin());
                        constraintWidget11.mListAnchors[i2].getResolutionNode().resolve(resolutionNode.resolvedTarget, margin);
                        float f10 = margin + ((float) width);
                        constraintWidget11.mListAnchors[i5].getResolutionNode().resolve(resolutionNode.resolvedTarget, f10);
                        constraintWidget11.mListAnchors[i2].getResolutionNode().addResolvedValue(linearSystem);
                        constraintWidget11.mListAnchors[i5].getResolutionNode().addResolvedValue(linearSystem);
                        biasPercent = f10 + ((float) constraintWidget11.mListAnchors[i5].getMargin());
                    }
                    constraintWidget11 = constraintWidget12;
                }
                return true;
            }
            ConstraintWidget constraintWidget13 = constraintWidget2;
            if (!z2 && !z) {
                return true;
            }
            if (z2 || z) {
                f9 -= f2;
            }
            float f11 = f9 / ((float) (i4 + 1));
            if (z) {
                f11 = f9 / (i4 > 1 ? (float) (i4 - 1) : 2.0f);
            }
            float f12 = constraintWidget13.getVisibility() != 8 ? f7 + f11 : f7;
            if (z && i4 > 1) {
                f12 = ((float) constraintWidget4.mListAnchors[i2].getMargin()) + f7;
            }
            if (z2 && constraintWidget4 != null) {
                f12 += (float) constraintWidget4.mListAnchors[i2].getMargin();
            }
            while (constraintWidget13 != null) {
                Metrics metrics2 = LinearSystem.sMetrics;
                if (metrics2 != null) {
                    metrics2.nonresolvedWidgets--;
                    metrics2.resolvedWidgets++;
                    metrics2.chainConnectionResolved++;
                }
                ConstraintWidget constraintWidget14 = constraintWidget13.mNextChainWidget[i];
                if (constraintWidget14 != null || constraintWidget13 == constraintWidget3) {
                    float width2 = (float) (i == 0 ? constraintWidget13.getWidth() : constraintWidget13.getHeight());
                    if (constraintWidget13 != constraintWidget4) {
                        f12 += (float) constraintWidget13.mListAnchors[i2].getMargin();
                    }
                    constraintWidget13.mListAnchors[i2].getResolutionNode().resolve(resolutionNode.resolvedTarget, f12);
                    constraintWidget13.mListAnchors[i5].getResolutionNode().resolve(resolutionNode.resolvedTarget, f12 + width2);
                    constraintWidget13.mListAnchors[i2].getResolutionNode().addResolvedValue(linearSystem);
                    constraintWidget13.mListAnchors[i5].getResolutionNode().addResolvedValue(linearSystem);
                    f12 += width2 + ((float) constraintWidget13.mListAnchors[i5].getMargin());
                    if (constraintWidget14 != null) {
                        if (constraintWidget14.getVisibility() != 8) {
                            f12 += f11;
                        }
                        constraintWidget13 = constraintWidget14;
                    }
                }
                constraintWidget13 = constraintWidget14;
            }
            return true;
        } else if (constraintWidget9.getParent() != null && constraintWidget9.getParent().mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            return false;
        } else {
            float f13 = (f9 + f5) - f6;
            float f14 = f7;
            ConstraintWidget constraintWidget15 = constraintWidget2;
            while (constraintWidget15 != null) {
                Metrics metrics3 = LinearSystem.sMetrics;
                if (metrics3 != null) {
                    metrics3.nonresolvedWidgets--;
                    metrics3.resolvedWidgets++;
                    metrics3.chainConnectionResolved++;
                }
                ConstraintWidget constraintWidget16 = constraintWidget15.mNextChainWidget[i];
                if (constraintWidget16 != null || constraintWidget15 == constraintWidget3) {
                    float f15 = f13 / ((float) i3);
                    if (f4 > 0.0f) {
                        float[] fArr = constraintWidget15.mWeight;
                        if (fArr[i] == -1.0f) {
                            f3 = 0.0f;
                            if (constraintWidget15.getVisibility() == 8) {
                                f3 = 0.0f;
                            }
                            float margin2 = f14 + ((float) constraintWidget15.mListAnchors[i2].getMargin());
                            constraintWidget15.mListAnchors[i2].getResolutionNode().resolve(resolutionNode.resolvedTarget, margin2);
                            float f16 = margin2 + f3;
                            constraintWidget15.mListAnchors[i5].getResolutionNode().resolve(resolutionNode.resolvedTarget, f16);
                            constraintWidget15.mListAnchors[i2].getResolutionNode().addResolvedValue(linearSystem);
                            constraintWidget15.mListAnchors[i5].getResolutionNode().addResolvedValue(linearSystem);
                            f14 = f16 + ((float) constraintWidget15.mListAnchors[i5].getMargin());
                        } else {
                            f15 = (fArr[i] * f13) / f4;
                        }
                    }
                    f3 = f15;
                    if (constraintWidget15.getVisibility() == 8) {
                    }
                    float margin22 = f14 + ((float) constraintWidget15.mListAnchors[i2].getMargin());
                    constraintWidget15.mListAnchors[i2].getResolutionNode().resolve(resolutionNode.resolvedTarget, margin22);
                    float f162 = margin22 + f3;
                    constraintWidget15.mListAnchors[i5].getResolutionNode().resolve(resolutionNode.resolvedTarget, f162);
                    constraintWidget15.mListAnchors[i2].getResolutionNode().addResolvedValue(linearSystem);
                    constraintWidget15.mListAnchors[i5].getResolutionNode().addResolvedValue(linearSystem);
                    f14 = f162 + ((float) constraintWidget15.mListAnchors[i5].getMargin());
                }
                constraintWidget15 = constraintWidget16;
            }
            return true;
        }
    }

    static void checkMatchParent(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        if (((ConstraintWidget) constraintWidgetContainer).mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int i = constraintWidget.mLeft.mMargin;
            int width = constraintWidgetContainer.getWidth() - constraintWidget.mRight.mMargin;
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            constraintAnchor.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor);
            ConstraintAnchor constraintAnchor2 = constraintWidget.mRight;
            constraintAnchor2.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor2);
            linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, i);
            linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, width);
            constraintWidget.mHorizontalResolution = 2;
            constraintWidget.setHorizontalDimension(i, width);
        }
        if (((ConstraintWidget) constraintWidgetContainer).mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int i2 = constraintWidget.mTop.mMargin;
            int height = constraintWidgetContainer.getHeight() - constraintWidget.mBottom.mMargin;
            ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
            constraintAnchor3.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor3);
            ConstraintAnchor constraintAnchor4 = constraintWidget.mBottom;
            constraintAnchor4.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor4);
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, i2);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, height);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                ConstraintAnchor constraintAnchor5 = constraintWidget.mBaseline;
                constraintAnchor5.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor5);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + i2);
            }
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.setVerticalDimension(i2, height);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x003b A[RETURN] */
    private static boolean optimizableMatchConstraint(ConstraintWidget constraintWidget, int i) {
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
        if (dimensionBehaviourArr[i] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            return false;
        }
        char c2 = 1;
        if (constraintWidget.mDimensionRatio != 0.0f) {
            if (i != 0) {
                c2 = 0;
            }
            if (dimensionBehaviourArr[c2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            }
            return false;
        }
        if (i == 0) {
            return constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.mMatchConstraintMinWidth == 0 && constraintWidget.mMatchConstraintMaxWidth == 0;
        }
        if (constraintWidget.mMatchConstraintDefaultHeight != 0 || constraintWidget.mMatchConstraintMinHeight != 0 || constraintWidget.mMatchConstraintMaxHeight != 0) {
            return false;
        }
    }

    static void setOptimizedWidget(ConstraintWidget constraintWidget, int i, int i2) {
        int i3 = i * 2;
        int i4 = i3 + 1;
        constraintWidget.mListAnchors[i3].getResolutionNode().resolvedTarget = constraintWidget.getParent().mLeft.getResolutionNode();
        constraintWidget.mListAnchors[i3].getResolutionNode().resolvedOffset = (float) i2;
        ((ResolutionNode) constraintWidget.mListAnchors[i3].getResolutionNode()).state = 1;
        constraintWidget.mListAnchors[i4].getResolutionNode().resolvedTarget = constraintWidget.mListAnchors[i3].getResolutionNode();
        constraintWidget.mListAnchors[i4].getResolutionNode().resolvedOffset = (float) constraintWidget.getLength(i);
        ((ResolutionNode) constraintWidget.mListAnchors[i4].getResolutionNode()).state = 1;
    }
}
