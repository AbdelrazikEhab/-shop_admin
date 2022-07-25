package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import io.grpc.internal.SerializingExecutor;

public class Gategory extends AppCompatActivity {
    Button add_category;
    RecyclerView rc;
    List<Gategory_data> cotants = new ArrayList<>();

    Adapter adapter;
    private static final String TAG = "CodeExa.Com";
    FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gategory);
        add_category = findViewById(R.id.add_category);
        rc = findViewById(R.id.categ);
        rc.setLayoutManager(new GridLayoutManager(Gategory.this, 1));
        adapter = new Adapter(Gategory.this, cotants);
        rc.setAdapter(adapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

            getData();

        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(Gategory.this).create();
                View view = getLayoutInflater().inflate(R.layout.fragment_add_category, null);
                 EditText Ename = view.findViewById(R.id.Ename_new_category);
                alertDialog.setView(view);
                alertDialog.show();
                view.findViewById(R.id.save_new_category).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {


                            String s = Ename.getText().toString();
                            Map<String,Object> map = new HashMap<>();
                            map.put("Colection",s);
                            Map<String,Object> objectMap = new HashMap<>();
//
//                            Gategory_data data = new Gategory_data(s);
//                            data.setKey( firebaseFirestore.collection("Gategories").document(data.getName()).getId());
                            firebaseFirestore.collection("Gategories")
                                    .document(s)
                                    .set(map)
                                    .addOnSuccessListener(avoid ->
                                     Toast.makeText(Gategory.this, "Data is added successfilly", Toast.LENGTH_SHORT).show());
                            firebaseFirestore.collection("Gategories")
                                    .document(s)
                                    .collection(s)
                                    .add(objectMap)
                                    .addOnSuccessListener(avoid ->
                                     Toast.makeText(Gategory.this, "Data is added successfilly", Toast.LENGTH_SHORT).show());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
//        firebaseFirestore.collection("Gategories").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Gategory_data data1 = document.toObject(Gategory_data.class);
//                    data1.setId(document.getId());
//                    cotants.add(data1);
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//        });


    }
    private void getData() {
        firebaseFirestore.collection("Gategories")
//                .orderBy("Colection",Query.Direction.DESCENDING)
                .get()

                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : list) {

                                Gategory_data c = documentSnapshot.toObject(Gategory_data.class);

                                c.setId(documentSnapshot.getId());
                                Log.d("Colection","new ID"+c.getId());

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                cotants.add(c);
//                                getActivity().getSharedPreferences("ID", Context.MODE_PRIVATE)
//                                        .edit()
//                                        .putString("Name", c.getCOLLECTIONNAME())
//                                        .apply();
//                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.putString("Name",c.getCOLLECTIONNAME());
//                                editor.apply();
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            adapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(Gategory.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}


//for(DataSnapshot snap : templateSnapshot.getChildren()){