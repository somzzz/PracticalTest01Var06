package practicaltest01var06.eim.systems.cs.pub.ro.practicaltest01var06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);

        Button ok = (Button)findViewById(R.id.ok);
        Button cancel = (Button)findViewById(R.id.cancel);

        ok.setOnClickListener(new OkListener());
        cancel.setOnClickListener(new CancelListener());

        EditText linkET = (EditText)findViewById(R.id.link_sec);
        EditText statEt = (EditText)findViewById(R.id.pass_sec);

        Intent intent = getIntent();
        if (intent != null) {
            String link = intent.getStringExtra("name");
            linkET.setText(link);

            String stat = intent.getStringExtra("stat");
            statEt.setText(stat);
        }
    }

    private class OkListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        }
    }

    private class CancelListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setResult(Activity.RESULT_CANCELED, new Intent());
            finish();
        }
    }


}
