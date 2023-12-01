/**
 * Kelas abstrak yang merepresentasikan suatu jenis driver.
 * Driver ini memiliki fungsi utama untuk menampilkan menu.
 * @author Alvia
 */
abstract class  Driver {

    /**
     * Menampilkan menu utama yang spesifik untuk jenis driver tertentu.
     * Implementasi metode ini harus disediakan oleh kelas turunan.
     */
    public abstract void showMenu();
}
