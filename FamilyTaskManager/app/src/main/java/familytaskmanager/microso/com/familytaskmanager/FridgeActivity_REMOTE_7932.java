package familytaskmanager.microso.com.familytaskmanager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FridgeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        Button addItem = (Button) findViewById(R.id.addItemButton);
        addItem.setOnClickListener(FridgeActivity.this);
    }

    @Override
    public void onClick(View v){
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
    }
}
