package co.edu.escuelaing.is.lab8_camera_activities_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;

    public void showFragment(Fragment fragment, boolean addToBackStack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        String tag = fragment.getClass().getSimpleName();
        if(addToBackStack){
            transaction.addToBackStack(tag);
        }

        transaction.replace(R.id.newPostFrag,fragment,tag);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment newpostfrag = new NewPostFragment();
        showFragment(newpostfrag,true);
        button = findViewById(R.id.btnSave);
        button.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed(){
        button.setVisibility(View.VISIBLE);
        Fragment newpostfrag = new NewPostFragment();
        showFragment(newpostfrag,true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == button.getId()){
            FragmentManager fragmentManager = getFragmentManager();
            Fragment actual = fragmentManager.findFragmentById(R.id.newPostFrag);
            if(actual instanceof NewPostFragment){
                Bundle bundle = ((NewPostFragment) actual).getDataBundle();
                if(bundle != null){
                    Fragment postFrag = new PostFragment();
                    postFrag.setArguments(bundle);
                    button.setVisibility(view.GONE);
                    showFragment(postFrag,true);
                }else{
                    Toast.makeText(this,"No fue posible realizar el cambio!!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
