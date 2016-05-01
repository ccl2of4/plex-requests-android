package ccl2of4.plexrequests.events;

import java.util.List;

import ccl2of4.plexrequests.model.request.Request;

import static com.google.common.base.Preconditions.checkNotNull;

public class RequestsUpdatedEvent {

    public RequestsUpdatedEvent(List<Request> requests) {
        checkNotNull(requests);
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    private List<Request> requests;
}
