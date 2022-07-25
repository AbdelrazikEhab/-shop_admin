package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
public class products extends AppCompatActivity {
    RecyclerView rc;
    List<product_data> cotants= new ArrayList<product_data>();
    Button add_product ;
    Adapter_products adapter;

     Gategory_data ID;
     public String id ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ID = (Gategory_data) getIntent().getSerializableExtra("ID");
        id= ID.getId().toString();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        rc=findViewById(R.id.prodct_ad);
        add_product=(Button) findViewById(R.id.add_product);
        rc.setLayoutManager(new GridLayoutManager(products.this, 1));
        adapter = new Adapter_products(this,cotants);
        rc.setAdapter(adapter);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();;
        firebaseFirestore.collection("Gategories")
                .document(ID.getId())
                .collection(ID.getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Gategory_data c = d.toObject(Gategory_data.class);
                                c.setId(d.getId());


                                             cotants.add(c);
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(products.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(products.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(products.this, Addcategory.class);
                home.putExtra("ID",id);
                startActivity(home);
                finish();
            }
        });
    }
}