package f.a.a.a.a;

import java.util.ArrayList;

/* compiled from: IResultValue */
public final class e {
    public static final int Sy = 0;
    public static final int Ty = 1;
    public static final int Uy = 2;
    public static final int Vy = 3;
    public static final int Wy = 4;
    public static final int Xy = 5;
    public static final int Yy = 6;
    public static final int Zy = 7;
    public static final int _y = 8;
    public static final int bz = 9;
    public static final int cz = 10;
    public static final int dz = 11;
    public static final int ez = 12;
    public static final int fz = 13;
    public static final int gz = 14;
    public static final int hz = 15;
    public static final int iz = 16;
    public static final int jz = 17;
    public static final int kz = 18;
    public static final int lz = 19;
    public static final int mz = 20;
    public static final int nz = 21;
    public static final int oz = 22;
    public static final int pz = 23;
    public static final int qz = 24;
    public static final int rz = 25;
    public static final int sz = 26;
    public static final int tz = 27;
    public static final int uz = 28;
    public static final int vz = 29;
    public static final int wz = 30;
    public static final int xz = 31;
    public static final int yz = 32;
    public static final int zz = 1024;

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("MISYS_SUCCESS");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("MISYS_EPERM");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("MISYS_NOENT");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("MISYS_ESRCH");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("MISYS_EINTR");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("MISYS_EIO");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("MISYS_ENXIO");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("MISYS_E2BIG");
            i2 |= 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("MISYS_ENOEXEC");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("MISYS_EBADF");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("MISYS_ECHILD");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("MISYS_EAGAIN");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("MISYS_ENOMEM");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("MISYS_EACCES");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("MISYS_EFAULT");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("MISYS_ENOTBLK");
            i2 |= 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("MISYS_EBUSY");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("MISYS_EEXIST");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("MISYS_EXDEV");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("MISYS_ENODEV");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("MISYS_ENOTDIR");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("MISYS_EISDIR");
            i2 |= 21;
        }
        if ((i & 22) == 22) {
            arrayList.add("MISYS_EINVAL");
            i2 |= 22;
        }
        if ((i & 23) == 23) {
            arrayList.add("MISYS_ENFILE");
            i2 |= 23;
        }
        if ((i & 24) == 24) {
            arrayList.add("MISYS_EMFILE");
            i2 |= 24;
        }
        if ((i & 25) == 25) {
            arrayList.add("MISYS_ENOTTY");
            i2 |= 25;
        }
        if ((i & 26) == 26) {
            arrayList.add("MISYS_ETXTBSY");
            i2 |= 26;
        }
        if ((i & 27) == 27) {
            arrayList.add("MISYS_EFBIG");
            i2 |= 27;
        }
        if ((i & 28) == 28) {
            arrayList.add("MISYS_ENOSPC");
            i2 |= 28;
        }
        if ((i & 29) == 29) {
            arrayList.add("MISYS_ESPIPE");
            i2 |= 29;
        }
        if ((i & 30) == 30) {
            arrayList.add("MISYS_EROFS");
            i2 |= 30;
        }
        if ((i & 31) == 31) {
            arrayList.add("MISYS_EMLINK");
            i2 |= 31;
        }
        if ((i & 32) == 32) {
            arrayList.add("MISYS_EPIPE");
            i2 |= 32;
        }
        if ((i & 1024) == 1024) {
            arrayList.add("MISYS_UNKNOWN");
            i2 |= 1024;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }

    public static final String toString(int i) {
        if (i == 0) {
            return "MISYS_SUCCESS";
        }
        if (i == 1) {
            return "MISYS_EPERM";
        }
        if (i == 2) {
            return "MISYS_NOENT";
        }
        if (i == 3) {
            return "MISYS_ESRCH";
        }
        if (i == 4) {
            return "MISYS_EINTR";
        }
        if (i == 5) {
            return "MISYS_EIO";
        }
        if (i == 6) {
            return "MISYS_ENXIO";
        }
        if (i == 7) {
            return "MISYS_E2BIG";
        }
        if (i == 8) {
            return "MISYS_ENOEXEC";
        }
        if (i == 9) {
            return "MISYS_EBADF";
        }
        if (i == 10) {
            return "MISYS_ECHILD";
        }
        if (i == 11) {
            return "MISYS_EAGAIN";
        }
        if (i == 12) {
            return "MISYS_ENOMEM";
        }
        if (i == 13) {
            return "MISYS_EACCES";
        }
        if (i == 14) {
            return "MISYS_EFAULT";
        }
        if (i == 15) {
            return "MISYS_ENOTBLK";
        }
        if (i == 16) {
            return "MISYS_EBUSY";
        }
        if (i == 17) {
            return "MISYS_EEXIST";
        }
        if (i == 18) {
            return "MISYS_EXDEV";
        }
        if (i == 19) {
            return "MISYS_ENODEV";
        }
        if (i == 20) {
            return "MISYS_ENOTDIR";
        }
        if (i == 21) {
            return "MISYS_EISDIR";
        }
        if (i == 22) {
            return "MISYS_EINVAL";
        }
        if (i == 23) {
            return "MISYS_ENFILE";
        }
        if (i == 24) {
            return "MISYS_EMFILE";
        }
        if (i == 25) {
            return "MISYS_ENOTTY";
        }
        if (i == 26) {
            return "MISYS_ETXTBSY";
        }
        if (i == 27) {
            return "MISYS_EFBIG";
        }
        if (i == 28) {
            return "MISYS_ENOSPC";
        }
        if (i == 29) {
            return "MISYS_ESPIPE";
        }
        if (i == 30) {
            return "MISYS_EROFS";
        }
        if (i == 31) {
            return "MISYS_EMLINK";
        }
        if (i == 32) {
            return "MISYS_EPIPE";
        }
        if (i == 1024) {
            return "MISYS_UNKNOWN";
        }
        return "0x" + Integer.toHexString(i);
    }
}
