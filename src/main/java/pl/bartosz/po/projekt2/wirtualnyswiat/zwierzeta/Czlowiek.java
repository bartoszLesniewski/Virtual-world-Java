package pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta;

import pl.bartosz.po.projekt2.wirtualnyswiat.Kierunek;
import pl.bartosz.po.projekt2.wirtualnyswiat.Organizm;
import pl.bartosz.po.projekt2.wirtualnyswiat.Swiat;
import pl.bartosz.po.projekt2.wirtualnyswiat.Zwierze;

import java.awt.*;
import java.util.Vector;

public class Czlowiek extends Zwierze
{
    private Kierunek kierunekRuchu;
    private boolean tarczaAlzura;
    private int czasTrwaniaUmiejetnosciSpecjalnej;

    public Czlowiek(Swiat swiat, Point polozenie)
    {
        super(swiat, "Czlowiek", 5, 4,polozenie, "czlowiek.png");
        kierunekRuchu = Kierunek.NIEZDEFINIOWANY;
        tarczaAlzura = false;
        czasTrwaniaUmiejetnosciSpecjalnej = 0;
    }

    public void setKierunekRuchu(Kierunek kierunek)
    {
        kierunekRuchu = kierunek;
    }

    public void aktywujTarczeAlzura()
    {
        tarczaAlzura = true;
    }

    public void dezaktywujTarczeAlzura()
    {
        tarczaAlzura = false;
    }

    public boolean czyAktywnaTarczaAlzura()
    {
        return tarczaAlzura;
    }

    public int getCzasTrwaniaUmiejetnosciSpecjalnej()
    {
        return czasTrwaniaUmiejetnosciSpecjalnej;
    }

    public void setCzasTrwaniaUmiejetnosciSpecjalnej(int wartosc)
    {
        czasTrwaniaUmiejetnosciSpecjalnej = wartosc;
    }

    @Override
    public void akcja(int zasiegRuchu)
    {
        if (kierunekRuchu != Kierunek.NIEZDEFINIOWANY)
        {
            Point nowePolozenie = ustalNowePolozenie(kierunekRuchu, zasiegRuchu);

            if (swiat.czyZajetePole(nowePolozenie))
                swiat.getOrganizmNaPlanszy(nowePolozenie).kolizja(this);

            else
            {
                swiat.aktualizujPolozenieNaPlanszy(polozenie, nowePolozenie);
                //swiat.dodajKomunikat(this.getGatunek() + " zmienia swoje polozenie z " + this.polozenieToString() + " na " + "(" + nowePolozenie.getX() + ", " + nowePolozenie.getY() + ")"); // usunac
                zmienPolozenie(nowePolozenie);
            }

            kierunekRuchu = Kierunek.NIEZDEFINIOWANY;
        }
    }

    public boolean czyOdbilAtak(Organizm atakujacy)
    {
        if (tarczaAlzura)
        {
            Vector<Point> sasiedniePola = swiat.getSasiednieDowolnePola(polozenie);
            int los = losujSposrodMozliwychPol(sasiedniePola);

            swiat.dodajKomunikat(gatunek + " " + polozenieToString() + " odgania " + atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " na pozycje " + "(" + sasiedniePola.get(los).x + ", " + sasiedniePola.get(los).y+ ")");

            if (!swiat.czyZajetePole(sasiedniePola.get(los)))
            {
                swiat.aktualizujPolozenieNaPlanszy(atakujacy.getPolozenie(), sasiedniePola.get(los));
                atakujacy.zmienPolozenie(sasiedniePola.get(los));
            }
            else if (sasiedniePola.get(los) != atakujacy.getPolozenie())
                swiat.getOrganizmNaPlanszy(sasiedniePola.get(los)).kolizja(atakujacy);

            return true;
        }

        boolean wynikWalki = super.czyOdbilAtak(atakujacy);
        return wynikWalki;
    }
}
