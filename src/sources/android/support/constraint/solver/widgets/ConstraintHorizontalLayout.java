package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment mAlignment = ContentAlignment.MIDDLE;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ConstraintHorizontalLayout() {
    }

    public ConstraintHorizontalLayout(int i, int i2) {
        super(i, i2);
    }

    public ConstraintHorizontalLayout(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        if (((WidgetContainer) this).mChildren.size() != 0) {
            int i = 0;
            int size = ((WidgetContainer) this).mChildren.size();
            ConstraintWidget constraintWidget = this;
            while (i < size) {
                ConstraintWidget constraintWidget2 = ((WidgetContainer) this).mChildren.get(i);
                if (constraintWidget != this) {
                    constraintWidget2.connect(ConstraintAnchor.Type.LEFT, constraintWidget, ConstraintAnchor.Type.RIGHT);
                    constraintWidget.connect(ConstraintAnchor.Type.RIGHT, constraintWidget2, ConstraintAnchor.Type.LEFT);
                } else {
                    ConstraintAnchor.Strength strength = ConstraintAnchor.Strength.STRONG;
                    if (this.mAlignment == ContentAlignment.END) {
                        strength = ConstraintAnchor.Strength.WEAK;
                    }
                    ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                    constraintWidget2.connect(type, constraintWidget, type, 0, strength);
                }
                ConstraintAnchor.Type type2 = ConstraintAnchor.Type.TOP;
                constraintWidget2.connect(type2, this, type2);
                ConstraintAnchor.Type type3 = ConstraintAnchor.Type.BOTTOM;
                constraintWidget2.connect(type3, this, type3);
                i++;
                constraintWidget = constraintWidget2;
            }
            if (constraintWidget != this) {
                ConstraintAnchor.Strength strength2 = ConstraintAnchor.Strength.STRONG;
                if (this.mAlignment == ContentAlignment.BEGIN) {
                    strength2 = ConstraintAnchor.Strength.WEAK;
                }
                ConstraintAnchor.Type type4 = ConstraintAnchor.Type.RIGHT;
                constraintWidget.connect(type4, this, type4, 0, strength2);
            }
        }
        super.addToSolver(linearSystem);
    }
}
