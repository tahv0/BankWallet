package com.kimmosoft.bankwallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kimmosoft.bankwallet.src.RealmHelper;
import com.kimmosoft.bankwallet.src.Validator;

public class FriendEditName extends AppCompatActivity {
    private int friendid;
    private Validator validator;
    private Button button;
    private RealmHelper realmHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_edit_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        friendid=  getIntent().getExtras().getInt("friendid");
        this.validator = new Validator();
        this.realmHelper = new RealmHelper(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit name");
        EditText nameText = (EditText) findViewById(R.id.edit_name_editText);
        nameText.setText(this.realmHelper.getFriend(friendid).getName());
        button = (Button) findViewById(R.id.button_save_name);
        button.setOnClickListener(new saveButtonActivity());
    }
    public class saveButtonActivity implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditText nameText = (EditText) findViewById(R.id.edit_name_editText);

            if (!validator.validName(nameText.getText().toString())){
                Snackbar.make(v, "Not valid name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

            }

            realmHelper.saveNewName(friendid, nameText.getText().toString());

            recreate();

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            setResult(Activity.RESULT_OK );
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
