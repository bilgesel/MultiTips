package com.multitips.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.multitips.R;
import com.multitips.entity.Coupon;
import com.multitips.entity.Match;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mDataset;
    private Context mContext;
    private Activity mActivity;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_HEADER = 2;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void add(int position, Object item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public CouponAdapter(Context context, List<Object> myDataset, RecyclerView recyclerView) {

        mContext = context;
        mActivity = (Activity)context;
        mDataset = myDataset;

        // load more
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.coupon_item, parent, false);
            return new ViewHolderRow(view);
        } else if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.list_coupon_header, parent, false);
            return new ViewHolderHeader(view);
        }else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.progressbar, parent, false);
            return new ViewHolderLoading(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderRow) {
            Match map =(Match) mDataset.get(position);
            ViewHolderRow matchViewHolder = (ViewHolderRow) holder;
            matchViewHolder.txtMatchHomeTeam.setText(map.getHome());
            matchViewHolder.txtMatchVisitorTeam.setText(map.getVisitor());
            //matchViewHolder.imgMatchLeagueFlag
            Glide.with(mContext).load(map.getStatus()).into(matchViewHolder.imgMatchStatusIcon);
            matchViewHolder.txtMatchBet.setText(map.getBet());
            matchViewHolder.txtMatchDate.setText(map.getDate());
            matchViewHolder.txtMatchLeagueName.setText(map.getLeague());
            matchViewHolder.txtMatchTime.setText(map.getTime());
            matchViewHolder.txtMatchHomeScore.setText(map.getHomeScore());
            matchViewHolder.txtMatchVisitorScore.setText(map.getVisitorScore());
            matchViewHolder.txtMatchRate.setText(map.getRate());
            String matchTeams = map.getHome()+" - "+map.getVisitor();
            matchViewHolder.txtMatchTeams.setText(matchTeams);
            String matchScore = map.getHomeScore()+" - "+map.getVisitorScore();
            matchViewHolder.txtMatchScore.setText(matchScore);
        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            //loadingViewHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof ViewHolderHeader) {
            ViewHolderHeader header = (ViewHolderHeader) holder;
            Coupon map = (Coupon) mDataset.get(position);
            header.txtCouponHeader.setText(map.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position) instanceof Match){
            return VIEW_TYPE_ITEM;
        }else if(mDataset.get(position) instanceof Coupon){
            return  VIEW_TYPE_HEADER;
        }
        else{
            return VIEW_TYPE_LOADING;
        }
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
    public class ViewHolderHeader extends RecyclerView.ViewHolder {
        public TextView txtCouponHeader;
        public ViewHolderHeader(View v) {
            super(v);
            txtCouponHeader = (TextView)v.findViewById(R.id.txtCouponHeader);
        }
    }
    public class ViewHolderRow extends RecyclerView.ViewHolder {
        public ImageView imgMatchLeagueFlag;
        public TextView txtMatchTime;
        public TextView txtMatchLeagueName;
        public ImageView imgMatchStatusIcon;
        public TextView txtMatchHomeTeam;
        public TextView txtMatchVisitorTeam;
        public TextView txtMatchHomeScore;
        public TextView txtMatchVisitorScore;
        public TextView txtMatchRate;
        public TextView txtMatchBet;
        public TextView txtMatchDate;
        public TextView txtMatchTeams;
        public TextView txtMatchScore;

        public ViewHolderRow(View v) {
            super(v);
            txtMatchDate = (TextView)v.findViewById(R.id.txtMatchDate);
            txtMatchBet = (TextView)v.findViewById(R.id.txtMatchBet);
            txtMatchRate = (TextView)v.findViewById(R.id.txtMatchRate);
            txtMatchVisitorScore = (TextView)v.findViewById(R.id.txtMatchVisitorScore);
            txtMatchHomeScore = (TextView)v.findViewById(R.id.txtMatchHomeScore);
            txtMatchVisitorTeam = (TextView)v.findViewById(R.id.txtMatchVisitorTeam);
            txtMatchHomeTeam = (TextView)v.findViewById(R.id.txtMatchHomeTeam);
            txtMatchLeagueName = (TextView)v.findViewById(R.id.txtMatchLeagueName);
            txtMatchTime = (TextView)v.findViewById(R.id.txtMatchTime);
            imgMatchStatusIcon = (ImageView)v.findViewById(R.id.imgMatchStatusIcon);
            //imgMatchLeagueFlag = (ImageView)v.findViewById(R.id.imgMatchLeagueFlag);
            txtMatchTeams=(TextView)v.findViewById(R.id.txtMatchTeams);
            txtMatchScore=(TextView)v.findViewById(R.id.txtMatchScore);
        }
    }
}
