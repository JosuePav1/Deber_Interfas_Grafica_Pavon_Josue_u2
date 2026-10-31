/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import modelo.ContactoTableModel;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class VentanaPrincipal extends JFrame {

    public JTextField txtNombre, txtTelefono, txtEmail, txtBuscar;
    public JComboBox<String> cbCategoria, cbFiltro;
    public JTable tablaContactos;
    public JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnExportar;
    public JProgressBar barraProgreso;
    public JTabbedPane tabbedPane;
    public JTextArea areaEstadisticas;
    public JPopupMenu menuContextual; 
    public JMenuItem itemEliminar, itemEditar;
    
    private JLabel lblNombre, lblTelefono, lblEmail, lblCategoria, lblBuscar, lblFiltrar;

    private final ContactoTableModel tableModel = new ContactoTableModel();
    private ResourceBundle bundle;

    public VentanaPrincipal(Locale locale) {
        bundle = ResourceBundle.getBundle("vista.Messages", locale);
        
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        
        // --- Pestaña 1: Contactos ---
        JPanel panelContactos = new JPanel(new BorderLayout(10, 10));
        panelContactos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Contacto"));
        
        lblNombre = new JLabel(); panelFormulario.add(lblNombre);
        txtNombre = new JTextField(); panelFormulario.add(txtNombre);
        lblTelefono = new JLabel(); panelFormulario.add(lblTelefono);
        txtTelefono = new JTextField(); panelFormulario.add(txtTelefono);
        lblEmail = new JLabel(); panelFormulario.add(lblEmail);
        txtEmail = new JTextField(); panelFormulario.add(txtEmail);
        lblCategoria = new JLabel(); panelFormulario.add(lblCategoria);
        cbCategoria = new JComboBox<>(new String[]{"Familia", "Trabajo", "Amigos", "Otros"});
        panelFormulario.add(cbCategoria);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton();
        btnEditar = new JButton();
        btnEliminar = new JButton();
        btnLimpiar = new JButton();
        btnExportar = new JButton();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnExportar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        panelContactos.add(panelNorte, BorderLayout.NORTH);

        tablaContactos = new JTable(tableModel);
        tablaContactos.setRowSorter(new TableRowSorter<>(tableModel)); 
        JScrollPane scrollTabla = new JScrollPane(tablaContactos);
        panelContactos.add(scrollTabla, BorderLayout.CENTER);

        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        panelContactos.add(barraProgreso, BorderLayout.SOUTH);

        // --- Pestaña 2: Estadísticas ---
        JPanel panelEstadisticas = new JPanel(new BorderLayout());
        areaEstadisticas = new JTextArea();
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panelEstadisticas.add(new JScrollPane(areaEstadisticas), BorderLayout.CENTER);

        tabbedPane.addTab("", panelContactos);
        tabbedPane.addTab("", panelEstadisticas);
        add(tabbedPane, BorderLayout.CENTER);

        // Menú Contextual
        menuContextual = new JPopupMenu();
        itemEditar = new JMenuItem();
        itemEliminar = new JMenuItem();
        menuContextual.add(itemEditar);
        menuContextual.add(itemEliminar);

        // Barra de búsqueda y filtro
        JPanel panelSur = new JPanel(new FlowLayout());
        lblBuscar = new JLabel(); panelSur.add(lblBuscar);
        txtBuscar = new JTextField(15); panelSur.add(txtBuscar);
        lblFiltrar = new JLabel(); panelSur.add(lblFiltrar);
        cbFiltro = new JComboBox<>(new String[]{"Todas", "Familia", "Trabajo", "Amigos", "Otros"});
        panelSur.add(cbFiltro);
        add(panelSur, BorderLayout.SOUTH);

        crearMenuIdiomas();
        aplicarIdioma();
        aplicarEstilos();
    }

    private void crearMenuIdiomas() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuIdioma = new JMenu("Idioma / Language");
        JMenuItem itemEs = new JMenuItem("Español (ES)");
        JMenuItem itemEn = new JMenuItem("English (EN)");
        JMenuItem itemJa = new JMenuItem("日本語 (JA)");

        itemEs.addActionListener(e -> reiniciarConIdioma(new Locale("es", "EC")));
        itemEn.addActionListener(e -> reiniciarConIdioma(new Locale("en", "US")));
        itemJa.addActionListener(e -> reiniciarConIdioma(new Locale("ja", "JP")));

        menuIdioma.add(itemEs);
        menuIdioma.add(itemEn);
        menuIdioma.add(itemJa);
        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);
    }

    private void reiniciarConIdioma(Locale locale) {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal nuevaVentana = new VentanaPrincipal(locale);
            new controlador.ContactoController(nuevaVentana, new modelo.ContactoDAO());
            nuevaVentana.setVisible(true);
        });
    }

    private void aplicarIdioma() {
        setTitle(bundle.getString("titulo"));
        
        tabbedPane.setTitleAt(0, bundle.getString("tab_contactos"));
        tabbedPane.setTitleAt(1, bundle.getString("tab_estadisticas"));

        lblNombre.setText(bundle.getString("lbl_nombre"));
        lblTelefono.setText(bundle.getString("lbl_telefono"));
        lblEmail.setText(bundle.getString("lbl_email"));
        lblCategoria.setText(bundle.getString("lbl_categoria"));
        lblBuscar.setText(bundle.getString("lbl_buscar"));
        lblFiltrar.setText(bundle.getString("lbl_filtrar"));

        btnAgregar.setText(bundle.getString("btn_agregar"));
        btnEditar.setText(bundle.getString("btn_editar"));
        btnEliminar.setText(bundle.getString("btn_eliminar"));
        btnLimpiar.setText(bundle.getString("btn_limpiar"));
        btnExportar.setText(bundle.getString("btn_exportar"));

        itemEditar.setText(bundle.getString("menu_editar"));
        itemEliminar.setText(bundle.getString("menu_eliminar"));
    }

    private void aplicarEstilos() {
        Color colorFondo = new Color(225, 240, 255); // Celeste suave
        Font fuentePrincipal = new Font("Dialog", Font.PLAIN, 14);
        Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);

        this.getContentPane().setBackground(colorFondo);
        
        // Pintar paneles internos de celeste
        for (Component c : this.getContentPane().getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(colorFondo);
                for (Component subC : ((JPanel) c).getComponents()) {
                    if (subC instanceof JPanel) {
                        subC.setBackground(colorFondo);
                    }
                }
            }
        }

        JButton[] botones = {btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnExportar};
        for (JButton btn : botones) {
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(false); 
            
            btn.setBackground(Color.WHITE); // Botones blancos
            btn.setForeground(Color.BLACK); // Texto negro
            btn.setFont(fuenteTitulo);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btn.setIconTextGap(8);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        txtNombre.setFont(fuentePrincipal);
        txtTelefono.setFont(fuentePrincipal);
        txtEmail.setFont(fuentePrincipal);
        txtBuscar.setFont(fuentePrincipal);
        tablaContactos.setFont(fuentePrincipal);
        tablaContactos.getTableHeader().setFont(fuenteTitulo);
        areaEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 14));

        btnAgregar.setIcon(UIManager.getIcon("FileChooser.newFolderIcon")); 
        btnEditar.setIcon(UIManager.getIcon("FileChooser.detailsViewIcon"));
        btnEliminar.setIcon(UIManager.getIcon("FileChooser.deleteIcon")); 
        btnExportar.setIcon(UIManager.getIcon("FileChooser.saveIcon"));
        btnLimpiar.setIcon(UIManager.getIcon("FileChooser.cancelIcon"));
    }

    public ContactoTableModel getTableModel() { return tableModel; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getCategoria() { return cbCategoria.getSelectedItem().toString(); }
    public String getTextoBuscar() { return txtBuscar.getText().trim(); }
    public String getFiltro() { return cbFiltro.getSelectedItem().toString(); }

    public void setCampos(String n, String t, String e, String c) {
        txtNombre.setText(n);
        txtTelefono.setText(t);
        txtEmail.setText(e);
        cbCategoria.setSelectedItem(c);
    }

    public void limpiarFormulario() {
        txtNombre.setText(""); txtTelefono.setText(""); txtEmail.setText("");
        cbCategoria.setSelectedIndex(0);
        tablaContactos.clearSelection();
    }

    public int getFilaSeleccionada() { return tablaContactos.getSelectedRow(); }
    
    public void mostrarError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    
    public boolean confirmar(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    
    public String getTxtCargando() { return bundle.getString("txt_cargando"); }
    public String getTxtCompletado() { return bundle.getString("txt_completado"); }
}