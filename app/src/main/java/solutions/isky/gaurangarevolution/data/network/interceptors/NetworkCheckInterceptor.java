package solutions.isky.gaurangarevolution.data.network.interceptors;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import solutions.isky.gaurangarevolution.data.network.NetworkChecker;
import solutions.isky.gaurangarevolution.domain.global.exceptions.NoNetworkException;

/**
 * Created by isky on 02.03.2018.
 */

public class NetworkCheckInterceptor implements Interceptor {
    private NetworkChecker networkChecker;

    public NetworkCheckInterceptor(NetworkChecker networkChecker) {
        this.networkChecker = networkChecker;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (!networkChecker.isConnected()) {
            throw new NoNetworkException("No network connection");
        }

        try {
            return chain.proceed(requestBuilder.build());
        } catch (SocketTimeoutException | UnknownHostException e) {
            throw new NoNetworkException();
        }

    }

}
