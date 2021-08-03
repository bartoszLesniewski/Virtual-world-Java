package pl.bartosz.po.projekt2.wirtualnyswiat.rosliny;

import pl.bartosz.po.projekt2.wirtualnyswiat.Organizm;
import pl.bartosz.po.projekt2.wirtualnyswiat.Roslina;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;

public class Guarana extends Roslina
{
    public Guarana(Swiat swiat, Point polozenie)
    {
        super(swiat, "Guarana", 0, polozenie, "guarana.png");
    }

    @Override
    public void kolizja(Organizm atakujacy)
    {
        super.kolizja(atakujacy);
        atakujacy.setSila(atakujacy.getSila() + 3);
    }
}
