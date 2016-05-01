package ccl2of4.plexrequests.model.callbacks;

import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Response;

public abstract class APICallback<T> extends ErrorLoggingCallback<T> {

    @Override
    public void onCompletion(Call<T> call, Response<T> response, boolean success) {
        T object = success ? response.body() : null;
        onCompletion(object);
    }

    public abstract void onCompletion(@Nullable T object);

}
