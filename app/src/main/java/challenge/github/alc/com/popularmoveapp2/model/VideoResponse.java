package challenge.github.alc.com.popularmoveapp2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gino Osahon on 19/06/2017.
 */
public class VideoResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Videos> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Videos> getResults() {
        return results;
    }

    public void setResults(List<Videos> results) {
        this.results = results;
    }

    public int size(){
        return results.size();
    }
}
