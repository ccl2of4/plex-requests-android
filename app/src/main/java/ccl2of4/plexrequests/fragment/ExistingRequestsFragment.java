package ccl2of4.plexrequests.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ccl2of4.plexrequests.view.ExistingRequestView;
import ccl2of4.plexrequests.R;
import ccl2of4.plexrequests.view.ExistingRequestView_;
import ccl2of4.plexrequests.view.RequestsListAdapter;
import ccl2of4.plexrequests.events.EventBus;
import ccl2of4.plexrequests.events.RequestsUpdatedEvent;
import ccl2of4.plexrequests.model.request.Request;

@EFragment(R.layout.fragment_existing_requests)
public class ExistingRequestsFragment extends Fragment {

    @Bean
    EventBus eventBus;

    @ViewById(R.id.existing_requests)
    ListView listView;

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
    void init() {
        listView.setAdapter(listAdapter);
    }

    @Subscribe
    public void requestsUpdated(RequestsUpdatedEvent event) {
        requestsUpdated(event.getRequests());
    }

    void requestsUpdated(List<Request> requests) {
        listAdapter.setRequests(requests);
    }

    private RequestsListAdapter listAdapter = new RequestsListAdapter() {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ExistingRequestView view = ExistingRequestView_.build(getContext());
            view.setRequest(getRequest(position));
            return view;
        }
    };

}
