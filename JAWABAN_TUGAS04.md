# Tugas 04 — Implementasi Unit Testing dengan GitHub Actions

**Nama:** Diva Anastasya
**NIM:** 1224026
**Kelas:** Implementasi Pengujian P/L
**Repository:** https://github.com/diva-28-ukay/latihan-git-1224026
**Tanggal:** 12 Oktober 2026

> File kode ada di repo ini:
> - `src/main/java/com/latihan/git1224026/PendaftaranValidator.java` (Soal 3)
> - `src/main/java/com/latihan/git1224026/StudentGrade.java` (Soal 4)
> - `src/main/java/com/latihan/git1224026/BankAccount.java` (Soal 8/Bonus)
> - `src/test/java/com/latihan/git1224026/*Test.java`
> - `.github/workflows/java-unit-test.yml` (Soal 6 + Soal 8)

---

## BAGIAN A: GIT & GITHUB DASAR (25 Poin)

### Soal 1 (10 Poin)

#### 1. Centralized VCS vs Distributed VCS

**Centralized VCS (CVCS):**
- Hanya ada SATU server pusat yang menyimpan seluruh riwayat versi. Client hanya mengambil working copy (checkout) dan setiap commit langsung ke server.
- Kelemahan: butuh koneksi ke server untuk commit/history, single point of failure.
- Contoh: **Subversion (SVN)**, **CVS** (contoh lain: Perforce).

**Distributed VCS (DVCS):**
- Setiap clone menyimpan SALINAN PENUH repository + history. Commit bisa offline, lalu sinkronisasi via push/pull/merge.
- Kelebihan: kerja offline, branching murah, lebih tahan kegagalan server.
- Contoh: **Git**, **Mercurial** (contoh lain: Bazaar).

| Aspek | Centralized | Distributed |
|---|---|---|
| Penyimpanan history | Hanya di server | Di setiap clone |
| Commit offline | Tidak bisa | Bisa |
| Contoh | SVN, CVS | Git, Mercurial |

#### 2. 5 Git commands wajib + fungsi

1. `git clone <url>` — menyalin repository remote ke lokal (sekaligus download history).
2. `git add <file>` — memindahkan perubahan ke staging area (siap di-commit). `git add .` = semua file.
3. `git commit -m "pesan"` — menyimpan snapshot staging ke history lokal.
4. `git push origin <branch>` — mengirim commit lokal ke remote (GitHub).
5. `git pull` — mengambil + menggabungkan perubahan remote ke lokal (`fetch` + `merge`).
   - Bonus wajib: `git status`, `git branch`, `git checkout -b <branch>`, `git merge <branch>`.

#### 3. Fungsi `.gitignore`

File `.gitignore` berisi daftar pola file/folder yang **diabaikan Git** (tidak di-track, tidak ikut `add/commit/push`).

Fungsi: mencegah file sampah/privat masuk repo, misal `target/`, `*.class`, `.idea/`, `.env`, `node_modules/`, hasil coverage `*.exec`.
Contoh isi di project ini: `target/`, `*.class`, `.idea/`, `.vscode/`.

---

### Soal 2 (15 Poin) — Praktik Git Commands

Urutan command yang digunakan (NIM = 1224026):

```bash
# 1. Buat repository baru di GitHub dengan nama latihan-git-[NIM]
#    (dilakukan via web GitHub: New repository > latihan-git-1224026 > Create)
#    Alternatif via CLI: gh repo create latihan-git-1224026 --public

# 2. Clone repository ke lokal
git clone https://github.com/diva-28-ukay/latihan-git-1224026.git
cd latihan-git-1224026

# 3. Buat file README.md dan tuliskan nama serta NIM
echo "# latihan-git-1224026" > README.md
echo "Nama: Diva Anastasya" >> README.md
echo "NIM: 1224026" >> README.md

# 4. Commit file dengan pesan "docs: add README"
git add README.md
git commit -m "docs: add README"

# 5. Push ke GitHub (branch main)
git push origin main

# 6. Buat branch baru feature-test
git branch feature-test

# 7. Pindah ke branch tersebut
git checkout feature-test
# atau: git switch feature-test
# atau sekaligus: git checkout -b feature-test

# 8. Buat file test.txt dan commit "test: add test file"
echo "File test untuk latihan branch feature-test." > test.txt
git add test.txt
git commit -m "test: add test file"

# 9. Push branch feature-test ke GitHub
git push origin feature-test

# 10. Merge branch feature-test ke main
git checkout main
git merge feature-test
git push origin main
```

