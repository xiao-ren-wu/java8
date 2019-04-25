package org.joywb.demo;

import java.util.Arrays;
import java.util.List;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/4/24 9:14
 */


public class Test2 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 2, 3, 4, 2, 3, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }
}
