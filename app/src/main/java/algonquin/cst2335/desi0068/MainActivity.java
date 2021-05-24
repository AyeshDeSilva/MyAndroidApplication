package algonquin.cst2335.desi0068;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //load object into java
        TextView myText = findViewById(R.id.textview);
        Button btn = findViewById(R.id.my_button);
        EditText myEdit = findViewById(R.id.my_edit_text);
        CheckBox cb1 = findViewById(R.id.checkbox1);
        CheckBox cb2 = findViewById(R.id.checkbox2);
        Switch switch1 = findViewById(R.id.switch1);
        Switch switch2 = findViewById(R.id.switch2);
        RadioButton rb1 = findViewById(R.id.radio_button1);
        RadioButton rb2 = findViewById(R.id.radio_button2);
        ImageView myImage = findViewById(R.id.logo_algonquin);
        ImageButton imgbtn = findViewById(R.id.myImageButton);


        String editString = myEdit.getText().toString();
        btn.setOnClickListener(vw -> myText.setText("Your edit text has: " + editString));

        cb1.setOnCheckedChangeListener((cbox, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the " + cb1.getText() + " and it is now: " + isChecked;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        cb2.setOnCheckedChangeListener((cbox, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the " + cb2.getText() + " and it is now: " + isChecked;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        switch1.setOnCheckedChangeListener((sw, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the " + switch1.getText() + " and it is now: " + isChecked;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        switch2.setOnCheckedChangeListener((sw, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the " + switch2.getText() + " and it is now: " + isChecked;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        rb1.setOnCheckedChangeListener((rbtn, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the " + rb1.getText() + " and it is now: " + isChecked;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        rb2.setOnCheckedChangeListener((rbtn, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the " + rb2.getText() + " and it is now: " + isChecked;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        imgbtn.setOnClickListener(rbtn -> {
            Context context = getApplicationContext();
            CharSequence text = "The width = " + imgbtn.getWidth() + " and height = " + imgbtn.getHeight();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
    }
}


