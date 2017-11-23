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

public class FridgeActivity extends AppCompatActivity {

    private String itemString;
    private String itemQuantity;
    public ArrayList<Grocerie> fridge = new ArrayList<Grocerie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        ListView listView = (ListView) findViewById(R.id.list_groceries);

        fridge.add(new Grocerie(1, "Tomatoes", 8));
        fridge.add(new Grocerie(2, "Potatoes", 16));

        FridgeListAdapter adapter = new FridgeListAdapter(this, fridge);
        listView.setAdapter(adapter);

        FloatingActionButton add_grocerie = (FloatingActionButton) findViewById(R.id.grocerie_add_btn);
        add_grocerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FridgeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.tool_add_dialog, null);
                mBuilder.setView(mView);

                final AlertDialog dialog = mBuilder.create();
                final EditText name_edit = (EditText) mView.findViewById(R.id.tool_name);
                final EditText supply_edit = (EditText) mView.findViewById(R.id.tool_supply);
                Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FridgeActivity.this, "Adding is cancelled.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                Button add_btn = (Button) mView.findViewById(R.id.add_btn);
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!name_edit.getText().toString().isEmpty() && !supply_edit.getText().toString().isEmpty()) {
                            Toast.makeText(FridgeActivity.this, "Adding completed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FridgeActivity.this, "You need to fill both fields..", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
