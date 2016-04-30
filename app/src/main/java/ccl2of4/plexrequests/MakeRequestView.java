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

@EViewGroup(R.layout.view_make_request)
public class MakeRequestView extends LinearLayout {

    @ViewById(R.id.name)
    TextView nameTextView;

    @ViewById(R.id.date)
    TextView dateTextView;

    @ViewById(R.id.summary)
    TextView summaryTextView;

    @ViewById(R.id.image_view)
    ImageView imageView;

    private Request request;

    public MakeRequestView(Context context) {
        super(context);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
        update();
    }

    @Click(R.id.add)
    void add() {

    }

    private void update() {
        nameTextView.setText(request.getName());
        summaryTextView.setText(request.getSummary());
        dateTextView.setText(request.getDate());
        setImage();
    }

    private void setImage() {
        Picasso.with(getContext())
                .load(request.getPosterPath())
                .into(imageView);
    }

}