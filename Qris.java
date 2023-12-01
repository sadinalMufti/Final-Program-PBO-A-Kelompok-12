/**
 * Kelas {@code Qris} merupakan subkelas dari {@code Pembayaran} yang mengimplementasikan
 * metode abstrak {@code prosesPembayaran()} untuk menangani pembayaran dengan metode QRIS (Quick Response Code).
 * @author Alvia
 */
public class Qris extends Pembayaran {

      /**
     * Konstruktor untuk membuat objek {@code Qris}.
     */
    public Qris(){
        
    }

    /**
     * Metode ini diimplementasikan untuk menangani proses pembayaran dengan metode QRIS.
     * Menampilkan QR Code yang dapat discan untuk pembayaran dan memberi informasi bahwa
     * pembayaran sedang diproses dan menunggu konfirmasi admin.
     */
    @Override
    protected void prosesPembayaran() {
        System.out.println("+--------------------------------------+");
        System.out.println("      Silahkan Scan QR Code Berikut\n");
        System.out.println("            |||||  |||| |||");
        System.out.println("            |||||||| | ||||");
        System.out.println("            ||| || ||||||||");
        System.out.println("            |||||| |||| |||");
        System.out.println("            ||||||||||| |||");
        System.out.println("\n Pembayaran dengan QRIS sedang diproses");
        System.out.println("      Menunggu konfirmasi admin\n");
        
    }

}
