//*******************************************************************
// Author: Archer Reilly
// Date: 15/Mar/2014
// File: UrlFilter.java
// Des: this class is used to solve the untidy url problems, since
//  urls get from html source file is not standard, some of it isnt
//  a url at all, some point to a resource that isnt a html file,
//  but a large damn file that Java HttpClient cant handle it well.
//  I think, for efficiency problem, I cant download all that shit
//  plus some of it cant be indexed, so this class is used to filter
//  all the possible problems.
//
// Produced By CSRGXTU.
//*******************************************************************

import org.apache.commons.validator.UrlValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlFilter {
  // url is the url that gonna to be processed
  private String url = null;


  /**
   * Constructor
   *
   * @param String
   */
  public UrlFilter(String url) {
    // check out the parameter
    if (url == null) {
      throw new IllegalArgumentException("UrlFilter:Constructor: "
        + "parameter not set correctly!");
    }

    this.url = url;
  }

  /**
   * filter, method that do kinds of test
   *
   * @return boolean
   */
  public boolean filter() {
    if (this.isEmpty()) {
      //System.out.println("URL is empty");
      return false;
    }

    if (this.isStandard()) {
      //System.out.println("URL is Standard");
      //return true;
    } else {
      //System.out.println("URL is not Standard");
      return false;
    }

    if (this.isImage()) {
      //System.out.println("URL is Image");
      return false;
    }

    if (this.isCompressFile()) {
      //System.out.println("URL is Compress file");
      return false;
    }

    if (this.isDocument()) {
      //System.out.println("URL is Document");
      return false;
    }

    if (this.isPHP()) {
      //System.out.println("URL is PHP");
      return true;
    }

    if (this.isStatic()) {
      //System.out.println("URL is static html file");
      return true;
    }

    if (this.isASP()) {
      //System.out.println("URL is ASP file");
      return true;
    }

    if (this.isMedia()) {
      //System.out.println("URL is media");
      return false;
    }

    if (this.isJSP()) {
      //System.out.println("URL is JSP file");
      return true;
    }

    return true;
  }

  /**
   * isEmpty, helper method check if url is empty String
   *
   * @return boolean
   */
  private boolean isEmpty() {
    if (this.url == null || this.url == "") {
      return true;
    } else {
      return false;
    }
  }

  /**
   * isStandard, helper method check if url is a standar http url
   *
   * @return boolean
   */
  private boolean isStandard() {
    // apaches Validator can do u a favour
    UrlValidator urlValidator = new UrlValidator();

    if (urlValidator.isValid(this.url)) {
      return true;
    } else {
      return false;
    }  
  }

  /**
   * isImage, helper method check if url is a jpg, png, gif, jpeg
   *
   * @return boolean
   */
  private boolean isImage() {
    // use extension do the check, also u can request the content
    // head, and check in the head, ubt is expensive
    String jpgPattern = "(.*\\.jpg$)";
    String pngPattern = "(.*\\.png$)";
    String gifPattern = "(.*\\.gif$)";
    String jpegPattern = "(.*\\.jpeg$)";

    Pattern jpg = Pattern.compile(jpgPattern);
    Pattern png = Pattern.compile(pngPattern);
    Pattern gif = Pattern.compile(gifPattern);
    Pattern jpeg = Pattern.compile(jpegPattern);

    Matcher jpgMatcher = jpg.matcher(this.url);
    if (jpgMatcher.find()) {
      return true;
    }

    Matcher pngMatcher = png.matcher(this.url);
    if (pngMatcher.find()) {
      return true;
    }

    Matcher gifMatcher = gif.matcher(this.url);
    if (gifMatcher.find()) {
      return true;
    }

    Matcher jpegMatcher = jpeg.matcher(this.url);
    if (jpegMatcher.find()) {
      return true;
    }

    // all upper test passed, not contain
    return false;
  }

  /**
   * isCompressFile, helper method check if url is a tar tar.gz tgz
   *  zip rar etc file.
   *
   * @return boolean
   */
  private boolean isCompressFile() {
    String tarPattern = "(.*\\.tar$)";
    String targzPattern = "(.*\\.tar\\.gz$)";
    String gzPattern = "(.*\\.gz$)";
    String tgzPattern = "(.*\\.tgz$)";
    String bz2Pattern = "(.*\\.bz2$)";
    String zipPattern = "(.*\\.zip$)";
    String rarPattern = "(.*\\.rar$)";
    String qzPattern = "(.*\\.7z$)";  // 7zPattern qzPattern
    String tarbz2Pattern = "(.*\\.tar\\.bz2$)";
    String tarZPattern = "(.*\\.tarZ$)";

    String apkPattern = "(.*\\.apk$)";
    String exePattern = "(.*\\.exe$)";
    String msiPattern = "(.*\\.msi$)";
    String dllPattern = "(.*\\.dll$)";
    String debPattern = "(.*\\.deb$)";
    String rpmPattern = "(.*\\.rpm$)";
    String dmgPattern = "(.*\\.dmg$)";
    String isoPattern = "(.*\\.iso$)";
    String ISOPattern = "(.*\\.ISO$)";

    Pattern tar = Pattern.compile(tarPattern);
    Pattern targz = Pattern.compile(targzPattern);
    Pattern gz = Pattern.compile(gzPattern);
    Pattern tgz = Pattern.compile(tgzPattern);
    Pattern bz2 = Pattern.compile(bz2Pattern);
    Pattern zip = Pattern.compile(zipPattern);
    Pattern rar = Pattern.compile(rarPattern);
    Pattern qz = Pattern.compile(qzPattern);
    Pattern tarbz2 = Pattern.compile(tarbz2Pattern);
    Pattern tarZ = Pattern.compile(tarZPattern);

    Pattern apk = Pattern.compile(apkPattern);
    Pattern exe = Pattern.compile(exePattern);
    Pattern msi = Pattern.compile(msiPattern);
    Pattern dll = Pattern.compile(dllPattern);
    Pattern deb = Pattern.compile(debPattern);
    Pattern rpm = Pattern.compile(rpmPattern);
    Pattern dmg = Pattern.compile(dmgPattern);
    Pattern iso = Pattern.compile(isoPattern);
    Pattern ISO = Pattern.compile(ISOPattern);

    Matcher tarMatcher = tar.matcher(this.url);
    if (tarMatcher.find()) {
      return true;
    }
    Matcher targzMatcher = targz.matcher(this.url);
    if (targzMatcher.find()) {
      return true;
    }
    Matcher gzMatcher = gz.matcher(this.url);
    if (gzMatcher.find()) {
      return true;
    }
    Matcher tgzMatcher = tgz.matcher(this.url);
    if (tgzMatcher.find()) {
      return true;
    }
    Matcher bz2Matcher = bz2.matcher(this.url);
    if (bz2Matcher.find()) {
      return true;
    }
    Matcher zipMatcher = zip.matcher(this.url);
    if (zipMatcher.find()) {
      return true;
    }
    Matcher rarMatcher = rar.matcher(this.url);
    if (rarMatcher.find()) {
      return true;
    }
    Matcher qzMatcher = qz.matcher(this.url);
    if (qzMatcher.find()) {
      return true;
    }
    Matcher tarbz2Matcher = tarbz2.matcher(this.url);
    if (tarbz2Matcher.find()) {
      return true;
    }
    Matcher tarZMatcher = tarZ.matcher(this.url);
    if (tarZMatcher.find()) {
      return true;
    }

    Matcher apkMatcher = apk.matcher(this.url);
    if (apkMatcher.find()) {
      return true;
    }
    Matcher exeMatcher = exe.matcher(this.url);
    if (exeMatcher.find()) {
      return true;
    }
    Matcher msiMatcher = msi.matcher(this.url);
    if (msiMatcher.find()) {
      return true;
    }
    Matcher dllMatcher = dll.matcher(this.url);
    if (dllMatcher.find()) {
      return true;
    }
    Matcher debMatcher = deb.matcher(this.url);
    if (debMatcher.find()) {
      return true;
    }
    Matcher rpmMatcher = rpm.matcher(this.url);
    if (rpmMatcher.find()) {
      return true;
    }
    Matcher dmgMatcher = dmg.matcher(this.url);
    if (dmgMatcher.find()) {
      return true;
    }
    Matcher isoMatcher = iso.matcher(this.url);
    if (isoMatcher.find()) {
      return true;
    }
    Matcher ISOMatcher = ISO.matcher(this.url);
    if (ISOMatcher.find()) {
      return true;
    }

    // all test passed
    return false;
  }

  /**
   * isDocument, helper method check if url is a documentation file,
   *  pdf, doc, docx, txt etc
   *
   * @return boolean
   */
  private boolean isDocument() {
    String pdfPattern = "(.*\\.pdf$)";
    String docPattern = "(.*\\.doc$)";
    String docxPattern = "(.*\\.docx$)";
    String txtPattern = "(.*\\.txt)";

    Pattern pdf = Pattern.compile(pdfPattern);
    Pattern doc = Pattern.compile(docPattern);
    Pattern docx = Pattern.compile(docxPattern);
    Pattern txt = Pattern.compile(txtPattern);

    Matcher pdfMatcher = pdf.matcher(this.url);
    if (pdfMatcher.find()) {
      return true;
    }
    Matcher docMatcher = doc.matcher(this.url);
    if (docMatcher.find()) {
      return true;
    }
    Matcher docxMatcher = docx.matcher(this.url);
    if (docxMatcher.find()) {
      return true;
    }
    Matcher txtMatcher = txt.matcher(this.url); 
    if (txtMatcher.find()) {
      return true;
    }

    return false;
  }

  /**
   * isPHP, helper method check if url is PHP implemented.
   *
   * @return boolean
   */
  private boolean isPHP() {
    String phpPattern = "(.*\\.php$)";
    String PHPPattern = "(.*\\.PHP$)";
    String PhpPattern = "(.*\\.Php$)";

    Pattern php = Pattern.compile(phpPattern);
    Pattern PHP = Pattern.compile(PHPPattern);
    Pattern Php = Pattern.compile(PhpPattern);

    Matcher phpMatcher = php.matcher(this.url);
    if (phpMatcher.find()) {
      return true;
    }
    Matcher PHPMatcher = PHP.matcher(this.url);
    if (PHPMatcher.find()) {
      return true;
    }
    Matcher PhpMatcher = Php.matcher(this.url);
    if (PhpMatcher.find()) {
      return true;
    }

    return false; 
  }

  /**
   * isStatic, helper method check if url is static html
   *
   * @return boolean
   */
  private boolean isStatic() {
    String htmlPattern = "(.*\\.html$)";
    String htmPattern = "(.*\\.htm$)";

    Pattern html = Pattern.compile(htmlPattern);
    Pattern htm = Pattern.compile(htmPattern);

    Matcher htmlMatcher = html.matcher(this.url);
    if (htmlMatcher.find()) {
      return true;
    }
    Matcher htmMatcher = htm.matcher(this.url);
    if (htmMatcher.find()) {
      return true;
    }

    return false;
  }

  /**
   * isASP, helper method check if url is ASP implemented
   *
   * @return boolean
   */
  private boolean isASP() {
    String aspPattern = "(.*\\.asp$)";

    Pattern asp = Pattern.compile(aspPattern);

    Matcher aspMatcher = asp.matcher(this.url);
    if (aspMatcher.find()) {
      return true;
    }

    return false;
  }

  /**
   * isMedia, helper method check if url is mp3, mp4, mov, flv
   * etc
   *
   * @return boolean
   */
  private boolean isMedia() {
    String mp3Pattern = "(.*\\.mp3$)";
    String acPattern = "(.*\\.ac$)";
    String wavPattern = "(.*\\.wav$)";
    String gpPattern = "(.*\\.3gp$)"; // gpPattern 3gpPattern
    String aacPattern = "(.*\\.aac$)";
    String flacPattern = "(.*\\.flac$)";
    String oggPattern = "(.*\\.ogg$)";

    String mp4Pattern = "(.*\\.mp4$)";
    String ogvPattern = "(.*\\.ogv$)";
    String movPattern = "(.*\\.mov$)";
    String flvPattern = "(.*\\.flv$)";
    String webmPattern = "(.*\\.webm$)";
    String mkvPattern = "(.*\\.mkv$)";
    String aviPattern = "(.*\\.avi$)";
    String rmPattern = "(.*\\.rm$)";
    String rmvbPattern = "(.*\\.rmvb$)";
    String mpegPattern = "(.*\\.mpeg$)";
    String qvodPattern = "(.*\\.qvod$)";

    Pattern mp3 = Pattern.compile(mp3Pattern);
    Pattern ac = Pattern.compile(acPattern);
    Pattern wav = Pattern.compile(wavPattern);
    Pattern gp = Pattern.compile(gpPattern);
    Pattern aac = Pattern.compile(aacPattern);
    Pattern flac = Pattern.compile(flacPattern);
    Pattern ogg = Pattern.compile(oggPattern);

    Pattern mp4 = Pattern.compile(mp4Pattern);
    Pattern ogv = Pattern.compile(ogvPattern);
    Pattern mov = Pattern.compile(movPattern);
    Pattern flv = Pattern.compile(flvPattern);
    Pattern webm = Pattern.compile(webmPattern);
    Pattern mkv = Pattern.compile(mkvPattern);
    Pattern avi = Pattern.compile(aviPattern);
    Pattern rm = Pattern.compile(rmPattern);
    Pattern rmvb = Pattern.compile(rmvbPattern);
    Pattern mpeg = Pattern.compile(mpegPattern);
    Pattern qvod = Pattern.compile(qvodPattern);

    Matcher mp3Matcher = mp3.matcher(this.url);
    if (mp3Matcher.find()) {
      return true;
    }
    Matcher acMatcher = ac.matcher(this.url);
    if (acMatcher.find()) {
      return true;
    }
    Matcher wavMatcher = wav.matcher(this.url);
    if (wavMatcher.find()) {
      return true;
    }
    Matcher gpMatcher = gp.matcher(this.url);
    if (gpMatcher.find()) {
      return true;
    }
    Matcher aacMatcher = aac.matcher(this.url);
    if (aacMatcher.find()) {
      return true;
    }
    Matcher flacMatcher = flac.matcher(this.url);
    if (flacMatcher.find()) {
      return true;
    }
    Matcher oggMatcher = ogg.matcher(this.url);
    if (oggMatcher.find()) {
      return true;
    }
    Matcher mp4Matcher = mp4.matcher(this.url);
    if (mp4Matcher.find()) {
      return true;
    }
    Matcher ogvMatcher = ogv.matcher(this.url);
    if (ogvMatcher.find()) {
      return true;
    }
    Matcher movMatcher = mov.matcher(this.url);
    if (movMatcher.find()) {
      return true;
    }
    Matcher flvMatcher = flv.matcher(this.url);
    if (flvMatcher.find()) {
      return true;
    }
    Matcher webmMatcher = webm.matcher(this.url);
    if (webmMatcher.find()) {
      return true;
    }
    Matcher mkvMatcher = mkv.matcher(this.url);
    if (mkvMatcher.find()) {
      return true;
    }
    Matcher aviMatcher = avi.matcher(this.url);
    if (aviMatcher.find()) {
      return true;
    }
    Matcher rmMatcher = rm.matcher(this.url);
    if (rmMatcher.find()) {
      return true;
    }
    Matcher rmvbMatcher = rmvb.matcher(this.url);
    if (rmvbMatcher.find()) {
      return true;
    }
    Matcher mpegMatcher = mpeg.matcher(this.url);
    if (mpegMatcher.find()) {
      return true;
    }
    Matcher qvodMatcher = qvod.matcher(this.url);
    if (qvodMatcher.find()) {
      return true;
    }

    return false;
  }

  /**
   * isJSP, helper method check if url is JSP implemented
   *
   * @return boolean
   */
  private boolean isJSP() {
    String jspPattern = "(.*\\.jsp$)";
    Pattern jsp = Pattern.compile(jspPattern);
    Matcher jspMatcher = jsp.matcher(this.url);
    if (jspMatcher.find()) {
      return true;
    }

    return false;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  // test
  /*public static void main(String[] args) {
    String url = "http://blogxtu.zapto.org/index.zip/";

    UrlFilter urlFilter = new UrlFilter(url);
    urlFilter.filter();
  }*/
}
