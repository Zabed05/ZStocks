package com.zak.zstocks.utils;

import com.zak.zstocks.model.PortfolioItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

public class PortfolioManager {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    // BUY STOCK
    public static void buyStock(String name) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference ref = db.collection("users")
                .document(userId)
                .collection("portfolio")
                .document(name);

        ref.get().addOnSuccessListener(doc -> {

            int qty = 1;

            if (doc.exists()) {
                Long existingQty = doc.getLong("quantity");
                if (existingQty != null) {
                    qty = existingQty.intValue() + 1;
                }
            }

            // 🔥 USE MAP (BEST WAY)
            java.util.Map<String, Object> data = new java.util.HashMap<>();
            data.put("stockName", name);
            data.put("quantity", qty);

            ref.set(data)
                    .addOnSuccessListener(aVoid -> {
                        System.out.println("✅ Data Saved");
                    })
                    .addOnFailureListener(e -> {
                        System.out.println("❌ Error: " + e.getMessage());
                    });
        });
    }



    // SELL STOCK
    public static void sellStock(String name) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference ref = db.collection("users")
                .document(userId)
                .collection("portfolio")
                .document(name);

        ref.get().addOnSuccessListener(doc -> {

            if (doc.exists()) {

                Long qtyLong = doc.getLong("quantity");

                if (qtyLong != null) {

                    int qty = qtyLong.intValue();

                    if (qty > 1) {
                        ref.update("quantity", qty - 1);
                    } else {
                        ref.delete();
                    }
                }
            }
        });
    }


    // 📥 GET PORTFOLIO
    public interface PortfolioCallback {
        void onSuccess(List<PortfolioItem> list);
    }

    public static void getPortfolio(PortfolioCallback callback) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users")
                .document(userId)
                .collection("portfolio")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<PortfolioItem> list = new ArrayList<>();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getString("stockName");
                        Long qtyLong = doc.getLong("quantity");

                        if (name != null && qtyLong != null) {
                            list.add(new PortfolioItem(name, qtyLong.intValue()));
                        }
                    }

                    callback.onSuccess(list);
                });
    }
}
