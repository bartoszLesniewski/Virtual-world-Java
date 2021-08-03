package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Organizm;
import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

public class Antylopa extends Zwierze
{
    public Antylopa(Swiat swiat, Point polozenie)
    {
        super(swiat, "Antylopa", 4, 4, polozenie, "antylopa.png");
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        Random rand = new Random();
        int zasieg = rand.nextInt(2) + 1;

        super.akcja(zasieg);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy)
    {
        Random rand = new Random();
        int szansa = rand.nextInt(100) + 1;

        if (szansa <= 50)
        {
            String komunikat = getGatunek() + " " + polozenieToString() + " ucieka przed walka na pole ";

            Point polozenieAtakujacego = atakujacy.getPolozenie();
            Vector<Point> wolneMiejsca = swiat.getSasiednieWolnePola(polozenie);
            wolneMiejsca.add(polozenieAtakujacego);
            int los = losujSposrodMozliwychPol(wolneMiejsca);

            if (wolneMiejsca.get(los) == polozenieAtakujacego)
                swiat.zamienOrganizmyNaPlanszy(polozenie, polozenieAtakujacego);

            else
            {
                swiat.aktualizujPolozenieNaPlanszy(polozenie, wolneMiejsca.get(los));
                swiat.aktualizujPolozenieNaPlanszy(polozenieAtakujacego, polozenie);
            }

            atakujacy.zmienPolozenie(polozenie);
            polozenie = wolneMiejsca.get(los);

            komunikat += polozenieToString();
            swiat.dodajKomunikat(komunikat);

            return true;
        }

        return false;
    }
}
