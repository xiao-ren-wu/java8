package org.joywb.framework;

import java.util.Spliterator;

/**
 * @author YuWenbo
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2019/5/2 15:59
 */


public class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }
Spliterator
    public WordCounter accumulate(Character c){
        if(Character.isWhitespace(c)){
            return lastSpace?
                    this:
                    new WordCounter(counter+1,false);
        }else{
            return lastSpace?
                    new WordCounter(counter+1,false):
                    this;
        }
    }
    public WordCounter combine(WordCounter wordCounter){
        return new WordCounter(counter+wordCounter.counter,wordCounter.lastSpace);
    }
    public int getCounter(){
        return counter;
    }
}
