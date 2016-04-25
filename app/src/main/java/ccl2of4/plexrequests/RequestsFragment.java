package ccl2of4.plexrequests;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.RepositoryFactory;
import ccl2of4.plexrequests.model.request.Request;
import ccl2of4.plexrequests.model.search.SearchRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_requests)
public class RequestsFragment extends Fragment {

    @Bean
    RepositoryFactory repositoryFactory;

    @ViewById(R.id.search)
    TextView searchTextView;

    @ViewById(R.id.search_results)
    TextView searchResultsTextView;

    private List<Request> searchResults;

    @TextChange(R.id.search)
    void searchMovies() {
        if (getQuery().isEmpty()) {
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

    private void updateSearchResultsTextView() {
        StringBuilder str = new StringBuilder();
        for (Request request : searchResults) {
            str.append(request.getName()).append("\n");
        }
        searchResultsTextView.setText(str.toString());
    }

    private String getQuery() {
        return ObjectUtils.toString(searchTextView.getText());
    }

    private SearchRepository searchRepository() {
        return repositoryFactory.getSearchRepository();
    }

}
