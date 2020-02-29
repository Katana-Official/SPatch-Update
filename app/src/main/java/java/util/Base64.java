package java.util;

public class Base64
{
    public static class Encoder
    {
        public byte[] encode(byte[] ss)
        {
            return android.util.Base64.encode(ss,android.util.Base64.DEFAULT);
        }
        public String encodeToString(byte[] ss)
        {
            return android.util.Base64.encodeToString(ss,android.util.Base64.DEFAULT);
        }

    }
    public static class Decoder
    {
        public byte[] decode(byte[] ss)
        {
            return android.util.Base64.decode(ss,android.util.Base64.DEFAULT);
        }
        public String decodeToString(byte[] ss)
        {
            return Arrays.toString(android.util.Base64.decode(ss, android.util.Base64.DEFAULT));
        }
    }
    static public Encoder getEncoder()
    {
        return new Encoder();
    }
    static public Decoder getDecoder()
    {
        return new Decoder();
    }

}
