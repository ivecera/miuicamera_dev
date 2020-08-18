package com.ss.android.ttve.log;

import com.ss.android.medialib.log.ILog2Client;
import com.ss.android.medialib.log.Log2Client;
import com.ss.android.medialib.log.Log2ClientInvoker;
import com.ss.android.vesdk.VELogProtocol;

public class TELog2Client {
    private static final String TAG = "[VESDK]";
    private static VELogProtocol mLogger;

    public static void init() {
        TELog2ClientInvoker.nativeInit();
        Log2Client.register(new ILog2Client() {
            /* class com.ss.android.ttve.log.TELog2Client.AnonymousClass1 */

            @Override // com.ss.android.medialib.log.ILog2Client
            public void logToLocal(int i, String str) {
                TELog2Client.logToLocal(i, str);
            }
        });
    }

    public static void logToLocal(int i, String str) {
        VELogProtocol vELogProtocol = mLogger;
        if (vELogProtocol != null) {
            vELogProtocol.logToLocal(i, TAG + str);
        }
    }

    public static void registerLogger(VELogProtocol vELogProtocol) {
        mLogger = vELogProtocol;
    }

    public static void setLog2ClientSwitch(boolean z) {
        TELog2ClientInvoker.nativeSetLog2ClientSwitch(z);
        Log2ClientInvoker.nativeSetLog2ClientSwitch(z);
    }
}
