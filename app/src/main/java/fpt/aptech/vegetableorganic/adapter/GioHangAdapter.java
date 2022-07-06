package fpt.aptech.vegetableorganic.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import fpt.aptech.vegetableorganic.Activity.GioHangActivity;
import fpt.aptech.vegetableorganic.Interface.IImageClikListener;
import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.api.ApiClient;
import fpt.aptech.vegetableorganic.api.InterfaceApi;
import fpt.aptech.vegetableorganic.model.EventBus.TinhTongEvent;
import fpt.aptech.vegetableorganic.model.GioHang;
import fpt.aptech.vegetableorganic.model.Product;
import fpt.aptech.vegetableorganic.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang=gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+"");
        SharedPreferences sharedPreferences = context.getSharedPreferences("Quantity", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("quantity",gioHangList.get(position).getSoluong());
        editor.commit();
//        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        Glide.with(context).load(Utils.ipLoadImage + gioHangList.get(position).getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia=gioHang.getSoluong()*gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.setListener(new IImageClikListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG","onImageClick: "+pos+"..."+giatri);
                if(giatri==1){
                    if(gioHangList.get(pos).getSoluong()>1){
                        int soluongmoi=gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+"");

//                        SharedPreferences sharedPreferences = context.getSharedPreferences("Quantity", context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("quantity",gioHangList.get(pos).getSoluong());
//                        editor.commit();

                        long gia=gioHangList.get(pos).getSoluong()*gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        GioHangActivity gioHangActivity = new GioHangActivity();
                        gioHangActivity.removeCart(gioHangList.get(pos).getIdsp());
                        gioHangActivity.addCart(gioHangList.get(pos).getIdsp(), gioHangList.get(pos).getSoluong());
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if(gioHangList.get(pos).getSoluong()==1){
                        AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());

                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    }
                }else if(giatri==2){
                    if(gioHangList.get(pos).getSoluong()<11){
                        int soluongmoi=gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                        GioHangActivity gioHangActivity = new GioHangActivity();
                        gioHangActivity.removeCart(gioHangList.get(pos).getIdsp());
                        gioHangActivity.addCart(gioHangList.get(pos).getIdsp(), soluongmoi);
                    }
//                    SharedPreferences sharedPreferences = context.getSharedPreferences("Quantity", context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putInt("quantity",gioHangList.get(pos).getSoluong());
//                    editor.commit();

                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+"");
                    long gia=gioHangList.get(pos).getSoluong()*gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image,imgtru,imgcong;
        TextView item_giohang_tensp,item_giohang_gia,item_giohang_soluong,item_giohang_giasp2;
        IImageClikListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image=itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp=itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia=itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong=itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2=itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru=itemView.findViewById(R.id.item_giohang_tru);
            imgcong=itemView.findViewById(R.id.item_giohang_cong);
            // event clik
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setListener(IImageClikListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(view==imgtru){
                listener.onImageClick(view,getAbsoluteAdapterPosition(),1);
                // 1 tru
            }else if(view ==imgcong){
                //2 cong
                listener.onImageClick(view,getAbsoluteAdapterPosition(),2);
            }
        }
    }
}
