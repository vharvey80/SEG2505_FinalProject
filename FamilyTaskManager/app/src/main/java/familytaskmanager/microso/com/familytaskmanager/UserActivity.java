package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class UserActivity extends AppCompatActivity {

    private User selectedUser;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Get the Intent that started this activity and extract the user
        Intent intent = getIntent();
        selectedUser = (User) intent.getSerializableExtra("user");

        //Get the user list of tasks
        taskList = selectedUser.getAssignedTo();

        //Setting the title to the task from which the activity was called
        if (selectedUser != null) {
            setTitle(selectedUser.getFname());
        } else {
            setTitle("User");
        }

        //Get the views from the layout
        TextView rewardValue = (TextView) findViewById(R.id.rewardValue);


        //Set user info into the views
        String s = selectedUser.getAccumulatedPts() + " pts";
        rewardValue.setText(s);

        setUserInfoInView();

        //Set tasks list view
        ListView listView = (ListView) findViewById(R.id.currentTasksListView);
        TaskListAdapter taskListAdapter = new TaskListAdapter(this.getApplicationContext(), taskList, null, this);
        listView.setAdapter(taskListAdapter);
    }

    public void setUserInfoInView() {
        TextView userName = (TextView) findViewById(R.id.userName);
        String s = selectedUser.getFname() + " " + selectedUser.getLname();
        userName.setText(s);

        ImageView userIcon = (ImageView) findViewById(R.id.userIcon);
        //Getting profile pic
        String resourceName = selectedUser.getProfilePicResourceName();
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
        userIcon.setImageResource(resourceId);
        //end of getting picture

    }

    @Override
    public void finish() {
        Intent returnedIntent = new Intent();
        returnedIntent.putExtra("user", selectedUser);
        setResult(4, returnedIntent);
        super.finish();
    }

    /**
     * Generates edit and delete buttons on top of screen
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_detail_menu, menu);
        return true;
    }


    /**
     * Invoques the appropriate logic when either the edit or delete
     * buttons are pressed
     *
     * @param item
     * @return boolean
     */
    // TODO: 03/12/17 Oliver: Adjust values to better suit this implementation
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_changeTaskName: // Actually change user
                Intent intent = new Intent(getApplicationContext(), UserModifyActivity.class);
                intent.putExtra("user", selectedUser);
                startActivityForResult(intent, 1);
                return true;
            case R.id.action_deletaTask: // Delete user
                Toast.makeText(this, "Can't delete yet, might get complicated", Toast.LENGTH_SHORT).show();
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item); //Simply copied this line from official Android Tutorials
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            selectedUser = (User) data.getSerializableExtra("user");
            setUserInfoInView();
        }
    }
}

