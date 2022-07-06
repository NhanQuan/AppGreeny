package fpt.aptech.vegetableorganic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.adapter.RauAdapter;
import fpt.aptech.vegetableorganic.adapter.TraiCayAdapter;
import fpt.aptech.vegetableorganic.model.Product;
import fpt.aptech.vegetableorganic.retrofit.ApiBanHang;
import fpt.aptech.vegetableorganic.retrofit.RetrofitClient;
import fpt.aptech.vegetableorganic.utils.Utils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuaActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    int page=1;
    int category_id;
    TraiCayAdapter adapterQua;
    List<Product>sanPhamList;
    LinearLayoutManager linearLayoutManager;
    Handler handler=new Handler();
    boolean isLoading=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qua);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        category_id=getIntent().getIntExtra("category_id",5);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading==false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition()==sanPhamList.size()-1){
                        isLoading=true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // add null
                sanPhamList.add(null);
                adapterQua.notifyItemInserted(sanPhamList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remover null
                sanPhamList.remove(sanPhamList.size()-1);
                adapterQua.notifyItemRemoved(sanPhamList.size());
                page=page+1;
                getData(page);
                adapterQua.notifyDataSetChanged();
                isLoading=false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanPham(page,category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                if(adapterQua==null){
                                    sanPhamList=productModel.getResult();
                                    adapterQua=new TraiCayAdapter(getApplicationContext(),sanPhamList);
                                    recyclerView.setAdapter(adapterQua);
                                }else {
                                    int vitri=sanPhamList.size()-1;
                                    int soluongadd=productModel.getResult().size();
                                    for(int i=0;i<soluongadd;i++){
                                        sanPhamList.add(productModel.getResult().get(i));
                                    }
                                    adapterQua.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Hết Dữ Liệu", Toast.LENGTH_LONG).show();
                                isLoading=true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối server", Toast.LENGTH_LONG).show();
                        }
                ));
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

    private void AnhXa() {
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycleview_traicay);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamList=new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}