package ccl2of4.plexrequests.model.search;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.callbacks.APICallback;
import ccl2of4.plexrequests.model.ServiceFactory;
import ccl2of4.plexrequests.model.callbacks.Callbacks;
import ccl2of4.plexrequests.model.callbacks.ErrorLoggingCallback;
import ccl2of4.plexrequests.model.request.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean
public class SearchRepository {

    public enum SearchType {
        MOVIES, TV_SHOWS
    }

    @Bean
    ServiceFactory serviceFactory;

    @Bean
    Callbacks callbacks;

    public SearchBuilder search(SearchType searchType) {
        return new SearchBuilder(searchType);
    }

    public class SearchBuilder {
        private String query;
        private SearchType searchType;

        public SearchBuilder withQuery(String query) {
            this.query = query;
            return this;
        }

        public void enqueue(APICallback<List<Request>> callback) {
            search(searchType, query, callback);
        }

        private SearchBuilder(SearchType searchType) {
            this.searchType = searchType;
        }
    }

    private void search(SearchType searchType, String query, APICallback<List<Request>> callback) {
        callbacks.cancelAll();

        if (query.isEmpty()) {
            handleEmptyQuery(callback);
            return;
        }

        Call<List<Request>> call = getCall(searchType, query);

        callbacks.enqueue(call, callback);
    }

    private void handleEmptyQuery(APICallback<List<Request>> callback) {
        callback.onCompletion(new ArrayList<Request>());
    }

    public Call<List<Request>> getCall(SearchType searchType, String query) {
        return searchType == SearchType.MOVIES ?
                searchService().searchMovies(query) : searchService().searchTV(query);
    }

    private SearchService searchService() {
        return serviceFactory.get(SearchService.class);
    }

}
