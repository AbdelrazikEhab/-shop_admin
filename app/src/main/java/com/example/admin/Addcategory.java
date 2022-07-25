package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.admin.R.layout.activity_addcategory;

public class Addcategory extends AppCompatActivity {
EditText name,price,quantati;
Button save ;
ImageView Image;
Spinner s;
product_data data;
ArrayList<String> contatns;
List<String> subjects;

private Uri imageUri;
private  String ImageURL;
private FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
private StorageReference storageReference;
private FirebaseFirestore firestore;
private String userID;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);
        name=findViewById(R.id.name_cagegory);
        price=findViewById(R.id.price_cagegory);
        quantati=findViewById(R.id.quantati_cagegory);
        save=findViewById(R.id.save_category);
        Image=findViewById(R.id.image);
        s=findViewById(R.id.spinner2);


        contatns = new ArrayList<String>();
         firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = firebaseFirestore.collection("Gategories");
        Bundle extras = getIntent().getExtras();
        String  CollectionName = extras.getString("ID");


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("5","5", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String names =name.getText().toString();
                int  prices  = Integer.parseInt(price.getText().toString());
                int Quantaties= Integer.parseInt(quantati.getText().toString());
                data=new product_data(names,prices,Quantaties,ImageURL);
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


                                        Log.d("ID collection", c.getId());
                                        subjectsRef.document(CollectionName)
                                                .collection(CollectionName)
                                                .document(data.getModel())
                                                .set(data)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Addcategory.this, "Data saved successfully :) ", Toast.LENGTH_SHORT).show();
                                                onPointerCaptureChanged(true);


                                            }
                                        });
                                    }


                                } else {

                                    Toast.makeText(Addcategory.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Addcategory.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



        subjects = new ArrayList<>();
        String compareValue = "some value";
//        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String subject = document.toObject(Gategory_data.class).getName();
//                              String subject = document.getString("Colection");
                              subjects.add(document.getId());

//                            int spinnerPosition = adapter.getPosition(subjects.get());
//                            s.setSelection(spinnerPosition);
//                            Log.d("itemid",""+spinnerPosition);


                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(Addcategory.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });



        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent gallaryintent=new Intent();
//                gallaryintent.setAction(Intent.ACTION_GET_CONTENT);
//                gallaryintent.setType("image/*");
//               startActivityForResult(gallaryintent,1);

                checkPermission();
                uploadImage();



            }
        });

    }


    private void checkPermission() {

        //use permission to READ_EXTERNAL_STORAGE For Device >= Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(Addcategory.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Addcategory.this, "permissionDenied", Toast.LENGTH_LONG).show();

                // to ask user to reade external storage
                ActivityCompat.requestPermissions(Addcategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                OpenGalleryImagePicker();
            }

            //implement code for device < Marshmallow
        } else {

            OpenGalleryImagePicker();
        }
    }
    private void OpenGalleryImagePicker() {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

                    if ( resultCode == RESULT_OK ){

                imageUri = result.getUri();

                // set image user in ImageView ;
                Image.setImageURI(imageUri);

                        uploadImage();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(Addcategory.this, "Error : " + error, Toast.LENGTH_LONG).show();

            }
        }
    }


    private void uploadImage() {

        if (firebaseAuth.getCurrentUser() != null) {

            // chick if user image is null or not
            if (imageUri != null) {

                userID = firebaseAuth.getCurrentUser().getUid();


                // mack progress bar dialog
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("uploading");
                progressDialog.setCancelable(false);
                progressDialog.show();



                     // mack collection in fireStorage
                  final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString()).child(userID + ".jpg");


                    // get image user and give to imageUserPathuserID +".jpg"
                    ref.putFile(imageUri)
                            .addOnProgressListener(taskSnapshot -> {

                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("uploaded" + (int) progress + "%");


                    }).continueWithTask(task -> {
                        if (!task.isSuccessful()) {

                            throw task.getException();

                        }
                        return ref.getDownloadUrl();

                    }).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            Uri downloadUri = task.getResult();

                          assert downloadUri != null;
                            ImageURL = downloadUri.toString();

//                     saveChange();

                        } else {

                            progressDialog.dismiss();
                            Toast.makeText(Addcategory.this, " Error in addOnCompleteListener " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                     }
                }
            }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Addcategory.this,"n");

        builder.setContentTitle(data.getModel());
        builder.setContentText("New produtc is Added");
        builder .setSmallIcon(R.drawable.not);
        builder.setAutoCancel(true);
        NotificationManagerCompat ma= NotificationManagerCompat.from(Addcategory.this);
        ma.notify(1,builder.build());
    }
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data4) {
//        super.onActivityResult(requestCode, resultCode, data4);
//        if(requestCode==1&&resultCode==RESULT_OK&& data4!=null&&data4.getData()!=null)
//        {
//            ImageUri=data4.getData();
//            Picasso.get().load(ImageUri).into(Image);
//
//
//
//        }
//
//    }

