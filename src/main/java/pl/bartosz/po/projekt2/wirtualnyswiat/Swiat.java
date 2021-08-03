package pl.bartosz.po.projekt2.wirtualnyswiat;

import pl.bartosz.po.projekt2.wirtualnyswiat.rosliny.*;
import pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Swiat
{
    private final Vector<Organizm> organizmy;
    private Organizm[][] plansza;
    private Czlowiek czlowiek;
    private int dlugoscPlanszy;
    private int szerokoscPlanszy;
    private final Vector<String> komunikaty;


    private static final int ILOSC_GATUNKOW_ZWIERZAT = 6;
    private static final int ILOSC_GATUNKOW_ROSLIN = 5;
    private static final double ZAPELNIENIE_SWIATA = 0.9;

    public Swiat()
    {
        dlugoscPlanszy = 0;
        szerokoscPlanszy = 0;
        organizmy = new Vector<>();
        komunikaty = new Vector<>();
    }

    public void stworzOrganizm(String gatunek, Point polozenie)
    {
        Organizm nowyOrganizm = null;

        if (gatunek.equals("Wilk"))
            nowyOrganizm = new Wilk(this, polozenie);
        else if (gatunek.equals("Owca"))
            nowyOrganizm = new Owca(this, polozenie);
        else if (gatunek.equals("Lis"))
            nowyOrganizm = new Lis(this, polozenie);
        else if (gatunek.equals("Zolw"))
            nowyOrganizm = new Zolw(this, polozenie);
        else if (gatunek.equals("Antylopa"))
            nowyOrganizm = new Antylopa(this, polozenie);
        else if (gatunek.equals("Trawa"))
            nowyOrganizm = new Trawa(this, polozenie);
        else if (gatunek.equals("Mlecz"))
            nowyOrganizm = new Mlecz(this, polozenie);
        else if (gatunek.equals("Guarana"))
            nowyOrganizm = new Guarana(this, polozenie);
        else if (gatunek.equals("Wilcze Jagody"))
            nowyOrganizm = new WilczeJagody(this, polozenie);
        else if (gatunek.equals("Barszcz Sosnowskiego"))
            nowyOrganizm = new BarszczSosnowskiego(this, polozenie);
        else if (gatunek.equals("Cyber-Owca"))
            nowyOrganizm = new CyberOwca(this, polozenie);
        else if (gatunek.equals("Czlowiek"))
        {
            nowyOrganizm = new Czlowiek(this, polozenie);
            czlowiek = (Czlowiek) nowyOrganizm;
        }

        organizmy.add(nowyOrganizm);
        plansza[(int) polozenie.getY()][(int) polozenie.getX()] = nowyOrganizm;

        dodajKomunikat("Nowy organizm: " + gatunek + " narodzil sie na polu: " + nowyOrganizm.polozenieToString());
    }

    public int getDlugoscPlanszy()
    {
        return dlugoscPlanszy;
    }

    public int getSzerokoscPlanszy()
    {
        return szerokoscPlanszy;
    }

    public Organizm getOrganizmNaPlanszy(Point pozycja)
    {
        return plansza[(int) pozycja.getY()][(int) pozycja.getX()];
    }

    public boolean czyZajetePole(Point pozycja)
    {
        if (plansza[(int) pozycja.getY()][(int) pozycja.getX()] != null)
            return true;

        return false;
    }

    public Vector<Point> getSasiednieWolnePola(Point polozenie)
    {
        Vector<Point> pola = new Vector<>();

        if (polozenie.getY() > 0)
        {
            Point pole = new Point((int) polozenie.getX(), (int) polozenie.getY() - 1);
            if (!czyZajetePole(pole))
                pola.add(pole);
        }

        if (polozenie.getY() < szerokoscPlanszy - 1)
        {
            Point pole = new Point((int) polozenie.getX(), (int) polozenie.getY() + 1);
            if (!czyZajetePole(pole))
                pola.add(pole);
        }

        if (polozenie.getX() > 0)
        {
            Point pole = new Point((int) polozenie.getX() - 1, (int) polozenie.getY());
            if (!czyZajetePole(pole))
                pola.add(pole);
        }
        if (polozenie.getX() < dlugoscPlanszy - 1)
        {
            Point pole = new Point((int) polozenie.getX() + 1, (int) polozenie.getY());
            if (!czyZajetePole(pole))
                pola.add(pole);
        }

        return pola;
    }

    public Vector<Point> getSasiednieDowolnePola(Point polozenie)
    {
        Vector<Point> pola = new Vector<>();

        if (polozenie.getY() > 0)
            pola.add(new Point((int) polozenie.getX(), (int) polozenie.getY() - 1));
        if (polozenie.getY() < szerokoscPlanszy - 1)
            pola.add(new Point((int) polozenie.getX(), (int) polozenie.getY() + 1));

        if (polozenie.getX() > 0)
            pola.add(new Point((int) polozenie.getX() - 1, (int) polozenie.getY()));
        if (polozenie.getX() < dlugoscPlanszy - 1)
            pola.add(new Point((int) polozenie.getX() + 1, (int) polozenie.getY()));

        return pola;
    }

    public Vector<Point> getPozycjeBarszczu()
    {
        Vector<Point> pozycje = new Vector<>();

        for (Organizm org : organizmy)
        {
            if (org instanceof BarszczSosnowskiego)
                pozycje.add(org.getPolozenie());
        }

        return pozycje;
    }

    public void aktualizujPolozenieNaPlanszy(Point poprzedniePolozenie, Point nowePolozenie)
    {
        plansza[(int) nowePolozenie.getY()][(int) nowePolozenie.getX()] = plansza[(int) poprzedniePolozenie.getY()][(int) poprzedniePolozenie.getX()];
        plansza[(int) poprzedniePolozenie.getY()][(int) poprzedniePolozenie.getX()] = null;
    }

    public void zamienOrganizmyNaPlanszy(Point polozenie1, Point polozenie2)
    {
        Organizm tmp = plansza[(int) polozenie1.getY()][(int) polozenie1.getX()];
        plansza[(int) polozenie1.getY()][(int) polozenie1.getX()] = plansza[(int) polozenie2.getY()][(int) polozenie2.getX()];
        plansza[(int) polozenie2.getY()][(int) polozenie2.getX()] = tmp;
    }

    public void usunOrganizmZPlanszy(Point polozenie)
    {
        plansza[(int) polozenie.getY()][(int) polozenie.getX()] = null;
    }

    public void dodajKomunikat(String komunikat)
    {
        komunikaty.add(komunikat);
    }

    public void inicjujSwiat()
    {
        stworzPlansze();
        dodajOrganizmy();
        wypelnijPlansze();
    }

    public void wykonajTure()
    {
        aktualizujWiekOrganizmow();
        sortujOrganizmy();

        int iloscOrganizmow = organizmy.size();

        for (int i = 0; i < iloscOrganizmow; i++)
        {
            if (organizmy.get(i).sprawdzCzyZyje())
                organizmy.get(i).akcja();
        }

        // usuwa nieżywe organizmy
        organizmy.removeIf(org -> !org.sprawdzCzyZyje());

        boolean czyCzlowiekZyje = false;
        for (Organizm org : organizmy)
        {
            if (org == czlowiek)
            {
                czyCzlowiekZyje = true;
                break;
            }
        }

        if (!czyCzlowiekZyje)
            czlowiek = null;
    }

    public void setWymiaryPlanszy(int dlugoscPlanszy, int szerokoscPlanszy)
    {
        this.dlugoscPlanszy = dlugoscPlanszy;
        this.szerokoscPlanszy = szerokoscPlanszy;
    }

    public Vector<String> getKomunikaty()
    {
        return komunikaty;
    }

    public Czlowiek getCzlowiek()
    {
        return czlowiek;
    }

    public void wyczyscKomunikaty()
    {
        komunikaty.clear();
    }

    public void zapiszGre(String nazwa, int nrTury)
    {
        PrintWriter zapis = null;
        boolean czySukces = false;

        try {
            zapis = new PrintWriter(nazwa);
            czySukces = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (czySukces)
        {
            zapis.println("#Wymiary planszy");
            zapis.println(dlugoscPlanszy + " " + szerokoscPlanszy);
            zapis.println("#Numer tury");
            zapis.println(nrTury);

            zapis.println("#Parametry organizmow (gatunek, polozenie(x), polozenie(y), sila, wiek), dla czlowieka dodatkowo (czyAktywnaTarczaAlzura, czasTrwaniaUmiejetnosciSpecjalnej)");

            for (Organizm org : organizmy)
            {
                zapis.print(org.getGatunek() + " " + (int)org.getPolozenie().getX() + " " + (int)org.getPolozenie().getY() + " " + org.getSila() + " " + org.getWiek());

                if (org instanceof Czlowiek)
                    zapis.print(" " + czlowiek.czyAktywnaTarczaAlzura() + " " + czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej());

                zapis.println();
            }

            zapis.close();
        }
    }

    // zwraca nr tury, jeśli udało się wczytać, w przeciwnym razie zwraca -1
    public int wczytajGre(String nazwa)
    {
        File plik = new File(nazwa);
        Scanner wejscie = null;
        boolean czySukces = false;
        int nrTury = -1;

        try {
            wejscie = new Scanner(plik);
            czySukces = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (czySukces)
        {
            String gatunek;
            int polozenieX, polozenieY, sila, wiek, czasTrwaniaUmiejetnosciSpecjalnej;
            boolean czyAktywnaTarczaAlzura;

            wejscie.nextLine();
            dlugoscPlanszy = wejscie.nextInt();
            szerokoscPlanszy = wejscie.nextInt();
            stworzPlansze();
            wejscie.nextLine();
            wejscie.nextLine();
            nrTury = wejscie.nextInt();
            wejscie.nextLine();
            wejscie.nextLine();

            while (wejscie.hasNext())
            {
                gatunek = wejscie.next();

                if (gatunek.equals("Wilcze") || gatunek.equals("Barszcz"))
                    gatunek += " " + wejscie.next();

                polozenieX = wejscie.nextInt();
                polozenieY = wejscie.nextInt();
                sila = wejscie.nextInt();
                wiek = wejscie.nextInt();

                stworzOrganizm(gatunek, new Point(polozenieX, polozenieY));
                plansza[polozenieY][polozenieX].setSila(sila);
                plansza[polozenieY][polozenieX].setWiek((wiek));

                if (gatunek.equals("Czlowiek"))
                {
                    czyAktywnaTarczaAlzura = wejscie.nextBoolean();
                    czasTrwaniaUmiejetnosciSpecjalnej = wejscie.nextInt();

                    czlowiek.setCzasTrwaniaUmiejetnosciSpecjalnej(czasTrwaniaUmiejetnosciSpecjalnej - 1);
                    // -1, bo zostanie zwiększona po rozpoczęciu symulacji
                    if (czyAktywnaTarczaAlzura)
                        czlowiek.aktywujTarczeAlzura();
                }
            }
        }

        return nrTury;
    }

    private void stworzPlansze()
    {
        plansza = new Organizm[szerokoscPlanszy][dlugoscPlanszy];

        for (int i = 0; i < szerokoscPlanszy; i++)
        {
            for (int j = 0; j < dlugoscPlanszy; j++)
                plansza[i][j] = null;
        }
    }

    private Point losujPozycje()
    {
        boolean powtorzona = false;
        int x, y;
        Random rand = new Random();

        if (organizmy.size() < dlugoscPlanszy * szerokoscPlanszy) // jeśli jest jeszcze miejsce na planszy
        {
            do
            {
                x = rand.nextInt(dlugoscPlanszy);
                y = rand.nextInt(szerokoscPlanszy);

                for (Organizm org : organizmy)
                {
                    Point polozenie = org.getPolozenie();

                    if (polozenie.getX() == x && polozenie.getY() == y)
                    {
                        powtorzona = true;
                        break;
                    }
                    else
                        powtorzona = false;
                }
            } while (powtorzona);
        }
        else
            x = y = -1;

        return new Point(x, y);
    }

    private void dodajOrganizmy()
    {
        czlowiek = new Czlowiek(this, losujPozycje());
        organizmy.add(czlowiek);

        int iloscOrganizmowJednegoGatunku = (int) (ZAPELNIENIE_SWIATA * (dlugoscPlanszy * szerokoscPlanszy)) / (ILOSC_GATUNKOW_ZWIERZAT + ILOSC_GATUNKOW_ROSLIN);
        boolean nadmiar = false;

        if (iloscOrganizmowJednegoGatunku < 1 && dlugoscPlanszy * szerokoscPlanszy > 1)
        {
            iloscOrganizmowJednegoGatunku = 1;
            nadmiar = true;
        }

        for (int i = 0; i < iloscOrganizmowJednegoGatunku; i++)
        {
            organizmy.add(new Wilk(this, losujPozycje()));
            organizmy.add(new Owca(this, losujPozycje()));
            organizmy.add(new Lis(this, losujPozycje()));
            organizmy.add(new Zolw(this, losujPozycje()));
            organizmy.add(new Antylopa(this, losujPozycje()));
            organizmy.add(new Trawa(this, losujPozycje()));
            organizmy.add(new Mlecz(this, losujPozycje()));
            organizmy.add(new Guarana(this, losujPozycje()));
            organizmy.add(new WilczeJagody(this, losujPozycje()));
            organizmy.add(new BarszczSosnowskiego(this, losujPozycje()));
            organizmy.add(new CyberOwca(this, losujPozycje()));
        }

        if (nadmiar)
            organizmy.removeIf(org -> org.getPolozenie().equals(new Point(-1, -1)));
    }

    private void wypelnijPlansze()
    {
        for (Organizm org : organizmy)
        {
            Point polozenie = org.getPolozenie();
            plansza[(int) polozenie.getY()][(int) polozenie.getX()] = org;
        }
    }

    private void rysujSwiat()
    {
        for (int i = 0; i < dlugoscPlanszy + 2; i++)
            System.out.print("# ");

        System.out.println();

        for (int i = 0; i < szerokoscPlanszy; i++)
        {
            System.out.print("# ");

            for (int j = 0; j < dlugoscPlanszy; j++)
            {
                if (plansza[i][j] != null)
                    plansza[i][j].rysowanie();
                else
                    System.out.print(".");

                System.out.print(" ");
            }

            System.out.println("#");
        }

        for (int i = 0; i < dlugoscPlanszy + 2; i++)
            System.out.print("# ");
    }

    private void sortujOrganizmy()
    {
        organizmy.sort(Comparator.comparing(Organizm::getInicjatywa, Comparator.reverseOrder()).thenComparing(Organizm::getWiek, Comparator.reverseOrder()));
    }

    private void aktualizujWiekOrganizmow()
    {
        for (Organizm org : organizmy)
            org.setWiek(org.getWiek() + 1);
    }
}
