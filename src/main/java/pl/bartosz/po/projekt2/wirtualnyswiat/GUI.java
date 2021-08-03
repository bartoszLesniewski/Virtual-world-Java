package pl.bartosz.po.projekt2.wirtualnyswiat;

import pl.bartosz.po.projekt2.wirtualnyswiat.zwierzeta.Czlowiek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class GUI
{
    private final JFrame frame;
    private final JPanel panel;
    private JButton[][] przyciskiPlanszy;
    private final SluchaczAkcji sluchacz;
    private static final int SZEROKOSC_OKNA = 1024;
    private static final int DLUGOSC_OKNA = 600;
    private Swiat swiat;
    private int nrTury;
    private final String[] nazwyOrganizmow;

    public GUI()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JFrame.setDefaultLookAndFeelDecorated(true);
        }

        frame = new JFrame("Wirtualny Świat, Bartosz Leśniewski 184783");
        panel = new JPanel();
        sluchacz = new SluchaczAkcji();
        nrTury = 0;
        nazwyOrganizmow = new String[]{
                "Czlowiek",
                "Wilk",
                "Owca",
                "Lis",
                "Zolw",
                "Antylopa",
                "Cyber-Owca",
                "Trawa",
                "Mlecz",
                "Guarana",
                "Wilcze Jagody",
                "Barszcz Sosnowskiego"
        };
    }

    public void menu()
    {
        panel.setPreferredSize(new Dimension(SZEROKOSC_OKNA, DLUGOSC_OKNA));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("<html><h1>Wirtualny Świat</h1></html>", JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 80)));
        panel.add(label);

        dodajPrzyciskMenu(panel, "Nowa gra");
        dodajPrzyciskMenu(panel, "Wczytaj grę");
        dodajPrzyciskMenu(panel, "Wyjście");

        frame.add(panel);
        frame.pack();
        frame.addKeyListener(sluchacz);
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                frame.requestFocusInWindow();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        swiat = new Swiat();
    }

    private void dodajPrzyciskMenu(JPanel panel, String nazwa)
    {
        JButton btn = new JButton(nazwa);
        btn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btn.addActionListener(sluchacz);

        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(btn);
    }

    private class SluchaczAkcji implements ActionListener, KeyListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() instanceof JMenuItem)
            {
                int x = (int) ((JMenuItem) e.getSource()).getClientProperty("x");
                int y = (int) ((JMenuItem) e.getSource()).getClientProperty("y");
                swiat.stworzOrganizm(e.getActionCommand(), new Point(x, y));
                Czlowiek czlowiek = swiat.getCzlowiek();
                if (czlowiek != null)
                    czlowiek.setCzasTrwaniaUmiejetnosciSpecjalnej(czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() - 1);
                rysujSwiat();
            }
            else
            {
                String buttonName = e.getActionCommand();
                switch (buttonName) {
                    case "Nowa gra":
                        wczytajDane();
                        break;
                    case "Wczytaj grę":
                        wyswietlOknoWczytywania();
                        break;
                    case "Wyjście":
                        frame.dispose();
                        break;
                    case "Przejdź do następnej tury":
                        nastepnaTura();
                        break;
                    case "Zapisz grę":
                        wyswietlOknoZapisu();
                        break;
                    case "Pomoc":
                        wyswietlPomoc();
                        break;
                    default:
                        wyswietlPopupmenu((JButton) e.getSource());
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case 'x': case 'X':
                    aktywacjaUmiejetnociSpecjalnej();
                    break;
                case KeyEvent.VK_SPACE:
                    nastepnaTura();
                    break;
                case KeyEvent.VK_UP:
                    ruchCzlowieka(Kierunek.GORA);
                    break;
                case KeyEvent.VK_DOWN:
                    ruchCzlowieka(Kierunek.DOL);
                    break;
                case KeyEvent.VK_RIGHT:
                    ruchCzlowieka(Kierunek.PRAWO);
                    break;
                case KeyEvent.VK_LEFT:
                    ruchCzlowieka(Kierunek.LEWO);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private void wczytajDane()
    {
        int dlugosc = 0, szerokosc = 0;
        String strDlugosc, strSzerokosc = null;
        boolean czyPrawidlowaDlugosc;
        boolean czyPrawidlowaSzerokosc;

        do
        {
            strDlugosc = JOptionPane.showInputDialog(frame, "Podaj długość planszy:");
            czyPrawidlowaDlugosc = true;
            if (strDlugosc != null)
            {
                try {
                    dlugosc = Integer.parseInt(strDlugosc);
                    if (dlugosc <= 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    czyPrawidlowaDlugosc = false;
                    JOptionPane.showMessageDialog(frame, "Nieprawidłowe dane", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while (!czyPrawidlowaDlugosc);

        if (strDlugosc != null)
        {
            do
            {
                strSzerokosc = JOptionPane.showInputDialog(frame, "Podaj szerokość planszy:");
                czyPrawidlowaSzerokosc = true;
                if (strSzerokosc != null)
                {
                    try {
                        szerokosc = Integer.parseInt(strSzerokosc);
                        if (szerokosc <= 0)
                            throw new NumberFormatException();
                    } catch (NumberFormatException e) {
                        czyPrawidlowaSzerokosc = false;
                        JOptionPane.showMessageDialog(frame, "Nieprawidłowe dane", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } while (!czyPrawidlowaSzerokosc);
        }

        if (strDlugosc != null && strSzerokosc != null)
        {
            swiat.setWymiaryPlanszy(dlugosc, szerokosc);
            swiat.inicjujSwiat();

            rysujSwiat();
        }
    }

    private void nastepnaTura()
    {
        swiat.wykonajTure();
        nrTury++;
        rysujSwiat();
    }

    private void wyswietlOknoWczytywania()
    {
        String nazwaPliku;

        nazwaPliku = JOptionPane.showInputDialog(frame, "Podaj nazwę pliku, z którego chcesz odczytać stan gry:");

        if (nazwaPliku != null)
        {
            nrTury = swiat.wczytajGre(nazwaPliku);

            if (nrTury == -1)
                JOptionPane.showMessageDialog(frame, "Wczytywanie stanu gry nie powiodło się. Spróbuj ponownie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            else
                rysujSwiat();
        }
    }

    private void wyswietlOknoZapisu()
    {
        String nazwaPliku;

        nazwaPliku = JOptionPane.showInputDialog(frame, "Podaj nazwę pliku, w którym chcesz zapisać stan gry:");

        if (nazwaPliku != null)
            swiat.zapiszGre(nazwaPliku, nrTury);
    }

    private void wyswietlPomoc()
    {
        String tekstPomoc = "Podstawowe sterowanie:\n Strzałki - poruszanie człowiekiem\n X - aktywacja umiejętności specjalnej człowieka\n Spacja lub przycisk \"Przejdź do następnej tury\" - przejście do następnej tury (bez ruchu człowieka)";
        JOptionPane.showMessageDialog(frame, tekstPomoc);
    }

    private void wyswietlPopupmenu(JButton btn)
    {
        JPopupMenu menu = new JPopupMenu("Menu");

        for (String nazwa : nazwyOrganizmow)
        {
            JMenuItem pozycjaMenu = new JMenuItem(nazwa);
            int pozycjaX = (int)btn.getClientProperty("x");
            int pozycjaY = (int)btn.getClientProperty("y");

            pozycjaMenu.putClientProperty("x", pozycjaX);
            pozycjaMenu.putClientProperty("y", pozycjaY);
            pozycjaMenu.addActionListener(sluchacz);

            if (nazwa.equals("Czlowiek") && swiat.getCzlowiek() != null)
                pozycjaMenu.setEnabled(false);

            menu.add(pozycjaMenu);
        }

        menu.show(btn, btn.getWidth()/2, btn.getHeight()/2);
    }

    private void aktywacjaUmiejetnociSpecjalnej()
    {
        Czlowiek czlowiek = swiat.getCzlowiek();

        if (czlowiek != null)
        {
            if (czlowiek.czyAktywnaTarczaAlzura())
                JOptionPane.showMessageDialog(frame,"Specjalna umiejetnosc czlowieka - Tarcza Alzura - jest juz aktywna.");

            else if (czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() > 0)
                JOptionPane.showMessageDialog(frame, "Nie mozna aktywowac specjalnej umiejetnosci czlowieka - Tarcza Alzura. Ponowna aktywacja bedzie mozliwa za " + czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() + " tur(y).", "Błąd", JOptionPane.ERROR_MESSAGE);

            else
            {
                JOptionPane.showMessageDialog(frame,"Specjalna umiejetnosc czlowieka - Tarcza Alzura - zostala aktywowana.");
                czlowiek.aktywujTarczeAlzura();
            }
        }
        else {
            JOptionPane.showMessageDialog(frame, "Nie mozna aktywowac umiejetnosci specjalnej - czlowiek nie zyje.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ruchCzlowieka(Kierunek kierunek)
    {
        Czlowiek czlowiek = swiat.getCzlowiek();

        if (czlowiek != null)
        {
            if (czlowiek.czyMozliwyRuch(kierunek, 1))
            {
                czlowiek.setKierunekRuchu(kierunek);
                nastepnaTura();
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "Nie można wykonać ruchu w tę stronę. Spróbuj ponownie...", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "Człowiek nie żyje - sterowanie nim nie jest możliwe.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rysujSwiat()
    {
        panel.removeAll();
        panel.setPreferredSize(new Dimension(SZEROKOSC_OKNA, DLUGOSC_OKNA));
        panel.setLayout(new BorderLayout());

        wyswietlNaglowek();

        rysujPlansze();
        wyswietlKomunikaty();
        wyswietlPrzyciskiAkcji();

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();

        for (int i = 0; i < swiat.getSzerokoscPlanszy(); i++)
        {
            for (int j = 0; j < swiat.getDlugoscPlanszy(); j++)
            {
                Organizm org = swiat.getOrganizmNaPlanszy(new Point(j, i));

                if (org != null)
                    przyciskiPlanszy[i][j].setIcon(dopasujRozmiar(org.rysowanie(), przyciskiPlanszy[i][j].getWidth(), przyciskiPlanszy[i][j].getHeight()));
                else
                    przyciskiPlanszy[i][j].addActionListener(sluchacz);

                przyciskiPlanszy[i][j].setFocusable(false);
                przyciskiPlanszy[i][j].putClientProperty("x", j);
                przyciskiPlanszy[i][j].putClientProperty("y", i);
            }
        }
    }

    private void wyswietlNaglowek()
    {
        JPanel naglowek = new JPanel();
        naglowek.setPreferredSize(new Dimension(SZEROKOSC_OKNA,74));
        naglowek.setBackground(Color.darkGray);
        naglowek.add(new JLabel("<html><h1><font color='white'>Symulator wirtualnego świata</font></h1></html>"));
        panel.add(naglowek, BorderLayout.PAGE_START);
    }

    private void rysujPlansze()
    {
        JPanel planszaPanel = new JPanel();
        planszaPanel.setLayout(new GridLayout(swiat.getSzerokoscPlanszy(), swiat.getDlugoscPlanszy()));

        przyciskiPlanszy = new JButton [swiat.getSzerokoscPlanszy()][swiat.getDlugoscPlanszy()];
        for (int i = 0; i < swiat.getSzerokoscPlanszy(); i++)
        {
            for (int j = 0; j < swiat.getDlugoscPlanszy(); j++)
            {
                przyciskiPlanszy[i][j] = new JButton();
                planszaPanel.add(przyciskiPlanszy[i][j]);
            }
        }

        panel.add(planszaPanel, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(450,500));
    }

    private void wyswietlKomunikaty()
    {
        JPanel komentarzePanel = new JPanel();
        komentarzePanel.setPreferredSize(new Dimension(350,400));
        komentarzePanel.setBorder(new EmptyBorder(0, 10, 0, 0));

        komentarzePanel.setBackground(Color.darkGray);
        komentarzePanel.setLayout(new BorderLayout());

        JTextPane poleTekstowe = new JTextPane();
        poleTekstowe.setEditable(false);
        poleTekstowe.setFocusable(false);
        poleTekstowe.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        poleTekstowe.setBackground(Color.lightGray);

        StyledDocument dokument = poleTekstowe.getStyledDocument();

        String info = "Tura: " + nrTury + "\n\n";
        dodajWyroznionaLinie(poleTekstowe, info);

        dodajKomunikatyOSterowaniu(dokument);

        for (String komunikat : swiat.getKomunikaty())
        {
            try {
                dokument.insertString(dokument.getLength(), komunikat + "\n\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        swiat.wyczyscKomunikaty();

        komentarzePanel.add(poleTekstowe, BorderLayout.CENTER);

        poleTekstowe.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPanel = new JScrollPane(poleTekstowe);
        komentarzePanel.add(scrollPanel);
        SwingUtilities.invokeLater(() -> scrollPanel.getVerticalScrollBar().setValue(0));

        panel.add(komentarzePanel, BorderLayout.LINE_END);
    }

    private void dodajKomunikatyOSterowaniu(StyledDocument dokument)
    {
        Czlowiek czlowiek = swiat.getCzlowiek();
        String komunikat = null;
        Style styl = dokument.addStyle("italic", null);
        StyleConstants.setItalic(styl, true);
        StyleConstants.setForeground(styl, Color.blue);

        if (czlowiek != null)
        {
            if (czlowiek.czyAktywnaTarczaAlzura())
            {
                czlowiek.setCzasTrwaniaUmiejetnosciSpecjalnej(czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() + 1);

                if (czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() == 5)
                {
                    czlowiek.dezaktywujTarczeAlzura();
                    komunikat = "Specjalna umiejetnosc czlowieka - Tarcza Alzura - zostala dezaktywowana.\n\n";
                }
                else
                {
                    komunikat = "Specjalna umiejetnosc czlowieka - Tarcza Alzura - jest aktywna. Pozostaly czas: " + (5 - czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej()) + " tur(y).\n\n";

                }
            }
            else
            {
                if (czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() > 0)
                    czlowiek.setCzasTrwaniaUmiejetnosciSpecjalnej(czlowiek.getCzasTrwaniaUmiejetnosciSpecjalnej() - 1);
            }
        }
        else
        {
            komunikat = "Czlowiek nie zyje. Wcisnij spacje, aby przejsc do nastepnej tury...\n\n";
            StyleConstants.setForeground(styl, Color.red);
        }


        if (komunikat != null)
        {
            try {
                dokument.insertString(dokument.getLength(), komunikat, styl);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void dodajWyroznionaLinie(JTextPane poleTekstowe, String tekst)
    {
        StyledDocument dokument = poleTekstowe.getStyledDocument();
        Style s = dokument.addStyle("bold", null);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 18);

        try {
            dokument.insertString(dokument.getLength(), tekst, s);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void wyswietlPrzyciskiAkcji()
    {
        JPanel przyciskiPanel = new JPanel();
        przyciskiPanel.setLayout(new BoxLayout(przyciskiPanel, BoxLayout.X_AXIS));
        przyciskiPanel.setPreferredSize(new Dimension(SZEROKOSC_OKNA,50));
        przyciskiPanel.setBackground(Color.darkGray);

        przyciskiPanel.add(Box.createHorizontalGlue());
        dodajPrzyciskAkcji(przyciskiPanel, "Przejdź do następnej tury");
        dodajPrzyciskAkcji(przyciskiPanel, "Zapisz grę");
        dodajPrzyciskAkcji(przyciskiPanel, "Pomoc");
        dodajPrzyciskAkcji(przyciskiPanel, "Wyjście");
        przyciskiPanel.add(Box.createHorizontalGlue());

        panel.add(przyciskiPanel, BorderLayout.PAGE_END);
    }

    private void dodajPrzyciskAkcji(JPanel przyciskiPanel, String nazwa)
    {
        JButton btn = new JButton(nazwa);
        btn.addActionListener(sluchacz);
        if (!nazwa.equals("Przejdź do następnej tury"))
            przyciskiPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        przyciskiPanel.add(btn);
    }

    private Icon dopasujRozmiar(ImageIcon ikona, int szerokosc, int dlugosc)
    {
        Image img = ikona.getImage();
        int wymiar = Math.min(szerokosc - 10, dlugosc - 10);
        Image imgPrzeskalowany = img.getScaledInstance(wymiar, wymiar,  Image.SCALE_SMOOTH);
        return new ImageIcon(imgPrzeskalowany);
    }
}
