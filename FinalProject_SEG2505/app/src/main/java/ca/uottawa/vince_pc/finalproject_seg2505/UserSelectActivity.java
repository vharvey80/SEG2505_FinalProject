package ca.uottawa.vince_pc.finalproject_seg2505;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

public class UserSelectActivity extends AppCompatActivity {
    ListView user_list;
    String[] web = {
            "Thomas",
            "Vincent",
            "Walid",
            "Oliver",
            "Jean-Gabriel",
    } ;
    Integer[] imageId = {
            R.drawable.user,
            R.drawable.user,
            R.drawable.user,
            R.drawable.user,
            R.drawable.user,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

       /* UserList adapter = new UserList(UserSelectActivity.this, web, imageId);
        user_list =(ListView)findViewById(R.id.users_list);
        user_list.setAdapter(adapter);
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(UserSelectActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });*/

    }
}
