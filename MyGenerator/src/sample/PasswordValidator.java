package sample;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PasswordValidator {
    private String password;
    PasswordValidator(String password){
        this.password = password;
    }
    public final String getInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        if (isSizeN()) stringBuilder.append("- has 8 or more symbols\n");
        else stringBuilder.append("- has less than 8 symbols\n");
        if (isLowCase()) stringBuilder.append("- has small characters\n");
        else stringBuilder.append("- doesn't have small characters\n");
        if (isUpperCase()) stringBuilder.append("- has big characters\n");
        else stringBuilder.append("- doesn't have big characters\n");
        if(isNumbs()) stringBuilder.append("- has numbers\n");
        else stringBuilder.append("- doesn't have numbers\n");
        if (isSymb()) stringBuilder.append("- has specific symbols such as ! - @ # $ % & * _ + = ? ^\n");
        else stringBuilder.append("- doesn't have specific characters such as ! - @ # $ % & * _ + = ? ^\n");
        if(isCommon()) stringBuilder.append("- is in a list of the most common passwords\n");
        else stringBuilder.append("isn't in a list of the most common passwords\n");
        return stringBuilder.toString();
    }

    public boolean isStrong() {
        return isLowCase() && isUpperCase() && isSizeN() && isSymb() && !isCommon() && isNumbs();
    }

    public boolean isLowCase() {
        String regex = ".*[a-z]+.*";
        String regexRus = ".*[а-я]+.*";
        return password.matches(regex) || password.matches(regexRus);
    }

    public boolean isSymb() {
        String regex = ".*[-|!|@|#|$|%|&|*|_|+|=|?|^]+.*";
        return password.matches(regex);
    }

    public boolean isUpperCase() {
        String regex = ".*[A-Z]+.*";
        String regexRus = ".*[А-Я]+.*";
        return password.matches(regex) || password.matches(regexRus);
    }

    public boolean isSizeN() {
        String regex = ".{8,}";
        return password.matches(regex);
    }
    public boolean isNumbs(){
        String regex = ".*[0-9]+.*";
        return password.matches(regex);
    }
    public final boolean isCommon(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/anaantonuk/Desktop/passwords.txt"));
            String line = bufferedReader.readLine();
            while(line != null){
                if(this.password.equals(line)) return true;
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
