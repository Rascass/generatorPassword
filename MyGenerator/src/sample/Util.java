package sample;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Util {
    public static final String shuffle(String str){
        List<String> array = Arrays.asList(str.split(""));
        Collections.shuffle(array);
        StringBuilder stringBuilder = new StringBuilder();
        for(String item: array){
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }
    public static final int boolToInt(boolean b){
        return b ? 1: 0;
    }
}
