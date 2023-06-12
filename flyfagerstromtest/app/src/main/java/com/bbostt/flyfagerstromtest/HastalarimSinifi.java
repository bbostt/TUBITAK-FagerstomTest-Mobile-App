package com.bbostt.flyfagerstromtest;

public class HastalarimSinifi {

    // ekliyeceğim verilerin yapısını oluşturduğum sınıf
    String hastaIsim;
    String gunlukIcilenSigara;
    int imgSrc;

    public HastalarimSinifi(String hastaIsim, String gunlukIcilenSigara, int imgSrc) {
        this.hastaIsim = hastaIsim;
        this.gunlukIcilenSigara = gunlukIcilenSigara;
        this.imgSrc = imgSrc;
    }

    public String getHastaIsim() {
        return hastaIsim;
    }

    public void setHastaIsim(String hastaIsim) {
        this.hastaIsim = hastaIsim;
    }

    public String getGunlukOrtTuketim() {
        return gunlukIcilenSigara;
    }

    public void setGunlukIcilenSigara(String gunlukIcilenSigara) {
        this.gunlukIcilenSigara = gunlukIcilenSigara;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }
}
