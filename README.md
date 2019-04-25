> 该仓库是阅读《java8实战》做的读书笔记。
>
> 大致会介绍java8的一些新特性和api。
>
> 更详细的java8新特性介绍请[戳](https://github.com/winterbe/java8-tutorial)

![java8](https://github.com/xiao-ren-wu/java8/blob/master/java8-all/src/site/image/java8.jpg)

## Lambda

可以简介的表示匿名函数的一种形式，没有名称，但有参数列表，返回类型，也可以抛出异常列表。

### **自定义函数式接口**

1. 接口中只能有一个抽象方法，可以有多个`default`方法。
2. 在接口上注释`@FunctionalInterface`该注解和`@Override`类似，只是一种规范，可以不写，但是如果写上该注解，但是接口的定义不符合规范会在编译的时候报错。

eg：

~~~java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
~~~

### 常见的函数式接口

1. Predicate

   接收泛型，返回boolean

~~~java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
~~~

2. Consumer

   接收泛型，返回void

~~~java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
~~~

3. Function

   接收泛型T返回泛型R

~~~java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
~~~

4.  Supplier

   没有入参返回泛型T

~~~java
@FunctionalInterface
public interface Supplier<T>{
    T get();
}
~~~

5. 针对原始数据类型

### 方法引用

调用特定方法的lambda的一种快捷写法；一种语法糖。

如果一个Lambda代表的只是“直接调用某个方法”，可以直接通过方法名进行调用。

**调用方式：**目标引用放在分隔符::前，方法放在后面

eg:

`System.out::println`

#### 构造函数引用

对于现有构造函数，可以使用名称和关键字`new`来创建他的一个引用`ClassName::new`他的功能与指向静态引用类似。

eg:

~~~java
class Apple{
    public Apple(){}
    public Apple(Integer integer) {}
}
Supplier<Apple> s = Apple::new;
Function<Integer,Apple> f = Apple::new;
~~~

## Stream

流出现的目的是为了计算，集合出现的目的是为了存储。

eg: 有如下场景：有一串数字，要求不重复的输出该串数字中的偶数。

`List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 4, 34, 65, 45, 765, 67, 325, 23);`

1. 如果是我们自己写，首先，找到偶数，然后存储到Set中，然后遍历Set进行输出。

~~~java
HashSet<Integer> integerHashSet = new HashSet<>();
for(Integer temp:numbers){
    if(temp%2==0){
        integerHashSet.add(temp);
    }
}
for(Integer temp:integerHashSet){
    System.out.println(temp);
}
~~~

2. 使用流计算后

~~~java
numbers.stream()
        .filter(i->i%2==0)
        .distinct()
        .forEach(System.out::println);
~~~

相比之下可以看出使用stream后，大大简化了开发，并且最重要的是之前我们使用了两次for循环，但是使用流之后，开发者不在编写循环语句，而是将循环语句交给了stream内部完成。这样做的好处就是开发者不用关心内部是怎么实现循环遍历的，大大简化了开发难度和程序的可读性。

#### 常见的流操作

| 方法签名 |            说明            |
| :--: | :------------------------: |
| `Stream<T> limit(long n);` | 返回前n个元素的流 |
| `Stream<T> skip(long n);` | 返回一个跳过前n个元素的流 |
| `<R> Stream<R> map(Function<? super T, ? extends R> mapper)` | 接收一个函数作为参数，并映射成一个新的元素 |
| `<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);` | 将所有流“扁平化”成一个流 |
| `boolean allMatch(Predicate<? super T> predicate);` | 检查谓词是否匹配所有元素 |
| `boolean anyMatch(Predicate<? super T> predicate);` | 检查谓词是否至少匹配一个元素 |
| `boolean noneMatch(Predicate<? super T> predicate);` | 流中内容是否没有任何一个元素与给定的相匹配 |
| `Optional<T> findFirst();` | 返回流中第一个元素 |
| `Optional<T> findAny();` | 返回流中任意一个元素 |

findFirst的约束比findAny要多，执行时间相对长些。

> **Optional<T>**
>
> 是一个容器类，代表一个值存在或者不存在。他的出现可以避免空指针异常。
>
> - isPresent() Optional包含值得时候返回true,否则false
> - ifPresent(Consumer<T> block)  Optional 值存在时执行给定的代码
> - T get() Optional 存在值时返回对应的值，否则抛出NoSuchElement异常
> - T orElse(T other) 值存在时返回值，否则返回默认值。

#### 归约

**reduce**一个可以提供迭代的函数

reduce接受两个参数：

- 一个初始值
- 一个`BinaryOperator<T>`来将元素结合起来产生一个新值

除此之外，还提供了一个重载方法

不接受初始值。但是会返回一个Optional对象。（返回Optional防止计算的值不存在返回null）

eg：

计算给定List的和。

` List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);`

- 传统for-each

~~~java
int count = 0;
for(Integer t:numbers){
      count+=t;
}
~~~

- Lambda

~~~java
Integer count = numbers.stream()
                       .reduce(0, (a, b) -> a + b);
~~~

更多例子请[戳]()

##### 数值流

上面的例子中，在计算的时候每次迭代，元素都要进行“拆箱”然后进行计算，比较浪费时间，java提供了一种比较优雅的流**数值流**专门用来操作基本数据类型。

1. 映射到数值流常用的方法：mapToInt、mapToDouble和mapToLong。

2. 响应的数值流转换成对象流的方法：boxed。

##### 构建流

1. 由值创建流，和之前创建的方式一样。

   ~~~java
   Stream<String> stringStream = Stream.of(python", "groovy", "scala");
   ~~~

2. 由数组创建流

   ~~~java
   int numbers[] = {3,2,5,8,4,7,6,9};
   int sum = Arrays.stream(numbers);
   ~~~

3. 由文件生成流

   Java中处理文件等I/O操作NIO，

   ~~~java
   try(Stream<String> lines = Files.lines(Paths.get("filepath"),Charset.defaultCharset())) {
     	...
   } catch (IOException e) {
       e.printStackTrace();
   }
   ~~~

4. 由函数生成流：创建无限流

   Stream AP提供了两个静态方法来从函数生成流，`Stream.iterate`&`Stream.generate`

   生成的流会通过传入的函数无穷无尽的计算下去，应该配合使用`limit`来进行限制。

   1. iterate

      iterate接受一个初始值，还有一个一次应用在每个产生新值的Lambda

      eg: 

      ~~~
      Stream.iterate(0,n->n+2)
              .limit(10)
              .forEach(System.out::println);
      ~~~

   2. generate

      generate接受一个产生新值的Lambda

      eg：

      ~~~java
      Stream.generate(Math::random)
              .limit(5)
              .forEach(System.out::println);
      ~~~

   







































