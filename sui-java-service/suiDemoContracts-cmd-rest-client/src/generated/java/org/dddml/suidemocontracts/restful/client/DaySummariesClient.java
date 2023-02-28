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
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.daysummary.*;

public interface DaySummariesClient {

    @Headers("Accept: application/json")
    @GET("DaySummaries")
    Call<List<DaySummaryStateDto>> getAll(@Query("sort") String sort, @Query("fields") String fields, @Query("firstResult") int firstResult, @Query("maxResults") int maxResults, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("DaySummaries/_count")
    Call<Long> getCount(@Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("DaySummaries/{day}")
    Call<DaySummaryStateDto> get(@Path("day") String day, @Query("fields") String fields);

    @Headers("Accept: application/json")
    @GET("DaySummaries/_metadata/filteringFields")
    Call<List<PropertyMetadataDto>> getMetadataFilteringFields();

    @Headers("Accept: application/json")
    @GET("DaySummaries/{day}/_events/{version}")
    Call<DaySummaryEventDto> getEvent(@Path("day") String day, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("DaySummaries/{day}/_historyStates/{version}")
    Call<DaySummaryStateDto> getHistoryState(@Path("day") String day, @Path("version") long version);

    @Headers("Accept: application/json")
    @PUT("DaySummaries/{day}")
    Call<String> put(@Path("day") String day, @Body CreateOrMergePatchDaySummaryDto.CreateDaySummaryDto value);

    @Headers("Accept: application/json")
    @POST("DaySummaries")
    Call<Day> post(@Body CreateOrMergePatchDaySummaryDto.CreateDaySummaryDto value);

    @Headers("Accept: application/json")
    @PATCH("DaySummaries/{day}")
    Call<String> patch(@Path("day") String day, @Body CreateOrMergePatchDaySummaryDto.MergePatchDaySummaryDto value);

    @Headers("Accept: application/json")
    @DELETE("DaySummaries/{day}")
    Call<String> delete(@Path("day") String day, @Query("commandId") String commandId, @Query("version") String version, @Query("requesterId") String requesterId);

}

