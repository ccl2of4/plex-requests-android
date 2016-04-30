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
        if (getQuery().isEmpty()) {
            searchResults = new ArrayList<>();
            dataSetChanged();
            return;
        }

        if (searchMoviesRadioButton.isChecked()) {
            searchMovies();
        } else {
            searchTVShows();
        }
    }

    private void searchMovies() {
        searchRepository()
                .searchMovies(getQuery())
                .enqueue(searchCallback());
    }

    private void searchTVShows() {
        searchRepository()
                .searchTV(getQuery())
                .enqueue(searchCallback());
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
                throw new RuntimeException(t);
            }
        };
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
        return repositoryFactory.getSearchRepository();
    }

}
