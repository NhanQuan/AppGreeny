package fpt.aptech.vegetableorganic.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.signinRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btnSignUp;
    EditText edUsername, edEmail,edPassword,edCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        btnSignUp = findViewById(R.id.btnSignUp);
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edCPassword = findViewById(R.id.edCPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ||
                        TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())
                || TextUtils.isEmpty(edCPassword.getText().toString())) {
                    String message = "All inputs required ..";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }else{
                    if(edPassword.getText().toString().equals(edCPassword.getText().toString())){
                        signinRequest sinRequest = new signinRequest();
                        sinRequest.setName(edUsername.getText().toString());
                        sinRequest.setEmail(edEmail.getText().toString());
                        sinRequest.setPassword(edPassword.getText().toString());
                        signIn(sinRequest);
                    }else {
                        String message = "PassWord Invalid";
                        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

    }

    public void signIn(signinRequest sinRequest){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);
        Call<signinRequest> loginResponseCall = interfaceApi.Signin(sinRequest);
        loginResponseCall.enqueue(new Callback<signinRequest>() {
            @Override
            public void onResponse(Call<signinRequest> call, Response<signinRequest> response) {
//                Log.e(TAG,"onReponse: " + response.code());
//                Log.e(TAG,"onReponse:" +response.body());

                if(response.isSuccessful()){
                    signinRequest signinResponse = response.body();
                    startActivity(new Intent(RegisterActivity.this,ConFirmActivity.class).putExtra("data",signinResponse));
                 finish();

                }else{
                    String message = "UserName or Email exists";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<signinRequest> call, Throwable t) {

                String message = "UserName or Email ";
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}