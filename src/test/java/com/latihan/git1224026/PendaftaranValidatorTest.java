package com.latihan.git1224026;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Soal 3 - Unit test untuk PendaftaranValidator.
 * 5 Independent Path (P1-P5) + 1 success path.
 */
class PendaftaranValidatorTest {

    private final PendaftaranValidator validator = new PendaftaranValidator();

    // P1: nama kosong -> "Nama harus diisi"
    @Test
    void testNamaKosong() {
        assertEquals("Nama harus diisi",
                validator.validasiPendaftaran("", 20, "budi@mail.com", true));
    }

    @Test
    void testNamaNull() {
        assertEquals("Nama harus diisi",
                validator.validasiPendaftaran(null, 20, "budi@mail.com", true));
    }

    // P2: umur < 17 -> "Umur minimal 17 tahun"
    @Test
    void testUmurKurangDari17() {
        assertEquals("Umur minimal 17 tahun",
                validator.validasiPendaftaran("Budi", 16, "budi@mail.com", true));
    }

    // P3: email null -> "Email tidak valid"
    @Test
    void testEmailNull() {
        assertEquals("Email tidak valid",
                validator.validasiPendaftaran("Budi", 20, null, true));
    }

    // P4: email tanpa @ -> "Email tidak valid"
    @Test
    void testEmailTanpaAt() {
        assertEquals("Email tidak valid",
                validator.validasiPendaftaran("Budi", 20, "budimail.com", true));
    }

    // P5: tidak setuju syarat -> "Harus menyetujui syarat dan ketentuan"
    @Test
    void testTidakSetujuSyarat() {
        assertEquals("Harus menyetujui syarat dan ketentuan",
                validator.validasiPendaftaran("Budi", 20, "budi@mail.com", false));
    }

    // Path sukses: semua valid -> "Pendaftaran berhasil"
    @Test
    void testPendaftaranBerhasil() {
        assertEquals("Pendaftaran berhasil",
                validator.validasiPendaftaran("Budi", 20, "budi@mail.com", true));
    }
}
