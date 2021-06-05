package algonquin.cst2335.desi0068;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView text = findViewById(R.id.textView);
        Button btn = findViewById(R.id.callbutton);
        EditText phoneNumber = findViewById(R.id.editTextPhone);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        text.setText("Welcome back " + emailAddress);

        btn.setOnClickListener( clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel: " + phoneNumber));
            startActivity(call);
        });
    }
}