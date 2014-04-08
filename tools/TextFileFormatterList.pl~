#!/usr/bin/perl
#
# Author: Archer Reilly
# Date: 04/Apr/2014
# File: TextFileFormatterList.pl
# Des: this script is used to format the plain text file into words
# list, just like StopWordFormatter.pl do.
#
# Produced By CSRGXTU

use strict;


# readFile
# read the stop words file into string
#
# @param file
# @return string
sub readFile {
  # check out the param
  if (@_ == 0) {
    print("StopWordFormater:readFile need file param\n");
    exit(1);
  }
  my $file = shift @_;
  
  # open the file
  open(FH, $file) or die("Unable To Open File: $file\n");
  
  # read the file
  my $content;
  while (<FH>) {
    $content .= $_;
  }
  
  # close
  close(FH);
  
  # debug
  #print($content);
  return lc($content);
}

# writeFile
# write the stop words array into file
#
# @param @array
# @param outputfile
# @return void
sub writeFile {
  # check out param
  if (@_ == 0) {
    print("StopWordFormatter:readFile need params\n");
    exit(1);
  }
  my $file = pop(@_);
  my @arr = @_;
  
  open(FH, ">", $file) or die("Unable To Open File: $file\n");
  foreach my $item (@arr) {
    #print($item . "\n");
    print(FH $item . "\n");
  }
  
  close(FH);  
}

# split
# split the string from readFile, first, remove whitespace, then
# split on ,
#
# @param string
# @return array
sub split {
  # check out param
  if (@_ == 0) {
    print("StopWordFormater:split need string param!\n");
    exit(1);
  }
  my $string = shift @_;
  
  #$string = trim($string);
  my @res = split(/, /, $string);
  #print(@res);
  
  return @res;
} 

# formatter
# format the original text string, replace all tabs, newlines, white lines
# with space
#
# @param string
# @return sring
sub formatter {
  # check out param
  if (@_ == 0) {
    print("TextFileFormatterList:formatter need string param\n");
    exit(1);
  }
  my $str = shift @_;
  
  $str =~ s/\s+/, /g;
  $str =~ s/[\W]+/, /g;
  $str =~ s/_/, /g;
  #print $str;
  return $str;
}

# parseArray
# parse the array, remove the empty element
#
# @param @arr
# @return @arr
sub parseArray {
  # check param
  if (@_ == 0) {
    print("TextFileFormatterList:parseArray need param\n");
    exit(1);
  }
  my @arr = @_;
  
  my @res = map {$_ =~ tr/\n//s} @arr;
  
  foreach my $item (@res) {
    print $item . "\n";
  }
  
  return @res;
}

# main
# main function that make all functions work together
#
# @param ARGV
sub main {
  if (@_ != 2) {
    print("Usage: TextFileFormatterList.pl <plainText.txt> <wordsList.txt>");
    exit(1);
  }
  my $srcFile = shift @_;
  my $dstFile = shift @_;

  my $content = &readFile($srcFile);
  my $res     = &formatter($content);
  my @res     = &split($res);
  &writeFile(@res, $dstFile); 
}

&main(@ARGV);
#my $content = &readFile('../tmp/crawl-blogxtu.zapto.org/fc71910dc2918115ebe8b0b5615a2959.html.content.txt');
#my $res = &formatter($content);
#my @res = &split($res);
#&writeFile(@res, "./fc71910dc2918115ebe8b0b5615a2959.html.content.txt.formated");
