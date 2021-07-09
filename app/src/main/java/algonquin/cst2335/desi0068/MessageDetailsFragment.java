package algonquin.cst2335.desi0068;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MessageDetailsFragment extends Fragment {

    MessageListFragment.ChatMessage chosenMessage;
    int chosenPosition;

    public MessageDetailsFragment(MessageListFragment.ChatMessage message, int position){
        chosenMessage = message;
        chosenPosition = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
          View detailsView = inflater.inflate(R.layout.details_layout, container, false);

          TextView messageView = detailsView.findViewById(R.id.messageView);
          TextView timeView = detailsView.findViewById(R.id.timeView);
          TextView sendView = detailsView.findViewById(R.id.sendView);
          TextView idView = detailsView.findViewById(R.id.idView);

          messageView.setText("Message is: " + chosenMessage.getMessage());
          sendView.setText("Send or Receive?" + chosenMessage.getSendOrReceive());
          timeView.setText("Time send:" + chosenMessage.getTimeSent());
          idView.setText("Database id is:" + chosenMessage.getId());

          Button closeButton = detailsView.findViewById(R.id.closeBtn);
          closeButton.setOnClickListener(closeClicked -> {

          });

          Button deleteButton = detailsView.findViewById(R.id.delBtn);
          deleteButton.setOnClickListener(deleteClicked -> {
              
          });

     return detailsView;
    }
}
