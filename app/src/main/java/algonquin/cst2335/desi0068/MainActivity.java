package algonquin.cst2335.desi0068;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity","In onCreate() - Loading Widgets");
        Log.d(TAG,"Message");
        Log.w("onStart()", "The application is now visible on screen");
        Log.w("onResume()", "The application is now responding to user input");
        Log.w("onPause()", "The application no longer responds to user input");
        Log.w("onStop()", "The application is no longer visible");
        Log.w("onDestroy()", "Any memory used by the application is freed");

    }
}