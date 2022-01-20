package com.multitips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multitips.adapter.CouponAdapter;
import com.multitips.api.ApiClient;
import com.multitips.api.ApiInterface;
import com.multitips.entity.BaseResult;
import com.multitips.entity.Category;
import com.multitips.entity.Coupon;
import com.multitips.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Button btnHeader;
    TextView txtNotification;
    TextView txtCategory;
    RecyclerView recyclerView;
    LinearLayout layout_bottom_buttons;
    Button btnTodayTips;
    Button btnOldTips;
    Button btnFooter;
    CouponAdapter mAdapter;
    List<Coupon> couponDataSource;
    int categoryId = 0;
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        FloatingActionButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(view -> finish());


        categoryId = getIntent().getExtras().getInt("categoryId");
        System.out.println("CategoryId : "+categoryId);


        btnHeader = findViewById(R.id.btnHeader);
        btnFooter = findViewById(R.id.btnFooter);
        txtNotification = findViewById(R.id.txtNotification);
        txtCategory = findViewById(R.id.txtCategory);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        layout_bottom_buttons = findViewById(R.id.layout_bottom_buttons);
        btnTodayTips = findViewById(R.id.btnTodayTips);
        btnTodayTips.setOnClickListener(view -> bindData(true));
        btnOldTips = findViewById(R.id.btnOldTips);
        btnOldTips.setOnClickListener(view -> bindData(false));
        progressDialog = new ProgressDialog(CategoryActivity.this,R.style.MyAlertDialogStyle);
        bindData(true);


    }

    public MenuItem getMenu(int categoryId) {
        List<MenuItem> menuDataSource = MenuItem.GetDataSource();
        for (MenuItem item: menuDataSource){
            if(item.getMenuId() == categoryId){
                return item;
            }
        }
        return null;
    }


    void bindData(boolean isToday){
        progressDialog.setMessage("Please waiting.. Its loading..");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        MenuItem currentMenu = getMenu(categoryId);
        txtCategory.setText(currentMenu.getMenuName());
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResult> call = api.getDataWithPage(currentMenu.getMenuName(),isToday,currentPage);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(@NonNull Call<BaseResult> call, Response<BaseResult> response) {
                List<Category> dataSource = response.body().getCategoryList();
                final Category currentCategory = dataSource.get(0);
                couponDataSource = currentCategory.getCouponList();
                System.out.println("finished");
                List<Object> adapterSource =new ArrayList<>();
                for (Coupon item: currentCategory.getCouponList()) {
                    adapterSource.add(item);
                    adapterSource.addAll(item.getMatchList());
                }

                mAdapter = new CouponAdapter(CategoryActivity.this, adapterSource, recyclerView);
                recyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();
                mAdapter.setOnLoadMoreListener(() -> {});

                boolean headerStatus = Boolean.parseBoolean(currentCategory.getHeaderIsVisible());
                if(headerStatus){
                    try {
                        btnHeader.setText(Html.fromHtml(getButtonColorText(currentCategory.getHeaderText())));
                    }catch (Exception e){

                    }
                    btnHeader.setVisibility(View.VISIBLE);
                    btnHeader.setOnClickListener(v -> {
                        Uri uri = Uri.parse(currentCategory.getHeaderUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    });
                    RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
                    p.addRule(RelativeLayout.BELOW, R.id.btnHeader);
                    recyclerView.setLayoutParams(p);
                }else{
                    btnHeader.setVisibility(View.GONE);
                }
                boolean footerStatus = Boolean.parseBoolean(currentCategory.getFooterIsVisible());
                if (footerStatus){
                    btnFooter.setText(Html.fromHtml(getButtonColorText(currentCategory.getFooterText())));
                    btnFooter.setVisibility(View.VISIBLE);
                    btnFooter.setOnClickListener(v -> {
                        Uri uri = Uri.parse(currentCategory.getFooterUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    });
                }else{
                    btnFooter.setVisibility(View.GONE);
                }
                if(currentCategory.getInformation().length()>0){
                    txtNotification.setText(currentCategory.getInformation());
                    txtNotification.setVisibility(View.VISIBLE);
                    if(headerStatus){
                        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) txtNotification.getLayoutParams();
                        p.addRule(RelativeLayout.BELOW, R.id.btnHeader);
                        txtNotification.setLayoutParams(p);
                        p = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
                        p.addRule(RelativeLayout.BELOW, R.id.txtNotification);
                        recyclerView.setLayoutParams(p);
                    }else{
                        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
                        p.addRule(RelativeLayout.BELOW, R.id.txtNotification);
                        recyclerView.setLayoutParams(p);
                    }
                }else{
                    txtNotification.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    String getButtonColorText(String htmlText){
        String[] text = htmlText.split("\\[SATIR]");
        String btnText = "<font color=\"red\">"+text[0]+"</font>";
        if(text.length>1){
            btnText += "<br/><font color=\"blue\">"+text[1]+"</font>";
        }
        return btnText;
    }

}