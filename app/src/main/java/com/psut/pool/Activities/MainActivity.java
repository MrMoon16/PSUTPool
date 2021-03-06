package com.psut.pool.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.psut.pool.Fragments.MainTabFragment;
import com.psut.pool.Fragments.NotificationTabFragment;
import com.psut.pool.Fragments.OfferTabFragment;
import com.psut.pool.Fragments.ProfileTabFragment;
import com.psut.pool.R;
import com.psut.pool.Shared.Layout;

import java.util.Objects;

import static com.psut.pool.R.drawable.ic_directions_car_black_24dp;
import static com.psut.pool.Shared.Constants.DATABASE_ACCOUNT_TYPE;
import static com.psut.pool.Shared.Constants.DATABASE_IS_DRIVER;
import static com.psut.pool.Shared.Constants.DATABASE_USERS;

public class MainActivity extends AppCompatActivity implements Layout {

    //Global Variables and Objects:
    private ImageView imgCar, imgOffer, imgNotification, imgAccount;
    private Fragment fragmentMainTab, fragmentOfferTab, fragmentNofitication, fragmentProfile;
    private static String isDriver, accountType;

    public static String getIsDriver() {
        return isDriver;
    }

    public static void setIsDriver(String isDriver) {
        MainActivity.isDriver = isDriver;
    }

    public static String getAccountType() {
        return accountType;
    }

    public static void setAccountType(String accountType) {
        MainActivity.accountType = accountType;
    }

    public static void isDriver(DatabaseReference databaseReference) {
        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child(DATABASE_IS_DRIVER)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            isDriver = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            Log.d("isDriver", isDriver);
                            Log.d("DataSnapshot", dataSnapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Objects:
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(DATABASE_USERS);

        layoutComponents();
        setupFragments();
        isDriver(databaseReference);
        setupAccountType(databaseReference);
    }

    private void setupAccountType(DatabaseReference reference) {
        reference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(DATABASE_ACCOUNT_TYPE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    accountType = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                } else {
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(DATABASE_ACCOUNT_TYPE).setValue("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    @Override
    public void layoutComponents() {
        //Layout Components:
        imgCar = findViewById(R.id.imgCarMain);
        imgOffer = findViewById(R.id.imgOfferMain);
        imgNotification = findViewById(R.id.imgNotificationMain);
        imgAccount = findViewById(R.id.imgAccountMain);

        //Fragments:
        fragmentMainTab = new MainTabFragment();
        fragmentOfferTab = new OfferTabFragment();
        fragmentNofitication = new NotificationTabFragment();
        fragmentProfile = new ProfileTabFragment();
    }

    private void setupFragments() {

        //Default Fragment:
        getSupportFragmentManager().beginTransaction().replace(R.id.linLayoutHomeMain, fragmentMainTab).commit();

        //Other Fragments:
        imgOffer.setOnClickListener(v -> {
            imgOffer.setImageResource(R.drawable.ic_local_offer_black_24dp);
            imgCar.setImageResource(R.drawable.ic_directions_car_gray_24dp);
            imgAccount.setImageResource(R.drawable.ic_account_circle_gray_24dp);
            imgNotification.setImageResource(R.drawable.ic_notifications_gray_24dp);
            getSupportFragmentManager().beginTransaction().replace(R.id.linLayoutHomeMain, fragmentOfferTab).commit();
        });

        imgNotification.setOnClickListener(v -> {
            imgNotification.setImageResource(R.drawable.ic_notifications_black_24dp);
            imgOffer.setImageResource(R.drawable.ic_local_offer_gray_24dp);
            imgCar.setImageResource(R.drawable.ic_directions_car_gray_24dp);
            imgAccount.setImageResource(R.drawable.ic_account_circle_gray_24dp);
            getSupportFragmentManager().beginTransaction().replace(R.id.linLayoutHomeMain, fragmentNofitication).commit();
        });

        imgAccount.setOnClickListener(v -> {
            imgAccount.setImageResource(R.drawable.ic_account_circle_black_24dp);
            imgOffer.setImageResource(R.drawable.ic_local_offer_gray_24dp);
            imgCar.setImageResource(R.drawable.ic_directions_car_gray_24dp);
            imgNotification.setImageResource(R.drawable.ic_notifications_gray_24dp);
            getSupportFragmentManager().beginTransaction().replace(R.id.linLayoutHomeMain, fragmentProfile).commit();
        });

        imgCar.setOnClickListener(v -> {
            imgCar.setImageResource(ic_directions_car_black_24dp);
            imgAccount.setImageResource(R.drawable.ic_account_circle_gray_24dp);
            imgOffer.setImageResource(R.drawable.ic_local_offer_gray_24dp);
            imgNotification.setImageResource(R.drawable.ic_notifications_gray_24dp);
            getSupportFragmentManager().beginTransaction().replace(R.id.linLayoutHomeMain, fragmentMainTab).commit();
        });
    }

    @Override
    public void getLayoutComponents() {
    }

    @Override
    public void onClickLayout() {
    }

    @Override
    public void onClick(View v) {
    }
}
