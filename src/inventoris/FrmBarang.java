/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoris;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Connect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.glass.events.KeyEvent;
import static inventoris.MenuUtama.dashboard;
import static inventoris.MenuUtama.divisi;
import static inventoris.MenuUtama.txtUserLogin;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;
import static koneksi.Connect.GetConnection;



/**
 *
 * @author PANASONIC
 */
public class FrmBarang extends javax.swing.JFrame {
    /**
     * Creates new form FrmBarang
     */
    private DefaultTableModel tabelDt;
    String sql=("");
    public FrmBarang() {        
        initComponents();
        
        //save data sortcut alt+S
        Action saveAction = new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
                return;
            }
        };
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        btnSave.setAction(saveAction);
        
        //tampil date time
        Tampil_Jam();
        Tampil_Tanggal();
        tgl_db();
        tgl_db.setVisible(false);
        
        //nonaktif button yang belum diperlukan
        btnUpdate.setEnabled(false);
        btnEditRows.setEnabled(false);
        btnDeleteSelected.setEnabled(false);
        btnSave.setEnabled(false);
        txtQty.setEnabled(false);
        btnCancel.setEnabled(false);
    }
    
    //tampil jam
    public void Tampil_Jam(){
        ActionListener taskPerformer = new ActionListener() {
 
        @Override
            public void actionPerformed(ActionEvent evt) {
            String nol_jam = "", nol_menit = "",nol_detik = "";
 
            java.util.Date dateTime = new java.util.Date();
            int nilai_jam = dateTime.getHours();
            int nilai_menit = dateTime.getMinutes();
            int nilai_detik = dateTime.getSeconds();
 
            if(nilai_jam <= 9) nol_jam= "0";
            if(nilai_menit <= 9) nol_menit= "0";
            if(nilai_detik <= 9) nol_detik= "0";
 
            String jam = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);
 
            jJam.setText(jam+":"+menit+":"+detik+"");
            }
        };
    new Timer(1000, taskPerformer).start();
    }   
 
//tampil tanggal
public void Tampil_Tanggal() {
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    jTanggal.setText(tanggal);
}

//tanggal untuk input ke database yyyy/mm/dd hh:mm:ss
public void tgl_db(){
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    tgl_db.setText(tanggal);
}

//function edit table
public void editTabel(){
    DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
            
            // deklarasikan i = index tabel
            try {
               btnUpdate.setEnabled(true);
               btnSave.setEnabled(false);
               
               int i = tabelData.getSelectedRow();
               //i = row yang dipilih
               
               //masukkan data dari table ke textfield
               txtCode.setText(dataModel.getValueAt(i, 0).toString());
               txtDescription.setText(dataModel.getValueAt(i, 1).toString());
               txtSize.setText(dataModel.getValueAt(i, 2).toString());
               txtArticle.setText(dataModel.getValueAt(i, 3).toString());
               txtMerk.setText(dataModel.getValueAt(i, 4).toString());
               txtSellprice.setText(dataModel.getValueAt(i, 5).toString());
               
               //edit qty pada txtQty
               txtQty.requestFocus();
            }catch (ArrayIndexOutOfBoundsException e) {
                //jika belum klik row maka tampilkan notif
                JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }
}

//function update table
public void updateTabel(){
    //deklarasi model tabel
    DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
    
    //cek jika tabel kosong maka update digagalkan
    if(txtCode.getText().equals("") || txtDescription.getText().equals("") || txtSize.getText().equals("") || txtArticle.getText().equals("") || txtMerk.getText().equals("") || txtSellprice.getText().equals("") || txtQty.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
            txtCode.requestFocus();
        }else{
        //jika sudah diisi maka lakukan update, menambhkan data dari jtable ke textfield dengan klik row
        int i = tabelData.getSelectedRow();
         if(i >= 0){
             dataModel.setValueAt(txtCode.getText(), i, 0);
             dataModel.setValueAt(txtDescription.getText(), i, 1);
             dataModel.setValueAt(txtSize.getText(), i, 2);
             dataModel.setValueAt(txtArticle.getText(), i, 3);
             dataModel.setValueAt(txtMerk.getText(), i, 4);
             dataModel.setValueAt(txtSellprice.getText(), i, 5);
             dataModel.setValueAt(txtQty.getText(),i , 6);
             
             //kosongkan textfield
             txtCode.setText("");
             txtDescription.setText("");
             txtSize.setText("");
             txtArticle.setText("");
             txtMerk.setText("");
             txtSellprice.setText("");
             txtQty.setText("");
             txtSellprice.setText("");
             txtCode.requestFocus();
             btnSave.setEnabled(true);
             btnUpdate.setEnabled(false);
         }else{
             JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
         }
    }
        
}

