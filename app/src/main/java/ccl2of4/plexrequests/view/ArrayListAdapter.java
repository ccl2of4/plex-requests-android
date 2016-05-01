package ccl2of4.plexrequests.view;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ArrayListAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<>();

    public List<T> getObjects() {
        return this.objects;
    }

    public void setObjects(@NonNull List<T> objects) {
        checkNotNull(objects);
        this.objects = objects;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    public T getObject(int position) {
        @SuppressWarnings("unchecked")
        T object = (T) getItem(position);
        return object;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
