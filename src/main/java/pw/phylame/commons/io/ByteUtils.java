package pw.phylame.commons.io;

import lombok.val;

public final class ByteUtils {
    public enum Endian {
        BIG,
        LITTLE
    }

    public static final Endian DEFAULT_ENDIAN = Endian.BIG;

    public static byte[] putInt8(byte x) {
        return putUint8(x, new byte[1], 0);
    }

    public static byte[] putInt8(byte x, byte[] dst, int off) {
        return putUint8(x, dst, off);
    }

    public static byte[] putUint8(int x) {
        return putUint8(x, new byte[1], 0);
    }

    public static byte[] putUint8(int x, byte[] dst, int off) {
        dst[off] = (byte) x;
        return dst;
    }

    public static byte[] putInt16(short x) {
        return putUint16(x, new byte[2], 0, DEFAULT_ENDIAN);
    }

    public static byte[] putInt16(short x, byte[] dst, int off) {
        return putUint16(x, dst, off, DEFAULT_ENDIAN);
    }

    public static byte[] putInt16(short x, Endian endian) {
        return putUint16(x, new byte[2], 0, endian);
    }

    public static byte[] putInt16(short x, byte[] dst, int off, Endian endian) {
        return putUint16(x, dst, off, endian);
    }

    public static byte[] putUint16(int x) {
        return putUint16(x, new byte[2], 0, DEFAULT_ENDIAN);
    }

    public static byte[] putUint16(int x, byte[] dst, int off) {
        return putUint16(x, dst, off, DEFAULT_ENDIAN);
    }

    public static byte[] putUint16(int x, Endian endian) {
        return putUint16(x, new byte[2], 0, endian);
    }

    public static byte[] putUint16(int x, byte[] dst, int off, Endian endian) {
        if (endian == Endian.BIG) {
            dst[off] = (byte) (x >> 8);
            dst[off + 1] = (byte) (x);
        } else {
            dst[off + 1] = (byte) (x >> 8);
            dst[off] = (byte) x;
        }
        return dst;
    }

    public static byte[] putInt32(int x) {
        return putUint32(x, new byte[4], 0, DEFAULT_ENDIAN);
    }

    public static byte[] putInt32(int x, byte[] dst, int off) {
        return putUint32(x, dst, off, DEFAULT_ENDIAN);
    }

    public static byte[] putInt32(int x, Endian endian) {
        return putUint32(x, new byte[4], 0, endian);
    }

    public static byte[] putInt32(int x, byte[] dst, int off, Endian endian) {
        return putUint32(x, dst, off, endian);
    }

    public static byte[] putUint32(long x) {
        return putUint32(x, new byte[4], 0, DEFAULT_ENDIAN);
    }

    public static byte[] putUint32(long x, byte[] dst, int off) {
        return putUint32(x, dst, off, DEFAULT_ENDIAN);
    }

    public static byte[] putUint32(long x, Endian endian) {
        return putUint32(x, new byte[4], 0, endian);
    }

    public static byte[] putUint32(long x, byte[] dst, int off, Endian endian) {
        if (endian == Endian.BIG) {
            dst[off] = (byte) (x >> 24);
            dst[off + 1] = (byte) (x >> 16);
            dst[off + 2] = (byte) (x >> 8);
            dst[off + 3] = (byte) x;
        } else {
            dst[off + 3] = (byte) (x >> 24);
            dst[off + 2] = (byte) (x >> 16);
            dst[off + 1] = (byte) (x >> 8);
            dst[off] = (byte) x;
        }
        return dst;
    }

    public static byte getInt8(byte[] src, int off) {
        return src[off];
    }

    public static int getUint8(byte[] src, int off) {
        return src[off] & 0xFF;
    }

    public static short getInt16(byte[] src, int off) {
        return getInt16(src, off, DEFAULT_ENDIAN);
    }

    public static short getInt16(byte[] src, int off, Endian endian) {
        if (endian == Endian.BIG) {
            return (short) ((src[off] << 8) | (src[1 + off] & 0xFF));
        } else {
            return (short) ((src[1 + off] << 8) | (src[off] & 0xFF));
        }
    }

    public static int getUint16(byte[] src, int off) {
        return getUint16(src, off, DEFAULT_ENDIAN);
    }

    public static int getUint16(byte[] src, int off, Endian endian) {
        return getInt16(src, off, endian) & 0xFFFF;
    }

    public static int getInt32(byte[] src, int off) {
        return getInt32(src, off, DEFAULT_ENDIAN);
    }

    public static int getInt32(byte[] src, int off, Endian endian) {
        if (endian == Endian.LITTLE) {
            return ((src[3 + off] & 0xFF) << 24)
                    | ((src[2 + off] & 0xFF) << 16)
                    | ((src[1 + off] & 0xFF) << 8)
                    | (src[off] & 0xFF);
        } else {
            return ((src[off] & 0xFF) << 24)
                    | ((src[1 + off] & 0xFF) << 16)
                    | ((src[2 + off] & 0xFF) << 8)
                    | (src[3 + off] & 0xFF);
        }
    }

