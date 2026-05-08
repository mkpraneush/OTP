package com.example.phonenumberverificationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText phone;
    Button sendOtp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.phone);
        sendOtp = findViewById(R.id.sendOtp);

        mAuth = FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(v -> {

            String number = phone.getText().toString().trim();

            if (number.isEmpty() || number.length() < 10) {
                Toast.makeText(this,
                        "Enter valid number",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91" + number)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(callbacks)
                            .build();

            PhoneAuthProvider.verifyPhoneNumber(options);
        });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(
                        PhoneAuthCredential credential) {

                    // Auto verification
                }

                @Override
                public void onVerificationFailed(
                        FirebaseException e) {

                    Toast.makeText(MainActivity.this,
                            "Error : " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(
                        String verificationId,
                        PhoneAuthProvider.ForceResendingToken token) {

                    Intent intent =
                            new Intent(MainActivity.this,
                                    VerifyActivity.class);

                    intent.putExtra(
                            "verificationId",
                            verificationId);

                    startActivity(intent);
                }
            };
}