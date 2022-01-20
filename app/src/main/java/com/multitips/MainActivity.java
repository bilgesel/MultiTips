package com.multitips;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.multitips.adapter.MenuAdapter;
import com.multitips.entity.MenuItem;
import com.multitips.entity.SubscriptionItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout drawer;
    List<SubscriptionItem> subscriptionDataSource;
    List<MenuItem> menuDataSource;
    private MenuAdapter menuAdapter;
    int mainSelectedIndex=-1;
    BillingClient billingClient;
    private static final String MERCHANT_ID=null;
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAklQF0CcJRxrqbseYV/lShp02vR2g6qLr8g3V2n4UCRyCiPW6+y5WiwnEAq9UteI/jMTB+0E5dOdybRoiA5iNKWqU4QQYeTw4n6+2AD0pzMQzdGtHpnmwMJbTP6QfIFI3iZJtWGPQACfDMGj9okYPjdFJZSO1jIyJHAsAlBJceqpxrfD2Ry9N41LNrJCqYqgfsAknvTQUdi+wAv9optGH+uwPndRsFmFze4rY2B+x1+3om5vymqmA1hSXrGtGmiBUS7N/JLyGSNPUVLNAcvKfU7LaXpElItYEzntMTSLyAHGSqCc6p1Xe8DPDdMOIt33X8kT0Oeu5zcb8YzyH9JPSLwIDAQAB";
    private static final String LOG_TAG = "iabv3";
    private static final String LOG_TAGV4 = "iabv4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(view -> drawer.open());
        menuDataSource = MenuItem.GetDataSource();
        final RecyclerView rvVIP = findViewById(R.id.rvVIP);
        rvVIP.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        final RecyclerView rvFREE = findViewById(R.id.rvFREE);
        rvFREE.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        menuAdapter = new MenuAdapter(MainActivity.this,getMenuData(true),rvFREE);
        menuAdapter.setOnItemListener(item -> showCategory(item.getMenuId()));
        rvFREE.setAdapter(menuAdapter);

        menuAdapter = new MenuAdapter(MainActivity.this,getMenuData(false),rvVIP);
        menuAdapter.setOnItemListener(item -> showCategory(item.getMenuId()));
        rvVIP.setAdapter(menuAdapter);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()){
                    Log.w("Message_Log","getInstanceId failed",task.getException());
                    return;
                }
                String token = task.getResult().getToken();
                Log.d("Token",token);
            }
        });

