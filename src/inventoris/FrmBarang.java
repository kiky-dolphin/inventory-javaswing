/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoris;

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
import static inventoris.MenuUtama.dashboard;
import static inventoris.MenuUtama.divisi;
import static inventoris.MenuUtama.txtUserLogin;
import java.awt.event.MouseEvent;
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
        Tampil_Jam();
        Tampil_Tanggal();
        tgl_db();
        tgl_db.setVisible(false);
        btnUpdate.setVisible(false);
        btnEditRows.setVisible(false);
        btnDeleteSelected.setVisible(false);
        btnSave.setVisible(false);
        txtQty.setVisible(false);
    }
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
 
public void Tampil_Tanggal() {
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    jTanggal.setText(tanggal);
}

public void tgl_db(){
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    tgl_db.setText(tanggal);
}

public void editTabel(){
    DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
            
            // i = index tabel
            try {
               btnUpdate.setVisible(true);
               btnSave.setVisible(false);
               int i = tabelData.getSelectedRow();
               txtCode.setText(dataModel.getValueAt(i, 0).toString());
               txtDescription.setText(dataModel.getValueAt(i, 1).toString());
               txtSize.setText(dataModel.getValueAt(i, 2).toString());
               txtArticle.setText(dataModel.getValueAt(i, 3).toString());
               txtMerk.setText(dataModel.getValueAt(i, 4).toString());
               txtSellprice.setText(dataModel.getValueAt(i, 5).toString());
               txtQty.requestFocus();
            }catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }
}

public void updateTabel(){
    DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
    if(txtCode.getText().equals("") || txtDescription.getText().equals("") || txtSize.getText().equals("") || txtArticle.getText().equals("") || txtMerk.getText().equals("") || txtSellprice.getText().equals("") || txtQty.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
            txtCode.requestFocus();
        }
        int i = tabelData.getSelectedRow();
         if(i >= 0){
             dataModel.setValueAt(txtCode.getText(), i, 0);
             dataModel.setValueAt(txtDescription.getText(), i, 1);
             dataModel.setValueAt(txtSize.getText(), i, 2);
             dataModel.setValueAt(txtArticle.getText(), i, 3);
             dataModel.setValueAt(txtMerk.getText(), i, 4);
             dataModel.setValueAt(txtSellprice.getText(), i, 5);
             dataModel.setValueAt(txtQty.getText(),i , 6);
             
             
             txtCode.setText("");
             txtDescription.setText("");
             txtSize.setText("");
             txtArticle.setText("");
             txtMerk.setText("");
             txtSellprice.setText("");
             txtQty.setText("");
             txtSellprice.setText("");
             txtCode.requestFocus();
             btnSave.setVisible(true);
             btnUpdate.setVisible(false);
         }else{
             JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
         }
}

public void addData(){
    if(txtCode.getText().equals("") || txtDescription.getText().equals("") || txtSize.getText().equals("") || txtArticle.getText().equals("") || txtMerk.getText().equals("") || txtSellprice.getText().equals("") || txtQty.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
            txtCode.requestFocus();
        }else{
        DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
       
                btnDeleteSelected.setVisible(true);
                btnEditRows.setVisible(true);
                
                
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
                
            
                txtCode.setText("");
                txtDescription.setText("");
                txtSize.setText("");
                txtArticle.setText("");
                txtMerk.setText("");
                txtSellprice.setText("");
                txtQty.setText("");
                txtSellprice.setText("");
                txtCode.requestFocus();
                
                
            
                int jml=dataModel.getRowCount();
                txtJmlRow.setText(""+jml);
    }
}

