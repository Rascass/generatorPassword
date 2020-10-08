package sample;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Generator {
    private static final int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final char[] rusLowCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
    private static final char[] rusUpperCase = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    private static final char[] lowCase = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] upperCase = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] symbols = {'!', '@', '#', '$', '%', '&', '*', '_', '+', '-', '=', '?', '^'};
    private static SecureRandom secureRandom = new SecureRandom();
    private static int size = 0;
    private static String language = "rus";
    private static boolean isLowCase = true;
    private static boolean isUpperCase = true;
    private static boolean isSymbs = true;
    private static boolean isNumbs = true;
    protected static double sizeChU = 0;
    protected static double sizeChL = 0;
    protected static double sizeN = 0;
    protected static double sizeS = 0;
    Generator(int size, String language){
        if(size < 1){
            throw new IllegalArgumentException();
        }
        else this.size = size;
        this.language = language;
    }

    public void setIsLowCase(boolean isLowCase) {
        Generator.isLowCase = isLowCase;
    }

    public void setIsUpperCase(boolean isUpperCase) {
        Generator.isUpperCase = isUpperCase;
    }

    public void setIsNumbs(boolean isNumbs) {
        Generator.isNumbs = isNumbs;
    }

    public void setIsSymbs(boolean isSymbs) {
        Generator.isSymbs = isSymbs;
    }
    public boolean getIsLowCase() {
        return Generator.isLowCase;
    }

    public boolean getIsUpperCase() {
        return Generator.isUpperCase;
    }

    public boolean getIsNumbs() {
        return Generator.isNumbs;
    }

    public boolean getIsSymbs() {
        return Generator.isSymbs;
    }
    public final String generateNumbs(int size)
    {
        if (size < 1) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < size; i++){
            stringBuilder.append(numbers[secureRandom.nextInt(numbers.length)]);
        }
        return stringBuilder.toString();
    }
    public final String generateCharUp(int size)
    {
        if (size < 1) return "";
        StringBuilder stringBuilder = new StringBuilder();
        if(language == "eng")
        {
            for (int i = 0; i < size; i++) stringBuilder.append(upperCase[secureRandom.nextInt(upperCase.length)]);
        }
        else {
            for (int i = 0; i < size; i++) stringBuilder.append(rusUpperCase[secureRandom.nextInt(rusUpperCase.length)]);
        }
        return stringBuilder.toString();
    }
    public final String generateCharLow(int size)
    {
        if (size < 1) return "";
        StringBuilder stringBuilder = new StringBuilder();
        if(language == "eng")
        {
            for (int i = 0; i < size; i++) stringBuilder.append(lowCase[secureRandom.nextInt(lowCase.length)]);
        }
        else{
            for (int i = 0; i < size; i++) stringBuilder.append(rusLowCase[secureRandom.nextInt(rusLowCase.length)]);
        }
        return stringBuilder.toString();
    }
    public final String generateSymbols(int size){
        if (size < 1) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < size; i++){
            stringBuilder.append(symbols[secureRandom.nextInt(symbols.length)]);
        }
        return stringBuilder.toString();
    }
    public static void sizes(){
        ArrayList<Integer> sizes = new ArrayList<>();
        sizeChL = isUpperCase ? 0.25 * Util.boolToInt(isLowCase) : 0.5 * Util.boolToInt(isLowCase);
        sizeChU = sizeChL == 0.5 ? 0 : sizeChL == 0.25 ? 0.25 : 0.5 * Util.boolToInt(isUpperCase);
        sizeN = sizeChL + sizeChU == 0 ? 0.8 * Util.boolToInt(isNumbs) : 0.4 * Util.boolToInt(isNumbs);
        sizeS = 1.0 - (sizeN + sizeChL + sizeChU);
        if(!isSymbs){
            if(sizeChL != 0) sizeChL += sizeS;
            else if(sizeChU != 0) sizeChU += sizeS;
            else if(sizeN != 0) sizeN += sizeS;
        }
        sizeS = Util.boolToInt(isSymbs) * sizeS;
        if(sizeS > 0.1 && !isNumbs && (isLowCase && isUpperCase)){
            sizeChL = sizeChU = 0.45;
            sizeS = 0.1;
        }
        else if(sizeS > 0.1 && !isNumbs && isLowCase && !isUpperCase){
            sizeS = 0.1;
            sizeChU = 0;
            sizeChL = 0.9;
        }
        else if(sizeS > 0.1 && !isNumbs && !isLowCase && isUpperCase){
            sizeS = 0.1;
            sizeChU = 0.9;
            sizeChL = 0;
        }
    }
    public String checkSize(String str){
        if(str.length() == size + 1){
            str = str.substring(0, str.length() - 2);
        }
        if (str.length() + 1 == size){
            if(isSymbs) str = str + symbols[secureRandom.nextInt(symbols.length - 1)];
            else str = str + str.charAt(secureRandom.nextInt(str.length() - 1));
        }
        return str;
    }
    public String generatePassword()
    {
        sizes();
        StringBuilder stringBuilder = new StringBuilder();
        if(sizeChL + sizeChU + sizeN + sizeS == 1)
        {
            stringBuilder.append(generateNumbs((int)Math.round(size * sizeN)));
            stringBuilder.append(generateCharLow((int)Math.round(size * sizeChL)));
            stringBuilder.append(generateCharUp((int)Math.round(size * sizeChU)));
            stringBuilder.append(generateSymbols((int)Math.round(size * sizeS)));
            String pas = Util.shuffle(stringBuilder.toString());
            return checkSize(pas);
        }
        else return null;
    }

}

