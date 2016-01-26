package com.kimmosoft.bankwallet;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kimmosoft.bankwallet.src.BankAccount;
import com.kimmosoft.bankwallet.src.RealmHelper;
import com.kimmosoft.bankwallet.src.Validator;

public class BankAccountAddNew extends AppCompatActivity {
    private RealmHelper realmHelper;
    private Validator validator;
    private Button button;
 //   private Intent intent;
    private int friendid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_add_new);
        ActionBar actionBar = getSupportActionBar();
        this.realmHelper = new RealmHelper(getApplicationContext());
        if (getIntent().getExtras().containsKey("friendid")){
            friendid = getIntent().getExtras().getInt("friendid");
            actionBar.setTitle("Add new account for "+ realmHelper.getFriend(friendid).getName());
        }
        else{
            EditText ibanText = (EditText) findViewById(R.id.iban_editText);
            EditText declarationText = (EditText) findViewById(R.id.iban_declaration_editText);
            BankAccount account = realmHelper.getAccount(getIntent().getExtras().getInt("accountid"));
            ibanText.setText( account.getIban());
            declarationText.setText(account.getDeclaration());
            actionBar.setTitle("Edit account");
        }
        this.validator = new Validator();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        button = ( Button) findViewById(R.id.account_add_button);
        button.setOnClickListener(new saveButtonActivity());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

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
            if (getIntent().getExtras().containsKey("friendid")) {
                realmHelper.addAccount(friendid, ibanText.getText().toString(), declarationText.getText().toString());
            }
            else{
                realmHelper.editAccount(getIntent().getExtras().getInt("accountid"),ibanText.getText().toString(),declarationText.getText().toString());
            }

        }


    }
}
