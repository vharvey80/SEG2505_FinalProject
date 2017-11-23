package familytaskmanager.microso.com.familytaskmanager;

<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> Some changes for FridgeActivity UI
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.LinearLayout;
import android.widget.Switch;

public class FridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private String itemString;
    private String itemQuantity;
=======

public class FridgeActivity extends AppCompatActivity implements View.OnClickListener {
>>>>>>> Some changes for FridgeActivity UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        Button addItem = (Button) findViewById(R.id.addItemButton);
        addItem.setOnClickListener(FridgeActivity.this);
    }

    @Override
    public void onClick(View v){
<<<<<<< HEAD
        switch(v.getId()) {

            case R.id.addItemButton:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                Context context = this;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText itemInput = new EditText(this);
                itemInput.setHint("Item name");
                layout.addView(itemInput);

                final EditText quantityInput = new EditText(context);
                quantityInput.setHint("Quantity");
                layout.addView(quantityInput);

                alertDialogBuilder.setView(layout);
                alertDialogBuilder.setNegativeButton("Cancel", null);

                alertDialogBuilder.setCancelable(true).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       itemString = itemInput.getText().toString();
                       itemQuantity = quantityInput.getText().toString();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
        }
=======
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(et);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
>>>>>>> Some changes for FridgeActivity UI
    }
}
