package com.example.kryptonworx.taskapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kryptonworx.taskapplication.helpers.Constants;
import com.example.kryptonworx.taskapplication.models.PhotoItem;
import com.example.kryptonworx.taskapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Details_Adapter extends RecyclerView.Adapter<Details_Adapter.MyCustomViewHolder> {


    private List<PhotoItem> photoItemList;
    private Context mContext;
    private String imgBaseUrl;
    private PhotoItem photoItem;

    public Details_Adapter(Context context, List<PhotoItem> photoItemList) {
        this.photoItemList = photoItemList;
        this.mContext = context;
        this.imgBaseUrl = Constants.Img_Base_URL;

    }

    @Override
    public MyCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        return new MyCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCustomViewHolder holder, int position) {
        photoItem = photoItemList.get(position);

        String imgUrl = String.format(imgBaseUrl, photoItem.getFarm(), photoItem.getServer(), photoItem.getId(), photoItem.getSecret());
        //Download image using picasso library
        Picasso.with(mContext)
                .load(imgUrl)
                .into(holder.imgPhoto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imgPhoto.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holder.imgPhoto.setVisibility(View.VISIBLE);
                        holder.imgPhoto.setImageResource(R.drawable.placeholder);
                        holder.progressBar.setVisibility(View.GONE);

                    }
                });

        //Setting text view title
        holder.txtPhotoTitle.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }

    public class MyCustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private TextView txtPhotoTitle;
        private ProgressBar progressBar;

        public MyCustomViewHolder(View view) {
            super(view);
            this.imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
            this.txtPhotoTitle = (TextView) view.findViewById(R.id.txtPhotoTitle);
            this.progressBar = (ProgressBar) view.findViewById(R.id.loadingpanel);
        }
    }
}
