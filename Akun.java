/**
 * Kelas ini menyimpan informasi dasar seperti username dan password.
 * @author Dinal
 */
class Akun {
    protected String username;
    protected String password;
    String id;

    /**
     * Konstruktor untuk membuat objek {@code Akun} dengan username dan password tertentu.
     *
     * @param username Username untuk akun
     * @param password Password untuk akun
     */
    public Akun(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return Username dari akun
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Password dari akun
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param enteredUsername Username yang dimasukkan oleh pengguna
     * @param enteredPassword Password yang dimasukkan oleh pengguna
     * @return {@code true} jika kredensial cocok, {@code false} jika tidak cocok
     */  
    public boolean checkLogin(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    /**
     * @return ID dari akun
     */
    public String getId() {
        return id;
    }
    
}