//public void sumQty(){
//    //deklarasi tabel
//    DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
//    
//    //variable baris dan kolom table
//    int i = tabelData.getRowCount();
//    int a = tabelData.getColumnCount();
//    
//    //variable menampung data yang sudah ada ditable
//    String skuOld = String.valueOf(dataModel.getValueAt(i, 0));
//    int qtyOld = (Integer) tabelData.getValueAt(i, 6);
//    
//    //variabel menampung data yang akan diinput ke table
//    String skuNew = txtCode.getText();
//    int qtyNew = Integer.parseInt(txtQty.getText());
//    
//    //kondisi jika sku_id yang akan diinput sudah ada maka menjumlahkan qty saja
//        qtyNew += qtyOld;
//             dataModel.setValueAt(txtCode.getText(), i, 0);
//             dataModel.setValueAt(txtDescription.getText(), i, 1);
//             dataModel.setValueAt(txtSize.getText(), i, 2);
//             dataModel.setValueAt(txtArticle.getText(), i, 3);
//             dataModel.setValueAt(txtMerk.getText(), i, 4);
//             dataModel.setValueAt(txtSellprice.getText(), i, 5);
//             dataModel.setValueAt(qtyNew,i, 6);
//    
//}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTanggal = new javax.swing.JLabel();
        jJam = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        txtSize = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        txtDescription = new javax.swing.JTextField();
        txtArticle = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtMerk = new javax.swing.JTextField();
        txtSellprice = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnEditRows = new javax.swing.JButton();
        btnDeleteSelected = new javax.swing.JButton();
        txtJmlRow = new javax.swing.JTextField();
        btnKembali = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel4.setBackground(new java.awt.Color(255, 102, 0));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(255, 102, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Form Input Barang");

        jTanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTanggal.setForeground(new java.awt.Color(255, 255, 255));
        jTanggal.setText("jTanggal");

        jJam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jJam.setForeground(new java.awt.Color(255, 255, 255));
        jJam.setText("jJam");

        tgl_db.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tgl_db.setForeground(new java.awt.Color(255, 255, 255));
        tgl_db.setText("tgl_for_db");

        txtUseInput.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUseInput.setForeground(new java.awt.Color(255, 255, 255));
        txtUseInput.setText("User Input");

        txtDivisi.setForeground(new java.awt.Color(255, 255, 255));
        txtDivisi.setText("Divisi");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTanggal)
                .addGap(26, 26, 26)
                .addComponent(jJam)
                .addGap(18, 18, 18)
                .addComponent(tgl_db)
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUseInput)
                    .addComponent(txtDivisi))
                .addGap(112, 112, 112))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTanggal)
                            .addComponent(jJam)
                            .addComponent(tgl_db)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtUseInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDivisi)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        btnCancel.setBackground(new java.awt.Color(51, 51, 51));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtSize.setBackground(new java.awt.Color(102, 102, 102));
        txtSize.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSize.setForeground(new java.awt.Color(255, 255, 255));
        txtSize.setBorder(null);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("SIZE");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Code Barang");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("DESCRIPTION");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ARTICLE");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MERK");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SELL PRICE");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Jumlah");

        txtCode.setBackground(new java.awt.Color(102, 102, 102));
        txtCode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCode.setForeground(new java.awt.Color(255, 255, 255));
        txtCode.setBorder(null);
        txtCode.setCaretColor(new java.awt.Color(255, 102, 0));
        txtCode.setMaximumSize(new java.awt.Dimension(274, 274));
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

        btnUpdate.setBackground(new java.awt.Color(255, 102, 0));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        txtDescription.setBackground(new java.awt.Color(102, 102, 102));
        txtDescription.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDescription.setForeground(new java.awt.Color(255, 255, 255));
        txtDescription.setBorder(null);

        txtArticle.setBackground(new java.awt.Color(102, 102, 102));
        txtArticle.setColumns(1);
        txtArticle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtArticle.setForeground(new java.awt.Color(255, 255, 255));
        txtArticle.setBorder(null);
        txtArticle.setCaretColor(new java.awt.Color(255, 102, 0));
        txtArticle.setSelectionColor(new java.awt.Color(255, 102, 0));

        btnAdd.setBackground(new java.awt.Color(255, 102, 51));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add to Column");
        btnAdd.setBorder(null);
        btnAdd.setPreferredSize(new java.awt.Dimension(43, 20));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
            }
        });

        txtMerk.setBackground(new java.awt.Color(102, 102, 102));
        txtMerk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMerk.setForeground(new java.awt.Color(255, 255, 255));
        txtMerk.setBorder(null);
        txtMerk.setSelectionColor(new java.awt.Color(255, 102, 0));

        txtSellprice.setBackground(new java.awt.Color(102, 102, 102));
        txtSellprice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSellprice.setForeground(new java.awt.Color(255, 255, 255));
        txtSellprice.setBorder(null);
        txtSellprice.setSelectionColor(new java.awt.Color(255, 102, 0));
        txtSellprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSellpriceKeyPressed(evt);
            }
        });

        txtQty.setBackground(new java.awt.Color(102, 102, 102));
        txtQty.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtQty.setForeground(new java.awt.Color(255, 255, 255));
        txtQty.setBorder(null);
        txtQty.setCaretColor(new java.awt.Color(255, 102, 0));
        txtQty.setSelectionColor(new java.awt.Color(255, 102, 0));
        txtQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtQtyMouseClicked(evt);
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

        btnSave.setBackground(new java.awt.Color(255, 102, 0));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setBorder(null);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSellprice, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(76, 76, 76)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQty)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSellprice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jScrollPane1.setBorder(null);

        tabelData.setBackground(new java.awt.Color(102, 102, 102));
        tabelData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Preview");

        btnEditRows.setBackground(new java.awt.Color(255, 102, 0));
        btnEditRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditRows.setForeground(new java.awt.Color(255, 255, 255));
        btnEditRows.setText("Edit Selected");
        btnEditRows.setBorder(null);
        btnEditRows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRowsActionPerformed(evt);
            }
        });

        btnDeleteSelected.setBackground(new java.awt.Color(255, 102, 0));
        btnDeleteSelected.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteSelected.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSelected.setText("Delete Selected");
        btnDeleteSelected.setBorder(null);
        btnDeleteSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedActionPerformed(evt);
            }
        });

        txtJmlRow.setBackground(new java.awt.Color(102, 102, 102));
        txtJmlRow.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtJmlRow.setForeground(new java.awt.Color(255, 255, 255));
        txtJmlRow.setBorder(null);
        txtJmlRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmlRowActionPerformed(evt);
            }
        });

        btnKembali.setBackground(new java.awt.Color(51, 51, 51));
        btnKembali.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKembali.setForeground(new java.awt.Color(255, 255, 255));
        btnKembali.setText("Back");
        btnKembali.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtJmlRow, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnEditRows, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditRows, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJmlRow, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel8))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 872, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 724, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(888, 732));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
