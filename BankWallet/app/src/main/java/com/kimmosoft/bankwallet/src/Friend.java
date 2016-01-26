package com.kimmosoft.bankwallet.src;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by tahv0 on 25-Jan-16.
 */
public  class Friend extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String name;
    private RealmList<BankAccount> bankAccounts;


    public void setId(int id){this.id = id;};
    public void setName(String name){this.name = name;};
    public int getId(){return this.id;};
    public String getName(){return this.name;};
    public RealmList<BankAccount> getBankAccounts() {return this.bankAccounts;};
    public void setBankAccounts(RealmList<BankAccount> value){this.bankAccounts.addAll(value);};


}