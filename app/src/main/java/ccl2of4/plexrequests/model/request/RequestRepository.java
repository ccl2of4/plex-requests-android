package ccl2of4.plexrequests.model.request;

import android.support.annotation.Nullable;

import com.google.common.base.Optional;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import ccl2of4.plexrequests.model.callbacks.APICallback;
import ccl2of4.plexrequests.model.callbacks.ErrorLoggingCallback;
import ccl2of4.plexrequests.events.AddRequestEvent;
import ccl2of4.plexrequests.events.DeleteRequestEvent;
import ccl2of4.plexrequests.events.EventBus;
import ccl2of4.plexrequests.events.RequestsUpdatedEvent;
import ccl2of4.plexrequests.model.callbacks.Callbacks;
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
        return new APICallback<Void>() {
            @Override
            public void onCompletion(@Nullable Void object) {
                refreshRequests();
            }
        };
    }

    private void refreshRequests() {
        callbacks.cancelAll();
        callbacks.enqueue(requestService().getRequests(), getRequestsCallback());
    }

    private Callback<List<Request>> getRequestsCallback() {
        return new APICallback<List<Request>>() {
            @Override
            public void onCompletion(@Nullable List<Request> object) {
                requests = Optional.fromNullable(object).or(new ArrayList<Request>());
                eventBus.post(new RequestsUpdatedEvent(requests));
            }
        };
    }

    private RequestService requestService() {
        return serviceFactory.get(RequestService.class);
    }

}
