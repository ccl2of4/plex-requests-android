package ccl2of4.plexrequests.model.callbacks;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ErrorLoggingCallback<T> implements Callback<T> {

    private static final String TAG = ErrorLoggingCallback.class.toString();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        boolean success = response.isSuccessful();
        if (!success) {
            Log.e(TAG, String.format("Erorr calling %s", call.request().url()));
        }

        onCompletion(call, response, success);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!call.isCanceled()) {
            Log.e(TAG, String.format("Erorr calling %s", call.request().url()), t);
        }
        onCompletion(call, null, false);
    }

    protected abstract void onCompletion(Call<T> call, Response<T> response, boolean success);
}