Verifikasi:

```bash
git log --oneline --graph --all
git branch -a
```

> Di repo ini langkah 1-10 sudah dipraktikkan. History lokal memuat commit `docs: add README` dan `test: add test file`, branch `feature-test` sudah di-merge ke `main`.

---

## BAGIAN B: UNIT TESTING FRAMEWORK (35 POIN)

### Soal 3 (15 Poin) — Validasi Pendaftaran

Kode: `src/main/java/com/latihan/git1224026/PendaftaranValidator.java`

#### 1. 5 Test Case berdasarkan Independent Path

| ID | Path | Input (nama, umur, email, setuju) | Expected Output |
|---|---|---|---|
| TC1 (P1) | Nama kosong | `("", 20, "budi@mail.com", true)` dan `(null, 20, "budi@mail.com", true)` | `"Nama harus diisi"` |
| TC2 (P2) | Umur < 17 | `("Budi", 16, "budi@mail.com", true)` | `"Umur minimal 17 tahun"` |
| TC3 (P3/P4) | Email tidak valid | `("Budi", 20, null, true)` dan `("Budi", 20, "budimail.com", true)` | `"Email tidak valid"` |
| TC4 (P5) | Tidak setuju syarat | `("Budi", 20, "budi@mail.com", false)` | `"Harus menyetujui syarat dan ketentuan"` |
| TC5 (success) | Semua valid | `("Budi", 20, "budi@mail.com", true)` | `"Pendaftaran berhasil"` |

Catatan: P3 (email null) dan P4 (email tanpa `@`) berada di kondisi `if` yang sama (`email == null || !email.contains("@")`), sehingga digabung jadi 1 test case grup (dipecah jadi 2 assert untuk coverage 100%).

#### 2. Implementasi JUnit (JUnit 5)

File: `src/test/java/com/latihan/git1224026/PendaftaranValidatorTest.java`

```java
package com.latihan.git1224026;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PendaftaranValidatorTest {
    private final PendaftaranValidator validator = new PendaftaranValidator();

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

    @Test
    void testUmurKurangDari17() {
        assertEquals("Umur minimal 17 tahun",
                validator.validasiPendaftaran("Budi", 16, "budi@mail.com", true));
    }

    @Test
    void testEmailNull() {
        assertEquals("Email tidak valid",
                validator.validasiPendaftaran("Budi", 20, null, true));
    }

    @Test
    void testEmailTanpaAt() {
        assertEquals("Email tidak valid",
                validator.validasiPendaftaran("Budi", 20, "budimail.com", true));
    }

    @Test
    void testTidakSetujuSyarat() {
        assertEquals("Harus menyetujui syarat dan ketentuan",
                validator.validasiPendaftaran("Budi", 20, "budi@mail.com", false));
    }

    @Test
    void testPendaftaranBerhasil() {
        assertEquals("Pendaftaran berhasil",
                validator.validasiPendaftaran("Budi", 20, "budi@mail.com", true));
    }
}
```

Cara jalan: `mvn test -Dtest=PendaftaranValidatorTest`

---

### Soal 4 (20 Poin) — StudentGrade

Kode: `src/main/java/com/latihan/git1224026/StudentGrade.java`

#### 1. Cyclomatic Complexity `calculateGrade()`

Rumus: `V(G) = jumlah predicate (decision) + 1`. Predikat `||` dihitung 2 karena short-circuit.

- `if (score < 0 || score > 100)` → 2 predicate (`<0`, `>100`)
- `if (score >= 80)` → 1
- `else if (score >= 70)` → 1
- `else if (score >= 60)` → 1
- `else if (score >= 50)` → 1

Total predicate = 6 → **V(G) = 6 + 1 = 7**.

Cara lain: jumlah `if/else-if` (5) + 1 kondisi `||` (1) + 1 = 7. Hasil sama.

#### 2. Independent Path (7 path)

- P1: `score < 0` → throw `IllegalArgumentException`
- P2: `score > 100` → throw `IllegalArgumentException`
- P3: `score >= 80` (mis. 85) → `"A"`
- P4: `70 <= score < 80` (mis. 75) → `"B"`
- P5: `60 <= score < 70` (mis. 65) → `"C"`
- P6: `50 <= score < 60` (mis. 55) → `"D"`
- P7: `0 <= score < 50` (mis. 40) → `"E"`

