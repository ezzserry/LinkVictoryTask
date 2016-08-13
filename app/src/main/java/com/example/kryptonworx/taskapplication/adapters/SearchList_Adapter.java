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

import com.bumptech.glide.Glide;
import com.example.kryptonworx.taskapplication.helpers.Constants;
import com.example.kryptonworx.taskapplication.interfaces.OnPhotoClickListener;
import com.example.kryptonworx.taskapplication.models.PhotoItem;
import com.example.kryptonworx.taskapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchList_Adapter extends RecyclerView.Adapter<SearchList_Adapter.CustomViewHolder> {

    private List<PhotoItem> photoItemList;
    private Context mContext;
    private String imgBaseUrl;
    private PhotoItem photoItem;

    OnPhotoClickListener mListener;

    public SearchList_Adapter(Context context, List<PhotoItem> photoItemList) {
        this.photoItemList = photoItemList;
        this.mContext = context;
        this.imgBaseUrl = Constants.Img_Base_URL;

        mListener = (OnPhotoClickListener) context;
    }


    @Override
    public SearchList_Adapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchList_Adapter.CustomViewHolder holder, int position) {
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
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onPhotoClick(photoItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private RelativeLayout rlContainer;
        private TextView txtPhotoTitle;
        private ProgressBar progressBar;

        public CustomViewHolder(View view) {
            super(view);
            imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
            txtPhotoTitle = (TextView) view.findViewById(R.id.txtPhotoTitle);
            rlContainer = (RelativeLayout) view.findViewById(R.id.rlContainer);
            this.progressBar = (ProgressBar) view.findViewById(R.id.loadingpanel);

        }
    }


}
