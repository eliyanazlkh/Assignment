public class Encoded {
    private String inputText;
    private int charCount;
    private String resultText;
    private final String groupID = "G03/DE-G03";  // Your group ID

    // Default constructor
    public Encoded() {}

    public String getInputText() { return inputText; }
    public int getCharCount() { return charCount; }
    public String getResultText() { return resultText; }

    public int countCharacters(String inputText) {
        int count = 0;
        for (int i = 0; i < inputText.length(); i++) {
            if (inputText.charAt(i) != ' ') {
                count++;
            }
        }
        return count;
    }

    public boolean checkStringValidity(String inputText) {
        if (inputText == null || inputText.isEmpty()) {
            return false;
        }
        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);
            boolean isLowercase = (c >= 'a' && c <= 'z');
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isSpace = (c == ' ');
            if (!isLowercase && !isDigit && !isSpace) {
                return false;
            }
        }
        return true;
    }

    public int generateShift() {
        int hash = groupID.hashCode();
        return Math.abs(hash) % 10 + 1;  // Always 1-10, same for group
    }

    public String applyCipher(String inputText, int shift) {
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);
            if (c >= 'a' && c <= 'z') {
                char shifted = (char) ((c - 'a' + shift) % 26 + 'a');
                encoded.append(shifted);
            } else if (c >= '0' && c <= '9') {
                char shifted = (char) ((c - '0' + shift) % 10 + '0');
                encoded.append(shifted);
            } else {
                encoded.append(c);  // Space unchanged
            }
        }
        return encoded.toString();
    }

    public boolean encode(String input) {
        if (!checkStringValidity(input)) {
            return false;
        }
        this.inputText = input;
        this.charCount = countCharacters(input);
        int groupShift = generateShift();
        int finalShift = groupShift + charCount;
        this.resultText = applyCipher(input, finalShift);
        return true;
    }
}