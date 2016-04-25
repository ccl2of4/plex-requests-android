package ccl2of4.plexrequests;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

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

    @ViewById(R.id.existing)
    TextView existingTextView;

    private List<Request> requests;

    @AfterViews
    void init() {
        getExistingRequests();
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

    private RequestRepository requestRepository() {
        return repositoryFactory.getRequestRepository();
    }


}
