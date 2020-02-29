package com.beust.jcommander;

import com.beust.jcommander.converters.DefaultListConverter;
import com.beust.jcommander.converters.EnumConverter;
import com.beust.jcommander.converters.NoConverter;
import com.beust.jcommander.converters.StringConverter;
import com.beust.jcommander.internal.Console;
import com.beust.jcommander.internal.DefaultConverterFactory;
import com.beust.jcommander.internal.JDK6Console;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.beust.jcommander.internal.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class JCommander {
  public static final String DEBUG_PROPERTY = "jcommander.debug";
  
  private static Console console;
  
  private final IVariableArity DEFAULT_VARIABLE_ARITY = new DefaultVariableArity();
  
  private Map<FuzzyMap.IKey, ProgramName> aliasMap = Maps.newLinkedHashMap();
  
  private Map<ProgramName, JCommander> commands = Maps.newLinkedHashMap();
  
  private Map<FuzzyMap.IKey, ParameterDescription> descriptions;
  
  private Map<Parameterized, ParameterDescription> fields = Maps.newHashMap();
  
  private boolean firstTimeMainParameter = true;
  
  private boolean helpWasSpecified;
  
  private Parameterized mainParameter = null;
  
  private Parameter mainParameterAnnotation;
  
  private ParameterDescription mainParameterDescription;
  
  private Object mainParameterObject;
  
  private List<Object> objects = Lists.newArrayList();
  
  private final Options options;
  
  private String parsedAlias;
  
  private String parsedCommand;
  
  private ProgramName programName;
  
  private Map<Parameterized, ParameterDescription> requiredFields = Maps.newHashMap();
  
  private List<String> unknownArgs = Lists.newArrayList();
  
  public JCommander() { this(new Options(null)); }
  
  private JCommander(Options paramOptions) {
    if (paramOptions != null) {
      this.options = paramOptions;
      addConverterFactory((IStringConverterFactory)new DefaultConverterFactory());
      return;
    } 
    throw new NullPointerException("options");
  }
  
  public JCommander(Object paramObject) { this(paramObject, (ResourceBundle)null); }
  
  public JCommander(Object paramObject, @Nullable ResourceBundle paramResourceBundle) { this(paramObject, paramResourceBundle, (String[])null); }
  
  public JCommander(Object paramObject, @Nullable ResourceBundle paramResourceBundle, String... paramVarArgs) {
    this();
    addObject(paramObject);
    if (paramResourceBundle != null)
      setDescriptionsBundle(paramResourceBundle); 
    createDescriptions();
    if (paramVarArgs != null)
      parse(paramVarArgs); 
  }
  
  public JCommander(Object paramObject, String... paramVarArgs) {
    this(paramObject);
    parse(paramVarArgs);
  }
  
  private void addDescription(Object<Parameterized> paramObject) {
    paramObject.getClass();
    Iterator<Parameterized> iterator = Parameterized.parseArg(paramObject).iterator();
    while (true) {
      Object<Parameterized> object = paramObject;
      if (iterator.hasNext()) {
        Object<Parameterized> object2;
        Parameterized parameterized = iterator.next();
        Object object3 = parameterized.getWrappedParameter();
        int j = 0;
        int i = 0;
        if (object3 != null && object3.getParameter() != null) {
          object3 = object3.getParameter();
          if ((object3.names()).length == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Found main parameter:");
            stringBuilder.append(parameterized);
            p(stringBuilder.toString());
            if (this.mainParameter == null) {
              this.mainParameter = parameterized;
              this.mainParameterObject = object;
              this.mainParameterAnnotation = (Parameter)object3;
              this.mainParameterDescription = new ParameterDescription(paramObject, (Parameter)object3, parameterized, this.options.bundle, this);
              object = (Object<Parameterized>)iterator;
            } else {
              paramObject = (Object<Parameterized>)new StringBuilder();
              paramObject.append("Only one @Parameter with no names attribute is allowed, found:");
              paramObject.append(this.mainParameter);
              paramObject.append(" and ");
              paramObject.append(parameterized);
              throw new ParameterException(paramObject.toString());
            } 
          } else {
            ParameterDescription parameterDescription = new ParameterDescription(paramObject, (Parameter)object3, parameterized, this.options.bundle, this);
            String[] arrayOfString = object3.names();
            j = arrayOfString.length;
            while (true) {
              object2 = (Object<Parameterized>)iterator;
              if (i < j) {
                object2 = (Object<Parameterized>)arrayOfString[i];
                if (!this.descriptions.containsKey(new StringKey((String)object2))) {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("Adding description for ");
                  stringBuilder.append((String)object2);
                  p(stringBuilder.toString());
                  this.fields.put(parameterized, parameterDescription);
                  this.descriptions.put(new StringKey((String)object2), parameterDescription);
                  if (object3.required())
                    this.requiredFields.put(parameterized, parameterDescription); 
                  i++;
                  continue;
                } 
                paramObject = (Object<Parameterized>)new StringBuilder();
                paramObject.append("Found the option ");
                paramObject.append((String)object2);
                paramObject.append(" multiple times");
                throw new ParameterException(paramObject.toString());
              } 
              break;
            } 
          } 
        } else if (parameterized.getDelegateAnnotation() != null) {
          object2 = (Object<Parameterized>)parameterized.get(object2);
          if (object2 != null) {
            addDescription(object2);
            object2 = (Object<Parameterized>)iterator;
          } else {
            paramObject = (Object<Parameterized>)new StringBuilder();
            paramObject.append("Delegate field '");
            paramObject.append(parameterized.getName());
            paramObject.append("' cannot be null.");
            throw new ParameterException(paramObject.toString());
          } 
        } else {
          Iterator<Parameterized> iterator1 = iterator;
          if (object3 != null) {
            iterator1 = iterator;
            if (object3.getDynamicParameter() != null) {
              object3 = object3.getDynamicParameter();
              String[] arrayOfString = object3.names();
              i = arrayOfString.length;
              while (true) {
                iterator1 = iterator;
                if (j < i) {
                  object2 = (Object<Parameterized>)arrayOfString[j];
                  if (!this.descriptions.containsKey(object2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Adding description for ");
                    stringBuilder.append((String)object2);
                    p(stringBuilder.toString());
                    ParameterDescription parameterDescription = new ParameterDescription(paramObject, (DynamicParameter)object3, parameterized, this.options.bundle, this);
                    this.fields.put(parameterized, parameterDescription);
                    this.descriptions.put(new StringKey((String)object2), parameterDescription);
                    if (object3.required())
                      this.requiredFields.put(parameterized, parameterDescription); 
                    j++;
                    continue;
                  } 
                  paramObject = (Object<Parameterized>)new StringBuilder();
                  paramObject.append("Found the option ");
                  paramObject.append((String)object2);
                  paramObject.append(" multiple times");
                  throw new ParameterException(paramObject.toString());
                } 
                break;
              } 
            } 
          } 
        } 
        Object<Parameterized> object1 = object2;
        continue;
      } 
      break;
    } 
  }
  
  private void createDescriptions() {
    this.descriptions = Maps.newHashMap();
    Iterator iterator = this.objects.iterator();
    while (iterator.hasNext())
      addDescription(iterator.next()); 
  }
  
  private String[] expandArgs(String[] paramArrayOfString) {
    List<String> list2 = Lists.newArrayList();
    int j = paramArrayOfString.length;
    int i;
    for (i = 0; i < j; i++) {
      String str = paramArrayOfString[i];
      if (str.startsWith("@") && this.options.expandAtSign) {
        list2.addAll(readFile(str.substring(1)));
      } else {
        list2.addAll(expandDynamicArg(str));
      } 
    } 
    List<String> list1 = Lists.newArrayList();
    for (Object[] arrayOfObject : list2) {
      if (isOption((String)arrayOfObject)) {
        String str = getSeparatorFor((String)arrayOfObject);
        if (!" ".equals(str)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("[");
          stringBuilder.append(str);
          stringBuilder.append("]");
          arrayOfObject = (Object[])arrayOfObject.split(stringBuilder.toString(), 2);
          j = arrayOfObject.length;
          for (i = 0; i < j; i++)
            list1.add(arrayOfObject[i]); 
          continue;
        } 
        list1.add((String)arrayOfObject);
        continue;
      } 
      list1.add(arrayOfObject);
    } 
    return list1.toArray(new String[list1.size()]);
  }
  
  private List<String> expandDynamicArg(String paramString) {
    for (ParameterDescription parameterDescription : this.descriptions.values()) {
      if (parameterDescription.isDynamicParameter())
        for (String str : parameterDescription.getParameter().names()) {
          if (paramString.startsWith(str) && !paramString.equals(str))
            return Arrays.asList(new String[] { str, paramString.substring(str.length()) }); 
        }  
    } 
    return Arrays.asList(new String[] { paramString });
  }
  
  private JCommander findCommand(ProgramName paramProgramName) { return FuzzyMap.findInMap((Map)this.commands, paramProgramName, this.options.caseSensitiveOptions, this.options.allowAbbreviatedOptions); }
  
  private JCommander findCommandByAlias(String paramString) {
    ProgramName programName1 = findProgramName(paramString);
    if (programName1 == null)
      return null; 
    JCommander jCommander = findCommand(programName1);
    if (jCommander != null)
      return jCommander; 
    throw new IllegalStateException("There appears to be inconsistency in the internal command database.  This is likely a bug. Please report.");
  }
  
  private IStringConverter<?> findConverterInstance(Parameter paramParameter, Class<?> paramClass, String paramString) {
    Iterator<IStringConverterInstanceFactory> iterator = this.options.converterInstanceFactories.iterator();
    while (iterator.hasNext()) {
      IStringConverter<?> iStringConverter = ((IStringConverterInstanceFactory)iterator.next()).getConverterInstance(paramParameter, paramClass, paramString);
      if (iStringConverter != null)
        return iStringConverter; 
    } 
    return null;
  }
  
  private ParameterDescription findParameterDescription(String paramString) { return FuzzyMap.findInMap(this.descriptions, new StringKey(paramString), this.options.caseSensitiveOptions, this.options.allowAbbreviatedOptions); }
  
  private ProgramName findProgramName(String paramString) { return FuzzyMap.findInMap(this.aliasMap, new StringKey(paramString), this.options.caseSensitiveOptions, this.options.allowAbbreviatedOptions); }
  
  public static Console getConsole() {
    if (console == null)
      try {
        console = (Console)new JDK6Console(System.class.getDeclaredMethod("console", new Class[0]).invoke(null, new Object[0]));
      } finally {
        Exception exception = null;
      }  
    return console;
  }
  
  private ParameterDescription getDescriptionFor(String paramString) { return getPrefixDescriptionFor(paramString); }
  
  private String getI18nString(ResourceBundle paramResourceBundle, String paramString1, String paramString2) {
    ResourceBundle resourceBundle;
    if (paramResourceBundle != null) {
      String str = paramResourceBundle.getString(paramString1);
    } else {
      paramResourceBundle = null;
    } 
    if (paramResourceBundle != null)
      resourceBundle = paramResourceBundle; 
    return (String)resourceBundle;
  }
  
  private List<?> getMainParameter(String paramString) {
    Object object;
    Parameterized parameterized = this.mainParameter;
    if (parameterized != null) {
      List list = (List)parameterized.get(this.mainParameterObject);
      object = list;
      if (list == null) {
        object = Lists.newArrayList();
        if (List.class.isAssignableFrom(this.mainParameter.getType())) {
          this.mainParameter.set(this.mainParameterObject, object);
        } else {
          object = new StringBuilder();
          object.append("Main parameter field ");
          object.append(this.mainParameter);
          object.append(" needs to be of type List, not ");
          object.append(this.mainParameter.getType());
          throw new ParameterException(object.toString());
        } 
      } 
      if (this.firstTimeMainParameter) {
        object.clear();
        this.firstTimeMainParameter = false;
      } 
      return (List<?>)object;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Was passed main parameter '");
    stringBuilder.append((String)object);
    stringBuilder.append("' but no main parameter was defined in your arg class");
    throw new ParameterException(stringBuilder.toString());
  }
  
  private Comparator<? super ParameterDescription> getParameterDescriptionComparator() { return this.options.parameterDescriptionComparator; }
  
  private ParameterDescription getPrefixDescriptionFor(String paramString) {
    for (Map.Entry<FuzzyMap.IKey, ParameterDescription> entry : this.descriptions.entrySet()) {
      if (paramString.startsWith(((FuzzyMap.IKey)entry.getKey()).getName()))
        return (ParameterDescription)entry.getValue(); 
    } 
    return null;
  }
  
  private String getSeparatorFor(String paramString) {
    ParameterDescription parameterDescription = getDescriptionFor(paramString);
    if (parameterDescription != null) {
      Parameters parameters = parameterDescription.getObject().getClass().getAnnotation(Parameters.class);
      if (parameters != null)
        return parameters.separators(); 
    } 
    return " ";
  }
  
  private void initializeDefaultValue(ParameterDescription paramParameterDescription) {
    for (String str1 : paramParameterDescription.getParameter().names()) {
      String str2 = this.options.defaultProvider.getDefaultValueFor(str1);
      if (str2 != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Initializing ");
        stringBuilder.append(str1);
        stringBuilder.append(" with default value:");
        stringBuilder.append(str2);
        p(stringBuilder.toString());
        paramParameterDescription.addValue(str2, true);
        this.requiredFields.remove(paramParameterDescription.getParameterized());
        return;
      } 
    } 
  }
  
  private void initializeDefaultValues() {
    if (this.options.defaultProvider != null) {
      Iterator<ParameterDescription> iterator = this.descriptions.values().iterator();
      while (iterator.hasNext())
        initializeDefaultValue(iterator.next()); 
      iterator = this.commands.entrySet().iterator();
      while (iterator.hasNext())
        ((JCommander)((Map.Entry)iterator.next()).getValue()).initializeDefaultValues(); 
    } 
  }
  
  private static <T> T instantiateConverter(String paramString, Class<? extends T> paramClass) throws InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor[] arrayOfConstructor = (Constructor[])paramClass.getDeclaredConstructors();
    int j = arrayOfConstructor.length;
    T t2 = null;
    Constructor<T> constructor2 = null;
    Constructor<T> constructor1 = constructor2;
    int i = 0;
    while (i < j) {
      Constructor<T> constructor4;
      Constructor<T> constructor3 = arrayOfConstructor[i];
      constructor3.setAccessible(true);
      Class[] arrayOfClass = constructor3.getParameterTypes();
      if (arrayOfClass.length == 1 && arrayOfClass[0].equals(String.class)) {
        constructor4 = constructor3;
      } else {
        constructor4 = constructor2;
        if (arrayOfClass.length == 0) {
          constructor1 = constructor3;
          constructor4 = constructor2;
        } 
      } 
      i++;
      constructor2 = constructor4;
    } 
    if (constructor2 != null)
      return constructor2.newInstance(new Object[] { paramString }); 
    T t1 = t2;
    if (constructor1 != null)
      t1 = constructor1.newInstance(new Object[0]); 
    return t1;
  }
  
  private boolean isOption(String paramString) {
    if (this.options.acceptUnknownOptions)
      return true; 
    if (!this.options.caseSensitiveOptions)
      paramString = paramString.toLowerCase(); 
    Iterator<FuzzyMap.IKey> iterator = this.descriptions.keySet().iterator();
    while (iterator.hasNext()) {
      if (matchArg(paramString, iterator.next()))
        return true; 
    } 
    iterator = this.commands.keySet().iterator();
    while (iterator.hasNext()) {
      if (matchArg(paramString, iterator.next()))
        return true; 
    } 
    return false;
  }
  
  private StringBuilder join(Object[] paramArrayOfObject) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      if (i > 0)
        stringBuilder.append(" "); 
      stringBuilder.append(paramArrayOfObject[i]);
    } 
    return stringBuilder;
  }
  
  private boolean matchArg(String paramString, FuzzyMap.IKey paramIKey) {
    String str;
    if (this.options.caseSensitiveOptions) {
      str = paramIKey.getName();
    } else {
      str = paramIKey.getName().toLowerCase();
    } 
    if (this.options.allowAbbreviatedOptions) {
      if (str.startsWith(paramString))
        return true; 
    } else if ((ParameterDescription)this.descriptions.get(paramIKey) != null) {
      if (!" ".equals(getSeparatorFor(paramString))) {
        if (paramString.startsWith(str))
          return true; 
      } else if (str.equals(paramString)) {
        return true;
      } 
    } else if (str.equals(paramString)) {
      return true;
    } 
    return false;
  }
  
  public static Builder newBuilder() { return new Builder(); }
  
  private void p(String paramString) {
    if (this.options.verbose > 0 || System.getProperty("jcommander.debug") != null) {
      Console console1 = getConsole();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[JCommander] ");
      stringBuilder.append(paramString);
      console1.println(stringBuilder.toString());
    } 
  }
  
  private void parse(boolean paramBoolean, String... paramVarArgs) {
    StringBuilder stringBuilder1 = new StringBuilder("Parsing \"");
    StringBuilder stringBuilder2 = join((Object[])paramVarArgs);
    stringBuilder2.append("\"\n  with:");
    stringBuilder2.append(join(this.objects.toArray()));
    stringBuilder1.append(stringBuilder2);
    p(stringBuilder1.toString());
    if (this.descriptions == null)
      createDescriptions(); 
    initializeDefaultValues();
    parseValues(expandArgs(paramVarArgs), paramBoolean);
    if (paramBoolean)
      validateOptions(); 
  }
  
  private void parseValues(String[] paramArrayOfString, boolean paramBoolean) { // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: iload #5
    //   5: istore_3
    //   6: iload_3
    //   7: istore #4
    //   9: iload_3
    //   10: istore #7
    //   12: iload #5
    //   14: istore_3
    //   15: aload_1
    //   16: arraylength
    //   17: istore #5
    //   19: iconst_1
    //   20: istore #9
    //   22: iload_3
    //   23: iload #5
    //   25: if_icmpge -> 739
    //   28: iload #7
    //   30: ifne -> 739
    //   33: aload_1
    //   34: iload_3
    //   35: aaload
    //   36: astore #11
    //   38: aload #11
    //   40: invokestatic trim : (Ljava/lang/String;)Ljava/lang/String;
    //   43: astore #10
    //   45: aload_1
    //   46: iload_3
    //   47: aload #10
    //   49: aastore
    //   50: new java/lang/StringBuilder
    //   53: dup
    //   54: invokespecial <init> : ()V
    //   57: astore #12
    //   59: aload #12
    //   61: ldc_w 'Parsing arg: '
    //   64: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: pop
    //   68: aload #12
    //   70: aload #10
    //   72: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload_0
    //   77: aload #12
    //   79: invokevirtual toString : ()Ljava/lang/String;
    //   82: invokespecial p : (Ljava/lang/String;)V
    //   85: aload_0
    //   86: aload #11
    //   88: invokespecial findCommandByAlias : (Ljava/lang/String;)Lcom/beust/jcommander/JCommander;
    //   91: astore #12
    //   93: iload #4
    //   95: ifne -> 446
    //   98: ldc_w '--'
    //   101: aload #10
    //   103: invokevirtual equals : (Ljava/lang/Object;)Z
    //   106: ifne -> 446
    //   109: aload_0
    //   110: aload #10
    //   112: invokespecial isOption : (Ljava/lang/String;)Z
    //   115: ifeq -> 446
    //   118: aload #12
    //   120: ifnonnull -> 446
    //   123: aload_0
    //   124: aload #10
    //   126: invokespecial findParameterDescription : (Ljava/lang/String;)Lcom/beust/jcommander/ParameterDescription;
    //   129: astore #10
    //   131: aload #10
    //   133: ifnull -> 339
    //   136: aload #10
    //   138: invokevirtual getParameter : ()Lcom/beust/jcommander/WrappedParameter;
    //   141: invokevirtual password : ()Z
    //   144: ifeq -> 202
    //   147: aload #10
    //   149: new java/lang/String
    //   152: dup
    //   153: aload_0
    //   154: aload #10
    //   156: invokevirtual getDescription : ()Ljava/lang/String;
    //   159: aload #10
    //   161: invokevirtual getParameter : ()Lcom/beust/jcommander/WrappedParameter;
    //   164: invokevirtual echoInput : ()Z
    //   167: invokespecial readPassword : (Ljava/lang/String;Z)[C
    //   170: invokespecial <init> : ([C)V
    //   173: invokevirtual addValue : (Ljava/lang/String;)V
    //   176: aload_0
    //   177: getfield requiredFields : Ljava/util/Map;
    //   180: aload #10
    //   182: invokevirtual getParameterized : ()Lcom/beust/jcommander/Parameterized;
    //   185: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   190: pop
    //   191: iload #4
    //   193: istore #8
    //   195: iload #9
    //   197: istore #6
    //   199: goto -> 727
    //   202: aload #10
    //   204: invokevirtual getParameter : ()Lcom/beust/jcommander/WrappedParameter;
    //   207: invokevirtual variableArity : ()Z
    //   210: ifeq -> 235
    //   213: aload_0
    //   214: aload_1
    //   215: iload_3
    //   216: aload #10
    //   218: iload_2
    //   219: invokespecial processVariableArity : ([Ljava/lang/String;ILcom/beust/jcommander/ParameterDescription;Z)I
    //   222: istore #5
    //   224: iload #4
    //   226: istore #8
    //   228: iload #5
    //   230: istore #6
    //   232: goto -> 727
    //   235: aload #10
    //   237: invokevirtual getParameterized : ()Lcom/beust/jcommander/Parameterized;
    //   240: invokevirtual getType : ()Ljava/lang/Class;
    //   243: astore #11
    //   245: aload #11
    //   247: getstatic java/lang/Boolean.TYPE : Ljava/lang/Class;
    //   250: if_acmpeq -> 261
    //   253: aload #11
    //   255: ldc_w java/lang/Boolean
    //   258: if_acmpne -> 302
    //   261: aload #10
    //   263: invokevirtual getParameter : ()Lcom/beust/jcommander/WrappedParameter;
    //   266: invokevirtual arity : ()I
    //   269: iconst_m1
    //   270: if_icmpne -> 302
    //   273: aload #10
    //   275: ldc_w 'true'
    //   278: invokevirtual addValue : (Ljava/lang/String;)V
    //   281: aload_0
    //   282: getfield requiredFields : Ljava/util/Map;
    //   285: aload #10
    //   287: invokevirtual getParameterized : ()Lcom/beust/jcommander/Parameterized;
    //   290: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   295: pop
    //   296: iconst_1
    //   297: istore #6
    //   299: goto -> 315
    //   302: aload_0
    //   303: aload_1
    //   304: iload_3
    //   305: aload #10
    //   307: iload_2
    //   308: aload #11
    //   310: invokespecial processFixedArity : ([Ljava/lang/String;ILcom/beust/jcommander/ParameterDescription;ZLjava/lang/Class;)I
    //   313: istore #6
    //   315: iload #6
    //   317: istore #5
    //   319: aload #10
    //   321: invokevirtual isHelp : ()Z
    //   324: ifeq -> 224
    //   327: aload_0
    //   328: iconst_1
    //   329: putfield helpWasSpecified : Z
    //   332: iload #6
    //   334: istore #5
    //   336: goto -> 224
    //   339: aload_0
    //   340: getfield options : Lcom/beust/jcommander/JCommander$Options;
    //   343: invokestatic access$600 : (Lcom/beust/jcommander/JCommander$Options;)Z
    //   346: ifeq -> 411
    //   349: aload_0
    //   350: getfield unknownArgs : Ljava/util/List;
    //   353: aload #11
    //   355: invokeinterface add : (Ljava/lang/Object;)Z
    //   360: pop
    //   361: iload_3
    //   362: iconst_1
    //   363: iadd
    //   364: istore_3
    //   365: iload_3
    //   366: aload_1
    //   367: arraylength
    //   368: if_icmpge -> 401
    //   371: aload_0
    //   372: aload_1
    //   373: iload_3
    //   374: aaload
    //   375: invokespecial isOption : (Ljava/lang/String;)Z
    //   378: ifne -> 401
    //   381: aload_0
    //   382: getfield unknownArgs : Ljava/util/List;
    //   385: aload_1
    //   386: iload_3
    //   387: aaload
    //   388: invokeinterface add : (Ljava/lang/Object;)Z
    //   393: pop
    //   394: iload_3
    //   395: iconst_1
    //   396: iadd
    //   397: istore_3
    //   398: goto -> 365
    //   401: iconst_0
    //   402: istore #6
    //   404: iload #4
    //   406: istore #8
    //   408: goto -> 727
    //   411: new java/lang/StringBuilder
    //   414: dup
    //   415: invokespecial <init> : ()V
    //   418: astore_1
    //   419: aload_1
    //   420: ldc_w 'Unknown option: '
    //   423: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   426: pop
    //   427: aload_1
    //   428: aload #11
    //   430: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   433: pop
    //   434: new com/beust/jcommander/ParameterException
    //   437: dup
    //   438: aload_1
    //   439: invokevirtual toString : ()Ljava/lang/String;
    //   442: invokespecial <init> : (Ljava/lang/String;)V
    //   445: athrow
    //   446: iload_3
    //   447: istore #5
    //   449: ldc_w '--'
    //   452: aload #11
    //   454: invokevirtual equals : (Ljava/lang/Object;)Z
    //   457: ifeq -> 477
    //   460: iload_3
    //   461: iconst_1
    //   462: iadd
    //   463: istore #5
    //   465: aload_1
    //   466: iload #5
    //   468: aaload
    //   469: invokestatic trim : (Ljava/lang/String;)Ljava/lang/String;
    //   472: astore #10
    //   474: iconst_1
    //   475: istore #4
    //   477: aload_0
    //   478: getfield commands : Ljava/util/Map;
    //   481: invokeinterface isEmpty : ()Z
    //   486: ifeq -> 615
    //   489: aload_0
    //   490: aload #11
    //   492: invokespecial getMainParameter : (Ljava/lang/String;)Ljava/util/List;
    //   495: astore #12
    //   497: aload_0
    //   498: getfield mainParameter : Lcom/beust/jcommander/Parameterized;
    //   501: invokevirtual getGenericType : ()Ljava/lang/reflect/Type;
    //   504: instanceof java/lang/reflect/ParameterizedType
    //   507: ifeq -> 558
    //   510: aload_0
    //   511: getfield mainParameter : Lcom/beust/jcommander/Parameterized;
    //   514: invokevirtual getGenericType : ()Ljava/lang/reflect/Type;
    //   517: checkcast java/lang/reflect/ParameterizedType
    //   520: invokeinterface getActualTypeArguments : ()[Ljava/lang/reflect/Type;
    //   525: iconst_0
    //   526: aaload
    //   527: astore #11
    //   529: aload #11
    //   531: instanceof java/lang/Class
    //   534: ifeq -> 558
    //   537: aload_0
    //   538: aload_0
    //   539: getfield mainParameter : Lcom/beust/jcommander/Parameterized;
    //   542: aload #11
    //   544: checkcast java/lang/Class
    //   547: aconst_null
    //   548: aload #10
    //   550: invokevirtual convertValue : (Lcom/beust/jcommander/Parameterized;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   553: astore #11
    //   555: goto -> 562
    //   558: aload #10
    //   560: astore #11
    //   562: aload_0
    //   563: getfield mainParameterDescription : Lcom/beust/jcommander/ParameterDescription;
    //   566: aload_0
    //   567: getfield mainParameterAnnotation : Lcom/beust/jcommander/Parameter;
    //   570: invokeinterface validateWith : ()Ljava/lang/Class;
    //   575: ldc_w 'Default'
    //   578: aload #10
    //   580: invokestatic validateParameter : (Lcom/beust/jcommander/ParameterDescription;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)V
    //   583: aload_0
    //   584: getfield mainParameterDescription : Lcom/beust/jcommander/ParameterDescription;
    //   587: iconst_1
    //   588: invokevirtual setAssigned : (Z)V
    //   591: aload #12
    //   593: aload #11
    //   595: invokeinterface add : (Ljava/lang/Object;)Z
    //   600: pop
    //   601: iload #5
    //   603: istore_3
    //   604: iload #4
    //   606: istore #8
    //   608: iload #9
    //   610: istore #6
    //   612: goto -> 727
    //   615: aload #12
    //   617: ifnonnull -> 664
    //   620: iload_2
    //   621: ifne -> 627
    //   624: goto -> 664
    //   627: new java/lang/StringBuilder
    //   630: dup
    //   631: invokespecial <init> : ()V
    //   634: astore_1
    //   635: aload_1
    //   636: ldc_w 'Expected a command, got '
    //   639: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   642: pop
    //   643: aload_1
    //   644: aload #11
    //   646: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   649: pop
    //   650: new com/beust/jcommander/MissingCommandException
    //   653: dup
    //   654: aload_1
    //   655: invokevirtual toString : ()Ljava/lang/String;
    //   658: aload #11
    //   660: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   663: athrow
    //   664: iload #5
    //   666: istore_3
    //   667: iload #4
    //   669: istore #8
    //   671: iload #9
    //   673: istore #6
    //   675: aload #12
    //   677: ifnull -> 727
    //   680: aload_0
    //   681: aload #12
    //   683: getfield programName : Lcom/beust/jcommander/JCommander$ProgramName;
    //   686: invokestatic access$800 : (Lcom/beust/jcommander/JCommander$ProgramName;)Ljava/lang/String;
    //   689: putfield parsedCommand : Ljava/lang/String;
    //   692: aload_0
    //   693: aload #11
    //   695: putfield parsedAlias : Ljava/lang/String;
    //   698: aload #12
    //   700: iload_2
    //   701: aload_0
    //   702: aload_1
    //   703: iload #5
    //   705: iconst_1
    //   706: iadd
    //   707: invokespecial subArray : ([Ljava/lang/String;I)[Ljava/lang/String;
    //   710: invokespecial parse : (Z[Ljava/lang/String;)V
    //   713: iconst_1
    //   714: istore #7
    //   716: iload #9
    //   718: istore #6
    //   720: iload #4
    //   722: istore #8
    //   724: iload #5
    //   726: istore_3
    //   727: iload_3
    //   728: iload #6
    //   730: iadd
    //   731: istore_3
    //   732: iload #8
    //   734: istore #4
    //   736: goto -> 15
    //   739: aload_0
    //   740: getfield descriptions : Ljava/util/Map;
    //   743: invokeinterface values : ()Ljava/util/Collection;
    //   748: invokeinterface iterator : ()Ljava/util/Iterator;
    //   753: astore_1
    //   754: aload_1
    //   755: invokeinterface hasNext : ()Z
    //   760: ifeq -> 806
    //   763: aload_1
    //   764: invokeinterface next : ()Ljava/lang/Object;
    //   769: checkcast com/beust/jcommander/ParameterDescription
    //   772: astore #10
    //   774: aload #10
    //   776: invokevirtual isAssigned : ()Z
    //   779: ifeq -> 754
    //   782: aload_0
    //   783: getfield fields : Ljava/util/Map;
    //   786: aload #10
    //   788: invokevirtual getParameterized : ()Lcom/beust/jcommander/Parameterized;
    //   791: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   796: checkcast com/beust/jcommander/ParameterDescription
    //   799: iconst_1
    //   800: invokevirtual setAssigned : (Z)V
    //   803: goto -> 754
    //   806: return }
  
  private static String pluralize(int paramInt, String paramString1, String paramString2) { return (paramInt == 1) ? paramString1 : paramString2; }
  
  private int processFixedArity(String[] paramArrayOfString, int paramInt, ParameterDescription paramParameterDescription, boolean paramBoolean, Class<?> paramClass) {
    int i = paramParameterDescription.getParameter().arity();
    if (i == -1)
      i = 1; 
    return processFixedArity(paramArrayOfString, paramInt, paramParameterDescription, paramBoolean, paramClass, i);
  }
  
  private int processFixedArity(String[] paramArrayOfString, int paramInt1, ParameterDescription paramParameterDescription, boolean paramBoolean, Class<?> paramClass, int paramInt2) { throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n"); }
  
  private int processVariableArity(String[] paramArrayOfString, int paramInt, ParameterDescription paramParameterDescription, boolean paramBoolean) {
    Object object = paramParameterDescription.getObject();
    if (!(object instanceof IVariableArity)) {
      object = this.DEFAULT_VARIABLE_ARITY;
    } else {
      object = object;
    } 
    List<String> list = Lists.newArrayList();
    int i;
    for (i = paramInt + 1; i < paramArrayOfString.length; i++)
      list.add(paramArrayOfString[i]); 
    return processFixedArity(paramArrayOfString, paramInt, paramParameterDescription, paramBoolean, List.class, object.processVariableArity(paramParameterDescription.getParameter().names()[0], list.toArray(new String[0])));
  }
  
  private List<String> readFile(String paramString) {
    List<String> list = Lists.newArrayList();
    try {
      BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(paramString, new String[0]), this.options.atFileCharset);
      try {
        while (true) {
          String str = bufferedReader.readLine();
          if (str != null) {
            if (str.length() > 0 && !str.trim().startsWith("#"))
              list.add(str); 
            continue;
          } 
          break;
        } 
        return list;
      } finally {
        list = null;
      } 
    } catch (IOException iOException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not read file ");
      stringBuilder.append(paramString);
      stringBuilder.append(": ");
      stringBuilder.append(iOException);
      throw new ParameterException(stringBuilder.toString());
    } 
  }
  
  private char[] readPassword(String paramString, boolean paramBoolean) {
    Console console1 = getConsole();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(": ");
    console1.print(stringBuilder.toString());
    return getConsole().readPassword(paramBoolean);
  }
  
  private String s(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < paramInt; i++)
      stringBuilder.append(" "); 
    return stringBuilder.toString();
  }
  
  private String[] subArray(String[] paramArrayOfString, int paramInt) {
    int i = paramArrayOfString.length - paramInt;
    String[] arrayOfString = new String[i];
    System.arraycopy(paramArrayOfString, paramInt, arrayOfString, 0, i);
    return arrayOfString;
  }
  
  private static String trim(String paramString) {
    String str = paramString.trim();
    paramString = str;
    if (str.startsWith("\"")) {
      paramString = str;
      if (str.endsWith("\"")) {
        paramString = str;
        if (str.length() > 1)
          paramString = str.substring(1, str.length() - 1); 
      } 
    } 
    return paramString;
  }
  
  private static <T> T tryInstantiateConverter(String paramString, Class<T> paramClass) {
    if (paramClass != NoConverter.class) {
      if (paramClass == null)
        return null; 
      try {
        return (T)instantiateConverter(paramString, (Class)paramClass);
      } catch (InstantiationException|IllegalAccessException|InvocationTargetException instantiationException) {
        return null;
      } 
    } 
    return null;
  }
  
  private void validateOptions() {
    if (this.helpWasSpecified)
      return; 
    if (!this.requiredFields.isEmpty()) {
      StringBuilder stringBuilder1 = new StringBuilder();
      Iterator<ParameterDescription> iterator = this.requiredFields.values().iterator();
      while (iterator.hasNext()) {
        stringBuilder1.append(((ParameterDescription)iterator.next()).getNames());
        stringBuilder1.append(" ");
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("The following ");
      stringBuilder2.append(pluralize(this.requiredFields.size(), "option is required: ", "options are required: "));
      stringBuilder2.append(stringBuilder1);
      throw new ParameterException(stringBuilder2.toString());
    } 
    ParameterDescription parameterDescription = this.mainParameterDescription;
    if (parameterDescription != null && parameterDescription.getParameter().required()) {
      if (this.mainParameterDescription.isAssigned())
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Main parameters are required (\"");
      stringBuilder.append(this.mainParameterDescription.getDescription());
      stringBuilder.append("\")");
      throw new ParameterException(stringBuilder.toString());
    } 
  }
  
  private void wrapDescription(StringBuilder paramStringBuilder, int paramInt, String paramString) {
    int k = getColumnSize();
    String[] arrayOfString = paramString.split(" ");
    int j = 0;
    int i = 0;
    while (j < arrayOfString.length) {
      String str = arrayOfString[j];
      if (str.length() > k || i + 1 + str.length() <= k) {
        paramStringBuilder.append(str);
        int m = i + str.length();
        i = m;
        if (j != arrayOfString.length - 1) {
          paramStringBuilder.append(" ");
          i = m + 1;
        } 
      } else {
        paramStringBuilder.append("\n");
        paramStringBuilder.append(s(paramInt));
        paramStringBuilder.append(str);
        paramStringBuilder.append(" ");
        i = paramInt + 1 + str.length();
      } 
      j++;
    } 
  }
  
  public void addCommand(Object paramObject) {
    Parameters parameters = paramObject.getClass().getAnnotation(Parameters.class);
    if (parameters != null && (parameters.commandNames()).length > 0) {
      String[] arrayOfString = parameters.commandNames();
      int j = arrayOfString.length;
      for (int i = 0; i < j; i++)
        addCommand(arrayOfString[i], paramObject); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Trying to add command ");
    stringBuilder.append(paramObject.getClass().getName());
    stringBuilder.append(" without specifying its names in @Parameters");
    throw new ParameterException(stringBuilder.toString());
  }
  
  public void addCommand(String paramString, Object paramObject) { addCommand(paramString, paramObject, new String[0]); }
  
  public void addCommand(String paramString, Object paramObject, String... paramVarArgs) {
    JCommander jCommander = new JCommander(this.options);
    jCommander.addObject(paramObject);
    jCommander.createDescriptions();
    jCommander.setProgramName(paramString, paramVarArgs);
    paramObject = jCommander.programName;
    this.commands.put(paramObject, jCommander);
    this.aliasMap.put(new StringKey(paramString), paramObject);
    int j = paramVarArgs.length;
    int i;
    for (i = 0; i < j; i++) {
      StringKey stringKey = new StringKey(paramVarArgs[i]);
      if (!stringKey.equals(paramString)) {
        ProgramName programName1 = this.aliasMap.get(stringKey);
        if (programName1 == null || programName1.equals(paramObject)) {
          this.aliasMap.put(stringKey, paramObject);
        } else {
          paramObject = new StringBuilder();
          paramObject.append("Cannot set alias ");
          paramObject.append(stringKey);
          paramObject.append(" for ");
          paramObject.append(paramString);
          paramObject.append(" command because it has already been defined for ");
          paramObject.append(programName1.name);
          paramObject.append(" command");
          throw new ParameterException(paramObject.toString());
        } 
      } 
    } 
  }
  
  public void addConverterFactory(final IStringConverterFactory converterFactory) { addConverterInstanceFactory(new IStringConverterInstanceFactory() {
          public IStringConverter<?> getConverterInstance(Parameter param1Parameter, Class<?> param1Class, String param1String) {
            Class<? extends IStringConverter<?>> clazz = converterFactory.getConverter(param1Class);
            String str = param1String;
            if (param1String == null) {
              try {
                if ((param1Parameter.names()).length > 0) {
                  object = param1Parameter.names()[0];
                } else {
                  object = "[Main class]";
                } 
                str = (String)object;
                return (clazz != null) ? (IStringConverter)JCommander.instantiateConverter(str, (Class)clazz) : null;
              } catch (InstantiationException null) {
              
              } catch (IllegalAccessException null) {
              
              } catch (InvocationTargetException object) {}
              throw new ParameterException((Throwable)object);
            } 
            return (clazz != null) ? (IStringConverter)JCommander.instantiateConverter(str, (Class)clazz) : null;
          }
        }); }
  
  public void addConverterInstanceFactory(IStringConverterInstanceFactory paramIStringConverterInstanceFactory) { this.options.converterInstanceFactories.add(0, paramIStringConverterInstanceFactory); }
  
  public final void addObject(Object paramObject) {
    if (paramObject instanceof Iterable) {
      paramObject = ((Iterable)paramObject).iterator();
      while (paramObject.hasNext()) {
        Object object = paramObject.next();
        this.objects.add(object);
      } 
    } else if (paramObject.getClass().isArray()) {
      paramObject = paramObject;
      int j = paramObject.length;
      for (int i = 0; i < j; i++) {
        Object object = paramObject[i];
        this.objects.add(object);
      } 
    } else {
      this.objects.add(paramObject);
    } 
  }
  
  public Object convertValue(final Parameterized parameterized, Class<?> paramClass, String paramString1, String paramString2) {
    DefaultListConverter defaultListConverter2;
    Parameter parameter = parameterized.getParameter();
    if (parameter == null)
      return paramString2; 
    String str = paramString1;
    if (paramString1 == null)
      if ((parameter.names()).length > 0) {
        str = parameter.names()[0];
      } else {
        str = "[Main class]";
      }  
    if (paramClass.isAssignableFrom(List.class)) {
      defaultListConverter2 = (DefaultListConverter)tryInstantiateConverter(str, (Class)parameter.listConverter());
    } else {
      defaultListConverter2 = null;
    } 
    DefaultListConverter defaultListConverter1 = defaultListConverter2;
    if (paramClass.isAssignableFrom(List.class)) {
      defaultListConverter1 = defaultListConverter2;
      if (defaultListConverter2 == null)
        defaultListConverter1 = new DefaultListConverter(tryInstantiateConverter(null, (Class)parameter.splitter()), new IStringConverter() {
              public Object convert(String param1String) {
                Type<String> type = parameterized.findFieldGenericType();
                JCommander jCommander = JCommander.this;
                Parameterized parameterized = parameterized;
                if (type instanceof Class) {
                  type = type;
                } else {
                  type = String.class;
                } 
                return jCommander.convertValue(parameterized, (Class)type, null, param1String);
              }
            }); 
    } 
    Object object1 = defaultListConverter1;
    if (defaultListConverter1 == null)
      object1 = tryInstantiateConverter(str, (Class)parameter.converter()); 
    Object<?> object = (Object<?>)object1;
    if (object1 == null)
      object = (Object<?>)findConverterInstance(parameter, paramClass, str); 
    object1 = object;
    if (object == null) {
      object1 = object;
      if (paramClass.isEnum())
        object1 = new EnumConverter(str, paramClass); 
    } 
    Object object2 = object1;
    if (object1 == null)
      object2 = new StringConverter(); 
    return object2.convert(paramString2);
  }
  
  public int getColumnSize() { return this.options.columnSize; }
  
  public String getCommandDescription(String paramString) {
    JCommander jCommander = findCommandByAlias(paramString);
    if (jCommander != null) {
      Parameters parameters = jCommander.getObjects().get(0).getClass().getAnnotation(Parameters.class);
      paramString = null;
      if (parameters != null) {
        ResourceBundle resourceBundle;
        String str = parameters.commandDescription();
        paramString = parameters.resourceBundle();
        if (!"".equals(paramString)) {
          resourceBundle = ResourceBundle.getBundle(paramString, Locale.getDefault());
        } else {
          resourceBundle = this.options.bundle;
        } 
        paramString = str;
        if (resourceBundle != null) {
          String str1 = parameters.commandDescriptionKey();
          paramString = str;
          if (!"".equals(str1))
            paramString = getI18nString(resourceBundle, str1, parameters.commandDescription()); 
        } 
      } 
      return paramString;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Asking description for unknown command: ");
    stringBuilder.append(paramString);
    throw new ParameterException(stringBuilder.toString());
  }
  
  public Map<String, JCommander> getCommands() {
    Map<String, JCommander> map = Maps.newLinkedHashMap();
    for (Map.Entry<ProgramName, JCommander> entry : this.commands.entrySet())
      map.put(((ProgramName)entry.getKey()).name, entry.getValue()); 
    return map;
  }
  
  public ParameterDescription getMainParameter() { return this.mainParameterDescription; }
  
  public String getMainParameterDescription() {
    if (this.descriptions == null)
      createDescriptions(); 
    Parameter parameter = this.mainParameterAnnotation;
    return (parameter != null) ? parameter.description() : null;
  }
  
  public List<Object> getObjects() { return this.objects; }
  
  public List<ParameterDescription> getParameters() { return new ArrayList<ParameterDescription>(this.fields.values()); }
  
  public String getParsedAlias() { return this.parsedAlias; }
  
  public String getParsedCommand() { return this.parsedCommand; }
  
  public List<String> getUnknownOptions() { return this.unknownArgs; }
  
  public boolean isParameterOverwritingAllowed() { return this.options.allowParameterOverwriting; }
  
  public void parse(String... paramVarArgs) { parse(true, paramVarArgs); }
  
  public void parseWithoutValidation(String... paramVarArgs) { parse(false, paramVarArgs); }
  
  public void setAcceptUnknownOptions(boolean paramBoolean) { Options.access$602(this.options, paramBoolean); }
  
  public void setAllowAbbreviatedOptions(boolean paramBoolean) { Options.access$502(this.options, paramBoolean); }
  
  public void setAllowParameterOverwriting(boolean paramBoolean) { Options.access$1702(this.options, paramBoolean); }
  
  public void setAtFileCharset(Charset paramCharset) { Options.access$702(this.options, paramCharset); }
  
  public void setCaseSensitiveOptions(boolean paramBoolean) { Options.access$402(this.options, paramBoolean); }
  
  public void setColumnSize(int paramInt) { Options.access$1302(this.options, paramInt); }
  
  public void setDefaultProvider(IDefaultProvider paramIDefaultProvider) { Options.access$302(this.options, paramIDefaultProvider); }
  
  public final void setDescriptionsBundle(ResourceBundle paramResourceBundle) { Options.access$202(this.options, paramResourceBundle); }
  
  public void setExpandAtSign(boolean paramBoolean) { Options.access$102(this.options, paramBoolean); }
  
  public void setParameterDescriptionComparator(Comparator<? super ParameterDescription> paramComparator) { Options.access$1202(this.options, paramComparator); }
  
  public void setProgramName(String paramString) { setProgramName(paramString, new String[0]); }
  
  public void setProgramName(String paramString, String... paramVarArgs) { this.programName = new ProgramName(paramString, Arrays.asList(paramVarArgs)); }
  
  public void setVerbose(int paramInt) { Options.access$1402(this.options, paramInt); }
  
  public void usage() {
    StringBuilder stringBuilder = new StringBuilder();
    usage(stringBuilder);
    getConsole().println(stringBuilder.toString());
  }
  
  public void usage(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    usage(paramString, stringBuilder);
    getConsole().println(stringBuilder.toString());
  }
  
  public void usage(String paramString, StringBuilder paramStringBuilder) { usage(paramString, paramStringBuilder, ""); }
  
  public void usage(String paramString1, StringBuilder paramStringBuilder, String paramString2) {
    String str = getCommandDescription(paramString1);
    JCommander jCommander = findCommandByAlias(paramString1);
    if (str != null) {
      paramStringBuilder.append(paramString2);
      paramStringBuilder.append(str);
      paramStringBuilder.append("\n");
    } 
    jCommander.usage(paramStringBuilder, paramString2);
  }
  
  public void usage(StringBuilder paramStringBuilder) { usage(paramStringBuilder, ""); }
  
  public void usage(StringBuilder paramStringBuilder, String paramString) {
    if (this.descriptions == null)
      createDescriptions(); 
    int j = this.commands.isEmpty() ^ true;
    boolean bool = this.descriptions.isEmpty();
    int k = paramString.length() + 6;
    Object<?> object = (Object<?>)this.programName;
    if (object != null) {
      String str = object.getDisplayName();
    } else {
      object = (Object<?>)"<main class>";
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("Usage: ");
    stringBuilder.append((String)object);
    if ((bool ^ true) != 0)
      stringBuilder.append(" [options]"); 
    if (j != 0) {
      stringBuilder.append(paramString);
      stringBuilder.append(" [command] [command options]");
    } 
    if (this.mainParameterDescription != null) {
      stringBuilder.append(" ");
      stringBuilder.append(this.mainParameterDescription.getDescription());
    } 
    wrapDescription(paramStringBuilder, k, stringBuilder.toString());
    paramStringBuilder.append("\n");
    object = (Object<?>)Lists.newArrayList();
    null = this.fields.values().iterator();
    int i = 0;
    while (null.hasNext()) {
      ParameterDescription parameterDescription = null.next();
      if (!parameterDescription.getParameter().hidden()) {
        object.add(parameterDescription);
        int m = parameterDescription.getNames().length() + 2;
        if (m > i)
          i = m; 
      } 
    } 
    Collections.sort((List)object, getParameterDescriptionComparator());
    if (object.size() > 0) {
      paramStringBuilder.append(paramString);
      paramStringBuilder.append("  Options:\n");
    } 
    for (ParameterDescription parameterDescription : object) {
      WrappedParameter wrappedParameter = parameterDescription.getParameter();
      paramStringBuilder.append(paramString);
      StringBuilder stringBuilder1 = new StringBuilder();
      object = (Object<?>)"  ";
      stringBuilder1.append("  ");
      if (wrappedParameter.required())
        object = (Object<?>)"* "; 
      stringBuilder1.append((String)object);
      stringBuilder1.append(parameterDescription.getNames());
      stringBuilder1.append("\n");
      paramStringBuilder.append(stringBuilder1.toString());
      object = (Object<?>)new StringBuilder();
      object.append(s(k));
      object.append(parameterDescription.getDescription());
      wrapDescription(paramStringBuilder, k, object.toString());
      object = (Object<?>)parameterDescription.getDefault();
      if (parameterDescription.isDynamicParameter()) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("\n");
        stringBuilder1.append(s(k));
        paramStringBuilder.append(stringBuilder1.toString());
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Syntax: ");
        stringBuilder1.append(wrappedParameter.names()[0]);
        stringBuilder1.append("key");
        stringBuilder1.append(wrappedParameter.getAssignment());
        stringBuilder1.append("value");
        paramStringBuilder.append(stringBuilder1.toString());
      } 
      if (object != null && !parameterDescription.isHelp()) {
        if (Strings.isStringEmpty(object.toString())) {
          object = (Object<?>)"<empty string>";
        } else {
          object = (Object<?>)object.toString();
        } 
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("\n");
        stringBuilder1.append(s(k));
        paramStringBuilder.append(stringBuilder1.toString());
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Default: ");
        if (wrappedParameter.password())
          object = (Object<?>)"********"; 
        stringBuilder1.append((String)object);
        paramStringBuilder.append(stringBuilder1.toString());
      } 
      object = (Object<?>)parameterDescription.getParameterized().getType();
      if (object.isEnum()) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("\n");
        stringBuilder2.append(s(k));
        paramStringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Possible Values: ");
        stringBuilder2.append(EnumSet.allOf((Class<?>)object));
        paramStringBuilder.append(stringBuilder2.toString());
      } 
      paramStringBuilder.append("\n");
    } 
    if (j != 0) {
      paramStringBuilder.append("  Commands:\n");
      for (Map.Entry<ProgramName, JCommander> entry : this.commands.entrySet()) {
        Parameters parameters = ((JCommander)entry.getValue()).getObjects().get(0).getClass().getAnnotation(Parameters.class);
        if (parameters == null || !parameters.hidden()) {
          ProgramName programName1 = (ProgramName)entry.getKey();
          String str1 = programName1.getDisplayName();
          String str2 = getCommandDescription(programName1.getName());
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(paramString);
          stringBuilder1.append("    ");
          stringBuilder1.append(str1);
          stringBuilder1.append("      ");
          stringBuilder1.append(str2);
          wrapDescription(paramStringBuilder, k + 6, stringBuilder1.toString());
          paramStringBuilder.append("\n");
          findCommandByAlias(programName1.getName()).usage(paramStringBuilder, "      ");
          paramStringBuilder.append("\n");
        } 
      } 
    } 
  }
  
  static class Builder {
    private String[] args = null;
    
    private JCommander jCommander = new JCommander();
    
    public Builder acceptUnknownOptions(boolean param1Boolean) {
      this.jCommander.setAcceptUnknownOptions(param1Boolean);
      return this;
    }
    
    public Builder addCommand(Object param1Object) {
      this.jCommander.addCommand(param1Object);
      return this;
    }
    
    public Builder addCommand(String param1String, Object param1Object, String... param1VarArgs) {
      this.jCommander.addCommand(param1String, param1Object, param1VarArgs);
      return this;
    }
    
    public Builder addConverterFactory(IStringConverterFactory param1IStringConverterFactory) {
      this.jCommander.addConverterFactory(param1IStringConverterFactory);
      return this;
    }
    
    public Builder addConverterInstanceFactory(IStringConverterInstanceFactory param1IStringConverterInstanceFactory) {
      this.jCommander.addConverterInstanceFactory(param1IStringConverterInstanceFactory);
      return this;
    }
    
    public Builder addObject(Object param1Object) {
      this.jCommander.addObject(param1Object);
      return this;
    }
    
    public Builder allowAbbreviatedOptions(boolean param1Boolean) {
      this.jCommander.setAllowAbbreviatedOptions(param1Boolean);
      return this;
    }
    
    public Builder allowParameterOverwriting(boolean param1Boolean) {
      this.jCommander.setAllowParameterOverwriting(param1Boolean);
      return this;
    }
    
    public Builder args(String[] param1ArrayOfString) {
      this.args = param1ArrayOfString;
      return this;
    }
    
    public Builder atFileCharset(Charset param1Charset) {
      this.jCommander.setAtFileCharset(param1Charset);
      return this;
    }
    
    public JCommander build() {
      String[] arrayOfString = this.args;
      if (arrayOfString != null)
        this.jCommander.parse(arrayOfString); 
      return this.jCommander;
    }
    
    public Builder columnSize(int param1Int) {
      this.jCommander.setColumnSize(param1Int);
      return this;
    }
    
    public Builder defaultProvider(IDefaultProvider param1IDefaultProvider) {
      this.jCommander.setDefaultProvider(param1IDefaultProvider);
      return this;
    }
    
    public Builder expandAtSign(Boolean param1Boolean) {
      this.jCommander.setExpandAtSign(param1Boolean.booleanValue());
      return this;
    }
    
    public Builder programName(String param1String) {
      this.jCommander.setProgramName(param1String);
      return this;
    }
    
    public Builder resourceBundle(ResourceBundle param1ResourceBundle) {
      this.jCommander.setDescriptionsBundle(param1ResourceBundle);
      return this;
    }
    
    public Builder verbose(int param1Int) {
      this.jCommander.setVerbose(param1Int);
      return this;
    }
  }
  
  private class DefaultVariableArity implements IVariableArity {
    private DefaultVariableArity() {}
    
    public int processVariableArity(String param1String, String[] param1ArrayOfString) {
      int i;
      for (i = 0; i < param1ArrayOfString.length && !JCommander.this.isOption(param1ArrayOfString[i]); i++);
      return i;
    }
  }
  
  private static class Options {
    private boolean acceptUnknownOptions = false;
    
    private boolean allowAbbreviatedOptions = false;
    
    private boolean allowParameterOverwriting = false;
    
    private Charset atFileCharset = Charset.defaultCharset();
    
    private ResourceBundle bundle;
    
    private boolean caseSensitiveOptions = true;
    
    private int columnSize = 79;
    
    private final List<IStringConverterInstanceFactory> converterInstanceFactories = new CopyOnWriteArrayList<IStringConverterInstanceFactory>();
    
    private IDefaultProvider defaultProvider;
    
    private boolean expandAtSign = true;
    
    private Comparator<? super ParameterDescription> parameterDescriptionComparator = new Comparator<ParameterDescription>() {
        public int compare(ParameterDescription param2ParameterDescription1, ParameterDescription param2ParameterDescription2) {
          Parameter parameter1 = param2ParameterDescription1.getParameterAnnotation();
          Parameter parameter2 = param2ParameterDescription2.getParameterAnnotation();
          return (parameter1 != null && parameter1.order() != -1 && parameter2 != null && parameter2.order() != -1) ? Integer.compare(parameter1.order(), parameter2.order()) : ((parameter1 != null && parameter1.order() != -1) ? -1 : ((parameter2 != null && parameter2.order() != -1) ? 1 : param2ParameterDescription1.getLongestName().compareTo(param2ParameterDescription2.getLongestName())));
        }
      };
    
    private int verbose = 0;
    
    private Options() {}
  }
  
  class null implements Comparator<ParameterDescription> {
    public int compare(ParameterDescription param1ParameterDescription1, ParameterDescription param1ParameterDescription2) {
      Parameter parameter1 = param1ParameterDescription1.getParameterAnnotation();
      Parameter parameter2 = param1ParameterDescription2.getParameterAnnotation();
      return (parameter1 != null && parameter1.order() != -1 && parameter2 != null && parameter2.order() != -1) ? Integer.compare(parameter1.order(), parameter2.order()) : ((parameter1 != null && parameter1.order() != -1) ? -1 : ((parameter2 != null && parameter2.order() != -1) ? 1 : param1ParameterDescription1.getLongestName().compareTo(param1ParameterDescription2.getLongestName())));
    }
  }
  
  private static final class ProgramName implements FuzzyMap.IKey {
    private final List<String> aliases;
    
    private final String name;
    
    ProgramName(String param1String, List<String> param1List) {
      this.name = param1String;
      this.aliases = param1List;
    }
    
    private String getDisplayName() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.name);
      if (!this.aliases.isEmpty()) {
        stringBuilder.append("(");
        Iterator<String> iterator = this.aliases.iterator();
        while (iterator.hasNext()) {
          stringBuilder.append(iterator.next());
          if (iterator.hasNext())
            stringBuilder.append(","); 
        } 
        stringBuilder.append(")");
      } 
      return stringBuilder.toString();
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null)
        return false; 
      if (getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      String str = this.name;
      if (str == null) {
        if (param1Object.name != null)
          return false; 
      } else if (!str.equals(param1Object.name)) {
        return false;
      } 
      return true;
    }
    
    public String getName() { return this.name; }
    
    public int hashCode() {
      int i;
      String str = this.name;
      if (str == null) {
        i = 0;
      } else {
        i = str.hashCode();
      } 
      return 31 + i;
    }
    
    public String toString() { return getDisplayName(); }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\JCommander.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */