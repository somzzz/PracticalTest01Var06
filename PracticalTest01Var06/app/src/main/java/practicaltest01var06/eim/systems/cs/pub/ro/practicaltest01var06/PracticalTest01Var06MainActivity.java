package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PracticalTest01Var06MainActivity extends AppCompatActivity {

    private static boolean startedService = false;
    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver = null;
    private IntentFilter startedServiceIntentFilter = null;

    private class EditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Button passBut = (Button)findViewById(R.id.pass);


            if (s.length() > 0 && s.charAt(0) == 'h') {
                if (s.length() > 1 && s.charAt(1) == 't') {
                    if (s.length() > 2 && s.charAt(2) == 't') {
                        if (s.length() > 3 && s.charAt(3) == 'p') {
                            passBut.setText("Pass");
                            passBut.setBackground(getApplicationContext().getResources().getDrawable(R.color.green));

                            if (!startedService) {
                                startedService = true;
                                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                                EditText link = (EditText)findViewById(R.id.link);
                                intent.putExtra("link", link.getText().toString());
                                startService(intent);
                            }

                        } else {
                            passBut.setText("Fail");
                            passBut.setBackground(getApplicationContext().getResources().getDrawable(R.color.red));
                        }
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class ShowHideListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LinearLayout container = (LinearLayout)findViewById(R.id.container);
            Button showBut = (Button)findViewById(R.id.detail);

            if (container.getVisibility() == View.INVISIBLE) {
                container.setVisibility(View.VISIBLE);
                showBut.setText("Less detail");
            } else if (container.getVisibility() == View.VISIBLE) {
                container.setVisibility(View.INVISIBLE);
                showBut.setText("More detail");
            }
        }
    }

    private class NavigateListener implements  View.OnClickListener {

        @Override
        public void onClick(View v) {
            EditText name = (EditText)findViewById(R.id.name);
            Button pass = (Button)findViewById(R.id.pass);

            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("stat", pass.getText().toString());

            startActivityForResult(intent, 42);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);


        Button showBut = (Button)findViewById(R.id.detail);
        showBut.setOnClickListener(new ShowHideListener());

        EditText link = (EditText)findViewById(R.id.link);
        link.addTextChangedListener(new EditTextWatcher());

        Button nav = (Button)findViewById(R.id.navigate);
        nav.setOnClickListener(new NavigateListener());




        if (savedInstanceState != null) {
            EditText linktext = (EditText)findViewById(R.id.link);
            EditText nametext = (EditText)findViewById(R.id.name);

            if (savedInstanceState.containsKey("name")) {
                EditText name = (EditText)findViewById(R.id.name);
                nametext.setText(savedInstanceState.getString("name").toString());
            }

            if (savedInstanceState.containsKey("link")) {
                EditText name = (EditText)findViewById(R.id.link);
                linktext.setText(savedInstanceState.getString("link").toString());
            }

            Toast.makeText(getApplication(), linktext.getText().toString() + " " + nametext.getText().toString(), Toast.LENGTH_LONG).show();
        }


        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver();

        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction(Constants.ACTION_1);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.d("---------------------->", "Called on save instance");

        EditText link = (EditText)findViewById(R.id.link);
        EditText name = (EditText)findViewById(R.id.name);
        savedInstanceState.putString("name", name.getText().toString());
        savedInstanceState.putString("link", link.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("name")) {
            EditText name = (EditText)findViewById(R.id.name);
            name.setText(savedInstanceState.getString("name"));
        }

        if (savedInstanceState.containsKey("link")) {
            EditText name = (EditText)findViewById(R.id.link);
            name.setText(savedInstanceState.getString("link"));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);
    }


    @Override
    protected void onPause() {
        unregisterReceiver(startedServiceBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onDestroy() {

        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        stopService(intent);

        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 42:
                Toast.makeText(getApplication(), "sec Activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
                break;
        }
    }

}
