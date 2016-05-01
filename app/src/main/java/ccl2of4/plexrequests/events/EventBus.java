package ccl2of4.plexrequests.events;

import com.squareup.otto.Bus;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class EventBus extends Bus {}
