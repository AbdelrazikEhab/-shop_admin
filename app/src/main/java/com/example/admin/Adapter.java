package com.example.admin;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewholder> {
    LayoutInflater layoutIntflater;
    Context Context;
    List<Gategory_data> contacts;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();



    public Adapter(Context context , List<Gategory_data> contacts) {
        this.Context = context;
        this.contacts = contacts;
        layoutIntflater = LayoutInflater.from(Context);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutIntflater.inflate(R.layout.adapter_category, parent, false);
        MyViewholder MY = new MyViewholder(v);
        return MY;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Gategory_data contant = contacts.get(position);
        holder.name.setText(contant.getId());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void filterList(ArrayList<Gategory_data> filterdNames) {
        this.contacts = filterdNames;
        notifyDataSetChanged();
    }
    public void removeAt(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contacts.size());
    }



    class MyViewholder extends RecyclerView.ViewHolder  {


        TextView name;
        Button remove, show, edit;
        Context context;



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.view_categ);
            remove = itemView.findViewById(R.id.de);
            show = itemView.findViewById(R.id.show);
            edit = itemView.findViewById(R.id.edit);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog=new AlertDialog.Builder(context).create();
                    View view = layoutIntflater.inflate(R.layout.fragment_add_category, null);
                    alertDialog.setView(view);
                    alertDialog.show();
                    EditText Ename=view.findViewById(R.id.Ename_new_category);

                    view.findViewById(R.id.save_new_category).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String s = Ename.getText().toString();
                            Gategory_data contant = contacts.get(getAdapterPosition());
                            String CollectionName = contant.getId();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Colection", s);
                            DocumentReference rc = firebaseFirestore
                                    .collection("Gategories")
                                    .document(CollectionName);

                                    rc.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Context, "Data's Updated", Toast.LENGTH_SHORT).show();
                                            contacts.set(getAdapterPosition(),new Gategory_data(s));
                                            notifyItemChanged(getAdapterPosition());
                                        }
                                    });

                        }
                    });


                }

            });


            //////////////////////////////////////////////
            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gategory_data contant = contacts.get(getAdapterPosition());

                        Intent home = new Intent(context, products.class);
                      home.putExtra("ID",contant);

                    context.startActivity(home);

                }
            });

            ///////////////////////////////////////////
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    Gategory_data contant = contacts.get(getAdapterPosition());
                     firebaseFirestore.collection("Gategories").document(contant.getId()).delete();
                     removeAt(getAdapterPosition());
                    Toast.makeText(Context, "Data's Deleted", Toast.LENGTH_SHORT).show();

                }
            });




        }


    }
}


