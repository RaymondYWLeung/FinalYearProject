package comps456f.finalyearproject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveJava extends Application {
    Context context = MyApplication.getAppContext();

    public String saveAsJava(String code) {

        String filename = "temp.java";
        File path = context.getFilesDir();
        File file = new File(path,filename);

        try{
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(code.getBytes());
            stream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "Saved at : " + path + "\\" + filename;
    }

}
