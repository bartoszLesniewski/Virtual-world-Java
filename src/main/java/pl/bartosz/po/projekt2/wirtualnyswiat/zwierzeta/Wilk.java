package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;

public class Wilk extends Zwierze
{
    public Wilk(Swiat swiat, Point polozenie)
    {
        super(swiat, "Wilk", 9, 5, polozenie, "wilk.png");
    }
}
