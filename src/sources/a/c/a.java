package a.c;

import android.content.ContentResolver;
import android.provider.Settings;
import java.util.List;

/* compiled from: MiuiSettings */
public class a {

    /* renamed from: a.c.a$a  reason: collision with other inner class name */
    /* compiled from: MiuiSettings */
    public static class C0002a {
        public static final String Ah = "can_nav_bar_hide";
        public static final String Bh = "show_assistant_button";
        public static final String Ch = "open_privacy_contact_in_second_space";
        public static final String Dh = "open_second_space_status_icon";
        public static final String uh = "immersive.preconfirms=*";
        public static final String vh = "immersive.navigation=*:immersive.preconfirms=*";
        public static final String wh = "force_immersive_nav_bar";
        public static final String xh = "show_gesture_back_animation";
        public static final String yh = "force_fsg_nav_bar";
        public static final String zh = "hide_nav_bar";

        public static boolean a(ContentResolver contentResolver) {
            return Settings.Global.getInt(contentResolver, Dh, 1) != 0;
        }

        public static boolean a(ContentResolver contentResolver, String str) {
            return Settings.Global.getInt(contentResolver, str, 0) != 0;
        }

        public static boolean a(ContentResolver contentResolver, String str, boolean z) {
            return Settings.Global.putInt(contentResolver, str, z ? 1 : 0);
        }

        public static boolean a(ContentResolver contentResolver, boolean z) {
            return a(contentResolver, Dh, z);
        }

        public static boolean b(ContentResolver contentResolver) {
            return a(contentResolver, Ch);
        }

        public static boolean b(ContentResolver contentResolver, boolean z) {
            return a(contentResolver, Ch, z);
        }
    }

    /* compiled from: MiuiSettings */
    public static final class b {
        public static final String Eh = "single_key_use_enable";
        public static final int Fh = 0;
        public static final int Gh = 1;
        public static final String Hh = "key_bank_card_in_ese";
        public static final int Ih = 0;
        public static final String Jh = "key_trans_card_in_ese";
        public static final int Kh = 0;
        public static final String Lh = "key_long_press_volume_down";
        public static final String Mh = "none";
        public static final String Nh = "Street-snap";
        public static final String Oh = "Street-snap-picture";
        public static final String Ph = "Street-snap-movie";
        public static final String Qh = "public_transportation_shortcuts";
        public static final String Rh = "volumekey_launch_camera";
    }

    /* compiled from: MiuiSettings */
    public static class c {
        public static final String Sh = "access_control_lock_enabled";
    }

    /* compiled from: MiuiSettings */
    public static final class d {
        public static final String Th = "productData";

        /* renamed from: a.c.a$d$a  reason: collision with other inner class name */
        /* compiled from: MiuiSettings */
        public static class C0003a {
        }

        public static int a(ContentResolver contentResolver, String str, String str2, int i) {
            return i;
        }

        public static long a(ContentResolver contentResolver, String str, String str2, long j) {
            return j;
        }

        public static C0003a a(ContentResolver contentResolver, String str, String str2, String str3, boolean z) {
            return null;
        }

        public static String a(ContentResolver contentResolver, String str, String str2, String str3) {
            return str3;
        }

        public static boolean a(ContentResolver contentResolver, String str, String str2, boolean z) {
            return z;
        }

        public static List<C0003a> b(ContentResolver contentResolver, String str) {
            return null;
        }
    }
}
