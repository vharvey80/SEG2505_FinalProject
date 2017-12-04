package familytaskmanager.microso.com.familytaskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class UserAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        // Set title
        setTitle("New user");

        // Set user icon to default initially
        final ImageView userIcon = (ImageView) findViewById(R.id.userIcon);
        userIcon.setImageResource(R.drawable.menu_people);



    }
}
