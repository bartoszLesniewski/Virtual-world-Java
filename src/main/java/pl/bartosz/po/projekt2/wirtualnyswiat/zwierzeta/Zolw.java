package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Organizm;
import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;
import java.util.Random;

public class Zolw extends Zwierze
{
    public Zolw(Swiat swiat, Point polozenie)
    {
        super(swiat, "Zolw", 2, 1, polozenie, "zolw.png");
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        Random rand = new Random();
        int los = rand.nextInt(100) + 1;

        if (los <= 25)
            super.akcja(1);
        //else
            //swiat.dodajKomunikat(getGatunek() + " " + polozenieToString() + " nie zmienia swojej pozycji");
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy)
    {
        if (atakujacy.getSila() < 5)
        {
            swiat.dodajKomunikat(getGatunek() + " " + polozenieToString() + " odpiera atak " + atakujacy.getGatunek() + " " + atakujacy.polozenieToString());

            return true;
        }

        return false;
    }

}
