package com.zmm.paintingdays.http;


import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public interface ApiService {


    /**
     * -----------------------------登录注册界面接口-----------------------------
     */


    /**
     * 获取验证码
     * @param username
     * @return
     */
    @POST("user/getVerifyCode")
    Observable<BaseBean<String>> getVerifyCode(@Query("username") String username);


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    Observable<BaseBean<UserBean>> login(@Query("username") String username,
                                         @Query("password") String password);

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @POST("user/register")
    Observable<BaseBean<UserBean>> register(@Query("username") String username,
                                            @Query("password") String password,
                                            @Query("verifyCode") String verifyCode);

    /**
     * 修改密码
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @POST("user/modifyPassword")
    Observable<BaseBean<String>> modifyPassword(@Query("username") String username,
                                                @Query("oldPassword") String oldPassword,
                                                @Query("newPassword") String newPassword);


    /**
     * 重置密码
     * @param username
     * @param newPassword
     * @param verifyCode
     * @return
     */
    @POST("user/resetPassword")
    Observable<BaseBean<String>> resetPassword(@Query("username") String username,
                                               @Query("newPassword") String newPassword,
                                               @Query("verifyCode") String verifyCode);



    /**
     * -----------------------------用户信息界面接口-----------------------------
     */

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GET("user/findUserById/{id}")
    Observable<BaseBean<UserBean>> findUserById(@Path("id") String id);

    /**
     * 根据id删除用户信息（本质是修改了手机号）
     * @param id
     * @return
     */
    @GET("user/deleteUserById/{id}")
    Observable<BaseBean<String>> deleteUserById(@Path("id") String id);

    @Multipart
    @POST("user/uploadIcon")
    Observable<BaseBean<UserBean>> uploadIcon( @Query("id") String id,
                                               @Query("username") String username,
                                               @Part() MultipartBody.Part file);

    @POST("user/updateUser")
    Observable<BaseBean<UserBean>> updateUserBean(@Body UserBean userBean);


    /**
     * -----------------------------画作相关界面接口-----------------------------
     */

    @GET("paintings/findAllPaintingsByUid")
    Observable<BaseBean<List<PaintingsBean>>> findAllPaintingsByUid(@Query("uId") String uId,
                                                                    @Query("page") int page,
                                                                    @Query("size") int size);

    @Multipart
    @POST("paintings/addPaintings")
    Observable<BaseBean<PaintingsBean>> addPaintings(@Query("uId")String uId,
                                                     @Query("username")String username,
                                                     @Query("title") String title,
                                                     @Query("content")String content,
                                                     @Query("tags")String tags,
                                                     @Query("jurisdiction") int jurisdiction,
                                                     @Part() MultipartBody.Part file);

    @GET("paintings/deletePaintings/{id}")
    Observable<BaseBean<String>> deletePaintingsById(@Path("id") String id);

    /**
     * -----------------------------相关界面接口-----------------------------
     */

    @GET("diary/addDiary")
    Observable<BaseBean<DiaryBean>> addDiary(@Query("uId")String uId,
                                             @Query("title")String title,
                                             @Query("content")String content,
                                             @Query("createTime")String createTime);


    @POST("diary/updateDiary")
    Observable<BaseBean<DiaryBean>> updateDiary(@Body DiaryBean diaryBean);


    @GET("diary/deleteDiary/{id}")
    Observable<BaseBean<String>> deleteDiary(@Path("id")String id);


    @GET("diary/findDiaryById/{id}")
    Observable<BaseBean<DiaryBean>> findDiaryById(@Path("id")String id);


    @GET("diary/findAllDiaryByUid")
    Observable<BaseBean<List<DiaryBean>>> findAllDiaryByUid(@Query("uId")String uId,
                                                      @Query("page")int page,
                                                      @Query("size")int size);


}