//buat function koneksi
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
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        //panggil koneksi
        Connection con = getConnection();
        Statement st;
        
        //deklarasi tabel
        DefaultTableModel model = (DefaultTableModel) tabelData.getModel();
        //start
        if(model.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Lengkapi Data Terlebih Dahulu");
            txtCode.requestFocus();
        }else{
            //cek jika database sudah ada
//            for(int a = 0; a < model.getRowCount(); a++){
//                String skua = model.getValueAt(a, 0).toString();
//                String qtya = model.getValueAt(a, 6).toString();
//                String sqlQueryA = "SELECT * FROM receive_item WHERE sku_id='"+skua+"'";
//            }
            
        
        try {
            st = con.createStatement();
            //variable tampungan untuk membaca sku dan qty yang sudah ada didatabase
//            String skuOld = "Select sku_id from receive_item where sku_id='"+txtCode.getText()+"'";
//            String qtyOld = "Select qty from receive_item where sku_id='"+txtCode.getText()+"'";
//            if(skuOld.equals(txtCode.getText()))
            
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
                
//                String SkuOld = "SELECT sku_id FROM receive_item WHERE sku_id = '"+sku+"'";
//                String QtyOld = "SELECT qty FROM receive_item WHERE sku_id = '"+sku+"'";
                
//                if(SkuOld.equals(sku)){
//                    int QtyOldInt = Integer.parseInt(QtyOld);
//                    int QtyNewInt = Integer.parseInt(qty);
//                    int QtyUp = QtyOldInt + QtyNewInt;
//                    String QtyFix = String.valueOf(QtyUp);
//                    String QueryUpdate = "UPDATE receive_item SET qty = '"+QtyFix+"' WHERE sku_id = '"+sku+"'";
//                    st.addBatch(QueryUpdate);
//                }else{
                    //query masukkan row ke database
                String sqlQuery = "INSERT INTO `receive_item`(`sku_id`, `sku_desc`, `size_code`, `article_desc`, `merk_desc`, `qty`, `sellprice`, `created_date`, `created_by`) VALUES ('"+sku+"','"+desc+"','"+size+"','"+article+"','"+merk+"','"+qty+"','"+sellprice+"','"+createddate+"','"+createdby+"')";
        //eksekusi query        
                st.addBatch(sqlQuery);
                //}
            }
        //jika sukses tampilkan jumlah row yang diinput    
            int[] rowsInserted = st.executeBatch();
            JOptionPane.showMessageDialog(rootPane, rowsInserted.length+" Row Berhasil Diinput Ke Database");
            model.setRowCount(0);
            txtCode.requestFocus();
        
        //jika gagal tampilkan pesan eror detailnya
        } catch (SQLException ex) {
            Logger.getLogger(FrmBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        txtCode.setText("");
        txtDescription.setText("");
        txtSize.setText("");
        txtArticle.setText("");
        txtMerk.setText("");
        txtSellprice.setText("");
        txtQty.setText("");
        txtCode.requestFocus();
        
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
                txtQty.setVisible(true);
                txtQty.requestFocus();
                
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
            btnAdd.requestFocus();
        }
    }//GEN-LAST:event_txtQtyKeyPressed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        new MenuUtama ().setVisible(true);
        txtUserLogin.setText(txtUseInput.getText());
        divisi.setText(txtDivisi.getText());
        if(txtDivisi.getText().trim().equals("USER")){
            dashboard.setEnabled(false);
        }else if(txtDivisi.getText().trim().equals("SUPERVISOR")){
            dashboard.setEnabled(false);
        }
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnDeleteSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dataModel = (DefaultTableModel) tabelData.getModel();
         try {
            int x = tabelData.getSelectedRow();
            dataModel.removeRow(x);
            txtCode.requestFocus();
            int jml=dataModel.getRowCount();
            txtJmlRow.setText(""+jml);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", 
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
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
        btnAdd.setVisible(true);
        btnUpdate.setVisible (false);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
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
               btnUpdate.setVisible(true);
               btnSave.setVisible(false);
               btnAdd.setVisible(true);
               
               
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
             btnSave.setVisible(true);
             btnUpdate.setVisible(false);
         }else{
             JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
         }
               
