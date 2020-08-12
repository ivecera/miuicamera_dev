package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

public class Barrier extends Helper {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private boolean mAllowsGoneWidget = true;
    private int mBarrierType = 0;
    private ArrayList<ResolutionAnchor> mNodes = new ArrayList<>(4);

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        ConstraintAnchor[] constraintAnchorArr;
        boolean z;
        int i;
        int i2;
        ConstraintAnchor[] constraintAnchorArr2 = ((ConstraintWidget) this).mListAnchors;
        constraintAnchorArr2[0] = ((ConstraintWidget) this).mLeft;
        constraintAnchorArr2[2] = ((ConstraintWidget) this).mTop;
        constraintAnchorArr2[1] = ((ConstraintWidget) this).mRight;
        constraintAnchorArr2[3] = ((ConstraintWidget) this).mBottom;
        int i3 = 0;
        while (true) {
            constraintAnchorArr = ((ConstraintWidget) this).mListAnchors;
            if (i3 >= constraintAnchorArr.length) {
                break;
            }
            constraintAnchorArr[i3].mSolverVariable = linearSystem.createObjectVariable(constraintAnchorArr[i3]);
            i3++;
        }
        int i4 = this.mBarrierType;
        if (i4 >= 0 && i4 < 4) {
            ConstraintAnchor constraintAnchor = constraintAnchorArr[i4];
            int i5 = 0;
            while (true) {
                if (i5 >= ((Helper) this).mWidgetsCount) {
                    z = false;
                    break;
                }
                ConstraintWidget constraintWidget = ((Helper) this).mWidgets[i5];
                if ((this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) && ((((i = this.mBarrierType) == 0 || i == 1) && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (((i2 = this.mBarrierType) == 2 || i2 == 3) && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))) {
                    z = true;
                } else {
                    i5++;
                }
            }
            int i6 = this.mBarrierType;
            if (i6 == 0 || i6 == 1 ? getParent().getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT : getParent().getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                z = false;
            }
            for (int i7 = 0; i7 < ((Helper) this).mWidgetsCount; i7++) {
                ConstraintWidget constraintWidget2 = ((Helper) this).mWidgets[i7];
                if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                    SolverVariable createObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.mBarrierType]);
                    ConstraintAnchor[] constraintAnchorArr3 = constraintWidget2.mListAnchors;
                    int i8 = this.mBarrierType;
                    constraintAnchorArr3[i8].mSolverVariable = createObjectVariable;
                    if (i8 == 0 || i8 == 2) {
                        linearSystem.addLowerBarrier(constraintAnchor.mSolverVariable, createObjectVariable, z);
                    } else {
                        linearSystem.addGreaterBarrier(constraintAnchor.mSolverVariable, createObjectVariable, z);
                    }
                }
            }
            int i9 = this.mBarrierType;
            if (i9 == 0) {
                linearSystem.addEquality(((ConstraintWidget) this).mRight.mSolverVariable, ((ConstraintWidget) this).mLeft.mSolverVariable, 0, 6);
                if (!z) {
                    linearSystem.addEquality(((ConstraintWidget) this).mLeft.mSolverVariable, ((ConstraintWidget) this).mParent.mRight.mSolverVariable, 0, 5);
                }
            } else if (i9 == 1) {
                linearSystem.addEquality(((ConstraintWidget) this).mLeft.mSolverVariable, ((ConstraintWidget) this).mRight.mSolverVariable, 0, 6);
                if (!z) {
                    linearSystem.addEquality(((ConstraintWidget) this).mLeft.mSolverVariable, ((ConstraintWidget) this).mParent.mLeft.mSolverVariable, 0, 5);
                }
            } else if (i9 == 2) {
                linearSystem.addEquality(((ConstraintWidget) this).mBottom.mSolverVariable, ((ConstraintWidget) this).mTop.mSolverVariable, 0, 6);
                if (!z) {
                    linearSystem.addEquality(((ConstraintWidget) this).mTop.mSolverVariable, ((ConstraintWidget) this).mParent.mBottom.mSolverVariable, 0, 5);
                }
            } else if (i9 == 3) {
                linearSystem.addEquality(((ConstraintWidget) this).mTop.mSolverVariable, ((ConstraintWidget) this).mBottom.mSolverVariable, 0, 6);
                if (!z) {
                    linearSystem.addEquality(((ConstraintWidget) this).mTop.mSolverVariable, ((ConstraintWidget) this).mParent.mTop.mSolverVariable, 0, 5);
                }
            }
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public boolean allowedInBarrier() {
        return true;
    }

    public boolean allowsGoneWidget() {
        return this.mAllowsGoneWidget;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void analyze(int i) {
        ResolutionAnchor resolutionAnchor;
        ConstraintWidget constraintWidget = ((ConstraintWidget) this).mParent;
        if (constraintWidget != null && ((ConstraintWidgetContainer) constraintWidget).optimizeFor(2)) {
            int i2 = this.mBarrierType;
            if (i2 == 0) {
                resolutionAnchor = ((ConstraintWidget) this).mLeft.getResolutionNode();
            } else if (i2 == 1) {
                resolutionAnchor = ((ConstraintWidget) this).mRight.getResolutionNode();
            } else if (i2 == 2) {
                resolutionAnchor = ((ConstraintWidget) this).mTop.getResolutionNode();
            } else if (i2 == 3) {
                resolutionAnchor = ((ConstraintWidget) this).mBottom.getResolutionNode();
            } else {
                return;
            }
            resolutionAnchor.setType(5);
            int i3 = this.mBarrierType;
            if (i3 == 0 || i3 == 1) {
                ((ConstraintWidget) this).mTop.getResolutionNode().resolve(null, 0.0f);
                ((ConstraintWidget) this).mBottom.getResolutionNode().resolve(null, 0.0f);
            } else {
                ((ConstraintWidget) this).mLeft.getResolutionNode().resolve(null, 0.0f);
                ((ConstraintWidget) this).mRight.getResolutionNode().resolve(null, 0.0f);
            }
            this.mNodes.clear();
            for (int i4 = 0; i4 < ((Helper) this).mWidgetsCount; i4++) {
                ConstraintWidget constraintWidget2 = ((Helper) this).mWidgets[i4];
                if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                    int i5 = this.mBarrierType;
                    ResolutionAnchor resolutionNode = i5 != 0 ? i5 != 1 ? i5 != 2 ? i5 != 3 ? null : constraintWidget2.mBottom.getResolutionNode() : constraintWidget2.mTop.getResolutionNode() : constraintWidget2.mRight.getResolutionNode() : constraintWidget2.mLeft.getResolutionNode();
                    if (resolutionNode != null) {
                        this.mNodes.add(resolutionNode);
                        resolutionNode.addDependent(resolutionAnchor);
                    }
                }
            }
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void resetResolutionNodes() {
        super.resetResolutionNodes();
        this.mNodes.clear();
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void resolve() {
        ResolutionAnchor resolutionAnchor;
        float f2;
        ResolutionAnchor resolutionAnchor2;
        int i = this.mBarrierType;
        float f3 = Float.MAX_VALUE;
        if (i != 0) {
            if (i == 1) {
                resolutionAnchor = ((ConstraintWidget) this).mRight.getResolutionNode();
            } else if (i == 2) {
                resolutionAnchor = ((ConstraintWidget) this).mTop.getResolutionNode();
            } else if (i == 3) {
                resolutionAnchor = ((ConstraintWidget) this).mBottom.getResolutionNode();
            } else {
                return;
            }
            f3 = 0.0f;
        } else {
            resolutionAnchor = ((ConstraintWidget) this).mLeft.getResolutionNode();
        }
        int size = this.mNodes.size();
        ResolutionAnchor resolutionAnchor3 = null;
        int i2 = 0;
        while (i2 < size) {
            ResolutionAnchor resolutionAnchor4 = this.mNodes.get(i2);
            if (((ResolutionNode) resolutionAnchor4).state == 1) {
                int i3 = this.mBarrierType;
                if (i3 == 0 || i3 == 2) {
                    f2 = resolutionAnchor4.resolvedOffset;
                    if (f2 < f3) {
                        resolutionAnchor2 = resolutionAnchor4.resolvedTarget;
                    } else {
                        i2++;
                    }
                } else {
                    f2 = resolutionAnchor4.resolvedOffset;
                    if (f2 > f3) {
                        resolutionAnchor2 = resolutionAnchor4.resolvedTarget;
                    } else {
                        i2++;
                    }
                }
                resolutionAnchor3 = resolutionAnchor2;
                f3 = f2;
                i2++;
            } else {
                return;
            }
        }
        if (LinearSystem.getMetrics() != null) {
            LinearSystem.getMetrics().barrierConnectionResolved++;
        }
        resolutionAnchor.resolvedTarget = resolutionAnchor3;
        resolutionAnchor.resolvedOffset = f3;
        resolutionAnchor.didResolve();
        int i4 = this.mBarrierType;
        if (i4 == 0) {
            ((ConstraintWidget) this).mRight.getResolutionNode().resolve(resolutionAnchor3, f3);
        } else if (i4 == 1) {
            ((ConstraintWidget) this).mLeft.getResolutionNode().resolve(resolutionAnchor3, f3);
        } else if (i4 == 2) {
            ((ConstraintWidget) this).mBottom.getResolutionNode().resolve(resolutionAnchor3, f3);
        } else if (i4 == 3) {
            ((ConstraintWidget) this).mTop.getResolutionNode().resolve(resolutionAnchor3, f3);
        }
    }

    public void setAllowsGoneWidget(boolean z) {
        this.mAllowsGoneWidget = z;
    }

    public void setBarrierType(int i) {
        this.mBarrierType = i;
    }
}
