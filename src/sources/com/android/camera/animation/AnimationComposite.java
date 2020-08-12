package com.android.camera.animation;

import android.animation.ValueAnimator;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.android.camera.animation.AnimationDelegate;
import com.android.camera.data.DataRepository;
import com.android.camera.module.loader.StartControl;
import com.ss.android.vesdk.VEResult;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnimationComposite implements Consumer<Integer> {
    private static final String TAG = "AnimationComposite";
    private AtomicBoolean mActive = new AtomicBoolean(true);
    private Disposable mAfterFrameArrivedDisposable = Observable.create(new ObservableOnSubscribe<Integer>() {
        /* class com.android.camera.animation.AnimationComposite.AnonymousClass1 */

        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
            ObservableEmitter unused = AnimationComposite.this.mAfterFrameArrivedEmitter = observableEmitter;
        }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    /* access modifiers changed from: private */
    public ObservableEmitter<Integer> mAfterFrameArrivedEmitter;
    private Disposable mAnimationDisposable;
    /* access modifiers changed from: private */
    public int mCurrentDegree = 0;
    private int mOrientation = -1;
    private SparseArray<AnimationDelegate.AnimationResource> mResourceSparseArray = new SparseArray<>();
    private ValueAnimator mRotationAnimator;
    private int mStartDegree = 0;
    private int mTargetDegree = 0;

    public void accept(@NonNull Integer num) throws Exception {
        DataRepository.dataItemGlobal().setRetriedIfCameraError(false);
        for (int i = 0; i < this.mResourceSparseArray.size(); i++) {
            AnimationDelegate.AnimationResource valueAt = this.mResourceSparseArray.valueAt(i);
            if (valueAt.canProvide()) {
                if (!this.mActive.get()) {
                    Log.e(TAG, "not active, skip notifyAfterFrameAvailable");
                    return;
                }
                if (!valueAt.isEnableClick()) {
                    valueAt.setClickEnable(true);
                }
                valueAt.notifyAfterFrameAvailable(num.intValue());
            }
        }
    }

    public void destroy() {
        SparseArray<AnimationDelegate.AnimationResource> sparseArray = this.mResourceSparseArray;
        if (sparseArray != null) {
            sparseArray.clear();
            this.mResourceSparseArray = null;
        }
        Disposable disposable = this.mAfterFrameArrivedDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAfterFrameArrivedEmitter.onComplete();
            this.mAfterFrameArrivedDisposable.dispose();
            this.mAfterFrameArrivedDisposable = null;
        }
    }

    public Disposable dispose(@Nullable Completable completable, @Nullable Action action, StartControl startControl) {
        ArrayList arrayList = new ArrayList();
        if (completable != null) {
            arrayList.add(completable);
        }
        int i = startControl.mTargetMode;
        int i2 = startControl.mResetType;
        int i3 = startControl.mViewConfigType;
        if (i3 != -1) {
            int i4 = 0;
            if (i3 == 1) {
                while (i4 < this.mResourceSparseArray.size()) {
                    AnimationDelegate.AnimationResource valueAt = this.mResourceSparseArray.valueAt(i4);
                    if (valueAt.canProvide()) {
                        valueAt.provideAnimateElement(i, null, i2);
                    }
                    i4++;
                }
            } else if (i3 == 2) {
                while (i4 < this.mResourceSparseArray.size()) {
                    AnimationDelegate.AnimationResource valueAt2 = this.mResourceSparseArray.valueAt(i4);
                    if (valueAt2.canProvide()) {
                        valueAt2.provideAnimateElement(i, arrayList, i2);
                    }
                    i4++;
                }
            } else if (i3 == 3) {
                while (i4 < this.mResourceSparseArray.size()) {
                    AnimationDelegate.AnimationResource valueAt3 = this.mResourceSparseArray.valueAt(i4);
                    if (valueAt3.canProvide() && valueAt3.needViewClear()) {
                        valueAt3.provideAnimateElement(i, null, i2);
                    }
                    i4++;
                }
            }
        }
        Disposable disposable = this.mAnimationDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAnimationDisposable.dispose();
        }
        if (action != null) {
            this.mAnimationDisposable = Completable.merge(arrayList).subscribe(action);
        } else {
            this.mAnimationDisposable = Completable.merge(arrayList).subscribe();
        }
        return this.mAnimationDisposable;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0090, code lost:
        if (r2 == 0) goto L_0x009f;
     */
    public void disposeRotation(int i) {
        int i2;
        int i3;
        int i4 = 360;
        int i5 = i >= 0 ? i % 360 : (i % 360) + 360;
        int i6 = this.mOrientation;
        if (i6 != i5) {
            boolean z = i6 != -1;
            int i7 = i5 - this.mOrientation;
            if (i7 < 0) {
                i7 += 360;
            }
            if (i7 > 180) {
                i7 += VEResult.TER_EGL_BAD_MATCH;
            }
            boolean z2 = i7 <= 0;
            this.mOrientation = i5;
            if (this.mOrientation != 0 || this.mCurrentDegree != 0) {
                this.mTargetDegree = (360 - i5) % 360;
                final ArrayList<View> arrayList = new ArrayList();
                for (int i8 = 0; i8 < this.mResourceSparseArray.size(); i8++) {
                    AnimationDelegate.AnimationResource valueAt = this.mResourceSparseArray.valueAt(i8);
                    if (valueAt.canProvide()) {
                        valueAt.provideRotateItem(arrayList, this.mTargetDegree);
                    }
                }
                if (!z) {
                    this.mCurrentDegree = this.mTargetDegree;
                    for (View view : arrayList) {
                        ViewCompat.setRotation(view, (float) this.mTargetDegree);
                    }
                    return;
                }
                ValueAnimator valueAnimator = this.mRotationAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                this.mStartDegree = this.mCurrentDegree;
                if (z2) {
                    i2 = this.mStartDegree;
                    if (i2 == 360) {
                        i2 = 0;
                    }
                    i3 = this.mTargetDegree;
                } else {
                    int i9 = this.mStartDegree;
                    if (i9 == 0) {
                        i9 = 360;
                    }
                    i3 = this.mTargetDegree;
                    if (i3 == 360) {
                        i4 = 0;
                        this.mRotationAnimator = ValueAnimator.ofInt(i2, i4);
                        this.mRotationAnimator.setInterpolator(new LinearInterpolator());
                        this.mRotationAnimator.removeAllUpdateListeners();
                        this.mRotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            /* class com.android.camera.animation.AnimationComposite.AnonymousClass2 */

                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int unused = AnimationComposite.this.mCurrentDegree = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                                for (View view : arrayList) {
                                    ViewCompat.setRotation(view, (float) AnimationComposite.this.mCurrentDegree);
                                }
                            }
                        });
                        this.mRotationAnimator.start();
                    }
                }
                i4 = i3;
                this.mRotationAnimator = ValueAnimator.ofInt(i2, i4);
                this.mRotationAnimator.setInterpolator(new LinearInterpolator());
                this.mRotationAnimator.removeAllUpdateListeners();
                this.mRotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.android.camera.animation.AnimationComposite.AnonymousClass2 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int unused = AnimationComposite.this.mCurrentDegree = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        for (View view : arrayList) {
                            ViewCompat.setRotation(view, (float) AnimationComposite.this.mCurrentDegree);
                        }
                    }
                });
                this.mRotationAnimator.start();
            }
        }
    }

    public void notifyAfterFirstFrameArrived(int i) {
        Disposable disposable = this.mAfterFrameArrivedDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAfterFrameArrivedEmitter.onNext(Integer.valueOf(i));
        }
    }

    public void notifyDataChanged(int i, int i2) {
        for (int i3 = 0; i3 < this.mResourceSparseArray.size(); i3++) {
            AnimationDelegate.AnimationResource valueAt = this.mResourceSparseArray.valueAt(i3);
            if (valueAt.canProvide()) {
                valueAt.notifyDataChanged(i, i2);
            }
        }
    }

    public void onStart() {
        this.mActive.set(true);
    }

    public void onStop() {
        this.mActive.set(false);
    }

    public void put(int i, AnimationDelegate.AnimationResource animationResource) {
        this.mResourceSparseArray.put(i, animationResource);
    }

    public void remove(int i) {
        this.mResourceSparseArray.remove(i);
    }

    public void setClickEnable(boolean z) {
        for (int i = 0; i < this.mResourceSparseArray.size(); i++) {
            AnimationDelegate.AnimationResource valueAt = this.mResourceSparseArray.valueAt(i);
            if (valueAt.canProvide() && valueAt.isEnableClick() != z) {
                valueAt.setClickEnable(z);
            }
        }
    }
}
