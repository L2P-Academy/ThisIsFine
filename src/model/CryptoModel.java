package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CryptoModel {

    public static final int WORD_LENGTH = 7;
    public static final int ROWS_PER_COLUMN = 17;
    public static final int CHARS_PER_ROW = 17;
    public static final int MAX_ATTEMPTS = 5;
    private static final int NUMBER_OF_WORDS = 12;
    private static final int ADDRESS_START = 0xF680;

    private static final List<String> CRYPTO_CHARS = new ArrayList<>();
    private static final List<String> CRYPTO_WORDS = new ArrayList<>();

    static {
        Collections.addAll(CRYPTO_CHARS,
            "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/",
            ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"
        );

        Collections.addAll(CRYPTO_WORDS,
            "HACKING", "NUCLEAR", "MUTANTS", "RAIDERS", "ENCLAVE", "OUTPOST",
            "WARHEAD", "FISSION", "REACTOR", "RADIANT", "PLASMAS", "FUSIONS", "STALKER", "SURVIVE",
            "WASTERS", "EXPLODE", "CAPSULE", "MEDICAL", "HEALING", "LOOTING",
            "STORAGE", "NETWORK", "SYSTEMS", "SERVERS", "CONSOLE", "MONITOR",
            "PROGRAM", "PROCESS", "KERNELS", "BACKUPS", "ROUTERS", "CIRCUIT",
            "CONTROL", "COMMAND", "ENCRYPT", "DECRYPT", "OFFLINE", "POWERUP",
            "RESTORE", "RESTART", "WARNING", "UNKNOWN", "SECRETS", "DEFENSE",
            "PROTECT", "FAILURE", "MACHINE", "FACTORY", "STATION", "PROJECT",
            "MANAGER", "UPGRADE", "DISABLE", "RECLAIM", "DELETED", "SECURED",
            "TARGETS", "CAPTURE", "MYSTERY", "DRIVING", "STORING"
        );
    }

    private final Random random = new Random();
    private final List<TerminalLine> leftLines = new ArrayList<>();
    private final List<TerminalLine> rightLines = new ArrayList<>();
    private final Set<String> activeWords = new HashSet<>();
    private final Set<String> triedWords = new HashSet<>();

    private String password;
    private int attemptsLeft;
    private boolean gameOver;
    private boolean solved;

    public CryptoModel() {
        newGame();
    }

    public void newGame() {
        attemptsLeft = MAX_ATTEMPTS;
        gameOver = false;
        solved = false;
        triedWords.clear();
        activeWords.clear();
        leftLines.clear();
        rightLines.clear();

        List<String> words = new ArrayList<>(CRYPTO_WORDS);
        words.removeIf(word -> !word.matches("[A-Z]{7}"));
        Collections.shuffle(words, random);

        int amount = Math.min(NUMBER_OF_WORDS, words.size());
        if (amount == 0) {
            throw new IllegalStateException("Keine gueltigen 7-Buchstaben-Woerter vorhanden.");
        }

        password = words.get(random.nextInt(amount));

        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < ROWS_PER_COLUMN * 2; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions, random);

        Map<Integer, String> wordsByRow = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            String word = words.get(i);
            wordsByRow.put(positions.get(i), word);
            activeWords.add(word);
        }

        for (int i = 0; i < ROWS_PER_COLUMN * 2; i++) {
            StringBuilder symbols = new StringBuilder();
            for (int j = 0; j < CHARS_PER_ROW; j++) {
                symbols.append(CRYPTO_CHARS.get(random.nextInt(CRYPTO_CHARS.size())));
            }

            String word = wordsByRow.get(i);
            int wordStart = -1;
            if (word != null) {
                int insertAt = random.nextInt(CHARS_PER_ROW - WORD_LENGTH + 1);
                symbols.replace(insertAt, insertAt + WORD_LENGTH, word);
                wordStart = 8 + insertAt; // "0xF680  " ist acht Zeichen lang
            }

            String address = String.format("0x%04X  ", ADDRESS_START + i * 16);
            TerminalLine line = new TerminalLine(address + symbols, word, wordStart);
            if (i < ROWS_PER_COLUMN) {
                leftLines.add(line);
            } else {
                rightLines.add(line);
            }
        }
    }

    public String submitGuess(String guess) {
        if (gameOver) {
            return "TERMINAL GESPERRT / SPIEL BEENDET";
        }
        if (!activeWords.contains(guess)) {
            return "UNGUELTIGE EINGABE";
        }
        if (!triedWords.add(guess)) {
            return "WORT BEREITS VERSUCHT: " + guess;
        }
        if (password.equals(guess)) {
            solved = true;
            gameOver = true;
            return "ACCESS GRANTED - PASSWORT: " + password;
        }

        int matches = 0;
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == password.charAt(i)) {
                matches++;
            }
        }
        attemptsLeft--;

        if (attemptsLeft == 0) {
            gameOver = true;
            return "TERMINAL LOCKED - PASSWORT WAR: " + password;
        }
        return "ACCESS DENIED: " + guess + " | LIKENESS " + matches + "/" + WORD_LENGTH;
    }

    public List<TerminalLine> getLeftLines() {
        return Collections.unmodifiableList(leftLines);
    }

    public List<TerminalLine> getRightLines() {
        return Collections.unmodifiableList(rightLines);
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isSolved() {
        return solved;
    }

    public static class TerminalLine {
        private final String text;
        private final String word;
        private final int wordStart;

        public TerminalLine(String text, String word, int wordStart) {
            this.text = text;
            this.word = word;
            this.wordStart = wordStart;
        }

        public String getText() {
            return text;
        }

        public String getWord() {
            return word;
        }

        public int getWordStart() {
            return wordStart;
        }
    }
}
