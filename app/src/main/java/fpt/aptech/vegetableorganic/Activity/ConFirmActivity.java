package fpt.aptech.vegetableorganic.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.signinRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConFirmActivity extends AppCompatActivity {
    Button btnConfirm;
    EditText edOtp;
    signinRequest signRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_firm);
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            signRequest = (signinRequest) intent.getSerializableExtra("data");
          }
    btnConfirm = findViewById(R.id.btnConfirm);
   edOtp = findViewById(R.id.edOtp);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edOtp.getText().toString()) ) {
                    String message = "All inputs required ..";
                    Toast.makeText(ConFirmActivity.this,message,Toast.LENGTH_LONG).show();
                }else{
                        int otp = Integer.parseInt(edOtp.getText().toString());
                        signinRequest sinRequest = new signinRequest();
                        sinRequest.setName(signRequest.getName());
                        sinRequest.setEmail(signRequest.getEmail());
                        sinRequest.setPassword(signRequest.getPassword());
                        sinRequest.setAvatar(signRequest.getAvatar());
                        signIn(sinRequest,otp);

                }

            }
        });

    }

    public void signIn(signinRequest sinRequest, int otp){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);
        Call<signinRequest> loginResponseCall = interfaceApi.signupConfim(sinRequest,otp);
        loginResponseCall.enqueue(new Callback<signinRequest>() {
            @Override
            public void onResponse(Call<signinRequest> call, Response<signinRequest> response) {
//                Log.e(TAG,"onReponse: " + response.code());
//                Log.e(TAG,"onReponse:" +response.body());

                if(response.isSuccessful()){

                    String message = "Register Success";
                    Toast.makeText(ConFirmActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ConFirmActivity.this,LoginActivity.class));

                }else{

                    String message = "Otp invaild";
                    Toast.makeText(ConFirmActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<signinRequest> call, Throwable t) {

                String message = "Otp invaild";
                Toast.makeText(ConFirmActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}