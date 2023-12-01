/**
 * merupakan turunan dari kelas {@code Akun}.
 * Menyediakan metode untuk mengambil username, password, dan
 * memeriksa kredensial login.
 * @author Dinal
 */
class Admin extends Akun {
    public Admin(String username, String password) {
        super(username, password);
    }

     /**
     * @return username dari akun admin
     */
    public String getUsername() {
        return username;
    }

      /**
     * @return password dari akun admin
     */
    public String getPassword() {
        return password;
    }
    
     /**
     * @param enteredUsername username yang dimasukkan oleh pengguna
     * @param enteredPassword password yang dimasukkan oleh pengguna
     * @return {@code true} jika kredensial yang dimasukkan valid,
     *         {@code false} sebaliknya
     */
    public boolean checkLogin(String enteredUsername, String enteredPassword) {
        return super.checkLogin(enteredUsername, enteredPassword);
    }
}