#### 3. Unit test JUnit untuk `calculateGrade` dan `isPassed`

File: `src/test/java/com/latihan/git1224026/StudentGradeTest.java`

```java
class StudentGradeTest {
    private final StudentGrade grade = new StudentGrade();

    @Test void testGradeA() {
        assertEquals("A", grade.calculateGrade(85));
        assertEquals("A", grade.calculateGrade(80));
        assertEquals("A", grade.calculateGrade(100));
    }
    @Test void testGradeB() {
        assertEquals("B", grade.calculateGrade(75));
        assertEquals("B", grade.calculateGrade(70));
    }
    @Test void testGradeC() { assertEquals("C", grade.calculateGrade(65)); }
    @Test void testGradeD() { assertEquals("D", grade.calculateGrade(55)); }
    @Test void testGradeE() { assertEquals("E", grade.calculateGrade(40)); }
    @Test void testGradeInvalidLow() {
        assertThrows(IllegalArgumentException.class, () -> grade.calculateGrade(-1));
    }
    @Test void testGradeInvalidHigh() {
        assertThrows(IllegalArgumentException.class, () -> grade.calculateGrade(101));
    }
    @Test void testIsPassedTrue() {
        assertTrue(grade.isPassed(60));
        assertTrue(grade.isPassed(80));
    }
    @Test void testIsPassedFalse() {
        assertFalse(grade.isPassed(59));
        assertFalse(grade.isPassed(0));
    }
    @Test void testIsPassedInvalid() {
        assertThrows(IllegalArgumentException.class, () -> grade.isPassed(-5));
        assertThrows(IllegalArgumentException.class, () -> grade.isPassed(150));
    }
}
```

(File lengkap di repo mencakup boundary 79, 60, 50, 0. Jalankan: `mvn test -Dtest=StudentGradeTest`)

---

## BAGIAN C: GITHUB ACTIONS (25 POIN)

### Soal 5 (15 Poin) — Analisis Workflow YAML

#### 1. Nama workflow
`Python CI` (dari `name: Python CI`).

#### 2. Trigger/event pemicu
- `push` ke branch `main` dan `develop`
- `pull_request` ke branch `main`
```yaml
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
```

#### 3. Fungsi `strategy.matrix`
Menjalankan **job yang sama secara paralel** untuk beberapa kombinasi versi, tanpa duplikasi YAML.
Di sini: `python-version: ['3.10', '3.11', '3.12']` → job `test` berjalan 3 kali (satu per versi Python) di `ubuntu-latest`, untuk memastikan kode kompatibel di semua versi. Variabel dipakai via `${{ matrix.python-version }}`.

#### 4. Steps di dalam job `test`
Di soal tertulis "5 steps", tetapi YAML yang diberikan hanya memuat **4 steps**:
1. `Checkout code` (`actions/checkout@v4`) — download kode repo ke runner.
2. `Setup Python ${{ matrix.python-version }}` (`actions/setup-python@v5`) — install Python sesuai matrix.
3. `Install dependencies` — `pip install --upgrade pip` + `pip install pytest pytest-cov`.
4. `Run tests` — `pytest --cov=src --cov-report=xml` (jalan test + coverage).

(Tidak ada step ke-5 di YAML tersebut; jika dihitung `runs-on`/`strategy` bukan step.)

---

### Soal 6 (10 Poin) — Workflow Java

File: `.github/workflows/java-unit-test.yml` (nama workflow `"Java Unit Test"`):

```yaml
name: Java Unit Test

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      - name: Run tests
        run: mvn test

      - name: Generate coverage report
        run: mvn jacoco:report

      - name: Upload coverage report
        uses: actions/upload-artifact@v4
        with:
          name: jacoco-report
          path: target/site/jacoco/
```

Penjelasan: trigger push main/develop + PR main, runner `ubuntu-latest`, 5 steps sesuai ketentuan (checkout, setup JDK 17, `mvn test`, `mvn jacoco:report`, upload artifact `jacoco-report`). Workflow ini sudah dipasang di repo sehingga pipeline berjalan otomatis tiap push.

---

## BAGIAN D: CODE COVERAGE & INTEGRASI (15 POIN)

### Soal 7 — Analisis Coverage

```
Name                  Stmts Miss Cover
src/calculator.py       10    0   100%
src/login_system.py     15    2    87%
src/payment.py          20    8    60%
src/utils.py             5    1    80%
TOTAL                   50   11    78%
```

