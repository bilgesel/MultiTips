package com.multitips.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multitips.R;
import com.multitips.entity.SubscriptionItem;

import java.util.HashMap;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubscriptionItem> mDataset;
    private Context mContext;
    private Activity mActivity;
    private OnItemClickListener listener;
    private String currentCategory;
    private String paymentInfoFormat="Your subscription will be renewed automatically after <b>XXX</b> unless you turn the renewal off at your Google Play payment profile. Even if you turn off the renewal you can see the content of your subscription until the end of your term.";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public interface OnItemClickListener {
        void onItemClick(SubscriptionItem item);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void add(int position, SubscriptionItem item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove( HashMap<String,String> item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public PaymentAdapter(Context context, List<SubscriptionItem> myDataset, RecyclerView recyclerView, String catName) {
        mContext = context;
        mActivity = (Activity)context;
        mDataset = myDataset;
        currentCategory = catName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.vip_row_item, parent, false);
            return new ViewHolderRow(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.progressbar, parent, false);
            return new ViewHolderLoading(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderRow) {
            SubscriptionItem map = mDataset.get(position);
            ViewHolderRow userViewHolder = (ViewHolderRow) holder;
            //userViewHolder.txtCatName.setText(currentCategory);
            userViewHolder.txtMenu.setText(map.getName().trim());
            userViewHolder.txtPrice.setText(map.getPrice().trim());
            String paymentInfo = paymentInfoFormat.replace("XXX",map.getName());
            //userViewHolder.txtDescription.setText(Html.fromHtml(paymentInfo));
            try {
                int resID = R.drawable.class.getField("icon_vip").getInt(null);
                userViewHolder.imgMenuIconLeft.setImageResource(resID);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            userViewHolder.bind(mDataset.get(position), listener);
        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class ViewHolderLoading extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ViewHolderLoading(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.itemProgressbar);
        }
    }

    public class ViewHolderRow extends RecyclerView.ViewHolder {
        public TextView txtMenu;
        public TextView txtPrice;
        public TextView txtCatName;
        public TextView txtDescription;
        public ImageView imgMenuIconLeft;
        public ViewHolderRow(View v) {
            super(v);
            txtMenu = (TextView)v.findViewById(R.id.txtMenuName);
            txtPrice = (TextView)v.findViewById(R.id.txtPrice);
            //txtCatName =(TextView)v.findViewById(R.id.txtCatName);
            //txtDescription=(TextView)v.findViewById(R.id.txtDescription);
            imgMenuIconLeft = (ImageView)v.findViewById(R.id.imgMenuIconLeft);
        }

        public void bind(final SubscriptionItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}