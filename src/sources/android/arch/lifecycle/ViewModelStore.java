package android.arch.lifecycle;

import java.util.HashMap;

public class ViewModelStore {
    private final HashMap<String, ViewModel> mMap = new HashMap<>();

    public final void clear() {
        for (ViewModel viewModel : this.mMap.values()) {
            viewModel.onCleared();
        }
        this.mMap.clear();
    }

    /* access modifiers changed from: package-private */
    public final ViewModel get(String str) {
        return this.mMap.get(str);
    }

    /* access modifiers changed from: package-private */
    public final void put(String str, ViewModel viewModel) {
        ViewModel put = this.mMap.put(str, viewModel);
        if (put != null) {
            put.onCleared();
        }
    }
}
