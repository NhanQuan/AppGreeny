package fpt.aptech.vegetableorganic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.GioHang;
import fpt.aptech.vegetableorganic.model.Product;
import fpt.aptech.vegetableorganic.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp,giasp,mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    Product product;
    List<Product> array;
    NotificationBadge badge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();

        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product=(Product) getIntent().getSerializableExtra("chitiet");
                int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                addCart(product.getProduct_id(),soluong);
                themgiohang();
            }
        });
    }

    public void addCart(int id, int soluong){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<Product> addProduct = interfaceApi.addCart(id, soluong);
        addProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){

                    Toast.makeText(ChiTietActivity.this,"Add to cart success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                String message = "Invalid";
                Toast.makeText(ChiTietActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void themgiohang() {
        if(Utils.manggiohang.size()>0){
            boolean flag=false;
            int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i=0;i<Utils.manggiohang.size();i++){
                if(Utils.manggiohang.get(i).getIdsp()==product.getProduct_id()){
                    Utils.manggiohang.get(i).setSoluong(soluong+Utils.manggiohang.get(i).getSoluong());
                    long gia=Long.parseLong(product.getPrice())*Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag=true;
                }
            }if(flag==false){
                long gia=Long.parseLong(product.getPrice())*soluong;
                GioHang gioHang=new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(product.getProduct_id());
                gioHang.setTensp(product.getProduct_name());
                gioHang.setHinhsp(product.getProduct_image());
                Utils.manggiohang.add(gioHang);
            }

        }else {
            int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
            long gia=Long.parseLong(product.getPrice())*soluong;
            GioHang gioHang=new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(product.getProduct_id());
            gioHang.setTensp(product.getProduct_name());
            gioHang.setHinhsp(product.getProduct_image());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            totalItem=totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        product=(Product) getIntent().getSerializableExtra("chitiet");
        tensp.setText(product.getProduct_name());
        mota.setText(product.getDescription());
//        Glide.with(getApplicationContext()).load(product.getProduct_image()).into(imghinhanh);
        Glide.with(getApplicationContext()).load(Utils.ipLoadImage+product.getProduct_image()).into(imghinhanh);
//        Glide.with(context).load(Utils.ipLoadImage + array.get(position).getProduct_image()).into(myViewHolder.hinhanh);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        giasp.setText("Gía : "+decimalFormat.format(Double.parseDouble(product.getPrice()))+"Đ");
        Integer[]so=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter <Integer>adapterspin=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }
    private void initView() {
        tensp=findViewById(R.id.txttensp);
        giasp=findViewById(R.id.txtgiasp);
        mota=findViewById(R.id.txtmotachitiet);
        btnthem=findViewById(R.id.btnthemvaogiohang);
        spinner=findViewById(R.id.spinner);
        imghinhanh=findViewById(R.id.imgchitiet);
        toolbar=findViewById(R.id.toolbar);
        array=new ArrayList<>();

        badge=findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang=findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if(Utils.manggiohang!=null){
            int totalItem=0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem=totalItem+Utils.manggiohang.get(i).getSoluong();
            }
//            badge.setText(String.valueOf(Utils.manggiohang.size()));
            badge.setText(String.valueOf(totalItem));
        }

    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang!=null){
            int totalItem=0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem=totalItem+Utils.manggiohang.get(i).getSoluong();
            }
//            badge.setText(String.valueOf(Utils.manggiohang.size()));
            badge.setText(String.valueOf(totalItem));
        }
    }
}