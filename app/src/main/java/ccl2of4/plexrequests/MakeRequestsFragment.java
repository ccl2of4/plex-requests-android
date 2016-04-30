package ccl2of4.plexrequests;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.common.base.Function;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.Callbacks;
import ccl2of4.plexrequests.model.RepositoryFactory;
import ccl2of4.plexrequests.model.request.Request;
import ccl2of4.plexrequests.model.search.SearchRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_make_requests)
public class MakeRequestsFragment extends Fragment {

    @Bean
    RepositoryFactory repositoryFactory;

    @Bean
    Callbacks callbacks;

    @ViewById(R.id.search)
    TextView searchTextView;

    @ViewById(R.id.search_results)
    ListView searchResultsListView;

    @ViewById(R.id.search_movies)
    RadioButton searchMoviesRadioButton;

    @ViewById(R.id.search_tv)
    RadioButton searchTVRadioButton;

    private List<Request> searchResults;

    @TextChange(R.id.search)
    @Click({R.id.search_movies, R.id.search_tv})
    void search() {
        callbacks.cancelAll();

        if (getQuery().isEmpty()) {
            handleEmptyQuery();
            return;
        }

        Call<List<Request>> call = shouldSearchMovies() ?
                searchMovies() : searchTVShows();

        callbacks.enqueue(call, searchCallback());
    }

    private boolean shouldSearchMovies() {
        return searchMoviesRadioButton.isChecked();
    }

    private Call<List<Request>> searchMovies() {
        return searchRepository().searchMovies(getQuery());
    }

    private Call<List<Request>> searchTVShows() {
        return searchRepository().searchTV(getQuery());
    }

    private Callback<List<Request>> searchCallback() {
        return new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                searchResults = response.isSuccessful() ? response.body() : new ArrayList<Request>();
                dataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                throw new RuntimeException(t);
            }
        };
    }

    private void handleEmptyQuery() {
        searchResults = new ArrayList<>();
        dataSetChanged();
    }

    private void dataSetChanged() {
        listAdapter.setRequests(searchResults);

        if (null == searchResultsListView.getAdapter()) {
            searchResultsListView.setAdapter(listAdapter);
        }
    }

    private RequestsListAdapter listAdapter = new RequestsListAdapter() {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MakeRequestView view = MakeRequestView_.build(getContext());
            view.setRequest(getRequest(position));
            return view;
        }
    };

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
        return repositoryFactory.get(SearchRepository.class);
    }

}
