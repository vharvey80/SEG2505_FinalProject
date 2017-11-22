package familytaskmanager.microso.com.familytaskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToolActivity extends AppCompatActivity {

    public ArrayList<Tool> tools = new ArrayList<Tool>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        ListView listView = (ListView) findViewById(R.id.list_tools);

        tools.add(new Tool(1, "Bucket", 5));
        tools.add(new Tool(2, "Broom", 1));
        tools.add(new Tool(3, "Sponge", 12));
        tools.add(new Tool(4, "Wrench", 2));


        ToolListAdapter adapter = new ToolListAdapter(this, tools);
        listView.setAdapter(adapter);
    }
}
