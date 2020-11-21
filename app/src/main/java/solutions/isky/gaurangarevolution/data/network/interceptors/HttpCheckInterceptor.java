package solutions.isky.gaurangarevolution.data.network.interceptors;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.HttpException;
import solutions.isky.gaurangarevolution.data.network.NetworkChecker;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;

/**
 * Created by isky on 02.03.2018.
 */

public class HttpCheckInterceptor implements Interceptor {


    public HttpCheckInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        try {
            return chain.proceed(requestBuilder.build());
        } catch (HttpException e) {
            throw e;
        }

    }

}