//               int n = JOptionPane.showConfirmDialog(null,"sample question?!" ,"",JOptionPane.YES_NO_OPTION);
//      if(n == JOptionPane.YES_OPTION){
//          JOptionPane.showMessageDialog(null, "Opening...");
//      }else{
//          JOptionPane.showMessageDialog(null, "Goodbye");
//           }
//          System.exit(0);
                
                return;
                
//                
//                    
//                    total = qtyNew + qtyOld;
//                    
//                    String ttl=Integer.toString(total);
//                    
//                    dataModel.setValueAt(ttl, a, 6);             
//
//                    txtCode.setText("");
//                    txtDescription.setText("");
//                    txtSize.setText("");
//                    txtArticle.setText("");
//                    txtMerk.setText("");
//                    txtSellprice.setText("");
//                    txtQty.setText("");
//                    txtCode.requestFocus();   
//                
            }else{
      
            }  
        }
         addData();
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtCodeKeyTyped

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQtyMouseClicked
        // TODO add your handling code here
//        int clicked = evt.getClickCount();
//        if(!(Character.isDigit(clicked))){
//            txtCode.requestFocus();
//            evt.consume();
//        }else{
//            btnAdd.requestFocus();
//        }
    }//GEN-LAST:event_txtQtyMouseClicked

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        // TODO add your handling code here:
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
               btnUpdate.setVisible(true);
               btnSave.setVisible(false);
               btnAdd.setVisible(true);
               
               
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
             btnSave.setVisible(true);
             btnUpdate.setVisible(false);
         }else{
             JOptionPane.showMessageDialog(this, "Tabel Belum Dipilih", "Kesalahan", JOptionPane.WARNING_MESSAGE);
         }
               
//               int n = JOptionPane.showConfirmDialog(null,"sample question?!" ,"",JOptionPane.YES_NO_OPTION);
//      if(n == JOptionPane.YES_OPTION){
//          JOptionPane.showMessageDialog(null, "Opening...");
//      }else{
//          JOptionPane.showMessageDialog(null, "Goodbye");
//           }
//          System.exit(0);
                
                return;
                
//                
//                    
//                    total = qtyNew + qtyOld;
//                    
//                    String ttl=Integer.toString(total);
//                    
//                    dataModel.setValueAt(ttl, a, 6);             
//
//                    txtCode.setText("");
//                    txtDescription.setText("");
//                    txtSize.setText("");
//                    txtArticle.setText("");
//                    txtMerk.setText("");
//                    txtSellprice.setText("");
//                    txtQty.setText("");
//                    txtCode.requestFocus();   
//                
            }else{
      
            }  
        }
         addData();
         
        
        
    }//GEN-LAST:event_btnAddKeyPressed

    private void txtJmlRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmlRowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJmlRowActionPerformed

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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeleteSelected;
    private javax.swing.JButton btnEditRows;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jJam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JSeparator jSeparator1;
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
