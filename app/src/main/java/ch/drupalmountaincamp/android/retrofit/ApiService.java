package ch.drupalmountaincamp.android.retrofit;

import ch.drupalmountaincamp.android.model.LoginData;
import ch.drupalmountaincamp.android.model.Node;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("user/login?_format=json")
    @Headers({"Content-type: application/x-www-form-urlencoded"})
    Call<ResponseBody> loginRequest(@Body LoginData body);

    @GET("node/{node_id}?_format=json")
    @Headers({"Content-type: application/json"})
    Call<Node> getNode(@Header("Authorization") String user_auth, @Path("node_id") String node_id);

    @POST("/node?_format=json")
    Call<Node> addNode(@Header("Authorization") String user_auth, @Header("X-CSRF-Token") String x_csrf_token, @Body Node node);
}
