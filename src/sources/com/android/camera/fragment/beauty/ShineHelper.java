package com.android.camera.fragment.beauty;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class ShineHelper {
    public static void clearBeauty() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            miBeautyProtocol.clearBeauty();
        }
    }

    public static void onBeautyChanged() {
        ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
        if (onShineChangedProtocol != null) {
            onShineChangedProtocol.onShineChanged(false, 239);
        }
    }

    public static void onVideoBokehRatioChanged() {
        ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
        if (onShineChangedProtocol != null) {
            onShineChangedProtocol.onShineChanged(false, 243);
        }
    }

    public static void onVideoFilterChanged() {
        ModeProtocol.OnShineChangedProtocol onShineChangedProtocol = (ModeProtocol.OnShineChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(234);
        if (onShineChangedProtocol != null) {
            onShineChangedProtocol.onShineChanged(false, 196);
        }
    }

    public static void resetBeauty() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            miBeautyProtocol.resetBeauty();
        }
    }
}
