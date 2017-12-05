package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.NoSuchPropertyException;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by oliver on 24/11/17.
 */

public class UserModifyActivity extends AppCompatActivity {
    // User object obtained from intent - will be returned on submit of changes
    private User selectedUser;
    // Intent used to return the user object via the finish() method
    // Globally store imageId to return at end
    private int imageId, requestCode;
    private String resourceName;
    private EditText editFname, editLname;
    private int resultCode;


    /**
     * On create of instance
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);

        // Extract user from intent that created this instance as selectedUser
        Intent intent = getIntent();
        requestCode = (int) intent.getSerializableExtra("requestCode");

        final ImageView userIcon = (ImageView) findViewById(R.id.userIcon);
        final TextView userName = (TextView) findViewById(R.id.userName);
        final TextView title_edit_first_name = (TextView) findViewById(R.id.title_edit_first_name);
        title_edit_first_name.setText("First name");
        final TextView title_edit_last_name = (TextView) findViewById(R.id.title_edit_last_name);
        title_edit_last_name.setText("Last name");
        editFname = (EditText) findViewById(R.id.edit_first_name);
        editLname = (EditText) findViewById(R.id.edit_last_name);

        if (requestCode == 1) {
            selectedUser = (User) intent.getSerializableExtra("user");
            String s = selectedUser.getFname() + " " + selectedUser.getLname();
            setTitle(s);
            userName.setText(s);
            imageId = getResources().getIdentifier(selectedUser.getProfilePicResourceName(), "drawable", getPackageName());
            userIcon.setImageResource(imageId);
            editFname.setText(selectedUser.getFname(), TextView.BufferType.EDITABLE);
            editLname.setText(selectedUser.getLname(), TextView.BufferType.EDITABLE);
        }
        if (requestCode == 0) {
            selectedUser = new User();
            String s = "New user";
            setTitle(s);
            userName.setText(s);
            imageId = R.drawable.man1;
            userIcon.setImageResource(imageId);
            editFname.setText("", TextView.BufferType.EDITABLE);
            editLname.setText("", TextView.BufferType.EDITABLE);
        }


        //****START IMAGE BUTTONS****//
        // Create on click listeners for set image buttons using the tag to set image
        // and storing it into global imageId for later
        View.OnClickListener iconClickHandler = new View.OnClickListener(){
           public void onClick(View v) {
               ImageButton ib = (ImageButton) findViewById(v.getId());
               resourceName = (String) ib.getTag();
               Resources resources = getResources();
               imageId = resources.getIdentifier(resourceName,"drawable", "familytaskmanager.microso.com.familytaskmanager");
               userIcon.setImageResource(imageId);
            }
        };

        // Initialise ImageButton man1
        final ImageButton man1 = (ImageButton) findViewById(R.id.select_man1);
        man1.setTag("man1");
        man1.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man2
        final ImageButton man2 = (ImageButton) findViewById(R.id.select_man2);
        man2.setTag("man2");
        man2.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man3
        final ImageButton man3 = (ImageButton) findViewById(R.id.select_man3);
        man3.setTag("man3");
        man3.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man4
        final ImageButton man4 = (ImageButton) findViewById(R.id.select_man4);
        man4.setTag("man4");
        man4.setOnClickListener(iconClickHandler);

        // Initialise ImageButton man5
        final ImageButton man5 = (ImageButton) findViewById(R.id.select_man5);
        man5.setTag("man5");
        man5.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman1
        final ImageButton woman1 = (ImageButton) findViewById(R.id.select_woman1);
        woman1.setTag("woman1");
        woman1.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman2
        final ImageButton woman2 = (ImageButton) findViewById(R.id.select_woman2);
        woman2.setTag("woman2");
        woman2.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman3
        final ImageButton woman3 = (ImageButton) findViewById(R.id.select_woman3);
        woman3.setTag("woman3");
        woman3.setOnClickListener(iconClickHandler);

        // Initialise ImageButton woman4
        final ImageButton woman4 = (ImageButton) findViewById(R.id.select_woman4);
        woman4.setTag("woman4");
        woman4.setOnClickListener(iconClickHandler);
        //****END IMAGE BUTTONS****//

        final Button submit = (Button) findViewById(R.id.submitChangedUser);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultCode = 1;
                finish();
            }
        });
    }

    /**
     * Method activated on Submit button press, returns the newly edited
     * values back to the class that called the intent, UserActivity
     */
    @Override
    public void finish() {
        selectedUser.setFname(editFname.getText().toString());
        selectedUser.setLname(editLname.getText().toString());
        selectedUser.setProfilePicResourceName(resourceName);

        Intent returnedIntent = new Intent();
        // Load the user object into the returnedIntent
        returnedIntent.putExtra("user", selectedUser);

        // Return required result code to parent
        setResult(resultCode, returnedIntent);

        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                resultCode = 0;
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}