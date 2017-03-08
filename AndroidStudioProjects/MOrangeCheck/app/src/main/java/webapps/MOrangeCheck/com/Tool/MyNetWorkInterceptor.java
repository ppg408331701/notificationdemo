package webapps.MOrangeCheck.com.Tool;



import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import webapps.MOrangeCheck.com.Application.AppApplication;

/**
 * Created by ppg777 on 2016/12/6.
 */

public class MyNetWorkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return savaToken(response);
    }

    private Response savaToken(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            Headers headers= clone.headers();
            for (int i = 0; i < headers.size(); i++) {
                LT.ee(headers.name(i)+":"+headers.value(i));
                if (headers.name(i).equals("Authorization")){
                    AppApplication.setHeadersNoBearer(headers.value(i));
                    break;
                }
            }
            MediaType mediaType = body.contentType();
            String resp = body.string();

            body = ResponseBody.create(mediaType, resp);
            return response.newBuilder().body(body).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
