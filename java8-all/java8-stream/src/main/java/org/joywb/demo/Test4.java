package org.joywb.demo;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/4/25 8:46
 */


public class Test4 {

    /**
     * 生成1-100之间所有的勾股数组合
     */
    @Test
    public void test1(){
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(
                        a ->
                                IntStream.rangeClosed(a, 100)
                                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                        .mapToObj(b ->
                                                new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );
        pythagoreanTriples.limit(5).
                forEach(t-> System.out.println(t[0]+","+t[1]+","+t[2]));
    }

    @Test
    public void test2(){
        Stream<String> stringStream = Stream.of("Tea", "java8", "python", "go", "groovy", "scala", "C", "node js");
        stringStream.map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    public void test3(){
        int[] data = {2,3,4,5,6,7,8,9,23};
        int sum = Arrays.stream(data).sum();
        System.out.println(sum);
    }

    @Test
    public void test4(){
        long uniqueWords = 0;
        try(Stream<String> lines = Files.lines(Paths.get("H:\\java8\\java8-all\\pom.xml"),Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line->Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(uniqueWords);
    }


    @Test
    public void test5(){

        Stream.iterate(0,n->n+2)
                .limit(10)
                .forEach(System.out::println);

    }

    @Test
    public void test6(){
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }



}