#### 1. Total statement: **50** (kolom Stmts baris TOTAL).
#### 2. Total miss (tidak tereksekusi): **11**.
#### 3. Coverage paling rendah: **`src/payment.py`, 60%** (8 dari 20 statement tidak tersentuh).
#### 4. Cara meningkatkan coverage `payment.py`:
- Lihat laporan detail (`pytest --cov=src --cov-report=html`, buka `htmlcov/`) untuk tahu baris/miss dan branch yang belum kena.
- Tambah test case untuk path yang belum ter-cover: kasus gagal (payment declined, saldo kurang, invalid input, exception), boundary (nominal 0, limit), dan branch `if/else` yang belum tersentuh.
- Targetkan minimal 80%+: tambah integration test untuk alur payment end-to-end (mock gateway).
- Jalankan ulang `pytest --cov` sampai Miss turun dari 8 → 0-2.

#### 5. Apakah 78% sudah cukup baik?
**Belum ideal, tapi lumayan sebagai baseline.** Alasan:
- Standar industri umumnya **≥80%** (banyak tim mensyaratkan 80-90% untuk merge). 78% berarti masih ada 11 statement tak teruji, risiko bug tersembunyi — apalagi file kritis `payment.py` hanya 60%.
- Coverage tinggi ≠ bebas bug (tidak mengukur kualitas assert), tapi 78% dengan 1 file kritis rendah = belum aman untuk production. Perlu naik ke ≥85% dengan fokus ke `payment.py` dan `login_system.py`.
- Jika deadline mepet, 78% bisa diterima sementara asal modul kritis (payment) sudah diuji manual; jangka panjang harus ditingkatkan.

---

## BAGIAN E: STUDI KASUS TERPADU (BONUS 10 POIN)

### 1. Kode Program `BankAccount`

File: `src/main/java/com/latihan/git1224026/BankAccount.java` — lihat repo. Inti:

```java
public class BankAccount {
    private double balance;
    public BankAccount() { this.balance = 0.0; }
    public BankAccount(double initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("Initial balance cannot be negative");
        this.balance = initialBalance;
    }
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive");
        this.balance += amount;
    }
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw amount must be positive");
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance");
        this.balance -= amount;
    }
    public double getBalance() { return balance; }
}
```

### 2. Unit Test (9 test case, syarat minimal 6)

File: `src/test/java/com/latihan/git1224026/BankAccountTest.java`:
1. `testInitialBalanceZero` — saldo awal 0
2. `testInitialBalanceWithValue` — saldo awal 100
3. `testInitialBalanceNegativeThrows` — initial negatif → exception
4. `testDepositMenambahSaldo` — 100+50=150
5. `testDepositNegatifThrows` — deposit -10/0 → exception
6. `testWithdrawMengurangiSaldo` — 100-40=60
7. `testWithdrawMelebihiSaldoThrows` — withdraw 150 dari 100 → exception
8. `testWithdrawNegatifThrows`
9. `testGetBalanceSetelahTransaksi` — deposit 200, withdraw 75 → 125

Jalankan: `mvn test -Dtest=BankAccountTest`

### 3. GitHub Actions
Pakai file yang sama dengan Soal 6 (`.github/workflows/java-unit-test.yml`): `mvn test` + `mvn jacoco:report` + upload `jacoco-report`. Memenuhi ketentuan Soal 8 poin 3.

### 4. Repository & Pipeline
- Link: https://github.com/diva-28-ukay/latihan-git-1224026
- Setelah push, cek tab **Actions** → workflow **Java Unit Test** harus hijau (✓).
- Download artifact `jacoco-report`, buka `index.html` untuk screenshot coverage.
- Screenshot yang perlu dilampirkan di PDF: (1) Actions sukses, (2) coverage report.

Perintah push (jalankan di laptop yang sudah login GitHub):

```bash
cd latihan-git-1224026
git add .
git commit -m "feat: add unit tests and java workflow"
git push origin main
git push origin feature-test
```

---

## LAMPIRAN: Cara Menjalankan Lokal

```bash
# butuh JDK 17 + Maven
mvn test
mvn jacoco:report
# laporan: target/site/jacoco/index.html
```

## Link Repository

- https://github.com/diva-28-ukay/latihan-git-1224026

---
*Disusun untuk memenuhi Tugas Pertemuan 4 — Bagian A s.d. E.*
