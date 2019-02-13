package comps456f.finalyearproject;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class CodeIDE {
    public String changeColor(String aCode){
        String result = findKeyWord(0,aCode);

        /*SpannableStringBuilder ssb = new SpannableStringBuilder(aCode);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                context.getResources()
                        // Specify your color
                        .getColor(R.color.your_font_color));
        realPrice.setSpan(colorSpan,
                0, // Start index of the single word
                1, // End index of the single word
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        yourEditText.setText(ssb);*/

        return result;
    }
    public String findKeyWord(int start, String aStr){
        String keyWord = "public";
        int i = aStr.indexOf(keyWord);
        String s = aStr.substring(i, i + keyWord.length() + 1);
        return s;
    }
}
