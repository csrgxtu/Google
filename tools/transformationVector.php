#!/usr/bin/php
<?php
/**
 * Author: Archer
 * Date: 09/Apr/2014
 * File: transformationVector.php
 * Des: well, I tried to use Perl do the work, but Perl arent working
 * as I wish, for Perl not good at network shit, so I turned to the
 * PHP, and hope it will do the work. well, in this script, i am
 * gonna build the transformation vector for a page in that directory
 * for building transformation matrix, will be done in other scripts
 * I dont know, for a workable system, all the work should be auto
 * maticly done, but here seems I do it by hand, maybe one day I will
 * implement all the task by only one language and make it work auto
 *
 * Produced By CSRGXTU
 */

//echo md5("http://blogxtu.zapto.org/?author=2");
//buildVector("../tmp/crawl-blogxtu.zapto.org/");
if (count($argv) != 3) {
  echo "Usage: transformationVector.php <htmlFile> <pageDir>\n";
  exit(1);
}

main($argv[1], $argv[2]);

/**
 * main
 * make all functions work together
 *
 * @param $htmlFile
 */
function main($htmlFile, $pageDir) {
  $baseUrl = getBaseUrl($htmlFile);
  $urls = PIPHP_GetLinksFromURL($baseUrl, $htmlFile);
  $md5Urls = toMD5($urls);
  $md5Pages = getPagesName("$pageDir");
  $vector = buildVector($md5Urls, $md5Pages);
  /*for ($i = 0; $i < count($vector); $i++) {
    echo $vector[$i] . "\n";
  }*/
  putVector2File($vector, $htmlFile.".vector");
}

/**
 * putVector2File
 * put the transformation vector into a file
 *
 * @param $vector
 * @param $outFile
 */
function putVector2File($vector, $outFile) {
  $str = implode("\n", $vector);
  file_put_contents($outFile, $str);
}

/**
 * buildVector
 * build vector according to the md5 urls and md5 pages
 *
 * @param $md5Urls
 * @param $md5Pages
 * @return $trans
 */
function buildVector($md5Urls, $md5Pages) {
  $vector = array();
  
  // foreach page, if in url, then the value will be 1/n
  $outDegree = 0;
  for ($i = 0; $i < count($md5Pages); $i++) {
    if (in_array($md5Pages[$i], $md5Urls)) {
      //echo $md5Pages[$i] . "\n";
      $outDegree++;
    }
  }
  
  for ($i = 0; $i < count($md5Pages); $i++) {
    if (in_array($md5Pages[$i], $md5Urls)) {
      $vector[$i] = 1 / $outDegree;
    } else {
      $vector[$i] = 0.0;
    }
  }
  
  return $vector;
}

/**
 * toMD5
 * convert all elements in an array into MD5 format, easy to compare
 * in the future
 *
 * @param array
 * @return array
 */
function toMD5($arr) {
  $res = array();
  
  for ($i = 0; $i < count($arr); $i++) {
    $res[$i] = md5($arr[$i]);
  }
  
  return $res;
}

/**
 * getPagesName
 * get all the md5 pages name from the directory, will used with the
 * md5 urls retrieved from the html page.
 *
 * @param $dir
 * @return $pages 
 */
function getPagesName($dir) {
  $files = glob($dir."*.content");
  $pages = array();
  
  for ($i = 0; $i < count($files); $i++) {
    $pages[$i] = substr($files[$i], -45);
    $pages[$i] = substr($pages[$i], 0, 32);
  } 
  
  return $pages;
}

/**
 * getBaseUrl
 * get the base url according to the htmlFile you give, for there
 * always have relative or abs urls, so to make things keep the
 * same, I use abs urls in my project, all the data crawled is
 * stored by the md5 checksum of he abs url, so to build the
 * transformation matrix, I need to which url is which html file
 *
 * @param $htmlFile
 * @return $baseUrl
 */
function getBaseUrl($htmlFile) {
  // get the head file
  $headFile = str_replace("content", "head", $htmlFile);
  
  // read file into array
  $lines = file($headFile);
  if ($lines == false) {
    echo "Fatal Error: cant read $headFile\n";
    exit(1);
  }
  
  // last line
  $lastLine = $lines[count($lines) -1];
  
  // rv trailling newline
  $lastLine = trim($lastLine);
  
  // get base url
  $baseUrl = str_replace("Url: ", "", $lastLine);
  
  return $baseUrl;
}

/**
 * PIPHP_GetLinksFromURL
 * the plugins from plugin php, will do the work for me, I wont write
 * the code to parse the html. the original code need an Url as param
 * to work with my needs in this situation, I changed ti to accept a
 * html file name as param, for all the html file are stored in the
 * hard drive.
 *
 * @param $baseUrl
 * @param $htmlFile
 * @return array of urls
 */
function PIPHP_GetLinksFromURL($baseUrl, $htmlFile)
{
   // Plug-in 22: get Links From URL
   //
   // This plug-in accepts the URL or a web page and returns
   // an array of all the links found in it. The argument
   // required is:
   //
   //    $page: The web site's main URL

   $contents = @file_get_contents($htmlFile);
   if (!$contents) return NULL;
   
   $urls    = array();
   $dom     = new domdocument();
   @$dom    ->loadhtml($contents);
   $xpath   = new domxpath($dom);
   $hrefs   = $xpath->evaluate("/html/body//a");

   for ($j = 0 ; $j < $hrefs->length ; $j++)
      $urls[$j] = PIPHP_RelToAbsURL($baseUrl,
         $hrefs->item($j)->getAttribute('href'));

   return $urls;
}

// The below function is repeated here to ensure that it's
// available to the main function which relies on it

function PIPHP_RelToAbsURL($page, $url)
{
   // Plug-in 21: Relative To Absolute URL
   // This plug-in accepts the absolute URL of a web page
   // and a link featured within that page. The link is then
   // turned into an absolute URL which can be independently
   // accessed. Only applies to http:// URLs. Arguments are:
   //    $page: The web page containing the URL
   //    $url:  The URL to convert to absolute

   if (substr($page, 0, 7) != "http://") return $url;
   
   $parse = parse_url($page);
   $root  = $parse['scheme'] . "://" . $parse['host'];
   $p     = strrpos(substr($page, 7), '/');
   
   if ($p) $base = substr($page, 0, $p + 8);
   else $base = "$page/";
   
   if (substr($url, 0, 1) == '/')           $url = $root . $url;
   elseif (substr($url, 0, 7) != "http://") $url = $base . $url;
   
   return $url;
}
?>
