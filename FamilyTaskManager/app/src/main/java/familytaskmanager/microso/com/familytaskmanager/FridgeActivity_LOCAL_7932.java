package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

public class FridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private String itemString;
    private String itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        Button addItem = (Button) findViewById(R.id.addItemButton);
        addItem.setOnClickListener(FridgeActivity.this);
    }

    @Override
    public void onClick(View v){
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
    }
}
