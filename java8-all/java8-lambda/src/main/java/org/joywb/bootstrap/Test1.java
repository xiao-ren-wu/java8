package org.joywb.bootstrap;

import org.joywb.fruit.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/4/22 11:27
 */


public class Test1 {
    public static void main(String[] args) {
        List<Integer> weights = Arrays.asList(7, 4, 3, 10);
        List<Apple> apples = map(weights, Apple::new);
    }
    public static List<Apple> map(List<Integer> list, Function<Integer,Apple> f){
        List<Apple> result = new ArrayList<>();
        for(Integer e:list){
            result.add(f.apply(e));
        }
        return result;
    }
}
