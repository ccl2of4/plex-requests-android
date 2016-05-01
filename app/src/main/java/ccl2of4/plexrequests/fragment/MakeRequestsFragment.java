package ccl2of4.plexrequests.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.callbacks.APICallback;
import ccl2of4.plexrequests.model.search.SearchRepository;
import ccl2of4.plexrequests.view.ArrayListAdapter;
import ccl2of4.plexrequests.view.MakeRequestView;
import ccl2of4.plexrequests.R;
import ccl2of4.plexrequests.view.MakeRequestView_;
import ccl2of4.plexrequests.events.EventBus;
import ccl2of4.plexrequests.events.ViewRequestEvent;
import ccl2of4.plexrequests.model.request.Request;
import retrofit2.Callback;

@EFragment(R.layout.fragment_make_requests)
public class MakeRequestsFragment extends Fragment {

    @Bean
    EventBus eventBus;

    @Bean
    SearchRepository searchRepository;

    @ViewById(R.id.search)
    TextView searchTextView;

    @ViewById(R.id.search_results)
    ListView searchResultsListView;

    @ViewById(R.id.search_movies)
    RadioButton searchMoviesRadioButton;

    @ViewById(R.id.search_tv)
    RadioButton searchTVRadioButton;

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

    @AfterViews
    public void init() {
        searchResultsListView.setAdapter(listAdapter);
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
        searchRepository.search(getSearchType())
                .withQuery(getQuery())
                .enqueue(searchCallback());
    }

    private SearchRepository.SearchType getSearchType() {
        return searchMoviesRadioButton.isChecked() ?
                SearchRepository.SearchType.MOVIES : SearchRepository.SearchType.TV_SHOWS;
    }

    private APICallback<List<Request>> searchCallback() {
        return new APICallback<List<Request>>() {
            @Override
            public void onCompletion(@Nullable List<Request> results) {
                List<Request> searchResults = Optional.fromNullable(results).or(new ArrayList<Request>());
                listAdapter.setObjects(searchResults);
            }
        };
    }

    private ArrayListAdapter<Request> listAdapter = new ArrayListAdapter<Request>() {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MakeRequestView view = MakeRequestView_.build(getContext());
            view.setRequest(getObject(position));
            return view;
        }
    };

    private String getQuery() {
        return ObjectUtils.toString(searchTextView.getText());
    }

}
