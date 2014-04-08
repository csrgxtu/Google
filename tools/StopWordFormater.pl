#!/usr/bin/perl
#
# Author: Archer Reilly
# Date: 04/Apr/2014
# File: StopWordFormater.pl
# Des: this script probably only used once, it is used to format the
# stop words file into one line per word, from a only line file that
# contains all the stop word.
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
  return $content;
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

my $content = &readFile('../data/stopWords.txt');
my @res = &split($content);
&writeFile(@res, '../data/stopWordsFormated.txt');
#foreach my $item (@res) {
 # print($item . "\n");
#}
