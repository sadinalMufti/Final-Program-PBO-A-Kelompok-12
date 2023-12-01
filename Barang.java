import java.io.Serializable;
/**
 * Kelas {@code Barang} merupakan kelas yang merepresentasikan informasi mengenai suatu barang.
 * Kelas ini implementasi dari antarmuka {@code Serializable} untuk mendukung serialisasi objek.
 * @author Dinal
 */
public class Barang implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nama;
    private int harga;
    private int stok;

    /**
     * Konstruktor untuk membuat objek {@code Barang} dengan nama, harga, dan stok tertentu.
     *
     * @param nama  Nama barang
     * @param harga Harga barang
     * @param stok  Jumlah stok barang
     */
    public Barang(String nama, int harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    /**
     * @return Nama barang
     */
    public String getNama() {
        return nama;
    }

    /**
     * @return Harga barang
     */
    public int getHarga() {
        return harga;
    }

    /**
     * @return Jumlah stok barang
     */
    public int getStok() {
        return stok;
    }

    /**
     * @param harga Harga baru untuk barang
     */
    public void setHarga(int harga) {
        this.harga = harga;
    }

    /**
     * @param stok Jumlah stok baru untuk barang
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
}
