package com.arcsoft.avatar.recoder;

import android.opengl.GLES30;
import com.arcsoft.avatar.gl.GLFramebuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FrameQueue {

    /* renamed from: a  reason: collision with root package name */
    private static final String f146a = "FrameQueue";

    /* renamed from: b  reason: collision with root package name */
    private FrameItem f147b = null;

    /* renamed from: c  reason: collision with root package name */
    private FrameItem f148c = null;

    /* renamed from: d  reason: collision with root package name */
    private List<FrameItem> f149d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private Queue<FrameItem> f150e = new LinkedList();

    /* renamed from: f  reason: collision with root package name */
    private boolean f151f;

    public void addEmptyFrameForConsumer() {
        FrameItem frameItem = this.f148c;
        if (frameItem != null) {
            this.f149d.add(frameItem);
            this.f148c = null;
        }
    }

    public void addFrameForProducer() {
        FrameItem frameItem = this.f147b;
        if (frameItem != null) {
            this.f150e.offer(frameItem);
            this.f147b = null;
        }
    }

    public void deleteSync(FrameItem frameItem) {
        try {
            if (0 != frameItem.f145a) {
                GLES30.glDeleteSync(frameItem.f145a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            frameItem.f145a = 0;
            throw th;
        }
        frameItem.f145a = 0;
    }

    public FrameItem getFrameForConsumer() {
        FrameItem frameItem = this.f148c;
        if (frameItem != null) {
            return frameItem;
        }
        if (this.f150e.isEmpty()) {
            return null;
        }
        this.f148c = this.f150e.poll();
        return this.f148c;
    }

    public FrameItem getFrameForProducer() {
        FrameItem frameItem = this.f147b;
        if (frameItem != null) {
            return frameItem;
        }
        if (!this.f149d.isEmpty()) {
            this.f147b = this.f149d.remove(0);
        } else if (this.f150e.isEmpty()) {
            return null;
        } else {
            this.f147b = this.f150e.poll();
        }
        return this.f147b;
    }

    public void init(int i, int i2, int i3, boolean z) {
        unInit();
        for (int i4 = 0; i4 < i; i4++) {
            FrameItem frameItem = new FrameItem();
            frameItem.mIsEmpty = true;
            frameItem.mIsInited = true;
            frameItem.mFrameIndex = i4;
            frameItem.mFramebuffer = new GLFramebuffer();
            frameItem.mFramebuffer.init(i2, i3, z);
            this.f149d.add(frameItem);
        }
        this.f151f = true;
    }

    public boolean isIsInited() {
        return this.f151f;
    }

    public int queueSize() {
        return this.f150e.size();
    }

    public void unInit() {
        GLFramebuffer gLFramebuffer;
        GLFramebuffer gLFramebuffer2;
        GLFramebuffer gLFramebuffer3;
        FrameItem frameItem = this.f147b;
        if (!(frameItem == null || (gLFramebuffer3 = frameItem.mFramebuffer) == null)) {
            gLFramebuffer3.unInit();
            deleteSync(this.f147b);
            this.f147b.mFramebuffer = null;
            this.f147b = null;
        }
        FrameItem frameItem2 = this.f148c;
        if (!(frameItem2 == null || (gLFramebuffer2 = frameItem2.mFramebuffer) == null)) {
            gLFramebuffer2.unInit();
            deleteSync(this.f148c);
            this.f148c.mFramebuffer = null;
            this.f148c = null;
        }
        if (!this.f149d.isEmpty()) {
            for (FrameItem frameItem3 : this.f149d) {
                GLFramebuffer gLFramebuffer4 = frameItem3.mFramebuffer;
                if (gLFramebuffer4 != null) {
                    gLFramebuffer4.unInit();
                    deleteSync(frameItem3);
                    frameItem3.mFramebuffer = null;
                }
            }
        }
        this.f149d.clear();
        while (!this.f150e.isEmpty()) {
            FrameItem poll = this.f150e.poll();
            if (!(poll == null || (gLFramebuffer = poll.mFramebuffer) == null)) {
                gLFramebuffer.unInit();
                deleteSync(poll);
                poll.mFramebuffer = null;
            }
        }
        this.f150e.clear();
        this.f151f = false;
    }
}
