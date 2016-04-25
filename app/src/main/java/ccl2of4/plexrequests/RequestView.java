package ccl2of4.plexrequests;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ccl2of4.plexrequests.model.request.Request;

@EViewGroup(R.layout.view_request)
public class RequestView extends LinearLayout {

    @ViewById(R.id.name)
    TextView nameTextView;

    @ViewById(R.id.summary)
    TextView summaryTextView;

    @ViewById(R.id.image_view)
    ImageView imageView;

    private Request request;

    public RequestView(Context context) {
        super(context);
    }

    public void setRequest(Request request) {
        this.request = request;
        update();
    }

    private void update() {
        nameTextView.setText(request.getName());
        summaryTextView.setText(request.getSummary());
        setImage();
    }

    private void setImage() {
        Picasso.with(getContext())
                .load(request.getPosterPath())
                .into(imageView);
    }

    public Request getRequest() {
        return request;
    }

}
