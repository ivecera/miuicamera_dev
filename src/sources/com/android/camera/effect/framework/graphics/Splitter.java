package com.android.camera.effect.framework.graphics;

import java.util.ArrayList;
import java.util.List;

public class Splitter {
    private List<Block> mBlockList = new ArrayList();

    public List<Block> split(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mBlockList.size() != 0) {
            this.mBlockList.clear();
        }
        int i7 = i % i3;
        int i8 = i / i3;
        if (i7 != 0) {
            i8++;
        }
        int i9 = i2 % i4;
        int i10 = i2 / i4;
        if (i9 != 0) {
            i10++;
        }
        for (int i11 = 0; i11 < i10; i11++) {
            for (int i12 = 0; i12 < i8; i12++) {
                Block block = new Block();
                block.mRowStride = i;
                block.mOffset = (i11 * i * i4) + (i12 * i3);
                block.mAdjWidth = i5;
                block.mAdjHeight = i6;
                int i13 = i8 - 1;
                if (i12 == i13 && i7 > 0 && i11 == i10 - 1 && i9 > 0) {
                    block.mWidth = i7;
                    block.mHeight = i9;
                    this.mBlockList.add(block);
                } else if (i12 == i13 && i7 > 0) {
                    block.mWidth = i7;
                    block.mHeight = i4;
                    this.mBlockList.add(block);
                } else if (i11 != i10 - 1 || i9 <= 0) {
                    block.mWidth = i3;
                    block.mHeight = i4;
                    this.mBlockList.add(block);
                } else {
                    block.mWidth = i3;
                    block.mHeight = i9;
                    this.mBlockList.add(block);
                }
            }
        }
        return this.mBlockList;
    }
}
