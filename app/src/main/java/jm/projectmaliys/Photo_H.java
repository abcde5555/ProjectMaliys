package jm.projectmaliys;

import android.graphics.drawable.Drawable;

// 일별 갤러리에 들어갈 데이터셋 모델
public class Photo_H {
    
    //region drawableImage
    private Drawable _drawableImage;

    public Drawable getDrawableImage() {
        return _drawableImage;
    }

    public void setDrawableImage(Drawable drawableImage) {
        _drawableImage = drawableImage;
    }
    //endregion
    
    //region shortenText
    private String _shortenText;
      
    public String getShortenText() {
        return _shortenText;
    }
      
    public void setShortenText(String shortenText) {
        _shortenText = shortenText;
    }
    //endregion
}
