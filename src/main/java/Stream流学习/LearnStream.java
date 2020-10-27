package Stream流学习;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/22 16:08
 */

public class LearnStream {


    public static final List<Integer> LIST = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static void main(String[] args) {
        //limit 用于获取指定数量的流
        LIST.stream().limit(4).forEach(System.out::println);
        System.out.println("=====================================");


        //filter 用于通过设置的条件过滤出元素
        LIST.stream().filter(x -> x > 1).forEach(System.out::println);
        System.out.println("=====================================");


        //sorted 用于对流进行排序
        new Random().ints().limit(10).sorted().forEach(System.out::println);
        System.out.println("=====================================");


        //Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty() && !string.contains("a")).collect(Collectors.joining(" "));
        System.out.println("合并字符串: " + mergedString);
        System.out.println("=====================================");


        //map 方法用于映射每个元素到对应的结果
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("=====================================");

        //统计
        IntSummaryStatistics stats = LIST.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        System.out.println("=====================================");

        //排序
        LIST.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
        System.out.println("=====================================");

        //判断的条件里，任意一个元素成功，返回true
        System.out.println(LIST.stream().anyMatch(x -> x == 1));
    }
}
