package ccl2of4.plexrequests;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

import ccl2of4.plexrequests.model.RepositoryFactory;
import ccl2of4.plexrequests.model.request.Request;
import ccl2of4.plexrequests.model.request.RequestRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Bean
    RepositoryFactory repositoryFactory;

    @ViewById(R.id.textview)
    TextView textView;

    private List<Request> requests;

    @Click(R.id.fab)
    void fetchRequests() {
        requestRepository().getRequests().enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                requests = response.body();
                updateTextView();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    private void updateTextView() {
        StringBuilder str = new StringBuilder();
        for (Request request : requests) {
            str.append(request.getName()).append("\n");
        }
        textView.setText(str.toString());
    }

    private RequestRepository requestRepository() {
        return repositoryFactory.getRequestRepository();
    }

}
