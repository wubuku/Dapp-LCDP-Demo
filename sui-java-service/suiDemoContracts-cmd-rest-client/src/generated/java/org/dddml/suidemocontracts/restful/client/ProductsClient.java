package org.dddml.suidemocontracts.restful.client;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import org.dddml.support.criterion.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.product.*;

public interface ProductsClient {

    @Headers("Accept: application/json")
    @GET("Products")
    Call<List<ProductStateDto>> getAll(@Query("sort") String sort, @Query("fields") String fields, @Query("firstResult") int firstResult, @Query("maxResults") int maxResults, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("Products/_count")
    Call<Long> getCount(@Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("Products/{productId}")
    Call<ProductStateDto> get(@Path("productId") String productId, @Query("fields") String fields);

    @Headers("Accept: application/json")
    @GET("Products/_metadata/filteringFields")
    Call<List<PropertyMetadataDto>> getMetadataFilteringFields();

    @Headers("Accept: application/json")
    @GET("Products/{productId}/_events/{version}")
    Call<ProductEventDto> getEvent(@Path("productId") String productId, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("Products/{productId}/_historyStates/{version}")
    Call<ProductStateDto> getHistoryState(@Path("productId") String productId, @Path("version") long version);

    @Headers("Accept: application/json")
    @PUT("Products/{productId}")
    Call<String> put(@Path("productId") String productId, @Body CreateOrMergePatchProductDto.CreateProductDto value);

    @Headers("Accept: application/json")
    @POST("Products")
    Call<String> post(@Body CreateOrMergePatchProductDto.CreateProductDto value);

}

