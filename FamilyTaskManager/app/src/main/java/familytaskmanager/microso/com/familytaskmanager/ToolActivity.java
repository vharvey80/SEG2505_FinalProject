package familytaskmanager.microso.com.familytaskmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        FloatingActionButton add_tool = (FloatingActionButton) findViewById(R.id.tool_add_btn);
        add_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ToolActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.tool_add_dialog, null);
                mBuilder.setView(mView);

                final AlertDialog dialog = mBuilder.create();
                final EditText name_edit = (EditText) mView.findViewById(R.id.tool_name);
                final EditText supply_edit = (EditText) mView.findViewById(R.id.tool_supply);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ToolActivity.this, "Adding is cancelled.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                Button add_btn = (Button) mView.findViewById(R.id.add_btn);
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!name_edit.getText().toString().isEmpty() && !supply_edit.getText().toString().isEmpty()) {
                            Toast.makeText(ToolActivity.this, "Adding completed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ToolActivity.this, "You need to fill both fields..", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
