package fpt.aptech.vegetableorganic.retrofit;

import fpt.aptech.vegetableorganic.model.MenuModel;
import fpt.aptech.vegetableorganic.model.OrderDetail;
import fpt.aptech.vegetableorganic.model.OrderDetailModel;
import fpt.aptech.vegetableorganic.model.OrderModel;
import fpt.aptech.vegetableorganic.model.ProductModel;
import fpt.aptech.vegetableorganic.model.UserModel;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {

    @GET("getloaisp.php")
    Observable<MenuModel>getLoaiSp();
    @GET("getspmoi.php")
    Observable<ProductModel>getSpMoi();
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<ProductModel>getSanPham(
            @Field("page")int page,
            @Field("category_id")int category_id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<ProductModel>search(
            @Field("search")String search
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<OrderModel>xemDonHang(
            @Field("user_id")int user_id
    );
    @POST("xemdonhangdetails.php")
    @FormUrlEncoded
    Observable<OrderDetailModel>xemDetails(
            @Field("user_id")int user_id
    );
}
