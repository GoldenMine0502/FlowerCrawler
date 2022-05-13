package kr.goldenmine.network;

import java.util.List;

import dleornjs.login.models.Account;
import dleornjs.login.models.Question;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FlowerService {
    @GET("/openapi/service/rest/PlantService")
    Call<String> plantService(

    );
}
