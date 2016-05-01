package ccl2of4.plexrequests.model.request;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.events.AddRequestEvent;
import ccl2of4.plexrequests.events.DeleteRequestEvent;
import ccl2of4.plexrequests.events.EventBus;
import ccl2of4.plexrequests.events.RequestsUpdatedEvent;
import ccl2of4.plexrequests.model.Callbacks;
import ccl2of4.plexrequests.model.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean(scope = EBean.Scope.Singleton)
public class RequestRepository {

    @Bean
    EventBus eventBus;

    @Bean
    ServiceFactory serviceFactory;

    @Bean
    Callbacks callbacks;

    private List<Request> requests = new ArrayList<>();

    @AfterInject
    void init() {
        eventBus.register(this);
        refreshRequests();
    }

    public List<Request> getRequests() {
        return requests;
    }

    @Subscribe
    public void addRequest(AddRequestEvent event) {
        requestService().addRequest(event.getRequest()).enqueue(modifyRequestsCallback());
    }

    @Subscribe
    public void deleteRequest(DeleteRequestEvent event) {
        String requestId = event.getRequest().getRequestId();
        requestService().deleteRequest(requestId).enqueue(modifyRequestsCallback());
    }

    @Produce
    public RequestsUpdatedEvent requestsUpdatedEvent() {
        return new RequestsUpdatedEvent(requests);
    }

    private Callback<Void> modifyRequestsCallback() {
        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                refreshRequests();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                throw new RuntimeException(t);
            }
        };
    }

    private void refreshRequests() {
        callbacks.cancelAll();
        callbacks.enqueue(requestService().getRequests(), getRequestsCallback());
    }

    private Callback<List<Request>> getRequestsCallback() {
        return new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                requests = response.isSuccessful() ? response.body() : new ArrayList<Request>();
                eventBus.post(new RequestsUpdatedEvent(requests));
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                throw new RuntimeException(t);
            }
        };
    }

    private RequestService requestService() {
        return serviceFactory.get(RequestService.class);
    }

}
