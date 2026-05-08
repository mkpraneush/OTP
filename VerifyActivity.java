package com.example.phonenumberverificationapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyActivity extends AppCompatActivity {

    EditText otp;
    Button verifyBtn;

    String verificationId;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        otp = findViewById(R.id.otp);
        verifyBtn = findViewById(R.id.verifyBtn);

        mAuth = FirebaseAuth.getInstance();

        verificationId =
                getIntent().getStringExtra("verificationId");

        verifyBtn.setOnClickListener(v -> {

            String code =
                    otp.getText().toString().trim();

            if (code.isEmpty()) {

                Toast.makeText(this,
                        "Enter OTP",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(
                            verificationId,
                            code);

            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(this,
                                    "Verification Successful",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(this,
                                    "Invalid OTP",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}