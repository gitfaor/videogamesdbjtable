package videogamesdbtable;

import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fabio Origoni
 * Description: a simple project based on a Video games DB in SQL lite
 * that allows you to make CRUD operations.
 * 
 */
public class VideoGamesDBTableForm extends javax.swing.JFrame {

    /**
     * Connection var
     */
    private Connection conn;

    private static final int NAME_TABLE_COLUMN = 0;
    private static final int YEAR_TABLE_COLUMN = 1;
    private static final int MANUFACTURER_TABLE_COLUMN = 2;
    private static final int CATEGORY_TABLE_COLUMN = 3;
    private static final int PLATFORM_TABLE_COLUMN = 4;
    private static final int PRICE_TABLE_COLUMN = 5;

    /**
     * Creates new form VideoGamesDBTableForm
     */
    public VideoGamesDBTableForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        cmbxCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"", "FPS", "Adventure", "Arcade", "Sport"}));
        cmbxPlatform.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"", "PC", "Nintendo", "PS4", "XBOX"}));
        cmbxSearchFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"", "Name", "Year", "Manufacturer", "Category", "Platform", "Price"}));
        cmbxWhereCondition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"", "Contains", "Not Contains", "Begin with", "End with"}));
    }

    /**
     * Updates the value of a cell
     */
    private void cellValueChanged() {
        // Event listener for the change of selection of the JTable
        ListSelectionModel selectionModel = this.tVideoGamesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //The user selected a new item from the JTable
                if (e.getValueIsAdjusting()) {
                    return;
                }

                // Get the index of the selected row from the JTable (-1 if there is none)
                int selRow = tVideoGamesTable.getSelectedRow();
                if (selRow < 0) {
                    return;
                }

                // Get DefaultTableModel of the JTable.
                DefaultTableModel dtm = (DefaultTableModel) tVideoGamesTable.getModel();

                //Copy of the data from the active JTable selection
                IdNameItem nameItem = (IdNameItem) dtm.getValueAt(selRow,
                        VideoGamesDBTableForm.NAME_TABLE_COLUMN);

                tfName.setText(nameItem.getName());
                tfYear.setText((String) dtm.getValueAt(selRow, VideoGamesDBTableForm.YEAR_TABLE_COLUMN).toString());
                tfManufacturer.setText((String) dtm.getValueAt(selRow, VideoGamesDBTableForm.MANUFACTURER_TABLE_COLUMN));
                cmbxCategory.getModel().setSelectedItem(dtm.getValueAt(selRow, VideoGamesDBTableForm.CATEGORY_TABLE_COLUMN));
                cmbxPlatform.getModel().setSelectedItem(dtm.getValueAt(selRow, VideoGamesDBTableForm.PLATFORM_TABLE_COLUMN));
                tfPrice.setText((String) dtm.getValueAt(selRow, VideoGamesDBTableForm.PRICE_TABLE_COLUMN));
            }
        });
    }
    

    /**
     * Opens a connection with the database. If not existing, it generates one.
     * Creates the video game table.
     *
     */
    private void createOrOpenDB() {

        if (tgbConnection.isSelected()) {
            try {
                Class.forName("org.sqlite.JDBC");

                this.conn = DriverManager.getConnection("jdbc:sqlite:Videogames.db");

                try ( Statement stmt = this.conn.createStatement()) {
                    String sql = "CREATE TABLE IF NOT EXISTS videogame ("
                            + " id INTEGER NOT NULL,"
                            + " name TEXT NOT NULL,"
                            + " year INTEGER NOT NULL, "
                            + " manufacturer TEXT NOT NULL, "
                            + " category TEXT NOT NULL,"
                            + " platform TEXT NOT NULL,"
                            + " price TEXT NOT NULL,"
                            + " PRIMARY KEY (id))";
                    stmt.executeUpdate(sql);
                }
                cellValueChanged();

                lConnection.setText("(Connected)");
                tgbConnection.setText("Disconnect");

            } catch (ClassNotFoundException | SQLException ex) {
                String err = ex.getMessage().trim();
                if (ex instanceof ClassNotFoundException) {
                    err = "Class '" + err + "' not found.";
                }

                JOptionPane.showMessageDialog(null,
                        err + "\n\nTerminating the application!",
                        "Videogames DB",
                        JOptionPane.ERROR_MESSAGE);

                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        } else {
            try {
                this.conn.close();
                lConnection.setText("(Disconnected)");
                tgbConnection.setText("Connect");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Videogames DB",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Populates the content of the JTable
     */
    private void refreshTableContent() {

        DefaultTableModel dtm = (DefaultTableModel) this.tVideoGamesTable.getModel();
        dtm.setRowCount(0);

        try ( Statement stmt = this.conn.createStatement()) {

            String sql = "SELECT * FROM videogame ORDER BY name";
            try ( ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {

                    Object[] rowData = {
                        new IdNameItem(rs.getInt("id"), rs.getString("name")),
                        rs.getInt("year"),
                        rs.getString("manufacturer"),
                        rs.getString("category"),
                        rs.getString("platform"),
                        rs.getString("price"),};

                    dtm.addRow(rowData);
                }
            }

            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Videogames DB",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pMainContainer = new javax.swing.JPanel();
        pTableContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tVideoGamesTable = new javax.swing.JTable();
        pInputData = new javax.swing.JPanel();
        bInsert = new javax.swing.JButton();
        bUpdate = new javax.swing.JButton();
        bDelete = new javax.swing.JButton();
        lName = new javax.swing.JLabel();
        lCategory = new javax.swing.JLabel();
        lYear = new javax.swing.JLabel();
        lPlatform = new javax.swing.JLabel();
        lPrice = new javax.swing.JLabel();
        lManufacturer = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        tfYear = new javax.swing.JTextField();
        tfManufacturer = new javax.swing.JTextField();
        tfPrice = new javax.swing.JTextField();
        cmbxCategory = new javax.swing.JComboBox<>();
        cmbxPlatform = new javax.swing.JComboBox<>();
        pFilters = new javax.swing.JPanel();
        cmbxSearchFilter = new javax.swing.JComboBox<>();
        bSearch = new javax.swing.JButton();
        lSearchIn = new javax.swing.JLabel();
        lWhereCondition = new javax.swing.JLabel();
        cmbxWhereCondition = new javax.swing.JComboBox<>();
        tfSearch = new javax.swing.JTextField();
        tgbConnection = new javax.swing.JToggleButton();
        lConnection = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tVideoGamesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Year", "Manufacturer", "Category", "Platform", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tVideoGamesTable);

        javax.swing.GroupLayout pTableContainerLayout = new javax.swing.GroupLayout(pTableContainer);
        pTableContainer.setLayout(pTableContainerLayout);
        pTableContainerLayout.setHorizontalGroup(
            pTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTableContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pTableContainerLayout.setVerticalGroup(
            pTableContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTableContainerLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pInputData.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Data"));

        bInsert.setText("Insert");
        bInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInsertActionPerformed(evt);
            }
        });

        bUpdate.setText("Update");
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        bDelete.setText("Delete");
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });

        lName.setText("Name");

        lCategory.setText("Category");

        lYear.setText("Year");

        lPlatform.setText("Platform");

        lPrice.setText("Price");

        lManufacturer.setText("Manufacturer");

        cmbxCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FPS", "Adventure", "Arcade", "Sport" }));

        cmbxPlatform.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nintendo", "PS4", "XBOX" }));

        javax.swing.GroupLayout pInputDataLayout = new javax.swing.GroupLayout(pInputData);
        pInputData.setLayout(pInputDataLayout);
        pInputDataLayout.setHorizontalGroup(
            pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInputDataLayout.createSequentialGroup()
                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pInputDataLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pInputDataLayout.createSequentialGroup()
                                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lYear, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lName, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfYear)
                                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pInputDataLayout.createSequentialGroup()
                                .addComponent(lManufacturer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfManufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pInputDataLayout.createSequentialGroup()
                                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lPlatform)
                                    .addComponent(lCategory))
                                .addGap(18, 18, 18)
                                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbxPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pInputDataLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(bInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInputDataLayout.createSequentialGroup()
                                .addComponent(bUpdate)
                                .addGap(172, 172, 172))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInputDataLayout.createSequentialGroup()
                                .addComponent(bDelete)
                                .addGap(31, 31, 31)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pInputDataLayout.setVerticalGroup(
            pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInputDataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lPrice)
                        .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInputDataLayout.createSequentialGroup()
                        .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lName)
                            .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lCategory)
                            .addComponent(cmbxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lYear)
                            .addComponent(tfYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lPlatform)
                            .addComponent(cmbxPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfManufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lManufacturer))))
                .addGap(18, 18, 18)
                .addGroup(pInputDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bUpdate)
                    .addComponent(bDelete)
                    .addComponent(bInsert))
                .addGap(146, 146, 146))
        );

        pFilters.setBorder(javax.swing.BorderFactory.createTitledBorder("Filters"));

        cmbxSearchFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Year", "Manufacturer", "Category", "Platform", "Price" }));

        bSearch.setText("Search");
        bSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSearchActionPerformed(evt);
            }
        });

        lSearchIn.setText("Search In");

        lWhereCondition.setText("WHERE condition");

        cmbxWhereCondition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contains", "Not Contains", "Begin with", "End with" }));

        javax.swing.GroupLayout pFiltersLayout = new javax.swing.GroupLayout(pFilters);
        pFilters.setLayout(pFiltersLayout);
        pFiltersLayout.setHorizontalGroup(
            pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pFiltersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tfSearch)
                    .addGroup(pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bSearch)
                        .addGroup(pFiltersLayout.createSequentialGroup()
                            .addGroup(pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lWhereCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lSearchIn, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbxWhereCondition, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbxSearchFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        pFiltersLayout.setVerticalGroup(
            pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pFiltersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbxSearchFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lSearchIn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lWhereCondition)
                    .addComponent(cmbxWhereCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tgbConnection.setText("Connect");
        tgbConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbConnectionActionPerformed(evt);
            }
        });

        lConnection.setText("(Disconnected)");

        javax.swing.GroupLayout pMainContainerLayout = new javax.swing.GroupLayout(pMainContainer);
        pMainContainer.setLayout(pMainContainerLayout);
        pMainContainerLayout.setHorizontalGroup(
            pMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pTableContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pMainContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pMainContainerLayout.createSequentialGroup()
                        .addComponent(pInputData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pMainContainerLayout.createSequentialGroup()
                        .addComponent(tgbConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lConnection)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pMainContainerLayout.setVerticalGroup(
            pMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMainContainerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgbConnection)
                    .addComponent(lConnection))
                .addGap(31, 31, 31)
                .addComponent(pTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pMainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pInputData, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pInputData.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tgbConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbConnectionActionPerformed
        this.createOrOpenDB();
        this.refreshTableContent();
    }//GEN-LAST:event_tgbConnectionActionPerformed

    /**
     * Inserts a new record based on comboboxes and textboxes
     *
     * @param evt
     */
    private void bInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInsertActionPerformed

        String sql = "INSERT INTO videogame (name, year, manufacturer, category, platform, price)"
                + " VALUES(?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            Object nonCastedPlatform = cmbxPlatform.getSelectedItem();
            String platform = nonCastedPlatform.toString();

            Object nonCastedCategory = cmbxCategory.getSelectedItem();
            String category = nonCastedCategory.toString();

            pstmt.setString(1, this.tfName.getText());
            pstmt.setInt(2, Integer.parseInt(this.tfYear.getText()));
            pstmt.setString(3, this.tfManufacturer.getText());
            pstmt.setString(4, category);
            pstmt.setString(5, platform);
            pstmt.setString(6, this.tfPrice.getText());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Videogames DB",
                    JOptionPane.ERROR_MESSAGE);
        }

        this.refreshTableContent();
    }//GEN-LAST:event_bInsertActionPerformed

    /**
     * Updates the corresponding record to the selected JTable row
     *
     * @param evt
     */
    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed

        int selRow = this.tVideoGamesTable.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) this.tVideoGamesTable.getModel();
        IdNameItem idNItem = (IdNameItem) dtm.getValueAt(selRow,
                VideoGamesDBTableForm.NAME_TABLE_COLUMN);

        String sql = "UPDATE videogame SET name = ?, year = ?,"
                + " manufacturer  = ?, category = ?, platform = ?, price = ?"
                + " WHERE id = ?";
        try ( PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            Object nonCastedPlatform = cmbxPlatform.getSelectedItem();
            String platform = nonCastedPlatform.toString();

            Object nonCastedCategory = cmbxCategory.getSelectedItem();
            String category = nonCastedCategory.toString();

            pstmt.setString(1, this.tfName.getText());
            pstmt.setInt(2, Integer.parseInt(this.tfYear.getText()));
            pstmt.setString(3, this.tfManufacturer.getText());
            pstmt.setString(4, category);
            pstmt.setString(5, platform);
            pstmt.setString(6, this.tfPrice.getText());

            pstmt.setInt(7, idNItem.getId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Videogames DB",
                    JOptionPane.ERROR_MESSAGE);
        }

        this.refreshTableContent();
    }//GEN-LAST:event_bUpdateActionPerformed

    /**
     * Deletes the corresponding record to the selected JTable row
     *
     * @param evt
     */
    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteActionPerformed

        int selRow = this.tVideoGamesTable.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel dtm = (DefaultTableModel) this.tVideoGamesTable.getModel();
        IdNameItem idNItem = (IdNameItem) dtm.getValueAt(selRow,
                VideoGamesDBTableForm.NAME_TABLE_COLUMN);

        String sql = "DELETE FROM videogame WHERE id = ?";
        try ( PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            pstmt.setInt(1, idNItem.getId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Videogames DB",
                    JOptionPane.ERROR_MESSAGE);
        }

        this.refreshTableContent();
    }//GEN-LAST:event_bDeleteActionPerformed

    /**
     * Allows you to search a value by selecting the field and the WHERE condition
     * @param evt 
     */
    private void bSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSearchActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) this.tVideoGamesTable.getModel();
        dtm.setRowCount(0);

        String sql = "", field = "";

        if (this.tfSearch.getText().isBlank()) {
            sql = "SELECT * FROM videogame ORDER BY name";

        } else {

            switch (cmbxSearchFilter.getSelectedItem().toString()) {
                case "Name":
                    field = "Name";
                    break;
                case "Year":
                    field = "year";
                    break;
                case "Manufacturer":
                    field = "manufacturer";
                    break;
                case "Category":
                    field = "category";
                    break;
                case "Platform":
                    field = "platform";
                    break;
                case "Price":
                    field = "price";
                    break;
                default:
                    throw new AssertionError();
            }

            switch (cmbxWhereCondition.getSelectedItem().toString()) {
                case "Contains":
                    sql = "SELECT * FROM videogame WHERE " + field + " LIKE ? ORDER BY name";
                    break;
                case "Not Contains":
                    sql = "SELECT * FROM videogame WHERE " + field + " NOT LIKE ? ORDER BY name";
                    break;
                case "Begin with":
                    sql = "SELECT * FROM videogame WHERE " + field + " LIKE ? ORDER BY name";
                    break;
                case "End with":
                    sql = "SELECT * FROM videogame WHERE " + field + " LIKE ? ORDER BY name";
                    break;
                default:
                    throw new AssertionError();
            }

        }

        try ( PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            if (!this.tfSearch.getText().isBlank()) {
                String src = this.tfSearch.getText();

                switch (cmbxWhereCondition.getSelectedItem().toString()) {
                    case "Contains":
                        pstmt.setString(1, "%" + src + "%");
                        break;
                    case "Not Contains":
                        pstmt.setString(1, "%" + src + "%");
                        break;
                    case "Begin with":
                        pstmt.setString(1, src + "%");
                        break;
                    case "End with":
                        pstmt.setString(1, "%" + src);
                        break;
                    default:
                        throw new AssertionError();
                }
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Object[] rowData = {
                    new IdNameItem(rs.getInt("id"), rs.getString("name")),
                    rs.getInt("year"),
                    rs.getString("manufacturer"),
                    rs.getString("category"),
                    rs.getString("platform"),
                    rs.getString("price"),};

                dtm.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Videogame",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bSearchActionPerformed

    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Windows (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VideoGamesDBTableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoGamesDBTableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoGamesDBTableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoGamesDBTableForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VideoGamesDBTableForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDelete;
    private javax.swing.JButton bInsert;
    private javax.swing.JButton bSearch;
    private javax.swing.JButton bUpdate;
    private javax.swing.JComboBox<String> cmbxCategory;
    private javax.swing.JComboBox<String> cmbxPlatform;
    private javax.swing.JComboBox<String> cmbxSearchFilter;
    private javax.swing.JComboBox<String> cmbxWhereCondition;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lCategory;
    private javax.swing.JLabel lConnection;
    private javax.swing.JLabel lManufacturer;
    private javax.swing.JLabel lName;
    private javax.swing.JLabel lPlatform;
    private javax.swing.JLabel lPrice;
    private javax.swing.JLabel lSearchIn;
    private javax.swing.JLabel lWhereCondition;
    private javax.swing.JLabel lYear;
    private javax.swing.JPanel pFilters;
    private javax.swing.JPanel pInputData;
    private javax.swing.JPanel pMainContainer;
    private javax.swing.JPanel pTableContainer;
    private javax.swing.JTable tVideoGamesTable;
    private javax.swing.JTextField tfManufacturer;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPrice;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JTextField tfYear;
    private javax.swing.JToggleButton tgbConnection;
    // End of variables declaration//GEN-END:variables

}
