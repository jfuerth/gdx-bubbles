package ca.fuerth.gdx.bubbles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.ceil;
import static com.badlogic.gdx.math.MathUtils.log2;
import static com.badlogic.gdx.math.MathUtils.nextPowerOfTwo;
import static com.sun.corba.se.impl.util.RepositoryId.cache;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class BubbleTexture {

    public static Texture ofSize(float diameter) {
        int size = nextPowerOfTwo(ceil(diameter));
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        try {
            pixmap.setColor(Color.alpha(0));
            pixmap.fill();

            pixmap.setColor(Color.WHITE);
            pixmap.fillCircle(
                    (int) diameter / 2,
                    (int) diameter / 2,
                    (int) diameter / 2);

            return new Texture(pixmap);
        } finally {
            pixmap.dispose();
        }
    }
}
