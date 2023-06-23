package com.fereshte.event_reminder.util.datetime.interseptor;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        final String Authorization = "Authorization";
        final String XB = "xb";
        final String XC = "xc";
        final String XN = "xn";

        final String xb = "1";
        final String xc = "71";
        final String xn = "j7bKdVgYVQ7nq27eOeXKTLpo664XRmhEqeDL2Pv%2BYwRRXuya" +
                "EUXsrgSbX7jbS57BBjbydEgz1nP2l6XMOocR3eOVipcUn47ecDRbgmTCiAZoA5" +
                "%2BpkQMlq845DYvg%2BuOhyqqksGKf1l%2BTJ0pyt7FfkrThaQ3UAiJcHqXLOui" +
                "E0AlLUhoX%2FPgo1iLpWLflFNP3sJUnCUJRGX3qKoN1AM1rdoyvqRcujsgem%2Fz" +
                "CGmCFS2IWiOGmJUgSoJqg9X%2BL1O9R2I8qEKsq%2BO3j%2FV3PuAyV6isjl5pYK1" +
                "Bbk49UkBcIVus%3D";

        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader(Authorization, "");
        requestBuilder.addHeader(XB, xb);
        requestBuilder.addHeader(XC, xc);
        requestBuilder.addHeader(XN, xn);
        return chain.proceed(requestBuilder.build());
    }
}


