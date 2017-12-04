package lc.com.myhandler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lc.com.myhandler.ImageLoader;
import lc.com.myhandler.NewsBean;
import lc.com.myhandler.R;

/**
 * Created by LiangCheng on 2017/12/4.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    List<NewsBean> list = new ArrayList<>();
    private Context context;
    private ImageLoader imageLoader;

    public ListViewAdapter(Context context, List<NewsBean> list) {
        this.context = context;
        this.list = list;
        imageLoader = new ImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_listview, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iv.setImageResource(R.mipmap.ic_launcher_round);
        holder.iv.setTag(list.get(position).getUrl());
        imageLoader.showImageByAsyncTask(holder.iv, list.get(position).getUrl());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_content.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_title;
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
