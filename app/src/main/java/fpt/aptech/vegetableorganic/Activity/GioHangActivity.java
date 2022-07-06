package fpt.aptech.vegetableorganic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.adapter.GioHangAdapter;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.EventBus.TinhTongEvent;
import fpt.aptech.vegetableorganic.model.GioHang;
import fpt.aptech.vegetableorganic.model.Product;
import fpt.aptech.vegetableorganic.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {
    TextView giohangtrong,tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    long tongtiensp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhTongTien();
    }

    private void tinhTongTien() {
        tongtiensp=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            tongtiensp=tongtiensp+(Utils.manggiohang.get(i).getGiasp()*Utils.manggiohang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));
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
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size()==0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else {
            adapter=new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Product product1 = new Product();
//                SharedPreferences sharedPreferences = getSharedPreferences("Quantity", Context.MODE_PRIVATE);
//                int quantity = sharedPreferences.getInt("quantity",product1.getQuantity());
//
//                addCart(product.getProduct_id(), soluong);

                Intent intent=new Intent(getApplicationContext(),ThanhToanActivity.class);
                intent.putExtra("tongtien",tongtiensp);
                startActivity(intent);
            }
        });

    }
    public void addCart(int id, int quantity){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<Product> addProduct = interfaceApi.addCart(id, quantity);
        addProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){

                   // Toast.makeText(GioHangActivity.this,"Add to cart success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                String message = "Invalid";
                Toast.makeText(GioHangActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }   public void removeCart(int id){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<Product> addProduct = interfaceApi.removeCart(id);
        addProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){

                    // Toast.makeText(GioHangActivity.this,"Add to cart success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                String message = "Invalid";
                Toast.makeText(GioHangActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initView() {
        giohangtrong=findViewById(R.id.txtgiohangtrong);
        tongtien=findViewById(R.id.txttongtien);
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycleviewgiohang);
        btnmuahang=findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if(event!=null){
            tinhTongTien();
        }
    }
}