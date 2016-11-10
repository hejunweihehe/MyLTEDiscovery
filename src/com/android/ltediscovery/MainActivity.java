package com.android.ltediscovery;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ltediscovery.R;

public class MainActivity extends Activity implements TabListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_main);
        initActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
        case R.id.action_detail:
            Intent intent = new Intent(this, AllCellActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void initActionBar() {
        ActionBar localActionBar = getActionBar();
        Log.d("test", "action bar : " + localActionBar);
        localActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        localActionBar.addTab(localActionBar.newTab()
                .setText(getString(R.string.tab_title_signal))
                .setTabListener(this));
        localActionBar.addTab(localActionBar.newTab()
                .setText(getString(R.string.tab_title_cell))
                .setTabListener(this));
        localActionBar.addTab(localActionBar.newTab()
                .setText(getString(R.string.tab_title_neighboring_cell))
                .setTabListener(this));
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if (tab.getText().equals(getString(R.string.tab_title_signal))) {
            getFragmentManager().beginTransaction().replace(R.id.fl_container,
                    new SignalFragment()).commit();
        } else if (tab.getText().equals(getString(R.string.tab_title_cell))) {
            getFragmentManager().beginTransaction().replace(R.id.fl_container,
                    new CellFragment()).commit();
        } else if (tab.getText().equals(
                getString(R.string.tab_title_neighboring_cell))) {
            getFragmentManager().beginTransaction().replace(R.id.fl_container,
                    new NeighboringFragment()).commit();
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
}
