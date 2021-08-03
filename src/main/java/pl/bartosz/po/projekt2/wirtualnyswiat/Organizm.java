package pl.bartosz.po.projekt2.wirtualnyswiat;

import javax.swing.ImageIcon;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

public abstract class Organizm
{
    protected Swiat swiat;
    protected int sila;
    protected int inicjatywa;
    protected Point polozenie;
    protected String ikona;
    protected boolean czyZyje;
    protected int wiek;
    protected String gatunek;

    public abstract void akcja(int zasiegRuchu) ;

    public void akcja()
    {
        akcja(1);
    }

    public abstract void kolizja(Organizm atakujacy);

    public ImageIcon rysowanie()
    {
        return new ImageIcon("ikony/" + ikona);
    }

    public int getSila()
    {
        return sila;
    }

    public void setSila(int wartosc)
    {
        sila = wartosc;
    }

    public int getInicjatywa()
    {
        return inicjatywa;
    }

    public Point getPolozenie()
    {
        return polozenie;
    }

    public int getWiek()
    {
        return wiek;
    }

    public void setWiek(int nowyWiek)
    {
        wiek = nowyWiek;
    }

    public String getGatunek()
    {
        return gatunek;
    }

    public boolean sprawdzCzyZyje()
    {
        return czyZyje;
    }

    public void zabij()
    {
        czyZyje = false;
        swiat.usunOrganizmZPlanszy(getPolozenie());
    }

    public String polozenieToString()
    {
        String polozenieString = "(" + (int)polozenie.getX() + ", " + (int)polozenie.getY() + ")";

        return polozenieString;
    }

    public boolean czyMozliwyRuch(Kierunek kierunek, int zasiegRuchu)
    {
        Point tmpPolozenie = new Point(polozenie);

        if (kierunek == Kierunek.LEWO)
            tmpPolozenie.x -= zasiegRuchu;
        else if (kierunek == Kierunek.PRAWO)
            tmpPolozenie.x += zasiegRuchu;
        else if (kierunek == Kierunek.GORA)
            tmpPolozenie.y -= zasiegRuchu;
        else if (kierunek == Kierunek.DOL)
            tmpPolozenie.y += zasiegRuchu;

        if (tmpPolozenie.x < 0 || tmpPolozenie.x >= swiat.getDlugoscPlanszy())
            return false;

        if (tmpPolozenie.y < 0 || tmpPolozenie.y >= swiat.getSzerokoscPlanszy())
            return false;

        return true;
    }

    public void zmienPolozenie(Point nowePolozenie)
    {
        //std::string komunikat = getGatunek() + " " + polozenieToString() + " zmienia swoje polozenie na ";

        polozenie = nowePolozenie;

        //komunikat += polozenieToString();
        //swiat.dodajKomunikat(komunikat);
    }

    protected Organizm(Swiat swiat, String gatunek, int sila, int inicjatywa, Point polozenie, String ikona, boolean czyZyje, int wiek)
    {
        this.swiat = swiat;
        this.gatunek = gatunek;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.polozenie = polozenie;
        this.ikona = ikona;
        this.czyZyje = czyZyje;
        this.wiek = wiek;
    }

    protected Organizm(Swiat swiat, String gatunek, int sila, int inicjatywa, Point polozenie, String ikona)
    {
        this(swiat, gatunek, sila, inicjatywa, polozenie, ikona, true, 0);
    }

    protected Point ustalNowePolozenie(Kierunek kierunek, int zasiegRuchu)
    {
        Point nowePolozenie = new Point(polozenie);

        if (kierunek == Kierunek.LEWO)
            nowePolozenie.x -= zasiegRuchu;
        else if (kierunek == Kierunek.PRAWO)
            nowePolozenie.x += zasiegRuchu;
        else if (kierunek == Kierunek.GORA)
            nowePolozenie.y -= zasiegRuchu;
        else if (kierunek == Kierunek.DOL)
            nowePolozenie.y += zasiegRuchu;

        return nowePolozenie;
    }

    protected Kierunek losujKierunek()
    {
        Random rand = new Random();
        int x = rand.nextInt(4);

        return Kierunek.values()[x];
    }

    protected int losujSposrodMozliwychPol(Vector<Point> wolnePola)
    {
        int los;
        Random rand = new Random();
        if (wolnePola.size() > 1)
            los = rand.nextInt(wolnePola.size());
        else
            los = 0;

        return los;
    }
}
