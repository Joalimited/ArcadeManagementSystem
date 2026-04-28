package arcade;

/**
 * EncryptionUtil.java
 * Uses simple reverse and Caesar-style shifting for passwords.
 */
public class EncryptionUtil
{
    public static String encrypt(String password)
    {
        StringBuilder result = new StringBuilder();

        for(int i = password.length() - 1; i >= 0; i--)
        {
            char shifted = (char)(password.charAt(i) + 3);
            result.append(shifted);
        }

        return result.toString();
    }
}
