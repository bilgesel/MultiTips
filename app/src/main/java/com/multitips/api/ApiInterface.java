package com.multitips.api;

import com.multitips.entity.BaseResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/Mobile?id=015BCA89-83A9-4159-92CC-FD5A312FAF33")
    Call<BaseResult> getData(@Query("cat") String category, @Query("day") boolean isToday);

    @GET("/Mobile/WithPage?id=015BCA89-83A9-4159-92CC-FD5A312FAF33")
    Call<BaseResult> getDataWithPage(@Query("cat") String category, @Query("day") boolean isToday,@Query("page") int page);

    @GET("/Mobile/WinnerCoupons?id=015BCA89-83A9-4159-92CC-FD5A312FAF33")
    Call<BaseResult> getWinner(@Query("cat") String category);

    @GET("/Mobile/Rewards?id=015BCA89-83A9-4159-92CC-FD5A312FAF33")
    Call<BaseResult> getRewards(@Query("cat") String category);
}
