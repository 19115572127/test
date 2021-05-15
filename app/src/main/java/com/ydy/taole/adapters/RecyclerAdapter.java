package com.ydy.taole.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ydy.taole.R;

import java.util.List;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VH> {
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recycleradpter,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        //加载图片和文字
        Map<String,Object> map = list.get(position);
        holder.image.setImageResource((Integer)map.get("image"));

        if(mItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     *  定义点击接口  利用接口 -> 给RecyclerView设置点击事件
     */
    private ItemClickListener mItemClickListener ;

    public interface ItemClickListener{
        void onItemClick(View view,int position) ;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;
    }

    private List<Map<String,Object>> list;
    private Context context;
    /**
     * 通过构造方法获取数据
     * @param context 上下文
     * @param list 数据源
     */
    public RecyclerAdapter(Context context, List<Map<String, Object>> list){
        this.context = context;
        this.list = list;
    }
    public class VH extends RecyclerView.ViewHolder{
        private ImageView image;
        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgs);
        }
    }
}
