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
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.*;

public interface OrderV2sClient {

    @Headers("Accept: application/json")
    @GET("OrderV2s")
    Call<List<OrderV2StateDto>> getAll(@Query("sort") String sort, @Query("fields") String fields, @Query("firstResult") int firstResult, @Query("maxResults") int maxResults, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("OrderV2s/_count")
    Call<Long> getCount(@Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("OrderV2s/{orderId}")
    Call<OrderV2StateDto> get(@Path("orderId") String orderId, @Query("fields") String fields);

    @Headers("Accept: application/json")
    @GET("OrderV2s/_metadata/filteringFields")
    Call<List<PropertyMetadataDto>> getMetadataFilteringFields();

    @Headers("Accept: application/json")
    @GET("OrderV2s/{orderId}/_events/{version}")
    Call<OrderV2EventDto> getEvent(@Path("orderId") String orderId, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("OrderV2s/{orderId}/_historyStates/{version}")
    Call<OrderV2StateDto> getHistoryState(@Path("orderId") String orderId, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("OrderV2s/{orderV2OrderId}/OrderV2Items/{productId}")
    Call<OrderV2ItemStateDto> getOrderV2Item(@Path("orderV2OrderId") String orderV2OrderId, @Path("productId") String productId);
 
    @Headers("Accept: application/json")
    @GET("OrderV2s/{orderId}/OrderV2Items")
    Call<List<OrderV2ItemStateDto>> getOrderV2Items(@Path("orderId") String orderId, @Query("sort") String sort, @Query("fields") String fields, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @PUT("OrderV2s/{orderId}")
    Call<String> put(@Path("orderId") String orderId, @Body CreateOrMergePatchOrderV2Dto.CreateOrderV2Dto value);

    @Headers("Accept: application/json")
    @POST("OrderV2s")
    Call<String> post(@Body CreateOrMergePatchOrderV2Dto.CreateOrderV2Dto value);

    @Headers("Accept: application/json")
    @PATCH("OrderV2s/{orderId}")
    Call<String> patch(@Path("orderId") String orderId, @Body CreateOrMergePatchOrderV2Dto.MergePatchOrderV2Dto value);

    @Headers("Accept: application/json")
    @DELETE("OrderV2s/{orderId}")
    Call<String> delete(@Path("orderId") String orderId, @Query("commandId") String commandId, @Query("version") String version, @Query("requesterId") String requesterId);

    @Headers("Accept: application/json")
    @PUT("OrderV2s/{orderId}/_commands/RemoveItem")
    Call<String> removeItem(@Path("orderId") String orderId, @Body OrderV2CommandDtos.RemoveItemDto content);

    @Headers("Accept: application/json")
    @PUT("OrderV2s/{orderId}/_commands/UpdateItemQuantity")
    Call<String> updateItemQuantity(@Path("orderId") String orderId, @Body OrderV2CommandDtos.UpdateItemQuantityDto content);

    @Headers("Accept: application/json")
    @PUT("OrderV2s/{orderId}/_commands/UpdateEstimatedShipDate")
    Call<String> updateEstimatedShipDate(@Path("orderId") String orderId, @Body OrderV2CommandDtos.UpdateEstimatedShipDateDto content);

}

