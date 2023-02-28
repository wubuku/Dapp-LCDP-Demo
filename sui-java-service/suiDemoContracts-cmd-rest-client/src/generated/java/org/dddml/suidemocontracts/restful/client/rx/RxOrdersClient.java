package org.dddml.suidemocontracts.restful.client.rx;

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
import rx.Observable;

import org.dddml.support.criterion.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.order.*;

public interface RxOrdersClient {

    @Headers("Accept: application/json")
    @GET("Orders")
    Observable<List<OrderStateDto>> getAll(@Query("sort") String sort, @Query("fields") String fields, @Query("firstResult") int firstResult, @Query("maxResults") int maxResults, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("Orders/_count")
    Observable<Long> getCount(@Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("Orders/{id}")
    Observable<OrderStateDto> get(@Path("id") String id, @Query("fields") String fields);

    @Headers("Accept: application/json")
    @GET("Orders/_metadata/filteringFields")
    Observable<List<PropertyMetadataDto>> getMetadataFilteringFields();

    @Headers("Accept: application/json")
    @GET("Orders/{id}/_events/{version}")
    Observable<OrderEventDto> getEvent(@Path("id") String id, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("Orders/{id}/_historyStates/{version}")
    Observable<OrderStateDto> getHistoryState(@Path("id") String id, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("Orders/{orderId}/OrderItems/{productId}")
    Observable<OrderItemStateDto> getOrderItem(@Path("orderId") String orderId, @Path("productId") String productId);
 
    @Headers("Accept: application/json")
    @GET("Orders/{id}/OrderItems")
    Observable<List<OrderItemStateDto>> getOrderItems(@Path("id") String id, @Query("sort") String sort, @Query("fields") String fields, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @PUT("Orders/{id}")
    Observable<String> put(@Path("id") String id, @Body CreateOrMergePatchOrderDto.CreateOrderDto value);

    @Headers("Accept: application/json")
    @POST("Orders")
    Observable<String> post(@Body CreateOrMergePatchOrderDto.CreateOrderDto value);

    @Headers("Accept: application/json")
    @PATCH("Orders/{id}")
    Observable<String> patch(@Path("id") String id, @Body CreateOrMergePatchOrderDto.MergePatchOrderDto value);

    @Headers("Accept: application/json")
    @DELETE("Orders/{id}")
    Observable<String> delete(@Path("id") String id, @Query("commandId") String commandId, @Query("version") String version, @Query("requesterId") String requesterId);

    @Headers("Accept: application/json")
    @PUT("Orders/{id}/_commands/RemoveItem")
    Observable<String> removeItem(@Path("id") String id, @Body OrderCommandDtos.RemoveItemDto content);

    @Headers("Accept: application/json")
    @PUT("Orders/{id}/_commands/UpdateItemQuantity")
    Observable<String> updateItemQuantity(@Path("id") String id, @Body OrderCommandDtos.UpdateItemQuantityDto content);

}

