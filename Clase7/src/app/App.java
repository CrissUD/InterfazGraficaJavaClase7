package app;

import javax.swing.SwingUtilities;

import app.client.login.LoginComponent;

public class App {
  public static void main(String[] args) {
    Runnable runApplication = new Runnable() {
      public void run() {
        LoginComponent login = new LoginComponent();
        login.getClass();
      }
    };
    SwingUtilities.invokeLater(runApplication);
  }
}