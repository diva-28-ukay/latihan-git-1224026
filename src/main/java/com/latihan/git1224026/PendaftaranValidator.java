package com.latihan.git1224026;

/**
 * Soal 3 - Validasi Pendaftaran (dari Tugas Minggu 3).
 */
public class PendaftaranValidator {

    public String validasiPendaftaran(String nama, int umur, String email, boolean setujuSyarat) {
        if (nama == null || nama.isEmpty()) { // P1
            return "Nama harus diisi";
        }
        if (umur < 17) { // P2
            return "Umur minimal 17 tahun";
        }
        if (email == null || !email.contains("@")) { // P3, P4
            return "Email tidak valid";
        }
        if (!setujuSyarat) { // P5
            return "Harus menyetujui syarat dan ketentuan";
        }
        return "Pendaftaran berhasil";
    }
}