//Function add table
public void addData(){
    //cek jika textfield masih kosong maka operasi add table gagal
    if(txtCode.getText().equals("") || txtDescription.getText().equals("") || txtSize.getText().equals("") || txtArticle.getText().equals("") || txtMerk.getText().equals("") || txtSellprice.getText().equals("") || txtQty.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
            txtCode.requestFocus();
        }else{
        //jika tidak maka lakukan insert table
        
        //deklarasi model tabel
            DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
            //aktifkan akses
                btnDeleteSelected.setEnabled(true);
                btnEditRows.setEnabled(true);
                btnSave.setEnabled(true);
                
                //masukkan ke table
                List list = new ArrayList<>();
                tabelData.setAutoCreateColumnsFromModel(true);
                list.add(txtCode.getText());
                list.add(txtDescription.getText());
                list.add(txtSize.getText());
                list.add(txtArticle.getText());
                list.add(txtMerk.getText());
                list.add(txtSellprice.getText());
                list.add(txtQty.getText());
                list.add(tgl_db.getText()+" "+jJam.getText());
                list.add(txtUseInput.getText());
                dataModel.addRow(list.toArray());
                
            //kosongkan kembali textfiedl
                txtCode.setText("");
                txtDescription.setText("");
                txtSize.setText("");
                txtArticle.setText("");
                txtMerk.setText("");
                txtSellprice.setText("");
                txtQty.setText("");
                txtSellprice.setText("");
                txtCode.requestFocus();
                
                //informasi jumlah rows
                int jml=dataModel.getRowCount();
                txtJmlRow.setText(""+jml);
    }
}

//function koneksi
public Connection getConnection(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
    }catch(ClassNotFoundException ex){
        System.out.println(ex.getMessage());
    }
    
    Connection con = null;
    try{
        con = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
    }catch(SQLException ex){
        System.out.println(ex.getMessage());
    }
    return con;
    }

