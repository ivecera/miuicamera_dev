package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        if (i == 0) {
            int i4 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i2 = i4;
            i3 = 0;
        } else {
            i3 = 2;
            i2 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            ChainHead chainHead = chainHeadArr[i5];
            chainHead.define();
            if (!constraintWidgetContainer.optimizeFor(4)) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            } else if (!Optimizer.applyChainOptimized(constraintWidgetContainer, linearSystem, i, i3, chainHead)) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r2.mHorizontalChainStyle == 2) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0048, code lost:
        if (r2.mVerticalChainStyle == 2) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004c, code lost:
        r5 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x0366  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0386  */
    /* JADX WARNING: Removed duplicated region for block: B:251:0x0452  */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x0487  */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x04ac  */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x04af  */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x04b5  */
    /* JADX WARNING: Removed duplicated region for block: B:270:0x04b8  */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x04bc  */
    /* JADX WARNING: Removed duplicated region for block: B:278:0x04cc  */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x0367 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0188  */
    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i, int i2, ChainHead chainHead) {
        boolean z;
        boolean z2;
        SolverVariable solverVariable;
        ConstraintWidget constraintWidget;
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        int i3;
        ConstraintWidget constraintWidget2;
        int i4;
        SolverVariable solverVariable2;
        SolverVariable solverVariable3;
        ConstraintAnchor constraintAnchor4;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        SolverVariable solverVariable4;
        SolverVariable solverVariable5;
        ConstraintAnchor constraintAnchor5;
        int size;
        int i5;
        ArrayList<ConstraintWidget> arrayList;
        int i6;
        float f2;
        boolean z3;
        int i7;
        ConstraintWidget constraintWidget5;
        boolean z4;
        int i8;
        ConstraintWidget constraintWidget6 = chainHead.mFirst;
        ConstraintWidget constraintWidget7 = chainHead.mLast;
        ConstraintWidget constraintWidget8 = chainHead.mFirstVisibleWidget;
        ConstraintWidget constraintWidget9 = chainHead.mLastVisibleWidget;
        ConstraintWidget constraintWidget10 = chainHead.mHead;
        float f3 = chainHead.mTotalWeight;
        ConstraintWidget constraintWidget11 = chainHead.mFirstMatchConstraintWidget;
        ConstraintWidget constraintWidget12 = chainHead.mLastMatchConstraintWidget;
        boolean z5 = ((ConstraintWidget) constraintWidgetContainer).mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i == 0) {
            z2 = constraintWidget10.mHorizontalChainStyle == 0;
            z = constraintWidget10.mHorizontalChainStyle == 1;
        } else {
            z2 = constraintWidget10.mVerticalChainStyle == 0;
            z = constraintWidget10.mVerticalChainStyle == 1;
        }
        boolean z6 = true;
        boolean z7 = z2;
        ConstraintWidget constraintWidget13 = constraintWidget6;
        boolean z8 = false;
        while (true) {
            ConstraintWidget constraintWidget14 = null;
            if (z8) {
                break;
            }
            ConstraintAnchor constraintAnchor6 = constraintWidget13.mListAnchors[i2];
            int i9 = (z5 || z6) ? 1 : 4;
            int margin = constraintAnchor6.getMargin();
            ConstraintAnchor constraintAnchor7 = constraintAnchor6.mTarget;
            if (!(constraintAnchor7 == null || constraintWidget13 == constraintWidget6)) {
                margin += constraintAnchor7.getMargin();
            }
            if (z6 && constraintWidget13 != constraintWidget6 && constraintWidget13 != constraintWidget8) {
                f2 = f3;
                z3 = z8;
                i7 = 6;
            } else if (!z7 || !z5) {
                f2 = f3;
                i7 = i9;
                z3 = z8;
            } else {
                f2 = f3;
                z3 = z8;
                i7 = 4;
            }
            ConstraintAnchor constraintAnchor8 = constraintAnchor6.mTarget;
            if (constraintAnchor8 != null) {
                if (constraintWidget13 == constraintWidget8) {
                    z4 = z7;
                    constraintWidget5 = constraintWidget10;
                    linearSystem.addGreaterThan(constraintAnchor6.mSolverVariable, constraintAnchor8.mSolverVariable, margin, 5);
                } else {
                    constraintWidget5 = constraintWidget10;
                    z4 = z7;
                    linearSystem.addGreaterThan(constraintAnchor6.mSolverVariable, constraintAnchor8.mSolverVariable, margin, 6);
                }
                linearSystem.addEquality(constraintAnchor6.mSolverVariable, constraintAnchor6.mTarget.mSolverVariable, margin, i7);
            } else {
                constraintWidget5 = constraintWidget10;
                z4 = z7;
            }
            if (z5) {
                if (constraintWidget13.getVisibility() == 8 || constraintWidget13.mListDimensionBehaviors[i] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i8 = 0;
                } else {
                    ConstraintAnchor[] constraintAnchorArr = constraintWidget13.mListAnchors;
                    i8 = 0;
                    linearSystem.addGreaterThan(constraintAnchorArr[i2 + 1].mSolverVariable, constraintAnchorArr[i2].mSolverVariable, 0, 5);
                }
                linearSystem.addGreaterThan(constraintWidget13.mListAnchors[i2].mSolverVariable, ((ConstraintWidget) constraintWidgetContainer).mListAnchors[i2].mSolverVariable, i8, 6);
            }
            ConstraintAnchor constraintAnchor9 = constraintWidget13.mListAnchors[i2 + 1].mTarget;
            if (constraintAnchor9 != null) {
                ConstraintWidget constraintWidget15 = constraintAnchor9.mOwner;
                ConstraintAnchor[] constraintAnchorArr2 = constraintWidget15.mListAnchors;
                if (constraintAnchorArr2[i2].mTarget != null && constraintAnchorArr2[i2].mTarget.mOwner == constraintWidget13) {
                    constraintWidget14 = constraintWidget15;
                }
            }
            if (constraintWidget14 != null) {
                constraintWidget13 = constraintWidget14;
                z8 = z3;
            } else {
                z8 = true;
            }
            f3 = f2;
            z7 = z4;
            constraintWidget10 = constraintWidget5;
        }
        if (constraintWidget9 != null) {
            ConstraintAnchor[] constraintAnchorArr3 = constraintWidget7.mListAnchors;
            int i10 = i2 + 1;
            if (constraintAnchorArr3[i10].mTarget != null) {
                ConstraintAnchor constraintAnchor10 = constraintWidget9.mListAnchors[i10];
                linearSystem.addLowerThan(constraintAnchor10.mSolverVariable, constraintAnchorArr3[i10].mTarget.mSolverVariable, -constraintAnchor10.getMargin(), 5);
                if (z5) {
                    int i11 = i2 + 1;
                    SolverVariable solverVariable6 = ((ConstraintWidget) constraintWidgetContainer).mListAnchors[i11].mSolverVariable;
                    ConstraintAnchor[] constraintAnchorArr4 = constraintWidget7.mListAnchors;
                    linearSystem.addGreaterThan(solverVariable6, constraintAnchorArr4[i11].mSolverVariable, constraintAnchorArr4[i11].getMargin(), 6);
                }
                ArrayList<ConstraintWidget> arrayList2 = chainHead.mWeightedMatchConstraintsWidgets;
                if (arrayList2 != null && (size = arrayList2.size()) > 1) {
                    float f4 = (chainHead.mHasUndefinedWeights || chainHead.mHasComplexMatchWeights) ? f3 : (float) chainHead.mWidgetsMatchCount;
                    float f5 = 0.0f;
                    float f6 = 0.0f;
                    ConstraintWidget constraintWidget16 = null;
                    i5 = 0;
                    while (i5 < size) {
                        ConstraintWidget constraintWidget17 = arrayList2.get(i5);
                        float f7 = constraintWidget17.mWeight[i];
                        if (f7 < f5) {
                            if (chainHead.mHasComplexMatchWeights) {
                                ConstraintAnchor[] constraintAnchorArr5 = constraintWidget17.mListAnchors;
                                linearSystem.addEquality(constraintAnchorArr5[i2 + 1].mSolverVariable, constraintAnchorArr5[i2].mSolverVariable, 0, 4);
                                arrayList = arrayList2;
                                i6 = size;
                                i5++;
                                size = i6;
                                arrayList2 = arrayList;
                                f5 = 0.0f;
                            } else {
                                f7 = 1.0f;
                                f5 = 0.0f;
                            }
                        }
                        if (f7 == f5) {
                            ConstraintAnchor[] constraintAnchorArr6 = constraintWidget17.mListAnchors;
                            linearSystem.addEquality(constraintAnchorArr6[i2 + 1].mSolverVariable, constraintAnchorArr6[i2].mSolverVariable, 0, 6);
                            arrayList = arrayList2;
                            i6 = size;
                            i5++;
                            size = i6;
                            arrayList2 = arrayList;
                            f5 = 0.0f;
                        } else {
                            if (constraintWidget16 != null) {
                                ConstraintAnchor[] constraintAnchorArr7 = constraintWidget16.mListAnchors;
                                SolverVariable solverVariable7 = constraintAnchorArr7[i2].mSolverVariable;
                                int i12 = i2 + 1;
                                SolverVariable solverVariable8 = constraintAnchorArr7[i12].mSolverVariable;
                                ConstraintAnchor[] constraintAnchorArr8 = constraintWidget17.mListAnchors;
                                arrayList = arrayList2;
                                SolverVariable solverVariable9 = constraintAnchorArr8[i2].mSolverVariable;
                                SolverVariable solverVariable10 = constraintAnchorArr8[i12].mSolverVariable;
                                i6 = size;
                                ArrayRow createRow = linearSystem.createRow();
                                createRow.createRowEqualMatchDimensions(f6, f4, f7, solverVariable7, solverVariable8, solverVariable9, solverVariable10);
                                linearSystem.addConstraint(createRow);
                            } else {
                                arrayList = arrayList2;
                                i6 = size;
                            }
                            f6 = f7;
                            constraintWidget16 = constraintWidget17;
                            i5++;
                            size = i6;
                            arrayList2 = arrayList;
                            f5 = 0.0f;
                        }
                    }
                }
                if (constraintWidget8 == null && (constraintWidget8 == constraintWidget9 || z6)) {
                    ConstraintAnchor[] constraintAnchorArr9 = constraintWidget6.mListAnchors;
                    ConstraintAnchor constraintAnchor11 = constraintAnchorArr9[i2];
                    int i13 = i2 + 1;
                    ConstraintAnchor constraintAnchor12 = constraintWidget7.mListAnchors[i13];
                    SolverVariable solverVariable11 = constraintAnchorArr9[i2].mTarget != null ? constraintAnchorArr9[i2].mTarget.mSolverVariable : null;
                    ConstraintAnchor[] constraintAnchorArr10 = constraintWidget7.mListAnchors;
                    SolverVariable solverVariable12 = constraintAnchorArr10[i13].mTarget != null ? constraintAnchorArr10[i13].mTarget.mSolverVariable : null;
                    if (constraintWidget8 == constraintWidget9) {
                        ConstraintAnchor[] constraintAnchorArr11 = constraintWidget8.mListAnchors;
                        constraintAnchor11 = constraintAnchorArr11[i2];
                        constraintAnchor12 = constraintAnchorArr11[i13];
                    }
                    if (!(solverVariable11 == null || solverVariable12 == null)) {
                        linearSystem.addCentering(constraintAnchor11.mSolverVariable, solverVariable11, constraintAnchor11.getMargin(), i == 0 ? constraintWidget10.mHorizontalBiasPercent : constraintWidget10.mVerticalBiasPercent, solverVariable12, constraintAnchor12.mSolverVariable, constraintAnchor12.getMargin(), 5);
                    }
                } else if (z7 || constraintWidget8 == null) {
                    int i14 = 8;
                    if (z && constraintWidget8 != null) {
                        int i15 = chainHead.mWidgetsMatchCount;
                        boolean z9 = i15 <= 0 && chainHead.mWidgetsCount == i15;
                        constraintWidget = constraintWidget8;
                        ConstraintWidget constraintWidget18 = constraintWidget;
                        while (constraintWidget != null) {
                            ConstraintWidget constraintWidget19 = constraintWidget.mNextChainWidget[i];
                            while (constraintWidget19 != null && constraintWidget19.getVisibility() == i14) {
                                constraintWidget19 = constraintWidget19.mNextChainWidget[i];
                            }
                            if (constraintWidget == constraintWidget8 || constraintWidget == constraintWidget9 || constraintWidget19 == null) {
                                constraintWidget2 = constraintWidget18;
                                i4 = i14;
                            } else {
                                ConstraintWidget constraintWidget20 = constraintWidget19 == constraintWidget9 ? null : constraintWidget19;
                                ConstraintAnchor constraintAnchor13 = constraintWidget.mListAnchors[i2];
                                SolverVariable solverVariable13 = constraintAnchor13.mSolverVariable;
                                ConstraintAnchor constraintAnchor14 = constraintAnchor13.mTarget;
                                if (constraintAnchor14 != null) {
                                    SolverVariable solverVariable14 = constraintAnchor14.mSolverVariable;
                                }
                                int i16 = i2 + 1;
                                SolverVariable solverVariable15 = constraintWidget18.mListAnchors[i16].mSolverVariable;
                                int margin2 = constraintAnchor13.getMargin();
                                int margin3 = constraintWidget.mListAnchors[i16].getMargin();
                                if (constraintWidget20 != null) {
                                    constraintAnchor4 = constraintWidget20.mListAnchors[i2];
                                    solverVariable3 = constraintAnchor4.mSolverVariable;
                                    ConstraintAnchor constraintAnchor15 = constraintAnchor4.mTarget;
                                    solverVariable2 = constraintAnchor15 != null ? constraintAnchor15.mSolverVariable : null;
                                } else {
                                    constraintAnchor4 = constraintWidget.mListAnchors[i16].mTarget;
                                    solverVariable3 = constraintAnchor4 != null ? constraintAnchor4.mSolverVariable : null;
                                    solverVariable2 = constraintWidget.mListAnchors[i16].mSolverVariable;
                                }
                                if (constraintAnchor4 != null) {
                                    margin3 += constraintAnchor4.getMargin();
                                }
                                if (constraintWidget18 != null) {
                                    margin2 += constraintWidget18.mListAnchors[i16].getMargin();
                                }
                                int i17 = z9 ? 6 : 4;
                                if (solverVariable13 == null || solverVariable15 == null || solverVariable3 == null || solverVariable2 == null) {
                                    constraintWidget3 = constraintWidget20;
                                    constraintWidget2 = constraintWidget18;
                                    i4 = 8;
                                } else {
                                    constraintWidget3 = constraintWidget20;
                                    constraintWidget2 = constraintWidget18;
                                    i4 = 8;
                                    linearSystem.addCentering(solverVariable13, solverVariable15, margin2, 0.5f, solverVariable3, solverVariable2, margin3, i17);
                                }
                                constraintWidget19 = constraintWidget3;
                            }
                            if (constraintWidget.getVisibility() == i4) {
                                constraintWidget = constraintWidget2;
                            }
                            i14 = i4;
                            constraintWidget18 = constraintWidget;
                            constraintWidget = constraintWidget19;
                        }
                        ConstraintAnchor constraintAnchor16 = constraintWidget8.mListAnchors[i2];
                        constraintAnchor = constraintWidget6.mListAnchors[i2].mTarget;
                        int i18 = i2 + 1;
                        constraintAnchor2 = constraintWidget9.mListAnchors[i18];
                        constraintAnchor3 = constraintWidget7.mListAnchors[i18].mTarget;
                        if (constraintAnchor != null) {
                            i3 = 5;
                        } else if (constraintWidget8 != constraintWidget9) {
                            i3 = 5;
                            linearSystem.addEquality(constraintAnchor16.mSolverVariable, constraintAnchor.mSolverVariable, constraintAnchor16.getMargin(), 5);
                        } else {
                            i3 = 5;
                            if (constraintAnchor3 != null) {
                                linearSystem.addCentering(constraintAnchor16.mSolverVariable, constraintAnchor.mSolverVariable, constraintAnchor16.getMargin(), 0.5f, constraintAnchor2.mSolverVariable, constraintAnchor3.mSolverVariable, constraintAnchor2.getMargin(), 5);
                            }
                        }
                        if (!(constraintAnchor3 == null || constraintWidget8 == constraintWidget9)) {
                            linearSystem.addEquality(constraintAnchor2.mSolverVariable, constraintAnchor3.mSolverVariable, -constraintAnchor2.getMargin(), i3);
                        }
                    }
                } else {
                    int i19 = chainHead.mWidgetsMatchCount;
                    boolean z10 = i19 > 0 && chainHead.mWidgetsCount == i19;
                    ConstraintWidget constraintWidget21 = constraintWidget8;
                    ConstraintWidget constraintWidget22 = constraintWidget21;
                    while (constraintWidget21 != null) {
                        ConstraintWidget constraintWidget23 = constraintWidget21.mNextChainWidget[i];
                        while (true) {
                            if (constraintWidget23 != null) {
                                if (constraintWidget23.getVisibility() != 8) {
                                    break;
                                }
                                constraintWidget23 = constraintWidget23.mNextChainWidget[i];
                            } else {
                                break;
                            }
                        }
                        if (constraintWidget23 != null || constraintWidget21 == constraintWidget9) {
                            ConstraintAnchor constraintAnchor17 = constraintWidget21.mListAnchors[i2];
                            SolverVariable solverVariable16 = constraintAnchor17.mSolverVariable;
                            ConstraintAnchor constraintAnchor18 = constraintAnchor17.mTarget;
                            SolverVariable solverVariable17 = constraintAnchor18 != null ? constraintAnchor18.mSolverVariable : null;
                            if (constraintWidget22 != constraintWidget21) {
                                solverVariable17 = constraintWidget22.mListAnchors[i2 + 1].mSolverVariable;
                            } else if (constraintWidget21 == constraintWidget8 && constraintWidget22 == constraintWidget21) {
                                ConstraintAnchor[] constraintAnchorArr12 = constraintWidget6.mListAnchors;
                                solverVariable17 = constraintAnchorArr12[i2].mTarget != null ? constraintAnchorArr12[i2].mTarget.mSolverVariable : null;
                            }
                            int margin4 = constraintAnchor17.getMargin();
                            int i20 = i2 + 1;
                            int margin5 = constraintWidget21.mListAnchors[i20].getMargin();
                            if (constraintWidget23 != null) {
                                constraintAnchor5 = constraintWidget23.mListAnchors[i2];
                                solverVariable5 = constraintAnchor5.mSolverVariable;
                                solverVariable4 = constraintWidget21.mListAnchors[i20].mSolverVariable;
                            } else {
                                constraintAnchor5 = constraintWidget7.mListAnchors[i20].mTarget;
                                solverVariable5 = constraintAnchor5 != null ? constraintAnchor5.mSolverVariable : null;
                                solverVariable4 = constraintWidget21.mListAnchors[i20].mSolverVariable;
                            }
                            if (constraintAnchor5 != null) {
                                margin5 += constraintAnchor5.getMargin();
                            }
                            if (constraintWidget22 != null) {
                                margin4 += constraintWidget22.mListAnchors[i20].getMargin();
                            }
                            if (!(solverVariable16 == null || solverVariable17 == null || solverVariable5 == null || solverVariable4 == null)) {
                                if (constraintWidget21 == constraintWidget8) {
                                    margin4 = constraintWidget8.mListAnchors[i2].getMargin();
                                }
                                constraintWidget4 = constraintWidget23;
                                linearSystem.addCentering(solverVariable16, solverVariable17, margin4, 0.5f, solverVariable5, solverVariable4, constraintWidget21 == constraintWidget9 ? constraintWidget9.mListAnchors[i20].getMargin() : margin5, z10 ? 6 : 4);
                                if (constraintWidget21.getVisibility() == 8) {
                                    constraintWidget22 = constraintWidget21;
                                }
                                constraintWidget21 = constraintWidget4;
                            }
                        }
                        constraintWidget4 = constraintWidget23;
                        if (constraintWidget21.getVisibility() == 8) {
                        }
                        constraintWidget21 = constraintWidget4;
                    }
                }
                if ((!z7 || z) && constraintWidget8 != null) {
                    ConstraintAnchor constraintAnchor19 = constraintWidget8.mListAnchors[i2];
                    int i21 = i2 + 1;
                    ConstraintAnchor constraintAnchor20 = constraintWidget9.mListAnchors[i21];
                    ConstraintAnchor constraintAnchor21 = constraintAnchor19.mTarget;
                    solverVariable = constraintAnchor21 == null ? constraintAnchor21.mSolverVariable : null;
                    ConstraintAnchor constraintAnchor22 = constraintAnchor20.mTarget;
                    SolverVariable solverVariable18 = constraintAnchor22 == null ? constraintAnchor22.mSolverVariable : null;
                    if (constraintWidget7 != constraintWidget9) {
                        ConstraintAnchor constraintAnchor23 = constraintWidget7.mListAnchors[i21].mTarget;
                        solverVariable18 = constraintAnchor23 != null ? constraintAnchor23.mSolverVariable : null;
                    }
                    if (constraintWidget8 == constraintWidget9) {
                        ConstraintAnchor[] constraintAnchorArr13 = constraintWidget8.mListAnchors;
                        ConstraintAnchor constraintAnchor24 = constraintAnchorArr13[i2];
                        constraintAnchor20 = constraintAnchorArr13[i21];
                        constraintAnchor19 = constraintAnchor24;
                    }
                    if (solverVariable != null && solverVariable18 != null) {
                        int margin6 = constraintAnchor19.getMargin();
                        if (constraintWidget9 != null) {
                            constraintWidget7 = constraintWidget9;
                        }
                        linearSystem.addCentering(constraintAnchor19.mSolverVariable, solverVariable, margin6, 0.5f, solverVariable18, constraintAnchor20.mSolverVariable, constraintWidget7.mListAnchors[i21].getMargin(), 5);
                        return;
                    }
                }
                return;
            }
        }
        if (z5) {
        }
        ArrayList<ConstraintWidget> arrayList22 = chainHead.mWeightedMatchConstraintsWidgets;
        if (chainHead.mHasUndefinedWeights) {
        }
        float f52 = 0.0f;
        float f62 = 0.0f;
        ConstraintWidget constraintWidget162 = null;
        i5 = 0;
        while (i5 < size) {
        }
        if (constraintWidget8 == null) {
        }
        if (z7) {
        }
        int i142 = 8;
        int i152 = chainHead.mWidgetsMatchCount;
        if (i152 <= 0) {
        }
        constraintWidget = constraintWidget8;
        ConstraintWidget constraintWidget182 = constraintWidget;
        while (constraintWidget != null) {
        }
        ConstraintAnchor constraintAnchor162 = constraintWidget8.mListAnchors[i2];
        constraintAnchor = constraintWidget6.mListAnchors[i2].mTarget;
        int i182 = i2 + 1;
        constraintAnchor2 = constraintWidget9.mListAnchors[i182];
        constraintAnchor3 = constraintWidget7.mListAnchors[i182].mTarget;
        if (constraintAnchor != null) {
        }
        linearSystem.addEquality(constraintAnchor2.mSolverVariable, constraintAnchor3.mSolverVariable, -constraintAnchor2.getMargin(), i3);
        if (!z7) {
        }
        ConstraintAnchor constraintAnchor192 = constraintWidget8.mListAnchors[i2];
        int i212 = i2 + 1;
        ConstraintAnchor constraintAnchor202 = constraintWidget9.mListAnchors[i212];
        ConstraintAnchor constraintAnchor212 = constraintAnchor192.mTarget;
        if (constraintAnchor212 == null) {
        }
        ConstraintAnchor constraintAnchor222 = constraintAnchor202.mTarget;
        if (constraintAnchor222 == null) {
        }
        if (constraintWidget7 != constraintWidget9) {
        }
        if (constraintWidget8 == constraintWidget9) {
        }
        if (solverVariable != null) {
        }
    }
}
