#!/usr/bin/perl
#
# Author: Archer Reilly
# Date: 09/Apr/2014
# File: transformationVector.pl
# Des: this Perl script is used to build the transformation vector
# of the transformation matrix, column. for each html file, it will
# point to other html files, so I will find the pointers and save
# it to a vector
#
# Produced By CSRGXTU

use strict;
use warnings;
use WWW::Mechanize;

# loadHtmlStr
# load the html file into string
#
# @param fileName
# @return str
sub loadHtmlStr {
  if (@_ ne 1) {
    print "Fatal Error: need html fileName!";
    exit(1);
  }
  my $htmlFile = shift @_;
  
  open(FH, $htmlFile) or die("Unable To Open File: $htmlFile\n");
  
  my $content;
  while (<FH>) {
    $content .= $_;
  }
  
  close(FH);
  
  return $content;
}

# retrieveHref
# retrieve the href links in A tag in an html string, notion, only
# in A tag, not interested in other tags
#
# @param htmlStr
# @return array
sub retrieveHref {
  if (@_ ne 1) {
    print "Fatal Error: need htmlStr parameter!\n";
    exit(1);
  }
  my $htmlStr = shift @_;
  
  foreach ($htmlStr =~ /<a[ ]+href="(http:.*)" /ig) {
    print $_ . "\n";
  }
  #my @links = $htmlStr =~ /<a[ ]+href="(http:\/\/.*)" /ig;
  #print @links;
  
}

sub retrieveLinks {
  if (@_ ne 1) {
    print "Fatal Error: need htmlFile parameter!\n";
    exit(1);
  }
  my $htmlFile = shift @_;
  
  my $url = "file:///home/archer/Documents/Google/tools/$htmlFile"
  my $mech = WWW::Mechanize->new();
  $mech->get($url);
  my @links = $mech->links();
  foreach my $link (@links) {
    print "LINK: " . $link->url() . "\n";
    print "DESCRIPTION: " . $link->text() . "\n";
  }
}

# main
# make all subs work together
#
# @param ARGV
sub main {
  if (@_ != 1) {
    print "Usage: transformationVector.pl <htmlFile>\n";
    exit(1);
  }
  my $htmlFile = shift @_;
  
  my $content = &loadHtmlStr($htmlFile);
  #&retrieveHref($content);
  &retrieveLinks($htmlFile);
}

&main(@ARGV);
