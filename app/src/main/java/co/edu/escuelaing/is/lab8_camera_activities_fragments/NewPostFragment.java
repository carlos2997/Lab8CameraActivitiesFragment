package co.edu.escuelaing.is.lab8_camera_activities_fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class NewPostFragment extends Fragment implements View.OnClickListener{

    private EditText edtMessage;
    private Button btnPhoto;
    private ImageView imgPhoto;
    private Uri selectedImageUri;
    private String[] photosCaptured = {"Camera", "Internal Storage"};

    private DialogInterface.OnClickListener onClickImageControl;

    private static final int SELECT_FILE_CAMERA = 0;
    private static final int SELECT_FILE_INTERNAL = 1;
    public static final String EXTRA_MESSAGE_MESSAGE = "co.edu.escuelaing.is.lab8.MESSAGE";
    public static final String EXTRA_MESSAGE_PICTURE = "co.edu.escuelaing.is.lab8.PICTURE";

    @NonNull
    public static Dialog createSingleChoiceAlertDialog(@NonNull Context context,
                                                       @Nullable String title,
                                                       @NonNull CharSequence[] items,
                                                       @NonNull DialogInterface.OnClickListener optionSelectedListener,
                                                       @Nullable DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items,optionSelectedListener);
        if(cancelListener != null){
            builder.setNegativeButton(R.string.Cancel,cancelListener);
        }
        return builder.create();

    }

    private void grantPermissions(){
        int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
        }
        int permission2 = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        grantPermissions();
        return inflater.inflate(R.layout.fragment_new_post,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        edtMessage = view.findViewById(R.id.edtMessage);
        btnPhoto = view.findViewById(R.id.btnPhoto);

        imgPhoto = view.findViewById(R.id.imgPhoto);

        onClickImageControl = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                if(photosCaptured[i].equals("Camera")){
                    int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
                    if(permission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                        Toast.makeText(getContext(),"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
                    }else{
                        int permission2 = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        if(permission2 != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                            Toast.makeText(getContext(),"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
                        }else{
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File photo = new File(Environment.getExternalStorageDirectory(),"Pic.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
                            selectedImageUri = Uri.fromFile(photo);
                            startActivityForResult(intent,SELECT_FILE_CAMERA);
                        }
                    }
                }else{
                    int permission2 = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(permission2 != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        Toast.makeText(getContext(),"You have to grant permissions, intent again!!",Toast.LENGTH_SHORT).show();
                    }else{
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select picture from internal storage"),SELECT_FILE_INTERNAL);
                    }
                }
            }
        };

        btnPhoto.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case SELECT_FILE_CAMERA:
                    imgPhoto.setImageURI(selectedImageUri);
                    break;
                case SELECT_FILE_INTERNAL:
                    selectedImageUri = data.getData();
                    imgPhoto.setImageURI(selectedImageUri);
                    break;
            }
        }
    }

    public Bundle getDataBundle(){
        if(edtMessage.getText().toString().length()==0 || imgPhoto.getDrawable() == null){
            Toast.makeText(getContext(),"Please enter either a message or select an image",Toast.LENGTH_SHORT).show();
        }else{
            if(edtMessage.getText().toString().length()<50){
                edtMessage.setError("The text field should have a lenght longer than 50 characters");
            }else{
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_MESSAGE_MESSAGE,edtMessage.getText().toString());
                bundle.putString(EXTRA_MESSAGE_PICTURE,selectedImageUri.toString());
                return bundle;
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnPhoto.getId()){
            createSingleChoiceAlertDialog(getContext(),"Select picture captured method",photosCaptured,onClickImageControl,null).show();
        }
    }

}
