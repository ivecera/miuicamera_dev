package android.support.constraint.solver.widgets;

public class ResolutionDimension extends ResolutionNode {
    float value = 0.0f;

    public void remove() {
        ((ResolutionNode) this).state = 2;
    }

    @Override // android.support.constraint.solver.widgets.ResolutionNode
    public void reset() {
        super.reset();
        this.value = 0.0f;
    }

    public void resolve(int i) {
        if (((ResolutionNode) this).state == 0 || this.value != ((float) i)) {
            this.value = (float) i;
            if (((ResolutionNode) this).state == 1) {
                invalidate();
            }
            didResolve();
        }
    }
}
