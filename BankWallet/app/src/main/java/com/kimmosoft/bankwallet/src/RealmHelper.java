package com.kimmosoft.bankwallet.src;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by tahv0 on 25-Jan-16.
 */
public class RealmHelper {
    public RealmHelper(Context context) {
        this.realm = Realm.getInstance(context);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Realm realm;

    public RealmResults<Friend> getBankAccounts(){
        RealmQuery<Friend> query = realm.where(Friend.class);
        return query.findAll();
    }

    public  void addFriend(String name, String iban,String declaration) {

        realm.beginTransaction();
        Friend friend  = realm.createObject(Friend.class);
        friend.setName(name);
        friend.setId(realm.where(Friend.class).max("id").intValue() + 1);
        RealmList<BankAccount> list = new RealmList<BankAccount>();
        BankAccount account = realm.createObject(BankAccount.class);
        account.setIban(iban);
        account.setId(realm.where(BankAccount.class).max("id").intValue()+1);
        account.setDeclaration(declaration);
        list.add(account);
        friend.setAccounts(list);
        realm.commitTransaction();
        Log.d("Bankaccounts", String.valueOf(getBankAccounts().size()));
    }
    public  Friend getFriend(int id){
        RealmQuery<Friend> query = realm.where(Friend.class);
        query.equalTo("id", id);
        RealmResults<Friend> result1 = query.findAll();
        return result1.first();
    }
    public BankAccount getAccount(int id){

        RealmQuery<BankAccount> query = realm.where(BankAccount.class);
        BankAccount account =  query.equalTo("id", id).findFirst();
        return account;
    }
    public void addAccount(int friendid,String iban,String declaration){
        realm.beginTransaction();
        RealmQuery<Friend> query = realm.where(Friend.class);
        RealmResults<Friend> results =  query.equalTo("id", friendid).findAll();
        BankAccount account = realm.createObject(BankAccount.class);
        account.setIban(iban);
        account.setDeclaration(declaration);
        account.setId(realm.where(BankAccount.class).max("id").intValue() + 1);
        results.first().getAccounts().add(account);

        realm.commitTransaction();
    }
    public void removeAccount(int friendid, int accountid){
        realm.beginTransaction();
        RealmQuery<Friend> query = realm.where(Friend.class);
        Friend friend =  query.equalTo("id", friendid).findFirst();
        friend.getAccounts().remove(realm.where(BankAccount.class).equalTo("id", accountid).findFirst());
        realm.commitTransaction();
    }
    public void removeFriend(int friendid){
        realm.beginTransaction();
        RealmQuery<Friend> query = realm.where(Friend.class);
        Friend friend =  query.equalTo("id", friendid).findFirst();
        friend.removeFromRealm();
        realm.commitTransaction();
    }
    public void saveNewName(int friendid, String name){
        realm.beginTransaction();
        RealmQuery<Friend> query = realm.where(Friend.class);
        Friend friend =  query.equalTo("id", friendid).findFirst();
        friend.setName(name);
        realm.commitTransaction();
    }
    public void editAccount(int accountid, String iban, String declaration){
        realm.beginTransaction();
        RealmQuery<BankAccount> query = realm.where(BankAccount.class);
        BankAccount account =  query.equalTo("id", accountid).findFirst();
        account.setIban(iban);
        account.setDeclaration(declaration);
        realm.commitTransaction();
    }
}
