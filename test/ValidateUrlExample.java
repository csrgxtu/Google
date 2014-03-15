import org.apache.commons.validator.UrlValidator;
 
public class ValidateUrlExample{
 
  public static void main(String[] args) {
 
      UrlValidator urlValidator = new UrlValidator();
 
      //valid URL
      if (urlValidator.isValid("http://blogxtu.zapto.org/")) {
         System.out.println("url is valid");
      } else {
         System.out.println("url is invalid");
      }
 
      //invalid URL
      if (urlValidator.isValid("http://")) {
          System.out.println("url is valid");
      } else {
          System.out.println("url is invalid");
      }
 
  }
}
