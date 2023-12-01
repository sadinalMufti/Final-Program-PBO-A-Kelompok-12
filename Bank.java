/**
 * Kelas {@code Bank} merupakan subkelas dari {@code Pembayaran} yang mengimplementasikan
 * metode abstrak {@code prosesPembayaran()} untuk menangani pembayaran melalui bank.
 * @author Alvia
 */
public class Bank extends Pembayaran {

     /**
     * Metode ini diimplementasikan untuk menangani proses pembayaran melalui bank.
     * Menampilkan informasi rekening untuk transfer dan memberi konfirmasi setelah transfer diterima.
     */
    @Override
    protected void prosesPembayaran() {
        System.out.println("     \nSilakan transfer ke No.Rekening 36248245");
        System.out.println("Pembayaran berhasil setelah transfer diterima Admin\n");
    }
}
