package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;
import java.awt.Point;
import java.util.Vector;

public class CyberOwca extends Zwierze
{
    private Point wyszukajNajblizszyBarszcz(Vector<Point> pozycjeBarszczu)
    {
        double odleglosc, minOdlelglosc = -1;
        Point najblizszy = null;

        for (Point pozycjaBarszczu : pozycjeBarszczu)
        {
            odleglosc = Math.sqrt(Math.pow(pozycjaBarszczu.getX() - this.polozenie.getX(), 2) + Math.pow(pozycjaBarszczu.getY() - this.polozenie.getY(), 2));

            if (odleglosc < minOdlelglosc || najblizszy == null)
            {
                minOdlelglosc = odleglosc;
                najblizszy = pozycjaBarszczu;
            }
        }

        return najblizszy;
    }

    public CyberOwca(Swiat swiat, Point polozenie)
    {
        super(swiat, "Cyber-Owca", 11, 4, polozenie, "cyberowca.png");
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        Vector<Point> pozycjeBarszczu = swiat.getPozycjeBarszczu();
        if (pozycjeBarszczu.isEmpty())
            super.akcja(1);
        else
        {
            Point najblizszy = wyszukajNajblizszyBarszcz(pozycjeBarszczu);
            Point nowePolozenie;
            if (najblizszy.getX() < this.polozenie.getX())
                nowePolozenie = new Point((int)polozenie.getX() - 1, (int)polozenie.getY());
            else if (najblizszy.getX() > this.polozenie.getX())
                nowePolozenie = new Point((int)polozenie.getX() + 1, (int)polozenie.getY());
            else if (najblizszy.getY() < this.polozenie.getY())
                nowePolozenie = new Point((int)polozenie.getX(), (int)polozenie.getY() - 1);
            else
                nowePolozenie = new Point((int)polozenie.getX(), (int)polozenie.getY() + 1);

            if (swiat.czyZajetePole(nowePolozenie))
                swiat.getOrganizmNaPlanszy(nowePolozenie).kolizja(this);

            else
            {
                swiat.aktualizujPolozenieNaPlanszy(polozenie, nowePolozenie);
                zmienPolozenie(nowePolozenie);
            }
        }
    }
}
