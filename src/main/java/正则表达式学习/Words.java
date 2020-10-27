package 正则表达式学习;

import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.regex.Pattern;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/27 11:08
 */
public class Words {

    public static void main(String[] args) {

    }

    @Test
    public void test01() {
        String regex = "zo*";
        String content1 = "z";
        String content2 = "zo";
        String content3 = "zooooo";
        System.out.println(Pattern.matches(regex, content1)); //true
        System.out.println(Pattern.matches(regex, content2)); //true
        System.out.println(Pattern.matches(regex, content3)); //true
    }

    @Test
    public void test02() {
        String regex = "zo+";
        String content1 = "z";
        String content2 = "zo";
        String content3 = "zooooo";
        System.out.println(Pattern.matches(regex, content1)); //false
        System.out.println(Pattern.matches(regex, content2)); //true
        System.out.println(Pattern.matches(regex, content3)); //true
    }

    @Test
    public void test03() {
        String regex = "zo?";
        String content1 = "z";
        String content2 = "zo";
        String content3 = "zooooo";
        System.out.println(Pattern.matches(regex, content1)); //false
        System.out.println(Pattern.matches(regex, content2)); //true
        System.out.println(Pattern.matches(regex, content3)); //true
    }

    @Test
    public void test04() {
        String regex = "^(1[0-9]{10})$";
        String content = "13968305745";
        System.out.println(Pattern.matches(regex, content));
    }

    @Test
    public void test05() {
        String regex = "[-w]*ick";
        String content = "Kickapoo";
        System.out.println(Pattern.matches(regex, content));
    }
}
