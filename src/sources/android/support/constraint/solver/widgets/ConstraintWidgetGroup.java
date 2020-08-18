package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintAnchor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConstraintWidgetGroup {
    public List<ConstraintWidget> mConstrainedGroup;
    public final int[] mGroupDimensions = {this.mGroupWidth, this.mGroupHeight};
    int mGroupHeight = -1;
    int mGroupWidth = -1;
    public boolean mSkipSolver = false;
    List<ConstraintWidget> mStartHorizontalWidgets = new ArrayList();
    List<ConstraintWidget> mStartVerticalWidgets = new ArrayList();
    List<ConstraintWidget> mUnresolvedWidgets = new ArrayList();
    HashSet<ConstraintWidget> mWidgetsToSetHorizontal = new HashSet<>();
    HashSet<ConstraintWidget> mWidgetsToSetVertical = new HashSet<>();
    List<ConstraintWidget> mWidgetsToSolve = new ArrayList();

    ConstraintWidgetGroup(List<ConstraintWidget> list) {
        this.mConstrainedGroup = list;
    }

    ConstraintWidgetGroup(List<ConstraintWidget> list, boolean z) {
        this.mConstrainedGroup = list;
        this.mSkipSolver = z;
    }

    private void getWidgetsToSolveTraversal(ArrayList<ConstraintWidget> arrayList, ConstraintWidget constraintWidget) {
        if (!constraintWidget.mGroupsToSolver) {
            arrayList.add(constraintWidget);
            constraintWidget.mGroupsToSolver = true;
            if (!constraintWidget.isFullyResolved()) {
                if (constraintWidget instanceof Helper) {
                    Helper helper = (Helper) constraintWidget;
                    int i = helper.mWidgetsCount;
                    for (int i2 = 0; i2 < i; i2++) {
                        getWidgetsToSolveTraversal(arrayList, helper.mWidgets[i2]);
                    }
                }
                int length = constraintWidget.mListAnchors.length;
                for (int i3 = 0; i3 < length; i3++) {
                    ConstraintAnchor constraintAnchor = constraintWidget.mListAnchors[i3].mTarget;
                    if (constraintAnchor != null) {
                        ConstraintWidget constraintWidget2 = constraintAnchor.mOwner;
                        if (!(constraintAnchor == null || constraintWidget2 == constraintWidget.getParent())) {
                            getWidgetsToSolveTraversal(arrayList, constraintWidget2);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0087  */
    private void updateResolvedDimension(ConstraintWidget constraintWidget) {
        int i;
        ConstraintAnchor constraintAnchor;
        if (constraintWidget.mOptimizerMeasurable && !constraintWidget.isFullyResolved()) {
            boolean z = false;
            boolean z2 = constraintWidget.mRight.mTarget != null;
            ConstraintAnchor constraintAnchor2 = z2 ? constraintWidget.mRight.mTarget : constraintWidget.mLeft.mTarget;
            if (constraintAnchor2 != null) {
                ConstraintWidget constraintWidget2 = constraintAnchor2.mOwner;
                if (!constraintWidget2.mOptimizerMeasured) {
                    updateResolvedDimension(constraintWidget2);
                }
                ConstraintAnchor.Type type = constraintAnchor2.mType;
                if (type == ConstraintAnchor.Type.RIGHT) {
                    ConstraintWidget constraintWidget3 = constraintAnchor2.mOwner;
                    i = constraintWidget3.getWidth() + constraintWidget3.mX;
                } else if (type == ConstraintAnchor.Type.LEFT) {
                    i = constraintAnchor2.mOwner.mX;
                }
                int margin = !z2 ? i - constraintWidget.mRight.getMargin() : i + constraintWidget.mLeft.getMargin() + constraintWidget.getWidth();
                constraintWidget.setHorizontalDimension(margin - constraintWidget.getWidth(), margin);
                constraintAnchor = constraintWidget.mBaseline.mTarget;
                if (constraintAnchor == null) {
                    ConstraintWidget constraintWidget4 = constraintAnchor.mOwner;
                    if (!constraintWidget4.mOptimizerMeasured) {
                        updateResolvedDimension(constraintWidget4);
                    }
                    ConstraintWidget constraintWidget5 = constraintAnchor.mOwner;
                    int i2 = (constraintWidget5.mY + constraintWidget5.mBaselineDistance) - constraintWidget.mBaselineDistance;
                    constraintWidget.setVerticalDimension(i2, constraintWidget.mHeight + i2);
                    constraintWidget.mOptimizerMeasured = true;
                    return;
                }
                if (constraintWidget.mBottom.mTarget != null) {
                    z = true;
                }
                ConstraintAnchor constraintAnchor3 = z ? constraintWidget.mBottom.mTarget : constraintWidget.mTop.mTarget;
                if (constraintAnchor3 != null) {
                    ConstraintWidget constraintWidget6 = constraintAnchor3.mOwner;
                    if (!constraintWidget6.mOptimizerMeasured) {
                        updateResolvedDimension(constraintWidget6);
                    }
                    ConstraintAnchor.Type type2 = constraintAnchor3.mType;
                    if (type2 == ConstraintAnchor.Type.BOTTOM) {
                        ConstraintWidget constraintWidget7 = constraintAnchor3.mOwner;
                        margin = constraintWidget7.mY + constraintWidget7.getHeight();
                    } else if (type2 == ConstraintAnchor.Type.TOP) {
                        margin = constraintAnchor3.mOwner.mY;
                    }
                }
                int margin2 = z ? margin - constraintWidget.mBottom.getMargin() : margin + constraintWidget.mTop.getMargin() + constraintWidget.getHeight();
                constraintWidget.setVerticalDimension(margin2 - constraintWidget.getHeight(), margin2);
                constraintWidget.mOptimizerMeasured = true;
                return;
            }
            i = 0;
            if (!z2) {
            }
            constraintWidget.setHorizontalDimension(margin - constraintWidget.getWidth(), margin);
            constraintAnchor = constraintWidget.mBaseline.mTarget;
            if (constraintAnchor == null) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addWidgetsToSet(ConstraintWidget constraintWidget, int i) {
        if (i == 0) {
            this.mWidgetsToSetHorizontal.add(constraintWidget);
        } else if (i == 1) {
            this.mWidgetsToSetVertical.add(constraintWidget);
        }
    }

    public List<ConstraintWidget> getStartWidgets(int i) {
        if (i == 0) {
            return this.mStartHorizontalWidgets;
        }
        if (i == 1) {
            return this.mStartVerticalWidgets;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Set<ConstraintWidget> getWidgetsToSet(int i) {
        if (i == 0) {
            return this.mWidgetsToSetHorizontal;
        }
        if (i == 1) {
            return this.mWidgetsToSetVertical;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public List<ConstraintWidget> getWidgetsToSolve() {
        if (!this.mWidgetsToSolve.isEmpty()) {
            return this.mWidgetsToSolve;
        }
        int size = this.mConstrainedGroup.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = this.mConstrainedGroup.get(i);
            if (!constraintWidget.mOptimizerMeasurable) {
                getWidgetsToSolveTraversal((ArrayList) this.mWidgetsToSolve, constraintWidget);
            }
        }
        this.mUnresolvedWidgets.clear();
        this.mUnresolvedWidgets.addAll(this.mConstrainedGroup);
        this.mUnresolvedWidgets.removeAll(this.mWidgetsToSolve);
        return this.mWidgetsToSolve;
    }

    /* access modifiers changed from: package-private */
    public void updateUnresolvedWidgets() {
        int size = this.mUnresolvedWidgets.size();
        for (int i = 0; i < size; i++) {
            updateResolvedDimension(this.mUnresolvedWidgets.get(i));
        }
    }
}
