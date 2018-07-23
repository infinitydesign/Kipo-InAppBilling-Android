package ir.kipo.billing.tools;

import android.util.Base64;
import android.webkit.URLUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by 1HE on 10/22/2017.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
public class KipoStringHelper {

    public static String createStringFromArray(String[] arr) {
        try {
            if (arr.length == 0)
                return "{}";

            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
                if (i != arr.length - 1)
                    sb.append(", ");
            }
            sb.append(" }");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static String capitalizeModelPhone(String s) {
        if (isEmpty(s))
            return "";
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static String generateUniqueId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String normalizeURL(String html) {
        if (isEmpty(html))
            return "";

        String[] pieces = html.split(" ");
        ArrayList<String> textParts = new ArrayList<>();

        for (String piece : pieces) {

            try {
                URL isURL = new URL(piece);
                String protocol = isURL.getProtocol();
                String host = isURL.getHost();
                String query = isURL.getQuery();
                String path = isURL.getPath();
                String questionMark = "?";

                if (path.equals("")) {
                    path = "/";
                }

                if (query == null) {
                    query = "";
                    questionMark = "";
                }

                String url = protocol + "://" + host + path + questionMark + query;
                textParts.add(url);
            } catch (MalformedURLException exception) {
                textParts.add(piece);
            }
        }

        StringBuilder resultString = new StringBuilder();
        for (String s : textParts) {
            resultString.append(s).append(" ");
        }

        return resultString.toString();
    }

    private static String getFirstMode(String in) {
        if (isEmpty(in))
            return in;

        char[] ch = in.toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        return String.valueOf(ch);
    }

    private static String getAllMode(String in) {
        if (isEmpty(in))
            return in;

        char[] ch = in.toCharArray();
        for (int i = 0; i < ch.length; i++)
            ch[i] = Character.toUpperCase(ch[i]);
        return String.valueOf(ch);
    }

    private static String getFirstSpaceMode(String in) {
        if (isEmpty(in))
            return in;

        char[] ch = in.toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        for (int i = 1; i < ch.length; i++) {
            if (i != ch.length - 1) {
                if (ch[i - 1] == ' ')
                    ch[i] = Character.toUpperCase(ch[i]);
            }
        }
        return String.valueOf(ch);
    }

    public static String getStarredText(String in) {
        if (isEmpty(in))
            return in;

        return in + "*";
    }

    public static String getDoubledText(String in) {
        if (isEmpty(in))
            return in;

        return in + ":";
    }

    public static boolean isEmpty(String s) {
        if (s == null)
            return true;

        s = s.replaceAll("\\s+", "");

        return s.equals("") || s.length() == 0;
    }

    public static boolean isEmptyCharSequenceWithNull(CharSequence ch) {
        if (ch == null)
            return true;

        String s = ch.toString();
        s = s.replaceAll("\\s+", "");

        return s.equalsIgnoreCase("null") || s.equals("") || s.length() == 0;
    }

    public static boolean isEmptyWithNull(String s) {
        if (s == null)
            return true;

        s = s.replaceAll("\\s+", "");

        return s.equalsIgnoreCase("null") || s.equals("") || s.length() == 0;
    }

    @SuppressWarnings("WeakerAccess")
    public static boolean isValidUrl(String s) {
        try {

            return URLUtil.isValidUrl(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidMobile(String s) {
        try {
            //noinspection SimplifiableIfStatement
            if (isEmpty(s))
                return false;
            s = toEnglishNumber(s);
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return s.length() == 11 && s.startsWith("09");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidPassword(String s) {
        try {
            return !isEmpty(s) && s.length() >= 6;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidWebsite(String website) {
        try {
            Pattern regex = Pattern.compile("\\b(?:(https?|ftp|file)://|www\\.)?[-A-Z0-9+&#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$]\\.[-A-Z0-9+&@#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$]", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher regexMatcher = regex.matcher(website);
            return regexMatcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static String formatCurrency(double d) {
        return formatCurrencyWithNum(d, 1, null);
    }

    public static String formatCurrency(String s) {
        try {
            if (isEmpty(s))
                return "0";
            return formatCurrency(convertDoubleCurrency(s));
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static String formatCurrencyEmpty(String s, int decSize) {
        try {
            if (isEmpty(s))
                return "";
            return formatCurrencyWithNum(convertDoubleCurrency(s), decSize, s);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatCurrencyWithNum(String s, int dec) {
        try {
            if (isEmpty(s))
                return "0";
            return formatCurrencyWithNum(Double.valueOf(s), dec, null);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    private static String formatCurrencyWithNum(double d, int dec, String original) {
        try {
            StringBuilder sb = new StringBuilder("#.");
            for (int i = 0; i < dec; i++) {
                sb.append("#");
            }
            NumberFormat nf = new DecimalFormat(sb.toString());
            String s = nf.format(d);
            boolean forceDotCheck = original != null && original.charAt(original.length() - 1) == '.';
            if (forceDotCheck) {
                s = original.replaceAll("[,]", "");
            }
            s = s.replaceAll("\\s+", "");
            s = s.replaceAll("[+-]", "");
            StringBuilder out = new StringBuilder();
            String[] arr = null;
            if (s.contains(".")) {
                arr = s.split("[.]");
                s = arr[0];
            }
            char[] ch = s.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                if ((ch.length - i) % 3 == 0 && i != 0) {
                    out.append(",");
                }
                out.append(ch[i]);
            }
            if (!forceDotCheck) {
                if (arr != null && arr.length == 2) {
                    out.append(".").append(arr[1]);
                }
            } else {
                if (original.contains(".") && arr != null && arr.length == 1)
                    out.append(".");
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static Double convertDoubleCurrency(String in) {
        try {
            if (isEmpty(in))
                return 0d;
            String cleanString = in.replaceAll("[+-,]", "");
            return Double.valueOf(cleanString);
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    public static Double convertDoublePercent(String in) {
        try {
            if (isEmpty(in))
                return 0d;
            String cleanString = in.replaceAll("[%]", "");
            return Double.valueOf(cleanString);
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    public static String trim(String in) {
        if (in == null)
            return "";

        return in.trim();
    }

    private static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encrypted, 0));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String toPersianNumber(String text) {
        String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        if (isEmpty(text))
            return "";
        StringBuilder out = new StringBuilder();
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out.append(persianNumbers[number]);
            } else if (c == '٫') {
                out.append('،');
            } else {
                out.append(c);
            }

        }
        return out.toString();
    }

    public static String toEnglishNumber(String text) {
        String[] englishNumbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        if (isEmpty(text))
            return "";
        StringBuilder out = new StringBuilder();
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (c >= 1776 && c <= 1785) {
                int number = Integer.parseInt(String.valueOf(c));
                out.append(englishNumbers[number]);
            } else if (c == '،') {
                out.append('٫');
            } else {
                out.append(c);
            }

        }
        return out.toString();
    }

    public static double toDoubleEnglish(String input) {
        try {
            input = KipoStringHelper.toEnglishNumber(input);
            return KipoStringHelper.convertDoubleCurrency(input);
        } catch (Exception e) {
            return 0;
        }
    }
}
