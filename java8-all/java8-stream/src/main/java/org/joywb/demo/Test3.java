package org.joywb.demo;

import org.joywb.sources.Trader;
import org.joywb.sources.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/4/24 14:28
 */

public class Test3 {

    private List<Transaction> transactions;

    @Before
    public void init() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(brian, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    /**
     * 找出2011年发生的所有交易，并按照交易额排序
     */
    @Test
    public void test1() {
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted((a, b) -> a.getValue() > b.getValue() ? 1 : 0)
                .forEach(System.out::println);
    }

    /**
     * 交易员都在哪些城市工作过
     */
    @Test
    public void test2(){
        transactions.stream()
                .map(d->d.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 找出所有在剑桥的交易员的名字，并排序
     */
    @Test
    public void test3(){
        transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(transaction -> transaction.getTrader().getName())
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * 找出所有在米兰工作的交易额
     */
    @Test
    public void test4(){
        transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }


    /**
     * 打印所有交易额
     */
    @Test
    public void test5(){
        transactions.stream()
                .map(Transaction::getValue)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * 找到最大的一笔交易额
     */
    @Test
    public void test6(){
        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        System.out.println(max.getAsInt());
    }

}
