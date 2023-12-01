/**
 * Kelas {@code Cod} merupakan subkelas dari {@code Pembayaran} yang mengimplementasikan
 * metode abstrak {@code prosesPembayaran()} untuk menangani pembayaran dengan metode Cash on Delivery (COD).
 * @author Alvia
 */
public class Cod extends Pembayaran {

     /**
     * Metode ini diimplementasikan untuk menangani proses pembayaran dengan metode COD.
     * Memberikan informasi bahwa barang segera tiba dan memberi petunjuk untuk menyiapkan pembayaran.
     * Pembayaran dianggap berhasil setelah dikonfirmasi.
     */
    @Override
    protected void prosesPembayaran() {
        System.out.println("\nBarang anda segera tiba, siapkan pembayaran");
        System.out.println("Pembayaran berhasil jika sudah dikonfirmasi\n");
    }
}
