package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import java.awt.Point;
import java.util.Vector;

public class Lis extends Zwierze
{
    public Lis(Swiat swiat, Point polozenie)
    {
        super(swiat, "Lis", 3, 7, polozenie, "lis.png");
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        Vector<Point> wolnePola = wybierzBezpiecznePola();
        if (!wolnePola.isEmpty())
        {
            int los = losujSposrodMozliwychPol(wolnePola);

            Point nowePolozenie = wolnePola.get(los);

            if (swiat.czyZajetePole(nowePolozenie))
                swiat.getOrganizmNaPlanszy(nowePolozenie).kolizja(this);

            else
            {
                swiat.aktualizujPolozenieNaPlanszy(polozenie, nowePolozenie);
                zmienPolozenie(nowePolozenie);
            }
        }
        //else
            //swiat.dodajKomunikat("Lis nie rusza sie z pola " + polozenieToString() + ", bo nie jest bezpiecznie");
    }

    private Vector<Point> wybierzBezpiecznePola()
    {
        Vector<Point> wolnePola = swiat.getSasiednieDowolnePola(polozenie);

        wolnePola.removeIf(pole -> (swiat.getOrganizmNaPlanszy(pole) != null && swiat.getOrganizmNaPlanszy(pole).getSila() > this.sila));

        return wolnePola;
    }
}
