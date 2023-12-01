/**
 * Kelas abstrak {@code Pembayaran} menyediakan kerangka dasar untuk menangani proses pembayaran.
 * Subkelas yang mengimplementasikan kelas ini diharapkan mengisi metode abstrak {@code prosesPembayaran()}.
 * @author Alvia
 */
public abstract class Pembayaran {
    protected Invoice invoice;
    protected Transaksi transaksi;
    Keranjang keranjang = new Keranjang();

     /**
     * Konstruktor untuk membuat objek {@code Pembayaran}.
     * Inisialisasi objek {@code Invoice}, {@code Transaksi}, dan {@code Keranjang}.
     */
    public Pembayaran() {
        this.invoice = new Invoice();
        this.transaksi = new Transaksi();
    }

    /**
     * Metode abstrak yang harus diimplementasikan oleh subkelas untuk menangani proses pembayaran spesifik.
     */
    protected abstract void prosesPembayaran();

}
