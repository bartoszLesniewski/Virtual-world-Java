package pl.bartosz.po.projekt2.wirtualnyswiat.rosliny;

import pl.bartosz.po.projekt2.wirtualnyswiat.Organizm;
import pl.bartosz.po.projekt2.wirtualnyswiat.Roslina;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta.CyberOwca;

import java.awt.Point;
import java.util.Vector;

public class BarszczSosnowskiego extends Roslina
{
    public BarszczSosnowskiego(Swiat swiat, Point polozenie)
    {
        super(swiat, "Barszcz Sosnowskiego", 10, polozenie, "barszczsosnowskiego.png");
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        Vector<Point> wolnePola = swiat.getSasiednieDowolnePola(polozenie);

        for (Point pole : wolnePola)
        {
            Organizm tmp = swiat.getOrganizmNaPlanszy(pole);
            if (tmp != null && tmp instanceof Zwierze && !(tmp instanceof CyberOwca))
            {
                swiat.dodajKomunikat(gatunek + " " + polozenieToString() + " zabija " + tmp.getGatunek() + " " + tmp.polozenieToString());
                tmp.zabij();
            }
        }
    }

    @Override
    public void kolizja(Organizm atakujacy)
    {
        if (atakujacy instanceof CyberOwca)
        {
            swiat.dodajKomunikat(atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " zjada " + getGatunek() + " " + polozenieToString());
            this.zabij();
            swiat.aktualizujPolozenieNaPlanszy(atakujacy.getPolozenie(), polozenie);
            atakujacy.zmienPolozenie(polozenie);
        }
        else
        {
            swiat.dodajKomunikat(atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " ginie w wyniku zjedzenia " + getGatunek() + " " + polozenieToString());
            atakujacy.zabij();
            this.zabij();
        }
    }
}
