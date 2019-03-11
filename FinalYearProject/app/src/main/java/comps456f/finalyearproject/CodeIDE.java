package comps456f.finalyearproject;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeIDE {
   /* public String changeColor(String aCode){
        String result = findKeyWord(0,aCode);

        SpannableStringBuilder ssb = new SpannableStringBuilder(aCode);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                context.getResources()
                        // Specify your color
                        .getColor(R.color.your_font_color));
        realPrice.setSpan(colorSpan,
                0, // Start index of the single word
                1, // End index of the single word
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        yourEditText.setText(ssb);

        return result;
    }*/
    public String findKeyWord(int start, String aStr){
        String keyWord = "public";
        int i = aStr.indexOf(keyWord);
        String s = aStr.substring(i, i + keyWord.length() + 1);
        return s;
    }

    public void changeColor(Editable editable, String word, int aColor){
        MyApplication myapp = new MyApplication();
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
