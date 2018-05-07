package com.supyuan.component.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public abstract class ValidateTools
{
  public ValidateTools()
  {
    throw new InstantiationError("工具类无法实例化");
  }
  
  public static boolean isMobile(String string)
  {
    return matcher(string, "(13|14|15|18|17){1}\\d{9}");
  }
  
  public static boolean matcher(String string, String regex)
  {
    if (string == null) {
      return false;
    }
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(string);
    return m.matches();
  }
}
