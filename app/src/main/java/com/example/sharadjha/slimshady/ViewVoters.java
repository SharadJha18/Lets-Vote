package com.example.sharadjha.slimshady;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharadjha.slimshady.Data.VoterData;
import com.example.sharadjha.slimshady.Data.VoterDatabaseHelper;

import java.util.ArrayList;

public class ViewVoters extends AppCompatActivity {

    private VoterAdapter voterAdapter;
    private TextView emptyStateTextView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_voters);

        context = getBaseContext();
        ListView voterListView = (ListView) findViewById(R.id.voterList);

        emptyStateTextView = (TextView) findViewById(R.id.emptyView);
        voterListView.setEmptyView(emptyStateTextView);

        ArrayList<VoterData> voterDatas = new ArrayList<VoterData>();
        final VoterDatabaseHelper db = new VoterDatabaseHelper(context);
        try {
            db.open();
            voterDatas = db.getAllData();
            db.close();
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        voterAdapter = new VoterAdapter(this, 0, voterDatas);

        voterListView.setAdapter(voterAdapter);

        voterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VoterData voterData = (VoterData) parent.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), voterData.getVoterId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), VoterProfile.class);
                intent.putExtra("voterId", voterData.getVoterId());
                intent.putExtra("buttonDisabled", true);
                startActivity(intent);
            }
        });

        voterListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                final PopupMenu popupMenu = new PopupMenu(ViewVoters.this, view, Gravity.CENTER);
                popupMenu.getMenuInflater().inflate(R.menu.menu_long_click, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        try {
                            Snackbar snackbar = Snackbar.make(getCurrentFocus(), "Are you sure to" +
                                    " delete ?", Snackbar.LENGTH_LONG);
                            snackbar.setAction("YES", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    VoterData VoterData = (VoterData) parent.getItemAtPosition(position);
                                    try {
                                        db.open();
                                        db.deleteData(VoterData.getVoterId());
                                        db.close();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                        Toast.makeText(getBaseContext(), "Deleted!!!", Toast
                                                .LENGTH_LONG)
                                                .show();
                                    } catch (SQLException e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            snackbar.show();
                        } catch (Exception e) {
                            Log.e("Dialog", e.getMessage());
                        }
                        return true;
                    }
                });

                MenuPopupHelper menuHelper = new MenuPopupHelper(ViewVoters.this, (MenuBuilder)
                        popupMenu.getMenu(), view);
                menuHelper.setForceShowIcon(true);
                try {
                    popupMenu.show();
                    menuHelper.show();
                } catch (Exception e) {
                    Log.e("PopUpMenu", e.getMessage());
                }
                return true;
            }
        });

    }
}
