/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
import controlador.ContactoController;
import modelo.ContactoDAO;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal vista = new VentanaPrincipal(new Locale("es", "EC"));
            ContactoDAO dao = new ContactoDAO();
            new ContactoController(vista, dao);
            vista.setVisible(true);
        });
    }
}