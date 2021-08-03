package pl.bartosz.po.projekt2.wirtualnyswiat.rosliny;

import pl.bartosz.po.projekt2.wirtualnyswiat.Roslina;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;

public class Trawa extends Roslina
{
    public Trawa(Swiat swiat, Point polozenie)
    {
        super(swiat, "Trawa", 0, polozenie, "trawa.png");
    }
}
