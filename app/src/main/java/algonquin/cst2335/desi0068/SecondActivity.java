package algonquin.cst2335.desi0068;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SecondActivity extends AppCompatActivity {

    ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView text = findViewById(R.id.textView);
        Button btn = findViewById(R.id.callbutton);
        EditText phoneNumber = findViewById(R.id.editTextPhone);
        Button btn2 = findViewById(R.id.button2);
        profileImage = findViewById(R.id.imageView);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        text.setText("Welcome back " + emailAddress);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneNumber1 = prefs.getString("PhoneNumber", "");
        phoneNumber.setText(phoneNumber1);

        btn.setOnClickListener( clk -> {
            String phoneNumber2 = phoneNumber.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel: " + phoneNumber2));

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("PhoneNumber", phoneNumber2);
            editor.apply();

            startActivityForResult(call, 5432);
        });

        btn2.setOnClickListener(clk -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 3456);
        });

        File file = new File("/data/user/0/algonquin.cst2335.desi0068/files/Picture.png");
        if(file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile("/data/user/0/algonquin.cst2335.desi0068/files/Picture.png");
            profileImage.setImageBitmap( theImage );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3456) {
            if (resultCode == RESULT_OK) {
                Bitmap thumbnail = data.getParcelableExtra("data");
                profileImage.setImageBitmap( thumbnail );
                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput( "Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}