package com.xiaomi.camera.processor;

import android.media.Image;
import android.support.annotation.NonNull;
import com.android.camera.groupshot.GroupShot;
import com.android.camera.log.Log;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.CaptureDataListener;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.List;

public class GroupShotProcessor implements AlgoProcessor {
    private static final int GROUP_SHOT_MAX_FACE_NUM = 10;
    private static final String TAG = "GroupShotProcessor";
    private GroupShot mGroupShot;

    public GroupShotProcessor() {
        GroupShot groupShot = this.mGroupShot;
        if (groupShot == null || groupShot.isUsed()) {
            this.mGroupShot = new GroupShot();
        }
    }

    private void onImageAvailable(CaptureData captureData, CaptureData.CaptureDataBean captureDataBean, ProcessResultListener processResultListener) {
        captureDataBean.getMainImage().setTimestamp(captureDataBean.getResult().getTimeStamp());
        captureData.setMultiFrameProcessResult(captureDataBean);
        processResultListener.onProcessFinished(captureData, false);
    }

    private void prepareGroupShot(int i, int i2, int i3) {
        this.mGroupShot.initialize(i, 10, i2, i3, i2, i3);
        this.mGroupShot.attach_start(0);
    }

    @Override // com.xiaomi.camera.processor.AlgoProcessor
    public void doProcess(@NonNull CaptureData captureData, ProcessResultListener processResultListener, TaskSession taskSession) {
        List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
        if (captureDataBeanList == null || captureDataBeanList.isEmpty()) {
            throw new IllegalArgumentException("taskBeanList is not allow to be empty!");
        }
        Log.d(TAG, "doProcess>>dataNum=" + captureDataBeanList.size());
        long currentTimeMillis = System.currentTimeMillis();
        CaptureData.CaptureDataBean captureDataBean = captureDataBeanList.get(0);
        Image mainImage = captureDataBean.getMainImage();
        prepareGroupShot(captureDataBeanList.size(), mainImage.getWidth(), mainImage.getHeight());
        for (int i = 0; i < captureDataBeanList.size(); i++) {
            this.mGroupShot.attach(captureDataBeanList.get(i).getMainImage());
        }
        Log.v(TAG, String.format("doProcess: attachEnd=0x%x", Integer.valueOf(this.mGroupShot.attach_end())));
        Log.v(TAG, String.format("doProcess: setBaseImage=0x%x", Integer.valueOf(this.mGroupShot.setBaseImage(0))));
        Log.v(TAG, String.format("doProcess: setBaseFace=0x%x", Integer.valueOf(this.mGroupShot.setBestFace())));
        CaptureData.CaptureDataBean captureDataBean2 = new CaptureData.CaptureDataBean(captureData.getStreamNum());
        ICustomCaptureResult result = captureDataBeanList.get(captureDataBeanList.size() - 1).getResult();
        captureDataBean2.setCaptureResult(result, true);
        long timeStamp = result.getTimeStamp();
        Image anEmptyImage = ImagePool.getInstance().getAnEmptyImage(new ImagePool.ImageFormat(mainImage.getWidth(), mainImage.getHeight(), mainImage.getFormat()));
        anEmptyImage.setTimestamp(timeStamp);
        this.mGroupShot.getYuvImage(anEmptyImage);
        ImagePool.getInstance().queueImage(anEmptyImage);
        Image image = ImagePool.getInstance().getImage(timeStamp);
        captureDataBean2.setImage(image, 0);
        ImagePool.getInstance().holdImage(image);
        CaptureDataListener captureDataListener = captureData.getCaptureDataListener();
        for (CaptureData.CaptureDataBean captureDataBean3 : captureDataBeanList) {
            Image mainImage2 = captureDataBean3.getMainImage();
            if (!captureData.isSaveInputImage() || captureDataBean3 != captureDataBean) {
                mainImage2.close();
                captureDataListener.onOriginalImageClosed(mainImage2);
            }
        }
        captureDataBeanList.clear();
        if (captureData.isSaveInputImage()) {
            Image mainImage3 = captureDataBean.getMainImage();
            captureDataListener.onOriginalImageClosed(mainImage3);
            long timestamp = mainImage3.getTimestamp();
            ImagePool.getInstance().queueImage(mainImage3);
            Image image2 = ImagePool.getInstance().getImage(timestamp);
            ImagePool.getInstance().holdImage(image2);
            captureDataBean.setMainImage(image2);
            captureDataBeanList.add(captureDataBean);
            result.setSequenceId((int) (((long) result.getSequenceId()) + result.getFrameNumber()));
            ICustomCaptureResult result2 = captureDataBean.getResult();
            result2.setSequenceId((int) (((long) result2.getSequenceId()) + result2.getFrameNumber()));
        }
        onImageAvailable(captureData, captureDataBean2, processResultListener);
        this.mGroupShot.clearImages();
        this.mGroupShot.finish();
        this.mGroupShot = null;
        Log.d(TAG, "doProcess<<cost=" + (System.currentTimeMillis() - currentTimeMillis));
    }
}
