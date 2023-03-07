package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Taulukko {

	private Connection conn;
	private JTable table;

	public Taulukko() {
	    super();
	}

	// Yhdistä tietokantaan
	private void yhdistaTietokantaan() throws SQLException {
	    String url = "jdbc:mariadb://localhost:3306/tulokset";
	    String kayttaja = "olso";
	    String salasana = "olso";
	    conn = DriverManager.getConnection(url, kayttaja, salasana);
	}

	// Luo taulukko
	private JTable luoTaulukko() throws SQLException {
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT * FROM simulaatiotulokset ORDER BY id DESC");

	    table = new JTable(luoTaulukkoModeli(rs)) {
	        @Override
	        public boolean getScrollableTracksViewportWidth() {
	            return getPreferredSize().width < getParent().getWidth();
	        }
	    };
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.getColumnModel().getColumn(0).setPreferredWidth(40);
	    table.getColumnModel().getColumn(1).setPreferredWidth(70);
	    table.getColumnModel().getColumn(2).setPreferredWidth(80);
	    for (int i = 3; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setPreferredWidth(120);
	    }

	    table.setPreferredScrollableViewportSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
	    return table;
	}

	// Luo taulukon mallin ResultSet-oliosta
	private static DefaultTableModel luoTaulukkoModeli(ResultSet rs) throws SQLException {
	    java.sql.ResultSetMetaData metaData = rs.getMetaData();

	    // Sarakkeiden nimet
	    String[] sarakkeidenNimet = new String[metaData.getColumnCount()];
	    for (int i = 0; i < metaData.getColumnCount(); i++) {
	        sarakkeidenNimet[i] = metaData.getColumnName(i + 1);
	    }

	    // Taulukon tiedot
	    DefaultTableModel taulukkoModeli = new DefaultTableModel(sarakkeidenNimet, 0);
	    while (rs.next()) {
	        Object[] rivi = new Object[metaData.getColumnCount()];
	        for (int i = 0; i < metaData.getColumnCount(); i++) {
	            rivi[i] = rs.getObject(i + 1);
	        }
	        taulukkoModeli.addRow(rivi);
	    }

	    return taulukkoModeli;
	}

	// Hae taulukko
	private JTable getTaulukko() throws SQLException {
	    yhdistaTietokantaan();
	    return luoTaulukko();
	}

	// Näytä taulukko
	public void naytaTaulukko() throws SQLException {
	    JTable taulukko = getTaulukko();
	    JScrollPane scrollPane = new JScrollPane(taulukko, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setPreferredSize(new Dimension(800, 600));
	    JFrame frame = new JFrame("Taulukko");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout());
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.pack();
	    frame.setVisible(true);
	}
}