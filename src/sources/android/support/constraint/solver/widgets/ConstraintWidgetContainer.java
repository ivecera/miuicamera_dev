package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstraintWidgetContainer extends WidgetContainer {
    private static final boolean DEBUG = false;
    static final boolean DEBUG_GRAPH = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final int MAX_ITERATIONS = 8;
    private static final boolean USE_SNAPSHOT = true;
    int mDebugSolverPassCount;
    public boolean mGroupsWrapOptimized;
    private boolean mHeightMeasuredTooSmall;
    ChainHead[] mHorizontalChainsArray;
    int mHorizontalChainsSize;
    public boolean mHorizontalWrapOptimized;
    private boolean mIsRtl;
    private int mOptimizationLevel;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    public boolean mSkipSolver;
    private Snapshot mSnapshot;
    protected LinearSystem mSystem;
    ChainHead[] mVerticalChainsArray;
    int mVerticalChainsSize;
    public boolean mVerticalWrapOptimized;
    public List<ConstraintWidgetGroup> mWidgetGroups;
    private boolean mWidthMeasuredTooSmall;
    public int mWrapFixedHeight;
    public int mWrapFixedWidth;

    public ConstraintWidgetContainer() {
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mWidgetGroups = new ArrayList();
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = 7;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    public ConstraintWidgetContainer(int i, int i2) {
        super(i, i2);
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mWidgetGroups = new ArrayList();
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = 7;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    public ConstraintWidgetContainer(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mWidgetGroups = new ArrayList();
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = 7;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    private void addHorizontalChain(ConstraintWidget constraintWidget) {
        int i = this.mHorizontalChainsSize + 1;
        ChainHead[] chainHeadArr = this.mHorizontalChainsArray;
        if (i >= chainHeadArr.length) {
            this.mHorizontalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(constraintWidget, 0, isRtl());
        this.mHorizontalChainsSize++;
    }

    private void addVerticalChain(ConstraintWidget constraintWidget) {
        int i = this.mVerticalChainsSize + 1;
        ChainHead[] chainHeadArr = this.mVerticalChainsArray;
        if (i >= chainHeadArr.length) {
            this.mVerticalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(constraintWidget, 1, isRtl());
        this.mVerticalChainsSize++;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    /* access modifiers changed from: package-private */
    public void addChain(ConstraintWidget constraintWidget, int i) {
        if (i == 0) {
            addHorizontalChain(constraintWidget);
        } else if (i == 1) {
            addVerticalChain(constraintWidget);
        }
    }

    public boolean addChildrenToSolver(LinearSystem linearSystem) {
        addToSolver(linearSystem);
        int size = ((WidgetContainer) this).mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = ((WidgetContainer) this).mChildren.get(i);
            if (constraintWidget instanceof ConstraintWidgetContainer) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                constraintWidget.addToSolver(linearSystem);
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setVerticalDimensionBehaviour(dimensionBehaviour2);
                }
            } else {
                Optimizer.checkMatchParent(this, linearSystem, constraintWidget);
                constraintWidget.addToSolver(linearSystem);
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 0);
        }
        if (this.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 1);
        }
        return true;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void analyze(int i) {
        super.analyze(i);
        int size = ((WidgetContainer) this).mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((WidgetContainer) this).mChildren.get(i2).analyze(i);
        }
    }

    public void fillMetrics(Metrics metrics) {
        this.mSystem.fillMetrics(metrics);
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<>();
        int size = ((WidgetContainer) this).mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = ((WidgetContainer) this).mChildren.get(i);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 0) {
                    arrayList.add(guideline);
                }
            }
        }
        return arrayList;
    }

    public int getOptimizationLevel() {
        return this.mOptimizationLevel;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public String getType() {
        return "ConstraintLayout";
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<>();
        int size = ((WidgetContainer) this).mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = ((WidgetContainer) this).mChildren.get(i);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 1) {
                    arrayList.add(guideline);
                }
            }
        }
        return arrayList;
    }

    public List<ConstraintWidgetGroup> getWidgetGroups() {
        return this.mWidgetGroups;
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public boolean isRtl() {
        return this.mIsRtl;
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    /* JADX WARN: Type inference failed for: r8v15, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0268  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0284  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0291  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0294  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x018f  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01db  */
    @Override // android.support.constraint.solver.widgets.WidgetContainer
    public void layout() {
        int i;
        int i2;
        boolean z;
        boolean z2;
        char c2;
        int i3;
        boolean z3;
        int max;
        int max2;
        ? r8;
        boolean z4;
        int i4 = ((ConstraintWidget) this).mX;
        int i5 = ((ConstraintWidget) this).mY;
        int i6 = 0;
        int max3 = Math.max(0, getWidth());
        int max4 = Math.max(0, getHeight());
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        if (((ConstraintWidget) this).mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            setX(this.mPaddingLeft);
            setY(this.mPaddingTop);
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            ((ConstraintWidget) this).mX = 0;
            ((ConstraintWidget) this).mY = 0;
        }
        int i7 = 32;
        if (this.mOptimizationLevel != 0) {
            if (!optimizeFor(8)) {
                optimizeReset();
            }
            if (!optimizeFor(32)) {
                optimize();
            }
            this.mSystem.graphOptimizer = true;
        } else {
            this.mSystem.graphOptimizer = false;
        }
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = ((ConstraintWidget) this).mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[1];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
        resetChains();
        if (this.mWidgetGroups.size() == 0) {
            this.mWidgetGroups.clear();
            this.mWidgetGroups.add(0, new ConstraintWidgetGroup(((WidgetContainer) this).mChildren));
        }
        int size = this.mWidgetGroups.size();
        ArrayList<ConstraintWidget> arrayList = ((WidgetContainer) this).mChildren;
        boolean z5 = getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z6 = false;
        int i8 = 0;
        while (i8 < size && !this.mSkipSolver) {
            if (this.mWidgetGroups.get(i8).mSkipSolver) {
                i = size;
            } else {
                if (optimizeFor(i7)) {
                    if (getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED && getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
                        ((WidgetContainer) this).mChildren = (ArrayList) this.mWidgetGroups.get(i8).getWidgetsToSolve();
                    } else {
                        ((WidgetContainer) this).mChildren = (ArrayList) this.mWidgetGroups.get(i8).mConstrainedGroup;
                    }
                }
                resetChains();
                int size2 = ((WidgetContainer) this).mChildren.size();
                for (int i9 = i6; i9 < size2; i9++) {
                    ConstraintWidget constraintWidget = ((WidgetContainer) this).mChildren.get(i9);
                    if (constraintWidget instanceof WidgetContainer) {
                        ((WidgetContainer) constraintWidget).layout();
                    }
                }
                boolean z7 = z6;
                int i10 = 0;
                boolean z8 = true;
                while (z8) {
                    int i11 = i10 + 1;
                    try {
                        this.mSystem.reset();
                        resetChains();
                        createObjectVariables(this.mSystem);
                        int i12 = 0;
                        while (i12 < size2) {
                            z = z7;
                            try {
                                ((WidgetContainer) this).mChildren.get(i12).createObjectVariables(this.mSystem);
                                i12++;
                                z7 = z;
                            } catch (Exception e2) {
                                e = e2;
                                z4 = z8;
                                e.printStackTrace();
                                PrintStream printStream = System.out;
                                z2 = z4;
                                StringBuilder sb = new StringBuilder();
                                i2 = size;
                                sb.append("EXCEPTION : ");
                                sb.append(e);
                                printStream.println(sb.toString());
                                if (z2) {
                                }
                                c2 = 2;
                                if (z5) {
                                }
                                i3 = i11;
                                z7 = z;
                                z3 = false;
                                max = Math.max(((ConstraintWidget) this).mMinWidth, getWidth());
                                if (max > getWidth()) {
                                }
                                max2 = Math.max(((ConstraintWidget) this).mMinHeight, getHeight());
                                if (max2 <= getHeight()) {
                                }
                                if (!z7) {
                                }
                                z8 = z3;
                                i10 = i3;
                                size = i2;
                            }
                        }
                        z = z7;
                        z4 = addChildrenToSolver(this.mSystem);
                        if (z4) {
                            try {
                                this.mSystem.minimize();
                            } catch (Exception e3) {
                                e = e3;
                            }
                        }
                        z2 = z4;
                        i2 = size;
                    } catch (Exception e4) {
                        e = e4;
                        z = z7;
                        z4 = z8;
                        e.printStackTrace();
                        PrintStream printStream2 = System.out;
                        z2 = z4;
                        StringBuilder sb2 = new StringBuilder();
                        i2 = size;
                        sb2.append("EXCEPTION : ");
                        sb2.append(e);
                        printStream2.println(sb2.toString());
                        if (z2) {
                        }
                        c2 = 2;
                        if (z5) {
                        }
                        i3 = i11;
                        z7 = z;
                        z3 = false;
                        max = Math.max(((ConstraintWidget) this).mMinWidth, getWidth());
                        if (max > getWidth()) {
                        }
                        max2 = Math.max(((ConstraintWidget) this).mMinHeight, getHeight());
                        if (max2 <= getHeight()) {
                        }
                        if (!z7) {
                        }
                        z8 = z3;
                        i10 = i3;
                        size = i2;
                    }
                    if (z2) {
                        updateChildrenFromSolver(this.mSystem, Optimizer.flags);
                    } else {
                        updateFromSolver(this.mSystem);
                        int i13 = 0;
                        while (true) {
                            if (i13 >= size2) {
                                break;
                            }
                            ConstraintWidget constraintWidget2 = ((WidgetContainer) this).mChildren.get(i13);
                            if (constraintWidget2.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget2.getWidth() >= constraintWidget2.getWrapWidth()) {
                                if (constraintWidget2.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.getHeight() < constraintWidget2.getWrapHeight()) {
                                    c2 = 2;
                                    Optimizer.flags[2] = true;
                                    break;
                                }
                                i13++;
                            } else {
                                Optimizer.flags[2] = true;
                                c2 = 2;
                                break;
                            }
                        }
                        if (z5 || i11 >= 8 || !Optimizer.flags[c2]) {
                            i3 = i11;
                            z7 = z;
                            z3 = false;
                        } else {
                            int i14 = 0;
                            int i15 = 0;
                            int i16 = 0;
                            while (i14 < size2) {
                                ConstraintWidget constraintWidget3 = ((WidgetContainer) this).mChildren.get(i14);
                                i15 = Math.max(i15, constraintWidget3.mX + constraintWidget3.getWidth());
                                i16 = Math.max(i16, constraintWidget3.mY + constraintWidget3.getHeight());
                                i14++;
                                i11 = i11;
                            }
                            i3 = i11;
                            int max5 = Math.max(((ConstraintWidget) this).mMinWidth, i15);
                            int max6 = Math.max(((ConstraintWidget) this).mMinHeight, i16);
                            if (dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getWidth() >= max5) {
                                z3 = false;
                            } else {
                                setWidth(max5);
                                ((ConstraintWidget) this).mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                z3 = true;
                                z = true;
                            }
                            if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getHeight() >= max6) {
                                z7 = z;
                            } else {
                                setHeight(max6);
                                ((ConstraintWidget) this).mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                z3 = true;
                                z7 = true;
                            }
                        }
                        max = Math.max(((ConstraintWidget) this).mMinWidth, getWidth());
                        if (max > getWidth()) {
                            setWidth(max);
                            ((ConstraintWidget) this).mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                            z3 = true;
                            z7 = true;
                        }
                        max2 = Math.max(((ConstraintWidget) this).mMinHeight, getHeight());
                        if (max2 <= getHeight()) {
                            setHeight(max2);
                            r8 = 1;
                            ((ConstraintWidget) this).mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                            z3 = true;
                            z7 = true;
                        } else {
                            r8 = 1;
                        }
                        if (!z7) {
                            if (((ConstraintWidget) this).mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && max3 > 0 && getWidth() > max3) {
                                this.mWidthMeasuredTooSmall = r8;
                                ((ConstraintWidget) this).mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                                setWidth(max3);
                                z3 = r8;
                                z7 = z3;
                            }
                            if (((ConstraintWidget) this).mListDimensionBehaviors[r8] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && max4 > 0 && getHeight() > max4) {
                                this.mHeightMeasuredTooSmall = r8;
                                ((ConstraintWidget) this).mListDimensionBehaviors[r8] = ConstraintWidget.DimensionBehaviour.FIXED;
                                setHeight(max4);
                                z8 = true;
                                z7 = true;
                                i10 = i3;
                                size = i2;
                            }
                        }
                        z8 = z3;
                        i10 = i3;
                        size = i2;
                    }
                    c2 = 2;
                    if (z5) {
                    }
                    i3 = i11;
                    z7 = z;
                    z3 = false;
                    max = Math.max(((ConstraintWidget) this).mMinWidth, getWidth());
                    if (max > getWidth()) {
                    }
                    max2 = Math.max(((ConstraintWidget) this).mMinHeight, getHeight());
                    if (max2 <= getHeight()) {
                    }
                    if (!z7) {
                    }
                    z8 = z3;
                    i10 = i3;
                    size = i2;
                }
                i = size;
                this.mWidgetGroups.get(i8).updateUnresolvedWidgets();
                z6 = z7;
            }
            i8++;
            size = i;
            i6 = 0;
            i7 = 32;
        }
        ((WidgetContainer) this).mChildren = arrayList;
        if (((ConstraintWidget) this).mParent != null) {
            int max7 = Math.max(((ConstraintWidget) this).mMinWidth, getWidth());
            int max8 = Math.max(((ConstraintWidget) this).mMinHeight, getHeight());
            this.mSnapshot.applyTo(this);
            setWidth(max7 + this.mPaddingLeft + this.mPaddingRight);
            setHeight(max8 + this.mPaddingTop + this.mPaddingBottom);
        } else {
            ((ConstraintWidget) this).mX = i4;
            ((ConstraintWidget) this).mY = i5;
        }
        if (z6) {
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = ((ConstraintWidget) this).mListDimensionBehaviors;
            dimensionBehaviourArr2[0] = dimensionBehaviour2;
            dimensionBehaviourArr2[1] = dimensionBehaviour;
        }
        resetSolverVariables(this.mSystem.getCache());
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    public void optimize() {
        if (!optimizeFor(8)) {
            analyze(this.mOptimizationLevel);
        }
        solveGraph();
    }

    public boolean optimizeFor(int i) {
        return (this.mOptimizationLevel & i) == i;
    }

    public void optimizeForDimensions(int i, int i2) {
        ResolutionDimension resolutionDimension;
        ResolutionDimension resolutionDimension2;
        if (!(((ConstraintWidget) this).mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (resolutionDimension2 = ((ConstraintWidget) this).mResolutionWidth) == null)) {
            resolutionDimension2.resolve(i);
        }
        if (((ConstraintWidget) this).mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && (resolutionDimension = ((ConstraintWidget) this).mResolutionHeight) != null) {
            resolutionDimension.resolve(i2);
        }
    }

    public void optimizeReset() {
        int size = ((WidgetContainer) this).mChildren.size();
        resetResolutionNodes();
        for (int i = 0; i < size; i++) {
            ((WidgetContainer) this).mChildren.get(i).resetResolutionNodes();
        }
    }

    public void preOptimize() {
        optimizeReset();
        analyze(this.mOptimizationLevel);
    }

    @Override // android.support.constraint.solver.widgets.WidgetContainer, android.support.constraint.solver.widgets.ConstraintWidget
    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        this.mWidgetGroups.clear();
        this.mSkipSolver = false;
        super.reset();
    }

    public void resetGraph() {
        ResolutionAnchor resolutionNode = getAnchor(ConstraintAnchor.Type.LEFT).getResolutionNode();
        ResolutionAnchor resolutionNode2 = getAnchor(ConstraintAnchor.Type.TOP).getResolutionNode();
        resolutionNode.invalidateAnchors();
        resolutionNode2.invalidateAnchors();
        resolutionNode.resolve(null, 0.0f);
        resolutionNode2.resolve(null, 0.0f);
    }

    public void setOptimizationLevel(int i) {
        this.mOptimizationLevel = i;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.mPaddingLeft = i;
        this.mPaddingTop = i2;
        this.mPaddingRight = i3;
        this.mPaddingBottom = i4;
    }

    public void setRtl(boolean z) {
        this.mIsRtl = z;
    }

    public void solveGraph() {
        ResolutionAnchor resolutionNode = getAnchor(ConstraintAnchor.Type.LEFT).getResolutionNode();
        ResolutionAnchor resolutionNode2 = getAnchor(ConstraintAnchor.Type.TOP).getResolutionNode();
        resolutionNode.resolve(null, 0.0f);
        resolutionNode2.resolve(null, 0.0f);
    }

    public void updateChildrenFromSolver(LinearSystem linearSystem, boolean[] zArr) {
        zArr[2] = false;
        updateFromSolver(linearSystem);
        int size = ((WidgetContainer) this).mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = ((WidgetContainer) this).mChildren.get(i);
            constraintWidget.updateFromSolver(linearSystem);
            if (constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.getWidth() < constraintWidget.getWrapWidth()) {
                zArr[2] = true;
            }
            if (constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.getHeight() < constraintWidget.getWrapHeight()) {
                zArr[2] = true;
            }
        }
    }
}
