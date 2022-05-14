package kr.goldenmine.network;

//import dleornjs.login.models.Account;
//import dleornjs.login.models.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlowerService {
    @GET("/openapi/service/rest/PlantService/plntIlstrSearch")
    Call<String> findId(
            @Query("st") int st,
            @Query("sw") String name,
            @Query("serviceKey") String serviceKeyEncoded,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo
    );

    @GET("/openapi/service/rest/PlantService/plntIlstrInfo")
    Call<String> findSpec(
            @Query("serviceKey") String serviceKeyEncoded,
            @Query("q1") int id
    );
}
