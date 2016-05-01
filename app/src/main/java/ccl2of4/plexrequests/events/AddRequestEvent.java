package ccl2of4.plexrequests.events;

import static com.google.common.base.Preconditions.checkNotNull;

import ccl2of4.plexrequests.model.request.Request;

public class AddRequestEvent {

    public AddRequestEvent(Request request) {
        checkNotNull(request);
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    private Request request;

}
