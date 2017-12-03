package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.NoSuchPropertyException;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by oliver on 24/11/17.
 */

public class UserModifyActivity extends AppCompatActivity {
    // User object obtained from intent - will be returned on submit of changes
    private User selectedUser;
    // Intent used to return the user object via the finish() method
    Intent returnedIntent;

    int imageId;



    /**
     * On create of instance
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);

        // Create object to accesss display statistics
        Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        // Extract user from intent that created this instance as selectedUser
        Intent intent = getIntent();
        selectedUser = (User) intent.getSerializableExtra("user");

        // Set title of page as user's
        String s = selectedUser.getFname() + " " + selectedUser.getLname();
        setTitle(s);

        // Grab the user's current image and set it to the ImageView
        final ImageView userIcon = (ImageView) findViewById(R.id.userIcon);
        imageId = getResources().getIdentifier(selectedUser.getProfilePicResourceName(), "drawable", getPackageName());
        userIcon.setImageResource(imageId);

        // Populate the test next to the user image in the page. This should stay constant as the old
        // value to give the user some context.
        final TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(s);

        // Label the text view "First name:" next to the edit text field
        final TextView title_edit_first_name = (TextView) findViewById(R.id.title_edit_first_name);
        title_edit_first_name.setText("First name");

        // Pre-populate the field with the user's current first name
        final EditText editFname = (EditText) findViewById(R.id.edit_first_name);
        editFname.setText(selectedUser.getFname(), TextView.BufferType.EDITABLE);

        // Label the text view "Last name:" next to the edit text field
        final TextView title_edit_last_name = (TextView) findViewById(R.id.title_edit_last_name);
        title_edit_last_name.setText("Last name");

        // Pre-populate the field with the user's current first name
        final EditText editLname = (EditText) findViewById(R.id.edit_last_name);
        editLname.setText(selectedUser.getLname(), TextView.BufferType.EDITABLE);

        // Create on click listeners for set image buttons using the tag to set image
        // and storing it into global imageId for later
        View.OnClickListener iconClickHandler = new View.OnClickListener(){
           public void onClick(View v) {
               ImageButton ib = (ImageButton) findViewById(v.getId());
               imageId = (int) ib.getTag();
               userIcon.setImageResource(imageId);
            }
        };

        // Initialise ImageButton man1
        final ImageButton man1 = (ImageButton) findViewById(R.id.select_man1);
        man1.setTag(R.drawable.man1);
        man1.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man2
        final ImageButton man2 = (ImageButton) findViewById(R.id.select_man2);
        man2.setTag(R.drawable.man2);
        man2.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man3
        final ImageButton man3 = (ImageButton) findViewById(R.id.select_man3);
        man3.setTag(R.drawable.man3);
        man3.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man4
        final ImageButton man4 = (ImageButton) findViewById(R.id.select_man4);
        man4.setTag(R.drawable.man4);
        man4.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man5
        final ImageButton man5 = (ImageButton) findViewById(R.id.select_man5);
        man5.setTag(R.drawable.man5);
        man5.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman1
        final ImageButton woman1 = (ImageButton) findViewById(R.id.select_woman1);
        woman1.setTag(R.drawable.woman1);
        woman1.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman2
        final ImageButton woman2 = (ImageButton) findViewById(R.id.select_woman2);
        woman2.setTag(R.drawable.woman2);
        woman2.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman3
        final ImageButton woman3 = (ImageButton) findViewById(R.id.select_woman3);
        woman3.setTag(R.drawable.woman3);
        woman3.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman4
        final ImageButton woman4 = (ImageButton) findViewById(R.id.select_woman4);
        woman4.setTag(R.drawable.woman4);
        woman4.setOnClickListener(iconClickHandler);
    }


    /**
     * Method activated on Submit button press, returns the newly edited
     * values back to the class that called the intent, UserActivity
     */
    @Override
    public void finish() {

        // Load the user object into the returnedIntent
        returnedIntent.putExtra("user", (Serializable) selectedUser);

        // Return required result code to parent
        // TODO: 03/12/17 Oliver: Understand why 1 and see if optimal 
        setResult(1, returnedIntent);

        super.finish();
    }
}
