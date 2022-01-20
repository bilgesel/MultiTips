package com.multitips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.multitips.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multitips.adapter.CouponAdapter;
import com.multitips.adapter.PaymentAdapter;
import com.multitips.api.ApiClient;
import com.multitips.api.ApiInterface;
import com.multitips.entity.BaseResult;
import com.multitips.entity.Category;
import com.multitips.entity.Coupon;
import com.multitips.entity.SubscriptionItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    public String selectedCategory = "";
    private TextView txtNotification;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewWinner;
    List<Coupon> couponDataSource;
    PaymentAdapter mAdapter;
    CouponAdapter mAdapterCoupon;
    ProgressDialog progressDialog;
    SubscriptionItem selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        FloatingActionButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerViewWinner = findViewById(R.id.recyclerViewWinner);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedCategory = extras.getString("selectedCategory");
            setTitle(selectedCategory);

            TextView txtCategory = findViewById(R.id.txtCategory);
            txtCategory.setText(selectedCategory);

            Object[] dataSource =(Object[]) extras.getSerializable("dataSource");
            ArrayList<SubscriptionItem> data = new ArrayList<>(dataSource.length);
            for (int i = 0; i <dataSource.length ; i++) {
                data.add((SubscriptionItem)dataSource[i]);
            }
            mAdapter = new PaymentAdapter(PaymentActivity.this,data,mRecyclerView,selectedCategory);
            mRecyclerView.setAdapter(mAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter.setOnItemListener(new PaymentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(SubscriptionItem item) {
                    selectedItem = item;
                    Intent intent = new Intent();
                    intent.putExtra("selectedVIP_SKU", selectedItem.getSkuNumber());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            progressDialog = new ProgressDialog(PaymentActivity.this,R.style.MyAlertDialogStyle);

            this.bindData();

            mAdapter.setOnLoadMoreListener(new PaymentAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                }
            });
        }
    }

    public void bindData(){
        progressDialog.setMessage("Please waiting.. Its loading..");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResult> call = apiService.getWinner(selectedCategory);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(@NonNull Call<BaseResult> call, Response<BaseResult> response) {
                try {
                    List<Category> dataSource = response.body().getCategoryList();
                    if(dataSource.size()<1){
                        progressDialog.dismiss();
                        return;
                    }
                    final Category currentCategory = dataSource.get(0);
                    couponDataSource = currentCategory.getCouponList();
                    System.out.println("finished");
                    List<Object> adapterSource =new ArrayList<>();
                    for (Coupon item: currentCategory.getCouponList()) {
                        adapterSource.add(item);
                        adapterSource.addAll(item.getMatchList());
                    }
                    mRecyclerViewWinner.setLayoutManager(new LinearLayoutManager(PaymentActivity.this, RecyclerView.VERTICAL, false));
                    mRecyclerViewWinner.setHasFixedSize(true);
                    mAdapterCoupon = new CouponAdapter(PaymentActivity.this, adapterSource, mRecyclerView);
                    mRecyclerViewWinner.setAdapter(mAdapterCoupon);
                    progressDialog.dismiss();
                    mAdapterCoupon.setOnLoadMoreListener(new CouponAdapter.OnLoadMoreListener() {
                        @Override
                        public void onLoadMore() { }
                    });
                    txtNotification = findViewById(R.id.txtNotification);
                    if(currentCategory.getInformation().length()>0){
                        txtNotification.setText(currentCategory.getInformation());
                        txtNotification.setVisibility(View.VISIBLE);
//                    RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mRecyclerView.getLayoutParams();
//                    p.addRule(RelativeLayout.BELOW, R.id.txtNotification);
//                    mRecyclerView.setLayoutParams(p);
                    }else{
                        txtNotification.setVisibility(View.GONE);
                    }
                }
                catch (Exception ex){
                    progressDialog.dismiss();
                }
                finally {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResult> call, @NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}