package cn.peyton.plum.core.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.List;

/**
 * <h3>文件工具类 .util</h3>
 * <pre>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2022/3/30 14:10
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public final class FileUtils implements Serializable {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * 删除文件夹(包含当前文件夹)
     * @param folderPath 文件夹完整绝对路径  D:/aa
     */
    public static void delFolder(String folderPath) {
        try {
            // 删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            // 删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }


    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径 如：D:/aa
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                // 再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除单个文件
     * @param pathAndName   被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean delFile(String pathAndName) {
        boolean flag = false;
        File file = new File(pathAndName);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    /**
     * 判断文件名是否存在
     * @param pathName 路径(绝对路径如：c:\\_temp\\ 或  c:/_temp/ 必须以\\ 或/结束)
     * @param fileName 文件名(如：file.jsp)
     * @return 有存在返回 true 否则为false
     */
    public static boolean isFileNameExists(String pathName , String fileName){
        File file = new File(pathName+fileName);
        if(file.exists()) {return true;}
        return false;
    }

    /**
     * 创建文件 ,当存在相同的文件名时不做处理
     * @param pathName 要给出绝对路径和文件名
     * @return 返回true表示有创建文件,并且文件创建成功,否则就表示文件已经存在不做处理
     */
    public static boolean createNewFile(String pathName){
        File file = new File(pathName);
        boolean result = false;
        if (!file.exists()) {
            try {
                result = file.createNewFile();
            } catch (Exception e) {
                LogUtils.error(e.getMessage());
            }
        }
        return result;
    }
    /**
     * 创建文件目录 ,当存在相同的文件目录名时不做处理
     * @param pathDirectoryName 要给出绝对路径和目录名称
     * @return 返回true表示有创建文件目录,并且文件创建成功,否则就表示文件目录已经存在不做处理
     */
    public static boolean createDirectory(String pathDirectoryName){
        //如果文件夹不存在则创建
        File file =new File(pathDirectoryName);
        boolean result = false;
        if (!file.exists() && !file.isDirectory()) {
            result = file.mkdirs();
        }
        return result;
    }
    /**
     * 文件或文件目录重命名
     * @param oldPathName 要给出绝对路径和目录名称
     * @param newName 要改成新的文件名或文件目录
     * @return 返回true表示有创建文件目录,并且文件创建成功,否则就表示文件目录已经存在不做处理
     */
    public static boolean rename(String oldPathName , String newName) {
        boolean result = false;
        File oldFile = new File(oldPathName);
        String path = oldFile.getPath();
        String newPathName = path.substring(0, path.lastIndexOf("\\")+1) + newName;
        File newFile = new File(newPathName);
        if (!newFile.exists()) {
            oldFile.renameTo(newFile);
            result = true;
        }
        return result;
    }
    /**
     * 查找文件目录下的所有文件
     * @param resultLists 查找返回文件的集合
     * @param path 绝对路径
     * @param isSearchAll 是否查找全部,true为查找当前文件目录所以子目录,false为只查找当前目录下的文件 ,子目录不做查找;
     */
    public static void  searchAllFile(List<File> resultLists , String path , boolean isSearchAll) {
        File file = new File(path);
        File[] array= file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                resultLists.add(array[i]);
            }else if (array[i].isDirectory()) {
                if (isSearchAll) {searchAllFile(resultLists , array[i].getPath(),isSearchAll);}
            }
        }
    }

    /**
     * 根据关键字查找目录下的所有文件
     * @param resultLists 查找返回文件的集合
     * @param folder 要查找的文件对象
     * @param keyWord 关键字
     * @param isSearchAll  是否查找全部,true为查找当前文件目录所以子目录,false为只查找当前目录下的文件 ,子目录不做查找;
     */
    public static void searchKeywordsFile(List<File> resultLists , File folder, final String keyWord, boolean isSearchAll) {
        // 递归查找包含关键字的文件
        // 运用内部匿名类获得文件
        File[] subFolders = folder.listFiles(new FileFilter() {
            // 实现FileFilter类的accept方法
            @Override
            public boolean accept(File pathname) {
                // 目录或文件包含关键字
                if (pathname.isDirectory() || (pathname.isFile() && pathname.getName()
                        .toLowerCase().contains(keyWord.toLowerCase()))){

                    return true;
                }
                return false;
            }
        });
        // 循环显示文件夹或文件
        for (int i = 0; i < subFolders.length; i++) {
            // 如果是文件则将文件添加到结果列表中
            if (subFolders[i].isFile()) {
                resultLists.add(subFolders[i]);
                // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
            } else {
                if (isSearchAll) {
                    searchKeywordsFile(resultLists , subFolders[i], keyWord,isSearchAll);
                }
            }
        }
    }
    /**
     * <h3>改变路径</h3>
     * <pre>
     *     路径 如: c:/tools 变换后 c:/tools/
     *          如: c:/tools/ 就不作改变直接返回 c:/tools/
     * </pre>
     * @param path 路径
     * @return
     */
    public static String changePath(String path) {
        String t = path.substring(path.length() - 1);
        int l = path.lastIndexOf("/");
        System.out.println(l);
        return ("/".equals(t) || "\\".equals(t)) ? (path) : (path + (l > -1 ? "/":"\\"));
    }

}
