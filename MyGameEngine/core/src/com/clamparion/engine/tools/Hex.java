package com.clamparion.engine.tools;

import com.badlogic.gdx.graphics.Color;

public class Hex {

    public static Color toColor(String hexcolor) {
        hexcolor = cutHex(hexcolor);
        if (hexcolor.length() == 6) {
            int r = Integer.parseInt(hexcolor.substring(0, 2), 16);
            int g = Integer.parseInt(hexcolor.substring(2, 4), 16);
            int b = Integer.parseInt(hexcolor.substring(4, 6), 16);

            return new Color(r / 255f, g / 255f, b / 255f, 1f);
        }
        return new Color(0f, 0f, 0f, 1f);
    }

    private static String cutHex(String h) {
        return (h.charAt(0) == '#') ? h.substring(1, 7) : h;

    }


    public static String toHex(Color color) {

        String r = Integer.toHexString((int) (color.r * 255f));
        String g = Integer.toHexString((int) (color.g * 255f));
        String b = Integer.toHexString((int) (color.b * 255f));

        if (r.length() == 1)
            r = "0" + r;
        if (g.length() == 1)
            g = "0" + g;
        if (b.length() == 1)
            b = "0" + b;

        return "#" + r + "" + g + "" + b;
    }

}