/*        billingClient = BillingClient.newBuilder(getApplicationContext()).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    for (Purchase item:list){
                        mPurchasedList.put(item.getOrderId(),item);
                        showIntent(mainSelectedIndex);
                        Log.d(LOG_TAGV4, "onPurchasesUpdated: "+item.getSkus());
                    }
                }else if(billingResult.getResponseCode()== BillingClient.BillingResponseCode.USER_CANCELED){

                }else if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
                    showIntent(mainSelectedIndex);
                }
            }
        }).enablePendingPurchases().build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                //you can restart your client here.
            }
            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    //here billing Client is ready to be used.
                    querySkuDetails();
                    RestoringPurchases();
                }
            }
        });*/
        billingClient = BillingClient.newBuilder(getApplicationContext()).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    for (Purchase item:list){
                        mPurchasedList.put(item.getOrderId(),item);
                        showIntent(mainSelectedIndex);
                        Log.d(LOG_TAGV4, "onPurchasesUpdated: "+item.getSkus());
                    }
                }else if(billingResult.getResponseCode()== BillingClient.BillingResponseCode.USER_CANCELED){

                }else if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
                    showIntent(mainSelectedIndex);
                }
            }
        }).enablePendingPurchases().build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                //you can restart your client here.
            }
            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    //here billing Client is ready to be used.
                    querySkuDetails();
                    RestoringPurchases();
                }
            }
        });
    }

    public List<MenuItem> getMenuData(boolean isFree) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item: menuDataSource){
            if (isFree && item.getMenuId()>5){
                result.add(item);
            }else if(!isFree && item.getMenuId()<6){
                result.add(item);
            }
        }
        return result;
    }

    private Map<String, SkuDetails> mSkuDetailsMap = new HashMap<>();
    private void querySkuDetails() {
        SkuDetailsParams.Builder skuDetailsParamsBuilder = SkuDetailsParams.newBuilder();
        List<SubscriptionItem> dataSource = SubscriptionItem.GetDataSourceAll();
        ArrayList<String> list = new ArrayList<>();
        for (SubscriptionItem item: dataSource) {
            list.add(item.getSkuNumber());
        }
        skuDetailsParamsBuilder.setSkusList(list);
        skuDetailsParamsBuilder.setType(BillingClient.SkuType.SUBS);

        billingClient.querySkuDetailsAsync(skuDetailsParamsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> resultList) {
                if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                    for (SkuDetails skuDetails:resultList){
                        mSkuDetailsMap.put(skuDetails.getSku(),skuDetails);
                    }
                }
            }
        });
    }

    private Map<String,Purchase> mPurchasedList = new HashMap<>();
    protected void RestoringPurchases(){
        billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, new PurchasesResponseListener() {
            @Override
            public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> resultList) {
                if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                    for (Purchase purchaseItem:resultList){
                        if (purchaseItem.getPurchaseState()== Purchase.PurchaseState.PURCHASED){
                            mPurchasedList.put(purchaseItem.getOrderId(),purchaseItem);
                        }
                    }
                    Log.d(LOG_TAGV4, "onSkuDetailsResponse: ");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void openDrawer(){
        drawer.open();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnNavPrivacy) {
            Intent i = new Intent(MainActivity.this, WebViewActivity.class);
            startActivity(i);
        }else if (id == R.id.btnNavSpecial) {
            showCategory(0);
        }else if (id == R.id.btnNavFixed) {
            showCategory(1);
        }else if (id == R.id.btnNavCorrectScore) {
            showCategory(2);
        }else if (id == R.id.btnNavHtFt) {
            showCategory(3);
        }else if (id == R.id.btnNavMulti) {
            showCategory(4);
        }else if (id == R.id.btnNavMixed) {
            showCategory(5);
        }else if (id == R.id.btnNavDailySure) {
            showCategory(6);
        }else if (id == R.id.btnNavFootball) {
            showCategory(7);
        }else if (id == R.id.btnNavOverUnder) {
            showCategory(8);
        }else if (id == R.id.btnNavSingle) {
            showCategory(9);
        }else if (id == R.id.btnNavDaily20) {
            showCategory(10);
        }else if (id == R.id.btnNavBonusSurprise) {
            showCategory(11);
        }else if (id == R.id.btnNavBasketball) {
            showCategory(12);
        }else if (id == R.id.btnNavTennis) {
            showCategory(13);
        }else if(id==R.id.btnNavSubscription){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/account/subscriptions"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String selectedSKU = data.getStringExtra("selectedVIP_SKU");
                if(selectedSKU.length()>0){
                    buySubscription(selectedSKU);
                }
            }
        }
    }

    private void buySubscription(String skuId) {
     /*   BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(mSkuDetailsMap.get(skuId))
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams).getResponseCode();*/
        this.billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder()
                .setSkuDetails(this.mSkuDetailsMap.get(skuId)).build()).getResponseCode();
    }

    public void showCategory(int selectedIndex){
        if(selectedIndex==0){
            subscriptionDataSource = SubscriptionItem.GetDataSource("specialVIP");
            showPayment(selectedIndex,false);
        }else if(selectedIndex==1){
            subscriptionDataSource = SubscriptionItem.GetDataSource("fixedVIP");
            showPayment(selectedIndex,false);
        }else if(selectedIndex==2){
            subscriptionDataSource = SubscriptionItem.GetDataSource("correctScoreVIP");
            showPayment(selectedIndex,false);
        }else if(selectedIndex==3){
            subscriptionDataSource = SubscriptionItem.GetDataSource("htFtVIP");
            showPayment(selectedIndex,false);
        }else if(selectedIndex==4){
            subscriptionDataSource = SubscriptionItem.GetDataSource("multiVIP");
            showPayment(selectedIndex,false);
        }else if(selectedIndex==5){
            subscriptionDataSource = SubscriptionItem.GetDataSource("mixedVIP");
            showPayment(selectedIndex,false);
        }else{
            showIntent(selectedIndex);
        }
    }

    void showPayment(int selectedIndex,boolean hasSubscription){
        mainSelectedIndex=selectedIndex;
        String title="";
        for (int i = 0; i <subscriptionDataSource.size() ; i++) {
            SubscriptionItem current = subscriptionDataSource.get(i);
            if (title.equals("")) title = current.getName();
             SkuDetails skuDetail = mSkuDetailsMap.get(current.getSkuNumber());
             if (skuDetail!=null){
                 current.setPrice(skuDetail.getPrice());
             }
             if(!hasSubscription){
                for (Map.Entry<String, Purchase> item:mPurchasedList.entrySet()){
                    hasSubscription = item.getValue().getSkus().contains(current.getSkuNumber());
                    if(hasSubscription){
                        break;
                    }
                }
             }
        }
        if(hasSubscription){
            showIntent(selectedIndex);
        }else{
            PaymentDialog(selectedIndex);
        }
    }

    public void PaymentDialog(int selectedIndex){
        List<MenuItem> menuDataSource = MenuItem.GetDataSource();
        Intent i = new Intent(MainActivity.this,PaymentActivity.class);
        i.putExtra("selectedIndex",selectedIndex);
        i.putExtra("selectedCategory",menuDataSource.get(selectedIndex).getMenuName());
        i.putExtra("dataSource",subscriptionDataSource.toArray());
        startActivityForResult(i,1);
    }

    public void showIntent(int selectedIndex){
        Intent intent = new Intent(this, CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId",selectedIndex);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ShowAlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {

        });
        builder.show();
    }

}