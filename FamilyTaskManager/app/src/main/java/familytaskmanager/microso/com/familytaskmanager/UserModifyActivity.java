package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.NoSuchPropertyException;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by oliver on 24/11/17.
 */

public class UserModifyActivity extends AppCompatActivity {
    private User selectedUser;

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

        String s = selectedUser.getFname()+" "+selectedUser.getLname();
        setTitle(s);

        ImageView userIcon = (ImageView) findViewById(R.id.userIcon);
        // TODO: 03/12/17 Oliver: Pull image resource from intent 
        userIcon.setImageResource(R.drawable.man1);
        final TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(s);
        final TextView title_edit_first_name = (TextView) findViewById(R.id.title_edit_first_name);
        final TextView title_edit_last_name  = (TextView) findViewById(R.id.title_edit_last_name);
        title_edit_first_name.setText("First name");
        title_edit_last_name.setText("Last name");
        final EditText editFname = (EditText) findViewById(R.id.edit_first_name);
        editFname.setText(selectedUser.getFname(), TextView.BufferType.EDITABLE);
        final EditText editLname = (EditText) findViewById(R.id.edit_last_name);
        editLname.setText(selectedUser.getLname(), TextView.BufferType.EDITABLE);

    }
}
