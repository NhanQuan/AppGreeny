package fpt.aptech.vegetableorganic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpt.aptech.vegetableorganic.R;
import fpt.aptech.vegetableorganic.model.Category;
import fpt.aptech.vegetableorganic.model.Menu;

public class LoaiSpAdapter extends BaseAdapter {
    List<Menu>array;
    Context context;
    public LoaiSpAdapter(List<Menu> array, Context context) {
        this.array = array;
        this.context = context;
    }
    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class viewHolder{
        TextView texttensp;
        ImageView imghinhanh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder viewHolder=null;
        if(view ==null){
            viewHolder =new viewHolder();
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.item_sanpham,null);
            viewHolder.texttensp=view.findViewById(R.id.item_tensp);
            viewHolder.imghinhanh=view.findViewById((R.id.item_image));
            view.setTag(viewHolder);
        }else{
            viewHolder=(viewHolder) view.getTag();

        }
        viewHolder.texttensp.setText(array.get(i).getMenu_name());
        Glide.with(context).load(array.get(i).getMenu_image()).into(viewHolder.imghinhanh);
        return view;
    }
}
