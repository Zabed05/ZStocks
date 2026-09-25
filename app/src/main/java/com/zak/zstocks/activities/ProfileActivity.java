package com.zak.zstocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zak.zstocks.R;
import com.zak.zstocks.model.User;

public class ProfileActivity extends AppCompatActivity {

    TextView userName, userEmail, userBalance;
    Button logoutBtn;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // UI init
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userBalance = findViewById(R.id.userBalance);
        logoutBtn = findViewById(R.id.logoutBtn);

        // Firebase init
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Load data
        loadUserData();

        // Logout
        logoutBtn.setOnClickListener(v -> {
            auth.signOut();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserData() {

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();

        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(doc -> {

                    if (doc.exists()) {

                        User user = doc.toObject(User.class);

                        if (user != null) {
                            userName.setText(user.getName());
                            userBalance.setText("Balance: ₹" + user.getBalance());
                        }

                        // Email from FirebaseAuth (always correct)
                        userEmail.setText(currentUser.getEmail());

                    } else {
                        Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
                    }

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
                );
    }
}