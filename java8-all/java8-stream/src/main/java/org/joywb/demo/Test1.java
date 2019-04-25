package org.joywb.demo;

import org.joywb.food.Dish;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/4/23 8:02
 */


public class Test1 {

    private List<Dish> menu;

    private List<Integer> numbers;

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

    }
    @Test
    public void test1(){
        List<String> collect = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 流的扁平化
     */
    @Test
    public void test2(){
        String[] arrayOfWords = {"GoodBye","World"};
        Arrays.stream(arrayOfWords)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void test3(){
        List<String> words = Arrays.asList("Hello", "World");
        words.stream()
                .map(word->word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void test4(){
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(2, 3);
        List<int[]> collect = list1.stream()
                .flatMap(i -> list2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        for (int[] array:collect){
            for (int anArray : array) {
                System.out.print(anArray);
            }
            System.out.println();
        }
    }

    @Test
    public void test5(){
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(dish -> System.out.println(dish.getName()));
    }

    @Test
    public void test6(){
        HashSet<Integer> integerHashSet = new HashSet<>();
        for(Integer temp:numbers){
            if(temp%2==0){
                integerHashSet.add(temp);
            }
        }
        for(Integer temp:integerHashSet){
            System.out.println(temp);
        }

        numbers.stream()
                .filter(i->i%2==0)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void test7(){
        Integer sum = numbers.stream()
                .map(d -> 1)
                .reduce(0, Integer::sum);
        System.out.println(sum);
    }
}























