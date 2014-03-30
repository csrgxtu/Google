#!/usr/bin/perl
#
# Author: Archer Reilly
# Date: 30/Mar/2014
# File: RetrieveHeadContent.pl
# Des: this Perl script is used to retrieve the head and content information
#   from the data crawled, the head will put into a file with extension head
#   and the content will with an extension content in the same directory.
#
# Produced By CSRGXTU


# A, read file into array
# B, get head and put it into file
# C, get content and put it into file

# process
# process the data file, the wrapper function
#
# @param srcFile
# @param dstFile
# @return boolean
sub process {
  # check out the param
  if (@_ == 0) {
    die("RetrieveHeadContent:process need fileName param!\n");
  }
  ($srcFile, $dstFile) = @_;
  
  # open file
  open(FH, $srcFile) or die("Unable To Open File: $srcFile\n");
  
  # read file into an array
  @data = <FH>;
  
  #&saveHead(@data, $fileName);
  &saveContent(@data, $dstFile);
  
  # close file
  close(FH);
}

# saveHead
# get the head from data and save it to file with head extension
#
# @param @data
# @param fileName
# @return boolean
sub saveHead {
  # check out the param
  if (@_ == 0) {
    die("RetrieveHeadContent:saveHead need data param!\n");
  }
  $fileName = pop(@_);
  $fileName = $fileName . ".head";
  (@data) = @_;
  
  # use reg get the head
  @head;
  foreach $line (@data) {
    if ($line =~ m/Url:/) {
      push(@head, $line);
      last;
    } else {
      push(@head, $line);
    }
  }
  
  &saveArrayFile(@head, $fileName);
}

# saveContent
# get the content from data and save it to file with extension content
#
# @param @data
# @param fileName
# @return boolean
sub saveContent {
  # check out the param
  if (@_ == 0) {
    die("RetrieveHeadContent:saveContent:saveContent need params!\n");
  }
  $fileName = pop(@_);
  $fileName = $fileName . ".content";
  (@data) = @_;
  
  # use reg locate content
  foreach $line (@data) {
    if ($line =~ m/Url:/) {
      $count += 1;
      last;
    } else {
      $count += 1;
    }
  }
  # remove the blank line
  $count += 1;
  @content = @data[$count .. $#data];
  
  &saveArrayFile(@content, $fileName);
}

# saveArrayFile
# save array of data into file you specified
#
# @param array data
# @param fileName
# @return boolean
sub saveArrayFile {
  # check out param
  if (@_ == 0) {
    die("RetrieveHeadContent:saveArrayFile need params!\n");
  }
  $fileName = pop(@_);
  (@data) = @_;

  # open file for write
  open(FH, '>', $fileName) or die("Cant open file: $fileName for Write!\n");  
  
  print FH @data;
  
  # close
  close(FH);
}

# main
# main functions
#
#
sub main {
  if (@ARGV == 0) {
    die("Usage: script fileName\n");
  }
  &process($ARGV[0], $ARGV[0]);
}
&main;
