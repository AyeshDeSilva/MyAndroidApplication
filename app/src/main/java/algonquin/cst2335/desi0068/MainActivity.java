package algonquin.cst2335.desi0068;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("onStart()", "The application is now visible on screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("onResume()", "The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("onPause()", "The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("onStop()", "The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("onDestroy()", "Any memory used by the application is freed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In onCreate() - Loading Widgets");

        Button loginButton = findViewById(R.id.login_button);
        EditText emailEditText = findViewById(R.id.editEmailText);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(emailAddress);


        loginButton.setOnClickListener(clk -> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", emailEditText.getText().toString());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailAddress);
            editor.apply();


            startActivity(nextPage);
        });


    }


}