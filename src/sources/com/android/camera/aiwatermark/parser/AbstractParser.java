package com.android.camera.aiwatermark.parser;

import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;

public abstract class AbstractParser {
    private static final String TAG = "AbstractParser";
    protected WatermarkItem markItem = null;
    protected ArrayList<WatermarkItem> watermarkItems = new ArrayList<>();

    public ArrayList<WatermarkItem> parseByPattern(int i) {
        new ArrayList();
        return i != 1 ? parseXml() : parseJson();
    }

    /* access modifiers changed from: protected */
    public abstract ArrayList<WatermarkItem> parseJson();

    /* access modifiers changed from: protected */
    public abstract ArrayList<WatermarkItem> parseXml();
}
