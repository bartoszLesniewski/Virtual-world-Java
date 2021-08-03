package pl.bartosz.po.projekt2.wirtualnyswiat;

import java.awt.Point;
import java.util.Vector;

public abstract class Zwierze extends Organizm
{
    @Override
    public void akcja(int zasiegRuchu)
    {
        Point nowePolozenie;
        Kierunek kierunek;
        do
        {
            kierunek = losujKierunek();
    
        } while (!czyMozliwyRuch(kierunek, zasiegRuchu));
    
        nowePolozenie = ustalNowePolozenie(kierunek, zasiegRuchu);
    
        if (swiat.czyZajetePole(nowePolozenie))
            swiat.getOrganizmNaPlanszy(nowePolozenie).kolizja(this);
        
        else
        {
            swiat.aktualizujPolozenieNaPlanszy(polozenie, nowePolozenie);
            zmienPolozenie(nowePolozenie);
        }
    }

    public boolean czyOdbilAtak(Organizm atakujacy)
    {
        if (sila <= atakujacy.getSila())
            return false;
    
        swiat.dodajKomunikat(atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " ginie podczas ataku na " + getGatunek() + " " + polozenieToString());
    
        atakujacy.zabij();
    
        return true;
    }

    @Override
    public void kolizja(Organizm atakujacy)
    {
        if (gatunek.equals(atakujacy.getGatunek()))
        {
            if (this.wiek > 0)
                rozmnazanie(atakujacy);
            else
                swiat.dodajKomunikat("Nieudana proba rozmnazania organizmu: " + atakujacy.getGatunek() + " " + atakujacy.polozenieToString() + " z " + getGatunek() + " " + polozenieToString());
        }
        else if (!czyOdbilAtak(atakujacy))
        {
            swiat.dodajKomunikat(getGatunek() + " " + polozenieToString() + " ginie w wyniku ataku " + atakujacy.getGatunek() + " " + atakujacy.polozenieToString());
    
            this.zabij();
            swiat.aktualizujPolozenieNaPlanszy(atakujacy.getPolozenie(), this.getPolozenie());
            atakujacy.zmienPolozenie(this.getPolozenie());
        }
    }

    public void rozmnazanie(Organizm rodzic)
    {
        Vector<Point> wolnePola = swiat.getSasiednieWolnePola(polozenie);
        Vector<Point> wolnePolaRodzic = swiat.getSasiednieWolnePola(rodzic.getPolozenie());
    
        wolnePola.addAll(wolnePolaRodzic);
    
        if (wolnePola.isEmpty())
            swiat.dodajKomunikat("Nieudana proba rozmnozenia " + rodzic.getGatunek() + " " + rodzic.polozenieToString() + " z " + getGatunek() + " " + polozenieToString() + " - brak miejsca");
        else
        {
            int los = losujSposrodMozliwychPol(wolnePola);
            swiat.stworzOrganizm(getGatunek(), wolnePola.get(los));
        }
    }

    protected Zwierze(Swiat swiat, String gatunek, int sila, int inicjatywa, Point polozenie, String ikona)
    {
        super(swiat, gatunek, sila, inicjatywa, polozenie, ikona);
    }
}
