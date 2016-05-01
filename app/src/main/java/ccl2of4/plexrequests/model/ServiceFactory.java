package ccl2of4.plexrequests.model;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EBean(scope = EBean.Scope.Singleton)
public class ServiceFactory {

    public <T> T get(Class<T> cls) {
        @SuppressWarnings("unchecked")
        T service = (T) services.get(cls);

        if (null == service) {
            service = getRetrofit().create(cls);
            services.put(cls, service);
        }
        return service;
    }

    private Retrofit getRetrofit() {
        retrofit = retrofit != null ? retrofit :
                new Retrofit.Builder()
                        .baseUrl("http://192.168.0.17:5000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        return retrofit;
    }

    private Retrofit retrofit;
    private Map<Class<?>, Object> services = new HashMap<>();

}
