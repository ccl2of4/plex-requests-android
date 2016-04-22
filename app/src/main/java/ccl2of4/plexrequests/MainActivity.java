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
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.RepositoryFactory;
import ccl2of4.plexrequests.model.request.Request;
import ccl2of4.plexrequests.model.request.RequestRepository;
import ccl2of4.plexrequests.model.search.SearchRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Bean
    RepositoryFactory repositoryFactory;

    @ViewById(R.id.search)
    TextView searchTextView;

    @ViewById(R.id.search_results)
    TextView searchResultsTextView;

    @ViewById(R.id.existing)
    TextView existingTextView;

    private List<Request> searchResults;
    private List<Request> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExistingRequests();
    }

    @TextChange(R.id.search)
    void searchMovies() {
        if (StringUtils.isEmpty(getQuery())) {
            searchResults = new ArrayList<>();
            updateSearchResultsTextView();
            return;
        }

        searchRepository().searchMovies(getQuery()).enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                searchResults = response.body();
                updateSearchResultsTextView();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    void getExistingRequests() {
        requestRepository().getRequests().enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                requests = response.body();
                updateExistingTextView();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    private void updateExistingTextView() {
        StringBuilder str = new StringBuilder();
        for (Request request : requests) {
            str.append(request.getName()).append("\n");
        }
        existingTextView.setText(str.toString());
    }

    private void updateSearchResultsTextView() {
        StringBuilder str = new StringBuilder();
        for (Request request : searchResults) {
            str.append(request.getName()).append("\n");
        }
        searchResultsTextView.setText(str.toString());
    }

    private String getQuery() {
        return searchTextView.getText().toString();
    }

    private RequestRepository requestRepository() {
        return repositoryFactory.getRequestRepository();
    }

    private SearchRepository searchRepository() {
        return repositoryFactory.getSearchRepository();
    }

}
