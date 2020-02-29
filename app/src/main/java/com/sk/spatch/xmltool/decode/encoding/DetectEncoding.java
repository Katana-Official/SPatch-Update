package com.sk.spatch.xmltool.decode.encoding;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class DetectEncoding {
  private static final int ASCII = 4;
  
  private static final int BIG5 = 5;
  
  private static final int[][] Big5Freq;
  
  public static final Charset CHARSET_BIG5;
  
  public static final Charset CHARSET_EUC_TW;
  
  public static final Charset CHARSET_GB2312;
  
  public static final Charset CHARSET_GBK;
  
  public static final Charset CHARSET_UNICODE;
  
  public static final Charset CHARSET_UTF8;
  
  private static final int EUC_TW = 6;
  
  private static final int[][] EUC_TWFreq;
  
  private static final int GB2312 = 1;
  
  private static final int[][] GBFreq;
  
  private static final int GBK = 2;
  
  private static final int[][] GBKFreq;
  
  private static final int OTHER = 7;
  
  private static final int TOTAL_ENCODINGS = 8;
  
  private static final int UNICODE = 3;
  
  private static final int UTF8 = 0;
  
  private static final Charset[] encodeCharset;
  
  int ascii_score = 70;
  
  int asciibytes = 0;
  
  int big5_chars = 1;
  
  int big5_dbchars = 1;
  
  long big5_freq = 0L;
  
  long big5_totalfreq = 1L;
  
  int encoding_guess = 7;
  
  int gb2312_chars = 1;
  
  int gb2312_dbchars = 1;
  
  long gb2312_gbfreq = 0L;
  
  long gb2312_totalfreq = 1L;
  
  int gbk_chars = 1;
  
  int gbk_dbchars = 1;
  
  long gbk_freq = 0L;
  
  long gbk_totalfreq = 1L;
  
  int goodbytes = 0;
  
  boolean impossibleUTF16 = false;
  
  int tw_chars = 1;
  
  int tw_dbchars = 1;
  
  long tw_freq = 0L;
  
  long tw_totalfreq = 1L;
  
  int utf8Length = 0;
  
  static  {
    Charset charset1;
    GBFreq = (int[][])Array.newInstance(int.class, new int[] { 94, 94 });
    GBKFreq = (int[][])Array.newInstance(int.class, new int[] { 126, 191 });
    Big5Freq = (int[][])Array.newInstance(int.class, new int[] { 94, 158 });
    EUC_TWFreq = (int[][])Array.newInstance(int.class, new int[] { 94, 94 });
    CHARSET_UTF8 = Charset.forName("UTF-8");
    CHARSET_GB2312 = Charset.forName("GB2312");
    CHARSET_GBK = Charset.forName("GBK");
    CHARSET_UNICODE = Charset.forName("Unicode");
    CHARSET_BIG5 = Charset.forName("Big5");
    try {
      charset1 = Charset.forName("EUC-TW");
    } catch (UnsupportedCharsetException unsupportedCharsetException) {
      charset1 = CHARSET_UTF8;
    } 
    CHARSET_EUC_TW = charset1;
    Charset[] arrayOfCharset = new Charset[8];
    encodeCharset = arrayOfCharset;
    Charset charset2 = CHARSET_UTF8;
    arrayOfCharset[0] = charset2;
    arrayOfCharset[1] = CHARSET_GB2312;
    Charset charset3 = CHARSET_GBK;
    arrayOfCharset[2] = charset3;
    arrayOfCharset[3] = CHARSET_UNICODE;
    arrayOfCharset[4] = charset3;
    arrayOfCharset[5] = CHARSET_BIG5;
    arrayOfCharset[6] = charset1;
    arrayOfCharset[7] = charset2;
    initialize_frequencies();
  }
  
  private int ascii_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2) {
      byte b = paramArrayOfbyte[paramInt1];
      int i = this.ascii_score;
      if (i <= 0)
        return 0; 
      if (b < 0) {
        this.ascii_score = i - 5;
      } else if (b == 27) {
        this.ascii_score = i - 5;
      } 
      paramInt1++;
    } 
    return this.ascii_score;
  }
  
  private int big5_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i;
    for (i = paramInt1; i < paramInt2 + paramInt1 - 1; i = j + 1) {
      int j = i;
      if (paramArrayOfbyte[i] < 0) {
        this.big5_dbchars++;
        if (-95 <= paramArrayOfbyte[i] && paramArrayOfbyte[i] <= -7) {
          j = i + 1;
          if ((64 <= paramArrayOfbyte[j] && paramArrayOfbyte[j] <= 126) || (-95 <= paramArrayOfbyte[j] && paramArrayOfbyte[j] <= -2)) {
            this.big5_chars++;
            this.big5_totalfreq += 500L;
            int k = paramArrayOfbyte[i] + 256 - 161;
            if (64 <= paramArrayOfbyte[j] && paramArrayOfbyte[j] <= 126) {
              j = paramArrayOfbyte[j] - 64;
            } else {
              j = paramArrayOfbyte[j] + 256 - 97;
            } 
            int[][] arrayOfInt = Big5Freq;
            if (arrayOfInt[k][j] != 0) {
              this.big5_freq += arrayOfInt[k][j];
            } else if (3 <= k && k <= 37) {
              this.big5_freq += 200L;
            } 
          } 
        } 
        j = i + 1;
      } 
    } 
    return (int)(this.big5_chars / this.big5_dbchars * 50.0F + (float)this.big5_freq / (float)this.big5_totalfreq * 50.0F);
  }
  
  public static String encodeString(byte[] paramArrayOfbyte, boolean paramBoolean) {
    DetectEncoding detectEncoding = new DetectEncoding();
    detectEncoding.update(paramArrayOfbyte);
    return (paramBoolean && detectEncoding.encoding_guess == 7) ? null : new String(paramArrayOfbyte, detectEncoding.getEncode());
  }
  
  private int euc_tw_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { // Byte code:
    //   0: iload_3
    //   1: iload_2
    //   2: iadd
    //   3: istore #5
    //   5: iload_2
    //   6: iload #5
    //   8: iconst_1
    //   9: isub
    //   10: if_icmpge -> 298
    //   13: iload_2
    //   14: istore_3
    //   15: aload_1
    //   16: iload_2
    //   17: baload
    //   18: ifge -> 291
    //   21: aload_0
    //   22: aload_0
    //   23: getfield tw_dbchars : I
    //   26: iconst_1
    //   27: iadd
    //   28: putfield tw_dbchars : I
    //   31: iload_2
    //   32: iconst_3
    //   33: iadd
    //   34: istore_3
    //   35: iload_3
    //   36: iload #5
    //   38: if_icmpge -> 126
    //   41: bipush #-114
    //   43: aload_1
    //   44: iload_2
    //   45: baload
    //   46: if_icmpne -> 126
    //   49: iload_2
    //   50: iconst_1
    //   51: iadd
    //   52: istore #4
    //   54: bipush #-95
    //   56: aload_1
    //   57: iload #4
    //   59: baload
    //   60: if_icmpgt -> 126
    //   63: aload_1
    //   64: iload #4
    //   66: baload
    //   67: bipush #-80
    //   69: if_icmpgt -> 126
    //   72: iload_2
    //   73: iconst_2
    //   74: iadd
    //   75: istore #4
    //   77: bipush #-95
    //   79: aload_1
    //   80: iload #4
    //   82: baload
    //   83: if_icmpgt -> 126
    //   86: aload_1
    //   87: iload #4
    //   89: baload
    //   90: bipush #-2
    //   92: if_icmpgt -> 126
    //   95: bipush #-95
    //   97: aload_1
    //   98: iload_3
    //   99: baload
    //   100: if_icmpgt -> 126
    //   103: aload_1
    //   104: iload_3
    //   105: baload
    //   106: bipush #-2
    //   108: if_icmpgt -> 126
    //   111: aload_0
    //   112: aload_0
    //   113: getfield tw_chars : I
    //   116: iconst_1
    //   117: iadd
    //   118: putfield tw_chars : I
    //   121: iload_3
    //   122: istore_2
    //   123: goto -> 289
    //   126: iload_2
    //   127: istore_3
    //   128: bipush #-95
    //   130: aload_1
    //   131: iload_2
    //   132: baload
    //   133: if_icmpgt -> 291
    //   136: iload_2
    //   137: istore_3
    //   138: aload_1
    //   139: iload_2
    //   140: baload
    //   141: bipush #-2
    //   143: if_icmpgt -> 291
    //   146: iload_2
    //   147: iconst_1
    //   148: iadd
    //   149: istore #4
    //   151: iload_2
    //   152: istore_3
    //   153: bipush #-95
    //   155: aload_1
    //   156: iload #4
    //   158: baload
    //   159: if_icmpgt -> 291
    //   162: iload_2
    //   163: istore_3
    //   164: aload_1
    //   165: iload #4
    //   167: baload
    //   168: bipush #-2
    //   170: if_icmpgt -> 291
    //   173: aload_0
    //   174: aload_0
    //   175: getfield tw_chars : I
    //   178: iconst_1
    //   179: iadd
    //   180: putfield tw_chars : I
    //   183: aload_0
    //   184: aload_0
    //   185: getfield tw_totalfreq : J
    //   188: ldc2_w 500
    //   191: ladd
    //   192: putfield tw_totalfreq : J
    //   195: aload_1
    //   196: iload_2
    //   197: baload
    //   198: sipush #256
    //   201: iadd
    //   202: sipush #161
    //   205: isub
    //   206: istore_3
    //   207: aload_1
    //   208: iload #4
    //   210: baload
    //   211: sipush #256
    //   214: iadd
    //   215: sipush #161
    //   218: isub
    //   219: istore_2
    //   220: getstatic com/sk/spatch/xmltool/decode/encoding/DetectEncoding.EUC_TWFreq : [[I
    //   223: astore #6
    //   225: aload #6
    //   227: iload_3
    //   228: aaload
    //   229: iload_2
    //   230: iaload
    //   231: ifeq -> 256
    //   234: aload_0
    //   235: aload_0
    //   236: getfield tw_freq : J
    //   239: aload #6
    //   241: iload_3
    //   242: aaload
    //   243: iload_2
    //   244: iaload
    //   245: i2l
    //   246: ladd
    //   247: putfield tw_freq : J
    //   250: iload #4
    //   252: istore_2
    //   253: goto -> 289
    //   256: iload #4
    //   258: istore_2
    //   259: bipush #35
    //   261: iload_3
    //   262: if_icmpgt -> 289
    //   265: iload #4
    //   267: istore_2
    //   268: iload_3
    //   269: bipush #92
    //   271: if_icmpgt -> 289
    //   274: aload_0
    //   275: aload_0
    //   276: getfield tw_freq : J
    //   279: ldc2_w 150
    //   282: ladd
    //   283: putfield tw_freq : J
    //   286: iload #4
    //   288: istore_2
    //   289: iload_2
    //   290: istore_3
    //   291: iload_3
    //   292: iconst_1
    //   293: iadd
    //   294: istore_2
    //   295: goto -> 5
    //   298: aload_0
    //   299: getfield tw_chars : I
    //   302: i2f
    //   303: aload_0
    //   304: getfield tw_dbchars : I
    //   307: i2f
    //   308: fdiv
    //   309: ldc 50.0
    //   311: fmul
    //   312: aload_0
    //   313: getfield tw_freq : J
    //   316: l2f
    //   317: aload_0
    //   318: getfield tw_totalfreq : J
    //   321: l2f
    //   322: fdiv
    //   323: ldc 50.0
    //   325: fmul
    //   326: fadd
    //   327: f2i
    //   328: ireturn }
  
  private int gb2312_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i;
    for (i = paramInt1; i < paramInt2 + paramInt1 - 1; i = j + 1) {
      int j = i;
      if (paramArrayOfbyte[i] < 0) {
        this.gb2312_dbchars++;
        if (-95 <= paramArrayOfbyte[i] && paramArrayOfbyte[i] <= -9) {
          int k = i + 1;
          if (-95 <= paramArrayOfbyte[k] && paramArrayOfbyte[k] <= -2) {
            this.gb2312_chars++;
            this.gb2312_totalfreq += 500L;
            j = paramArrayOfbyte[i] + 256 - 161;
            k = paramArrayOfbyte[k] + 256 - 161;
            int[][] arrayOfInt = GBFreq;
            if (arrayOfInt[j][k] != 0) {
              this.gb2312_gbfreq += arrayOfInt[j][k];
            } else if (15 <= j && j < 55) {
              this.gb2312_gbfreq += 200L;
            } 
          } 
        } 
        j = i + 1;
      } 
    } 
    return (int)(this.gb2312_chars / this.gb2312_dbchars * 50.0F + (float)this.gb2312_gbfreq / (float)this.gb2312_totalfreq * 50.0F);
  }
  
  private int gbk_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { // Byte code:
    //   0: iload_2
    //   1: istore #4
    //   3: iload #4
    //   5: iload_3
    //   6: iload_2
    //   7: iadd
    //   8: iconst_1
    //   9: isub
    //   10: if_icmpge -> 381
    //   13: iload #4
    //   15: istore #5
    //   17: aload_1
    //   18: iload #4
    //   20: baload
    //   21: ifge -> 372
    //   24: aload_0
    //   25: aload_0
    //   26: getfield gbk_dbchars : I
    //   29: iconst_1
    //   30: iadd
    //   31: putfield gbk_dbchars : I
    //   34: bipush #-95
    //   36: aload_1
    //   37: iload #4
    //   39: baload
    //   40: if_icmpgt -> 192
    //   43: aload_1
    //   44: iload #4
    //   46: baload
    //   47: bipush #-9
    //   49: if_icmpgt -> 192
    //   52: iload #4
    //   54: iconst_1
    //   55: iadd
    //   56: istore #6
    //   58: bipush #-95
    //   60: aload_1
    //   61: iload #6
    //   63: baload
    //   64: if_icmpgt -> 192
    //   67: aload_1
    //   68: iload #6
    //   70: baload
    //   71: bipush #-2
    //   73: if_icmpgt -> 192
    //   76: aload_0
    //   77: aload_0
    //   78: getfield gbk_chars : I
    //   81: iconst_1
    //   82: iadd
    //   83: putfield gbk_chars : I
    //   86: aload_0
    //   87: aload_0
    //   88: getfield gbk_totalfreq : J
    //   91: ldc2_w 500
    //   94: ladd
    //   95: putfield gbk_totalfreq : J
    //   98: aload_1
    //   99: iload #4
    //   101: baload
    //   102: sipush #256
    //   105: iadd
    //   106: sipush #161
    //   109: isub
    //   110: istore #5
    //   112: aload_1
    //   113: iload #6
    //   115: baload
    //   116: sipush #256
    //   119: iadd
    //   120: sipush #161
    //   123: isub
    //   124: istore #6
    //   126: getstatic com/sk/spatch/xmltool/decode/encoding/DetectEncoding.GBFreq : [[I
    //   129: astore #7
    //   131: aload #7
    //   133: iload #5
    //   135: aaload
    //   136: iload #6
    //   138: iaload
    //   139: ifeq -> 163
    //   142: aload_0
    //   143: aload_0
    //   144: getfield gbk_freq : J
    //   147: aload #7
    //   149: iload #5
    //   151: aaload
    //   152: iload #6
    //   154: iaload
    //   155: i2l
    //   156: ladd
    //   157: putfield gbk_freq : J
    //   160: goto -> 366
    //   163: bipush #15
    //   165: iload #5
    //   167: if_icmpgt -> 366
    //   170: iload #5
    //   172: bipush #55
    //   174: if_icmpge -> 366
    //   177: aload_0
    //   178: aload_0
    //   179: getfield gbk_freq : J
    //   182: ldc2_w 200
    //   185: ladd
    //   186: putfield gbk_freq : J
    //   189: goto -> 366
    //   192: bipush #-127
    //   194: aload_1
    //   195: iload #4
    //   197: baload
    //   198: if_icmpgt -> 366
    //   201: aload_1
    //   202: iload #4
    //   204: baload
    //   205: bipush #-2
    //   207: if_icmpgt -> 366
    //   210: iload #4
    //   212: iconst_1
    //   213: iadd
    //   214: istore #5
    //   216: bipush #-128
    //   218: aload_1
    //   219: iload #5
    //   221: baload
    //   222: if_icmpgt -> 234
    //   225: aload_1
    //   226: iload #5
    //   228: baload
    //   229: bipush #-2
    //   231: if_icmple -> 252
    //   234: bipush #64
    //   236: aload_1
    //   237: iload #5
    //   239: baload
    //   240: if_icmpgt -> 366
    //   243: aload_1
    //   244: iload #5
    //   246: baload
    //   247: bipush #126
    //   249: if_icmpgt -> 366
    //   252: aload_0
    //   253: aload_0
    //   254: getfield gbk_chars : I
    //   257: iconst_1
    //   258: iadd
    //   259: putfield gbk_chars : I
    //   262: aload_0
    //   263: aload_0
    //   264: getfield gbk_totalfreq : J
    //   267: ldc2_w 500
    //   270: ladd
    //   271: putfield gbk_totalfreq : J
    //   274: aload_1
    //   275: iload #4
    //   277: baload
    //   278: sipush #256
    //   281: iadd
    //   282: sipush #129
    //   285: isub
    //   286: istore #6
    //   288: bipush #64
    //   290: aload_1
    //   291: iload #5
    //   293: baload
    //   294: if_icmpgt -> 318
    //   297: aload_1
    //   298: iload #5
    //   300: baload
    //   301: bipush #126
    //   303: if_icmpgt -> 318
    //   306: aload_1
    //   307: iload #5
    //   309: baload
    //   310: bipush #64
    //   312: isub
    //   313: istore #5
    //   315: goto -> 332
    //   318: aload_1
    //   319: iload #5
    //   321: baload
    //   322: sipush #256
    //   325: iadd
    //   326: sipush #128
    //   329: isub
    //   330: istore #5
    //   332: getstatic com/sk/spatch/xmltool/decode/encoding/DetectEncoding.GBKFreq : [[I
    //   335: astore #7
    //   337: aload #7
    //   339: iload #6
    //   341: aaload
    //   342: iload #5
    //   344: iaload
    //   345: ifeq -> 366
    //   348: aload_0
    //   349: aload_0
    //   350: getfield gbk_freq : J
    //   353: aload #7
    //   355: iload #6
    //   357: aaload
    //   358: iload #5
    //   360: iaload
    //   361: i2l
    //   362: ladd
    //   363: putfield gbk_freq : J
    //   366: iload #4
    //   368: iconst_1
    //   369: iadd
    //   370: istore #5
    //   372: iload #5
    //   374: iconst_1
    //   375: iadd
    //   376: istore #4
    //   378: goto -> 3
    //   381: aload_0
    //   382: getfield gbk_chars : I
    //   385: i2f
    //   386: aload_0
    //   387: getfield gbk_dbchars : I
    //   390: i2f
    //   391: fdiv
    //   392: ldc 50.0
    //   394: fmul
    //   395: aload_0
    //   396: getfield gbk_freq : J
    //   399: l2f
    //   400: aload_0
    //   401: getfield gbk_totalfreq : J
    //   404: l2f
    //   405: fdiv
    //   406: ldc 50.0
    //   408: fmul
    //   409: fadd
    //   410: f2i
    //   411: iconst_1
    //   412: isub
    //   413: ireturn }
  
  public static Charset getEncodeByIndex(int paramInt) { return encodeCharset[paramInt]; }
  
  public static String getEncodeName(int paramInt) {
    switch (paramInt) {
      default:
        return "UTF-8";
      case 6:
        return "EUC-TW";
      case 5:
        return "Big5";
      case 4:
        return "ASCII";
      case 3:
        return "Unicode";
      case 2:
        return "GBK";
      case 1:
        break;
    } 
    return "GB2312";
  }
  
  static void initialize_frequencies() {
    int i;
    for (i = 0; i < 93; i++) {
      for (int j = 0; j < 93; j++)
        GBFreq[i][j] = 0; 
    } 
    for (i = 0; i < 126; i++) {
      for (int j = 0; j < 191; j++)
        GBKFreq[i][j] = 0; 
    } 
    for (i = 0; i < 93; i++) {
      for (int j = 0; j < 157; j++)
        Big5Freq[i][j] = 0; 
    } 
    for (i = 0; i < 93; i++) {
      for (int j = 0; j < 93; j++)
        EUC_TWFreq[i][j] = 0; 
    } 
    int[][] arrayOfInt = GBFreq;
    arrayOfInt[20][35] = 599;
    arrayOfInt[49][26] = 598;
    arrayOfInt[41][38] = 597;
    arrayOfInt[17][26] = 596;
    arrayOfInt[32][42] = 595;
    arrayOfInt[39][42] = 594;
    arrayOfInt[45][49] = 593;
    arrayOfInt[51][57] = 592;
    arrayOfInt[50][47] = 591;
    arrayOfInt[42][90] = 590;
    arrayOfInt[52][65] = 589;
    arrayOfInt[53][47] = 588;
    arrayOfInt[19][82] = 587;
    arrayOfInt[31][19] = 586;
    arrayOfInt[40][46] = 585;
    arrayOfInt[24][89] = 584;
    arrayOfInt[23][85] = 583;
    arrayOfInt[20][28] = 582;
    arrayOfInt[42][20] = 581;
    arrayOfInt[34][38] = 580;
    arrayOfInt[45][9] = 579;
    arrayOfInt[54][50] = 578;
    arrayOfInt[25][44] = 577;
    arrayOfInt[35][66] = 576;
    arrayOfInt[20][55] = 575;
    arrayOfInt[18][85] = 574;
    arrayOfInt[20][31] = 573;
    arrayOfInt[49][17] = 572;
    arrayOfInt[41][16] = 571;
    arrayOfInt[35][73] = 570;
    arrayOfInt[20][34] = 569;
    arrayOfInt[29][44] = 568;
    arrayOfInt[35][38] = 567;
    arrayOfInt[49][9] = 566;
    arrayOfInt[46][33] = 565;
    arrayOfInt[49][51] = 564;
    arrayOfInt[40][89] = 563;
    arrayOfInt[26][64] = 562;
    arrayOfInt[54][51] = 561;
    arrayOfInt[54][36] = 560;
    arrayOfInt[39][4] = 559;
    arrayOfInt[53][13] = 558;
    arrayOfInt[24][92] = 557;
    arrayOfInt[27][49] = 556;
    arrayOfInt[48][6] = 555;
    arrayOfInt[21][51] = 554;
    arrayOfInt[30][40] = 553;
    arrayOfInt[42][92] = 552;
    arrayOfInt[31][78] = 551;
    arrayOfInt[25][82] = 550;
    arrayOfInt[47][0] = 549;
    arrayOfInt[34][19] = 548;
    arrayOfInt[47][35] = 547;
    arrayOfInt[21][63] = 546;
    arrayOfInt[43][75] = 545;
    arrayOfInt[21][87] = 544;
    arrayOfInt[35][59] = 543;
    arrayOfInt[25][34] = 542;
    arrayOfInt[21][27] = 541;
    arrayOfInt[39][26] = 540;
    arrayOfInt[34][26] = 539;
    arrayOfInt[39][52] = 538;
    arrayOfInt[50][57] = 537;
    arrayOfInt[37][79] = 536;
    arrayOfInt[26][24] = 535;
    arrayOfInt[22][1] = 534;
    arrayOfInt[18][40] = 533;
    arrayOfInt[41][33] = 532;
    arrayOfInt[53][26] = 531;
    arrayOfInt[54][86] = 530;
    arrayOfInt[20][16] = 529;
    arrayOfInt[46][74] = 528;
    arrayOfInt[30][19] = 527;
    arrayOfInt[45][35] = 526;
    arrayOfInt[45][61] = 525;
    arrayOfInt[30][9] = 524;
    arrayOfInt[41][53] = 523;
    arrayOfInt[41][13] = 522;
    arrayOfInt[50][34] = 521;
    arrayOfInt[53][86] = 520;
    arrayOfInt[47][47] = 519;
    arrayOfInt[22][28] = 518;
    arrayOfInt[50][53] = 517;
    arrayOfInt[39][70] = 516;
    arrayOfInt[38][15] = 515;
    arrayOfInt[42][88] = 514;
    arrayOfInt[16][29] = 513;
    arrayOfInt[27][90] = 512;
    arrayOfInt[29][12] = 511;
    arrayOfInt[44][22] = 510;
    arrayOfInt[34][69] = 509;
    arrayOfInt[24][10] = 508;
    arrayOfInt[44][11] = 507;
    arrayOfInt[39][92] = 506;
    arrayOfInt[49][48] = 505;
    arrayOfInt[31][46] = 504;
    arrayOfInt[19][50] = 503;
    arrayOfInt[21][14] = 502;
    arrayOfInt[32][28] = 501;
    arrayOfInt[18][3] = 500;
    arrayOfInt[53][9] = 499;
    arrayOfInt[34][80] = 498;
    arrayOfInt[48][88] = 497;
    arrayOfInt[46][53] = 496;
    arrayOfInt[22][53] = 495;
    arrayOfInt[28][10] = 494;
    arrayOfInt[44][65] = 493;
    arrayOfInt[20][10] = 492;
    arrayOfInt[40][76] = 491;
    arrayOfInt[47][8] = 490;
    arrayOfInt[50][74] = 489;
    arrayOfInt[23][62] = 488;
    arrayOfInt[49][65] = 487;
    arrayOfInt[28][87] = 486;
    arrayOfInt[15][48] = 485;
    arrayOfInt[22][7] = 484;
    arrayOfInt[19][42] = 483;
    arrayOfInt[41][20] = 482;
    arrayOfInt[26][55] = 481;
    arrayOfInt[21][93] = 480;
    arrayOfInt[31][76] = 479;
    arrayOfInt[34][31] = 478;
    arrayOfInt[20][66] = 477;
    arrayOfInt[51][33] = 476;
    arrayOfInt[34][86] = 475;
    arrayOfInt[37][67] = 474;
    arrayOfInt[53][53] = 473;
    arrayOfInt[40][88] = 472;
    arrayOfInt[39][10] = 471;
    arrayOfInt[24][3] = 470;
    arrayOfInt[27][25] = 469;
    arrayOfInt[26][15] = 468;
    arrayOfInt[21][88] = 467;
    arrayOfInt[52][62] = 466;
    arrayOfInt[46][81] = 465;
    arrayOfInt[38][72] = 464;
    arrayOfInt[17][30] = 463;
    arrayOfInt[52][92] = 462;
    arrayOfInt[34][90] = 461;
    arrayOfInt[21][7] = 460;
    arrayOfInt[36][13] = 459;
    arrayOfInt[45][41] = 458;
    arrayOfInt[32][5] = 457;
    arrayOfInt[26][89] = 456;
    arrayOfInt[23][87] = 455;
    arrayOfInt[20][39] = 454;
    arrayOfInt[27][23] = 453;
    arrayOfInt[25][59] = 452;
    arrayOfInt[49][20] = 451;
    arrayOfInt[54][77] = 450;
    arrayOfInt[27][67] = 449;
    arrayOfInt[47][33] = 448;
    arrayOfInt[41][17] = 447;
    arrayOfInt[19][81] = 446;
    arrayOfInt[16][66] = 445;
    arrayOfInt[45][26] = 444;
    arrayOfInt[49][81] = 443;
    arrayOfInt[53][55] = 442;
    arrayOfInt[16][26] = 441;
    arrayOfInt[54][62] = 440;
    arrayOfInt[20][70] = 439;
    arrayOfInt[42][35] = 438;
    arrayOfInt[20][57] = 437;
    arrayOfInt[34][36] = 436;
    arrayOfInt[46][63] = 435;
    arrayOfInt[19][45] = 434;
    arrayOfInt[21][10] = 433;
    arrayOfInt[52][93] = 432;
    arrayOfInt[25][2] = 431;
    arrayOfInt[30][57] = 430;
    arrayOfInt[41][24] = 429;
    arrayOfInt[28][43] = 428;
    arrayOfInt[45][86] = 427;
    arrayOfInt[51][56] = 426;
    arrayOfInt[37][28] = 425;
    arrayOfInt[52][69] = 424;
    arrayOfInt[43][92] = 423;
    arrayOfInt[41][31] = 422;
    arrayOfInt[37][87] = 421;
    arrayOfInt[47][36] = 420;
    arrayOfInt[16][16] = 419;
    arrayOfInt[40][56] = 418;
    arrayOfInt[24][55] = 417;
    arrayOfInt[17][1] = 416;
    arrayOfInt[35][57] = 415;
    arrayOfInt[27][50] = 414;
    arrayOfInt[26][14] = 413;
    arrayOfInt[50][40] = 412;
    arrayOfInt[39][19] = 411;
    arrayOfInt[19][89] = 410;
    arrayOfInt[29][91] = 409;
    arrayOfInt[17][89] = 408;
    arrayOfInt[39][74] = 407;
    arrayOfInt[46][39] = 406;
    arrayOfInt[40][28] = 405;
    arrayOfInt[45][68] = 404;
    arrayOfInt[43][10] = 403;
    arrayOfInt[42][13] = 402;
    arrayOfInt[44][81] = 401;
    arrayOfInt[41][47] = 400;
    arrayOfInt[48][58] = 399;
    arrayOfInt[43][68] = 398;
    arrayOfInt[16][79] = 397;
    arrayOfInt[19][5] = 396;
    arrayOfInt[54][59] = 395;
    arrayOfInt[17][36] = 394;
    arrayOfInt[18][0] = 393;
    arrayOfInt[41][5] = 392;
    arrayOfInt[41][72] = 391;
    arrayOfInt[16][39] = 390;
    arrayOfInt[54][0] = 389;
    arrayOfInt[51][16] = 388;
    arrayOfInt[29][36] = 387;
    arrayOfInt[47][5] = 386;
    arrayOfInt[47][51] = 385;
    arrayOfInt[44][7] = 384;
    arrayOfInt[35][30] = 383;
    arrayOfInt[26][9] = 382;
    arrayOfInt[16][7] = 381;
    arrayOfInt[32][1] = 380;
    arrayOfInt[33][76] = 379;
    arrayOfInt[34][91] = 378;
    arrayOfInt[52][36] = 377;
    arrayOfInt[26][77] = 376;
    arrayOfInt[35][48] = 375;
    arrayOfInt[40][80] = 374;
    arrayOfInt[41][92] = 373;
    arrayOfInt[27][93] = 372;
    arrayOfInt[15][17] = 371;
    arrayOfInt[16][76] = 370;
    arrayOfInt[51][12] = 369;
    arrayOfInt[18][20] = 368;
    arrayOfInt[15][54] = 367;
    arrayOfInt[50][5] = 366;
    arrayOfInt[33][22] = 365;
    arrayOfInt[37][57] = 364;
    arrayOfInt[28][47] = 363;
    arrayOfInt[42][31] = 362;
    arrayOfInt[18][2] = 361;
    arrayOfInt[43][64] = 360;
    arrayOfInt[23][47] = 359;
    arrayOfInt[28][79] = 358;
    arrayOfInt[25][45] = 357;
    arrayOfInt[23][91] = 356;
    arrayOfInt[22][19] = 355;
    arrayOfInt[25][46] = 354;
    arrayOfInt[22][36] = 353;
    arrayOfInt[54][85] = 352;
    arrayOfInt[46][20] = 351;
    arrayOfInt[27][37] = 350;
    arrayOfInt[26][81] = 349;
    arrayOfInt[42][29] = 348;
    arrayOfInt[31][90] = 347;
    arrayOfInt[41][59] = 346;
    arrayOfInt[24][65] = 345;
    arrayOfInt[44][84] = 344;
    arrayOfInt[24][90] = 343;
    arrayOfInt[38][54] = 342;
    arrayOfInt[28][70] = 341;
    arrayOfInt[27][15] = 340;
    arrayOfInt[28][80] = 339;
    arrayOfInt[29][8] = 338;
    arrayOfInt[45][80] = 337;
    arrayOfInt[53][37] = 336;
    arrayOfInt[28][65] = 335;
    arrayOfInt[23][86] = 334;
    arrayOfInt[39][45] = 333;
    arrayOfInt[53][32] = 332;
    arrayOfInt[38][68] = 331;
    arrayOfInt[45][78] = 330;
    arrayOfInt[43][7] = 329;
    arrayOfInt[46][82] = 328;
    arrayOfInt[27][38] = 327;
    arrayOfInt[16][62] = 326;
    arrayOfInt[24][17] = 325;
    arrayOfInt[22][70] = 324;
    arrayOfInt[52][28] = 323;
    arrayOfInt[23][40] = 322;
    arrayOfInt[28][50] = 321;
    arrayOfInt[42][91] = 320;
    arrayOfInt[47][76] = 319;
    arrayOfInt[15][42] = 318;
    arrayOfInt[43][55] = 317;
    arrayOfInt[29][84] = 316;
    arrayOfInt[44][90] = 315;
    arrayOfInt[53][16] = 314;
    arrayOfInt[22][93] = 313;
    arrayOfInt[34][10] = 312;
    arrayOfInt[32][53] = 311;
    arrayOfInt[43][65] = 310;
    arrayOfInt[28][7] = 309;
    arrayOfInt[35][46] = 308;
    arrayOfInt[21][39] = 307;
    arrayOfInt[44][18] = 306;
    arrayOfInt[40][10] = 305;
    arrayOfInt[54][53] = 304;
    arrayOfInt[38][74] = 303;
    arrayOfInt[28][26] = 302;
    arrayOfInt[15][13] = 301;
    arrayOfInt[39][34] = 300;
    arrayOfInt[39][46] = 299;
    arrayOfInt[42][66] = 298;
    arrayOfInt[33][58] = 297;
    arrayOfInt[15][56] = 296;
    arrayOfInt[18][51] = 295;
    arrayOfInt[49][68] = 294;
    arrayOfInt[30][37] = 293;
    arrayOfInt[51][84] = 292;
    arrayOfInt[51][9] = 291;
    arrayOfInt[40][70] = 290;
    arrayOfInt[41][84] = 289;
    arrayOfInt[28][64] = 288;
    arrayOfInt[32][88] = 287;
    arrayOfInt[24][5] = 286;
    arrayOfInt[53][23] = 285;
    arrayOfInt[42][27] = 284;
    arrayOfInt[22][38] = 283;
    arrayOfInt[32][86] = 282;
    arrayOfInt[34][30] = 281;
    arrayOfInt[38][63] = 280;
    arrayOfInt[24][59] = 279;
    arrayOfInt[22][81] = 278;
    arrayOfInt[32][11] = 277;
    arrayOfInt[51][21] = 276;
    arrayOfInt[54][41] = 275;
    arrayOfInt[21][50] = 274;
    arrayOfInt[23][89] = 273;
    arrayOfInt[19][87] = 272;
    arrayOfInt[26][7] = 271;
    arrayOfInt[30][75] = 270;
    arrayOfInt[43][84] = 269;
    arrayOfInt[51][25] = 268;
    arrayOfInt[16][67] = 267;
    arrayOfInt[32][9] = 266;
    arrayOfInt[48][51] = 265;
    arrayOfInt[39][7] = 264;
    arrayOfInt[44][88] = 263;
    arrayOfInt[52][24] = 262;
    arrayOfInt[23][34] = 261;
    arrayOfInt[32][75] = 260;
    arrayOfInt[19][10] = 259;
    arrayOfInt[28][91] = 258;
    arrayOfInt[32][83] = 257;
    arrayOfInt[25][75] = 256;
    arrayOfInt[53][45] = 255;
    arrayOfInt[29][85] = 254;
    arrayOfInt[53][59] = 253;
    arrayOfInt[16][2] = 252;
    arrayOfInt[19][78] = 251;
    arrayOfInt[15][75] = 250;
    arrayOfInt[51][42] = 249;
    arrayOfInt[45][67] = 248;
    arrayOfInt[15][74] = 247;
    arrayOfInt[25][81] = 246;
    arrayOfInt[37][62] = 245;
    arrayOfInt[16][55] = 244;
    arrayOfInt[18][38] = 243;
    arrayOfInt[23][23] = 242;
    arrayOfInt[38][30] = 241;
    arrayOfInt[17][28] = 240;
    arrayOfInt[44][73] = 239;
    arrayOfInt[23][78] = 238;
    arrayOfInt[40][77] = 237;
    arrayOfInt[38][87] = 236;
    arrayOfInt[27][19] = 235;
    arrayOfInt[38][82] = 234;
    arrayOfInt[37][22] = 233;
    arrayOfInt[41][30] = 232;
    arrayOfInt[54][9] = 231;
    arrayOfInt[32][30] = 230;
    arrayOfInt[30][52] = 229;
    arrayOfInt[40][84] = 228;
    arrayOfInt[53][57] = 227;
    arrayOfInt[27][27] = 226;
    arrayOfInt[38][64] = 225;
    arrayOfInt[18][43] = 224;
    arrayOfInt[23][69] = 223;
    arrayOfInt[28][12] = 222;
    arrayOfInt[50][78] = 221;
    arrayOfInt[50][1] = 220;
    arrayOfInt[26][88] = 219;
    arrayOfInt[36][40] = 218;
    arrayOfInt[33][89] = 217;
    arrayOfInt[41][28] = 216;
    arrayOfInt[31][77] = 215;
    arrayOfInt[46][1] = 214;
    arrayOfInt[47][19] = 213;
    arrayOfInt[35][55] = 212;
    arrayOfInt[41][21] = 211;
    arrayOfInt[27][10] = 210;
    arrayOfInt[32][77] = 209;
    arrayOfInt[26][37] = 208;
    arrayOfInt[20][33] = 207;
    arrayOfInt[41][52] = 206;
    arrayOfInt[32][18] = 205;
    arrayOfInt[38][13] = 204;
    arrayOfInt[20][18] = 203;
    arrayOfInt[20][24] = 202;
    arrayOfInt[45][19] = 201;
    arrayOfInt[18][53] = 200;
    arrayOfInt = Big5Freq;
    arrayOfInt[9][89] = 600;
    arrayOfInt[11][15] = 599;
    arrayOfInt[3][66] = 598;
    arrayOfInt[6][121] = 597;
    arrayOfInt[3][0] = 596;
    arrayOfInt[5][82] = 595;
    arrayOfInt[3][42] = 594;
    arrayOfInt[5][34] = 593;
    arrayOfInt[3][8] = 592;
    arrayOfInt[3][6] = 591;
    arrayOfInt[3][67] = 590;
    arrayOfInt[7][139] = 589;
    arrayOfInt[23][137] = 588;
    arrayOfInt[12][46] = 587;
    arrayOfInt[4][8] = 586;
    arrayOfInt[4][41] = 585;
    arrayOfInt[18][47] = 584;
    arrayOfInt[12][114] = 583;
    arrayOfInt[6][1] = 582;
    arrayOfInt[22][60] = 581;
    arrayOfInt[5][46] = 580;
    arrayOfInt[11][79] = 579;
    arrayOfInt[3][23] = 578;
    arrayOfInt[7][114] = 577;
    arrayOfInt[29][102] = 576;
    arrayOfInt[19][14] = 575;
    arrayOfInt[4][133] = 574;
    arrayOfInt[3][29] = 573;
    arrayOfInt[4][109] = 572;
    arrayOfInt[14][127] = 571;
    arrayOfInt[5][48] = 570;
    arrayOfInt[13][104] = 569;
    arrayOfInt[3][132] = 568;
    arrayOfInt[26][64] = 567;
    arrayOfInt[7][19] = 566;
    arrayOfInt[4][12] = 565;
    arrayOfInt[11][124] = 564;
    arrayOfInt[7][89] = 563;
    arrayOfInt[15][124] = 562;
    arrayOfInt[4][108] = 561;
    arrayOfInt[19][66] = 560;
    arrayOfInt[3][21] = 559;
    arrayOfInt[24][12] = 558;
    arrayOfInt[28][111] = 557;
    arrayOfInt[12][107] = 556;
    arrayOfInt[3][112] = 555;
    arrayOfInt[8][113] = 554;
    arrayOfInt[5][40] = 553;
    arrayOfInt[26][145] = 552;
    arrayOfInt[3][48] = 551;
    arrayOfInt[3][70] = 550;
    arrayOfInt[22][17] = 549;
    arrayOfInt[16][47] = 548;
    arrayOfInt[3][53] = 547;
    arrayOfInt[4][24] = 546;
    arrayOfInt[32][120] = 545;
    arrayOfInt[24][49] = 544;
    arrayOfInt[24][142] = 543;
    arrayOfInt[18][66] = 542;
    arrayOfInt[29][150] = 541;
    arrayOfInt[5][122] = 540;
    arrayOfInt[5][114] = 539;
    arrayOfInt[3][44] = 538;
    arrayOfInt[10][128] = 537;
    arrayOfInt[15][20] = 536;
    arrayOfInt[13][33] = 535;
    arrayOfInt[14][87] = 534;
    arrayOfInt[3][126] = 533;
    arrayOfInt[4][53] = 532;
    arrayOfInt[4][40] = 531;
    arrayOfInt[9][93] = 530;
    arrayOfInt[15][137] = 529;
    arrayOfInt[10][123] = 528;
    arrayOfInt[4][56] = 527;
    arrayOfInt[5][71] = 526;
    arrayOfInt[10][8] = 525;
    arrayOfInt[5][16] = 524;
    arrayOfInt[5][146] = 523;
    arrayOfInt[18][88] = 522;
    arrayOfInt[24][4] = 521;
    arrayOfInt[20][47] = 520;
    arrayOfInt[5][33] = 519;
    arrayOfInt[9][43] = 518;
    arrayOfInt[20][12] = 517;
    arrayOfInt[20][13] = 516;
    arrayOfInt[5][156] = 515;
    arrayOfInt[22][140] = 514;
    arrayOfInt[8][146] = 513;
    arrayOfInt[21][123] = 512;
    arrayOfInt[4][90] = 511;
    arrayOfInt[5][62] = 510;
    arrayOfInt[17][59] = 509;
    arrayOfInt[10][37] = 508;
    arrayOfInt[18][107] = 507;
    arrayOfInt[14][53] = 506;
    arrayOfInt[22][51] = 505;
    arrayOfInt[8][13] = 504;
    arrayOfInt[5][29] = 503;
    arrayOfInt[9][7] = 502;
    arrayOfInt[22][14] = 501;
    arrayOfInt[8][55] = 500;
    arrayOfInt[33][9] = 499;
    arrayOfInt[16][64] = 498;
    arrayOfInt[7][131] = 497;
    arrayOfInt[34][4] = 496;
    arrayOfInt[7][101] = 495;
    arrayOfInt[11][139] = 494;
    arrayOfInt[3][135] = 493;
    arrayOfInt[7][102] = 492;
    arrayOfInt[17][13] = 491;
    arrayOfInt[3][20] = 490;
    arrayOfInt[27][106] = 489;
    arrayOfInt[5][88] = 488;
    arrayOfInt[6][33] = 487;
    arrayOfInt[5][139] = 486;
    arrayOfInt[6][0] = 485;
    arrayOfInt[17][58] = 484;
    arrayOfInt[5][133] = 483;
    arrayOfInt[9][107] = 482;
    arrayOfInt[23][39] = 481;
    arrayOfInt[5][23] = 480;
    arrayOfInt[3][79] = 479;
    arrayOfInt[32][97] = 478;
    arrayOfInt[3][136] = 477;
    arrayOfInt[4][94] = 476;
    arrayOfInt[21][61] = 475;
    arrayOfInt[23][123] = 474;
    arrayOfInt[26][16] = 473;
    arrayOfInt[24][137] = 472;
    arrayOfInt[22][18] = 471;
    arrayOfInt[5][1] = 470;
    arrayOfInt[20][119] = 469;
    arrayOfInt[3][7] = 468;
    arrayOfInt[10][79] = 467;
    arrayOfInt[15][105] = 466;
    arrayOfInt[3][144] = 465;
    arrayOfInt[12][80] = 464;
    arrayOfInt[15][73] = 463;
    arrayOfInt[3][19] = 462;
    arrayOfInt[8][109] = 461;
    arrayOfInt[3][15] = 460;
    arrayOfInt[31][82] = 459;
    arrayOfInt[3][43] = 458;
    arrayOfInt[25][119] = 457;
    arrayOfInt[16][111] = 456;
    arrayOfInt[7][77] = 455;
    arrayOfInt[3][95] = 454;
    arrayOfInt[24][82] = 453;
    arrayOfInt[7][52] = 452;
    arrayOfInt[9][151] = 451;
    arrayOfInt[3][129] = 450;
    arrayOfInt[5][87] = 449;
    arrayOfInt[3][55] = 448;
    arrayOfInt[8][153] = 447;
    arrayOfInt[4][83] = 446;
    arrayOfInt[3][114] = 445;
    arrayOfInt[23][147] = 444;
    arrayOfInt[15][31] = 443;
    arrayOfInt[3][54] = 442;
    arrayOfInt[11][122] = 441;
    arrayOfInt[4][4] = 440;
    arrayOfInt[34][149] = 439;
    arrayOfInt[3][17] = 438;
    arrayOfInt[21][64] = 437;
    arrayOfInt[26][144] = 436;
    arrayOfInt[4][62] = 435;
    arrayOfInt[8][15] = 434;
    arrayOfInt[35][80] = 433;
    arrayOfInt[7][110] = 432;
    arrayOfInt[23][114] = 431;
    arrayOfInt[3][108] = 430;
    arrayOfInt[3][62] = 429;
    arrayOfInt[21][41] = 428;
    arrayOfInt[15][99] = 427;
    arrayOfInt[5][47] = 426;
    arrayOfInt[4][96] = 425;
    arrayOfInt[20][122] = 424;
    arrayOfInt[5][21] = 423;
    arrayOfInt[4][157] = 422;
    arrayOfInt[16][14] = 421;
    arrayOfInt[3][117] = 420;
    arrayOfInt[7][129] = 419;
    arrayOfInt[4][27] = 418;
    arrayOfInt[5][30] = 417;
    arrayOfInt[22][16] = 416;
    arrayOfInt[5][64] = 415;
    arrayOfInt[17][99] = 414;
    arrayOfInt[17][57] = 413;
    arrayOfInt[8][105] = 412;
    arrayOfInt[5][112] = 411;
    arrayOfInt[20][59] = 410;
    arrayOfInt[6][129] = 409;
    arrayOfInt[18][17] = 408;
    arrayOfInt[3][92] = 407;
    arrayOfInt[28][118] = 406;
    arrayOfInt[3][109] = 405;
    arrayOfInt[31][51] = 404;
    arrayOfInt[13][116] = 403;
    arrayOfInt[6][15] = 402;
    arrayOfInt[36][136] = 401;
    arrayOfInt[12][74] = 400;
    arrayOfInt[20][88] = 399;
    arrayOfInt[36][68] = 398;
    arrayOfInt[3][147] = 397;
    arrayOfInt[15][84] = 396;
    arrayOfInt[16][32] = 395;
    arrayOfInt[16][58] = 394;
    arrayOfInt[7][66] = 393;
    arrayOfInt[23][107] = 392;
    arrayOfInt[9][6] = 391;
    arrayOfInt[12][86] = 390;
    arrayOfInt[23][112] = 389;
    arrayOfInt[37][23] = 388;
    arrayOfInt[3][138] = 387;
    arrayOfInt[20][68] = 386;
    arrayOfInt[15][116] = 385;
    arrayOfInt[18][64] = 384;
    arrayOfInt[12][139] = 383;
    arrayOfInt[11][155] = 382;
    arrayOfInt[4][156] = 381;
    arrayOfInt[12][84] = 380;
    arrayOfInt[18][49] = 379;
    arrayOfInt[25][125] = 378;
    arrayOfInt[25][147] = 377;
    arrayOfInt[15][110] = 376;
    arrayOfInt[19][96] = 375;
    arrayOfInt[30][152] = 374;
    arrayOfInt[6][31] = 373;
    arrayOfInt[27][117] = 372;
    arrayOfInt[3][10] = 371;
    arrayOfInt[6][131] = 370;
    arrayOfInt[13][112] = 369;
    arrayOfInt[36][156] = 368;
    arrayOfInt[4][60] = 367;
    arrayOfInt[15][121] = 366;
    arrayOfInt[4][112] = 365;
    arrayOfInt[30][142] = 364;
    arrayOfInt[23][154] = 363;
    arrayOfInt[27][101] = 362;
    arrayOfInt[9][140] = 361;
    arrayOfInt[3][89] = 360;
    arrayOfInt[18][148] = 359;
    arrayOfInt[4][69] = 358;
    arrayOfInt[16][49] = 357;
    arrayOfInt[6][117] = 356;
    arrayOfInt[36][55] = 355;
    arrayOfInt[5][123] = 354;
    arrayOfInt[4][126] = 353;
    arrayOfInt[4][119] = 352;
    arrayOfInt[9][95] = 351;
    arrayOfInt[5][24] = 350;
    arrayOfInt[16][133] = 349;
    arrayOfInt[10][134] = 348;
    arrayOfInt[26][59] = 347;
    arrayOfInt[6][41] = 346;
    arrayOfInt[6][146] = 345;
    arrayOfInt[19][24] = 344;
    arrayOfInt[5][113] = 343;
    arrayOfInt[10][118] = 342;
    arrayOfInt[34][151] = 341;
    arrayOfInt[9][72] = 340;
    arrayOfInt[31][25] = 339;
    arrayOfInt[18][126] = 338;
    arrayOfInt[18][28] = 337;
    arrayOfInt[4][153] = 336;
    arrayOfInt[3][84] = 335;
    arrayOfInt[21][18] = 334;
    arrayOfInt[25][129] = 333;
    arrayOfInt[6][107] = 332;
    arrayOfInt[12][25] = 331;
    arrayOfInt[17][109] = 330;
    arrayOfInt[7][76] = 329;
    arrayOfInt[15][15] = 328;
    arrayOfInt[4][14] = 327;
    arrayOfInt[23][88] = 326;
    arrayOfInt[18][2] = 325;
    arrayOfInt[6][88] = 324;
    arrayOfInt[16][84] = 323;
    arrayOfInt[12][48] = 322;
    arrayOfInt[7][68] = 321;
    arrayOfInt[5][50] = 320;
    arrayOfInt[13][54] = 319;
    arrayOfInt[7][98] = 318;
    arrayOfInt[11][6] = 317;
    arrayOfInt[9][80] = 316;
    arrayOfInt[16][41] = 315;
    arrayOfInt[7][43] = 314;
    arrayOfInt[28][117] = 313;
    arrayOfInt[3][51] = 312;
    arrayOfInt[7][3] = 311;
    arrayOfInt[20][81] = 310;
    arrayOfInt[4][2] = 309;
    arrayOfInt[11][16] = 308;
    arrayOfInt[10][4] = 307;
    arrayOfInt[10][119] = 306;
    arrayOfInt[6][142] = 305;
    arrayOfInt[18][51] = 304;
    arrayOfInt[8][144] = 303;
    arrayOfInt[10][65] = 302;
    arrayOfInt[11][64] = 301;
    arrayOfInt[11][130] = 300;
    arrayOfInt[9][92] = 299;
    arrayOfInt[18][29] = 298;
    arrayOfInt[18][78] = 297;
    arrayOfInt[18][151] = 296;
    arrayOfInt[33][127] = 295;
    arrayOfInt[35][113] = 294;
    arrayOfInt[10][155] = 293;
    arrayOfInt[3][76] = 292;
    arrayOfInt[36][123] = 291;
    arrayOfInt[13][143] = 290;
    arrayOfInt[5][135] = 289;
    arrayOfInt[23][116] = 288;
    arrayOfInt[6][101] = 287;
    arrayOfInt[14][74] = 286;
    arrayOfInt[7][153] = 285;
    arrayOfInt[3][101] = 284;
    arrayOfInt[9][74] = 283;
    arrayOfInt[3][156] = 282;
    arrayOfInt[4][147] = 281;
    arrayOfInt[9][12] = 280;
    arrayOfInt[18][133] = 279;
    arrayOfInt[4][0] = 278;
    arrayOfInt[7][155] = 277;
    arrayOfInt[9][144] = 276;
    arrayOfInt[23][49] = 275;
    arrayOfInt[5][89] = 274;
    arrayOfInt[10][11] = 273;
    arrayOfInt[3][110] = 272;
    arrayOfInt[3][40] = 271;
    arrayOfInt[29][115] = 270;
    arrayOfInt[9][100] = 269;
    arrayOfInt[21][67] = 268;
    arrayOfInt[23][145] = 267;
    arrayOfInt[10][47] = 266;
    arrayOfInt[4][31] = 265;
    arrayOfInt[4][81] = 264;
    arrayOfInt[22][62] = 263;
    arrayOfInt[4][28] = 262;
    arrayOfInt[27][39] = 261;
    arrayOfInt[27][54] = 260;
    arrayOfInt[32][46] = 259;
    arrayOfInt[4][76] = 258;
    arrayOfInt[26][15] = 257;
    arrayOfInt[12][154] = 256;
    arrayOfInt[9][150] = 255;
    arrayOfInt[15][17] = 254;
    arrayOfInt[5][129] = 253;
    arrayOfInt[10][40] = 252;
    arrayOfInt[13][37] = 251;
    arrayOfInt[31][104] = 250;
    arrayOfInt[3][152] = 249;
    arrayOfInt[5][22] = 248;
    arrayOfInt[8][48] = 247;
    arrayOfInt[4][74] = 246;
    arrayOfInt[6][17] = 245;
    arrayOfInt[30][82] = 244;
    arrayOfInt[4][116] = 243;
    arrayOfInt[16][42] = 242;
    arrayOfInt[5][55] = 241;
    arrayOfInt[4][64] = 240;
    arrayOfInt[14][19] = 239;
    arrayOfInt[35][82] = 238;
    arrayOfInt[30][139] = 237;
    arrayOfInt[26][152] = 236;
    arrayOfInt[32][32] = 235;
    arrayOfInt[21][102] = 234;
    arrayOfInt[10][131] = 233;
    arrayOfInt[9][128] = 232;
    arrayOfInt[3][87] = 231;
    arrayOfInt[4][51] = 230;
    arrayOfInt[10][15] = 229;
    arrayOfInt[4][150] = 228;
    arrayOfInt[7][4] = 227;
    arrayOfInt[7][51] = 226;
    arrayOfInt[7][157] = 225;
    arrayOfInt[4][146] = 224;
    arrayOfInt[4][91] = 223;
    arrayOfInt[7][13] = 222;
    arrayOfInt[17][116] = 221;
    arrayOfInt[23][21] = 220;
    arrayOfInt[5][106] = 219;
    arrayOfInt[14][100] = 218;
    arrayOfInt[10][152] = 217;
    arrayOfInt[14][89] = 216;
    arrayOfInt[6][138] = 215;
    arrayOfInt[12][157] = 214;
    arrayOfInt[10][102] = 213;
    arrayOfInt[19][94] = 212;
    arrayOfInt[7][74] = 211;
    arrayOfInt[18][128] = 210;
    arrayOfInt[27][111] = 209;
    arrayOfInt[11][57] = 208;
    arrayOfInt[3][131] = 207;
    arrayOfInt[30][23] = 206;
    arrayOfInt[30][126] = 205;
    arrayOfInt[4][36] = 204;
    arrayOfInt[26][124] = 203;
    arrayOfInt[4][19] = 202;
    arrayOfInt[9][152] = 201;
    arrayOfInt = EUC_TWFreq;
    arrayOfInt[48][49] = 599;
    arrayOfInt[35][65] = 598;
    arrayOfInt[41][27] = 597;
    arrayOfInt[35][0] = 596;
    arrayOfInt[39][19] = 595;
    arrayOfInt[35][42] = 594;
    arrayOfInt[38][66] = 593;
    arrayOfInt[35][8] = 592;
    arrayOfInt[35][6] = 591;
    arrayOfInt[35][66] = 590;
    arrayOfInt[43][14] = 589;
    arrayOfInt[69][80] = 588;
    arrayOfInt[50][48] = 587;
    arrayOfInt[36][71] = 586;
    arrayOfInt[37][10] = 585;
    arrayOfInt[60][52] = 584;
    arrayOfInt[51][21] = 583;
    arrayOfInt[40][2] = 582;
    arrayOfInt[67][35] = 581;
    arrayOfInt[38][78] = 580;
    arrayOfInt[49][18] = 579;
    arrayOfInt[35][23] = 578;
    arrayOfInt[42][83] = 577;
    arrayOfInt[79][47] = 576;
    arrayOfInt[61][82] = 575;
    arrayOfInt[38][7] = 574;
    arrayOfInt[35][29] = 573;
    arrayOfInt[37][77] = 572;
    arrayOfInt[54][67] = 571;
    arrayOfInt[38][80] = 570;
    arrayOfInt[52][74] = 569;
    arrayOfInt[36][37] = 568;
    arrayOfInt[74][8] = 567;
    arrayOfInt[41][83] = 566;
    arrayOfInt[36][75] = 565;
    arrayOfInt[49][63] = 564;
    arrayOfInt[42][58] = 563;
    arrayOfInt[56][33] = 562;
    arrayOfInt[37][76] = 561;
    arrayOfInt[62][39] = 560;
    arrayOfInt[35][21] = 559;
    arrayOfInt[70][19] = 558;
    arrayOfInt[77][88] = 557;
    arrayOfInt[51][14] = 556;
    arrayOfInt[36][17] = 555;
    arrayOfInt[44][51] = 554;
    arrayOfInt[38][72] = 553;
    arrayOfInt[74][90] = 552;
    arrayOfInt[35][48] = 551;
    arrayOfInt[35][69] = 550;
    arrayOfInt[66][86] = 549;
    arrayOfInt[57][20] = 548;
    arrayOfInt[35][53] = 547;
    arrayOfInt[36][87] = 546;
    arrayOfInt[84][67] = 545;
    arrayOfInt[70][56] = 544;
    arrayOfInt[71][54] = 543;
    arrayOfInt[60][70] = 542;
    arrayOfInt[80][1] = 541;
    arrayOfInt[39][59] = 540;
    arrayOfInt[39][51] = 539;
    arrayOfInt[35][44] = 538;
    arrayOfInt[48][4] = 537;
    arrayOfInt[55][24] = 536;
    arrayOfInt[52][4] = 535;
    arrayOfInt[54][26] = 534;
    arrayOfInt[36][31] = 533;
    arrayOfInt[37][22] = 532;
    arrayOfInt[37][9] = 531;
    arrayOfInt[46][0] = 530;
    arrayOfInt[56][46] = 529;
    arrayOfInt[47][93] = 528;
    arrayOfInt[37][25] = 527;
    arrayOfInt[39][8] = 526;
    arrayOfInt[46][73] = 525;
    arrayOfInt[38][48] = 524;
    arrayOfInt[39][83] = 523;
    arrayOfInt[60][92] = 522;
    arrayOfInt[70][11] = 521;
    arrayOfInt[63][84] = 520;
    arrayOfInt[38][65] = 519;
    arrayOfInt[45][45] = 518;
    arrayOfInt[63][49] = 517;
    arrayOfInt[63][50] = 516;
    arrayOfInt[39][93] = 515;
    arrayOfInt[68][20] = 514;
    arrayOfInt[44][84] = 513;
    arrayOfInt[66][34] = 512;
    arrayOfInt[37][58] = 511;
    arrayOfInt[39][0] = 510;
    arrayOfInt[59][1] = 509;
    arrayOfInt[47][8] = 508;
    arrayOfInt[61][17] = 507;
    arrayOfInt[53][87] = 506;
    arrayOfInt[67][26] = 505;
    arrayOfInt[43][46] = 504;
    arrayOfInt[38][61] = 503;
    arrayOfInt[45][9] = 502;
    arrayOfInt[66][83] = 501;
    arrayOfInt[43][88] = 500;
    arrayOfInt[85][20] = 499;
    arrayOfInt[57][36] = 498;
    arrayOfInt[43][6] = 497;
    arrayOfInt[86][77] = 496;
    arrayOfInt[42][70] = 495;
    arrayOfInt[49][78] = 494;
    arrayOfInt[36][40] = 493;
    arrayOfInt[42][71] = 492;
    arrayOfInt[58][49] = 491;
    arrayOfInt[35][20] = 490;
    arrayOfInt[76][20] = 489;
    arrayOfInt[39][25] = 488;
    arrayOfInt[40][34] = 487;
    arrayOfInt[39][76] = 486;
    arrayOfInt[40][1] = 485;
    arrayOfInt[59][0] = 484;
    arrayOfInt[39][70] = 483;
    arrayOfInt[46][14] = 482;
    arrayOfInt[68][77] = 481;
    arrayOfInt[38][55] = 480;
    arrayOfInt[35][78] = 479;
    arrayOfInt[84][44] = 478;
    arrayOfInt[36][41] = 477;
    arrayOfInt[37][62] = 476;
    arrayOfInt[65][67] = 475;
    arrayOfInt[69][66] = 474;
    arrayOfInt[73][55] = 473;
    arrayOfInt[71][49] = 472;
    arrayOfInt[66][87] = 471;
    arrayOfInt[38][33] = 470;
    arrayOfInt[64][61] = 469;
    arrayOfInt[35][7] = 468;
    arrayOfInt[47][49] = 467;
    arrayOfInt[56][14] = 466;
    arrayOfInt[36][49] = 465;
    arrayOfInt[50][81] = 464;
    arrayOfInt[55][76] = 463;
    arrayOfInt[35][19] = 462;
    arrayOfInt[44][47] = 461;
    arrayOfInt[35][15] = 460;
    arrayOfInt[82][59] = 459;
    arrayOfInt[35][43] = 458;
    arrayOfInt[73][0] = 457;
    arrayOfInt[57][83] = 456;
    arrayOfInt[42][46] = 455;
    arrayOfInt[36][0] = 454;
    arrayOfInt[70][88] = 453;
    arrayOfInt[42][22] = 452;
    arrayOfInt[46][58] = 451;
    arrayOfInt[36][34] = 450;
    arrayOfInt[39][24] = 449;
    arrayOfInt[35][55] = 448;
    arrayOfInt[44][91] = 447;
    arrayOfInt[37][51] = 446;
    arrayOfInt[36][19] = 445;
    arrayOfInt[69][90] = 444;
    arrayOfInt[55][35] = 443;
    arrayOfInt[35][54] = 442;
    arrayOfInt[49][61] = 441;
    arrayOfInt[36][67] = 440;
    arrayOfInt[88][34] = 439;
    arrayOfInt[35][17] = 438;
    arrayOfInt[65][69] = 437;
    arrayOfInt[74][89] = 436;
    arrayOfInt[37][31] = 435;
    arrayOfInt[43][48] = 434;
    arrayOfInt[89][27] = 433;
    arrayOfInt[42][79] = 432;
    arrayOfInt[69][57] = 431;
    arrayOfInt[36][13] = 430;
    arrayOfInt[35][62] = 429;
    arrayOfInt[65][47] = 428;
    arrayOfInt[56][8] = 427;
    arrayOfInt[38][79] = 426;
    arrayOfInt[37][64] = 425;
    arrayOfInt[64][64] = 424;
    arrayOfInt[38][53] = 423;
    arrayOfInt[38][31] = 422;
    arrayOfInt[56][81] = 421;
    arrayOfInt[36][22] = 420;
    arrayOfInt[43][4] = 419;
    arrayOfInt[36][90] = 418;
    arrayOfInt[38][62] = 417;
    arrayOfInt[66][85] = 416;
    arrayOfInt[39][1] = 415;
    arrayOfInt[59][40] = 414;
    arrayOfInt[58][93] = 413;
    arrayOfInt[44][43] = 412;
    arrayOfInt[39][49] = 411;
    arrayOfInt[64][2] = 410;
    arrayOfInt[41][35] = 409;
    arrayOfInt[60][22] = 408;
    arrayOfInt[35][91] = 407;
    arrayOfInt[78][1] = 406;
    arrayOfInt[36][14] = 405;
    arrayOfInt[82][29] = 404;
    arrayOfInt[52][86] = 403;
    arrayOfInt[40][16] = 402;
    arrayOfInt[91][52] = 401;
    arrayOfInt[50][75] = 400;
    arrayOfInt[64][30] = 399;
    arrayOfInt[90][78] = 398;
    arrayOfInt[36][52] = 397;
    arrayOfInt[55][87] = 396;
    arrayOfInt[57][5] = 395;
    arrayOfInt[57][31] = 394;
    arrayOfInt[42][35] = 393;
    arrayOfInt[69][50] = 392;
    arrayOfInt[45][8] = 391;
    arrayOfInt[50][87] = 390;
    arrayOfInt[69][55] = 389;
    arrayOfInt[92][3] = 388;
    arrayOfInt[36][43] = 387;
    arrayOfInt[64][10] = 386;
    arrayOfInt[56][25] = 385;
    arrayOfInt[60][68] = 384;
    arrayOfInt[51][46] = 383;
    arrayOfInt[50][0] = 382;
    arrayOfInt[38][30] = 381;
    arrayOfInt[50][85] = 380;
    arrayOfInt[60][54] = 379;
    arrayOfInt[73][6] = 378;
    arrayOfInt[73][28] = 377;
    arrayOfInt[56][19] = 376;
    arrayOfInt[62][69] = 375;
    arrayOfInt[81][66] = 374;
    arrayOfInt[40][32] = 373;
    arrayOfInt[76][31] = 372;
    arrayOfInt[35][10] = 371;
    arrayOfInt[41][37] = 370;
    arrayOfInt[52][82] = 369;
    arrayOfInt[91][72] = 368;
    arrayOfInt[37][29] = 367;
    arrayOfInt[56][30] = 366;
    arrayOfInt[37][80] = 365;
    arrayOfInt[81][56] = 364;
    arrayOfInt[70][3] = 363;
    arrayOfInt[76][15] = 362;
    arrayOfInt[46][47] = 361;
    arrayOfInt[35][88] = 360;
    arrayOfInt[61][58] = 359;
    arrayOfInt[37][37] = 358;
    arrayOfInt[57][22] = 357;
    arrayOfInt[41][23] = 356;
    arrayOfInt[90][66] = 355;
    arrayOfInt[39][60] = 354;
    arrayOfInt[38][0] = 353;
    arrayOfInt[37][87] = 352;
    arrayOfInt[46][2] = 351;
    arrayOfInt[38][56] = 350;
    arrayOfInt[58][11] = 349;
    arrayOfInt[48][10] = 348;
    arrayOfInt[74][4] = 347;
    arrayOfInt[40][42] = 346;
    arrayOfInt[41][52] = 345;
    arrayOfInt[61][92] = 344;
    arrayOfInt[39][50] = 343;
    arrayOfInt[47][88] = 342;
    arrayOfInt[88][36] = 341;
    arrayOfInt[45][73] = 340;
    arrayOfInt[82][3] = 339;
    arrayOfInt[61][36] = 338;
    arrayOfInt[60][33] = 337;
    arrayOfInt[38][27] = 336;
    arrayOfInt[35][83] = 335;
    arrayOfInt[65][24] = 334;
    arrayOfInt[73][10] = 333;
    arrayOfInt[41][13] = 332;
    arrayOfInt[50][27] = 331;
    arrayOfInt[59][50] = 330;
    arrayOfInt[42][45] = 329;
    arrayOfInt[55][19] = 328;
    arrayOfInt[36][77] = 327;
    arrayOfInt[69][31] = 326;
    arrayOfInt[60][7] = 325;
    arrayOfInt[40][88] = 324;
    arrayOfInt[57][56] = 323;
    arrayOfInt[50][50] = 322;
    arrayOfInt[42][37] = 321;
    arrayOfInt[38][82] = 320;
    arrayOfInt[52][25] = 319;
    arrayOfInt[42][67] = 318;
    arrayOfInt[48][40] = 317;
    arrayOfInt[45][81] = 316;
    arrayOfInt[57][14] = 315;
    arrayOfInt[42][13] = 314;
    arrayOfInt[78][0] = 313;
    arrayOfInt[35][51] = 312;
    arrayOfInt[41][67] = 311;
    arrayOfInt[64][23] = 310;
    arrayOfInt[36][65] = 309;
    arrayOfInt[48][50] = 308;
    arrayOfInt[46][69] = 307;
    arrayOfInt[47][89] = 306;
    arrayOfInt[41][48] = 305;
    arrayOfInt[60][56] = 304;
    arrayOfInt[44][82] = 303;
    arrayOfInt[47][35] = 302;
    arrayOfInt[49][3] = 301;
    arrayOfInt[49][69] = 300;
    arrayOfInt[45][93] = 299;
    arrayOfInt[60][34] = 298;
    arrayOfInt[60][82] = 297;
    arrayOfInt[61][61] = 296;
    arrayOfInt[86][42] = 295;
    arrayOfInt[89][60] = 294;
    arrayOfInt[48][31] = 293;
    arrayOfInt[35][75] = 292;
    arrayOfInt[91][39] = 291;
    arrayOfInt[53][19] = 290;
    arrayOfInt[39][72] = 289;
    arrayOfInt[69][59] = 288;
    arrayOfInt[41][7] = 287;
    arrayOfInt[54][13] = 286;
    arrayOfInt[43][28] = 285;
    arrayOfInt[36][6] = 284;
    arrayOfInt[45][75] = 283;
    arrayOfInt[36][61] = 282;
    arrayOfInt[38][21] = 281;
    arrayOfInt[45][14] = 280;
    arrayOfInt[61][43] = 279;
    arrayOfInt[36][63] = 278;
    arrayOfInt[43][30] = 277;
    arrayOfInt[46][51] = 276;
    arrayOfInt[68][87] = 275;
    arrayOfInt[39][26] = 274;
    arrayOfInt[46][76] = 273;
    arrayOfInt[36][15] = 272;
    arrayOfInt[35][40] = 271;
    arrayOfInt[79][60] = 270;
    arrayOfInt[46][7] = 269;
    arrayOfInt[65][72] = 268;
    arrayOfInt[69][88] = 267;
    arrayOfInt[47][18] = 266;
    arrayOfInt[37][0] = 265;
    arrayOfInt[37][49] = 264;
    arrayOfInt[67][37] = 263;
    arrayOfInt[36][91] = 262;
    arrayOfInt[75][48] = 261;
    arrayOfInt[75][63] = 260;
    arrayOfInt[83][87] = 259;
    arrayOfInt[37][44] = 258;
    arrayOfInt[73][54] = 257;
    arrayOfInt[51][61] = 256;
    arrayOfInt[46][57] = 255;
    arrayOfInt[55][21] = 254;
    arrayOfInt[39][66] = 253;
    arrayOfInt[47][11] = 252;
    arrayOfInt[52][8] = 251;
    arrayOfInt[82][81] = 250;
    arrayOfInt[36][57] = 249;
    arrayOfInt[38][54] = 248;
    arrayOfInt[43][81] = 247;
    arrayOfInt[37][42] = 246;
    arrayOfInt[40][18] = 245;
    arrayOfInt[80][90] = 244;
    arrayOfInt[37][84] = 243;
    arrayOfInt[57][15] = 242;
    arrayOfInt[38][87] = 241;
    arrayOfInt[37][32] = 240;
    arrayOfInt[53][53] = 239;
    arrayOfInt[89][29] = 238;
    arrayOfInt[81][53] = 237;
    arrayOfInt[75][3] = 236;
    arrayOfInt[83][73] = 235;
    arrayOfInt[66][13] = 234;
    arrayOfInt[48][7] = 233;
    arrayOfInt[46][35] = 232;
    arrayOfInt[35][86] = 231;
    arrayOfInt[37][20] = 230;
    arrayOfInt[46][80] = 229;
    arrayOfInt[38][24] = 228;
    arrayOfInt[41][68] = 227;
    arrayOfInt[42][21] = 226;
    arrayOfInt[43][32] = 225;
    arrayOfInt[38][20] = 224;
    arrayOfInt[37][59] = 223;
    arrayOfInt[41][77] = 222;
    arrayOfInt[59][57] = 221;
    arrayOfInt[68][59] = 220;
    arrayOfInt[39][43] = 219;
    arrayOfInt[54][39] = 218;
    arrayOfInt[48][28] = 217;
    arrayOfInt[54][28] = 216;
    arrayOfInt[41][44] = 215;
    arrayOfInt[51][64] = 214;
    arrayOfInt[47][72] = 213;
    arrayOfInt[62][67] = 212;
    arrayOfInt[42][43] = 211;
    arrayOfInt[61][38] = 210;
    arrayOfInt[76][25] = 209;
    arrayOfInt[48][91] = 208;
    arrayOfInt[36][36] = 207;
    arrayOfInt[80][32] = 206;
    arrayOfInt[81][40] = 205;
    arrayOfInt[37][5] = 204;
    arrayOfInt[74][69] = 203;
    arrayOfInt[36][82] = 202;
    arrayOfInt[46][59] = 201;
    arrayOfInt = GBKFreq;
    arrayOfInt[52][132] = 600;
    arrayOfInt[73][135] = 599;
    arrayOfInt[49][123] = 598;
    arrayOfInt[77][146] = 597;
    arrayOfInt[81][123] = 596;
    arrayOfInt[82][144] = 595;
    arrayOfInt[51][179] = 594;
    arrayOfInt[83][154] = 593;
    arrayOfInt[71][139] = 592;
    arrayOfInt[64][139] = 591;
    arrayOfInt[85][144] = 590;
    arrayOfInt[52][125] = 589;
    arrayOfInt[88][25] = 588;
    arrayOfInt[81][106] = 587;
    arrayOfInt[81][148] = 586;
    arrayOfInt[62][137] = 585;
    arrayOfInt[94][0] = 584;
    arrayOfInt[1][64] = 583;
    arrayOfInt[67][163] = 582;
    arrayOfInt[20][190] = 581;
    arrayOfInt[57][131] = 580;
    arrayOfInt[29][169] = 579;
    arrayOfInt[72][143] = 578;
    arrayOfInt[0][173] = 577;
    arrayOfInt[11][23] = 576;
    arrayOfInt[61][141] = 575;
    arrayOfInt[60][123] = 574;
    arrayOfInt[81][114] = 573;
    arrayOfInt[82][131] = 572;
    arrayOfInt[67][156] = 571;
    arrayOfInt[71][167] = 570;
    arrayOfInt[20][50] = 569;
    arrayOfInt[77][132] = 568;
    arrayOfInt[84][38] = 567;
    arrayOfInt[26][29] = 566;
    arrayOfInt[74][187] = 565;
    arrayOfInt[62][116] = 564;
    arrayOfInt[67][135] = 563;
    arrayOfInt[5][86] = 562;
    arrayOfInt[72][186] = 561;
    arrayOfInt[75][161] = 560;
    arrayOfInt[78][130] = 559;
    arrayOfInt[94][30] = 558;
    arrayOfInt[84][72] = 557;
    arrayOfInt[1][67] = 556;
    arrayOfInt[75][172] = 555;
    arrayOfInt[74][185] = 554;
    arrayOfInt[53][160] = 553;
    arrayOfInt[123][14] = 552;
    arrayOfInt[79][97] = 551;
    arrayOfInt[85][110] = 550;
    arrayOfInt[78][171] = 549;
    arrayOfInt[52][131] = 548;
    arrayOfInt[56][100] = 547;
    arrayOfInt[50][182] = 546;
    arrayOfInt[94][64] = 545;
    arrayOfInt[106][74] = 544;
    arrayOfInt[11][102] = 543;
    arrayOfInt[53][124] = 542;
    arrayOfInt[24][3] = 541;
    arrayOfInt[86][148] = 540;
    arrayOfInt[53][184] = 539;
    arrayOfInt[86][147] = 538;
    arrayOfInt[96][161] = 537;
    arrayOfInt[82][77] = 536;
    arrayOfInt[59][146] = 535;
    arrayOfInt[84][126] = 534;
    arrayOfInt[79][132] = 533;
    arrayOfInt[85][123] = 532;
    arrayOfInt[71][101] = 531;
    arrayOfInt[85][106] = 530;
    arrayOfInt[6][184] = 529;
    arrayOfInt[57][156] = 528;
    arrayOfInt[75][104] = 527;
    arrayOfInt[50][137] = 526;
    arrayOfInt[79][133] = 525;
    arrayOfInt[76][108] = 524;
    arrayOfInt[57][142] = 523;
    arrayOfInt[84][130] = 522;
    arrayOfInt[52][128] = 521;
    arrayOfInt[47][44] = 520;
    arrayOfInt[52][152] = 519;
    arrayOfInt[54][104] = 518;
    arrayOfInt[30][47] = 517;
    arrayOfInt[71][123] = 516;
    arrayOfInt[52][107] = 515;
    arrayOfInt[45][84] = 514;
    arrayOfInt[107][118] = 513;
    arrayOfInt[5][161] = 512;
    arrayOfInt[48][126] = 511;
    arrayOfInt[67][170] = 510;
    arrayOfInt[43][6] = 509;
    arrayOfInt[70][112] = 508;
    arrayOfInt[86][174] = 507;
    arrayOfInt[84][166] = 506;
    arrayOfInt[79][130] = 505;
    arrayOfInt[57][141] = 504;
    arrayOfInt[81][178] = 503;
    arrayOfInt[56][187] = 502;
    arrayOfInt[81][162] = 501;
    arrayOfInt[53][104] = 500;
    arrayOfInt[123][35] = 499;
    arrayOfInt[70][169] = 498;
    arrayOfInt[69][164] = 497;
    arrayOfInt[109][61] = 496;
    arrayOfInt[73][130] = 495;
    arrayOfInt[62][134] = 494;
    arrayOfInt[54][125] = 493;
    arrayOfInt[79][105] = 492;
    arrayOfInt[70][165] = 491;
    arrayOfInt[71][189] = 490;
    arrayOfInt[23][147] = 489;
    arrayOfInt[51][139] = 488;
    arrayOfInt[47][137] = 487;
    arrayOfInt[77][123] = 486;
    arrayOfInt[86][183] = 485;
    arrayOfInt[63][173] = 484;
    arrayOfInt[79][144] = 483;
    arrayOfInt[84][159] = 482;
    arrayOfInt[60][91] = 481;
    arrayOfInt[66][187] = 480;
    arrayOfInt[73][114] = 479;
    arrayOfInt[85][56] = 478;
    arrayOfInt[71][149] = 477;
    arrayOfInt[84][189] = 476;
    arrayOfInt[104][31] = 475;
    arrayOfInt[83][82] = 474;
    arrayOfInt[68][35] = 473;
    arrayOfInt[11][77] = 472;
    arrayOfInt[15][155] = 471;
    arrayOfInt[83][153] = 470;
    arrayOfInt[71][1] = 469;
    arrayOfInt[53][190] = 468;
    arrayOfInt[50][135] = 467;
    arrayOfInt[3][147] = 466;
    arrayOfInt[48][136] = 465;
    arrayOfInt[66][166] = 464;
    arrayOfInt[55][159] = 463;
    arrayOfInt[82][150] = 462;
    arrayOfInt[58][178] = 461;
    arrayOfInt[64][102] = 460;
    arrayOfInt[16][106] = 459;
    arrayOfInt[68][110] = 458;
    arrayOfInt[54][14] = 457;
    arrayOfInt[60][140] = 456;
    arrayOfInt[91][71] = 455;
    arrayOfInt[54][150] = 454;
    arrayOfInt[78][177] = 453;
    arrayOfInt[78][117] = 452;
    arrayOfInt[104][12] = 451;
    arrayOfInt[73][150] = 450;
    arrayOfInt[51][142] = 449;
    arrayOfInt[81][145] = 448;
    arrayOfInt[66][183] = 447;
    arrayOfInt[51][178] = 446;
    arrayOfInt[75][107] = 445;
    arrayOfInt[65][119] = 444;
    arrayOfInt[69][176] = 443;
    arrayOfInt[59][122] = 442;
    arrayOfInt[78][160] = 441;
    arrayOfInt[85][183] = 440;
    arrayOfInt[105][16] = 439;
    arrayOfInt[73][110] = 438;
    arrayOfInt[104][39] = 437;
    arrayOfInt[119][16] = 436;
    arrayOfInt[76][162] = 435;
    arrayOfInt[67][152] = 434;
    arrayOfInt[82][24] = 433;
    arrayOfInt[73][121] = 432;
    arrayOfInt[83][83] = 431;
    arrayOfInt[82][145] = 430;
    arrayOfInt[49][133] = 429;
    arrayOfInt[94][13] = 428;
    arrayOfInt[58][139] = 427;
    arrayOfInt[74][189] = 426;
    arrayOfInt[66][177] = 425;
    arrayOfInt[85][184] = 424;
    arrayOfInt[55][183] = 423;
    arrayOfInt[71][107] = 422;
    arrayOfInt[11][98] = 421;
    arrayOfInt[72][153] = 420;
    arrayOfInt[2][137] = 419;
    arrayOfInt[59][147] = 418;
    arrayOfInt[58][152] = 417;
    arrayOfInt[55][144] = 416;
    arrayOfInt[73][125] = 415;
    arrayOfInt[52][154] = 414;
    arrayOfInt[70][178] = 413;
    arrayOfInt[79][148] = 412;
    arrayOfInt[63][143] = 411;
    arrayOfInt[50][140] = 410;
    arrayOfInt[47][145] = 409;
    arrayOfInt[48][123] = 408;
    arrayOfInt[56][107] = 407;
    arrayOfInt[84][83] = 406;
    arrayOfInt[59][112] = 405;
    arrayOfInt[124][72] = 404;
    arrayOfInt[79][99] = 403;
    arrayOfInt[3][37] = 402;
    arrayOfInt[114][55] = 401;
    arrayOfInt[85][152] = 400;
    arrayOfInt[60][47] = 399;
    arrayOfInt[65][96] = 398;
    arrayOfInt[74][110] = 397;
    arrayOfInt[86][182] = 396;
    arrayOfInt[50][99] = 395;
    arrayOfInt[67][186] = 394;
    arrayOfInt[81][74] = 393;
    arrayOfInt[80][37] = 392;
    arrayOfInt[21][60] = 391;
    arrayOfInt[110][12] = 390;
    arrayOfInt[60][162] = 389;
    arrayOfInt[29][115] = 388;
    arrayOfInt[83][130] = 387;
    arrayOfInt[52][136] = 386;
    arrayOfInt[63][114] = 385;
    arrayOfInt[49][127] = 384;
    arrayOfInt[83][109] = 383;
    arrayOfInt[66][128] = 382;
    arrayOfInt[78][136] = 381;
    arrayOfInt[81][180] = 380;
    arrayOfInt[76][104] = 379;
    arrayOfInt[56][156] = 378;
    arrayOfInt[61][23] = 377;
    arrayOfInt[4][30] = 376;
    arrayOfInt[69][154] = 375;
    arrayOfInt[100][37] = 374;
    arrayOfInt[54][177] = 373;
    arrayOfInt[23][119] = 372;
    arrayOfInt[71][171] = 371;
    arrayOfInt[84][146] = 370;
    arrayOfInt[20][184] = 369;
    arrayOfInt[86][76] = 368;
    arrayOfInt[74][132] = 367;
    arrayOfInt[47][97] = 366;
    arrayOfInt[82][137] = 365;
    arrayOfInt[94][56] = 364;
    arrayOfInt[92][30] = 363;
    arrayOfInt[19][117] = 362;
    arrayOfInt[48][173] = 361;
    arrayOfInt[2][136] = 360;
    arrayOfInt[7][182] = 359;
    arrayOfInt[74][188] = 358;
    arrayOfInt[14][132] = 357;
    arrayOfInt[62][172] = 356;
    arrayOfInt[25][39] = 355;
    arrayOfInt[85][129] = 354;
    arrayOfInt[64][98] = 353;
    arrayOfInt[67][127] = 352;
    arrayOfInt[72][167] = 351;
    arrayOfInt[57][143] = 350;
    arrayOfInt[76][187] = 349;
    arrayOfInt[83][181] = 348;
    arrayOfInt[84][10] = 347;
    arrayOfInt[55][166] = 346;
    arrayOfInt[55][188] = 345;
    arrayOfInt[13][151] = 344;
    arrayOfInt[62][124] = 343;
    arrayOfInt[53][136] = 342;
    arrayOfInt[106][57] = 341;
    arrayOfInt[47][166] = 340;
    arrayOfInt[109][30] = 339;
    arrayOfInt[78][114] = 338;
    arrayOfInt[83][19] = 337;
    arrayOfInt[56][162] = 336;
    arrayOfInt[60][177] = 335;
    arrayOfInt[88][9] = 334;
    arrayOfInt[74][163] = 333;
    arrayOfInt[52][156] = 332;
    arrayOfInt[71][180] = 331;
    arrayOfInt[60][57] = 330;
    arrayOfInt[72][173] = 329;
    arrayOfInt[82][91] = 328;
    arrayOfInt[51][186] = 327;
    arrayOfInt[75][86] = 326;
    arrayOfInt[75][78] = 325;
    arrayOfInt[76][170] = 324;
    arrayOfInt[60][147] = 323;
    arrayOfInt[82][75] = 322;
    arrayOfInt[80][148] = 321;
    arrayOfInt[86][150] = 320;
    arrayOfInt[13][95] = 319;
    arrayOfInt[0][11] = 318;
    arrayOfInt[84][190] = 317;
    arrayOfInt[76][166] = 316;
    arrayOfInt[14][72] = 315;
    arrayOfInt[67][144] = 314;
    arrayOfInt[84][44] = 313;
    arrayOfInt[72][125] = 312;
    arrayOfInt[66][127] = 311;
    arrayOfInt[60][25] = 310;
    arrayOfInt[70][146] = 309;
    arrayOfInt[79][135] = 308;
    arrayOfInt[54][135] = 307;
    arrayOfInt[60][104] = 306;
    arrayOfInt[55][132] = 305;
    arrayOfInt[94][2] = 304;
    arrayOfInt[54][133] = 303;
    arrayOfInt[56][190] = 302;
    arrayOfInt[58][174] = 301;
    arrayOfInt[80][144] = 300;
    arrayOfInt[85][113] = 299;
  }
  
  private int utf16_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (!this.impossibleUTF16) {
      if (paramInt2 < 2)
        return 0; 
      if ((-2 == paramArrayOfbyte[paramInt1] && -1 == paramArrayOfbyte[paramInt1 + 1]) || (-1 == paramArrayOfbyte[paramInt1] && -2 == paramArrayOfbyte[paramInt1 + 1]))
        return 100; 
      this.impossibleUTF16 = true;
    } 
    return 0;
  }
  
  private int utf8_probability(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { // Byte code:
    //   0: iload_2
    //   1: iload_3
    //   2: iadd
    //   3: istore #6
    //   5: iload_2
    //   6: iload #6
    //   8: if_icmpge -> 218
    //   11: aload_1
    //   12: iload_2
    //   13: baload
    //   14: bipush #127
    //   16: iand
    //   17: aload_1
    //   18: iload_2
    //   19: baload
    //   20: if_icmpne -> 39
    //   23: aload_0
    //   24: aload_0
    //   25: getfield asciibytes : I
    //   28: iconst_1
    //   29: iadd
    //   30: putfield asciibytes : I
    //   33: iload_2
    //   34: istore #4
    //   36: goto -> 210
    //   39: bipush #-64
    //   41: aload_1
    //   42: iload_2
    //   43: baload
    //   44: if_icmpgt -> 104
    //   47: aload_1
    //   48: iload_2
    //   49: baload
    //   50: bipush #-33
    //   52: if_icmpgt -> 104
    //   55: iload_2
    //   56: iconst_1
    //   57: iadd
    //   58: istore #4
    //   60: iload #4
    //   62: iload #6
    //   64: if_icmpge -> 104
    //   67: bipush #-128
    //   69: aload_1
    //   70: iload #4
    //   72: baload
    //   73: if_icmpgt -> 104
    //   76: aload_1
    //   77: iload #4
    //   79: baload
    //   80: bipush #-65
    //   82: if_icmpgt -> 104
    //   85: aload_0
    //   86: aload_0
    //   87: getfield goodbytes : I
    //   90: iconst_2
    //   91: iadd
    //   92: putfield goodbytes : I
    //   95: iload #4
    //   97: istore_2
    //   98: iload_2
    //   99: istore #4
    //   101: goto -> 210
    //   104: iload_2
    //   105: istore #4
    //   107: bipush #-32
    //   109: aload_1
    //   110: iload_2
    //   111: baload
    //   112: if_icmpgt -> 210
    //   115: iload_2
    //   116: istore #4
    //   118: aload_1
    //   119: iload_2
    //   120: baload
    //   121: bipush #-17
    //   123: if_icmpgt -> 210
    //   126: iload_2
    //   127: iconst_2
    //   128: iadd
    //   129: istore #5
    //   131: iload_2
    //   132: istore #4
    //   134: iload #5
    //   136: iload #6
    //   138: if_icmpge -> 210
    //   141: iload_2
    //   142: iconst_1
    //   143: iadd
    //   144: istore #7
    //   146: iload_2
    //   147: istore #4
    //   149: bipush #-128
    //   151: aload_1
    //   152: iload #7
    //   154: baload
    //   155: if_icmpgt -> 210
    //   158: iload_2
    //   159: istore #4
    //   161: aload_1
    //   162: iload #7
    //   164: baload
    //   165: bipush #-65
    //   167: if_icmpgt -> 210
    //   170: iload_2
    //   171: istore #4
    //   173: bipush #-128
    //   175: aload_1
    //   176: iload #5
    //   178: baload
    //   179: if_icmpgt -> 210
    //   182: iload_2
    //   183: istore #4
    //   185: aload_1
    //   186: iload #5
    //   188: baload
    //   189: bipush #-65
    //   191: if_icmpgt -> 210
    //   194: aload_0
    //   195: aload_0
    //   196: getfield goodbytes : I
    //   199: iconst_3
    //   200: iadd
    //   201: putfield goodbytes : I
    //   204: iload #5
    //   206: istore_2
    //   207: goto -> 98
    //   210: iload #4
    //   212: iconst_1
    //   213: iadd
    //   214: istore_2
    //   215: goto -> 5
    //   218: aload_0
    //   219: getfield asciibytes : I
    //   222: istore_2
    //   223: iload_2
    //   224: iload_3
    //   225: if_icmpne -> 230
    //   228: iconst_0
    //   229: ireturn
    //   230: aload_0
    //   231: getfield utf8Length : I
    //   234: iload_3
    //   235: iload_2
    //   236: isub
    //   237: iadd
    //   238: istore_3
    //   239: aload_0
    //   240: iload_3
    //   241: putfield utf8Length : I
    //   244: aload_0
    //   245: getfield goodbytes : I
    //   248: istore_2
    //   249: iload_2
    //   250: i2f
    //   251: iload_3
    //   252: i2f
    //   253: fdiv
    //   254: ldc 100.0
    //   256: fmul
    //   257: f2i
    //   258: istore_3
    //   259: iload_3
    //   260: bipush #98
    //   262: if_icmple -> 267
    //   265: iload_3
    //   266: ireturn
    //   267: iload_3
    //   268: bipush #95
    //   270: if_icmple -> 281
    //   273: iload_2
    //   274: bipush #30
    //   276: if_icmple -> 281
    //   279: iload_3
    //   280: ireturn
    //   281: aload_0
    //   282: iconst_0
    //   283: putfield goodbytes : I
    //   286: iconst_0
    //   287: ireturn }
  
  public Charset getEncode() { return encodeCharset[this.encoding_guess]; }
  
  public int getEncodeIndex() { return this.encoding_guess; }
  
  public void update(byte[] paramArrayOfbyte) { update(paramArrayOfbyte, 0, paramArrayOfbyte.length); }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int[] arrayOfInt = new int[8];
    int j = utf8_probability(paramArrayOfbyte, paramInt1, paramInt2);
    int i = 0;
    arrayOfInt[0] = j;
    arrayOfInt[1] = gb2312_probability(paramArrayOfbyte, paramInt1, paramInt2);
    arrayOfInt[2] = gbk_probability(paramArrayOfbyte, paramInt1, paramInt2);
    arrayOfInt[3] = utf16_probability(paramArrayOfbyte, paramInt1, paramInt2);
    arrayOfInt[4] = ascii_probability(paramArrayOfbyte, paramInt1, paramInt2);
    arrayOfInt[5] = big5_probability(paramArrayOfbyte, paramInt1, paramInt2);
    arrayOfInt[6] = euc_tw_probability(paramArrayOfbyte, paramInt1, paramInt2);
    paramInt2 = 0;
    paramInt1 = i;
    while (paramInt1 < 8) {
      i = paramInt2;
      if (arrayOfInt[paramInt1] > paramInt2) {
        this.encoding_guess = paramInt1;
        i = arrayOfInt[paramInt1];
      } 
      paramInt1++;
      paramInt2 = i;
    } 
    if (paramInt2 <= 50)
      this.encoding_guess = 7; 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\encoding\DetectEncoding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */