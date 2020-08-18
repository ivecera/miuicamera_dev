package d.a.a;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import miui.cloud.backup.data.DataPackage;
import miui.cloud.backup.data.SettingItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SettingsBackupHelper */
public class d {
    private static final String KEY_DATA = "data";
    private static final String KEY_VERSION = "version";
    private static final String TAG = "SettingsBackup";

    private d() {
    }

    public static DataPackage a(Context context, ParcelFileDescriptor parcelFileDescriptor, b bVar) throws IOException {
        IOException e2;
        JSONException e3;
        DataPackage dataPackage = new DataPackage();
        bVar.onBackupSettings(context, dataPackage);
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Collection<SettingItem<?>> values = dataPackage.wm().values();
        FileOutputStream fileOutputStream = null;
        if (values != null) {
            try {
                for (SettingItem<?> settingItem : values) {
                    jSONArray.put(settingItem.toJson());
                }
                jSONObject.put(c.EXTRA_PACKAGE_NAME, context.getPackageName());
                jSONObject.put("version", bVar.getCurrentVersion(context));
                jSONObject.put("data", jSONArray);
            } catch (IOException e4) {
                e2 = e4;
                Log.e("SettingsBackup", "IOException in backupSettings", e2);
                closeQuietly(fileOutputStream);
                return dataPackage;
            } catch (JSONException e5) {
                e3 = e5;
                Log.e("SettingsBackup", "JSONException in backupSettings", e3);
                closeQuietly(fileOutputStream);
                return dataPackage;
            } catch (Throwable th) {
                th = th;
                closeQuietly(fileOutputStream);
                throw th;
            }
        }
        FileOutputStream fileOutputStream2 = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
        try {
            fileOutputStream2.write(jSONObject.toString().getBytes("utf-8"));
            fileOutputStream2.flush();
            fileOutputStream2.close();
            closeQuietly(fileOutputStream2);
        } catch (IOException e6) {
            fileOutputStream = fileOutputStream2;
            e2 = e6;
            Log.e("SettingsBackup", "IOException in backupSettings", e2);
            closeQuietly(fileOutputStream);
            return dataPackage;
        } catch (JSONException e7) {
            fileOutputStream = fileOutputStream2;
            e3 = e7;
            Log.e("SettingsBackup", "JSONException in backupSettings", e3);
            closeQuietly(fileOutputStream);
            return dataPackage;
        } catch (Throwable th2) {
            fileOutputStream = fileOutputStream2;
            th = th2;
            closeQuietly(fileOutputStream);
            throw th;
        }
        return dataPackage;
    }

    public static void a(String str, ParcelFileDescriptor parcelFileDescriptor) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            FileInputStream fileInputStream4 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                new File(str.substring(0, str.lastIndexOf(File.separator))).mkdirs();
                FileOutputStream fileOutputStream3 = new FileOutputStream(new File(str));
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream4.read(bArr);
                        if (read > 0) {
                            fileOutputStream3.write(bArr, 0, read);
                        } else {
                            fileOutputStream3.flush();
                            closeQuietly(fileInputStream4);
                            closeQuietly(fileOutputStream3);
                            return;
                        }
                    }
                } catch (FileNotFoundException e2) {
                    fileInputStream = fileInputStream4;
                    fileOutputStream2 = fileOutputStream3;
                    e = e2;
                    fileInputStream3 = fileInputStream;
                    Log.e("SettingsBackup", "FileNotFoundException in restoreFiles: " + str, e);
                    closeQuietly(fileInputStream3);
                    closeQuietly(fileOutputStream);
                } catch (IOException e3) {
                    fileInputStream2 = fileInputStream4;
                    fileOutputStream = fileOutputStream3;
                    e = e3;
                    fileInputStream3 = fileInputStream2;
                    Log.e("SettingsBackup", "IOException in restoreFiles: " + str, e);
                    closeQuietly(fileInputStream3);
                    closeQuietly(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream3 = fileInputStream4;
                    fileOutputStream = fileOutputStream3;
                    closeQuietly(fileInputStream3);
                    closeQuietly(fileOutputStream);
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                fileInputStream = fileInputStream4;
                fileOutputStream2 = null;
                fileInputStream3 = fileInputStream;
                Log.e("SettingsBackup", "FileNotFoundException in restoreFiles: " + str, e);
                closeQuietly(fileInputStream3);
                closeQuietly(fileOutputStream);
            } catch (IOException e5) {
                e = e5;
                fileInputStream2 = fileInputStream4;
                fileOutputStream = null;
                fileInputStream3 = fileInputStream2;
                Log.e("SettingsBackup", "IOException in restoreFiles: " + str, e);
                closeQuietly(fileInputStream3);
                closeQuietly(fileOutputStream);
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                fileInputStream3 = fileInputStream4;
                closeQuietly(fileInputStream3);
                closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            fileOutputStream2 = null;
            Log.e("SettingsBackup", "FileNotFoundException in restoreFiles: " + str, e);
            closeQuietly(fileInputStream3);
            closeQuietly(fileOutputStream);
        } catch (IOException e7) {
            e = e7;
            fileOutputStream = null;
            Log.e("SettingsBackup", "IOException in restoreFiles: " + str, e);
            closeQuietly(fileInputStream3);
            closeQuietly(fileOutputStream);
        } catch (Throwable th3) {
            th = th3;
            closeQuietly(fileInputStream3);
            closeQuietly(fileOutputStream);
            throw th;
        }
    }

    public static void a(DataPackage dataPackage) {
        for (Map.Entry<String, ParcelFileDescriptor> entry : dataPackage.xm().entrySet()) {
            a(entry.getKey(), entry.getValue());
        }
    }

    public static void b(Context context, ParcelFileDescriptor parcelFileDescriptor, b bVar) throws IOException {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(parcelFileDescriptor.getFileDescriptor()));
            try {
                StringBuilder sb = new StringBuilder();
                String property = System.getProperty("line.separator");
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append(property);
                }
                JSONObject jSONObject = new JSONObject(sb.toString());
                if (jSONObject.length() > 0) {
                    int optInt = jSONObject.optInt("version");
                    JSONArray optJSONArray = jSONObject.optJSONArray("data");
                    DataPackage dataPackage = new DataPackage();
                    if (optJSONArray != null) {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                            if (optJSONObject != null) {
                                SettingItem<?> b2 = SettingItem.b(optJSONObject);
                                dataPackage.a(b2.key, b2);
                            }
                        }
                    }
                    bVar.onRestoreSettings(context, dataPackage, optInt);
                }
                closeQuietly(bufferedReader);
            } catch (IOException e2) {
                e = e2;
                bufferedReader2 = bufferedReader;
                Log.e("SettingsBackup", "IOException in restoreSettings", e);
                closeQuietly(bufferedReader2);
            } catch (JSONException e3) {
                e = e3;
                bufferedReader2 = bufferedReader;
                Log.e("SettingsBackup", "JSONException in restoreSettings", e);
                closeQuietly(bufferedReader2);
            } catch (Throwable th) {
                th = th;
                closeQuietly(bufferedReader);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            Log.e("SettingsBackup", "IOException in restoreSettings", e);
            closeQuietly(bufferedReader2);
        } catch (JSONException e5) {
            e = e5;
            Log.e("SettingsBackup", "JSONException in restoreSettings", e);
            closeQuietly(bufferedReader2);
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = bufferedReader2;
            closeQuietly(bufferedReader);
            throw th;
        }
    }

    private static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:1|2|3|4|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
    private static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
    }

    private static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException unused) {
            }
        }
    }
}
