package ccl2of4.plexrequests.model;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

@EBean
public class Callbacks {

    private List<Call<?>> calls = new ArrayList<>();

    public <T> void enqueue(Call<T> call, Callback<T> callback) {
        calls.add(call);
        call.enqueue(callback);
    }

    public void cancelAll() {
        for (Call<?> call : calls) {
            call.cancel();
        }
        calls.clear();
    }

}
