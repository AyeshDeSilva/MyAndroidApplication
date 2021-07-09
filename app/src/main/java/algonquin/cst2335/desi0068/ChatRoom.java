package algonquin.cst2335.desi0068;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ChatRoom extends AppCompatActivity {

    boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        isTablet = findViewById(R.id.detailsRoom) != null;

        MessageListFragment chatFragment = new MessageListFragment();
        //FragmentManager object, which is a Singleton object
        FragmentManager fMgr = getSupportFragmentManager();
        //Fragment Transactions can add, replace or remove a fragment
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.add(R.id.fragmentRoom, chatFragment);
        //This line  loads the fragment into the specified FrameLayout
        tx.commit();

    }

    public void userClickedMessage(MessageListFragment.ChatMessage chatMessage, int position) {
        MessageDetailsFragment mdFragment = new MessageDetailsFragment(chatMessage, position);

        if (isTablet) {

        } else { // on a phone

        }

    }
}


