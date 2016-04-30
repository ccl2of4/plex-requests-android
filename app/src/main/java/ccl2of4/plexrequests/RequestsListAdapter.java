package ccl2of4.plexrequests;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import ccl2of4.plexrequests.model.request.Request;

public abstract class RequestsListAdapter extends BaseAdapter {

    private List<Request> requests;

    public List<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return requests.get(position);
    }

    public Request getRequest(int position) {
        return (Request) getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
