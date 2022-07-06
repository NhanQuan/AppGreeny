package fpt.aptech.vegetableorganic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.model.Item;
import fpt.aptech.vegetableorganic.model.OrderDetail;
import fpt.aptech.vegetableorganic.utils.Utils;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyViewHolder> {
    Context context;
    List<Item>itemList;

    public ChitietAdapter(Context context, List<Item> itemList) {
        super();
        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Item item = itemList.get(position);


        holder.txtsoluong.setText(item.getQuantitydetail()+"");

        holder.txtten.setText(item.getProduct_name()+"");

            //holder.txtsoluong.setText(item.getList().get(0).getQuantity()+"");

        Glide.with(context).load(Utils.ipLoadImage + itemList.get(position).getProduct_image()).into(holder.imagechitiet);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imagechitiet;
        TextView txtten,txtsoluong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet=itemView.findViewById(R.id.item_imgchitiet);
            txtten=itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong=itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
