package ccl2of4.plexrequests;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.RepositoryFactory;
import ccl2of4.plexrequests.model.request.Request;
import ccl2of4.plexrequests.model.request.RequestRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_existing_requests)
public class ExistingRequestsFragment extends Fragment {

    @Bean
    RepositoryFactory repositoryFactory;

    @ViewById(R.id.existing_requests)
    ListView listView;

    private List<Request> requests;

    @AfterViews
    void init() {
        getExistingRequests();
    }

    void getExistingRequests() {
        requestRepository().getRequests().enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                requests = response.isSuccessful() ? response.body() : new ArrayList<Request>();
                dataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    void dataSetChanged() {
        listAdapter.setRequests(requests);
        if (null == listView.getAdapter()) {
            listView.setAdapter(listAdapter);
        }
    }

    private RequestsListAdapter listAdapter = new RequestsListAdapter() {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ExistingRequestView view = ExistingRequestView_.build(getContext());
            view.setRequest(getRequest(position));
            return view;
        }
    };

    private RequestRepository requestRepository() {
        return repositoryFactory.get(RequestRepository.class);
    }

}
