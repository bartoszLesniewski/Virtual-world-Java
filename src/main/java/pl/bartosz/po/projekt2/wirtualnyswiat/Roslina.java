package pl.bartosz.po.projekt2.wirtualnyswiat;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

public abstract class Roslina extends Organizm
{
    @Override
    public void akcja(int zasiegRuchu)
    {
        Random rand = new Random();
        int szansa = rand.nextInt(100) + 1;

        if (szansa <= 10)
        {
            Vector<Point> wolnePola = swiat.getSasiednieWolnePola(polozenie);

            if (!wolnePola.isEmpty())
            {
                int los = losujSposrodMozliwychPol(wolnePola);
                swiat.stworzOrganizm(gatunek, wolnePola.get(los));
            }
        }
    }

    @Override
    public void kolizja(Organizm atakujacy)
    {
        swiat.dodajKomunikat(atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " zjada " + gatunek + " " + polozenieToString());
        this.zabij();
        swiat.aktualizujPolozenieNaPlanszy(atakujacy.getPolozenie(), this.getPolozenie());
        atakujacy.zmienPolozenie(this.getPolozenie());
    }

    protected Roslina (Swiat swiat, String gatunek, int sila, Point polozenie, String ikona)
    {
        super(swiat, gatunek, sila, 0, polozenie, ikona);
    }
}
