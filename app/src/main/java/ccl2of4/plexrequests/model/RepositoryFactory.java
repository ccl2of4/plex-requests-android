package ccl2of4.plexrequests.model;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EBean(scope = EBean.Scope.Singleton)
public class RepositoryFactory {

    public <T> T get(Class<T> cls) {
        @SuppressWarnings("unchecked")
        T repository = (T) repositories.get(cls);

        if (null == repository) {
            repository = getRetrofit().create(cls);
            repositories.put(cls, repository);
        }
        return repository;
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
    private Map<Class<?>, Object> repositories = new HashMap<>();

}
