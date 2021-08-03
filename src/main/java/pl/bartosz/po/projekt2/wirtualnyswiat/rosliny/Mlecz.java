package pl.bartosz.po.projekt2.wirtualnyswiat.rosliny;

import pl.bartosz.po.projekt2.wirtualnyswiat.Roslina;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;

public class Mlecz extends Roslina
{
    public Mlecz(Swiat swiat, Point polozenie)
    {
        super(swiat, "Mlecz", 0, polozenie, "mlecz.png");
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        for (int i = 1; i <= 3; i++)
            super.akcja(1);
    }
}
