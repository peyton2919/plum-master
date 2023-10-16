package cn.peyton.plum.core.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>序列化工具类</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:02
 * @author:<a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public final class SerializableUtils implements Serializable {
	/**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            LogUtils.info(object + "对象为空");
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        } finally {
            close(oos);
            close(baos);
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        } finally {
            close(bais);
            close(ois);
        }
        return null;
    }

    /**
     * 序列化 list 集合
     *
     * @param list
     * @return
     */
    public static byte[] serializeList(List<?> list) {

        if (list==null||list.size()==0) {
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            for (Object obj : list) {
                oos.writeObject(obj);
            }
            bytes = baos.toByteArray();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        } finally {
            close(oos);
            close(baos);
        }
        return bytes;
    }

    /**
     * 反序列化 list 集合
     */
    public static List<?> unserializeList(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        List<Object> list = new ArrayList<Object>();
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            while (bais.available() > 0) {
                Object obj = (Object) ois.readObject();
                if (obj == null) {
                    break;
                }
                list.add(obj);
            }
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        } finally {
            close(bais);
            close(ois);
        }
        return list;
    }

    /**
     * 关闭io流对象
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LogUtils.error(e.getMessage());
            }
        }
    }

}
