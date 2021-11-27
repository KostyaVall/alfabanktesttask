package ru.alfabank.myjob.alfabanktesttask.model;

public class Gif {

    private DataClient data;
    private String tag;

    public Gif(DataClient data, String tag) {

        this.data = data;
        this.tag = tag;
    }

    public Gif() {
    }

    public DataClient getData() {
        return data;
    }

    public void setData(DataClient data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {

        return "Gif{" +
                "data=" + data +
                "tag=" + tag +
                '}';
    }
}
