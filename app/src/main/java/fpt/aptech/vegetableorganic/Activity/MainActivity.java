package fpt.aptech.vegetableorganic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.adapter.LoaiSpAdapter;
import fpt.aptech.vegetableorganic.adapter.SanPhamMoiAdapter;
import fpt.aptech.vegetableorganic.model.Menu;
import fpt.aptech.vegetableorganic.model.Product;
import fpt.aptech.vegetableorganic.retrofit.ApiBanHang;
import fpt.aptech.vegetableorganic.retrofit.RetrofitClient;
import fpt.aptech.vegetableorganic.utils.Utils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarHomePage;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHomePage;
    NavigationView navigationView;
    ListView listviewHomePage;
    LoaiSpAdapter loaiSpAdapter;
    List<Menu>mangloaisp;
    DrawerLayout drawerLayout;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<Product>mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imagesearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();

        if(isConnected(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSpMoi();
            getEvenClik();
        }else {
            Toast.makeText(getApplicationContext(), "ko co internet, vui lòng kết cối", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEvenClik() {
        listviewHomePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent trangchu=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent rau=new Intent(getApplicationContext(),RauActivity.class);
                        rau.putExtra("menu_id",4);
                        startActivity(rau);
                        break;
                    case 2:
                        Intent qua=new Intent(getApplicationContext(),QuaActivity.class);
                        qua.putExtra("menu_id",5);
                        startActivity(qua);
                        break;
                    case 3:
                        Intent cu=new Intent(getApplicationContext(), TraicayActivity.class);
                        cu.putExtra("menu_id",6);
                        startActivity(cu);
                        break;
                    case 4:
                        Intent lienhe=new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(lienhe);
                        break;
                    case 5:
                        Intent info=new Intent(getApplicationContext(),InfomationActivity.class);
                        startActivity(info);
                        break;
                    case 6:
                      Intent lichsu=new Intent(getApplicationContext(),XemDonActivity.class);
                       // Intent lichsu=new Intent(getApplicationContext(),DonHangChiTiet.class);
                        startActivity(lichsu);
                        break;
                }
            }
        });
    }


    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                mangSpMoi=productModel.getResult();
                                spAdapter=new SanPhamMoiAdapter(getApplicationContext(),mangSpMoi);
                                recyclerViewHomePage.setAdapter(spAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối dc với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        menuModel->{
                            if(menuModel.isSuccess()){
                                mangloaisp=menuModel.getResult();
                                // khoi tao adapter
                                loaiSpAdapter=new LoaiSpAdapter(mangloaisp, getApplicationContext());
                                listviewHomePage.setAdapter(loaiSpAdapter);
                            }
                        }
                        ));
    }

    private void Anhxa(){
        imagesearch=findViewById(R.id.imgsearch);
        toolbarHomePage=findViewById(R.id.toolbarhomepage);
        viewFlipper=findViewById(R.id.viewflipper);

        recyclerViewHomePage=findViewById(R.id.recycleviewhomepage);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerViewHomePage.setLayoutManager(layoutManager);
        recyclerViewHomePage.setHasFixedSize(true);

        badge=findViewById(R.id.menu_sl);
        frameLayout=findViewById(R.id.framegiohang);
        navigationView=findViewById(R.id.navigationview);
        listviewHomePage=findViewById(R.id.listviewhomepage);
        drawerLayout=findViewById(R.id.drawerLayout);
        // khoi tao list
        mangloaisp=new ArrayList<>();
        mangSpMoi=new ArrayList<>();

        if(Utils.manggiohang==null){
            Utils.manggiohang=new ArrayList<>();
        }else {
            int totalItem=0;
            for(int i=0;i<Utils.manggiohang.size();i++){
                totalItem=totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            totalItem=totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void ActionViewFlipper() {
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }


    private void ActionBar() {
        setSupportActionBar(toolbarHomePage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarHomePage.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarHomePage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi!=null && wifi.isConnected()) || (mobile !=null && mobile.isConnected())){
            return true;
        }else {
            return  false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}