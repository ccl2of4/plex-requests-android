package ccl2of4.plexrequests.model;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import org.androidannotations.annotations.EBean;

import ccl2of4.plexrequests.model.comment.CommentRepository;
import ccl2of4.plexrequests.model.request.RequestRepository;
import ccl2of4.plexrequests.model.search.SearchRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EBean
public class RepositoryFactory {

    public CommentRepository getCommentRepository() {
        commentRepository = commentRepository != null ? commentRepository :
                getRetrofit().create(CommentRepository.class);
        return commentRepository;
    }

    public RequestRepository getRequestRepository() {
        requestRepository = requestRepository != null ? requestRepository :
                getRetrofit().create(RequestRepository.class);
        return requestRepository;
    }

    public SearchRepository getSearchRepository() {
        searchRepository = searchRepository != null ? searchRepository :
                getRetrofit().create(SearchRepository.class);
        return searchRepository;
    }

    private Retrofit getRetrofit() {
        retrofit = retrofit != null ? retrofit :
                new Retrofit.Builder()
                        .baseUrl("http://192.168.0.5:5000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        return retrofit;
    }

    private Retrofit retrofit;
    private CommentRepository commentRepository;
    private RequestRepository requestRepository;
    private SearchRepository searchRepository;

}
