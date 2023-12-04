package com.example.project_a;

public class UserWalletDB {
    String userName, mail_id;
    float wallet_balance;

    public UserWalletDB(float wallet_balance) {
        this.userName = userName;
        this.wallet_balance = wallet_balance;
    }

    public UserWalletDB() {
    }


    public float getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(float wallet_balance) {
        this.wallet_balance = wallet_balance;
    }
}
