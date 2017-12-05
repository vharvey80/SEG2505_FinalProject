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

public class FridgeActivity extends AppCompatActivity {

    public List<ShoppingItem> fridge, addedItems;
    public List<String> deletedItems;
    private FridgeListAdapter fridgeAdapter;
    private Intent returnedIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        getSupportActionBar().setTitle("Fridge management");

        returnedIntent = new Intent(getBaseContext(), MainActivity.class);

        ListView listView = (ListView) findViewById(R.id.list_groceries);


        fridge = (List<ShoppingItem>) getIntent().getSerializableExtra("fridge");
        addedItems = new ArrayList<ShoppingItem>();
        deletedItems = new ArrayList<String>();

        fridgeAdapter = new FridgeListAdapter(this, fridge);
        listView.setAdapter(fridgeAdapter);


        //Setting the listener on the add grocery button.
        FloatingActionButton add_grocerie = (FloatingActionButton) findViewById(R.id.grocerie_add_btn);
        add_grocerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FridgeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.tool_add_dialog, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                /* PERSONALIZATION OF THE DIALOG (REUSABILITY) */
                final EditText name_edit = (EditText) mView.findViewById(R.id.tool_name);
                name_edit.setHint("Grocerie name"); // personalize the hint in the edittext
                final EditText supply_edit = (EditText) mView.findViewById(R.id.tool_supply);
                supply_edit.setHint("Quantity"); // personalize the hint in the edittext
                final ImageView image = (ImageView) mView.findViewById(R.id.add_pic);
                final TextView add_title = (TextView) mView.findViewById(R.id.add_title);
                add_title.setText("ADD GROCERIE MANAGER"); // personalize the title of your dialog
                /* END OF PERSONALIZATION */

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
                        if(!name_edit.getText().toString().isEmpty() && !supply_edit.getText().toString().isEmpty()) {
                            ShoppingItem createdGrocery = new ShoppingItem("temp", name_edit.getText().toString(), false, ShoppingItem.Category.Grocerie, Integer.parseInt(supply_edit.getText().toString()));
                            fridge.add(createdGrocery);
                            addedItems.add(createdGrocery);
                            fridgeAdapter.notifyDataSetChanged();
                            Toast.makeText(FridgeActivity.this, name_edit.getText() + " has been added to your fridge.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(FridgeActivity.this, "You need to fill both fields..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    /**
     * Overides the function that gets called when the activity gets closed, returns the added/
     * deleted items as an intent to the MainActivity for further processing in the DB.
     */
    @Override
    public void finish() {
        returnedIntent.putExtra("addedItems", (Serializable) addedItems);
        returnedIntent.putExtra("deletedItems", (Serializable) deletedItems);
        setResult(3, returnedIntent);
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

    public boolean deleteItem(ShoppingItem d_item) {
        areYouSure(d_item);
        return true;
    }

    /**
     * Simple function asking the user if he really wants to delete the item.
     *
     * @param itemToDelete Reference to the item that needs to be deleted.
     */
    private void areYouSure(final ShoppingItem itemToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FridgeActivity.this);
        builder.setTitle("Are you sure you want to delete this item ? ");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                fridge.remove(itemToDelete);
                if (addedItems.size() <= 0) {
                    deletedItems.add(itemToDelete.getId());
                } else {
                    boolean added = false;
                    for (ShoppingItem t : addedItems) {
                        if (t.getId().equals(itemToDelete.getId())) {
                            added = true;
                            break;
                        }
                    }
                    if(added) {
                        addedItems.remove(itemToDelete);
                    } else {
                        deletedItems.add(itemToDelete.getId());
                    }
                }
                fridgeAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
