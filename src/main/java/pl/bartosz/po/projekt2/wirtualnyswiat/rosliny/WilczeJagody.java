package pl.bartosz.po.projekt2.wirtualnyswiat.rosliny;

import pl.bartosz.po.projekt2.wirtualnyswiat.Organizm;
import pl.bartosz.po.projekt2.wirtualnyswiat.Roslina;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;

import java.awt.Point;

public class
WilczeJagody extends Roslina
{
    public WilczeJagody(Swiat swiat, Point polozenie)
    {
        super(swiat, "Wilcze Jagody", 99, polozenie, "wilczejagody.png");
    }

    @Override
    public void kolizja(Organizm atakujacy)
    {
        if (atakujacy instanceof Zwierze)
        {
            swiat.dodajKomunikat(atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " ginie w wyniku zjedzenia " + getGatunek() + " " + polozenieToString());
            atakujacy.zabij();
            this.zabij();
        }
    }
}
