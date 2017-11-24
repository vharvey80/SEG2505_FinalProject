package familytaskmanager.microso.com.familytaskmanager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {


    TaskListAdapter taskListAdapter;

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        //Setting onClick for the FAB
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addTaskFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskFabClicked();
            }
        });


        //List view code
        ListView listView = (ListView) view.findViewById(R.id.tasksListView);

        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(),
                ((MainActivity)getActivity()).getFamilyTaskList() ,((MainActivity)getActivity()).getFamilyUserList(),
                getActivity());

        listView.setAdapter(taskListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Toast.makeText(getActivity(), "You clicked a view", Toast.LENGTH_SHORT).show();

                final Task clickedTask = (Task) parent.getItemAtPosition(position);


                Intent intent = new Intent(getActivity().getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("task", (Serializable) clickedTask);
                intent.putExtra("toolList", (Serializable) ((MainActivity)getActivity()).getFamilyToolList());
                startActivityForResult(intent, 0);

            }
        });
        //End of List view code


        return view;
    }

    public void taskFabClicked() {
        Toast.makeText(getActivity(), "Task FAB clicked", Toast.LENGTH_SHORT).show();

        showDialogPart1();

    }

    /**
     * REMOVE METHOD, JUST FOR TEST.
     * @param list
     * @return
     */
    private String[] getToolArray(List<Tool> list) {
        String[] out = new String[list.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = list.get(i).getName();
        }
        return out;
    }

    private void showDialogPart1() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_1, null);
        MainActivity.setNumberPickersDialog(dialogView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Task Info (1/2)")
                .setView(dialogView)
                .setPositiveButton("Confirm and Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Here would be logic after entering info
                        showDialogPart2();
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    private void showDialogPart2() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_2, null);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Task Info (2/2)")
                .setView(dialogView)
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Here would be logic after choosing tools
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();

        List<Tool> toolList = ((MainActivity)getActivity()).getFamilyToolList();
        ListView list = (ListView) dialogView.findViewById(R.id.toolListView);
        String[] toolArray = this.getToolArray(toolList);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, toolArray);
        list.setAdapter(adapter);
    }

}
