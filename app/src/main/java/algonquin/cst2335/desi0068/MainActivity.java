package algonquin.cst2335.desi0068;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

/** This page of the application validates if the password you login with is complex enough and will tell you if it is complex or not complex enough
 * @author Ayesh De Silva
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * This holds the text at the centre of the screen
     */
    TextView tv;

    /**
     * This holds the editext to enter the password
     */
    EditText et;

    /**
     * This holds the button to login
     */
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editPassword);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();

            if (checkPasswordComplexity(password)) {
                tv.setText("Your password meets the requirements");
            } else {
                tv.setText("You shall not pass!");
            }

        });
    }

    /**
     * This Function checks if this String has upper, lower case letters, numbers and special symbols.
     * If it is missing one of these requirements a toast message will say u are missing a requirement
     *
     * @param pw The String object that we are checking
     * @return Returns true if password is complex enough
     */
    boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;


        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if(Character.isUpperCase(c)) {
                foundUpperCase = true;
            }
            else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            }
            else if(isSpecialCharacter(c)) {
                foundSpecial = true;
            }
            else if(Character.isDigit(c)) {
                foundNumber = true;
            }
        }

        if (!foundUpperCase) {
            Context context = getApplicationContext();
            CharSequence text = "Password is missing an upper case letter";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);//
            toast.show();

            return false;

        } else if (!foundLowerCase) {
            //  Toast toast = Toast.makeText("");// Say that they are missing a lower case letter;
            Context context = getApplicationContext();
            CharSequence text = "Password is missing an lower case letter";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);//
            toast.show();
            return false;

        } else if (!foundNumber) {
            Context context = getApplicationContext();
            CharSequence text = "Password is missing numbers";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);//
            toast.show();
            return false;

        } else if (!foundSpecial) {
            Context context = getApplicationContext();
            CharSequence text = "Password is missing special characters";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);//
            toast.show();

            return false;
        } else
            return true;

    }

    /**
     * This function finds the following special characters
     * If it does not find any of the special characters in the password it will return false but if there is it will return true
     *
     * @param c The character variable we are checking
     * @return true if it finds special characters. Returns false if not
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}