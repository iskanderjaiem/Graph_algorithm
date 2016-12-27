
import com.djikistra.Edge;
import com.djikistra.Vertex;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;


/*
 * Restauration depuis un fichier
 */
public class CanvasJpanel extends JPanel implements MouseListener,
        MouseMotionListener {

    static final int CREATION_SOMMET = 1;
    static final int ETIQUETAGE_SOMMET = 2;
    static final int DEBUT_DEPLACEMENT_SOMMET = 3;
    static final int SUITE_DEPLACEMENT_SOMMET = 4;
    static final int DEBUT_CREATION_ARETE = 5;
    static final int SUITE_CREATION_ARETE = 6;

    static final int TOUT_PRES = 32;

    public static List<Vertex> vertexList ;
    JLabel barreEtat;
    int etat;
    Sommet sommetSelectionne;
    PrincipaleJFrame frame;
    String mapUrl = "img/mapTunisia.png";
    String iconUrl = "img/house.png";
    CanvasJpanel(PrincipaleJFrame frame) {
       /* JFrame cadre = new JFrame("Step 10");
        cadre.setBounds(100, 50, 600, 500);
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
*/
        this.frame = frame;
        this.setBackground(Color.WHITE);
        vertexList = new ArrayList<Vertex>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        barreEtat = new JLabel("Choisir une action");
       
/*
        cadre.add(this, BorderLayout.CENTER);
        cadre.add(barreEtat, BorderLayout.SOUTH);
        cadre.setJMenuBar(creerBarreMenus());
        cadre.setVisible(true);*/
    }

     public List<Vertex> getVertexList(){
        return vertexList;
    }
    public JLabel getBarreEtat(){
        return barreEtat;
    }
    
    public void setMapUrl(String str){
        this.mapUrl = str;
    }
    public void setEtat(int etat){
        this.etat = etat;
    }
    public JMenuBar creerBarreMenus() {
        JMenuBar barre = new JMenuBar();
        JMenu menu = new JMenu("Fichier");
        barre.add(menu);

        JMenuItem item = new JMenuItem("Ouvrir...");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                restauration();
            }
        });
        menu.add(item);

        item = new JMenuItem("Enregistrer...");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                enregistrement();
            }
        });
        menu.add(item);

        menu.addSeparator();
        item = new JMenuItem("Quitter");
        menu.add(item);

        menu = new JMenu("Actions");
        barre.add(menu);

        item = new JMenuItem("Creer sommet");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                etat = CREATION_SOMMET;
                rafraichirBarreEtat();
            }
        });
        menu.add(item);

        item = new JMenuItem("Etiqueter sommet");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                etat = ETIQUETAGE_SOMMET;
                rafraichirBarreEtat();
            }
        });
        menu.add(item);

        item = new JMenuItem("Déplacer sommet");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                etat = DEBUT_DEPLACEMENT_SOMMET;
                rafraichirBarreEtat();
            }
        });
        menu.add(item);

         menu = new JMenu("Mes emplacements");
        barre.add(menu);

        item = new JMenuItem("Tunisie");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mapUrl = "img/mapTunisia.png";
            }
        });
        menu.add(item);

        item = new JMenuItem("Tunis");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mapUrl = "img/mapTunis.png";
            }
        });
        menu.add(item);

        item = new JMenuItem("Exemple");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mapUrl = "img/mapExemple.png";
            }
        });
        menu.add(item);
        
         menu = new JMenu("Batiments");
        barre.add(menu);

        item = new JMenuItem("Maison");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                iconUrl = "img/house.png";
            }
        });
        menu.add(item);
        
        item = new JMenuItem("La poste");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                iconUrl = "img/laposte.png";
            }
        });
        menu.add(item);
        
        
        revalidate();
        repaint();
        return barre;
    }

    void rafraichirBarreEtat() {
        switch (etat) {
            case CREATION_SOMMET:
                barreEtat.setText("Creation sommet : cliquer pour designer l'endroit pour le poser");
                break;
            case ETIQUETAGE_SOMMET:
                barreEtat
                        .setText("Nommage du sommet : désigner le sommet à nommer");
                break;
            case DEBUT_DEPLACEMENT_SOMMET:
                barreEtat
                        .setText("Deplacement sommet : traîner le sommet à déplacer");
                break;
            case SUITE_DEPLACEMENT_SOMMET:
                barreEtat
                        .setText("Deplacement sommet : lâcher le sommet à la place voulue");
                break;
            case DEBUT_CREATION_ARETE:
                barreEtat.setText("Création de la liaison : désigner le premier sommet");
                break;
            case SUITE_CREATION_ARETE:
                barreEtat.setText("Création de la liaison : désigner le second sommet");
                break;
        }
    }

    Sommet sommetVoisin(int x, int y) {
        Iterator<Sommet> iter = Sommet.iterator();
        while (iter.hasNext()) {
            Sommet s = iter.next();
            if (Math.abs(x - s.getX()) + Math.abs(y - s.getY()) < TOUT_PRES) {
                return s;
            }
        }
        return null;
    }

    public void mousePressed(MouseEvent evt) {
        Sommet sommet = null;

        switch (etat) {
            case CREATION_SOMMET:
                Sommet s = new Sommet(evt.getX(), evt.getY());
                vertexList.add(new Vertex(s.getEtiquette()));
                repaint();
                return;
            case ETIQUETAGE_SOMMET:
                sommet = sommetVoisin(evt.getX(), evt.getY());
                if (sommet == null) {
                    return;
                }
                String texte = JOptionPane.showInputDialog(this, "Nouveau nom:",
                        "Définition d'une étiquette", JOptionPane.QUESTION_MESSAGE);
                String ancienNomDuSommet = sommet.getEtiquette();
                if (texte != null) {
                    sommet.setEtiquette(texte);
                }
                
                //rename in vertexList
                for (int i = 0; i<vertexList.size(); i++){
                    if (vertexList.get(i).getName().equals(ancienNomDuSommet)){
                        vertexList.get(i).setName(texte);
                    }
                }
                
                repaint();
                return;
            case DEBUT_DEPLACEMENT_SOMMET:
                sommetSelectionne = sommetVoisin(evt.getX(), evt.getY());
                if (sommetSelectionne == null) {
                    return;
                }
                etat = SUITE_DEPLACEMENT_SOMMET;
                rafraichirBarreEtat();
                return;
            case DEBUT_CREATION_ARETE:
                sommetSelectionne = sommetVoisin(evt.getX(), evt.getY());
                if (sommetSelectionne == null) {
                    return;
                }
                etat = SUITE_CREATION_ARETE;
                rafraichirBarreEtat();
                return;
            case SUITE_CREATION_ARETE:
                //
                //
                sommet = sommetVoisin(evt.getX(), evt.getY());
                if (sommet == null || sommet.equals(sommetSelectionne)) {
                    return;
                }
                
                int distance = Integer.parseInt( JOptionPane.showInputDialog(this,"La distance entre "+ sommetSelectionne.getEtiquette()+" et "+sommet.getEtiquette()+ " est : ",
                        "Distance en km", JOptionPane.QUESTION_MESSAGE));
                
                new Arete(sommetSelectionne, sommet);
                //ajouter la distance dans le chemin entre le depart et l'arrivée
                   vertexList.get(Vertex.getIndexFromVertexList(vertexList,sommetSelectionne.getEtiquette())).addAdjancie(new Edge(vertexList.get(Vertex.getIndexFromVertexList(vertexList,sommet.getEtiquette())), distance));
                 
                //ajouter la distance dans le chemin entre l'arrivée et le départ
                 vertexList.get(Vertex.getIndexFromVertexList(vertexList,sommet.getEtiquette())).addAdjancie(new Edge(vertexList.get(Vertex.getIndexFromVertexList(vertexList,sommetSelectionne.getEtiquette())), distance));
                 frame.setTextInfo("********************************************* "
                + Vertex.showEdgesOfVertexList(CanvasJpanel.vertexList));
                repaint();
                etat = DEBUT_CREATION_ARETE;
                rafraichirBarreEtat();
                return;
        }
    }

    public void mouseReleased(MouseEvent evt) {
        if (etat == SUITE_DEPLACEMENT_SOMMET) {
            etat = DEBUT_DEPLACEMENT_SOMMET;
        }
        rafraichirBarreEtat();
    }

    public void mouseDragged(MouseEvent evt) {
        if (etat == SUITE_DEPLACEMENT_SOMMET) {
            sommetSelectionne.setPosition(evt.getX(), evt.getY());
            repaint();
        }
    }

    public void mouseMoved(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        //BG
         BufferedImage imagebg = null;
            try {
                imagebg = ImageIO.read(this.getClass().getResource(mapUrl));
            } catch (IOException ex) {
                System.out.println(ex);
            }
            
            g.drawImage(imagebg, 0, 0, this);
        //ENDBG
        
        Color cp = g.getColor();

        g.setColor(Color.black);
        Iterator<Arete> iterA = Arete.iterator();
        while (iterA.hasNext()) {
            Arete a = iterA.next();
            g.drawLine(a.getOrig().getX(), a.getOrig().getY(), a.getExtr()
                    .getX(), a.getExtr().getY());
        }

        Iterator<Sommet> iterS = Sommet.iterator();
        while (iterS.hasNext()) {
            Sommet s = iterS.next();
            BufferedImage imageb = null;
            try {
                imageb = ImageIO.read(this.getClass().getResource(iconUrl));
            } catch (IOException ex) {
                System.out.println(ex);
            }
            
            g.drawImage(imageb, s.getX() - 32, s.getY() - 32, this);
           // g.setColor(Color.red);
           // g.fillOval(s.getX() - 5, s.getY() - 5, 10, 10);
            if (s.getEtiquette() != null) {
                g.setColor(Color.black);
                g.drawString(s.getEtiquette(), s.getX()-10 , s.getY() - 35);
            }
        }
        g.setColor(cp);
    }

    void enregistrement() {
        JFileChooser dial = new JFileChooser();
        int result = dial.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            PrintStream sortie = new PrintStream(dial.getSelectedFile());

            sortie.println(Sommet.nombreSommets());
            Iterator<Sommet> iterS = Sommet.iterator();
            while (iterS.hasNext()) {
                sortie.println(iterS.next());
            }

            sortie.println(Arete.nombreAretes());
            Iterator<Arete> iterA = Arete.iterator();
            while (iterA.hasNext()) {
                sortie.println(iterA.next());
            }
            sortie.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    StreamTokenizer analyseurLexical;

    String prochainMot() throws IOException {
        int typeUnite = analyseurLexical.nextToken();
        if (typeUnite != StreamTokenizer.TT_WORD && typeUnite != '"') {
            throw new IOException("Erreur de syntaxe");
        }
        return analyseurLexical.sval;
    }

    int prochainEntier() throws IOException {
        int typeUnite = analyseurLexical.nextToken();
        if (typeUnite != StreamTokenizer.TT_NUMBER) {
            throw new IOException("Erreur de syntaxe");
        }
        return (int) analyseurLexical.nval;
    }

    void restauration() {
        JFileChooser dial = new JFileChooser();
        int result = dial.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            analyseurLexical = new StreamTokenizer(new FileReader(dial
                    .getSelectedFile()));
            int nbr = prochainEntier();
            for (int i = 0; i < nbr; i++) {
                String etik = prochainMot();
                int x = prochainEntier();
                int y = prochainEntier();
                new Sommet(etik, x, y);
            }

            nbr = prochainEntier();
            for (int i = 0; i < nbr; i++) {
                String etik1 = prochainMot();
                String etik2 = prochainMot();
                Sommet s1 = Sommet.trouverSommet(etik1);
                if (s1 == null) {
                    throw new IOException(etik1 + " sommet inexistant");
                }
                Sommet s2 = Sommet.trouverSommet(etik2);
                if (s2 == null) {
                    throw new IOException(etik2 + " sommet inexistant");
                }
                new Arete(s1, s2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        repaint();
    }

    /*public static void main(String[] args) {
        
        PrincipaleJFrame.setTheme();
        new CanvasJpanel();
    }*/
    
    
    


}
