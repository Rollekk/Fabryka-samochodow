package z12.Pracownik;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginDialogP extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    private boolean succeeded;

    public LoginDialogP(Frame parent) {
        super(parent, "LoginP", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;


        lbUsername = new JLabel("Login: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Hasło: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Zaloguj");

        btnLogin.addActionListener(e -> {
            try {
                if (LoginP.authenticate(getUsername(), getPassword())) {
                    JOptionPane.showMessageDialog(LoginDialogP.this,
                            "Witaj " + getUsername() + "! Zalogowano do systemu.",
                            "Logowanie",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialogP.this,
                            "Błędny login lub hasło",
                            "Logowanie",
                            JOptionPane.ERROR_MESSAGE);

                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;

                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btnCancel = new JButton("Anuluj");
        btnCancel.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