//function save data
public void saveData(){
    //panggil koneksi
        Connection con = getConnection();
        Statement st;
        
        //deklarasi tabel
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        try {
            st = con.createStatement();
            
            for (int i = 0; i < model.getRowCount(); i++){
                String sku = model.getValueAt(i, 0).toString();
                String desc = model.getValueAt(i, 1).toString();
                String size = model.getValueAt(i, 2).toString();
                String article = model.getValueAt(i, 3).toString();
                String merk = model.getValueAt(i, 4).toString();
                String qty = model.getValueAt(i, 6).toString();
                String sellprice = model.getValueAt(i, 5).toString();
                String createddate = model.getValueAt(i, 7).toString();
                String createdby = model.getValueAt(i, 8).toString();
                
        //query masukkan row ke database
                String sqlQuery = "INSERT INTO `receive_item`(`sku_id`, `sku_desc`, `size_code`, `article_desc`, `merk_desc`, `qty`, `sellprice`, `created_date`, `created_by`) VALUES ('"+sku+"','"+desc+"','"+size+"','"+article+"','"+merk+"','"+qty+"','"+sellprice+"','"+createddate+"','"+createdby+"')";
        //eksekusi query   
                
                st.addBatch(sqlQuery);
            }
        //jika sukses tampilkan jumlah row yang diinput    
            int[] rowsInserted = st.executeBatch();
            JOptionPane.showMessageDialog(rootPane, rowsInserted.length+" Row Berhasil Diinput Ke Database");
            model.setRowCount(0);
            int jml=model.getRowCount();
            txtJmlRow.setText(""+jml);
            txtCode.requestFocus();
            btnSave.setEnabled(false);
            btnEditRows.setEnabled(false);
            btnDeleteSelected.setEnabled(false);
            btnCancel.setEnabled(false);
        
        //jika gagal tampilkan pesan eror detailnya
        } catch (SQLException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}

//function cancel input kosongkan textfield
public void cancelInput(){
    txtCode.setText("");
        txtDescription.setText("");
        txtSize.setText("");
        txtArticle.setText("");
        txtMerk.setText("");
        txtSellprice.setText("");
        txtQty.setText("");
        txtCode.requestFocus();
}

//kembali ke menu utama
public void kembali(){
    new MenuUtama ().setVisible(true);
    txtUserLogin.setText(txtUseInput.getText());
    divisi.setText(txtDivisi.getText());
    
    if(txtDivisi.getText().trim().equals("USER")){
        dashboard.setEnabled(false);
    }else if(txtDivisi.getText().trim().equals("SUPERVISOR")){
        dashboard.setEnabled(false);
    }
    this.dispose();
}

//delete row yang terpilih
public void deleteSelected(){
    DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
         try {
            int x = tabelData.getSelectedRow();
            dataModel.removeRow(x);
            txtCode.requestFocus();
            int jml=dataModel.getRowCount();
            txtJmlRow.setText(""+jml);
            btnSave.setEnabled(false);
            txtQty.setEnabled(false);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", 
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtDescription = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSize = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtArticle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMerk = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSellprice = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnKembali = new javax.swing.JButton();
        btnEditRows = new javax.swing.JButton();
        btnDeleteSelected = new javax.swing.JButton();
        txtJmlRow = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTanggal = new javax.swing.JLabel();
        jJam = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Code Item", "Description", "size","Article", "Merk", "Sell Price", "Qty", "Created date", "Created by"
            }
        ));
        tabelData.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabelData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelData);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("CODE ITEM");

        txtCode.setBackground(new java.awt.Color(51, 51, 51));
        txtCode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCode.setForeground(new java.awt.Color(255, 102, 0));
        txtCode.setToolTipText("");
        txtCode.setBorder(null);
        txtCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeActionPerformed(evt);
            }
        });
        txtCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodeKeyTyped(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(51, 51, 51));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 102, 0));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 2));
        btnCancel.setContentAreaFilled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 102, 0));
        jLabel6.setText("QTY");

        txtQty.setBackground(new java.awt.Color(51, 51, 51));
        txtQty.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtQty.setForeground(new java.awt.Color(255, 102, 0));
        txtQty.setBorder(null);
        txtQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtQtyMouseClicked(evt);
            }
        });
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        txtDescription.setBackground(new java.awt.Color(51, 51, 51));
        txtDescription.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDescription.setForeground(new java.awt.Color(255, 102, 0));
        txtDescription.setBorder(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("DESCRIPTION");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 102, 0));
        jLabel7.setText("Size");

        txtSize.setBackground(new java.awt.Color(51, 51, 51));
        txtSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSize.setForeground(new java.awt.Color(255, 102, 0));
        txtSize.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 0));
        jLabel3.setText("ARTICLE");

        txtArticle.setBackground(new java.awt.Color(51, 51, 51));
        txtArticle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtArticle.setForeground(new java.awt.Color(255, 102, 0));
        txtArticle.setBorder(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 102, 0));
        jLabel4.setText("MERK");

        txtMerk.setBackground(new java.awt.Color(51, 51, 51));
        txtMerk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMerk.setForeground(new java.awt.Color(255, 102, 0));
        txtMerk.setBorder(null);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 102, 0));
        jLabel5.setText("SELL PRICE");

        txtSellprice.setBackground(new java.awt.Color(51, 51, 51));
        txtSellprice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSellprice.setForeground(new java.awt.Color(255, 102, 0));
        txtSellprice.setBorder(null);
        txtSellprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSellpriceKeyPressed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(102, 102, 102));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 102, 0));
        btnUpdate.setText("Update");
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(102, 102, 102));
        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 102, 0));
        btnSave.setText("Save");
        btnSave.setBorder(null);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        btnSave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSaveKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtSellprice, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSellprice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        btnKembali.setBackground(new java.awt.Color(51, 51, 51));
        btnKembali.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKembali.setForeground(new java.awt.Color(255, 102, 0));
        btnKembali.setText("Back");
        btnKembali.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 2));
        btnKembali.setContentAreaFilled(false);
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        btnEditRows.setBackground(new java.awt.Color(51, 51, 51));
        btnEditRows.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditRows.setForeground(new java.awt.Color(255, 102, 0));
        btnEditRows.setText("Edit Selected");
        btnEditRows.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 2));
        btnEditRows.setContentAreaFilled(false);
        btnEditRows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRowsActionPerformed(evt);
            }
        });

        btnDeleteSelected.setBackground(new java.awt.Color(51, 51, 51));
        btnDeleteSelected.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteSelected.setForeground(new java.awt.Color(255, 102, 0));
        btnDeleteSelected.setText("Delete Selected");
        btnDeleteSelected.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 2));
        btnDeleteSelected.setContentAreaFilled(false);
        btnDeleteSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedActionPerformed(evt);
            }
        });

        txtJmlRow.setBackground(new java.awt.Color(51, 51, 51));
        txtJmlRow.setBorder(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditRows, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtJmlRow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditRows, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJmlRow, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 102, 0));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Form Barang");

        jTanggal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTanggal.setForeground(null);
        jTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTanggal.setText("jTanggal");

        txtUseInput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUseInput.setForeground(null);
        txtUseInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtUseInput.setText("User Input");

        txtDivisi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDivisi.setForeground(null);
        txtDivisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtDivisi.setText("Divisi");

        jJam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jJam.setForeground(null);
        jJam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jJam.setText("jJam");

        tgl_db.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tgl_db.setForeground(null);
        tgl_db.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tgl_db.setText("tgl_for_db");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(177, 177, 177)
                .addComponent(jJam)
                .addGap(51, 51, 51)
                .addComponent(jTanggal)
                .addGap(90, 90, 90)
                .addComponent(tgl_db)
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDivisi)
                    .addComponent(txtUseInput))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtUseInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDivisi))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jTanggal)
                        .addComponent(jJam)
                        .addComponent(tgl_db)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(945, 674));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        if(model.getRowCount()==0){
            //JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
            txtCode.requestFocus();
        }else{
        saveData();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        cancelInput();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeActionPerformed

    private void txtCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
        try{
            Statement statement = (Statement)Connect.GetConnection().createStatement();
            
            ResultSet result=statement.executeQuery("SELECT * FROM mst_barang WHERE sku_id='" + txtCode.getText()+"'");
            if(result.next()){
                txtDescription.setText(result.getString("sku_desc"));
                txtSize.setText(result.getString("size_code"));
                txtArticle.setText(result.getString("article_desc"));
                txtMerk.setText(result.getString("merk_desc"));
                txtSellprice.setText(result.getString("sellprice"));
                
                btnEditRows.setVisible(true);
                btnDeleteSelected.setVisible(true);
                btnSave.setVisible(true);
                txtDescription.setEditable(false);
                txtSize.setEditable(false);
                txtArticle.setEditable(false);
                txtMerk.setEditable(false);
                txtSellprice.setEditable(false);
                txtQty.setEnabled(true);
                txtQty.requestFocus();
                btnCancel.setEnabled(true);
                
            }else{
                JOptionPane.showMessageDialog(rootPane, "SKU Not Match");
                txtCode.setText("");
                txtQty.setText("");
                txtCode.requestFocus();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(rootPane,"Gagal");
            this.dispose();
        }
        }
    }//GEN-LAST:event_txtCodeKeyPressed

    private void txtSellpriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSellpriceKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
         txtQty.requestFocus();   
        }
    }//GEN-LAST:event_txtSellpriceKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
            
            String skuNew = txtCode.getText();
            int total = 0;
            int b = dataModel.getRowCount();
            
            for(int i = 0; i < b; i++){
                int qtyNew = Integer.parseInt(txtQty.getText());
                String skuOld = String.valueOf(dataModel.getValueAt(i, 0));
                int qtyOld = Integer.parseInt((String)dataModel.getValueAt(i, 6));
                String qtyBaru = Integer.toString(qtyNew);
                String qtyLama = Integer.toString(qtyOld);
                
                if(skuOld.equals(txtCode.getText())){
                    int qtyUp = qtyNew+qtyOld;
                    String qtyFix = String.valueOf(qtyUp);
                    txtCode.setText(dataModel.getValueAt(i, 0).toString());
                    txtDescription.setText(dataModel.getValueAt(i, 1).toString());
                    txtSize.setText(dataModel.getValueAt(i, 2).toString());
                    txtArticle.setText(dataModel.getValueAt(i, 3).toString());
                    txtMerk.setText(dataModel.getValueAt(i, 4).toString());
                    txtSellprice.setText(dataModel.getValueAt(i, 5).toString());
                    txtQty.requestFocus();
                    tabelData.changeSelection(i,0,false, false);
                    btnUpdate.setEnabled(true);
                    btnSave.setEnabled(false);
                    
                    if(txtCode.getText().equals("") || txtDescription.getText().equals("") || txtSize.getText().equals("") || txtArticle.getText().equals("") || txtMerk.getText().equals("") || txtSellprice.getText().equals("") || txtQty.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
                        txtCode.requestFocus();
                    }
                    
                    int baris = tabelData.getSelectedRow();
                    if(baris >= 0){
                        dataModel.setValueAt(txtCode.getText(), baris, 0);
                        dataModel.setValueAt(txtDescription.getText(), baris, 1);
                        dataModel.setValueAt(txtSize.getText(), baris, 2);
                        dataModel.setValueAt(txtArticle.getText(), baris, 3);
                        dataModel.setValueAt(txtMerk.getText(), baris, 4);
                        dataModel.setValueAt(txtSellprice.getText(), baris, 5);
                        dataModel.setValueAt(qtyFix,baris , 6);
             
             
                        txtCode.setText("");
                        txtDescription.setText("");
                        txtSize.setText("");
                        txtArticle.setText("");
                        txtMerk.setText("");
                        txtSellprice.setText("");
                        txtQty.setText("");
                        txtSellprice.setText("");
                        txtCode.requestFocus();
                        btnSave.setEnabled(true);
                        btnUpdate.setEnabled(false);
                    }else{
                        JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                    } 
                    return;                
                }else{
                }
            }
            addData();
        }
    }//GEN-LAST:event_txtQtyKeyPressed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        kembali();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnDeleteSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedActionPerformed
        // TODO add your handling code here:
        deleteSelected();
    }//GEN-LAST:event_btnDeleteSelectedActionPerformed

    private void tabelDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDataMouseClicked

    private void btnEditRowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRowsActionPerformed
        // TODO add your handling code here:
        editTabel();
    }//GEN-LAST:event_btnEditRowsActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateTabel();
        btnUpdate.setEnabled (false);
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtCodeKeyTyped

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQtyMouseClicked
        // TODO add your handling code here
    }//GEN-LAST:event_txtQtyMouseClicked

    private void btnSaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSaveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveKeyPressed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeleteSelected;
    private javax.swing.JButton btnEditRows;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jJam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTanggal;
    private javax.swing.JTable tabelData;
    public static final javax.swing.JLabel tgl_db = new javax.swing.JLabel();
    private javax.swing.JTextField txtArticle;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtDescription;
    public static final javax.swing.JLabel txtDivisi = new javax.swing.JLabel();
    private javax.swing.JTextField txtJmlRow;
    private javax.swing.JTextField txtMerk;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSellprice;
    private javax.swing.JTextField txtSize;
    public static final javax.swing.JLabel txtUseInput = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
}
