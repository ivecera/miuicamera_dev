package com.ss.android.ugc.effectmanager.effect.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ss.android.ugc.effectmanager.common.model.UrlModel;
import com.ss.android.ugc.effectmanager.common.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposerHelper {
    public static final String COMPOSER_CHILDREN = "nodes";
    public static final String COMPOSER_CONTENT = "content";
    public static final String COMPOSER_DEFAULT_VALUE = "default_value";
    public static final String COMPOSER_EXTRA_INFO = "extra_info";
    public static final String COMPOSER_ICON = "icon";
    public static final String COMPOSER_MAX_VALUE = "max_value";
    public static final String COMPOSER_MIN_VALUE = "min_value";
    public static final String COMPOSER_NODES = "nodes";
    public static final String COMPOSER_PATH = "file";
    public static final String COMPOSER_SWITCH_MODE = "switch_mode";
    public static final String COMPOSER_TAG_NAME = "tag_name";
    public static final String COMPOSER_TYPE = "type";
    public static final String COMPOSER_UI_NAME = "UI_name";
    public static final String COMPOSER_VERSION = "version";
    public static final String CONFIG_EFFECT = "effect";
    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String CONFIG_LINK = "Link";
    public static final String CONFIG_PATH = "path";
    public static final String CONFIG_TYPE = "type";
    public static final String CONFIG_TYPE_COMPOSER = "composer";
    public static final String CONTENT_FILE_NAME = "content.json";
    public static final String DEFAULT_SERVER_URL1 = "https://sf1-hscdn-tos.pstatp.com/obj/ies.fe.effect/";
    public static final String DEFAULT_SERVER_URL2 = "https://sf3-hscdn-tos.pstatp.com/obj/ies.fe.effect/";
    public static final String DEFAULT_SERVER_URL3 = "https://sf6-hscdn-tos.pstatp.com/obj/ies.fe.effect/";
    private static List<String> serverURLs = new ArrayList();

    static {
        serverURLs.add(DEFAULT_SERVER_URL1);
        serverURLs.add(DEFAULT_SERVER_URL2);
        serverURLs.add(DEFAULT_SERVER_URL3);
    }

    @NonNull
    public static List<String> getServerUrls() {
        return serverURLs;
    }

    @Nullable
    public static ComposerNode parseComposerMaterial(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        try {
            JSONArray jSONArray = new JSONObject(FileUtils.getFileContent(str + File.separator + CONFIG_FILE_NAME)).getJSONObject(CONFIG_EFFECT).getJSONArray(CONFIG_LINK);
            if (jSONArray.length() <= 0) {
                return null;
            }
            String string = jSONArray.getJSONObject(0).getString(CONFIG_PATH);
            if (!jSONArray.getJSONObject(0).getString("type").toLowerCase().equals(CONFIG_TYPE_COMPOSER)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(FileUtils.getFileContent(str + File.separator + string + CONTENT_FILE_NAME));
            ComposerNode composerNode = new ComposerNode();
            composerNode.tag_name = "root";
            composerNode.UI_name = "Composer";
            composerNode.type = 4;
            JSONObject jSONObject2 = jSONObject.getJSONObject("content");
            composerNode.children = new ArrayList();
            JSONArray jSONArray2 = jSONObject2.getJSONArray("nodes");
            for (int i = 0; i < jSONArray2.length(); i++) {
                ComposerNode parseComposerNode = parseComposerNode(jSONArray2.getJSONObject(i), str2, str3);
                if (parseComposerNode != null) {
                    parseComposerNode.parent = composerNode;
                }
                composerNode.children.add(parseComposerNode);
            }
            return composerNode;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static ComposerNode parseComposerNode(@NonNull JSONObject jSONObject, @NonNull String str, String str2) {
        try {
            ComposerNode composerNode = new ComposerNode();
            composerNode.type = jSONObject.getInt("type");
            composerNode.file = jSONObject.optString(COMPOSER_PATH, "");
            composerNode.icon = DEFAULT_SERVER_URL1 + jSONObject.optString("icon", null);
            composerNode.UI_name = jSONObject.optString(COMPOSER_UI_NAME, null);
            composerNode.tag_name = jSONObject.getString(COMPOSER_TAG_NAME);
            composerNode.default_value = (float) jSONObject.optDouble(COMPOSER_DEFAULT_VALUE, 0.0d);
            composerNode.min_value = (float) jSONObject.optDouble(COMPOSER_MIN_VALUE, 0.0d);
            composerNode.max_value = (float) jSONObject.optDouble(COMPOSER_MAX_VALUE, 0.0d);
            composerNode.switch_mode = jSONObject.optInt(COMPOSER_SWITCH_MODE, 0);
            if (jSONObject.has("nodes")) {
                composerNode.children = new ArrayList();
                JSONArray jSONArray = jSONObject.getJSONArray("nodes");
                for (int i = 0; i < jSONArray.length(); i++) {
                    ComposerNode parseComposerNode = parseComposerNode(jSONArray.getJSONObject(i), str, str2);
                    if (parseComposerNode != null) {
                        parseComposerNode.parent = composerNode;
                        composerNode.children.add(parseComposerNode);
                    }
                }
                if (composerNode.children.size() == 0 || (composerNode.children.size() == 1 && composerNode.children.get(0).type == -1)) {
                    return null;
                }
            } else {
                List<String> serverUrls = getServerUrls();
                ArrayList arrayList = new ArrayList();
                Iterator<String> it = serverUrls.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next() + composerNode.file);
                }
                UrlModel urlModel = new UrlModel();
                urlModel.setUrlList(arrayList);
                urlModel.setUri(composerNode.file);
                Effect effect = new Effect();
                effect.setFileUrl(urlModel);
                effect.setId(composerNode.file);
                effect.setEffectId(composerNode.file);
                effect.setZipPath(str2 + File.separator + effect.getId() + ".zip");
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(File.separator);
                sb.append(effect.getId());
                effect.setUnzipPath(sb.toString());
                composerNode.effect = effect;
                JSONObject optJSONObject = jSONObject.optJSONObject(COMPOSER_EXTRA_INFO);
                String str3 = "0";
                if (optJSONObject != null) {
                    str3 = optJSONObject.optString("version", str3);
                }
                if (versionCompare(str, str3) < 0) {
                    return null;
                }
            }
            return composerNode;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void setServerUrls(@NonNull List<String> list) {
        serverURLs = list;
    }

    public static int versionCompare(@NonNull String str, @NonNull String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int i = 0;
        while (i < split.length && i < split2.length && split[i].equals(split2[i])) {
            i++;
        }
        return (i >= split.length || i >= split2.length) ? Integer.signum(split.length - split2.length) : Integer.signum(Integer.valueOf(split[i]).compareTo(Integer.valueOf(split2[i])));
    }
}
