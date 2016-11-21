package yang.com.tl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String translsteResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get user input word
        EditText queryWord = (EditText) findViewById(R.id.TL_from);
        try {
            translsteResult = new TL_unit().translate(String.valueOf(queryWord.getText()),"zh", "en");
        } catch (IOException e) {
        } catch (JSONException e) {
            e.printStackTrace();
            e.printStackTrace();
        }

        TextView reslutTextView = (TextView) findViewById(R.id.TL_got);
        reslutTextView.setText(translsteResult);

    }
}
