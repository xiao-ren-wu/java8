package org.joywb.collect;

import org.joywb.food.Dish;
import org.joywb.sources.Trader;
import org.joywb.sources.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/4/26 10:24
 */


public class Test1 {
    public List<Dish> menu;

    public List<Integer> numbers;

    public List<String> words;

    public List<Transaction> transactions;

    public enum CaloricLevel {
        DIET,
        NORMAL,
        FAT
    }

    @Before
    public void before() {
        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.META),
                new Dish("beef", false, 700, Dish.Type.META),
                new Dish("chicken", false, 400, Dish.Type.META),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
        numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 213, 4, 34, 65, 45, 765, 67, 5678, 325, 23);
        words = Arrays.asList("go", "python", "node js", "java", "C", "Groovy");

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

    @Test
    public void test1() {
        String collect = words.stream()
                .collect(joining(","));
        System.out.println(collect);
    }

    @Test
    public void test2() {
        int totalCalories = menu.stream().map(Dish::getCalories).reduce(0, (a, b) -> a + b);
        System.out.println(totalCalories);
    }

    @Test
    public void test3() {
        Map<CaloricLevel, List<Dish>> collect = menu.stream().collect(
                groupingBy(this::getCaloricLevel)
        );
    }

    /**
     * 多级分组
     */
    @Test
    public void test4() {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect = menu.stream()
                .collect(
                        groupingBy(
                                Dish::getType, groupingBy(
                                        this::getCaloricLevel
                                )
                        )
                );
    }

    private CaloricLevel getCaloricLevel(Dish dish) {
        if (dish.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (dish.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }
    }

    @Test
    public void test5() {
        Map<Dish.Type, Dish> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)), Optional::get
                        )));
    }

    /**
     * 找到流中最大值
     */
    @Test
    public void test6() {
        Optional<Dish> mostCalorieDish = menu.stream().max(comparingInt(Dish::getCalories));
        System.out.println(mostCalorieDish.get().getName());
    }

    /**
     * 计算菜单中菜品的总热量
     */
    @Test
    public void test7() {
        Integer sum = menu.stream()
                .collect(summingInt(Dish::getCalories));
        System.out.println(sum);
    }

    @Test
    public void test8() {
        String joinWords = menu.stream()
                .map(Dish::getName)
                .collect(joining("::"));
        System.out.println(joinWords);
    }


    /**
     * 将菜单分组，并取出每组热量最高的菜
     */
    @Test
    public void test9() {
        Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
                .collect(
                        groupingBy(
                                Dish::getType,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get)));
    }


    public void test10(){
        Map<Boolean, List<Dish>> partitionMenu = menu.stream()
                .collect(
                        partitioningBy(Dish::isVegetarian)
                );
    }

}

























