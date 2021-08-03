package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;

public class Owca extends Zwierze
{
    public Owca(Swiat swiat, Point polozenie)
    {
        super(swiat, "Owca", 4, 4, polozenie, "owca.png");
    }
}
