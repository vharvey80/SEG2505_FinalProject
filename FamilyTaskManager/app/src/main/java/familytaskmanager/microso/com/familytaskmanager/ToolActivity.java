package familytaskmanager.microso.com.familytaskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToolActivity extends AppCompatActivity {

    public List<Tool> tools, addedTools;
    public List<String> deletedTools;
    ToolListAdapter toolListAdapter;
    Intent returnedIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        getSupportActionBar().setTitle("Tool management");

        returnedIntent = new Intent(getBaseContext(), MainActivity.class);

        ListView listView = (ListView) findViewById(R.id.list_tools);

        Intent intent = getIntent();
        tools = (List<Tool>) intent.getSerializableExtra("tools");
        addedTools =  new ArrayList<Tool>();
        deletedTools = new ArrayList<String>();

        toolListAdapter = new ToolListAdapter(this, tools);
        listView.setAdapter(toolListAdapter);

        FloatingActionButton add_tool = (FloatingActionButton) findViewById(R.id.tool_add_btn);
        add_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ToolActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.tool_add_dialog, null);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            /* PERSONALIZATION OF THE DIALOG (REUSABILITY) */
            final EditText name_edit = (EditText) mView.findViewById(R.id.tool_name);
            name_edit.setHint("Tool name"); // personalize the hint in the edittext
            final EditText supply_edit = (EditText) mView.findViewById(R.id.tool_supply);
            supply_edit.setHint("Tool supply"); // personalize the hint in the edittext
            final ImageView image = (ImageView) mView.findViewById(R.id.add_pic);
            final TextView add_title = (TextView) mView.findViewById(R.id.add_title);
            add_title.setText("ADD TOOL MANAGER"); // personalize the title of your dialog
            /* END OF PERSONALIZATION */

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
                        Tool createdTool = new Tool("1", name_edit.getText().toString(), Integer.parseInt(supply_edit.getText().toString()));
                        tools.add(createdTool);
                        addedTools.add(createdTool);
                        toolListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(ToolActivity.this, "You need to fill both fields..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
            }
        });
    }

    @Override
    public void finish() {
        returnedIntent.putExtra("addedTools", (Serializable) addedTools);
        returnedIntent.putExtra("deletedTools", (Serializable) deletedTools);
        setResult(1, returnedIntent);
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

    public boolean deleteTools(Tool d_tool) {
        areYouSure(d_tool);
        return true;
    }

    private void areYouSure(final Tool toolToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ToolActivity.this);
        builder.setTitle("Are you sure you want to delete this tool ? ");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            tools.remove(toolToDelete);
            if (addedTools.size() <= 0) {
                deletedTools.add(toolToDelete.getId());
            } else {
                boolean added = false;
                for (Tool t : addedTools) {
                    if (t.getId().equals(toolToDelete.getId())) {
                        added = true;
                        break;
                    }
                }
                if(added) {
                    addedTools.remove(toolToDelete);
                } else {
                    deletedTools.add(toolToDelete.getId());
                }
            }
            toolListAdapter.notifyDataSetChanged();
            dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            //TODO
            dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
