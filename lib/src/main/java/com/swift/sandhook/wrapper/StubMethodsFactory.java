package com.swift.sandhook.wrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class StubMethodsFactory {
  private static volatile int curStub = 0;
  
  static final int maxStub = 300;
  
  private static Method proxyGenClass;
  
  static  {
    try {
      Method method = Proxy.class.getDeclaredMethod("generateProxy", new Class[] { String.class, Class[].class, ClassLoader.class, Method[].class, Class[][].class });
      return;
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
  }
  
  public static Method getStubMethod() { // Byte code:
    //   0: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   2: monitorenter
    //   3: getstatic com/swift/sandhook/wrapper/StubMethodsFactory.curStub : I
    //   6: istore_0
    //   7: iload_0
    //   8: sipush #300
    //   11: if_icmpgt -> 64
    //   14: new java/lang/StringBuilder
    //   17: dup
    //   18: invokespecial <init> : ()V
    //   21: astore_1
    //   22: aload_1
    //   23: ldc 'stub'
    //   25: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: getstatic com/swift/sandhook/wrapper/StubMethodsFactory.curStub : I
    //   32: istore_0
    //   33: iload_0
    //   34: iconst_1
    //   35: iadd
    //   36: putstatic com/swift/sandhook/wrapper/StubMethodsFactory.curStub : I
    //   39: aload_1
    //   40: iload_0
    //   41: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   47: aload_1
    //   48: invokevirtual toString : ()Ljava/lang/String;
    //   51: iconst_0
    //   52: anewarray java/lang/Class
    //   55: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   58: astore_1
    //   59: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   61: monitorexit
    //   62: aload_1
    //   63: areturn
    //   64: getstatic com/swift/sandhook/wrapper/StubMethodsFactory.curStub : I
    //   67: iconst_1
    //   68: iadd
    //   69: putstatic com/swift/sandhook/wrapper/StubMethodsFactory.curStub : I
    //   72: getstatic com/swift/sandhook/wrapper/StubMethodsFactory.proxyGenClass : Ljava/lang/reflect/Method;
    //   75: astore_1
    //   76: new java/lang/StringBuilder
    //   79: dup
    //   80: invokespecial <init> : ()V
    //   83: astore_2
    //   84: aload_2
    //   85: ldc 'SandHookerStubClass_'
    //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: aload_2
    //   92: getstatic com/swift/sandhook/wrapper/StubMethodsFactory.curStub : I
    //   95: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: aload_1
    //   100: aconst_null
    //   101: iconst_5
    //   102: anewarray java/lang/Object
    //   105: dup
    //   106: iconst_0
    //   107: aload_2
    //   108: invokevirtual toString : ()Ljava/lang/String;
    //   111: aastore
    //   112: dup
    //   113: iconst_1
    //   114: aconst_null
    //   115: aastore
    //   116: dup
    //   117: iconst_2
    //   118: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   120: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   123: aastore
    //   124: dup
    //   125: iconst_3
    //   126: iconst_1
    //   127: anewarray java/lang/reflect/Method
    //   130: dup
    //   131: iconst_0
    //   132: getstatic com/swift/sandhook/wrapper/StubMethodsFactory.proxyGenClass : Ljava/lang/reflect/Method;
    //   135: aastore
    //   136: aastore
    //   137: dup
    //   138: iconst_4
    //   139: aconst_null
    //   140: aastore
    //   141: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   144: checkcast java/lang/Class
    //   147: invokevirtual getDeclaredMethods : ()[Ljava/lang/reflect/Method;
    //   150: iconst_0
    //   151: aaload
    //   152: astore_1
    //   153: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   155: monitorexit
    //   156: aload_1
    //   157: areturn
    //   158: astore_1
    //   159: aload_1
    //   160: invokevirtual printStackTrace : ()V
    //   163: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   165: monitorexit
    //   166: aconst_null
    //   167: areturn
    //   168: astore_1
    //   169: ldc com/swift/sandhook/wrapper/StubMethodsFactory
    //   171: monitorexit
    //   172: aload_1
    //   173: athrow
    //   174: astore_1
    //   175: goto -> 3
    // Exception table:
    //   from	to	target	type
    //   3	7	168	finally
    //   14	59	174	java/lang/NoSuchMethodException
    //   14	59	168	finally
    //   64	153	158	finally
    //   159	163	168	finally }
  
  public void stub0() {}
  
  public void stub1() {}
  
  public void stub10() {}
  
  public void stub100() {}
  
  public void stub101() {}
  
  public void stub102() {}
  
  public void stub103() {}
  
  public void stub104() {}
  
  public void stub105() {}
  
  public void stub106() {}
  
  public void stub107() {}
  
  public void stub108() {}
  
  public void stub109() {}
  
  public void stub11() {}
  
  public void stub110() {}
  
  public void stub111() {}
  
  public void stub112() {}
  
  public void stub113() {}
  
  public void stub114() {}
  
  public void stub115() {}
  
  public void stub116() {}
  
  public void stub117() {}
  
  public void stub118() {}
  
  public void stub119() {}
  
  public void stub12() {}
  
  public void stub120() {}
  
  public void stub121() {}
  
  public void stub122() {}
  
  public void stub123() {}
  
  public void stub124() {}
  
  public void stub125() {}
  
  public void stub126() {}
  
  public void stub127() {}
  
  public void stub128() {}
  
  public void stub129() {}
  
  public void stub13() {}
  
  public void stub130() {}
  
  public void stub131() {}
  
  public void stub132() {}
  
  public void stub133() {}
  
  public void stub134() {}
  
  public void stub135() {}
  
  public void stub136() {}
  
  public void stub137() {}
  
  public void stub138() {}
  
  public void stub139() {}
  
  public void stub14() {}
  
  public void stub140() {}
  
  public void stub141() {}
  
  public void stub142() {}
  
  public void stub143() {}
  
  public void stub144() {}
  
  public void stub145() {}
  
  public void stub146() {}
  
  public void stub147() {}
  
  public void stub148() {}
  
  public void stub149() {}
  
  public void stub15() {}
  
  public void stub150() {}
  
  public void stub151() {}
  
  public void stub152() {}
  
  public void stub153() {}
  
  public void stub154() {}
  
  public void stub155() {}
  
  public void stub156() {}
  
  public void stub157() {}
  
  public void stub158() {}
  
  public void stub159() {}
  
  public void stub16() {}
  
  public void stub160() {}
  
  public void stub161() {}
  
  public void stub162() {}
  
  public void stub163() {}
  
  public void stub164() {}
  
  public void stub165() {}
  
  public void stub166() {}
  
  public void stub167() {}
  
  public void stub168() {}
  
  public void stub169() {}
  
  public void stub17() {}
  
  public void stub170() {}
  
  public void stub171() {}
  
  public void stub172() {}
  
  public void stub173() {}
  
  public void stub174() {}
  
  public void stub175() {}
  
  public void stub176() {}
  
  public void stub177() {}
  
  public void stub178() {}
  
  public void stub179() {}
  
  public void stub18() {}
  
  public void stub180() {}
  
  public void stub181() {}
  
  public void stub182() {}
  
  public void stub183() {}
  
  public void stub184() {}
  
  public void stub185() {}
  
  public void stub186() {}
  
  public void stub187() {}
  
  public void stub188() {}
  
  public void stub189() {}
  
  public void stub19() {}
  
  public void stub190() {}
  
  public void stub191() {}
  
  public void stub192() {}
  
  public void stub193() {}
  
  public void stub194() {}
  
  public void stub195() {}
  
  public void stub196() {}
  
  public void stub197() {}
  
  public void stub198() {}
  
  public void stub199() {}
  
  public void stub2() {}
  
  public void stub20() {}
  
  public void stub200() {}
  
  public void stub201() {}
  
  public void stub202() {}
  
  public void stub203() {}
  
  public void stub204() {}
  
  public void stub205() {}
  
  public void stub206() {}
  
  public void stub207() {}
  
  public void stub208() {}
  
  public void stub209() {}
  
  public void stub21() {}
  
  public void stub210() {}
  
  public void stub211() {}
  
  public void stub212() {}
  
  public void stub213() {}
  
  public void stub214() {}
  
  public void stub215() {}
  
  public void stub216() {}
  
  public void stub217() {}
  
  public void stub218() {}
  
  public void stub219() {}
  
  public void stub22() {}
  
  public void stub220() {}
  
  public void stub221() {}
  
  public void stub222() {}
  
  public void stub223() {}
  
  public void stub224() {}
  
  public void stub225() {}
  
  public void stub226() {}
  
  public void stub227() {}
  
  public void stub228() {}
  
  public void stub229() {}
  
  public void stub23() {}
  
  public void stub230() {}
  
  public void stub231() {}
  
  public void stub232() {}
  
  public void stub233() {}
  
  public void stub234() {}
  
  public void stub235() {}
  
  public void stub236() {}
  
  public void stub237() {}
  
  public void stub238() {}
  
  public void stub239() {}
  
  public void stub24() {}
  
  public void stub240() {}
  
  public void stub241() {}
  
  public void stub242() {}
  
  public void stub243() {}
  
  public void stub244() {}
  
  public void stub245() {}
  
  public void stub246() {}
  
  public void stub247() {}
  
  public void stub248() {}
  
  public void stub249() {}
  
  public void stub25() {}
  
  public void stub250() {}
  
  public void stub251() {}
  
  public void stub252() {}
  
  public void stub253() {}
  
  public void stub254() {}
  
  public void stub255() {}
  
  public void stub256() {}
  
  public void stub257() {}
  
  public void stub258() {}
  
  public void stub259() {}
  
  public void stub26() {}
  
  public void stub260() {}
  
  public void stub261() {}
  
  public void stub262() {}
  
  public void stub263() {}
  
  public void stub264() {}
  
  public void stub265() {}
  
  public void stub266() {}
  
  public void stub267() {}
  
  public void stub268() {}
  
  public void stub269() {}
  
  public void stub27() {}
  
  public void stub270() {}
  
  public void stub271() {}
  
  public void stub272() {}
  
  public void stub273() {}
  
  public void stub274() {}
  
  public void stub275() {}
  
  public void stub276() {}
  
  public void stub277() {}
  
  public void stub278() {}
  
  public void stub279() {}
  
  public void stub28() {}
  
  public void stub280() {}
  
  public void stub281() {}
  
  public void stub282() {}
  
  public void stub283() {}
  
  public void stub284() {}
  
  public void stub285() {}
  
  public void stub286() {}
  
  public void stub287() {}
  
  public void stub288() {}
  
  public void stub289() {}
  
  public void stub29() {}
  
  public void stub290() {}
  
  public void stub291() {}
  
  public void stub292() {}
  
  public void stub293() {}
  
  public void stub294() {}
  
  public void stub295() {}
  
  public void stub296() {}
  
  public void stub297() {}
  
  public void stub298() {}
  
  public void stub299() {}
  
  public void stub3() {}
  
  public void stub30() {}
  
  public void stub300() {}
  
  public void stub31() {}
  
  public void stub32() {}
  
  public void stub33() {}
  
  public void stub34() {}
  
  public void stub35() {}
  
  public void stub36() {}
  
  public void stub37() {}
  
  public void stub38() {}
  
  public void stub39() {}
  
  public void stub4() {}
  
  public void stub40() {}
  
  public void stub41() {}
  
  public void stub42() {}
  
  public void stub43() {}
  
  public void stub44() {}
  
  public void stub45() {}
  
  public void stub46() {}
  
  public void stub47() {}
  
  public void stub48() {}
  
  public void stub49() {}
  
  public void stub5() {}
  
  public void stub50() {}
  
  public void stub51() {}
  
  public void stub52() {}
  
  public void stub53() {}
  
  public void stub54() {}
  
  public void stub55() {}
  
  public void stub56() {}
  
  public void stub57() {}
  
  public void stub58() {}
  
  public void stub59() {}
  
  public void stub6() {}
  
  public void stub60() {}
  
  public void stub61() {}
  
  public void stub62() {}
  
  public void stub63() {}
  
  public void stub64() {}
  
  public void stub65() {}
  
  public void stub66() {}
  
  public void stub67() {}
  
  public void stub68() {}
  
  public void stub69() {}
  
  public void stub7() {}
  
  public void stub70() {}
  
  public void stub71() {}
  
  public void stub72() {}
  
  public void stub73() {}
  
  public void stub74() {}
  
  public void stub75() {}
  
  public void stub76() {}
  
  public void stub77() {}
  
  public void stub78() {}
  
  public void stub79() {}
  
  public void stub8() {}
  
  public void stub80() {}
  
  public void stub81() {}
  
  public void stub82() {}
  
  public void stub83() {}
  
  public void stub84() {}
  
  public void stub85() {}
  
  public void stub86() {}
  
  public void stub87() {}
  
  public void stub88() {}
  
  public void stub89() {}
  
  public void stub9() {}
  
  public void stub90() {}
  
  public void stub91() {}
  
  public void stub92() {}
  
  public void stub93() {}
  
  public void stub94() {}
  
  public void stub95() {}
  
  public void stub96() {}
  
  public void stub97() {}
  
  public void stub98() {}
  
  public void stub99() {}
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhook\wrapper\StubMethodsFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */