import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches
{
    public static void main( String args[] ){

      // String to be scanned to find the pattern.
      String url = "http://blogxtu.zapto.org/dog.jpg";
      String urlPattern = "(.*\\.jpg$)";

      // Create a Pattern object
      Pattern r = Pattern.compile(urlPattern);

      // Now create matcher object.
      Matcher m = r.matcher(url);
      if (m.find()) {
         System.out.println("Found value: " + m.group(0) );
      } else {
         System.out.println("NO MATCH");
      }
   }
}
