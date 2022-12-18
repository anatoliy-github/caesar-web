package logic;

public final class Crypto {
    private Crypto() {}
    private static final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','.',',','”','”',':','-','!','?',' '};

    public static String encryptByKey(String str, int key) {
        return process(str, key);
    }

    public static String decryptByKey(String str, int key) {
       return process(str, -key);
    }

    private static String process(String str, int key) {
        StringBuilder result = new StringBuilder();
        int lengthArray = getLengthOfAlphabet();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            ch = Character.toLowerCase(ch);
            for (int j = 0; j < lengthArray; j++) {
                if(ch == alphabet[j]) {
                    result.append(alphabet[(lengthArray + j + key) % lengthArray]);
                }
            }
        }
        return result.toString();
    }

    public static int getLengthOfAlphabet() {
        return alphabet.length;
    }
}
