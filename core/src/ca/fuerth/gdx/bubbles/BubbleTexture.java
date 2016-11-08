package ca.fuerth.gdx.bubbles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class BubbleTexture {

    private static final Map<Float, Texture> cache = new HashMap<Float, Texture>();

    public static Texture ofSize(float diameter) {
        Texture t;
        t = cache.get(diameter);
        if (t != null) {
            return t;
        }

        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        try {
            pixmap.setColor(Color.alpha(0));
            pixmap.fill();

            pixmap.setColor(Color.WHITE);
            pixmap.fillCircle(
                    (int) diameter / 2,
                    (int) diameter / 2,
                    (int) diameter / 2);

            t = new Texture(pixmap);
            cache.put(diameter, t);
            return t;
        } finally {
            //It's the textures responsibility now... get rid of the pixmap
            pixmap.dispose();
        }
    }
}
