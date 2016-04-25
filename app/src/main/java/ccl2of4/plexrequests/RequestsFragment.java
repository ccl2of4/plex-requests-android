package ccl2of4.plexrequests;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

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
    ListView searchResultsListView;

    private List<Request> searchResults;

    @TextChange(R.id.search)
    void searchMovies() {
        if (getQuery().isEmpty()) {
            searchResults = new ArrayList<>();
            updateListView();
            return;
        }

        searchRepository().searchMovies(getQuery()).enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                searchResults = response.body();
                updateListView();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    void updateListView() {
        List<String> labels = FluentIterable.from(searchResults)
                .transform(getName())
                .toList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.listitem_request, R.id.request_name, labels);
        searchResultsListView.setAdapter(adapter);
    }

    private Function<Request, String> getName() {
        return new Function<Request, String>() {
            @Override
            public String apply(Request request) {
                return request.getName();
            }
        };
    }

    private String getQuery() {
        return ObjectUtils.toString(searchTextView.getText());
    }

    private SearchRepository searchRepository() {
        return repositoryFactory.getSearchRepository();
    }

}
