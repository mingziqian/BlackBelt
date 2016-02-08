package com.example.amill.cse5911dojo;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import java.util.Vector;

public class ImageSprite extends Sprite
{
    private BmpWrap displayedImage;

    public ImageSprite(Rect area, BmpWrap img)
    {
        super(area);

        this.displayedImage = img;
    }

    public void saveState(Bundle map, Vector savedSprites) {
        if (getSavedId() != -1) {
            return;
        }
        super.saveState(map, savedSprites);
        map.putInt(String.format("%d-imageId", getSavedId()), displayedImage.id);
    }

    public int getTypeId()
    {
        return Sprite.TYPE_IMAGE;
    }

    public void changeImage(BmpWrap img)
    {
        this.displayedImage = img;
    }

    public final void paint(Canvas c, double scale, int dx, int dy)
    {
        Point p = super.getSpritePosition();
        drawImage(displayedImage, p.x, p.y, c, scale, dx, dy);
    }
}
