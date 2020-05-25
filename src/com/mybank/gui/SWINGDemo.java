package com.mybank.gui;

import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.mybank.data.DataSource;
import com.mybank.domain.Account;


/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGDemo {
    
    private final JEditorPane log;
    private final JButton show;
    private final JButton report;
    private final JComboBox clients;
    
    public SWINGDemo() {
        log = new JEditorPane("text/html", "");
        log.setPreferredSize(new Dimension(250, 560));
        show = new JButton("Show");
        report = new JButton("Report");
        clients = new JComboBox();
        
        for (int i=0; i<Bank.getNumberOfCustomers();i++)
        {
            clients.addItem(Bank.getCustomer(i).getLastName()+", "+Bank.getCustomer(i).getFirstName());
        }
        
    }
    
    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));
        
        cpane.add(clients);
        cpane.add(show);
        cpane.add(report);
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(log, BorderLayout.CENTER);
        
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer current = Bank.getCustomer(clients.getSelectedIndex());
                String accType = current.getAccount(0)instanceof CheckingAccount?"Checking":"Savings";                
                String custInfo="<br>&nbsp;<b><span style=\"font-size:2em;\">"+current.getLastName()+", "+
                        current.getFirstName()+"</span><br><hr>"+
                        "&nbsp;<b>Acc Type: </b>"+accType+
                        "<br>&nbsp;<b>Balance: <span style=\"color:red;\">$"+current.getAccount(0).getBalance()+"</span></b>";
                log.setText(custInfo);                
            }
        });
        
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                StringBuilder repcust = new StringBuilder();
                 
                for(int i = 0; i < Bank.getNumberOfCustomers(); i++ ){
                    
                    Customer current = Bank.getCustomer(i);       
                    
                    repcust.append("<br>&nbsp;<b><span style=\"font-size:2em;\">").append(current.getLastName()).append(", ").append(current.getFirstName()).append("</span><br><hr>");                                                  
                          
                    for (int k = 0; k < current.getNumberOfAccounts(); k++) {
                        
                        Account account = current.getAccount(k);
                        
                       repcust.append("&nbsp;<b>Acc Type: </b>").append(account instanceof CheckingAccount ? "Checking" : "Savings").append("<br>&nbsp;<b>Balance: <span style=\"color:blue;\">$").append(account.getBalance()).append("</span></b><br>");
                    }
                }
                log.setText(repcust.toString()); 
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setResizable(false);
        frame.setVisible(true);        
    }
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        
       DataSource dataSource = new DataSource("data/test.dat");
        
        
            dataSource.loadData();
           
        SWINGDemo demo = new SWINGDemo();        
        demo.launchFrame();
         
    }
}