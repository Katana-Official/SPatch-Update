package com.beust.jcommander;

import com.beust.jcommander.internal.Console;
import com.beust.jcommander.validators.NoValueValidator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ParameterDescription {
  private boolean assigned = false;
  
  private ResourceBundle bundle;
  
  private Object defaultObject;
  
  private String description;
  
  private DynamicParameter dynamicParameterAnnotation;
  
  private JCommander jCommander;
  
  private String longestName = "";
  
  private Object object;
  
  private Parameter parameterAnnotation;
  
  private Parameterized parameterized;
  
  private WrappedParameter wrappedParameter;
  
  public ParameterDescription(Object paramObject, DynamicParameter paramDynamicParameter, Parameterized paramParameterized, ResourceBundle paramResourceBundle, JCommander paramJCommander) {
    if (Map.class.isAssignableFrom(paramParameterized.getType())) {
      this.dynamicParameterAnnotation = paramDynamicParameter;
      this.wrappedParameter = new WrappedParameter(paramDynamicParameter);
      init(paramObject, paramParameterized, paramResourceBundle, paramJCommander);
      return;
    } 
    paramObject = new StringBuilder();
    paramObject.append("@DynamicParameter ");
    paramObject.append(paramParameterized.getName());
    paramObject.append(" should be of type Map but is ");
    paramObject.append(paramParameterized.getType().getName());
    throw new ParameterException(paramObject.toString());
  }
  
  public ParameterDescription(Object paramObject, Parameter paramParameter, Parameterized paramParameterized, ResourceBundle paramResourceBundle, JCommander paramJCommander) {
    this.parameterAnnotation = paramParameter;
    this.wrappedParameter = new WrappedParameter(paramParameter);
    init(paramObject, paramParameterized, paramResourceBundle, paramJCommander);
  }
  
  private boolean fieldIsSetForTheFirstTime(boolean paramBoolean) { return (!paramBoolean && !this.assigned); }
  
  private ResourceBundle findResourceBundle(Object paramObject) {
    Parameters parameters = paramObject.getClass().getAnnotation(Parameters.class);
    if (parameters != null && !isEmpty(parameters.resourceBundle()))
      return ResourceBundle.getBundle(parameters.resourceBundle(), Locale.getDefault()); 
    paramObject = paramObject.getClass().getAnnotation(ResourceBundle.class);
    return (paramObject != null && !isEmpty(paramObject.value())) ? ResourceBundle.getBundle(paramObject.value(), Locale.getDefault()) : null;
  }
  
  private List<SubParameterIndex> findSubParameters(Class<?> paramClass) {
    ArrayList<SubParameterIndex> arrayList = new ArrayList();
    for (Field field : paramClass.getDeclaredFields()) {
      SubParameter subParameter = (SubParameter)field.getAnnotation((Class)SubParameter.class);
      if (subParameter != null)
        arrayList.add(new SubParameterIndex(((SubParameter)subParameter).order(), field)); 
    } 
    return arrayList;
  }
  
  private Object handleSubParameters(String paramString, int paramInt, Class<?> paramClass, List<SubParameterIndex> paramList) { // Byte code:
    //   0: aload #4
    //   2: invokeinterface iterator : ()Ljava/util/Iterator;
    //   7: astore #5
    //   9: aload #5
    //   11: invokeinterface hasNext : ()Z
    //   16: ifeq -> 43
    //   19: aload #5
    //   21: invokeinterface next : ()Ljava/lang/Object;
    //   26: checkcast com/beust/jcommander/ParameterDescription$SubParameterIndex
    //   29: astore #4
    //   31: aload #4
    //   33: getfield order : I
    //   36: iload_2
    //   37: if_icmpne -> 9
    //   40: goto -> 46
    //   43: aconst_null
    //   44: astore #4
    //   46: aload #4
    //   48: ifnull -> 157
    //   51: aload_0
    //   52: getfield parameterized : Lcom/beust/jcommander/Parameterized;
    //   55: aload_0
    //   56: getfield object : Ljava/lang/Object;
    //   59: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   62: astore #6
    //   64: aload #6
    //   66: astore #5
    //   68: aload #6
    //   70: ifnonnull -> 92
    //   73: aload_3
    //   74: invokevirtual newInstance : ()Ljava/lang/Object;
    //   77: astore #5
    //   79: aload_0
    //   80: getfield parameterized : Lcom/beust/jcommander/Parameterized;
    //   83: aload_0
    //   84: getfield object : Ljava/lang/Object;
    //   87: aload #5
    //   89: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   92: aload_0
    //   93: getfield wrappedParameter : Lcom/beust/jcommander/WrappedParameter;
    //   96: aload_0
    //   97: getfield parameterized : Lcom/beust/jcommander/Parameterized;
    //   100: aload #5
    //   102: aload_1
    //   103: aload #4
    //   105: getfield field : Ljava/lang/reflect/Field;
    //   108: invokevirtual addValue : (Lcom/beust/jcommander/Parameterized;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Field;)V
    //   111: aload #5
    //   113: areturn
    //   114: astore_1
    //   115: goto -> 119
    //   118: astore_1
    //   119: new java/lang/StringBuilder
    //   122: dup
    //   123: invokespecial <init> : ()V
    //   126: astore #4
    //   128: aload #4
    //   130: ldc 'Couldn't instantiate '
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload #4
    //   138: aload_3
    //   139: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: new com/beust/jcommander/ParameterException
    //   146: dup
    //   147: aload #4
    //   149: invokevirtual toString : ()Ljava/lang/String;
    //   152: aload_1
    //   153: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   156: athrow
    //   157: new java/lang/StringBuilder
    //   160: dup
    //   161: invokespecial <init> : ()V
    //   164: astore #4
    //   166: aload #4
    //   168: ldc 'Couldn't find where to assign parameter '
    //   170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: aload #4
    //   176: aload_1
    //   177: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   180: pop
    //   181: aload #4
    //   183: ldc ' in '
    //   185: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: aload #4
    //   191: aload_3
    //   192: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: new com/beust/jcommander/ParameterException
    //   199: dup
    //   200: aload #4
    //   202: invokevirtual toString : ()Ljava/lang/String;
    //   205: invokespecial <init> : (Ljava/lang/String;)V
    //   208: athrow
    // Exception table:
    //   from	to	target	type
    //   73	92	118	java/lang/InstantiationException
    //   73	92	114	java/lang/IllegalAccessException
    //   92	111	118	java/lang/InstantiationException
    //   92	111	114	java/lang/IllegalAccessException }
  
  private void init(Object paramObject, Parameterized paramParameterized, ResourceBundle paramResourceBundle, JCommander paramJCommander) {
    this.object = paramObject;
    this.parameterized = paramParameterized;
    this.bundle = paramResourceBundle;
    if (paramResourceBundle == null)
      this.bundle = findResourceBundle(paramObject); 
    this.jCommander = paramJCommander;
    if (this.parameterAnnotation != null) {
      String str;
      if (Enum.class.isAssignableFrom(paramParameterized.getType()) && this.parameterAnnotation.description().isEmpty()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Options: ");
        stringBuilder.append(EnumSet.allOf(paramParameterized.getType()));
        str = stringBuilder.toString();
      } else {
        str = this.parameterAnnotation.description();
      } 
      initDescription(str, this.parameterAnnotation.descriptionKey(), this.parameterAnnotation.names());
    } else {
      DynamicParameter dynamicParameter = this.dynamicParameterAnnotation;
      if (dynamicParameter != null) {
        initDescription(dynamicParameter.description(), this.dynamicParameterAnnotation.descriptionKey(), this.dynamicParameterAnnotation.names());
      } else {
        throw new AssertionError("Shound never happen");
      } 
    } 
    try {
      this.defaultObject = paramParameterized.get(paramObject);
    } catch (Exception exception) {}
    if (this.defaultObject != null) {
      paramObject = this.parameterAnnotation;
      if (paramObject != null)
        validateDefaultValues(paramObject.names()); 
    } 
  }
  
  private void initDescription(String paramString1, String paramString2, String[] paramArrayOfString) {
    this.description = paramString1;
    if (!"".equals(paramString2)) {
      ResourceBundle resourceBundle = this.bundle;
      if (resourceBundle != null)
        this.description = resourceBundle.getString(paramString2); 
    } 
    int j = paramArrayOfString.length;
    int i;
    for (i = 0; i < j; i++) {
      paramString1 = paramArrayOfString[i];
      if (paramString1.length() > this.longestName.length())
        this.longestName = paramString1; 
    } 
  }
  
  private boolean isEmpty(String paramString) { return (paramString == null || "".equals(paramString)); }
  
  private boolean isMultiOption() {
    Class<?> clazz = this.parameterized.getType();
    return (clazz.equals(List.class) || clazz.equals(Set.class) || this.parameterized.isDynamicParameter());
  }
  
  private Collection<Object> newCollection(Class<?> paramClass) {
    if (SortedSet.class.isAssignableFrom(paramClass))
      return new TreeSet(); 
    if (LinkedHashSet.class.isAssignableFrom(paramClass))
      return new LinkedHashSet(); 
    if (Set.class.isAssignableFrom(paramClass))
      return new HashSet(); 
    if (List.class.isAssignableFrom(paramClass))
      return new ArrayList(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Parameters of Collection type '");
    stringBuilder.append(paramClass.getSimpleName());
    stringBuilder.append("' are not supported. Please use List or Set instead.");
    throw new ParameterException(stringBuilder.toString());
  }
  
  private static void p(String paramString) {
    if (System.getProperty("jcommander.debug") != null) {
      Console console = JCommander.getConsole();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[ParameterDescription] ");
      stringBuilder.append(paramString);
      console.println(stringBuilder.toString());
    } 
  }
  
  private void validateDefaultValues(String[] paramArrayOfString) {
    String str;
    if (paramArrayOfString.length > 0) {
      str = paramArrayOfString[0];
    } else {
      str = "";
    } 
    validateValueParameter(str, this.defaultObject);
  }
  
  public static void validateParameter(ParameterDescription paramParameterDescription, Class<? extends IParameterValidator> paramClass, String paramString1, String paramString2) { // Byte code:
    //   0: aload_1
    //   1: ldc_w com/beust/jcommander/validators/NoValidator
    //   4: if_acmpeq -> 72
    //   7: new java/lang/StringBuilder
    //   10: dup
    //   11: invokespecial <init> : ()V
    //   14: astore #4
    //   16: aload #4
    //   18: ldc_w 'Validating parameter:'
    //   21: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload #4
    //   27: aload_2
    //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: pop
    //   32: aload #4
    //   34: ldc_w ' value:'
    //   37: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload #4
    //   43: aload_3
    //   44: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload #4
    //   50: ldc_w ' validator:'
    //   53: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: aload #4
    //   59: aload_1
    //   60: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: aload #4
    //   66: invokevirtual toString : ()Ljava/lang/String;
    //   69: invokestatic p : (Ljava/lang/String;)V
    //   72: aload_1
    //   73: invokevirtual newInstance : ()Ljava/lang/Object;
    //   76: checkcast com/beust/jcommander/IParameterValidator
    //   79: aload_2
    //   80: aload_3
    //   81: invokeinterface validate : (Ljava/lang/String;Ljava/lang/String;)V
    //   86: ldc_w com/beust/jcommander/IParameterValidator2
    //   89: aload_1
    //   90: invokevirtual isAssignableFrom : (Ljava/lang/Class;)Z
    //   93: ifeq -> 111
    //   96: aload_1
    //   97: invokevirtual newInstance : ()Ljava/lang/Object;
    //   100: checkcast com/beust/jcommander/IParameterValidator2
    //   103: aload_2
    //   104: aload_3
    //   105: aload_0
    //   106: invokeinterface validate : (Ljava/lang/String;Ljava/lang/String;Lcom/beust/jcommander/ParameterDescription;)V
    //   111: return
    //   112: astore_0
    //   113: new com/beust/jcommander/ParameterException
    //   116: dup
    //   117: aload_0
    //   118: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   121: athrow
    //   122: astore_0
    //   123: aload_0
    //   124: athrow
    //   125: astore_0
    //   126: goto -> 130
    //   129: astore_0
    //   130: new java/lang/StringBuilder
    //   133: dup
    //   134: invokespecial <init> : ()V
    //   137: astore_1
    //   138: aload_1
    //   139: ldc_w 'Can't instantiate validator:'
    //   142: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload_1
    //   147: aload_0
    //   148: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: new com/beust/jcommander/ParameterException
    //   155: dup
    //   156: aload_1
    //   157: invokevirtual toString : ()Ljava/lang/String;
    //   160: invokespecial <init> : (Ljava/lang/String;)V
    //   163: athrow
    // Exception table:
    //   from	to	target	type
    //   7	72	129	java/lang/InstantiationException
    //   7	72	125	java/lang/IllegalAccessException
    //   7	72	122	com/beust/jcommander/ParameterException
    //   7	72	112	java/lang/Exception
    //   72	111	129	java/lang/InstantiationException
    //   72	111	125	java/lang/IllegalAccessException
    //   72	111	122	com/beust/jcommander/ParameterException
    //   72	111	112	java/lang/Exception }
  
  private void validateParameter(String paramString1, String paramString2) {
    Class<? extends IParameterValidator> clazz = this.wrappedParameter.validateWith();
    if (clazz != null)
      validateParameter(this, clazz, paramString1, paramString2); 
  }
  
  public static void validateValueParameter(Class<? extends IValueValidator> paramClass, String paramString, Object paramObject) {
    StringBuilder stringBuilder;
    if (paramClass != NoValueValidator.class) {
      try {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Validating value parameter:");
        stringBuilder1.append(paramString);
        stringBuilder1.append(" value:");
        stringBuilder1.append(paramObject);
        stringBuilder1.append(" validator:");
        stringBuilder1.append(paramClass);
        p(stringBuilder1.toString());
        ((IValueValidator<Object>)paramClass.newInstance()).validate(paramString, paramObject);
        return;
      } catch (InstantiationException null) {
      
      } catch (IllegalAccessException object1) {}
      stringBuilder = new StringBuilder();
      stringBuilder.append("Can't instantiate validator:");
      stringBuilder.append(object1);
      throw new ParameterException(stringBuilder.toString());
    } 
    ((IValueValidator<Object>)object1.newInstance()).validate((String)stringBuilder, paramObject);
  }
  
  Object addValue(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt) {
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Adding ");
    if (paramBoolean1) {
      str = "default ";
    } else {
      str = "";
    } 
    stringBuilder2.append(str);
    stringBuilder2.append("value:");
    stringBuilder2.append(paramString2);
    stringBuilder2.append(" to parameter:");
    stringBuilder2.append(this.parameterized.getName());
    p(stringBuilder2.toString());
    String str = paramString1;
    if (paramString1 == null)
      str = this.wrappedParameter.names()[0]; 
    if ((paramInt != 0 || !this.assigned || isMultiOption() || this.jCommander.isParameterOverwritingAllowed()) && !isNonOverwritableForced()) {
      Object object1;
      if (paramBoolean2)
        validateParameter(str, paramString2); 
      Class<?> clazz = this.parameterized.getType();
      Object object2 = this.jCommander.convertValue(getParameterized(), getParameterized().getType(), str, paramString2);
      if (paramBoolean2)
        validateValueParameter(str, object2); 
      if (Collection.class.isAssignableFrom(clazz)) {
        object1 = this.parameterized.get(this.object);
        if (object1 == null || fieldIsSetForTheFirstTime(paramBoolean1)) {
          object1 = newCollection(clazz);
          this.parameterized.set(this.object, object1);
        } 
        if (object2 instanceof Collection) {
          object1.addAll((Collection)object2);
        } else {
          object1.add(object2);
        } 
      } else {
        object1 = findSubParameters(clazz);
        if (!object1.isEmpty()) {
          Object object3 = handleSubParameters(paramString2, paramInt, clazz, (List<SubParameterIndex>)object1);
        } else {
          this.wrappedParameter.addValue(this.parameterized, this.object, object2);
          object1 = object2;
        } 
      } 
      if (!paramBoolean1)
        this.assigned = true; 
      return object1;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Can only specify option ");
    stringBuilder1.append(str);
    stringBuilder1.append(" once.");
    throw new ParameterException(stringBuilder1.toString());
  }
  
  public void addValue(String paramString) { addValue(paramString, false); }
  
  public void addValue(String paramString, boolean paramBoolean) { addValue(null, paramString, paramBoolean, true, -1); }
  
  public Object getDefault() { return this.defaultObject; }
  
  public String getDescription() { return this.description; }
  
  public String getLongestName() { return this.longestName; }
  
  public String getNames() {
    StringBuilder stringBuilder = new StringBuilder();
    String[] arrayOfString = this.wrappedParameter.names();
    for (int i = 0; i < arrayOfString.length; i++) {
      if (i > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(arrayOfString[i]);
    } 
    return stringBuilder.toString();
  }
  
  public Object getObject() { return this.object; }
  
  public WrappedParameter getParameter() { return this.wrappedParameter; }
  
  public Parameter getParameterAnnotation() { return this.parameterAnnotation; }
  
  public Parameterized getParameterized() { return this.parameterized; }
  
  public boolean isAssigned() { return this.assigned; }
  
  public boolean isDynamicParameter() { return (this.dynamicParameterAnnotation != null); }
  
  public boolean isHelp() { return this.wrappedParameter.isHelp(); }
  
  public boolean isNonOverwritableForced() { return this.wrappedParameter.isNonOverwritableForced(); }
  
  public void setAssigned(boolean paramBoolean) { this.assigned = paramBoolean; }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[ParameterDescription ");
    stringBuilder.append(this.parameterized.getName());
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  void validateValueParameter(String paramString, Object paramObject) {
    Class<? extends IValueValidator> clazz = this.wrappedParameter.validateValueWith();
    if (clazz != null)
      validateValueParameter(clazz, paramString, paramObject); 
  }
  
  class SubParameterIndex {
    Field field;
    
    int order = -1;
    
    public SubParameterIndex(int param1Int, Field param1Field) {
      this.order = param1Int;
      this.field = param1Field;
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\ParameterDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */