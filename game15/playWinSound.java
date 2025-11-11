private void playWinSound() {
    try {
        java.io.File file = new java.io.File("src/game15/win.wav");
        javax.sound.sampled.AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(file);
        javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    } catch (Exception e) {
        System.out.println("Kunde inte spela upp ljudet: " + e.getMessage());
    }
}

void main() {
}