    public static long getUint32(byte[] src, int off) {
        return getInt32(src, off, DEFAULT_ENDIAN);
    }

    public static long getUint32(byte[] src, int off, Endian endian) {
        return getInt32(src, off, endian) & 0xFFFFFFFFL;
    }

    // for Java platform conversion

    public static int getUbyte(byte n) {
        return n & 0xFF;
    }

    public static int getUshort(short n) {
        return n & 0xFFFF;
    }

    public static long getUint(int n) {
        return n & 0xFFFFFFFFL;
    }

    public static byte[] putShort(short x) {
        return putInt16(x, DEFAULT_ENDIAN);
    }

    public static short getShort(byte[] src, int off) {
        return getInt16(src, off, DEFAULT_ENDIAN);
    }

    public static byte[] putInt(int x) {
        return putInt32(x, DEFAULT_ENDIAN);
    }

    public static int getInt(byte[] src, int off) {
        return getInt32(src, off, DEFAULT_ENDIAN);
    }

    public static byte[] putLong(long x) {
        val b = new byte[8];
        putLong(x, b, 0);
        return b;
    }

    public static void putLong(long x, byte[] dst, int off) {
        dst[off] = (byte) (x >> 56);
        dst[off + 1] = (byte) (x >> 48);
        dst[off + 2] = (byte) (x >> 40);
        dst[off + 3] = (byte) (x >> 32);
        dst[off + 4] = (byte) (x >> 24);
        dst[off + 5] = (byte) (x >> 16);
        dst[off + 6] = (byte) (x >> 8);
        dst[off + 7] = (byte) x;
    }

    public static long getLong(byte[] src, int off) {
        return ((((long) src[off + 7] & 0xFF) << 56)
                | (((long) src[off + 6] & 0xFF) << 48)
                | (((long) src[off + 5] & 0xFF) << 40)
                | (((long) src[off + 4] & 0xFF) << 32)
                | (((long) src[off + 3] & 0xFF) << 24)
                | (((long) src[off + 2] & 0xFF) << 16)
                | (((long) src[off + 1] & 0xFF) << 8)
                | ((long) src[off] & 0xFF));
    }

    public static byte[] putChar(char x) {
        val b = new byte[2];
        putChar(x, b, 0);
        return b;
    }

    public static void putChar(char ch, byte[] dst, int off) {
        int temp = ch;
        for (int i = 0; i < 2; i++) {
            dst[off + i] = Integer.valueOf(temp & 0xFF).byteValue();
            temp = temp >> 8;
        }
    }

    public static char getChar(byte[] src, int off) {
        int ch = 0;
        if (src[off + 1] > 0) {
            ch += src[off + 1];
        } else {
            ch += 256 + src[off];
        }
        ch <<= 8;
        if (src[off] > 0) {
            ch += src[off + 1];
        } else {
            ch += 256 + src[off];
        }
        return (char) ch;
    }

    public static byte[] putFloat(float x) {
        val b = new byte[4];
        putFloat(x, b, 0);
        return b;
    }

    public static void putFloat(float x, byte[] dst, int index) {
        int n = Float.floatToIntBits(x);
        for (int i = 3; i >= 0; i++) {
            dst[index + i] = Integer.valueOf(n).byteValue();
            n = n >> 8;
        }
    }

    public static float getFloat(byte[] src, int off) {
        int n;
        n = src[off + 3];
        n &= 0xFF;
        n |= ((long) src[off + 2] << 8);
        n &= 0xFFFF;
        n |= ((long) src[off + 1] << 16);
        n &= 0xFFFFFF;
        n |= ((long) src[off] << 24);
        return Float.intBitsToFloat(n);
    }

    public static byte[] putDouble(double x) {
        val b = new byte[8];
        putDouble(x, b, 0);
        return b;
    }

    public static void putDouble(double x, byte[] dst, int off) {
        long n = Double.doubleToLongBits(x);
        for (int i = 7; i >= 0; i++) {
            dst[off + i] = Long.valueOf(n).byteValue();
            n = n >> 8;
        }
    }

    public static double getDouble(byte[] src, int off) {
        long n;
        n = src[off + 7];
        n &= 0xFF;
        n |= ((long) src[off + 6] << 8);
        n &= 0xFFFF;
        n |= ((long) src[off + 5] << 16);
        n &= 0xFFFFFF;
        n |= ((long) src[off + 4] << 24);
        n &= 0xFFFFFFFFL;
        n |= ((long) src[off + 3] << 32);
        n &= 0xFFFFFFFFFFL;
        n |= ((long) src[off + 2] << 40);
        n &= 0xFFFFFFFFFFFFL;
        n |= ((long) src[off + 1] << 48);
        n &= 0xFFFFFFFFFFFFFFL;
        n |= ((long) src[off] << 56);
        return Double.longBitsToDouble(n);
    }
}