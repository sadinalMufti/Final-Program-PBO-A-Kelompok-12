/**
 * Kelas {@code Customer} merupakan subkelas dari {@code Akun} yang merepresentasikan akun pengguna dengan peran pelanggan.
 * Kelas ini menyediakan konstruktor untuk membuat objek pelanggan serta mengakses dan memverifikasi informasi login.
 * @author Dinal
 */
class Customer extends Akun {
    /**
     * Konstruktor untuk membuat objek {@code Customer} dengan username dan password tertentu.
     *
     * @param username Username pelanggan
     * @param password Password pelanggan
     */
    public Customer(String username, String password) {
        super(username, password);
    }

    /**
     * @return Password pelanggan
     */
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * @return Username pelanggan
     */
    public String getUsername() {
        return super.getUsername();
    }
  
    /**
     * @param enteredUsername Username yang dimasukkan oleh pengguna
     * @param enteredPassword Password yang dimasukkan oleh pengguna
     * @return {@code true} jika kredensial cocok, {@code false} jika tidak cocok
     */
    public boolean checkLogin(String enteredUsername, String enteredPassword) {
        return super.checkLogin(enteredUsername, enteredPassword);
    }
}
