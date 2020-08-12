package android.support.constraint.solver;

public class GoalRow extends ArrayRow {
    public GoalRow(Cache cache) {
        super(cache);
    }

    @Override // android.support.constraint.solver.LinearSystem.Row, android.support.constraint.solver.ArrayRow
    public void addError(SolverVariable solverVariable) {
        super.addError(solverVariable);
        solverVariable.usageInRowCount--;
    }
}
