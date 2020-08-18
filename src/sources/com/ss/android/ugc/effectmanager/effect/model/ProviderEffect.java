package com.ss.android.ugc.effectmanager.effect.model;

public class ProviderEffect {
    private String click_url;
    private String id;
    private String path;
    private StickerBean sticker;
    private StickerBean thumbnail_sticker;
    private String title;

    public class StickerBean {
        private String height;
        private String size;
        private String url;
        private String width;

        public StickerBean() {
        }

        public String getHeight() {
            return this.height;
        }

        public String getSize() {
            return this.size;
        }

        public String getUrl() {
            return this.url;
        }

        public String getWidth() {
            return this.width;
        }

        public void setHeight(String str) {
            this.height = str;
        }

        public void setSize(String str) {
            this.size = str;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public void setWidth(String str) {
            this.width = str;
        }

        public String toString() {
            return "StickerBean{url='" + this.url + '\'' + ", width='" + this.width + '\'' + ", height='" + this.height + '\'' + ", size='" + this.size + '\'' + '}';
        }
    }

    public String getClickUrl() {
        return this.click_url;
    }

    public String getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public StickerBean getSticker() {
        return this.sticker;
    }

    public StickerBean getThumbnailSticker() {
        return this.thumbnail_sticker;
    }

    public String getTitle() {
        return this.title;
    }

    public void setClickUrl(String str) {
        this.click_url = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setSticker(StickerBean stickerBean) {
        this.sticker = stickerBean;
    }

    public void setThumbnailSticker(StickerBean stickerBean) {
        this.thumbnail_sticker = stickerBean;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        return "StickerListBean{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", thumbnail_sticker=" + this.thumbnail_sticker + ", sticker=" + this.sticker + ", path='" + this.path + '\'' + '}';
    }
}
