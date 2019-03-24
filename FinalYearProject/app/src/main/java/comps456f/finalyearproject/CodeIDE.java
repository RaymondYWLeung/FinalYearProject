package comps456f.finalyearproject;


import android.content.res.Resources;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeIDE {

    public Editable editable;
    public Resources resources;
    public int textLength;

    String red_key_word = "\\b(public)\\b|\\b(static)\\b|(\\[\\])|\\b(new)\\|\\b(for)\\b|\\b(if)\\b" +
            "|\\b(else)\\b|\\b(else if)\\b|\\b(=)\\b|\\b(:)\\b" ;
    String blue_key_word = "\\b(String)\\b|\\b(System)\\b|\\b(print)\\b|\\b(println)\\b" ;
    String double_quote = "/u0022";
    String yellow_key_word = "^\".*\"$";

    //Before Text Changed

    CodeIDE(Editable editable, Resources resources){
        this.editable = editable;
        this.resources = resources;
        setTextColor();
    }

    public Spannable setTextColor(){
        Spannable textSpan = editable;
        changeTextColor(textSpan);
        return textSpan;
    }

    public void applySpan(String word, int aColor , Spannable textSpan){
        final Pattern pattern = Pattern.compile(word);
        final Matcher matcher = pattern.matcher(textSpan);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            textSpan.setSpan(new ForegroundColorSpan(aColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public void changeTextColor(Spannable textSpan){
        applySpan(red_key_word,resources.getColor(R.color.ide_red),textSpan);
        applySpan(blue_key_word,resources.getColor(R.color.ide_blue),textSpan);
        applySpan(yellow_key_word,resources.getColor(R.color.ide_yellow),textSpan);
    }


    //After Text Changed


    CodeIDE(Editable editable, Resources resources,int textLength){
        this.editable = editable;
        this.resources = resources;
        this.textLength = textLength;
        clearSpan();
        changeTextColor();
    }

    public void clearSpan(){

        ForegroundColorSpan[] spannable = editable.getSpans(0, textLength - 1, ForegroundColorSpan.class);
        if (spannable != null && spannable.length > 0) {
            for (int i = 0; i < spannable.length; i++) {
                editable.removeSpan(spannable[i]);
            }
        }
    }

    public void changeTextColor(){
        applySpan(red_key_word,resources.getColor(R.color.ide_red));
        applySpan(blue_key_word,resources.getColor(R.color.ide_blue));
        applySpan(yellow_key_word,resources.getColor(R.color.ide_yellow));
    }

    public void applySpan(String word, int aColor){
        Spannable textSpan = editable;
        final Pattern pattern = Pattern.compile(word);
        final Matcher matcher = pattern.matcher(textSpan);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            textSpan.setSpan(new ForegroundColorSpan(aColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
