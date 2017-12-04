package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        selectedUser = (User) intent.getSerializableExtra("user");

        //Get the user list of tasks
        taskList = selectedUser.getAssignedTo();

        //Setting the title to the task from which the activity was called
        if (selectedUser != null) {
            setTitle(selectedUser.getFname());
        }else {
            setTitle("User");
        }

        //Get the views from the layout
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView rewardValue = (TextView) findViewById(R.id.rewardValue);
        ImageView userIcon = (ImageView) findViewById(R.id.userIcon);


        //Set user info into the views
        String s = selectedUser.getFname()+" "+selectedUser.getLname();
        userName.setText(s);
        s = selectedUser.getAccumulatedPts()+" pts";
        rewardValue.setText(s);
        //Getting profile pic
        String resourceName = selectedUser.getProfilePicResourceName();
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
        userIcon.setImageResource(resourceId);
        //end of getting picture

        //Set tasks list view
        ListView listView = (ListView) findViewById(R.id.currentTasksListView);
        TaskListAdapter taskListAdapter = new TaskListAdapter(this.getApplicationContext(), taskList, null, this);
        listView.setAdapter(taskListAdapter);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
