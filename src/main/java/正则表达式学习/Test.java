package 正则表达式学习;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/27 9:50
 */
public class Test {

    public static void main(String args[]){
//        String content = "1113354695";
//        String regex="^(1[\\d]{10}|(AC|\\d{2})\\d{8})$";
//        String pattern = "zo{3}";
//        String regex = "zoooo";

//        boolean isMatch = Pattern.matches(pattern, regex);
//        boolean isMatch = Pattern.matches(regex, content);
//        System.out.println(isMatch);



        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }
}
