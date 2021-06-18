package com.tripperfypactivity.tripperfyp.activiteis;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.listeners.FirebaseResponseListener;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;
import com.tripperfypactivity.tripperfyp.utilites.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements FirebaseResponseListener {


    @BindView(R.id.profileUserName)
    EditText profileUserName;
    @BindView(R.id.profieEmail)
    EditText profieEmail;
    @BindView(R.id.profieFirstName)
    EditText profieFirstName;
    @BindView(R.id.prfileLastName)
    EditText prfileLastName;
    @BindView(R.id.profilephoneno)
    EditText profilephoneno;
    User user;
    @BindView(R.id.profileUpdate)
    Button profileUpdate;
    @BindView(R.id.profile_imageview)
    CircleImageView profileImageview;

    String Url=null;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getUserListOfData();
       // String url = "https://firebasestorage.googleapis.com/v0/b/tripperfyp.appspot.com/o/mountains.jpg%2FlLM%5BEAimages%2Fmountains.jpg?alt=media&token=df284785-0947-41a6-a55a-9f66d984bf58";

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.chat_icon);
        requestOptions.error(R.drawable.home_icon);

        Glide.with(this).setDefaultRequestOptions(requestOptions).load(Url).into(profileImageview);

    }


    public void getUserListOfData() {

        DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user = new User();
                user = snapshot.getValue(User.class);
                profieEmail.setText(user.email);
                profieFirstName.setText(user.firstName);
                profileUserName.setText(user.userName);
                prfileLastName.setText(user.lastName);
                profilephoneno.setText(user.userPhonNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ss = "";
            }

        });
    }

    public void updatePfrofileData(String firstName, String lastName, String email, String userName,String userPhoneno) {


        final HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put(Constans.USER_FIRST_NAME, firstName);
        dataMap.put(Constans.USER_LAST_NAME, lastName);
        dataMap.put(Constans.USER_EMAIL, email);
        dataMap.put(Constans.USER_USER_NAME, userName);
        dataMap.put(Constans.USER_USER_PHONENO,userPhoneno);
        DatabaseReference mDatabase = Shared.getDataBaseRef(Constans.USER_TABEL).child(Shared.getUser().getUid());
       mDatabase.updateChildren(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               GlobalUtils.showToast(ProfileActivity.this, Constans.USER_UPDATE);
                startActivity(new Intent(ProfileActivity.this, Navigation_Activity.class));
           }
       });





//        mDatabase.child(Constans.USER_FIRST_NAME).setValue(firstName);
//        mDatabase.child(Constans.USER_LAST_NAME).setValue(lastName);
//        mDatabase.child(Constans.USER_USER_NAME).setValue(userName);
//        mDatabase.child("userPhoneno").setValue(userPhoneno);
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                dataSnapshot.getRef().setValue(dataMap);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        mDatabase.setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                GlobalUtils.showToast(ProfileActivity.this, Constans.USER_UPDATE);
//                startActivity(new Intent(ProfileActivity.this, Navigation_Activity.class));
//            }
//        });

    }

    @Override
    public void onSuccess(boolean isOffline) {
    }

    @Override
    public void onFailure(boolean isOffline) {

    }


    // profile pic
    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile PIC");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);
                if (items[item].equals("Camera")) {
                    userChoosenTask = "Camera";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Gallery")) {
                    userChoosenTask = "Gallery";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                saveImageFirebase(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        profileImageview.setImageBitmap(bm);
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        saveImageFirebase(thumbnail);
        profileImageview.setImageBitmap(thumbnail);
    }

    // End profile pic function
    public void saveImageFirebase(Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte[] data1 = bytes.toByteArray();


        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://tripperfyp.appspot.com")
                .child("mountains.jpg").child(random() + "images/mountains.jpg");
        storageRef.getName().equals(storageRef.getName());    // true
        storageRef.getPath().equals(storageRef.getPath());

        final UploadTask uploadTask = storageRef.putBytes(data1);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                String picture = "";
                GlobalUtils.showToast(ProfileActivity.this, "");
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Url = uri.toString();
                    }
                });
            }
        });

    }

    @OnClick({R.id.profile_imageview, R.id.profileUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_imageview:
                selectImage();
                break;
            case R.id.profileUpdate:
                updatePfrofileData(profieFirstName.getText().toString()
                        , prfileLastName.getText().toString(), profieEmail.getText().toString(), profileUserName.getText().toString(),profilephoneno.getText().toString());
                break;
        }
    }
}
