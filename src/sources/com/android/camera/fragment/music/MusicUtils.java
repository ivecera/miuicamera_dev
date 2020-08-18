package com.android.camera.fragment.music;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.storage.Storage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    private static final String[] LOCAL_MUSIC_LIST_CN = {"Innervation", "Cheerful"};
    private static final String[] LOCAL_MUSIC_LIST_GLOBAL = {"Smooth", "Autumn", "City", "Sports", "Cheerful", "Morning", "Lovely", "Dynamic", "Fashion", "Chic"};

    public static List<LiveMusicInfo> getMusicListFromLocalFolder(String str, Context context) {
        ArrayList arrayList = new ArrayList();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        File file = new File(str);
        String string = context.getResources().getString(R.string.live_music_author);
        String[] strArr = Util.isGlobalVersion() ? LOCAL_MUSIC_LIST_GLOBAL : LOCAL_MUSIC_LIST_CN;
        String str2 = Util.isGlobalVersion() ? ".aac" : ".mp3";
        if (file.listFiles() != null) {
            for (String str3 : strArr) {
                String str4 = str3 + str2;
                File file2 = new File(str + str4);
                if (file2.exists()) {
                    LiveMusicInfo liveMusicInfo = new LiveMusicInfo();
                    mediaMetadataRetriever.setDataSource(file2.getAbsolutePath());
                    String extractMetadata = mediaMetadataRetriever.extractMetadata(7);
                    if (extractMetadata == null) {
                        extractMetadata = str4.substring(0, str4.length() - 4);
                    }
                    liveMusicInfo.mTitle = extractMetadata;
                    String extractMetadata2 = mediaMetadataRetriever.extractMetadata(1);
                    if (extractMetadata2 == null) {
                        extractMetadata2 = FileUtils.MUSIC_LOCAL + extractMetadata + Storage.JPEG_SUFFIX;
                    }
                    liveMusicInfo.mThumbnailUrl = extractMetadata2;
                    String extractMetadata3 = mediaMetadataRetriever.extractMetadata(2);
                    if (extractMetadata3 == null) {
                        extractMetadata3 = string;
                    }
                    liveMusicInfo.mAuthor = extractMetadata3;
                    String extractMetadata4 = mediaMetadataRetriever.extractMetadata(9);
                    liveMusicInfo.mDurationText = extractMetadata4.substring(0, extractMetadata4.length() - 3);
                    liveMusicInfo.mPlayUrl = file2.getAbsolutePath();
                    arrayList.add(liveMusicInfo);
                    Log.d("LiveMusicInfo", liveMusicInfo.mAuthor + ", " + liveMusicInfo.mTitle + ", " + liveMusicInfo.mPlayUrl + "," + liveMusicInfo.mThumbnailUrl + "," + liveMusicInfo.mDurationText);
                }
            }
        }
        mediaMetadataRetriever.release();
        return arrayList;
    }
}
