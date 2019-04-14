package com.psut.pool.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.psut.pool.R;

import java.util.Objects;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Objects.requireNonNull(getSupportActionBar()).hide();
        RelativeLayout relativeLayout = findViewById(R.id.relativeWallet);
        relativeLayout.setOnClickListener(v -> finish());
    }
}
