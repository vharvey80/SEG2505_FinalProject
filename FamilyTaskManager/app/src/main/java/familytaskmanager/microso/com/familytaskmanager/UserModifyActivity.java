package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.NoSuchPropertyException;
import android.widget.EditText;
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

    /**
     * On create of instance
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);

        // Extract user from intent that created this instance as selectedUser
        Intent intent = getIntent();
        selectedUser = (User) intent.getSerializableExtra("user");

        // Set title of page as user's
        String s = selectedUser.getFname()+" "+selectedUser.getLname();
        setTitle(s);

        // Grab the user's current image and set it to the ImageView
        ImageView userIcon = (ImageView) findViewById(R.id.userIcon);
        userIcon.setImageResource(R.drawable.man1); // TODO: 03/12/17 Oliver: Pull image resource from intent

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
        final TextView title_edit_last_name  = (TextView) findViewById(R.id.title_edit_last_name);
        title_edit_last_name.setText("Last name");

        // Pre-populate the field with the user's current first name
        final EditText editLname = (EditText) findViewById(R.id.edit_last_name);
        editLname.setText(selectedUser.getLname(), TextView.BufferType.EDITABLE);
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
