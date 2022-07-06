package fpt.aptech.vegetableorganic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.EventBus.TinhTongEvent;
import fpt.aptech.vegetableorganic.model.LoginRequest;
import fpt.aptech.vegetableorganic.model.Product;
import fpt.aptech.vegetableorganic.model.User;
import fpt.aptech.vegetableorganic.model.signinRequest;
import fpt.aptech.vegetableorganic.model.userCheckout;
import fpt.aptech.vegetableorganic.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien,txtsodt,txtemail;
    EditText editdiachi, editsdt;
    AppCompatButton btndathang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        long tongtien=getIntent().getLongExtra("tongtien",0);
        //secsion
        User user = new User();
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user",user.getEmail());
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(email);
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi=editdiachi.getText().toString().trim();
                String sdt = editsdt.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập đại chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    userCheckout userCheckout1 = new userCheckout();
                    userCheckout1.setAddress(str_diachi);
                    userCheckout1.setPhone(sdt);
                    addCart(userCheckout1,email);

                    //post data
                    // Log.d("test",new Gson().toJson(Utils.manggiohang));
                }
            }
        });
    }

    private void initView() {
        toolbar=findViewById(R.id.toolbar);
        txttongtien=findViewById(R.id.txttongtien);
        txtemail=findViewById(R.id.txtemail);

        editdiachi=findViewById(R.id.editdiachi);
        editsdt=findViewById(R.id.editsdt);
        btndathang=findViewById(R.id.btndathang);
    }
    public void addCart(userCheckout userCheckout1, String email){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<userCheckout> addProduct = interfaceApi.checkout(userCheckout1,email);
        addProduct.enqueue(new Callback<userCheckout>() {
            @Override
            public void onResponse(Call<userCheckout> call, Response<userCheckout> response) {
                if(response.isSuccessful()){

                    Toast.makeText(ThanhToanActivity.this,"Dat hang thanh cong",Toast.LENGTH_LONG).show();
                    Utils.manggiohang.clear();

                    EventBus.getDefault().postSticky(new TinhTongEvent());
                    startActivity(new Intent(ThanhToanActivity.this,MainActivity.class));

                }
            }

            @Override
            public void onFailure(Call<userCheckout> call, Throwable t) {

                String message = "Invalid";
                Toast.makeText(ThanhToanActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}