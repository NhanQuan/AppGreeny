package fpt.aptech.vegetableorganic.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.CartItem;
import fpt.aptech.vegetableorganic.model.LoginRequest;
import fpt.aptech.vegetableorganic.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView txtdangki,txtdangki1;
    Button btnLogin;
    EditText edUsername, edPassword;
    Spinner spinner;
    public static final String[]languages={"Select Language","English","French","Hàn","Việt Nam"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        edUsername = findViewById(R.id.et_email);
        edPassword = findViewById(R.id.et_password);
        txtdangki=findViewById(R.id.txtdangki);
        txtdangki1=findViewById(R.id.txtdangki1);
        spinner=findViewById(R.id.spinner1);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang=parent.getItemAtPosition(position).toString();
                if(selectedLang.equals("English")){
                    setLocale(LoginActivity.this,"en");
                    finish();
                    startActivity(getIntent());
                }else if(selectedLang.equals("French")){
                    setLocale(LoginActivity.this,"fr");
                    finish();
                    startActivity(getIntent());
                }else if(selectedLang.equals("Hàn")){
                    setLocale(LoginActivity.this,"han");
                    finish();
                    startActivity(getIntent());
                }else if(selectedLang.equals("Việt Nam")){
                    setLocale(LoginActivity.this,"vn");
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtdangki1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ) {
                    String message = "All inputs required ..";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }else{

                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setNameOrEmail(edUsername.getText().toString());
                    loginRequest.setPassword(edPassword.getText().toString());
                    loginUser(loginRequest);

                }

            }
        });

    }
    public void setLocale(Activity activity,String langCode){
        Locale locale=new Locale(langCode);
        locale.setDefault(locale);
        Resources resources=activity.getResources();
        Configuration config =resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }


    public void loginUser(LoginRequest loginRequest){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<User> loginResponseCall = interfaceApi.loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                Log.e(TAG,"onReponse: " + response.code());
//                Log.e(TAG,"onReponse:" +response.body());

                if(response.isSuccessful()){
                    clearCart();
                    User loginRequest = response.body();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user",edUsername.getText().toString());
                    // loginRequest.setUser_id(15);
                    editor.putInt("user_id",loginRequest.getUserId());
                    editor.commit();
                }else{

                    String message = "User Name or Password Invalid";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                String message = "User Name or Password Invalid";
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clearCart(){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<CartItem> loginResponseCall = interfaceApi.clear();
        loginResponseCall.enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {

            }
        });
    }
}