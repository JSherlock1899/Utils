package 遍历文件夹下的文件;

import org.junit.Test;

import java.io.File;
import java.util.*;


/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/14 9:24
 */
public class TraversalFile {

    @Test
    public void test7(){
        String path = "D:\\";
        Map<String, String> res = new HashMap<>();
        listPic(path, res);
        Iterator<Map.Entry<String, String>> iterator = res.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> f = iterator.next();
            System.out.println("文件名为：" + f.getKey() + "，路径为：" +  f.getValue());
        }
    }

    public void listPic(String path, Map<String, String> res) {
        File[] files = new File(path).listFiles(file -> file.isDirectory()
                || file.getName().endsWith(".jpg")
                || file.getName().endsWith(".png"));
        if (files == null || files.length == 0) return;
        for (File f : files) {
            if (f.isDirectory()) {
                listPic(path + "\\" + f.getName(), res);
            }
            if (f.isFile()) {
                res.put(f.getName(), f.getPath());
//                System.out.println("路径为：" + f.getPath() + "文件名为：" +  f.getName());
            }
        }
    }
}
