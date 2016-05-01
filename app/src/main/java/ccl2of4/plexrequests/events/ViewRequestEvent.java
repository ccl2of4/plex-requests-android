package ccl2of4.plexrequests.events;

import ccl2of4.plexrequests.model.request.Request;

import static com.google.common.base.Preconditions.checkNotNull;

public class ViewRequestEvent {

    public ViewRequestEvent(Request request) {
        checkNotNull(request);
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    private Request request;

}
