package com.android.camera.storage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.camera.Util;
import com.android.internal.util.CollectionUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jcodec.containers.mp4.boxes.MetaBox;
import org.jcodec.containers.mp4.boxes.MetaValue;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.containers.mp4.boxes.MovieFragmentBox;
import org.jcodec.containers.mp4.boxes.MsrtBox;
import org.jcodec.containers.mp4.boxes.MtagBox;
import org.jcodec.containers.mp4.boxes.NodeBox;
import org.jcodec.containers.mp4.boxes.UdtaBox;
import org.jcodec.movtool.MP4Edit;
import org.jcodec.movtool.RelocateMP4Editor;

public class VideoTagSaveRequest implements SaveRequest {
    private static final String TAG = "VideoTagSaveRequest";
    /* access modifiers changed from: private */
    public boolean isSubtitleSupported;
    private String mFileName;
    /* access modifiers changed from: private */
    public String mStrContent;
    /* access modifiers changed from: private */
    public ArrayList<String> mTagNames;

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return 0;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return false;
    }

    @Override // com.android.camera.storage.SaveRequest
    public void onFinish() {
    }

    public void run() {
        save();
    }

    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        if (!CollectionUtils.isEmpty(this.mTagNames)) {
            if (TextUtils.isEmpty(this.mFileName)) {
                Log.e(TAG, "file path is null");
                return;
            }
            File file = new File(this.mFileName);
            Log.d(TAG, "mCurrentVideoFilename: " + this.mFileName);
            if (!file.exists()) {
                Log.e(TAG, "file is not exists");
            } else if (Util.getDuration(this.mFileName) == 0) {
                Log.e(TAG, " video file is illegal");
            } else {
                try {
                    new RelocateMP4Editor().modifyOrRelocate(file, new MP4Edit() {
                        /* class com.android.camera.storage.VideoTagSaveRequest.AnonymousClass1 */

                        @Override // org.jcodec.movtool.MP4Edit
                        public void apply(MovieBox movieBox) {
                            MetaBox metaBox = (MetaBox) NodeBox.findFirst(movieBox, MetaBox.class, MetaBox.fourcc());
                            if (metaBox == null) {
                                metaBox = MetaBox.createMetaBox();
                                movieBox.add(metaBox);
                            }
                            Map<String, MetaValue> keyedMeta = metaBox.getKeyedMeta();
                            if (keyedMeta == null) {
                                keyedMeta = new HashMap<>();
                            }
                            Iterator it = VideoTagSaveRequest.this.mTagNames.iterator();
                            while (it.hasNext()) {
                                keyedMeta.put((String) it.next(), MetaValue.createInt(1));
                            }
                            metaBox.setKeyedMeta(keyedMeta);
                            if (!VideoTagSaveRequest.this.mStrContent.isEmpty()) {
                                UdtaBox udtaBox = (UdtaBox) NodeBox.findFirst(movieBox, UdtaBox.class, UdtaBox.fourcc());
                                Log.d(VideoTagSaveRequest.TAG, "apply: tags " + VideoTagSaveRequest.this.mStrContent);
                                if (udtaBox == null) {
                                    udtaBox = UdtaBox.createUdtaBox();
                                    movieBox.add(udtaBox);
                                }
                                if (VideoTagSaveRequest.this.isSubtitleSupported) {
                                    udtaBox.add(MsrtBox.createMsrtBox(VideoTagSaveRequest.this.mStrContent.getBytes(Charset.forName("UTF-8"))));
                                } else {
                                    udtaBox.add(MtagBox.createMtagBox(VideoTagSaveRequest.this.mStrContent.getBytes(Charset.forName("UTF-8"))));
                                }
                            }
                        }

                        @Override // org.jcodec.movtool.MP4Edit
                        public void applyToFragment(MovieBox movieBox, MovieFragmentBox[] movieFragmentBoxArr) {
                        }
                    });
                } catch (IOException | ClassCastException e2) {
                    Log.e(TAG, "apply error  " + e2);
                }
            }
        }
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context, SaverCallback saverCallback) {
    }

    public void setFielNameAndContent(String str, String str2) {
        this.mFileName = str;
        this.mStrContent = str2;
    }

    public void setTagName(ArrayList<String> arrayList) {
        this.mTagNames = arrayList;
    }

    public void setTagType(boolean z) {
        this.isSubtitleSupported = z;
    }
}
