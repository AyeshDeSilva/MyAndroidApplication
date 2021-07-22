package algonquin.cst2335.desi0068;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    /**
     * This string represents the address of the server we will connect to
     **/

    private String stringURL;
    Button forecastBtn;
    EditText cityText;
    TextView currentView, maxView, minView, descView, humidView;

    Bitmap image = null;
    String current = null;
    String min = null;
    String max = null;
    String humidity = null;
    String description = null;
    String iconName = null;
    Toolbar myToolBar;
    ImageView iv;

    float newSize = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastBtn = findViewById(R.id.forecastBtn);
        cityText = findViewById(R.id.cityTextField);

        myToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);

        forecastBtn.setOnClickListener((click) -> {
            String cityName = cityText.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting Forecast")
                    .setMessage("We're calling people in " + cityName + " to look outside their windows and tell us what's the weather like over there")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

            // Gets the menu from your toolbar
            myToolBar.getMenu().add(0,5,10,cityName).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            runForecast(cityName);


            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                //this runs on another thread
                try {
                    stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

                    // Creates a URL object and you pass in the URL of the server you want to connect to as a String.
                    URL url = new URL(stringURL);
                    //connects to the server,
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //Waits for a response from the server.
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //This code creates a pull parser from the inputStream in that you got from the server
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(in, "UTF-8");

                    while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                        switch (xpp.getEventType()) {
                            case XmlPullParser.START_TAG:
                                if (xpp.getName().equals("temperature")) {

                                    current = xpp.getAttributeValue(null, "value");//This gets the current temprature
                                    min = xpp.getAttributeValue(null, "min");//This gets the min temprature
                                    max = xpp.getAttributeValue(null, "max");//This gets the max temprature

                                } else if (xpp.getName().equals("weather")) {

                                    description = xpp.getAttributeValue(null, "value");//This gets the weather description
                                    iconName = xpp.getAttributeValue(null, "icon");//This gets the icon name

                                } else if (xpp.getName().equals("humidity")) {

                                    humidity = xpp.getAttributeValue(null, "value"); // This gets the humidity
                                }
                                break;

                            case XmlPullParser.END_TAG:
                                break;

                            case XmlPullParser.TEXT:
                                break;
                        }
                    }

                    //download that URL and store it as a bitmap
                    File file = new File(getFilesDir(), iconName + ".png");
                    if (file.exists()) {
                        image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + ".png");
                    } else {
                        URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                        HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            image = BitmapFactory.decodeStream(connection.getInputStream());
                            image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                        }
                    }

                    runOnUiThread(() -> {
                        currentView = findViewById(R.id.temp);
                        currentView.setText("The current temperature is " + current);
                        currentView.setVisibility(View.VISIBLE);

                        maxView = findViewById(R.id.maxTemp);
                        maxView.setText("The min temperature is " + min);
                        maxView.setVisibility(View.VISIBLE);

                        minView = findViewById(R.id.minTemp);
                        minView.setText("The max temperature is " + max);
                        minView.setVisibility(View.VISIBLE);

                        humidView = findViewById(R.id.humidity);
                        humidView.setText("The humidity is " + humidity + "%");
                        humidView.setVisibility(View.VISIBLE);

                        humidView = findViewById(R.id.description);
                        humidView.setText(description);
                        humidView.setVisibility(View.VISIBLE);

                        iv = findViewById(R.id.icon);
                        iv.setImageBitmap(image);
                        iv.setVisibility(View.VISIBLE);

                        dialog.hide();
                    });

                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 5:
                String cityName = item.getTitle().toString();
                runForecast(cityName);
                currentView.setVisibility(View.INVISIBLE);
                maxView.setVisibility(View.INVISIBLE);
                minView.setVisibility(View.INVISIBLE);
                humidView.setVisibility(View.INVISIBLE);
                descView.setVisibility(View.INVISIBLE);
                iv = findViewById(R.id.icon);
                iv.setVisibility(View.INVISIBLE);
                cityText.setText("");//clear the city name
                break;

            case R.id.id_increase:
                newSize = newSize + 1;

                currentView.setTextSize(newSize);
                maxView.setTextSize(newSize);
                minView.setTextSize(newSize);
                humidView.setTextSize(newSize);
                descView.setTextSize(newSize);
                cityText.setTextSize(newSize);
                break;

            case R.id.id_decrease:
                newSize = newSize - 1;

                currentView.setTextSize(newSize);
                maxView.setTextSize(newSize);
                minView.setTextSize(newSize);
                humidView.setTextSize(newSize);
                descView.setTextSize(newSize);
                cityText.setTextSize(newSize);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void runForecast(String cityName) {

    }


}