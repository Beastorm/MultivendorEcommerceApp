package com.kplar.utilities;


import com.kplar.activities.ForgotPassword;
import com.kplar.models.DealSlider;
import com.kplar.models.OfferSliders;
import com.kplar.models.UpdatePassword;
import com.kplar.models.UserResponseData;
import com.kplar.models.billAndProductPackage.BillProduct;
import com.kplar.models.cartPackage.Cart;
import com.kplar.models.categoriesPackage.Category;
import com.kplar.models.kplarWalletPackage.WalletBalance;
import com.kplar.models.newProductsPackage.NewProduct;
import com.kplar.models.offerPackage.Offer;
import com.kplar.models.paytmPackage.PaytmChecksum;
import com.kplar.models.productDetailsPackage.CompleteProductsDetails;
import com.kplar.models.productsPackage.Products;
import com.kplar.models.recentViewPackage.RecentView;
import com.kplar.models.subcategoriesPackage.SubCategory;
import com.kplar.models.topCategoriesPackage.TopCategorySlider;
import com.kplar.models.wishListPackage.WishListItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("register1.php")
    Call<UserResponseData> performSignUp(@Query("uname") String userName, @Query("mobile") String mobile, @Query("psw") String pwd);

    @Headers("Content-Type: application/json")
    @GET("login1.php")
    Call<UserResponseData> performSignIn(@Query("mobile") String mobile, @Query("psw") String pwd);

    @GET("toplevelcategory.php")
    Call<List<TopCategorySlider>> getSliderItems();

    @GET("slider.php")
    Call<List<OfferSliders>> getSlider();

    @GET("category.php")
    Call<Category> getCategories();

    @GET("deals.php")
    Call<List<DealSlider>> getDealsImage();

    @GET("subcategory.php")
    Call<SubCategory> getSubCategories(@Query("categoryId") String id);

    @GET("subcategory.php")
    Call<Products> getProducts(@Query("categoryId") String id);

    @GET("productDetails.php")
    Call<CompleteProductsDetails> getProductDetails(@Query("productId") String productId);

    @GET("addReview.php")
    Call<ResponseBody> addReview(@Query("userName") String userName, @Query("product_id") String product_id, @Query("rate") String rate, @Query("review") String review);

    @GET("filter.php")
    Call<Products> getSortedProducts(@Query("sort") String sortId, @Query("categoryId") String categoryId);

    @GET("addWishlist.php")
    Call<ResponseBody> addProduct2Wishlist(@Query("product_id") String productId, @Query("userName") String userName);


    @GET("removeWishlist.php")
    Call<ResponseBody> removeProductToWishList(@Query("product_id") String productId, @Query("userName") String userName);


    @GET("viewWishlist.php")
    Call<WishListItem> viewWishList(@Query("userName") String userName);

    @GET("removeAllWishlist.php")
    Call<ResponseBody> removeAllWishList(@Query("userName") String userName);

    @GET("addCart.php")
    Call<ResponseBody> addProduct2Cart(@Query("product_id") String productId, @Query("userName") String userName, @Query("product_quantity") String qty);


    @GET("viewCart.php")
    Call<Cart> viewAllCart(@Query("userName") String userName);

    @GET("removeCart.php")
    Call<ResponseBody> removeProductToCart(@Query("product_id") String productId, @Query("userName") String userName);


    @GET("removeAllCart.php")
    Call<ResponseBody> removeAllCart(@Query("userName") String userName);


    @GET("updateCart.php")
    Call<ResponseBody> updateCart(@Query("product_id") String productId, @Query("userName") String userName, @Query("product_quantity") String qty);


    @GET("viewOfferZone.php")
    Call<Offer> viewOffers(@Query("section") String section);


    @GET("bill.php")
    Call<BillProduct> getProductWithBill(@Query("userName") String userName);

    @FormUrlEncoded
    @POST("Paytm_Android_App/generateChecksum.php")
    Call<PaytmChecksum> getChecksum(@Field("MID") String mid,
                                    @Field("ORDER_ID") String orderId,
                                    @Field("CUST_ID") String customerId,
                                    @Field("CHANNEL_ID") String channelId,
                                    @Field("TXN_AMOUNT") String amount,
                                    @Field("WEBSITE") String website,
                                    @Field("INDUSTRY_TYPE_ID") String industryType,
                                    @Field("CALLBACK_URL") String callBackUrl);


//    @GET("help.php")
//    Call<Help> getHelp();

    //Forgot password end path
    @GET("otp.php")
    Call<ForgotPassword> getForgetPassword(@Query("uMob") String uMob);

    @GET("resetPassword.php")
    Call<UpdatePassword> setNewPass(
            @Query("userMobile") String userMobile,
            @Query("newPassword") String newPassword);


    @GET("addMoneyToWallet.php")
    Call<ResponseBody> addMoneyToWallet(@Query("userName") String userName, @Query("money") String money);


    @GET("addMoneyToWallet.php")
    Call<WalletBalance> viewWalletMoney(@Query("userName") String userName);


    @GET("addRecentProduct.php")
    Call<ResponseBody> addToRecentProductList(@Query("userName") String userName, @Query("productId") String productId);

    @GET("viewRecentProduct.php")
    Call<RecentView> viewRecentProducts(@Query("userName") String userName);

    @GET("viewNewProduct.php")
    Call<NewProduct> getNewProducts();


}
