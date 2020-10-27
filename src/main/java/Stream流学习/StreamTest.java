package Stream流学习;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: jiang yonghui
 * @description:
 * @date: 2020/10/22 16:48
 */
// 1. 找出2011年发生的所有交易,并按交易额排序(从低到高)
// 2. 交易员都在哪些不同的城市工作过
// 3. 查找所有来自剑桥的交易员,并按姓名排序
// 4. 返回所有交易员的姓名字符串,按字母顺序排序
// 5. 有没有交易员是在米兰工作的?
// 6. 所有交易中,最高的交易额是多少
// 7. 找到交易额最小的交易
public class StreamTest {
    List<Transaction> transactions = null;
    @Before
    public void before() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    @Test
    public void demo1() {
        //1. 找出2011年发生的所有交易,并按交易额排序(从低到高)
        transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted((e1, e2) -> -Integer.compare(e1.getValue(), e2.getValue()))
                .forEach(System.out::println);

        System.out.println("------------------------------");


        System.out.println("---------------------------------");

        // 3. 查找所有来自剑桥的交易员,并按姓名排序
        Stream<Transaction> stream3 = transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).sorted(Comparator.comparing(e1 -> e1.getTrader().getName()));
        stream3.forEach(System.out::println);
        System.out.println("---------------------------------");

        // 4. 返回所有交易员的姓名字符串,按字母顺序排序
        Stream<String> stream4 = transactions.stream().map(e -> e.getTrader().getName()).sorted((e1, e2) -> e1.compareTo(e2));
        stream4.forEach(System.out::println);
        System.out.println("---------------------------------");

        // 5. 有没有交易员是在米兰工作的?
        Boolean bool1 = transactions.stream().anyMatch(e -> e.getTrader().getCity().equals("Milan"));
        System.out.println("有没有交易员是在米兰工作的 : " + bool1);
        System.out.println("---------------------------------");

        // 6. 打印生活在剑桥的交易员的所有交易额
        transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).forEach(System.out::println);
        System.out.println("---------------------------------");

        // 7. 所有交易中,最高的交易额是多少
        Optional<Integer> op = transactions.stream().map(Transaction::getValue).max(Integer::compareTo);
        System.out.println("所有交易中,最高的交易额是多少: " + op.get());
        System.out.println("---------------------------------");

        // 8. 找到交易额最小的交易
        Optional<Transaction> op1 = transactions.stream().min(Comparator.comparingInt(e -> e.getValue()));
        System.out.println("找到交易额最小的交易: " + op1);
    }

    // 2. 交易员都在哪些不同的城市工作过
    @Test
    public void demo2() {
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    // 3. 查找所有来自剑桥的交易员,并按姓名排序
    @Test
    public void demo3() {
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(e -> e.getTrader().getName()))
                .forEach(System.out::println);
    }
}

// 商人
@Data
class Trader {
    private String name;
    private String city;


    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }
    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

// 交易
@Data
class Transaction {
    private Trader trader;
    private int year;
    private int value;

    public Transaction() {
    }

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

}
