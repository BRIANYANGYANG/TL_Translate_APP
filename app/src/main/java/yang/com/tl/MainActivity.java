package yang.com.tl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import yang.com.tl.TL_utils.TransApi;

public class MainActivity extends AppCompatActivity {

    private String translsteResult;
    private static final String APP_ID = "20160807000026394";
    private static final String SECURITY_KEY = "coA5YQ9jQDdsYs3nX4Mx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get user input word
        final EditText queryWord = (EditText) findViewById(R.id.TL_from);
        Button confirmButton = (Button) findViewById(R.id.TL_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //use api to get result
                TransApi api = new TransApi(APP_ID, SECURITY_KEY);
                translsteResult = api.getTransResult(String.valueOf(queryWord.getText()), "auto", "en");


                Toast.makeText(getApplicationContext(), "已翻译", Toast.LENGTH_SHORT);
            }
        });


        TextView reslutTextView = (TextView) findViewById(R.id.TL_got);
        reslutTextView.setText(translsteResult);

    }
}
