package com.example.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Adapter_products extends RecyclerView.Adapter<Adapter_products.MyViewholder> {
    LayoutInflater layoutIntflater;
    android.content.Context Context;
    List<product_data> contacts;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference subjectsRef = firebaseFirestore.collection("Gategories");



    public Adapter_products(products context, List<product_data> contacts) {
        this.Context = context;
        this.contacts = contacts;
        layoutIntflater = LayoutInflater.from(Context);
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutIntflater.inflate(R.layout.adapter_products, parent, false);
        MyViewholder MY = new MyViewholder(v);
        return MY;
    }
    public void removeAt(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contacts.size());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        product_data contant = contacts.get(position);
        holder.name.setText(contant.getModel());
        holder.salary.setText("" + contant.getPrice());
        holder.qiantati.setText("" + contant.getQuantati());

    }
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void filterList(ArrayList<product_data> filterdNames) {
        this.contacts = filterdNames;
        notifyDataSetChanged();
    }


    class MyViewholder extends RecyclerView.ViewHolder {

        TextView name, salary, qiantati;
        Button remove;
        Context context;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.prduct_name);
            salary = itemView.findViewById(R.id.prduct_salary);
            qiantati = itemView.findViewById(R.id.prduct_quantati);
            remove = itemView.findViewById(R.id.delete);


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    product_data contant = contacts.get(getAdapterPosition());
                   subjectsRef
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    if (!queryDocumentSnapshots.isEmpty()) {


                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {

                                            Gategory_data c = d.toObject(Gategory_data.class);
                                            c.setId(d.getId());
                                            subjectsRef.document(c.getId())
                                                    .collection(c.getId())
                                                    .document(contant.getModel()).delete();
                                        }
                                    }


                                    removeAt(getAdapterPosition());
                                }
                            });


                }
            });
        }



    }
}



