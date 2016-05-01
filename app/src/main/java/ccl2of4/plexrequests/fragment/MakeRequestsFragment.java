package ccl2of4.plexrequests.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.view.MakeRequestView;
import ccl2of4.plexrequests.R;
import ccl2of4.plexrequests.view.MakeRequestView_;
import ccl2of4.plexrequests.view.RequestsListAdapter;
import ccl2of4.plexrequests.model.callbacks.ErrorLoggingCallback;
import ccl2of4.plexrequests.events.EventBus;
import ccl2of4.plexrequests.events.ViewRequestEvent;
import ccl2of4.plexrequests.model.callbacks.Callbacks;
import ccl2of4.plexrequests.model.ServiceFactory;
import ccl2of4.plexrequests.model.request.Request;
import ccl2of4.plexrequests.model.search.SearchService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_make_requests)
public class MakeRequestsFragment extends Fragment {

    @Bean
    EventBus eventBus;

    @Bean
    ServiceFactory serviceFactory;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Subscribe
    public void viewRequest(ViewRequestEvent event) {
        Request request = event.getRequest();
        searchMoviesRadioButton.setChecked(request.isMovie());
        searchTVRadioButton.setChecked(!request.isMovie());
        searchTextView.setText(request.getName());
        searchResultsListView.smoothScrollToPosition(0);
    }

    @TextChange(R.id.search)
    @Click({R.id.search_movies, R.id.search_tv})
    void updateSearchResults() {
        callbacks.cancelAll();

        if (getQuery().isEmpty()) {
            handleEmptyQuery();
            return;
        }

        callbacks.enqueue(search(), searchCallback());
    }

    private Call<List<Request>> search() {
        return shouldSearchMovies() ?
                searchService().searchMovies(getQuery()) :
                searchService().searchTV(getQuery());
    }

    private boolean shouldSearchMovies() {
        return searchMoviesRadioButton.isChecked();
    }

    private Callback<List<Request>> searchCallback() {
        return new ErrorLoggingCallback<List<Request>>() {
            @Override
            protected void onCompletion(Call<List<Request>> call, Response<List<Request>> response, boolean success) {
                searchResults = success ? response.body() : new ArrayList<Request>();
                dataSetChanged();
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


    private String getQuery() {
        return ObjectUtils.toString(searchTextView.getText());
    }

    private SearchService searchService() {
        return serviceFactory.get(SearchService.class);
    }

}
