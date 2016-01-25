package com.kimmosoft.bankwallet;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kimmosoft.bankwallet.src.RealmHelper;
import com.kimmosoft.bankwallet.src.Validator;

public class AccountAddNew extends AppCompatActivity {
    private RealmHelper realmHelper;
    private Validator validator;
    private Button button;
 //   private Intent intent;
    private int friendid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add_new);
        ActionBar actionBar = getSupportActionBar();
        this.realmHelper = new RealmHelper(getApplicationContext());
       // this.intent = new Intent(getApplicationContext(),AccountListActivity.class);
        friendid = getIntent().getExtras().getInt("friendid");
        //this.intent.putExtra("friendid",friendid);
        this.validator = new Validator();

        if (actionBar != null){
            //actionBar.setTitle(realmHelper.getFriend(friendtId).getName() + "'s accounts");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        button = ( Button) findViewById(R.id.account_add_button);
        button.setOnClickListener(new saveButtonActivity());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Log.d("cyka", "blyat2");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class saveButtonActivity implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.iban_declaration_editText);

            if (!validator.validIBAN(ibanText.getText().toString())){
                Snackbar.make(v, "Not valid IBAN. It must be valid iban and it contains 2 letters and 16 digits", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            realmHelper.addAccount( friendid, ibanText.getText().toString(), declarationText.getText().toString());

        }


    }
}