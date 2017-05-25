package jm.projectmaliys;

import android.net.Uri;

public class DiaryModel_H {

    private String _date;
    private String _content;
    private Uri _image;
    private String _weather;

    public DiaryModel_H() {
    }

    public DiaryModel_H(String date, String content, Uri image) {
        _date = date;
        _content = content;
        _image = image;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        _date = date;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        _content = content;
    }

    public Uri getImage() {
        return _image;
    }

    public void setImage(Uri image) {
        _image = image;
    }

    public String getWeather() {
        return _weather;
    }

    public void setWeather(String weather) {
        _weather = weather;
    }
}
