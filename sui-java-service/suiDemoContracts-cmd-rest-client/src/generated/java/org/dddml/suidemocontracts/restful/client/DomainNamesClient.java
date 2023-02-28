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
import org.dddml.suidemocontracts.domain.domainname.*;

public interface DomainNamesClient {

    @Headers("Accept: application/json")
    @GET("DomainNames")
    Call<List<DomainNameStateDto>> getAll(@Query("sort") String sort, @Query("fields") String fields, @Query("firstResult") int firstResult, @Query("maxResults") int maxResults, @Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("DomainNames/_count")
    Call<Long> getCount(@Query("filter") String filter);

    @Headers("Accept: application/json")
    @GET("DomainNames/{domainNameId}")
    Call<DomainNameStateDto> get(@Path("domainNameId") String domainNameId, @Query("fields") String fields);

    @Headers("Accept: application/json")
    @GET("DomainNames/_metadata/filteringFields")
    Call<List<PropertyMetadataDto>> getMetadataFilteringFields();

    @Headers("Accept: application/json")
    @GET("DomainNames/{domainNameId}/_events/{version}")
    Call<DomainNameEventDto> getEvent(@Path("domainNameId") String domainNameId, @Path("version") long version);

    @Headers("Accept: application/json")
    @GET("DomainNames/{domainNameId}/_historyStates/{version}")
    Call<DomainNameStateDto> getHistoryState(@Path("domainNameId") String domainNameId, @Path("version") long version);

    @Headers("Accept: application/json")
    @PUT("DomainNames/{domainNameId}")
    Call<String> put(@Path("domainNameId") String domainNameId, @Body CreateOrMergePatchDomainNameDto.CreateDomainNameDto value);

    @Headers("Accept: application/json")
    @POST("DomainNames")
    Call<DomainNameId> post(@Body CreateOrMergePatchDomainNameDto.CreateDomainNameDto value);

    @Headers("Accept: application/json")
    @PATCH("DomainNames/{domainNameId}")
    Call<String> patch(@Path("domainNameId") String domainNameId, @Body CreateOrMergePatchDomainNameDto.MergePatchDomainNameDto value);

    @Headers("Accept: application/json")
    @DELETE("DomainNames/{domainNameId}")
    Call<String> delete(@Path("domainNameId") String domainNameId, @Query("commandId") String commandId, @Query("version") String version, @Query("requesterId") String requesterId);

    @Headers("Accept: application/json")
    @PUT("DomainNames/{domainNameId}/_commands/Register")
    Call<String> register(@Path("domainNameId") String domainNameId, @Body DomainNameCommandDtos.RegisterDto content);

    @Headers("Accept: application/json")
    @PUT("DomainNames/{domainNameId}/_commands/Renew")
    Call<String> renew(@Path("domainNameId") String domainNameId, @Body DomainNameCommandDtos.RenewDto content);

}

