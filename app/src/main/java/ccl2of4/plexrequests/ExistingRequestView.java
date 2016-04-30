package ccl2of4.plexrequests;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ccl2of4.plexrequests.model.request.Request;

@EViewGroup(R.layout.view_existing_request)
public class ExistingRequestView extends LinearLayout {

    @ViewById(R.id.name)
    TextView nameTextView;

    @ViewById(R.id.date)
    TextView dateTextView;

    private Request request;

    public ExistingRequestView(Context context) {
        super(context);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
        update();
    }

    private void update() {
        nameTextView.setText(request.getName());
        dateTextView.setText(request.getDate());
    }

    @Click(R.id.view)
    void view() {

    }

    @Click(R.id.mark_complete)
    void markComplete() {

    }

    @Click(R.id.delete)
    void delete() {

    }

}
